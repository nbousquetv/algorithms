package org.nbousquet.algorithms.common;

public class Utils {

    /**
     * Swaps 2 elements at specified index in the array.
     * Remark: No check is performed if the array is null or if the indexes are valid. Standard java exception would be
     * raised as expected as this is considered to be a bug.
     *
     * @param array array to swap
     * @param i     first index to swap
     * @param j     second index to swap
     */
    public static void swap(int[] array, int i, int j) {
        int swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }

}
