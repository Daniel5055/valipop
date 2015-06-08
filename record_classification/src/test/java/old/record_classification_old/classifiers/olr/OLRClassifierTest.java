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
package old.record_classification_old.classifiers.olr;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import old.record_classification_old.classifiers.ClassifierTestingHelper;
import old.record_classification_old.datastructures.OriginalData;
import old.record_classification_old.datastructures.bucket.Bucket;
import old.record_classification_old.datastructures.classification.Classification;
import old.record_classification_old.datastructures.code.Code;
import old.record_classification_old.datastructures.code.CodeDictionary;
import old.record_classification_old.datastructures.code.CodeNotValidException;
import old.record_classification_old.datastructures.records.Record;
import old.record_classification_old.datastructures.records.RecordFactory;
import old.record_classification_old.datastructures.tokens.TokenSet;
import old.record_classification_old.exceptions.InputFormatException;
import old.record_classification_old.tools.Timer;
import old.record_classification_old.tools.configuration.MachineLearningConfiguration;
import old.record_classification_old.classifiers.AbstractClassifier;

import java.io.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * The Class OLRClassifierTest.
 */
//FIXME
public class OLRClassifierTest {

    /**
     * The os.
     */
    private OutputStream os;

    /**
     * The original out.
     */
    private PrintStream originalOut;

    /**
     * The helper.
     */
    private ClassifierTestingHelper helper = new ClassifierTestingHelper();

    private Properties properties = MachineLearningConfiguration.getDefaultProperties();

    // private CodeIndexer index;

    private CodeDictionary codeDictionary;

    /**
     * Setup.
     *
     * @throws Exception the exception
     */
    @Before
    public void setup() throws Exception, CodeNotValidException {

        codeDictionary = new CodeDictionary(new File(getClass().getResource("/CodeFactoryTestFile.txt").getFile()));
        createTrainingBucket();
        divertOutputStream();
    }

    /**
     * Divert output stream.
     */
    private void divertOutputStream() {

        originalOut = System.out;
        os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {

        System.setOut(originalOut);
        FileUtils.deleteQuietly(new File("target/olrClassifierWriteTest"));
        FileUtils.deleteQuietly(new File("target/OLRWriteTest.txt"));
        FileUtils.deleteQuietly(new File("target/olrModelPath"));

    }

    @Test
    public void trainExistingModelWithNewDataTest() throws Exception, CodeNotValidException {

        Bucket bucketA = createTrainingBucket();
        OLRClassifier olrClassifier1 = new OLRClassifier();
        olrClassifier1.train(bucketA);
        olrClassifier1.serializeModel("target/olrClassifierWriteTest2");
        OLRClassifier olrClassifier2 = new OLRClassifier();
        olrClassifier2 = olrClassifier2.deSerializeModel("target/olrClassifierWriteTest2");
        Iterable<Record> records = createNewRecords();
        bucketA.addCollectionOfRecords(records);
        olrClassifier2.train(bucketA);
    }

    private Iterable<Record> createNewRecords() throws InputFormatException, CodeNotValidException {

        String[] description = {"Occupation 1", "Another Job", "Foo", "Bar", "FooManChu"};
        String[] tokenSetArry = {"The Red", "Cat Or", "Should it be fox", "jumped over something", "that", "should maybe be a ", "big cat or something", "idk", "jamie", "fraser"};

        List<Record> records = new ArrayList<Record>();

        for (int i = 0; i < 100; i++) {

            final OriginalData originalData = new OriginalData(description[i % 5], 2010 + i, 1, "fileName.txt");
            Set<Classification> goldStandardClassification = new HashSet<>();
            Code code = getRandomCode();
            goldStandardClassification.add(new Classification(code, new TokenSet(tokenSetArry[i % 10]), 1.0));
            originalData.setGoldStandardClassification(goldStandardClassification);
            Record r = new Record(i, originalData);
            records.add(r);
        }

        return records;
    }

    private Code getRandomCode() throws CodeNotValidException {

        double rnd = Math.random();
        if (rnd < 0.33) {
            return codeDictionary.getCode("95240");
        } else if (rnd > 0.3 && rnd < 0.66) {
            return codeDictionary.getCode("9500");
        }
        return codeDictionary.getCode("952");
    }

    /**
     * Test classify with de serialized model.
     *
     * @throws Exception
     */
    @Test
    public void testClassifyWithDeSerializedModel() throws Exception, CodeNotValidException {

        Bucket bucketA = createTrainingBucket();

        OLRClassifier olrClassifier1 = new OLRClassifier();

        olrClassifier1.train(bucketA);
        olrClassifier1.serializeModel("target/olrClassifierWriteTest");
        OLRClassifier olrClassifier2 = new OLRClassifier();
        olrClassifier2 = olrClassifier2.deSerializeModel("target/olrClassifierWriteTest");

        for (Record record : bucketA) {
            String s = record.getDescription();
            Classification c1 = AbstractClassifier.getSingleClassification(olrClassifier1.classify(new TokenSet(s)));
            Classification c2 = AbstractClassifier.getSingleClassification(olrClassifier2.classify(new TokenSet(s)));
            assertEquals(c1, c2);
        }
    }

    /**
     * if the record's gold standard set contains the code then return true else return false.
     */
    private boolean recordHasCodeInGoldStandardSet(Record record, Code c) {

        Set<Classification> gs = record.getGoldStandardClassificationSet();
        Set<Code> gsCodes = new HashSet<>();
        for (Classification classification : gs) {
            gsCodes.add(classification.getCode());
        }
        return gsCodes.contains(c);
    }

    /**
     * Creates the training bucket.
     *
     * @return the bucket
     * @throws Exception the exception
     */
    private Bucket createTrainingBucket() throws Exception, CodeNotValidException {

        properties.setProperty("numCategories", "8");
        properties.setProperty("OLRPoolSize", "3");
        properties.setProperty("OLRFolds", "3");
        properties.setProperty("OLRPoolNumSurvivors", "1");
        properties.setProperty("OLRShuffledReps", "1");
        properties.setProperty("perTermLearning", "false");
        properties.setProperty("olrRegularisation", "false");
        properties.setProperty("numDropped", "1");

        File inputFileTraining = new File(getClass().getResource("/occupationTestFormatPipe.txt").getFile());
        List<Record> listOfRecordsTraining = RecordFactory.makeUnCodedRecordsFromFile(inputFileTraining);
        Bucket bucketA = new Bucket(listOfRecordsTraining);
        bucketA = helper.giveBucketTestingOccCodes(bucketA);
        return bucketA;
    }

    /**
     * Test stop listener.
     *
     * @throws Exception the exception
     */
    @Test
    public void testStop() throws Exception, CodeNotValidException {

        Timer t = new Timer();
        t.start();

        Bucket bucketA = createTrainingBucket();

        String data = "count\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        OLRClassifier olrClassifier1 = new OLRClassifier();
        MachineLearningConfiguration.getDefaultProperties().setProperty("reps", "5000000");

        olrClassifier1.train(bucketA);

        System.setIn(stdin);

        t.stop();
        System.setOut(System.out);
        long elapsedTime = (long) (t.elapsedTime() / 1000000);
        int cutOff = 1000;
        Assert.assertTrue(elapsedTime < cutOff);

    }

}
