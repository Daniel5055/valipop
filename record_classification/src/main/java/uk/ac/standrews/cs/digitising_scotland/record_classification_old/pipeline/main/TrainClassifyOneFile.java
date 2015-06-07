/*
 * Copyright 2015 Digitising Scotland project:
 * <http://digitisingscotland.cs.st-andrews.ac.uk/>
 *
 * This file is part of the module record_classification.
 *
 * record_classification is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * record_classification is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with record_classification. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package uk.ac.standrews.cs.digitising_scotland.record_classification_old.pipeline.main;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.classifiers.lookup.ExactMatchClassifier;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.classifiers.olr.OLRClassifier;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.classifiers.resolver.LogLengthWeightedLossFunction;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.analysis_metrics.CodeMetrics;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.analysis_metrics.ListAccuracyMetrics;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.analysis_metrics.StrictConfusionMatrix;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.bucket.Bucket;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.bucket.BucketFilter;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.bucket.BucketUtils;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.code.CodeDictionary;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.code.CodeNotValidException;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.records.Record;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.vectors.CodeIndexer;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.pipeline.*;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.tools.Timer;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.tools.configuration.MachineLearningConfiguration;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.writers.DataClerkingWriter;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.writers.FileComparisonWriter;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.writers.MetricsWriter;

import java.io.File;
import java.io.IOException;

public class TrainClassifyOneFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainClassifyOneFile.class);
    private static final double DEFAULT_TRAINING_RATIO = 0.8;
    private static final String usageHelp = "usage: $" + TrainClassifyOneFile.class.getSimpleName() + "    <goldStandardDataFile>  <propertiesFile>  <trainingRatio(optional)>    <output multiple classificatiosn";

    /**
     * Entry method for training and classifying a batch of records into
     * multiple codes.
     * 
     * @param args
     *            <file1> training file <file2> file to classify
     * @throws Exception
     *             If exception occurs
     */
    public static void main(final String[] args) throws Exception, CodeNotValidException {

        TrainClassifyOneFile instance = new TrainClassifyOneFile();
        instance.run(args);
    }

    public Bucket run(final String[] args) throws Exception, CodeNotValidException {

        printArgs(args);

        String experimentalFolderName;
        File goldStandard;
        Bucket trainingBucket;
        Bucket predictionBucket;

        Timer timer = PipelineUtils.initAndStartTimer();

        experimentalFolderName = PipelineUtils.setupExperimentalFolders("TestExperiments");

        goldStandard = parseGoldStandFile(args);
        parseProperties(args);
        double trainingRatio = parseTrainingRatio(args);
        boolean multipleClassifications = parseMultipleClassifications(args);

        File codeDictionaryFile = new File(MachineLearningConfiguration.getDefaultProperties().getProperty("codeDictionaryFile"));
        CodeDictionary codeDictionary = new CodeDictionary(codeDictionaryFile);

        BucketGenerator generator = new BucketGenerator(codeDictionary);
        Bucket allInputRecords = generator.generateTrainingBucket(goldStandard);

        Bucket[] trainingPredicition = randomlyAssignToTrainingAndPrediction(allInputRecords, trainingRatio);
        trainingBucket = trainingPredicition[0];
        predictionBucket = trainingPredicition[1];

        LOGGER.info("********** Training Classifiers **********");

        CodeIndexer codeIndex = new CodeIndexer(allInputRecords);

        ExactMatchClassifier exactMatchClassifier = new ExactMatchClassifier();
        exactMatchClassifier.setModelFileName(experimentalFolderName + "/Models/lookupTable");
        exactMatchClassifier.train(trainingBucket);

        OLRClassifier.setModelPath(experimentalFolderName + "/Models/olrModel");
        OLRClassifier olrClassifier = new OLRClassifier();
        olrClassifier.train(trainingBucket);

        IPipeline exactMatchPipeline = new ExactMatchPipeline(exactMatchClassifier);
        IPipeline machineLearningClassifier = new ClassifierPipeline(olrClassifier, trainingBucket, new LogLengthWeightedLossFunction(), multipleClassifications, true);

        Bucket notExactMatched = exactMatchPipeline.classify(predictionBucket);
        Bucket notMachineLearned = machineLearningClassifier.classify(notExactMatched);
        Bucket successfullyClassifiedMachineLearning = machineLearningClassifier.getSuccessfullyClassified();
        Bucket successfullyExactMatched = exactMatchPipeline.getSuccessfullyClassified();
        Bucket uniqueRecordsExactMatched = BucketFilter.uniqueRecordsOnly(successfullyExactMatched);
        Bucket uniqueRecordsMachineLearned = BucketFilter.uniqueRecordsOnly(successfullyClassifiedMachineLearning);
        Bucket uniqueRecordsNotMatched = BucketFilter.uniqueRecordsOnly(notMachineLearned);

        LOGGER.info("Exact Matched Bucket Size: " + successfullyExactMatched.size());
        LOGGER.info("Machine Learned Bucket Size: " + successfullyClassifiedMachineLearning.size());
        LOGGER.info("Not Classifed Bucket Size: " + notMachineLearned.size());
        LOGGER.info("Unique Exact Matched Bucket Size: " + uniqueRecordsExactMatched.size());
        LOGGER.info("UniqueMachine Learned Bucket Size: " + uniqueRecordsMachineLearned.size());
        LOGGER.info("Unique Not Classifed Bucket Size: " + uniqueRecordsNotMatched.size());

        Bucket allClassifed = BucketUtils.getUnion(successfullyExactMatched, successfullyClassifiedMachineLearning);
        Bucket allRecords = BucketUtils.getUnion(allClassifed, notMachineLearned);
        assert(allRecords.size() == predictionBucket.size());

        writeRecords(experimentalFolderName, allRecords);

        writeComparisonFile(experimentalFolderName, allRecords);

        LOGGER.info("********** Output Stats **********");

        printAllStats(experimentalFolderName, codeIndex, allRecords, "allRecords");
        printAllStats(experimentalFolderName, codeIndex, successfullyExactMatched, "exactMatched");
        printAllStats(experimentalFolderName, codeIndex, successfullyClassifiedMachineLearning, "machineLearned");

        timer.stop();

        return allRecords;
    }

    private void printArgs(final String[] args) {

        StringBuilder sb = new StringBuilder();
        for (String string : args) {
            sb.append(string + " ");
        }
        LOGGER.info("Running with args: " + sb.toString().trim());
    }

    private void printAllStats(final String experimentalFolderName, final CodeIndexer codeIndex, final Bucket bucket, final String identifier) throws IOException {

        final Bucket uniqueRecordsOnly = BucketFilter.uniqueRecordsOnly(bucket);

        LOGGER.info("All Records");
        LOGGER.info("All Records Bucket Size: " + bucket.size());
        CodeMetrics codeMetrics = new CodeMetrics(new StrictConfusionMatrix(bucket, codeIndex), codeIndex);
        ListAccuracyMetrics accuracyMetrics = new ListAccuracyMetrics(bucket, codeMetrics);
        MetricsWriter metricsWriter = new MetricsWriter(accuracyMetrics, experimentalFolderName, codeIndex);
        metricsWriter.write(identifier, "nonUniqueRecords");
        accuracyMetrics.prettyPrint("AllRecords");

        LOGGER.info("Unique Only");
        LOGGER.info("Unique Only  Bucket Size: " + uniqueRecordsOnly.size());

        CodeMetrics codeMetrics1 = new CodeMetrics(new StrictConfusionMatrix(uniqueRecordsOnly, codeIndex), codeIndex);
        accuracyMetrics = new ListAccuracyMetrics(uniqueRecordsOnly, codeMetrics1);
        accuracyMetrics.prettyPrint("Unique Only");
        metricsWriter = new MetricsWriter(accuracyMetrics, experimentalFolderName, codeIndex);
        metricsWriter.write(identifier, "uniqueRecords");
        accuracyMetrics.prettyPrint("UniqueRecords");
    }

    private void writeComparisonFile(final String experimentalFolderName, final Bucket allClassifed) throws IOException {

        final String comparisonReportPath = "/Data/" + "MachineLearning" + "/comaprison.txt";
        final File outputPath2 = new File(experimentalFolderName + comparisonReportPath);
        Files.createParentDirs(outputPath2);

        final FileComparisonWriter comparisonWriter = new FileComparisonWriter(outputPath2, "\t");
        for (final Record record : allClassifed) {
            comparisonWriter.write(record);
        }
        comparisonWriter.close();
    }

    private void writeRecords(final String experimentalFolderName, final Bucket allClassifed) throws IOException {

        final String nrsReportPath = "/Data/" + "MachineLearning" + "/NRSData.txt";
        final File outputPath = new File(experimentalFolderName + nrsReportPath);
        Files.createParentDirs(outputPath);
        final DataClerkingWriter writer = new DataClerkingWriter(outputPath);
        for (final Record record : allClassifed) {
            writer.write(record);
        }
        writer.close();
    }

    private static File parseGoldStandFile(final String[] args) {

        File goldStandard = null;
        if (args.length > 5) {
            System.err.println(usageHelp);
        }
        else {
            goldStandard = new File(args[0]);
            PipelineUtils.exitIfDoesNotExist(goldStandard);

        }
        return goldStandard;
    }

    public File parseProperties(String[] args) {

        File properties = null;
        if (args.length > 5) {
            System.err.println(usageHelp);
        }
        else {
            properties = new File(args[1]);
            if (properties.exists()) {
                MachineLearningConfiguration.loadProperties(properties);
            }
            else {
                LOGGER.error("Supplied properties file does not exsists. Using system defaults.");
            }
        }
        return properties;
    }

    private boolean parseMultipleClassifications(final String[] args) {

        if (args.length > 5) {
            System.err.println(usageHelp);
        }
        else {
            if (args[3].equals("1")) { return true; }
        }
        return false;

    }

    private static double parseTrainingRatio(final String[] args) {

        double trainingRatio = DEFAULT_TRAINING_RATIO;
        if (args.length > 1) {
            double userRatio = Double.valueOf(args[2]);
            if (userRatio > 0 && userRatio < 1) {
                trainingRatio = userRatio;
            }
            else {
                System.err.println("trainingRatio must be between 0 and 1. Exiting.");
                System.exit(1);
            }
        }
        return trainingRatio;
    }

    private Bucket[] randomlyAssignToTrainingAndPrediction(final Bucket bucket, final double trainingRatio) {

        Bucket[] buckets = initBuckets();

        for (Record record : bucket) {
            if (Math.random() < trainingRatio) {
                buckets[0].addRecordToBucket(record);
            }
            else {
                buckets[1].addRecordToBucket(record);
            }
        }
        return buckets;
    }

    private Bucket[] initBuckets() {

        Bucket[] buckets = new Bucket[2];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new Bucket();
        }
        return buckets;
    }

}
