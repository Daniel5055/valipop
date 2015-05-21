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
package uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.bucket;

import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.records.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BucketFilter {

    /**
     * Returns a bucket containing only the unique records from the original bucket.
     *
     * @param bucket the bucket we want to filter
     * @return Bucket a new Bucket with only the unique records
     */
    public static Bucket uniqueRecordsOnly(final Bucket bucket) {

        Map<String, Record> map = new HashMap<>();

        for (Record record : bucket) {
            if (!map.containsKey(record.getDescription())) {
                map.put(record.getDescription(), record);
            }
        }

        List<Record> recordList = new ArrayList<>();

        for (Record record : map.values()) {
            recordList.add(record);
        }

        return new Bucket(recordList);
    }
}
