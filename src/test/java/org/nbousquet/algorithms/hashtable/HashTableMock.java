package org.nbousquet.algorithms.hashtable;

public class HashTableMock<K, V> extends HashTable<K, V> {

    protected Data<K, V> getData() {
        return data;
    }

}
