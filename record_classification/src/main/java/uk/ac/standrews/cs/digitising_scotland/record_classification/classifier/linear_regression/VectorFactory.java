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
package uk.ac.standrews.cs.digitising_scotland.record_classification.classifier.linear_regression;

import old.record_classification_old.machinelearning.tokenizing.StandardTokenizerIterable;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import uk.ac.standrews.cs.digitising_scotland.record_classification.model.Bucket;
import uk.ac.standrews.cs.digitising_scotland.record_classification.model.Classification;
import uk.ac.standrews.cs.digitising_scotland.record_classification.model.Record;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Factory that allows us to create vectors from strings.
 * Created by fraserdunlop on 23/04/2014 at 19:34.
 */
public class VectorFactory implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 5369887941319861994L;

    /**
     * The index.
     */
    private CodeIndexer index;

    /**
     * The vector encoder.
     */
    private SimpleVectorEncoder vectorEncoder;

    /**
     * Constructs an empty {@link VectorFactory} with number of features set to 0 and a new vectorEncoder.
     */
    public VectorFactory() {

        index = new CodeIndexer();
        vectorEncoder = new SimpleVectorEncoder();
    }

    /**
     * Constructs a new {@link VectorFactory} from the specified {@link Bucket}.
     *
     * @param bucket bucket
     * @param index  the index
     */
    public VectorFactory(final Bucket bucket, final CodeIndexer index) {

        this.index = index;
        vectorEncoder = new SimpleVectorEncoder();
        updateDictionary(bucket);
    }

    /**
     * Updates the dictionary with the tokens for all the records in the given bucket.
     *
     * @param bucket the bucket
     */
    public void updateDictionary(final Bucket bucket) {

        for (Record record : bucket) {
                updateDictionary(record.getClassification().getTokenSet().toString());
        }
        setNumFeatures();
    }

    /**
     * Sets the num features.
     */
    private void setNumFeatures() {

        int numFeatures = vectorEncoder.getDictionarySize();
        MachineLearningConfiguration.getDefaultProperties().setProperty("numFeatures", String.valueOf(numFeatures));
    }

    /**
     * Creates a {@link NamedVector} from the cleaned description of a {@link Record}.
     * If a gold standard coding exists this will be used for the name of the vector.
     * If no gold standard then "noGoldStandard" will be used.
     * A List<NamedVector> is returned as the record may have more than one line in the original description.
     *
     * @param record Record to generate vector for.
     * @return List<NamedVector> List of {@link NamedVector} for this record
     */
    public List<NamedVector> generateVectorsFromRecord(final Record record) {

        List<NamedVector> vectors = new ArrayList<>();

        if (record.getClassification() != Classification.UNCLASSIFIED) {
            vectors.addAll(createNamedVectorsWithGoldStandardCodes(record));
        } else {
            // TODO not sure correct - used to be record.getDescription()
            vectors.addAll(createUnNamedVectorsFromDescription(record.getData()));
        }
        return vectors;
    }

    /**
     * Creates a new Vector object.
     *
     * @param description the description
     * @return the collection<? extends named vector>
     */
    private Collection<? extends NamedVector> createUnNamedVectorsFromDescription(final String description) {

        List<NamedVector> vectorList = new ArrayList<>();

        Vector v = createVectorFromString(description);
        vectorList.add(new NamedVector(v, "noGoldStandard"));

        return vectorList;
    }

    /**
     * Creates a new Vector object.
     *
     * @param record the record
     * @return the list< named vector>
     */
    private List<NamedVector> createNamedVectorsWithGoldStandardCodes(final Record record) {

        List<NamedVector> vectors = new ArrayList<>();

        Classification codeTriple = record.getClassification();
            Integer id = index.getID(codeTriple.getCode());
            vectors.add(createNamedVectorFromString(codeTriple.getTokenSet().toString(), id.toString()));


        return vectors;
    }

    /**
     * Creates a vector from a string using {@link SimpleVectorEncoder}
     * to encode the tokens and StandardTokenizerIterable to
     * tokenize the string.
     *
     * @param description the string to vectorize
     * @return a vector encoding of the string
     */
    public Vector createVectorFromString(final String description) {

        Vector vector = new RandomAccessSparseVector(getNumberOfFeatures());
        addFeaturesToVector(vector, description);
        return vector;
    }

    /**
     * Creates a named vector from a string using {@link SimpleVectorEncoder}
     * to encode the tokens and {StandardTokenizerIterable} to
     * tokenize the string.
     *
     * @param description the string to vectorize
     * @param name        name
     * @return a vector encoding of the string
     */
    public NamedVector createNamedVectorFromString(final String description, final String name) {

        Vector vector = createVectorFromString(description);
        return new NamedVector(vector, name);
    }

    /**
     * Adds the features to vector.
     *
     * @param vector      the vector
     * @param description the description
     */
    private void addFeaturesToVector(final Vector vector, final String description) {

        StandardTokenizerIterable tokenStream = new StandardTokenizerIterable(Version.LUCENE_36, new StringReader(description));
        for (CharTermAttribute attribute : tokenStream) {
            vectorEncoder.addToVector(attribute.toString(), vector);
        }
    }

    /**
     * Adds all the tokens in the specified string to this {@link VectorFactory}'s dictionary.
     *
     * @param description String to add
     */
    public void updateDictionary(final String description) {

        String descriptionLower = description.toLowerCase();
        StandardTokenizerIterable tokenStream = new StandardTokenizerIterable(Version.LUCENE_36, new StringReader(descriptionLower));
        for (CharTermAttribute attribute : tokenStream) {
            vectorEncoder.updateDictionary(attribute.toString());
        }
        setNumFeatures();
    }

    /**
     * Gets the code indexer that was used to construct this vector factory.
     *
     * @return the code indexer
     */
    public CodeIndexer getCodeIndexer() {

        return index;
    }

    /**
     * Gets the number of features.
     *
     * @return the number of features
     */
    public int getNumberOfFeatures() {

        return vectorEncoder.getDictionarySize();
    }
}
