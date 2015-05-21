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
package uk.ac.standrews.cs.digitising_scotland.record_classification.machinelearning.hmm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.standrews.cs.digitising_scotland.record_classification.tools.Utils;

/**
 * The Class HMMPreparser prepares a data file for use by the {@link HiddenMarkovModel} builder.
 */
public class HMMPreparser {

    private static final Logger LOGGER = LoggerFactory.getLogger(HMMPreparser.class);

    /**
     * Prepare file.
     *
     * @param input the input
     * @param output the output
     * @return the file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public File prepareFile(final File input, final File output) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));
        String line = "";

        while ((line = reader.readLine()) != null) {
            String preparedLine = seperatePuncutation(line);
            preparedLine = tokenise(preparedLine);
            Utils.writeToFile(preparedLine, output.getAbsolutePath(), true);
        }
        reader.close();
        return output;

    }

    /**
     * Split into multipule lines.
     *
     * @param preparedLine the prepared line
     * @return the string
     */
    protected String splitIntoMultipuleLines(final ArrayList<String> preparedLine) {

        StringBuilder sb = new StringBuilder();
        for (String string : preparedLine) {
            sb.append(string + "\n");
        }
        sb.append("\n");

        return sb.toString();
    }

    /**
     * Tokenise.
     *
     * @param preparedLine the prepared line
     * @return the string
     */
    protected String tokenise(final String preparedLine) {

        StringTokenizer st = new StringTokenizer(preparedLine);
        ArrayList<String> tokenisedLine = new ArrayList<String>();
        LOGGER.info("---- Split by space ------");
        while (st.hasMoreElements()) {
            tokenisedLine.add(st.nextElement().toString());
        }

        return splitIntoMultipuleLines(tokenisedLine);
    }

    /**
     * Seperate puncutation.
     *
     * @param line the line
     * @return the string
     */
    protected String seperatePuncutation(final String line) {

        LOGGER.info(line);
        String newLine = removeQuotes(line);
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile("[;,&12/]").matcher(newLine);

        while (m.find()) {
            allMatches.add(m.group());
        }

        for (int i = 0; i < allMatches.size(); i++) {
            newLine = newLine.replaceAll(allMatches.get(i), " " + allMatches.get(i));
        }

        LOGGER.info(newLine);

        return newLine.trim();
    }

    /**
     * Removes the quotes.
     *
     * @param line the line
     * @return the string
     */
    protected String removeQuotes(final String line) {

        return line.replaceAll("\"", "");
    }
}
