package org.nbousquet.algorithms.test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InPlaceSortTester {


    public static void assertInPlaceSort(Consumer<int[]> inPlaceSorting, int[] expected, int[] actual, String msg) {
        inPlaceSorting.accept(actual);
        assertArrayEquals(expected, actual, msg);
    }

    public static void assertInPlaceSortGeneratedArray(Consumer<int[]> inPlaceSorting, int nbrElements) {
        int[] randomArray = new int[nbrElements];
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = ThreadLocalRandom.current().nextInt(randomArray.length / 10);
        }
        inPlaceSorting.accept(randomArray);
        for (int i = 0; i < randomArray.length - 1; i++) {
            assertTrue(randomArray[i] <= randomArray[i + 1]);
        }
    }


    public static void assertInPlaceSort(Consumer<int[]> inPlaceSorting) {
        assertInPlaceSort(inPlaceSorting, null, null, "Null array");
        assertInPlaceSort(inPlaceSorting, new int[]{3}, new int[]{3}, "Single element array");
        assertInPlaceSort(inPlaceSorting, new int[]{1, 2}, new int[]{2, 1}, "2 elements array");
        assertInPlaceSort(inPlaceSorting, new int[]{1, 2}, new int[]{1, 2}, "Already sorted 2 elements array");
        assertInPlaceSort(inPlaceSorting, new int[]{1, 2, 3}, new int[]{2, 3, 1}, "3 element typical array");
        assertInPlaceSort(inPlaceSorting, new int[]{1, 2, 3, 4, 5}, new int[]{5, 4, 3, 2, 1}, "5 elements reversed array");
        assertInPlaceSort(inPlaceSorting, new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5}, new int[]{5, 4, 3, 2, 2, 1, 5, 3, 4, 1}, "10 elements with duplicates");
        assertInPlaceSort(inPlaceSorting, new int[]{1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2}, new int[]{1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2}, "Many duplicated");

        assertInPlaceSortGeneratedArray(inPlaceSorting, 100);
        assertInPlaceSortGeneratedArray(inPlaceSorting, 10000);
    }
}
