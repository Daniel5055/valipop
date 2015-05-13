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
package uk.ac.standrews.cs.digitising_scotland.record_classification.data_readers;

import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.CODOriginalData;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.classification.Classification;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.code.CodeDictionary;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.records.Record;
import uk.ac.standrews.cs.digitising_scotland.record_classification.exceptions.InputFormatException;
import uk.ac.standrews.cs.digitising_scotland.record_classification.tools.ReaderWriterFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * The Class FormatConverter converts a comma separated text file in the format that is used by the modern cod data
 * to a list of Record objects.
 *
 * @author jkc25
 */
public final class PilotDataFormatConverter extends AbstractFormatConverter {

    private static final String CHARSET_NAME = "UTF8";

    static final int CODLINELENGTH = 8;

    private static final int ID_POSITION = 1;

    private static final int AGE_POSITION = 3;

    private static final int SEX_POSITION = 2;

    private static final int DESC_START = 4;

    private static final int DESC_END = 6;

    private static final int YEAR_POSITION = 0;

    private static final int IMAGE_QUALITY_POS = 7;

    /**
     * Converts the data in the inputFile (one record per line, comma separated) into {@link Record}s.
     *
     * @param inputFile the input file to be read
     * @return the list of records
     * @throws IOException          Signals that an I/O exception has occurred.
     * @throws InputFormatException the input format exception
     */
    public List<Record> convert(final File inputFile, final CodeDictionary codeDictionary) throws IOException, InputFormatException {

        BufferedReader br = ReaderWriterFactory.createBufferedReader(inputFile);

        String line = "";
        List<Record> recordList = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] lineSplit = line.split("\t");

            checkLineLength(lineSplit, CODLINELENGTH);

            int id = Integer.parseInt(lineSplit[ID_POSITION]);
            int imageQuality = parseImageQuality(lineSplit);
            int ageGroup = convertAgeGroup(removeQuotes(lineSplit[AGE_POSITION]));
            int sex = convertSex(removeQuotes(lineSplit[SEX_POSITION]));
            String description = formDescription(lineSplit, DESC_START, DESC_END);
            int year = Integer.parseInt(removeQuotes(lineSplit[YEAR_POSITION]));

            CODOriginalData originalData = new CODOriginalData(description, year, ageGroup, sex, imageQuality, inputFile.getName());
            HashSet<Classification> goldStandard = new HashSet<>();

            Record r = new Record(id, originalData);
            r.getOriginalData().setGoldStandardClassification(goldStandard);

            recordList.add(r);
        }

        br.close();
        return recordList;
    }

    private static int parseImageQuality(final String[] lineSplit) {

        if (lineSplit[IMAGE_QUALITY_POS].equalsIgnoreCase("null")) {
            return 0;
        } else {
            return Integer.parseInt(lineSplit[IMAGE_QUALITY_POS]);
        }
    }


}
