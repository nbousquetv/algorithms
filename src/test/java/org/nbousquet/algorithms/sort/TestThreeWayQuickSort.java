package org.nbousquet.algorithms.sort;

import org.junit.jupiter.api.Test;
import org.nbousquet.algorithms.test.InPlaceSortTester;

public class TestThreeWayQuickSort {

    @Test
    public void testThreeWayQuickSort() {
        InPlaceSortTester.assertInPlaceSort(ThreeWayQuickSort::sort);
    }

}
