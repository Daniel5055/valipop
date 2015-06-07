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
package uk.ac.standrews.cs.digitising_scotland.record_classification_old.classifiers.resolver.generic;

import uk.ac.standrews.cs.digitising_scotland.record_classification_old.tools.DeepCloner;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * A map from keys to lists of values, iterable over keys and serializable.
 * @author frjd2
 * @author jkc25
 *         Created by fraserdunlop on 10/06/2014 at 14:57.
 */
public class MultiValueMap<K, V> implements Iterable<K>, Serializable, Map<K, List<V>> {

    DeepCloner deepCloner = new DeepCloner();
    private final Map<K, List<V>> map;

    public MultiValueMap(final Map<K, List<V>> map) {

        this.map = map;
    }

    public MultiValueMap<K, V> deepClone() throws IOException, ClassNotFoundException {

        return deepCloner.deepClone(this);
    }

    /**
     * Calculates a numerical value for the complexity of the map.
     * This value is calculated by multiplying the size of each list by the size of every other list.
     * For example, a Map with 3 different keys, with lists of length 4, 5 and 6 would result in a complexity of
     * 120 being returned (4*5*6). Returns Long.MAX_VALUE if overflow occurs.
     * @return the int numerical representation of the complexity of the matrix.
     */
    public long complexity() {

        long complexity = 1;
        long overFlowCheck = 1;
        for (K k : this) {
            if (!map.get(k).isEmpty()) {
                overFlowCheck = complexity * map.get(k).size();
            }
            if (overFlowCheck < complexity) { return Long.MAX_VALUE; //TODO check that this happens, not tested yet!
            }
            complexity = overFlowCheck;
        }
        return complexity;
    }

    /**
     * Adds the value v to the list associated with key k.
     * @param k the key
     * @param v the value
     */
    public void add(final K k, final V v) {

        if (map.get(k) == null) {
            map.put(k, new ArrayList<V>());
        }
        map.get(k).add(v);
    }

    @Override
    public int size() {

        return map.keySet().size();
    }

    @Override
    public boolean isEmpty() {

        return map.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {

        return map.containsValue(value);
    }

    @Override
    public List<V> get(final Object key) {

        return map.get(key);
    }

    @Override
    public List<V> put(final K key, final List<V> value) {

        return map.put(key, value);
    }

    @Override
    public Collection<List<V>> values() {

        return map.values();
    }

    @Override
    public Set<Entry<K, List<V>>> entrySet() {

        return map.entrySet();
    }

    @Override
    public Iterator<K> iterator() {

        return map.keySet().iterator();
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((map == null) ? 0 : map.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        MultiValueMap other = (MultiValueMap) obj;
        if (map == null) {
            if (other.map != null) { return false; }
        }
        else if (!map.equals(other.map)) { return false; }
        return true;
    }

    @Override
    public String toString() {

        return "ResolverMatrix [map=" + map + "]";
    }

    @Override
    public List<V> remove(final Object key) {

        return map.remove(key);
    }

    @Override
    public void putAll(final Map<? extends K, ? extends List<V>> m) {

        map.putAll(m);
    }

    @Override
    public void clear() {

        map.clear();
    }

    @Override
    public Set<K> keySet() {

        return map.keySet();
    }
}
