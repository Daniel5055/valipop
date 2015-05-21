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
package uk.ac.standrews.cs.digitising_scotland.record_classification.data_readers;

import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.code.CodeDictionary;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.code.CodeNotValidException;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.records.Record;
import uk.ac.standrews.cs.digitising_scotland.record_classification.exceptions.InputFormatException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class AbstractFormatConverter {

    public abstract List<Record> convert(final File inputFile, CodeDictionary codeDictionary) throws IOException, InputFormatException, CodeNotValidException;

    /**
     * Check line length, for modern cod data it should be 38.
     *
     * @param lineSplit the line split
     */
    protected static void checkLineLength(final String[] lineSplit, final int codeLineLength) {

        if (lineSplit.length != codeLineLength) {
            System.err.println("Line is wrong length, should be" + codeLineLength + ", is " + lineSplit.length);
        }
    }

    /**
     * Converts a string representation of an age group to the format needed by NRS.
     *
     * @param lineSplit the line split
     * @return the int
     */
    protected static int convertAgeGroup(final String lineSplit) {

        //     * TODO make sure this is the correct format

        int group = Integer.parseInt(lineSplit);
        final int max_age_group = 5;
        if (group > max_age_group) { return max_age_group; }

        return group;
    }

    /**
     * Converts sex from M or F characters to 1 or 0. 1 is male, 0 is female.
     *
     * @param sexIndicator the string to convert to binary, 1 (male) or 0 (female)
     * @return the int associated with the sex
     */
    protected static int convertSex(final String sexIndicator) {

        if (sexIndicator.equals("M")) { return 1; }
        return 0;
    }

    /**
     * Removes quotes from a string.
     *
     * @param string the string to remove quotes from
     * @return the string with quotes removed
     */
    protected static String removeQuotes(final String string) {

        String noQuotes = string.replaceAll("\"", "").trim();

        return noQuotes;
    }

    /**
     * Concatenates strings  between the start and end points of an array with a ',' delimiter.
     *
     * @param stringArray   the String array with consecutive strings to concatenate
     * @param startPosition the first index to concatenate
     * @param endPosition   the last index to concatenate
     * @return the concatenated string, comma separated
     */
    protected static String formDescription(final String[] stringArray, final int startPosition, final int endPosition) {

        String description = "";

        for (int currentPosition = startPosition; currentPosition <= endPosition; currentPosition++) {
            if (stringArray[currentPosition].length() != 0 && !stringArray[currentPosition].equalsIgnoreCase("null")) {
                description += stringArray[currentPosition].toLowerCase() + ",";
            }
        }

        return description;
    }
}
