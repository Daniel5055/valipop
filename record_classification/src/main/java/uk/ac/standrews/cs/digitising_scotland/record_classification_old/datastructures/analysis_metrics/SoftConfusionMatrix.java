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
package uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.analysis_metrics;

import java.util.Set;

import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.bucket.Bucket;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.classification.Classification;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.code.Code;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.vectors.CodeIndexer;

/**
 * This confusion matrix counts predictions as correct if they are either exactly
 * correct or an ancestor of the correct code (a more general code that is in the
 * same branch of the hierarchy).
 * Created by fraserdunlop on 02/07/2014 at 10:59.
 */
public class SoftConfusionMatrix extends AbstractConfusionMatrix {

    /**
     * Instantiates a new soft confusion matrix.
     *
     * @param bucket the bucket
     */
    public SoftConfusionMatrix(final Bucket bucket, final CodeIndexer index) {

        super(bucket, index);
    }

    /**
     * True positive and false negative.
     *
     * @param setCodeTriples the set code triples
     * @param goldStandardTriples the gold standard triples
     */
    protected void truePosAndFalseNeg(final Set<Classification> setCodeTriples, final Set<Classification> goldStandardTriples) {

        for (Classification goldStanardCode : goldStandardTriples) {
            final Code code = goldStanardCode.getCode();
            int codeID = index.getID(code);

            if (containsOrHasAncestors(code, setCodeTriples)) {
                truePositive[codeID]++;
            }
            else {
                falseNegative[codeID]++;
            }
        }

    }

    /**
     * Total and false pos.
     *
     * @param setCodeTriples the set code triples
     * @param goldStandardTriples the gold standard triples
     */
    protected void totalAndFalsePos(final Set<Classification> setCodeTriples, final Set<Classification> goldStandardTriples) {

        for (Classification predictedCode : setCodeTriples) {
            final Code code = predictedCode.getCode();
            int codeID = index.getID(code);
            totalPredictions[codeID]++;
            if (!containsOrHasDescendants(code, goldStandardTriples)) {
                falsePositive[codeID]++;
            }
        }
    }

    /**
     * Returns true is a code is in the specified set of CodeTriples or is an ancestor
     * of one of the codes in the specified set of CodeTriples.
     * @param code the code
     * @param setCodeTriples the set code triples
     * @return true, if successful
     */
    private boolean containsOrHasDescendants(final Code code, final Set<Classification> setCodeTriples) {

        for (Classification codeTriple : setCodeTriples) {
            if (codeTriple.getCode() == code || codeTriple.getCode().isDescendant(code)) { return true; }
        }
        return false;
    }

    /**
     * Returns true is a code is in the specified set of CodeTriples or is a descendant
     * of one of the codes in the specified set of CodeTriples.
     * @param code code to check for
     * @param setCodeTriples set to check in
     * @return true if present
     */
    public boolean containsOrHasAncestors(final Code code, final Set<Classification> setCodeTriples) {

        for (Classification codeTriple : setCodeTriples) {
            if (codeTriple.getCode() == code || codeTriple.getCode().isAncestor(code)) { return true; }
        }
        return false;
    }

}
