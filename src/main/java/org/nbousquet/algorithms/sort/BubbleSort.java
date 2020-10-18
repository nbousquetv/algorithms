package org.nbousquet.algorithms.sort;

import org.nbousquet.algorithms.common.Utils;

public class BubbleSort {

    /**
     * Sort the provided array in ascending order using bubble sort.
     * <p>
     * The bubble sort algorithm sort by inverting adjacent elements until the whole array is sorted.
     * Complexity is O(N^2).
     *
     * @param array array to sort
     */
    public static void sort(int[] array) {
        // A null array or with only 1 element is already sorted.
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    Utils.swap(array, i, j);
                }
            }
        }
    }
}
