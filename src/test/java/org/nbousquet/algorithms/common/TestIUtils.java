package org.nbousquet.algorithms.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nbousquet.algorithms.test.InPlaceSortTester;

public class TestIUtils {

    @Test
    public void testSwapExceptionCases() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Utils.swap(null, 1, 2);
        });
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Utils.swap(new int[]{}, 1, 2);
        });
    }

    @Test
    public void testNominalCase() {
        int[] actual = new int[]{0, 1, 2};
        Utils.swap(actual, 1, 2);
        Assertions.assertArrayEquals(new int[]{0, 2, 1}, actual);
    }

    @Test
    public void testSwapSameIndex() {
        int[] actual = new int[]{0, 1, 2};
        Utils.swap(actual, 1, 1);
        Assertions.assertArrayEquals(new int[]{0, 1, 2}, actual);
    }


}
