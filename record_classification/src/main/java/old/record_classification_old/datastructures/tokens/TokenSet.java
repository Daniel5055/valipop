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
package old.record_classification_old.datastructures.tokens;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import old.record_classification_old.machinelearning.tokenizing.TokenStreamIterator;
import old.record_classification_old.machinelearning.tokenizing.TokenStreamIteratorFactory;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

/**
 *{@link TokenSet}s are used to represent a bag of tokens. Tokens in this context are strings that are whitespace delimited, ie single words.
 * TokenSets can contain multiple of the same token.
 * 
 * Created by fraserdunlop on 09/06/2014 at 10:06.
 */
public class TokenSet implements Serializable, Collection<String> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4771078200991926082L;
    /** The token set. */
    private Multiset<String> tokenSet;

    /**
     * Instantiates a new token set.
     */
    public TokenSet() {

        tokenSet = LinkedHashMultiset.create();
    }

    /**
     * Instantiates a new token set.
     *
     * @param string the string
     */
    public TokenSet(final String string) {

        this();
        addSubstringTokensToTokenSet(string.toLowerCase());
    }

    /**
     * Instantiates a new token set.
     *
     * @param tokenSet the token set
     */
    public TokenSet(final Collection<String> tokenSet) {

        this();
        this.tokenSet.addAll(tokenSet);
    }

    /**
     * Adds the substring tokens to token set.
     *
     * @param substring the substring
     */
    public void addSubstringTokensToTokenSet(final String substring) {

        TokenStreamIterator<CharTermAttribute> it = TokenStreamIteratorFactory.newTokenStreamIterator(substring.toLowerCase());
        while (it.hasNext()) {
            String token = it.next().toString();
            if (token.trim().length() != 0) {
                tokenSet.add(token.trim());
            }
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (String token : tokenSet) {
            sb.append(token).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Contains.
     *
     * @param token the token
     * @return true, if successful
     */
    public boolean contains(final String token) {

        return tokenSet.contains(token);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#size()
     */
    public int size() {

        return tokenSet.size();
    }

    /* (non-Javadoc)
     * @see java.util.Collection#isEmpty()
     */
    public boolean isEmpty() {

        return tokenSet.isEmpty();
    }

    /* (non-Javadoc)
     * @see java.util.Collection#contains(java.lang.Object)
     */
    @Override
    public boolean contains(final Object o) {

        return tokenSet.contains(o);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#iterator()
     */
    @Override
    public Iterator<String> iterator() {

        return tokenSet.iterator();
    }

    /* (non-Javadoc)
     * @see java.util.Collection#toArray()
     */
    @Override
    public Object[] toArray() {

        return tokenSet.toArray();
    }

    /* (non-Javadoc)
     * @see java.util.Collection#toArray(java.lang.Object[])
     */
    @Override
    public <T> T[] toArray(final T[] a) {

        return tokenSet.toArray(a);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#add(java.lang.Object)
     */
    @Override
    public boolean add(final String s) {

        return tokenSet.add(s);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#remove(java.lang.Object)
     */
    @Override
    public boolean remove(final Object o) {

        return tokenSet.remove(o);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(final Collection<?> c) {

        return tokenSet.containsAll(c);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(final Collection<? extends String> c) {

        return tokenSet.addAll(c);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(final Collection<?> c) {

        return tokenSet.removeAll(c);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(final Collection<?> c) {

        return tokenSet.retainAll(c);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#clear()
     */
    @Override
    public void clear() {

        tokenSet.clear();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object o) {

        if (this == o) { return true; }
        if (!(o instanceof TokenSet)) { return false; }

        TokenSet strings = (TokenSet) o;

        if (!tokenSet.equals(strings.tokenSet)) { return false; }

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return tokenSet.hashCode();
    }
}
