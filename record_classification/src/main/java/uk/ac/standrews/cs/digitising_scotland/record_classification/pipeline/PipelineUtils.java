/*
 * Copyright 2014 Digitising Scotland project:
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
package uk.ac.standrews.cs.digitising_scotland.record_classification.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.standrews.cs.digitising_scotland.record_classification.classifiers.lookup.ExactMatchClassifier;
import uk.ac.standrews.cs.digitising_scotland.record_classification.classifiers.olr.OLRClassifier;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.analysis_metrics.CodeMetrics;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.analysis_metrics.ListAccuracyMetrics;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.bucket.Bucket;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.vectors.CodeIndexer;
import uk.ac.standrews.cs.digitising_scotland.record_classification.exceptions.FolderCreationException;
import uk.ac.standrews.cs.digitising_scotland.record_classification.tools.ReaderWriterFactory;
import uk.ac.standrews.cs.digitising_scotland.record_classification.tools.Timer;
import uk.ac.standrews.cs.digitising_scotland.record_classification.tools.Utils;
import uk.ac.standrews.cs.digitising_scotland.record_classification.tools.configuration.MachineLearningConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * Utility class containing methods to help with the creation and use of the exact match and machine learning pipelines.
 * @author jkc25
 *
 */
public final class PipelineUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PipelineUtils.class);

    private PipelineUtils() {

    }

    public static String printCodeMetrics(final Bucket bucket, final CodeMetrics codeMetrics, final ListAccuracyMetrics accuracyMetrics, final CodeIndexer codeIndexer, final String strictCodeStatsPath, final String codeStatsPath, final String experimentalFolderName, final String identifier) {

        LOGGER.info(codeMetrics.getMicroStatsAsString());
        codeMetrics.writeStats(strictCodeStatsPath);
        LOGGER.info(strictCodeStatsPath + ": " + codeMetrics.getTotalCorrectlyPredicted());
        accuracyMetrics.generateMarkDownSummary(experimentalFolderName, codeStatsPath);
        return strictCodeStatsPath;
    }

    public static Timer initAndStartTimer() {

        Timer timer = new Timer();
        timer.start();
        return timer;
    }

    public static OLRClassifier getExistingOLRModel(final String modelLocations) {

        OLRClassifier olrClassifier = new OLRClassifier();
        OLRClassifier.setModelPath(modelLocations + "/olrModel");
        try {
            olrClassifier = olrClassifier.deSerializeModel(modelLocations + "/olrModel");
        }
        catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return olrClassifier;
    }

    public static ExactMatchClassifier getExistingExactMatchClassifier(final String modelLocations) {

        ExactMatchClassifier exactMatchClassifier = new ExactMatchClassifier();
        exactMatchClassifier.setModelFileName(modelLocations + "/lookupTable");
        return exactMatchClassifier.getModelFromDefaultLocation();
    }

    public static void printStatusUpdate() {

        LOGGER.info("********** Training Classifiers **********");
        LOGGER.info("Training with a dictionary size of: " + MachineLearningConfiguration.getDefaultProperties().getProperty("numFeatures"));
        LOGGER.info("Training with this number of output classes: " + MachineLearningConfiguration.getDefaultProperties().getProperty("numCategories"));
    }

    public static boolean checkFileType(final File inputFile) throws IOException {

        BufferedReader br = ReaderWriterFactory.createBufferedReader(inputFile);
        String line = "";
        if ((line = br.readLine()) != null) {
            br.close();

            final int expectedLineLength = 38;
            final String[] length = line.split(Utils.getCSVComma());
            if (length.length == expectedLineLength) { return true; }
        }
        return false;
    }

    public static String setupExperimentalFolders(final String baseFolder) {

        final String experimentalFolderName = Utils.getExperimentalFolderName(baseFolder, "Experiment");

        if (!(new File(experimentalFolderName).mkdirs() && new File(experimentalFolderName + "/Reports").mkdirs() && new File(experimentalFolderName + "/Data").mkdirs() && new File(experimentalFolderName + "/Models").mkdirs())) { throw new FolderCreationException(
                        "couldn't create experimental folder"); }

        return experimentalFolderName;
    }

    public static void exitIfDoesNotExist(final File file) {

        if (!file.exists()) {
            LOGGER.error(file.getAbsolutePath() + " does not exist. Exiting");
            throw new RuntimeException();
        }
    }
}
