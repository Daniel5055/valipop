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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing Closest Match Map with the simple String Length Similarity Metric.
 * We make a map of of strings to strings where values= their keys. We randomly
 * generate strings and get the closest match then check that there are no other
 * elements of the key set with greater similarity than the one chosen by ClosestMatchMap.
 * Created by fraserdunlop on 01/10/2014 at 15:31.
 */
public class ClosestMatchMapTest {

    Map<String, String> map;
    private StringLengthSimilarityMetric stringLengthSimilarityMetric = new StringLengthSimilarityMetric();
    private int randomStringLengthUpperBound;
    private int numTests;

    @Before
    public void setUp() {

        numTests = 50;
        randomStringLengthUpperBound = 50;
        Map<String, String> map = new HashMap<>();
        String[] strings = new String[]{"apple", "banana", "bonobo", "fricassee", "fermentation vessel", "morose wanderer", "challenged to a fight in the danger zone", "lanaaaaaaaaaaaaa"};
        for (String string : strings) {
            map.put(string, string);
        }
        this.map = map;
    }

    @Test
    public void test() {

        ClosestMatchMap<String, String> closestMatchMap = new ClosestMatchMap<>(stringLengthSimilarityMetric, map);
        for (int i = 0; i < numTests; i++)
            assertRandomStringGetsClosestMatch(closestMatchMap);

    }

    private void assertRandomStringGetsClosestMatch(ClosestMatchMap<String, String> closestMatchMap) {

        String randomString = generateRandomString();
        String match = closestMatchMap.getClosestMatch(randomString);
        Set<String> keySet = closestMatchMap.keySet();
        for (String key : keySet) {
            Assert.assertTrue(stringLengthSimilarityMetric.getSimilarity(randomString, match) >= stringLengthSimilarityMetric.getSimilarity(randomString, key));
        }
    }

    private String generateRandomString() {

        return randomString(randomStringLengthUpperBound);
    }

    private String randomString(int lengthUpperBound) {

        Random rnd = new Random();
        int len = rnd.nextInt(lengthUpperBound);
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

}
