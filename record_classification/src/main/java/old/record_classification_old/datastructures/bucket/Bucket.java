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
package old.record_classification_old.datastructures.bucket;

import old.record_classification_old.data_readers.AbstractFormatConverter;
import old.record_classification_old.data_readers.LongFormatConverter;
import old.record_classification_old.datastructures.code.CodeDictionary;
import old.record_classification_old.datastructures.code.CodeNotValidException;
import old.record_classification_old.datastructures.records.Record;
import old.record_classification_old.datastructures.records.RecordFactory;
import old.record_classification_old.exceptions.InputFormatException;
import old.record_classification_old.pipeline.PipelineUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A Bucket is an iterable collection of {@link Record} objects. Buckets can be manipulated using the {@link BucketUtils}
 * and {@link BucketFilter} classes. Methods for checking if a bucket contains a given record, checking it's size etc are provided.
 *
 * @author jkc25, frjd2
 */
public class Bucket implements Iterable<Record> {

    // TODO replace with List<Record>?
    private Map<Integer, Record> records;

    /**
     * Instantiates a new empty bucket.
     */
    public Bucket() {

        records = new LinkedHashMap<>();
    }

    /**
     * Instantiates a new bucket from a list of {@link Record}s.
     *
     * @param listOfRecords the list of {@link Record}s
     */
    public Bucket(final List<Record> listOfRecords) {

        this();

        for (Record record : listOfRecords) {
            addRecordToBucket(record);
        }
    }

    /**
     * Generates a bucket of training records (with gold standard codes) from the given training file.
     * The file should be either in the short NRS format or in the format the matches the {@link AbstractFormatConverter}
     * specified in the class. Set to {@link LongFormatConverter} as  default.
     *
     * @param trainingFile the training file to generate the records and train the models from
     * @return the bucket that will be populated
     * @throws IOException           Signals that an I/O exception has occurred.
     * @throws InputFormatException  the input format exception
     * @throws CodeNotValidException
     */
    public Bucket(final File trainingFile, final CodeDictionary codeDictionary) throws IOException, InputFormatException, CodeNotValidException {

        this();

        boolean training_file_is_in_long_format = PipelineUtils.checkFileType(trainingFile);

        if (training_file_is_in_long_format) {
            addCollectionOfRecords(new LongFormatConverter().convert(trainingFile, codeDictionary));
        } else {
            addCollectionOfRecords(RecordFactory.getRecordsFromFile(trainingFile, codeDictionary));
        }
    }

    /**
     * Adds a single {@link Record} to a bucket.
     *
     * @param recordToInsert the record to insert.
     */
    public void addRecordToBucket(final Record recordToInsert) {

        records.put(recordToInsert.getid(), recordToInsert);
    }

    /**
     * Adds each {@link Record} to the Bucket's collection of records.
     */
    public void addCollectionOfRecords(final Iterable<Record> records) {

        for (Record record : records) {
            addRecordToBucket(record);
        }
    }

    /**
     * Checks if the specified record is in this bucket. Will return true if and only if the record is a member of the bucket.
     *
     * @param record record to check for membership.
     * @return true if this record is a member, false otherwise
     */
    public boolean contains(final Record record) {

        return records.containsValue(record);
    }

    /**
     * Removes the record from this bucket if it is present (optional operation).
     * More formally, if this map contains a mapping from key k to value v such that (key==null ? k==null : key.equals(k)), that mapping is removed.
     * (The map can contain at most one such mapping.)
     * <p/>
     * Returns the value to which this map previously associated the key, or null if the map contained no mapping for the key.
     *
     * @param record Record to remove
     * @return the previous record associated with the record, or null if there was no record for the record.
     */
    public Record remove(final Record record) {

        return records.remove(record.getid());
    }

    /**
     * Returns the number of {@link Record}s in the bucket.
     *
     * @return the number of records in the bucket
     */
    public int size() {

        return records.size();
    }

    /**
     * Checks if a bucket is empty or not.
     *
     * @return true is there are no records in the bucket, false if not.
     */
    public boolean isEmpty() {

        if (records.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Iterator A {@link Record} itereator that allows iteration though all the records in
     * the bucket.
     *
     * @return iterator of type Iterator<Record>.
     */
    @Override
    public Iterator<Record> iterator() {

        return records.values().iterator();
    }

    /**
     * Returns a single record that matches the String UID supplied.
     *
     * @param uid hashCode of the vector we are looking for
     * @return Record with matching hashCode
     */
    public Record getRecord(final int uid) {

        return records.get(uid);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "Bucket [records=" + records + "]";
    }

}
