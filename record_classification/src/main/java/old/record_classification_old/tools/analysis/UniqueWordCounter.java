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
package old.record_classification_old.tools.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;

import old.record_classification_old.datastructures.tokens.TokenSet;
import old.record_classification_old.tools.ReaderWriterFactory;
import old.record_classification_old.tools.Utils;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * The Class UniqueWordCounter.
 */
public final class UniqueWordCounter {

    /** The Constant LUCENE_VERSION. */
    static final Version LUCENE_VERSION = Version.LUCENE_36;

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(final String[] args) throws IOException {

        if (!new File(args[0]).exists()) {
            System.err.println("Argument 0 must be a path to a file and it must be accessible to the program");
        }

        getNumberOfUniqueWords(new File(args[0]));
    }

    /**
     * Instantiates a new unique word counter.
     */
    private UniqueWordCounter() {

        //utility class - private constructor
    }

    /**
     * Passes each line of a document through an {@link Analyzer} to remove stop words then counts the total number of unique words in that file.
     * This result is then written to file.
     * @param inputFile File to count unique words of.
     * @return number of unique words.
     * @throws IOException io error.
     */
    public static int getNumberOfUniqueWords(final File inputFile) throws IOException {

        Multiset<String> wordMultiset = HashMultiset.create();
        BufferedReader br = ReaderWriterFactory.createBufferedReader(inputFile);

        String line = "";
        while ((line = br.readLine()) != null) {
            wordMultiset = countWordsInLine(wordMultiset, new TokenSet(line));
        }

        StringBuilder sb = new StringBuilder();
        for (String string : wordMultiset.elementSet()) {
            sb.append(string).append("\t").append(wordMultiset.count(string)).append("\n");
        }
        Utils.writeToFile(sb.toString(), "target/wordCounts.txt");
        br.close();

        return 0;
    }

    /**
     * Counts the number of words in a string.
     *
     * @param wordMultiset the word multiset to add words to. This keeps track of how many time each word appears.
     * @return the multiset with new words added
     */
    public static Multiset<String> countWordsInLine(final Multiset<String> wordMultiset, final TokenSet tokenSet) {

        for (String token : tokenSet) {
            wordMultiset.add(token.trim().toLowerCase());
        }
        return wordMultiset;
    }
}
