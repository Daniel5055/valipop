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
package uk.ac.standrews.cs.digitising_scotland.record_classification.tools.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.standrews.cs.digitising_scotland.record_classification.tools.ReaderWriterFactory;
import uk.ac.standrews.cs.digitising_scotland.record_classification.tools.Utils;

/**
 * Ad hoc class to read old output files and calculate the number of unique records and the corresponding accuracy values.
 * @author jkc25
 *
 */
public class HistoricOutputUniqueRecordAnalyser {

    private static Logger LOGGER = LoggerFactory.getLogger(HistoricOutputUniqueRecordAnalyser.class);
    private static int descriptionPos;
    private static int correctPos;
    private static int nbPos;
    private static int sgdPos;
    private static int stringSimPos;

    public static void main(final String[] args) throws IOException {

        File input = new File(args[0]);
        if (!input.exists()) {
            LOGGER.error("File (" + input.getAbsolutePath() + ")does not exsist");
        }

        init(args);

        if (input.isDirectory()) {
            runOnAllFiles(input);
        }
        else {
            HistoricOutputUniqueRecordAnalyser instance = new HistoricOutputUniqueRecordAnalyser();
            instance.run(input);
        }

    }

    private static void init(final String[] args) {

        descriptionPos = Integer.parseInt(args[1]);
        correctPos = Integer.parseInt(args[2]);
        nbPos = Integer.parseInt(args[3]);
        sgdPos = Integer.parseInt(args[4]);
        stringSimPos = Integer.parseInt(args[5]);
    }

    private static void runOnAllFiles(final File input) throws IOException {

        HistoricOutputUniqueRecordAnalyser instance = new HistoricOutputUniqueRecordAnalyser();
        File[] files = input.listFiles();
        for (File file : files) {
            instance.run(file);
        }
    }

    public void run(final File input) throws IOException {

        Map<String, String> uniqueMap = new HashMap<>();
        uniqueMap = populateMap(input);
        System.out.println(uniqueMap.size());
        processMap(uniqueMap);
    }

    private void processMap(final Map<String, String> uniqueMap) {

        double total = uniqueMap.size();
        double nbCorrect = 0;
        double sgdCorrect = 0;
        double stringCorrect = 0;
        String nb = "";
        String sgd = "";
        String sim = "";

        Iterator<Entry<String, String>> it = uniqueMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> next = it.next();
            String[] values = next.getValue().split(Utils.getCSVComma());
            String correct = values[correctPos].trim().toLowerCase();
            nb = getValue(values, nbPos);
            sgd = getValue(values, sgdPos);
            sim = getValue(values, stringSimPos);

            if (!nb.equals("") && correct.equals(nb)) {
                nbCorrect++;
            }
            if (!sgd.equals("") && correct.equals(sgd)) {
                sgdCorrect++;
            }
            if (!sim.equals("") && correct.equals(sim)) {
                stringCorrect++;
            }

        }
        LOGGER.info("Total unique records: " + total);
        LOGGER.info("Total nb correct: " + nbCorrect + " (" + nbCorrect / total * 100 + ")");
        LOGGER.info("Total sgd correct: " + sgdCorrect + " (" + sgdCorrect / total * 100 + ")");
        LOGGER.info("Total string sim correct: " + stringCorrect + " (" + stringCorrect / total * 100 + ")");

    }

    private String getValue(final String[] values, final int pos) {

        String string = "";
        if (pos != -1) {
            string = values[pos].trim().toLowerCase();
        }
        return string;
    }

    private Map<String, String> populateMap(final File input) throws IOException {

        BufferedReader br = ReaderWriterFactory.createBufferedReader(input);
        String line = "";
        Map<String, String> uniqueMap = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] lineSplit = line.split(Utils.getCSVComma());
            String description = lineSplit[descriptionPos];
            uniqueMap.put(description, line);
        }
        return uniqueMap;
    }
}
