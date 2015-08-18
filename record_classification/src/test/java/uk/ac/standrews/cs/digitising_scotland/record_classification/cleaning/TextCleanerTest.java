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
package uk.ac.standrews.cs.digitising_scotland.record_classification.cleaning;

import org.junit.Test;
import uk.ac.standrews.cs.digitising_scotland.record_classification.model.Bucket;
import uk.ac.standrews.cs.digitising_scotland.record_classification.model.Classification;
import uk.ac.standrews.cs.digitising_scotland.record_classification.model.Record;
import uk.ac.standrews.cs.digitising_scotland.record_classification.model.TokenList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Utility class used to test cleaners based on {@link TokenFilterCleaner}.
 *
 * @author Masih Hajiarab Derkani
 */
public abstract class TextCleanerTest {

    private final TextCleaner cleaner;
    private final Map<String, String> test_values;

    public TextCleanerTest(final TextCleaner cleaner, Map<String, String> test_values) {

        this.cleaner = cleaner;
        this.test_values = test_values;
    }

    @Test
    public void cleanBucket() throws Exception {

        final Bucket test_bucket = getTestBucket();
        final Bucket expected_cleaned_bucket = getExpectedCleanedBucket();

        final Bucket actual_cleaned_bucket = cleaner.apply(Arrays.asList(test_bucket)).get(0);

        assertEquals(expected_cleaned_bucket, actual_cleaned_bucket);
    }

    @Test
    public void cleanClassification() throws Exception {

        assertEquals(Classification.UNCLASSIFIED, cleaner.cleanClassification("", Classification.UNCLASSIFIED));
    }

    private Classification makeClassification(String data) {

        return new Classification("code", new TokenList(data), 0.1, null);
    }

    private Bucket getTestBucket() {

        return getBucket(test_values.keySet());
    }

    private Bucket getExpectedCleanedBucket() {

        return getBucket(test_values.values());
    }

    private Bucket getBucket(Collection<String> data) {

        final Bucket expected_cleaned_bucket = new Bucket();

        int record_id = 1;
        for (String s : data) {
            expected_cleaned_bucket.add(new Record(record_id++, s, makeClassification(s)));
        }
        return expected_cleaned_bucket;
    }
}
