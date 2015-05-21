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
/*
 * | ______________________________________________________________________________________________ | Understanding
 * Scotland's People (USP) project. | | The aim of the project is to produce a linked pedigree for all publicly | |
 * available Scottish birth/death/marriage records from 1855 to the present day. | | | | Digitization of the records is
 * being carried out by the ESRC-funded Digitising | | Scotland project, run by University of St Andrews and National
 * Records of Scotland. | | | | The project is led by Chris Dibben at the Longitudinal Studies Centre at St Andrews. | |
 * The other project members are Lee Williamson (also at the Longitudinal Studies Centre) | | Graham Kirby, Alan Dearle
 * and Jamie Carson at the School of Computer Science at St Andrews; | | and Eilidh Garret and Alice Reid at the
 * Department of Geography at Cambridge. | | | |
 * ______________________________________________________________________________________________
 */
package uk.ac.standrews.cs.digitising_scotland.record_classification.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SpellingCorrecter.
 */
class SpellingCorrecter {

    // http://raelcunha.com/spell-correct.php
    // dictionary
    private final HashMap<String, Integer> dictionary = new HashMap<String, Integer>();

    /**
     * Instantiates a new spelling correcter.
     *
     * @param file the file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SpellingCorrecter(final String file) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        Pattern p = Pattern.compile("\\w+");
        for (String temp = ""; temp != null; temp = in.readLine()) {
            Matcher m = p.matcher(temp.toLowerCase());
            while (m.find()) {
                dictionary.put((temp = m.group()), dictionary.containsKey(temp) ? dictionary.get(temp) + 1 : 1);
            }
        }
        in.close();
    }

    private List<String> edits(final String word) {

        List<String> result = new ArrayList<String>();
        for (int i = 0; i < word.length(); ++i) {
            result.add(word.substring(0, i) + word.substring(i + 1));
        }
        for (int i = 0; i < word.length() - 1; ++i) {
            result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + word.substring(i, i + 1) + word.substring(i + 2));
        }
        for (int i = 0; i < word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i + 1));
            }
        }
        for (int i = 0; i <= word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
            }
        }
        return result;
    }

    /**
     * Correct.
     *
     * @param word the word
     * @return the string
     */
    public String correct(final String word) {

        if (dictionary.containsKey(word)) { return word; }
        List<String> list = edits(word);
        Map<Integer, String> candidates = new HashMap<Integer, String>();
        for (String s : list) {
            if (dictionary.containsKey(s)) {
                candidates.put(dictionary.get(s), s);
            }
        }

        if (candidates.size() > 0) { return candidates.get(Collections.max(candidates.keySet())); }

        for (String s : list) {
            for (String w : edits(s)) {
                if (dictionary.containsKey(w)) {
                    candidates.put(dictionary.get(w), w);
                }
            }
        }

        return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : word;
    }

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(final String[] args) throws IOException {

        final Logger logger = LoggerFactory.getLogger(SpellingCorrecter.class);

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                logger.info(new SpellingCorrecter("dictSmall.txt").correct(args[i]));
            }
        }

    }

}
