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
package uk.ac.standrews.cs.digitising_scotland.record_classification_old.classifiers.lookup;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import uk.ac.standrews.cs.digitising_scotland.record_classification_old.datastructures.tokens.TokenSet;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import uk.ac.standrews.cs.digitising_scotland.record_classification_old.classifiers.resolver.Interfaces.SubsetEnumerator;

/**
 * This class creates and stores the nGrams created from a record and provides
 * methods to access those nGrams.
 *
 * @author frjd2
 *
 *
 */
public class NGramSubstrings implements Iterable<TokenSet>, SubsetEnumerator<TokenSet> {

    //TODO - refactor this class!

    private final List<TokenSet> grams;

    @Override
    public Multiset<TokenSet> enumerate(TokenSet tokenSet) throws IOException {
        Multiset<TokenSet> multiset = HashMultiset.create();
        multiset.addAll(splitIntoNGrams(tokenSet.toString()));
        return multiset;
    }

    /**
     * Initalises and {@link NGramSubstrings} object with the input string split
     * into grams.
     *
     * @param inputString
     *            the input string to create Ngrams from.
     * @throws IOException
     *             IO error
     */
    public NGramSubstrings(final String inputString) throws IOException {

        this.grams = splitIntoNGrams(inputString);
    }

    /**
     * Initalises and {@link NGramSubstrings} object with the input string split
     * into grams.
     *
     *            the input tokenSet to create Ngrams from.
     *             IO error
     */
    public NGramSubstrings() {
        this.grams = new ArrayList<>();
    }

    /**
     * Returns the nGrams for this {@link NGramSubstrings} class as a List of
     * Strings.
     *
     * @return List<String> nGrams created for the original string or Record
     */
    public List<TokenSet> getGrams() {

        return grams;
    }

    /**
     * Returns the nGrams for this {@link NGramSubstrings} class as a List of
     * Strings.
     *
     * @return List<String> nGrams created for the original string or Record
     */
    public Multiset<TokenSet> getGramMultiset() {

        Multiset<TokenSet> multiset = HashMultiset.create();
        multiset.addAll(getGrams());

        return multiset;
    }

    /**
     * Splits a string into nGrams.
     *
     * @param stringToSplit
     *            - the string to be split into nGrams
     * @return ArrayList<String> - an ArrayList of NGrams
     * @throws IOException
     *             If the stream cannot read the stringToSplit
     *
     */
    private List<TokenSet> splitIntoNGrams(final String stringToSplit) throws IOException {

        TokenStream stream = null;
        TokenStream nGramStream = null;
        stream = constructStream(stringToSplit);
        int longestGram = calculateLongestGram(stringToSplit);
        nGramStream = new ShingleFilter(stream, 2, longestGram);
        List<TokenSet> createNGrams = createNGrams(nGramStream);

        return createNGrams;

    }

    /**
     * Checks for a null stream and closes it.
     *
     * @param stream
     *            Stream to close
     * @throws IOException
     *             if stream cannot be closed
     */
    private static void closeStreams(final TokenStream stream) throws IOException {

        if (stream != null) {
            stream.close();
        }

    }

    /**
     * Constructs a {@link StandardTokenizer} from this string.
     *
     * @param string
     *            String to create Tokenizer from.
     * @return {@link StandardTokenizer} from string
     */
    private static StandardTokenizer constructStream(final String string) {

        return new StandardTokenizer(Version.LUCENE_36, new StringReader(string));
    }

    private static List<TokenSet> createNGrams(final TokenStream nGramStream) throws IOException {

        List<TokenSet> grams = new ArrayList<TokenSet>();
        while (nGramStream.incrementToken()) {
            grams.add(new TokenSet(nGramStream.getAttribute(CharTermAttribute.class).toString()));
        }
        closeStreams(nGramStream);

        return grams;
    }

    private static int calculateLongestGram(final String string) throws IOException {

        int longestGram = countNumTokensInStream(string);
        if (longestGram < 2) {
            longestGram = 2;
        }
        return longestGram;
    }

    private static int countNumTokensInStream(final String string) throws IOException {

        TokenStream stream = constructStream(string);
        int tokens = 0;
        while (stream.incrementToken()) {
            tokens++;
        }
        return tokens;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((grams == null) ? 0 : grams.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        NGramSubstrings other = (NGramSubstrings) obj;
        if (grams == null) {
            if (other.grams != null) { return false; }
        }
        else if (!grams.equals(other.grams)) { return false; }
        return true;
    }

    @Override
    public Iterator<TokenSet> iterator() {

        return grams.iterator();
    }
}
