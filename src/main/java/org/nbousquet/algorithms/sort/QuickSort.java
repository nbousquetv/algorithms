package org.nbousquet.algorithms.sort;

import jdk.jshell.execution.Util;
import org.nbousquet.algorithms.common.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {

    /**
     * Sort the provided array in ascending order using bubble sort.
     * <p>
     * The quick sort use a diver an conquer strategy. The principle is to select a index arbitrary (called pivot)
     * in the array and divide the array in 2 part. Numbers on the left of the pivot have to be smaller than the pivot.
     * Numbers on the right of the pivot have to be greater than the pivot. Then repeat the algorithm on the 2
     * partitions.
     * <p>
     * Complexity is O(N^2) for the worst case because efficiantly highly depend of the selected pivot. A good pivot
     * will provide O(Nlog(N)) performance while using the worst possible pivot will give O(N^2) complexity.
     * This algorithm is still very interesting if the choice of the pivot is robust, as the constant factors
     * behind the Nlog(N) complexity are small.
     *
     * @param array array to sort
     */
    public static void sort(int[] array) {
        // A null array  is already sorted.
        if (array == null) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    /**
     * See {@link QuickSort#sort(int[])}. For our divide & conquer strategy we specify in what range we do perform the
     * sorting.
     *
     * @param array array to sort
     * @param start beginning of the range to sort, included.
     * @param end   end of the range to sort, included.
     */
    private static void sort(int[] array, int start, int end) {
        // An empty partition or with a single element is already sorted.
        if (end <= start) {
            return;
        }
        int pivotIndex = partition(array, start, end);
        sort(array, start, pivotIndex - 1);
        sort(array, pivotIndex + 1, end);
    }

    /**
     * partition the provided array around a pivot. All element on the left of the pivot have lower values and
     * all elements on the right have higher value.
     * <p>
     * To be able to to that in place, the pivot is always the last element of the range and elements lower than the
     * pivot are put at the beginning, then the pivot is put after and its index is returned.
     * By definition all the elements that are still after are greater than the pivot.
     *
     * @param array array to partition withing start (included) and end (included)
     * @param start begining of the range to partition
     * @param end   end of the range to partition
     * @return index of the pivot of the partition
     */
    private static int partition(int[] array, int start, int end) {
        // We choose a random pivot and put it at the end of the range.
        // This is to avoid the issue where a wrong pivot would make for bad performance O(N^2).
        int randomPivotIndex = ThreadLocalRandom.current().nextInt(start, end + 1);
        Utils.swap(array, randomPivotIndex, end);
        int pivotValue = array[end];
        // Found out all elements smaller and the pivot and put them at the begining.
        int nextSmallElementIndex = start;
        for (int i = start; i < end; i++) {
            if (array[i] < pivotValue) {
                Utils.swap(array, i, nextSmallElementIndex);
                nextSmallElementIndex++;
            }
        }
        // Swap the pivot that was kept at the end until know in its right place, just after the small elements:
        Utils.swap(array, nextSmallElementIndex, end);
        return nextSmallElementIndex; // The pivot is now there
    }


}
