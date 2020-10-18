package org.nbousquet.algorithms.common;

public class InsertionSort {

    /**
     * Sort the provide array in ascending order using insertion sort.
     * <p>
     * The insertion sort has O(N^2) complexity.
     * It process the array by inserting element one by one in the already sorted array.
     *
     * @param array array to sort
     */
    public static void sort(int[] array) {
        // A null array or with only 1 element is already sorted.
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[i]) {
                    Utils.swap(array, i, j);
                }
            }
        }
    }
}
