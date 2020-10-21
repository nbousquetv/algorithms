package org.nbousquet.algorithms.hastable;

/**
 * Basic implementation of a chained Hash Table.
 * <p>
 * An Hash table allows to store and retrieves element by their key in amortized O(1) time.
 * Actually, elements are put in an array like if the key were integer.
 * <p>
 * We must be able to compute an integer hash of each key (here we delegate it to standard hashCode method) and use that
 * number to select in which cell of the array we put the entry.
 * <p>
 * Each cell is actually a linked list and can hold as many entries as necessary. So in case of collision, the map still
 * behaves as expected but slow down a bit. So it is important to have a good hashing method for the key.
 * <p>
 * Finally this map grows the array capacity with the size. The growth rate are controlled by constants
 * MAX_LOAD_FACTOR & GROWTH_FACTOR.
 * <p>
 * Remarks:
 * This map can be exploited for a DOS attack. We don't randomize the hash, and as such a
 * attacker can provide key (say from an HTTP request) that will all go into the same bucket.
 * We have no interface and don't implement any for Java API. Bad for an actual lib, but against, this is just an
 * exercise.
 *
 * @param <K> Type of the Key
 * @param <V> Type of the Value
 */
public class HashTable<K, V> {
    public static final int INITIAL_CAPACITY = 16;
    /*
     * Max load factor as a number between 0 and 1. 0.5 means 50% load factor
     * When the map is more loaded than this max load factor, we create a bigger array and relocate all entries.
     **/
    public static final float MAX_LOAD_FACTOR = 0.7f;
    /**
     * By what factor the size of the array is increased when the max load capacity is crossed
     */
    public static final float GROWTH_FACTOR = 1.33f;

    /**
     * The hash table state is stored in data field, this make grow of the map easier as well as unit testing
     */
    protected Data data = new Data(new Entry[INITIAL_CAPACITY], 0, MAX_LOAD_FACTOR, GROWTH_FACTOR);

    /**
     * Hash Table complete internal state
     */
    public class Data<K, V> {
        /* Array container the HashTable entries. */
        public Entry<K, V>[] array;
        /* Number of entries currently present in the map */
        public int size;
        /* Current max load factor, can be overridden by unit tests */
        public float maxLoadFactor;
        /* Current growth factor, can be overridden by unit tests */
        public float growthFactor;

        // Constructor for pure syntaxic convenience.
        public Data(Entry<K, V>[] array, int size, float maxLoadFactor, float growthFactor) {
            this.array = array;
            this.size = size;
            this.maxLoadFactor = maxLoadFactor;
            this.growthFactor = growthFactor;
        }
        // We don't put getter/setters as this is an internal data structure. We use that class as a "struct"
    }

    /**
     * An entry store an entry (K,V) tuple in the map.
     * Thanks to it nextEntry field, is also act as a node in a linked list.
     * We could have reused existing java linked list for the job, but for the exercise decided to do it by hand.
     **/
    public static class Entry<K, V> {
        public K key;
        public V value;
        public Entry<K, V> nextEntry;

        public Entry(K key, V value, Entry<K, V> nextEntry) {
            this.key = key;
            this.value = value;
            this.nextEntry = nextEntry;
        }
        // We don't put getter/setters as this is an internal data structure. We use that class as a "struct"
    }


    /**
     * Default empty constructor. Nothing special, field are already initialized.
     */
    public HashTable() {
    }

    /**
     * Insert an entry inside the map.
     * If an entry is already present for that key, replace it with the new value.
     *
     * @param key   key to insert. Must not be null.
     * @param value value associated to the key. Can be null.
     * @throws NullPointerException null key was provided as parameter.
     */
    public void put(K key, V value) {
        assertKey(key);
        ensureCapacity();
        insert(data, key, value);
    }

    /**
     * Return the value for the keu, null if not found.
     *
     * @param key key
     * @return Value associated to the value, or null if not found (might be that the inserted value was null).
     * @throws NullPointerException null key was provided as parameter.
     */
    public V get(K key) {
        assertKey(key);
        return retrieve(data, key);
    }

    /**
     * Remove the provided key from the map if it was present.
     * Return the actual value that was associated to key if available, null otherwise.
     *
     * @param key key to remove from the map.
     * @return value associated to removed entry.
     * @throws NullPointerException null key was provided as parameter.
     */
    public V remove(K key) {
        assertKey(key);
        return remove(data, key);
    }

    /**
     * Return the numbers of entries actually present in the map
     */
    public int size() {
        return data.size;
    }

    /**
     * Throw an exception if the key is null
     *
     * @param key key to check.
     */
    private void assertKey(K key) {
        if (key == null) {
            throw new NullPointerException("Provided Key is null");
        }
    }

    /**
     * Hashing strategy used to associate a cell with a key
     *
     * @param hashTableData hash table state
     * @param key           key
     * @return index of the cell for provided key.
     */
    private int computeKeyCell(Data hashTableData, K key) {
        return key.hashCode() % hashTableData.array.length;
    }

    /**
     * For more details: {@link HashTable#put(Object, Object)}
     */
    private void insert(Data hashTableData, K key, V value) {
        int cellIndex = key.hashCode() % hashTableData.array.length;

        if (hashTableData.array[cellIndex] == null) {
            hashTableData.array[cellIndex] = new Entry<K, V>(key, value, null);
            hashTableData.size++;
        } else {
            Entry<K, V> entry = hashTableData.array[cellIndex];
            while (true) {
                if (entry.key.equals(key)) {
                    entry.value = value;
                    return;
                }
                if (entry.nextEntry == null) {
                    break;
                }
                entry = entry.nextEntry;
            }
            entry.nextEntry = new Entry<K, V>(key, value, null);
            hashTableData.size++;
        }
    }

    /**
     * For more details: {@link HashTable#remove(Object)}
     */
    private V remove(Data hashTableData, K key) {
        int cellIndex = computeKeyCell(hashTableData, key);
        if (hashTableData.array[cellIndex] != null) {
            Entry<K, V> parentEntry = null;
            Entry<K, V> entry = hashTableData.array[cellIndex];
            while (entry != null) {
                if (entry.key.equals(key)) {
                    if (parentEntry == null) {
                        hashTableData.array[cellIndex] = entry.nextEntry;
                    } else {
                        parentEntry.nextEntry = entry.nextEntry;
                    }
                    hashTableData.size--;
                    return entry.value;
                }
                parentEntry = entry;
                entry = entry.nextEntry;
            }
        }
        return null;
    }

    /**
     * For more details: {@link HashTable#get(Object)}
     */
    private V retrieve(Data hashTableData, K key) {
        int cellIndex = computeKeyCell(hashTableData, key);
        if (hashTableData.array[cellIndex] != null) {
            Entry<K, V> entry = hashTableData.array[cellIndex];
            while (entry != null && !(entry.key.equals(key))) {
                entry = entry.nextEntry;
            }
            if (entry != null) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * Before every insertion we check the map capacity.
     * If the map is too small, we grow it to be able to insert a bunch of new values.
     * Remark: our basic map grow in size but never shrink.
     */
    private void ensureCapacity() {
        if (data.size > (data.array.length * data.maxLoadFactor)) {
            int newCapacity = (int) (data.array.length * data.growthFactor);
            Data<K, V> newData = new Data<>(new Entry[newCapacity], 0, data.maxLoadFactor, data.growthFactor);
            for (Entry<K, V> entry : data.array) {
                while (entry != null) {
                    insert(newData, entry.key, entry.value);
                    entry = entry.nextEntry;
                }
            }
            data = newData;
        }
    }

}
