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
import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.standrews.cs.digitising_scotland.record_classification.classifiers.closestmatchmap.ClosestMatchMap;
import uk.ac.standrews.cs.digitising_scotland.record_classification.classifiers.closestmatchmap.SimilarityMetric;
import uk.ac.standrews.cs.digitising_scotland.record_classification.classifiers.closestmatchmap.SimilarityMetricFromSimmetricFactory;
import uk.ac.standrews.cs.digitising_scotland.record_classification.classifiers.closestmatchmap.StringSimilarityClassifier;
import uk.ac.standrews.cs.digitising_scotland.record_classification.classifiers.lookup.ExactMatchClassifier;
import uk.ac.standrews.cs.digitising_scotland.record_classification.classifiers.olr.OLRClassifier;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.bucket.Bucket;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.classification.Classification;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.vectors.CodeIndexer;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.vectors.VectorFactory;

import java.util.Map;

/**
 * The Class ClassifierTrainer contains methods for training both {@link OLRClassifier} and {@link ExactMatchClassifier} objects.
 * 
 */
public class ClassifierTrainer {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassifierTrainer.class);

    /** The vector factory. */
    private VectorFactory vectorFactory;

    /** The olr classifier. */
    private OLRClassifier olrClassifier;

    private StringSimilarityClassifier stringSimClassifier;

    /** The exact match classifier. */
    private ExactMatchClassifier exactMatchClassifier;

    /** The training bucket. */
    private Bucket trainingBucket;

    /** The experimental folder name. */
    private String experimentalFolderName;

    /**
     * Instantiates a new classifier trainer with a bucket containing training data and the folder where models should be written to.
     *
     * @param trainingBucket the training bucket
     * @param experimentalFolderName the experimental folder name
     * @param codeIndex the code index
     */
    public ClassifierTrainer(final Bucket trainingBucket, final String experimentalFolderName, final CodeIndexer codeIndex) {

        this.trainingBucket = trainingBucket;
        this.experimentalFolderName = experimentalFolderName;
        vectorFactory = new VectorFactory(trainingBucket, codeIndex);

    }

    public StringSimilarityClassifier trainStringSimilarityClassifier(final Map<String, Classification> map, final AbstractStringMetric simMetric) {

        SimilarityMetricFromSimmetricFactory factory = new SimilarityMetricFromSimmetricFactory();
        SimilarityMetric<String> metric = factory.create(simMetric);
        ClosestMatchMap<String, Classification> closestMatchMap = new ClosestMatchMap<>(metric, map);
        return stringSimClassifier = new StringSimilarityClassifier(closestMatchMap);
    }

    public StringSimilarityClassifier getStringSimClassifier() {

        return stringSimClassifier;
    }

    /**
     * Trains an exact match classifier with data from the training bucket. The model is written to the experimental folder name
     * /Models/lookupTable.
     *
     * @return the exact match classifier with a build lookup table/model
     * @throws Exception the exception
     */
    public ExactMatchClassifier trainExactMatchClassifier() throws Exception {

        LOGGER.info("********** Creating Lookup Tables **********");

        exactMatchClassifier = new ExactMatchClassifier();
        exactMatchClassifier.setModelFileName(experimentalFolderName + "/Models/lookupTable");
        exactMatchClassifier.train(trainingBucket);
        return exactMatchClassifier;
    }

    /**
     * Trains an online logistic regression (OLR) classifier. The model is written to the experimental folder
     * + "/Models/olrModel".
     *
     * @return the olrClassifier classifier after training
     * @throws Exception the exception
     */
    public OLRClassifier trainOLRClassifier() throws Exception {

        LOGGER.info("********** Training OLR Classifiers **********");
        olrClassifier = new OLRClassifier();
        OLRClassifier.setModelPath(experimentalFolderName + "/Models/olrModel");
        olrClassifier.train(trainingBucket);
        return olrClassifier;
    }

    /**
     * Instansiates an {@link ExactMatchClassifier} and a {@link OLRClassifier} from models stored on disk.
     * The model locations should be in "/lookupTable" and "/olrModel" from modelLocations path.
     *
     * @param modelLocations Path to parent directory of pre-built models.
     * @return the existings models
     */
    public void getExistingModels(final String modelLocations) {

        exactMatchClassifier = new ExactMatchClassifier();
        exactMatchClassifier.setModelFileName(modelLocations + "/lookupTable");
        exactMatchClassifier.loadModelFromDefaultLocation();
        olrClassifier = new OLRClassifier();
        OLRClassifier.setModelPath(modelLocations + "/olrModel");
        olrClassifier = olrClassifier.getModelFromDefaultLocation();
        vectorFactory = olrClassifier.getVectorFactory();

    }

    /**
     * Gets the vector factory that was used in vector creation.
     *
     * @return the vector factory
     */
    public VectorFactory getVectorFactory() {

        return vectorFactory;
    }

    /**
     * Gets the olr classifier.
     *
     * @return the olr classifier
     */
    public OLRClassifier getOlrClassifier() {

        return olrClassifier;
    }

    /**
     * Gets the exact match classifier.
     *
     * @return the exact match classifier
     */
    public ExactMatchClassifier getExactMatchClassifier() {

        return exactMatchClassifier;
    }

}
