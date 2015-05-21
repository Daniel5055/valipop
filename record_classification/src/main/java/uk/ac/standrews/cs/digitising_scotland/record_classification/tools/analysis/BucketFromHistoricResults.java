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
package uk.ac.standrews.cs.digitising_scotland.record_classification.tools.analysis;

import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.OriginalData;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.analysis_metrics.AbstractConfusionMatrix;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.analysis_metrics.CodeMetrics;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.analysis_metrics.StrictConfusionMatrix;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.bucket.Bucket;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.classification.Classification;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.code.CodeDictionary;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.code.CodeNotValidException;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.records.Record;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.tokens.TokenSet;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.vectors.CodeIndexer;
import uk.ac.standrews.cs.digitising_scotland.record_classification.exceptions.InputFormatException;
import uk.ac.standrews.cs.digitising_scotland.record_classification.tools.ReaderWriterFactory;
import uk.ac.standrews.cs.digitising_scotland.record_classification.tools.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;

public class BucketFromHistoricResults {

    String fileName;
    CodeDictionary cd;

    public static void main(final String[] args) throws IOException, InputFormatException, CodeNotValidException {

        String inputFile = "/Users/jkc25/Documents/resultsForAmsterdamPaper/resultsTopPerforming/outputDirectorySetup_20_Output5/occOutput.csv";
        File codeDictionaryFile = new File("HiscoCodeDict.txt");
        BucketFromHistoricResults instance = new BucketFromHistoricResults(inputFile, codeDictionaryFile);
        instance.getResults(new File(inputFile));
    }

    public BucketFromHistoricResults(final String fileName, final File codeDictionaryFile) throws IOException {

        this.fileName = fileName;
        cd = new CodeDictionary(codeDictionaryFile);
    }

    public void getResults(final File inputFile) throws IOException, InputFormatException, CodeNotValidException {

        Bucket[] buckets = processFile(inputFile);
        CodeIndexer index = new CodeIndexer(cd);
        printStats("nb", buckets[0], index);
        printStats("sgd", buckets[1], index);
        printStats("string", buckets[2], index);

    }

    private void printStats(final String id, final Bucket bucket, final CodeIndexer index) {

        AbstractConfusionMatrix confusionMatrix = new StrictConfusionMatrix(bucket, index);
        CodeMetrics cm = new CodeMetrics(confusionMatrix, index);
        System.out.println(id + " Stats \n " + cm.getMicroStatsAsString());
        System.out.println(id + " Stats \n " + cm.getMacroStatsAsString());

    }

    public Bucket[] processFile(final File inputFile) throws IOException, InputFormatException, CodeNotValidException {

        Bucket nbBucket = new Bucket();
        Bucket sgdBucket = new Bucket();
        Bucket stringBucket = new Bucket();

        BufferedReader br = ReaderWriterFactory.createBufferedReader(inputFile);
        String line = "";
        int count = 0;

        while ((line = br.readLine()) != null) {
            Record[] records = processLine(line, count++);
            nbBucket.addRecordToBucket(records[0]);
            sgdBucket.addRecordToBucket(records[1]);
            stringBucket.addRecordToBucket(records[2]);
        }

        Bucket[] buckets = new Bucket[3];
        buckets[0] = nbBucket;
        buckets[1] = sgdBucket;
        buckets[2] = stringBucket;
        return buckets;
    }

    private Record[] processLine(final String line, final int count) throws InputFormatException, CodeNotValidException {

        String normLine = normalize(line);
        String[] lineSplit = normLine.split(Utils.getCSVComma());
        Record[] records = getRecords(lineSplit, count);
        return records;

    }

    private Record[] getRecords(final String[] lineSplit, final int count) throws InputFormatException, CodeNotValidException {

        OriginalData originalData = new OriginalData(lineSplit[0], 2004, 1, fileName);
        Record record = new Record(count, originalData);
        record = createGoldStandardRecord(lineSplit, record);

        Record nbRecord = addClassification(lineSplit, record.copyOfOriginalRecord(record), 2);
        Record sgdRecord = addClassification(lineSplit, record.copyOfOriginalRecord(record), 4);
        Record stringRecord = addClassification(lineSplit, record.copyOfOriginalRecord(record), 7);

        Record[] arr = new Record[3];
        arr[0] = nbRecord;
        arr[1] = sgdRecord;
        arr[2] = stringRecord;

        return arr;
    }

    private Record addClassification(final String[] lineSplit, final Record record, final int classificationPos) throws CodeNotValidException {

        final String codeAsString = lineSplit[classificationPos];

        Classification classification = new Classification(cd.getCode(codeAsString), new TokenSet(lineSplit[0]), 1.0);
        record.addClassification(classification);
        return record;
    }

    private Record createGoldStandardRecord(final String[] lineSplit, final Record nbRecord) throws CodeNotValidException {

        Set<Classification> goldStandardClassification = new HashSet<>();
        Classification goldStandard = new Classification(cd.getCode(lineSplit[1].trim()), new TokenSet(lineSplit[0]), 1.0);
        goldStandardClassification.add(goldStandard);
        nbRecord.getOriginalData().setGoldStandardClassification(goldStandardClassification);
        return nbRecord;
    }

    private String normalize(final String string) {

        String subjectString = Normalizer.normalize(string, Normalizer.Form.NFD);
        String resultString = subjectString.replaceAll("[^\\x00-\\x7F]", "");

        return resultString;
    }
}
