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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.mahout.math.NamedVector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import old.record_classification_old.datastructures.code.CodeDictionary;
import old.record_classification_old.datastructures.code.CodeNotValidException;
import old.record_classification_old.datastructures.vectors.CodeIndexer;
import old.record_classification_old.datastructures.vectors.VectorFactory;
import old.record_classification_old.tools.configuration.MachineLearningConfiguration;

/**
 *
 * Created by fraserdunlop on 06/05/2014 at 11:37.
 */
public class OLRCrossFoldTest {

    /**
     * The properties.
     */
    private Properties properties = MachineLearningConfiguration.getDefaultProperties();

    /**
     * The vector factory.
     */
    private VectorFactory vectorFactory;

    /**
     * The training vector list.
     */
    private ArrayList<NamedVector> trainingVectorList = new ArrayList<NamedVector>();

    /**
     * The model.
     */
    private OLRCrossFold model;

    private CodeIndexer index;

    private CodeDictionary codeDictionary;

    /**
     * Setup.
     *
     * @throws IOException           Signals that an I/O exception has occurred.
     * @throws CodeNotValidException
     */
    @Before
    public void setup() throws IOException, CodeNotValidException {

        if (!new File("target/olrModelPath").delete()) {
            System.err.println("Could not clean up all resources.");
        }

        String codeDictionaryFile = getClass().getResource("/CodeFactoryOLRTestFile.txt").getFile();

        codeDictionary = new CodeDictionary(new File(codeDictionaryFile));
        index = new CodeIndexer(codeDictionary);
        vectorFactory = new VectorFactory();

        populateDictionary();

        setProperties();

        trainingVectorList = generateTrainingVectors();

        model = new OLRCrossFold(trainingVectorList, properties);
        model.train();

    }

    private void setProperties() {

        properties.setProperty("numCategories", "10");
        properties.setProperty("OLRPoolSize", "3");
        properties.setProperty("OLRFolds", "3");
        properties.setProperty("OLRPoolNumSurvivors", "1");
        properties.setProperty("OLRShuffledReps", "1");
        properties.setProperty("perTermLearning", "false");
        properties.setProperty("olrRegularisation", "false");
        properties.setProperty("numDropped", "1");
    }

    @After
    public void tearDown() {

        if (!new File("target/testOLRCrossfoldWrite.txt").delete()) {
            System.err.println("Could not clean up all resources.");
        }
    }

    /**
     * Generate training vectors.
     *
     * @return the array list
     * @throws IOException           Signals that an I/O exception has occurred.
     * @throws CodeNotValidException
     */
    private ArrayList<NamedVector> generateTrainingVectors() throws IOException, CodeNotValidException {

        BufferedReader br = getBufferedReaderOfCodeDictionaryFile();
        String line;
        ArrayList<NamedVector> trainingVectorList = new ArrayList<NamedVector>();
        while ((line = br.readLine()) != null) {
            trainingVectorList.add(createTrainingVector(line));
        }
        return trainingVectorList;
    }

    /**
     * Populate dictionary.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void populateDictionary() throws IOException {

        BufferedReader br = getBufferedReaderOfCodeDictionaryFile();
        String line;
        while ((line = br.readLine()) != null) {
            String[] splitLine = line.split("\t");
            String descriptionFromFile = splitLine[1].trim();
            vectorFactory.updateDictionary(descriptionFromFile);
        }
    }

    /**
     * Creates the training vector.
     *
     * @param line the line
     * @return the named vector
     * @throws IOException
     * @throws CodeNotValidException
     */
    private NamedVector createTrainingVector(final String line) throws IOException, CodeNotValidException {

        String[] splitLine = line.split("\t");
        String codeFromFile = splitLine[0].trim();
        String descriptionFromFile = splitLine[1].trim();
        CodeDictionary codeDictionary = new CodeDictionary(new File(getClass().getResource("/CodeFactoryOLRTestFile.txt").getFile()));

        int id = index.getID(codeDictionary.getCode(codeFromFile));
        return vectorFactory.createNamedVectorFromString(descriptionFromFile, Integer.toString(id));
    }

    /**
     * Gets the buffered reader of code dictionary file.
     *
     * @return the buffered reader of code dictionary file
     * @throws FileNotFoundException the file not found exception
     */
    private BufferedReader getBufferedReaderOfCodeDictionaryFile() throws FileNotFoundException {

        File file = new File(getClass().getResource("/CodeFactoryOLRTestFile.txt").getFile());
        return new BufferedReader(new FileReader(file));
    }

    /**
     * Test training de serialized model.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testTrainingDeSerializedModel() throws IOException, ClassNotFoundException {

        String filename = "target/testOLRCrossfoldWrite.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(model);
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);

        OLRCrossFold olrCrossFold = (OLRCrossFold) inputStream.readObject();
        SerializableDenseMatrix a = new SerializableDenseMatrix(new double[1][1]);

        double[][] modelBetaArray = a.asArray(model.getAverageBetaMatrix());
        double[][] olrBetaArray = a.asArray(olrCrossFold.getAverageBetaMatrix());

        for (int i = 0; i < modelBetaArray.length; i++) {
            for (int j = 0; j < modelBetaArray[0].length; j++) {
                Assert.assertEquals(modelBetaArray[i][j], olrBetaArray[i][j], 0.0001);
            }
        }

    }
}
