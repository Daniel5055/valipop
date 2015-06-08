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
package old.record_classification_old.classifiers.closestmatchmap;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Similaritor is a factory which creates Comparators
 * which compare the similarity of objects to a reference object k.
 * If the metric supplied at construction larger values for more similar
 * objects then the comparator returned will sort a list so that the most
 * similar object is at the head of the list.
 * Created by fraserdunlop on 01/10/2014 at 16:23.
 */
public class Similaritor<K> {

    private static final transient Logger LOGGER = LoggerFactory.getLogger(Similaritor.class);

    private final SimilarityMetric<K> metric;

    public Similaritor(final SimilarityMetric<K> metric) {

        this.metric = metric;
    }

    public double getSimilarity(final K o1, final K o2) {

        Double sim = metric.getSimilarity(o1, o2);
        if (sim.isNaN()) {
            if (o1.equals(o2)) { return 1.0; }
            //   LOGGER.debug("NaN returned by similarity metric :- o1: " + o1 + " o2: " + o2);
            return 0;
        }
        return sim;

    }

    /**
     * @param k reference object.
     * @return A Comparator for objects of type K.
     */
    public Comparator<K> getComparator(final K k) {

        return new MetricComparator(k);
    }

    public class MetricComparator implements Comparator<K> {

        private K k;

        public MetricComparator(final K k) {

            this.k = k;
        }

        @Override
        public int compare(final K o1, final K o2) {

            double o1Score = metric.getSimilarity(o1, k);
            double o2Score = metric.getSimilarity(o2, k);

            if ((Double) o1Score == null || (Double) o2Score == null) { throw new IllegalArgumentException("o1 or o2 score is null. There may be a problem with your similarity metric."); }

            if (o1Score < o2Score) {
                return 1;
            }
            else if (o1Score > o2Score) {
                return -1;
            }
            else {
                return 0;
            }

        }
    }

}
