package org.nbousquet.algorithms.hashtable;

import org.nbousquet.algorithms.hastable.HashTable;

public class HashTableMock<K, V> extends HashTable<K, V> {

    protected Data<K, V> getData() {
        return data;
    }

}
