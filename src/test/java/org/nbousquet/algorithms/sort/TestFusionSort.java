package org.nbousquet.algorithms.sort;

import org.junit.jupiter.api.Test;
import org.nbousquet.algorithms.common.InsertionSort;
import org.nbousquet.algorithms.test.InPlaceSortTester;

public class TestFusionSort {

    @Test
    public void testFusionSort() {
        InPlaceSortTester.assertInPlaceSort(FusionSort::sort);
    }

}
