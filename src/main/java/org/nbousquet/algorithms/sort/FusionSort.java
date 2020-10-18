package org.nbousquet.algorithms.sort;

import org.nbousquet.algorithms.common.Utils;

public class FusionSort {

    /**
     * Sort the provide array in ascending order using insertion sort.
     * <p>
     * The fusion sort has O(N log(N)) complexity.
     * The idea is to recursively divide the array in 2 sub arrays, sort then and then merge the result.
     *
     * @param array array to sort
     */
    public static void sort(int[] array) {
        if (array == null) {
            return;
        }
        sortSubArray(array, 0, array.length - 1);
    }

    /**
     * Sort the provided array but within the index range (start & end included).
     *
     * @param array array to sort
     * @param start start of range (included)
     * @param end   end of range (included)
     */
    private static void sortSubArray(int[] array, int start, int end) {
        // A null array or with only 1 element is already sorted.
        if (end - start == 0) { // Sub array of size one, work is already done !
            return;
        }
        int midDistance = (end - start) / 2;

        sortSubArray(array, start, start + midDistance);
        sortSubArray(array, start + midDistance + 1, end);
        mergeAdjacentSubArrays(array, start, start + midDistance+1, end);
    }

    private static void mergeAdjacentSubArrays(int[] array, int startFirstArray, int startSecondArray, int end) {
        // Create copy of the sub arrays
        int[] firstArray = new int[startSecondArray - startFirstArray];
        System.arraycopy(array, startFirstArray, firstArray, 0, startSecondArray - startFirstArray);
        int[] secondArray = new int[end - startSecondArray + 1];
        System.arraycopy(array, startSecondArray, secondArray, 0, end - startSecondArray + 1);

        // Merge the sub arrays
        int firstIndex = 0;
        int secondIndex = 0;
        for (int i = startFirstArray; i <= end; i++) {
            if (firstArray.length > firstIndex) {
                if ((firstArray.length > firstIndex) && (secondArray.length <= secondIndex || (firstArray[firstIndex] < secondArray[secondIndex]))) {
                    array[i] = firstArray[firstIndex];
                    firstIndex++;
                } else {
                    array[i] = secondArray[secondIndex];
                    secondIndex++;
                }
            }
        }
    }
}
