package org.nbousquet.algorithms.sort;

import org.nbousquet.algorithms.common.Utils;

public class HeapSort {

    /**
     * Sort the provided array using heap sort.
     * <p>
     * A "max heap" is a binary tree where each node hold a bigger value than its 2 children.
     * We can efficiently store it as an array storing the binary layer by layer.
     * The index O is the root, index one is the left child of the root and index two the right child of the root.
     * This goes on recursively index 3 & 4 are children of 1 and index 5 & 6 are children of 2.
     * <p>
     * So for a node at index i we can say that:
     * leftchild index = 2* i + 1;
     * rightchild index = 2 * i + 1;
     * parent index = (i-1) / 2
     * <p>
     * The algorithm first transform an arbitrary array into a max heap, meaning that the largest element is now
     * at the root, index 0. It then put the root at the end knowing it at the right place, being the largest and
     * it rebuild a max heap with an array of size reduced by 1.
     * <p>
     * See https://en.wikipedia.org/wiki/Heapsort for more details.
     *
     * @param array
     */
    public static void sort(int[] array) {
        if (array == null) {
            return;
        }
        buildMaxHeap(array, array.length);
        for (int i = array.length; i >= 1; i--) {
            // Put the biggest element at its final place
            Utils.swap(array, i - 1, 0);
            // Rectify the heap that has now an arbitrary root.
            maxHeapify(array, i - 1, 0);
        }

    }


    /**
     * Perform a max-Heapify operation at provided index on a heap stored as an array.
     * <p>
     * The max-heapify operation for a given node correct the provided root to ensure the property of the max-heap are
     * meet. In case one of the children has changed, the operation is done recursively.
     *
     * @param array arbitrary array
     */
    private static void maxHeapify(int[] array, int length, int index) {
        int leftChild = 2 * index + 1;
        int rightChild = leftChild + 1;
        int max = index;

        if (leftChild < length && array[leftChild] > array[max]) {
            max = leftChild;
        }
        if (rightChild < length && array[rightChild] > array[max]) {
            max = rightChild;
        }
        if (max != index) {
            Utils.swap(array, max, index);
            maxHeapify(array, length, max);
        }
    }

    private static void buildMaxHeap(int[] array, int length) {
        for (int i = (array.length - 1) / 2; i >= 0; i--) {
            maxHeapify(array, array.length, i);
        }
    }
}
