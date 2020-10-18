package org.nbousquet.algorithms.sort;

import org.junit.jupiter.api.Test;
import org.nbousquet.algorithms.common.InsertionSort;
import org.nbousquet.algorithms.test.InPlaceSortTester;

public class TestInsertionSort {

    @Test
    public void testInsertionSort() {
        InPlaceSortTester.assertInPlaceSort(InsertionSort::sort);
    }

}
