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
package uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.records;

import com.google.common.collect.HashMultimap;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.OriginalData;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.classification.Classification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Record {

    private final int id;
    private OriginalData originalData;

    /** The code triples. */
    private HashMultimap<String, Classification> listOfClassifications;

    /**
     * Instantiates a new record.
     * @param id the unique id of this record
     * @param originalData the original data from the initial record.
     */
    public Record(final int id, final OriginalData originalData) {

        this.id = id;
        this.originalData = originalData;
        listOfClassifications = HashMultimap.create();

    }

    public boolean isFullyClassified() {

        for (String description : originalData.getDescription())
            if (!descriptionIsClassified(description)) { return false; }
        return true;
    }

    public boolean descriptionIsClassified(final String description) {

        return listOfClassifications.containsKey(description);
    }

    public void addClassificationsToDescription(final String description, final Set<Classification> classifications) {

        for (Classification codeTriple : classifications) {
            addClassification(description, codeTriple);
        }
    }

    /**
     * Copy the current records original attributes.
     * @param source
     */
    public static Record copyOfOriginalRecord(final Record source) {

        return new Record(source.id, source.originalData);
    }

    /**
     * Gets the original data. Original data is the data supplied on records.
     *
     * @return the original data
     */
    public OriginalData getOriginalData() {

        return originalData;
    }

    /**
     * Gets the description from the record's original data object.
     *
     * @return the cleaned description
     */
    public List<String> getDescription() {

        return originalData.getDescription();
    }

    /**
     * Updates a specific line of the description to a new value.
     * @param oldDescription line to update
     * @param newDescription new value
     * @return true if replacement successful
     */
    public boolean updateDescription(final String oldDescription, final String newDescription) {

        int index = originalData.getDescription().indexOf(oldDescription);
        if (index != -1) {
            originalData.getDescription().set(index, newDescription);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Gets the unique ID of the record.
     *
     * @return unique ID
     */
    public int getid() {

        return id;
    }

    /**
     * Returns the gold standard set of {@link Classification} for this Record.
     * If no gold standard set exists then an empty {@link Classification} will be returned.
     *
     * @return the gold standard classification set
     */
    public Set<Classification> getGoldStandardClassificationSet() {

        return originalData.getGoldStandardClassifications();
    }

    /**
     * Returns true is this record is of the subType CoDRecord.
     * @return true if cause of death record
     */
    public boolean isCoDRecord() {

        String thisClassName = originalData.getClass().getName();
        String[] split = thisClassName.split("\\.");
        if (split[split.length - 1].equals("CODOrignalData")) { return true;

        }
        return false;
    }

    /**
     * Adds a {@link Classification} to the list of classifications that this record has. The classification's tokenSet is used as the key.
     * @param classification to add.
     * @return true if the method increased the size of the multimap, or false if the multimap already contained the key-value pair
     */
    public boolean addClassification(final Classification classification) {

        return listOfClassifications.put(classification.getTokenSet().toString(), classification);
    }

    /**
     * Adds a {@link Classification} to the list of classifications that this record has. The description given is used as the key.
     * @param classification to add.
     * @return true if the method increased the size of the multimap, or false if the multimap already contained the key-value pair
     */
    public boolean addClassification(final String description, final Classification classification) {

        return listOfClassifications.put(description, classification);
    }

    /**
     * Gets the Set of {@link Classification}s contained in this record.
     *
     * @return the Set of CodeTriples.
     */
    public Set<Classification> getClassifications() {

        return new HashSet<>(listOfClassifications.values());
    }

    public HashMultimap<String, Classification> getListOfClassifications() {

        return listOfClassifications;
    }

    public void setListOfClassifications(final HashMultimap<String, Classification> listOfClassifications) {

        this.listOfClassifications = listOfClassifications;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((listOfClassifications == null) ? 0 : listOfClassifications.hashCode());
        result = prime * result + ((originalData == null) ? 0 : originalData.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        Record other = (Record) obj;
        if (id != other.id) { return false; }
        if (listOfClassifications == null) {
            if (other.listOfClassifications != null) { return false; }
        }
        else if (!listOfClassifications.equals(other.listOfClassifications)) { return false; }
        if (originalData == null) {
            if (other.originalData != null) { return false; }
        }
        else if (!originalData.equals(other.originalData)) { return false; }
        return true;
    }

    @Override
    public String toString() {

        return "Record [id=" + id + ", originalData=" + originalData + ", listOfClassifications=" + listOfClassifications + "]";
    }

}
