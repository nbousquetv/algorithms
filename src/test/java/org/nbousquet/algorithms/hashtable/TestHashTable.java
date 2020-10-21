package org.nbousquet.algorithms.hashtable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nbousquet.algorithms.hastable.HashTable;

import static org.junit.jupiter.api.Assertions.*;

public class TestHashTable {

    @Test
    public void testSimpleNewEmptyHashTable() {
        HashTableMock<String, Integer> map = new HashTableMock<>();
        assertEquals(0, map.size(), "New hashtable shall be empty");
        HashTable<String, Integer>.Data<String, Integer> data = map.getData();
        assertEquals(HashTable.INITIAL_CAPACITY, data.array.length,
                "Array shall be initialized with initial capacity constant");
        assertEquals(HashTable.MAX_LOAD_FACTOR, data.maxLoadFactor, "Max Load factor shall be correctly forwarded");
        assertEquals(HashTable.GROWTH_FACTOR, data.growthFactor, "Growth factor shall be correctly forwarded");

    }

    @Test
    public void testGetWithNullKeyThrowsNpe() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            HashTable<String, Integer> map = new HashTable<>();
            map.get(null);
        });
    }

    @Test
    public void testPutWithNullKeyThrowsNpe() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            HashTable<String, Integer> map = new HashTable<>();
            map.put(null, 3);
        });
    }

    @Test
    public void testRemoveWithNullKeyThrowsNpe() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            HashTable<String, Integer> map = new HashTable<>();
            map.remove(null);
        });
    }


    @Test
    public void testSimpleRemoveOnEmptyMapDoesNothing() {
        HashTableMock<String, Integer> map = new HashTableMock<>();
        map.remove("3");
        assertEquals(0, map.size(), "New hashtable shall be empty");
        HashTable<String, Integer>.Data<String, Integer> data = map.getData();
        assertEquals(HashTable.INITIAL_CAPACITY, data.array.length,
                "Array shall be initialized with initial capacity constant");
    }

    @Test
    public void testSimpleInsertOneEntryOnEmptyMap() {
        HashTableMock<String, Integer> map = new HashTableMock<>();
        map.put("3", 3);
        assertEquals(1, map.size(), "Shall have 1 element");
        assertEquals(3, map.get("3"), "Shall be able to retrieve inserted value");
    }

    @Test
    public void testSimpleInsertTenValuesOnEmptyMap() {
        HashTableMock<String, Integer> map = new HashTableMock<>();
        map.put("0", 0);
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);
        map.put("6", 6);
        map.put("7", 7);
        map.put("8", 8);
        map.put("9", 9);
        assertEquals(10, map.size(), "Shall have 10 elements");
        assertEquals(0, map.get("0"));
        assertEquals(1, map.get("1"));
        assertEquals(2, map.get("2"));
        assertEquals(3, map.get("3"));
        assertEquals(4, map.get("4"));
        assertEquals(5, map.get("5"));
        assertEquals(6, map.get("6"));
        assertEquals(7, map.get("7"));
        assertEquals(8, map.get("8"));
        assertEquals(9, map.get("9"));
    }

    @Test
    public void testSimpleOverwriteOfValues() {
        HashTableMock<String, Integer> map = new HashTableMock<>();
        map.put("0", 0);
        map.put("1", 1);
        map.put("2", 2);
        map.put("0", 10);
        map.put("1", 11);
        map.put("2", 12);
        assertEquals(3, map.size(), "Shall have 3 elements");
        assertEquals(10, map.get("0"));
        assertEquals(11, map.get("1"));
        assertEquals(12, map.get("2"));
    }

    @Test
    public void testSimpleRemovalOfValue() {
        HashTableMock<String, Integer> map = new HashTableMock<>();
        map.put("0", 0);
        map.put("1", 1);
        map.put("2", 2);
        map.remove("1");
        assertEquals(2, map.size(), "Shall have 3 elements");
        assertEquals(0, map.get("0"), "Non removed value shall still be present");
        assertNull(map.get("1"), "Value was removed, get shall return null");
        assertEquals(2, map.get("2"), "Non removed value shall still be present");
    }

    private HashTableMock<String, Integer> singleBucketMap20MaxLoadFactor() {
        HashTableMock<String, Integer> map = new HashTableMock<>();
        HashTable<String, Integer>.Data<String, Integer> data = map.getData();
        data.maxLoadFactor = 20;
        data.array = new HashTable.Entry[1];
        return map;
    }

    @Test
    public void testInsertionInSameBucket() {
        HashTableMock<String, Integer> map = singleBucketMap20MaxLoadFactor();
        map.put("0", 0);
        map.put("1", 1);
        map.put("2", 2);
        assertEquals(1, map.getData().array.length, "Backing array kept size 1");
        assertEquals(3, map.size(), "Shall have 3 elements");
        assertEquals(0, map.get("0"));
        assertEquals(1, map.get("1"));
        assertEquals(2, map.get("2"));
    }

    @Test
    public void testRemovalInSameBucket() {
        HashTableMock<String, Integer> map = singleBucketMap20MaxLoadFactor();
        map.put("0", 0);
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        assertEquals(1, map.getData().array.length, "Backing array kept size 1");
        assertEquals(5, map.size(), "Shall have 5 elements");

        // Removal of first element in bucket:
        map.remove("0");
        assertEquals(1, map.getData().array.length, "Backing array kept size 1");
        assertEquals(4, map.size(), "Shall have 4 elements");
        assertNull(map.get("0"), "0 shall not longer be there");
        assertEquals(1, map.get("1"), "Other elements shall still be there");
        assertEquals(2, map.get("2"), "Other elements shall still be there");
        assertEquals(3, map.get("3"), "Other elements shall still be there");
        assertEquals(4, map.get("4"), "Other elements shall still be there");

        // Removal of last element in bucket
        map.remove("4");
        assertEquals(1, map.getData().array.length, "Backing array kept size 1");
        assertEquals(3, map.size(), "Shall have 3 elements");
        assertNull(map.get("0"), "0 shall not longer be there");
        assertEquals(1, map.get("1"), "Other elements shall still be there");
        assertEquals(2, map.get("2"), "Other elements shall still be there");
        assertEquals(3, map.get("3"), "Other elements shall still be there");
        assertNull(map.get("4"), "4 shall not longer be there");

        // Removal of middle element in bucket
        map.remove("2");
        assertEquals(1, map.getData().array.length, "Backing array kept size 1");
        assertEquals(2, map.size(), "Shall have 2 elements");
        assertNull(map.get("0"), "0 shall not longer be there");
        assertEquals(1, map.get("1"), "Other elements shall still be there");
        assertNull(map.get("2"), "2 shall not longer be there");
        assertEquals(3, map.get("3"), "Other elements shall still be there");
        assertNull(map.get("4"), "4 shall not longer be there");

        // Removal of everything and even more
        map.remove("1");
        map.remove("3");
        map.remove("5");
        map.remove("6");
        assertEquals(1, map.getData().array.length, "Backing array kept size 1");
        assertEquals(0, map.size(), "Shall be empty");
        assertNull(map.get("0"), "Shall be empty");
        assertNull(map.get("1"), "Shall be empty");
        assertNull(map.get("2"), "Shall be empty");
        assertNull(map.get("3"), "Shall be empty");
        assertNull(map.get("4"), "Shall be empty");
    }

    @Test
    public void testLoadFactor() {
        HashTableMock<String, Integer> map = new HashTableMock<>();

        // Let's load the map with some data so it need to grow...
        for (int i = 0; i < 100; i++) {
            map.put("" + i, i);
            assertEquals(i + 1, map.size(), "Size shall be still computed correctly");
            int minCapacity = (int) (map.size() * (1.0f / HashTable.MAX_LOAD_FACTOR)) - 1;
            assertTrue(map.getData().array.length >= minCapacity,
                    "Load Factor shall stay bellow a certain level for index " + i +
                            "Array length :" + map.getData().array.length + " min capacity :" + minCapacity);
        }

        // Let's check the data is still accessible after several growth steps:
        for (int i = 0; i < 100; i++) {
            assertEquals(i, map.get("" + i), "After a growth in capacity data shall still be present in the map for index " + i);
        }
    }


}
