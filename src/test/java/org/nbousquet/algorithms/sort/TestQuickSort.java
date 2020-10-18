package org.nbousquet.algorithms.sort;

import org.junit.jupiter.api.Test;
import org.nbousquet.algorithms.test.InPlaceSortTester;

public class TestQuickSort {

    @Test
    public void testQuickSort() {
        InPlaceSortTester.assertInPlaceSort(QuickSort::sort);
    }

}
