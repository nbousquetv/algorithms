package org.nbousquet.algorithms.sort;

import org.nbousquet.algorithms.common.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class ThreeWayQuickSort {

    /**
     * Sort the provided array in ascending order using randomized 3 way quick sort.
     * <p>
     * The quick sort use a divide and conquer strategy. The principle is to select a index arbitrary (called pivot)
     * in the array and divide the array in 3 part. Numbers on the left of the pivot have to be smaller than the pivot.
     * Numbers on the right of the pivot have to be greater than the pivot and number in the middle are equal to
     * the pivot. It possible to create such partition in linear time. Then repeat the algorithm on the partitions left
     * & right partition as the middle partition is already sorted. This avod the poor performance of quick sort when
     * many there lot of equals elements in the array to sort.
     * <p>
     * Complexity is O(N^2) for the worst case because efficiency highly depend of the selected pivot. A good pivot
     * will provide O(Nlog(N)) performance while using the worst possible pivot will give O(N^2) complexity.
     * This algorithm is still very interesting if the choice of the pivot is robust, as the constant factors
     * behind the Nlog(N) complexity are small. Implementation here choose the pivot randomly.
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
     * See {@link ThreeWayQuickSort#sort(int[])}. For our divide & conquer strategy we specify in what range we do perform the
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
        PivotIndexes pivotIndex = partition(array, start, end);
        sort(array, start, pivotIndex.start - 1);
        sort(array, pivotIndex.end + 1, end);
    }

    static class PivotIndexes {
        int start;
        int end;
    }

    /**
     * Partition the provided array around a pivot. All elements on the left of the pivot have lower values and
     * all elements on the right have higher value.
     * <p>
     * To be able to do that in place, the pivot is always the last element of the range and elements lower than the
     * pivot are put at the beginning, then the pivot is put after and its index is returned.
     * By definition all the elements that are still after are greater than the pivot.
     *
     * @param array array to partition withing start (included) and end (included)
     * @param start begining of the range to partition
     * @param end   end of the range to partition
     * @return range of the pivot of the partition
     */
    private static PivotIndexes partition(int[] array, int start, int end) {
        // We choose a random pivot and put it at the end of the range.
        // This is to avoid the issue where a wrong pivot would make for bad performance O(N^2).
        int randomPivotIndex = ThreadLocalRandom.current().nextInt(start, end + 1);
        Utils.swap(array, randomPivotIndex, end);
        int pivotValue = array[end];
        // Found out all elements smaller and the pivot and put them at the begining.
        int nextSmallElementIndex = start;
        int nextEqualElementIndex = end - 1;
        int i = start;
        while (i <= nextEqualElementIndex) {
            if (array[i] < pivotValue) {
                Utils.swap(array, i, nextSmallElementIndex);
                nextSmallElementIndex++;
                i++;
            } else if (array[i] == pivotValue) {
                Utils.swap(array, i, nextEqualElementIndex);
                nextEqualElementIndex--;
            } else {
                i++;
            }
        }
        PivotIndexes pivot = new PivotIndexes();
        pivot.start = nextSmallElementIndex;
        for (i = nextEqualElementIndex + 1; i <= end; i++) {
            Utils.swap(array, nextSmallElementIndex, i);
            nextSmallElementIndex++;
        }
        pivot.end = nextSmallElementIndex - 1;
        return pivot;
    }


}
