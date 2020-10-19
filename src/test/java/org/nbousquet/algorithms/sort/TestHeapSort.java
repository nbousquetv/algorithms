package org.nbousquet.algorithms.sort;

import org.junit.jupiter.api.Test;
import org.nbousquet.algorithms.test.InPlaceSortTester;

public class TestHeapSort {

    @Test
    public void testHeapSort() {
        InPlaceSortTester.assertInPlaceSort(HeapSort::sort);
    }

}
