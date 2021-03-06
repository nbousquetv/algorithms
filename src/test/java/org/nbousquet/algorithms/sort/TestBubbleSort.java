package org.nbousquet.algorithms.sort;

import org.junit.jupiter.api.Test;
import org.nbousquet.algorithms.common.InsertionSort;
import org.nbousquet.algorithms.test.InPlaceSortTester;

public class TestBubbleSort {

    @Test
    public void testBubbleSort() {
        InPlaceSortTester.assertInPlaceSort(BubbleSort::sort);
    }

}
