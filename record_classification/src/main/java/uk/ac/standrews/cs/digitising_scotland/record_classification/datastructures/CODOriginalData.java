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
package uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures;

import uk.ac.standrews.cs.digitising_scotland.record_classification.exceptions.InputFormatException;

/**
 * Represents cause of death data from from NRS. As data is supplied this cannot be changed once set.
 * @author jkc25
 *
 */
public class CODOriginalData extends OriginalData {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 148704864605209743L;

    /** The age group. */
    private int ageGroup;

    /** The sex. */
    private int sex;

    /**
     * Describes original data from NRS.
     * @param description raw description as transcribed
     * @param year year from original record
     * @param ageGroup age group from record
     * @param sex sex indicator. 1 for male, 0 for female
     * @param imageQuality 0 or 1. 1 if bad, 0 is OK
     * @param fileName name of file containing this original data
     * @throws InputFormatException if one or more of the inputs are null
     */
    public CODOriginalData(final String description, final int year, final int ageGroup, final int sex, final int imageQuality, final String fileName) throws InputFormatException {

        super(description, year, imageQuality, fileName);
        if (ageGroup < 0 || ageGroup > 5) { throw new NumberFormatException("age group must be between 0 and 5"); }
        this.ageGroup = ageGroup;
        if (sex < 0 || sex > 1) { throw new NumberFormatException(sex + " read for sex.\nsex must be 1 or 0. 1 is male, 0 is female"); }
        this.sex = sex;
    }

    /**
     * Gets the age group.
     * @return age group
     */
    public int getAgeGroup() {

        return ageGroup;
    }

    /**
     * Returns the sex of the person the record refers to.
     * 1 is male, 0 is female.
     * @return sex, integer represent the sex
     */
    public int getSex() {

        return sex;
    }

    /* (non-Javadoc)
     * @see uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.OriginalData#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ageGroup;
        result = prime * result + sex;
        return result;
    }

    /* (non-Javadoc)
     * @see uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.OriginalData#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {

        if (this == obj) { return true; }
        if (!super.equals(obj)) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        CODOriginalData other = (CODOriginalData) obj;
        if (ageGroup != other.ageGroup) { return false; }
        if (sex != other.sex) { return false; }
        return true;
    }

}
