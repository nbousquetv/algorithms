package org.nbousquet.algorithms.binarytree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestBinaryTree {

    @Test
    public void testConstructor() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        assertDoesNotThrow(() -> tree.remove(0), "Trying to delete an element on an empty tree work as expected");

        for (int current : tree) {
            fail("There no element, so we shall not enter the loop");
        }
    }

    @Test
    public void testInsertWithNullElementThrowsNpe() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        Assertions.assertThrows(NullPointerException.class, () -> {
            tree.insert(null);
        });
    }

    @Test
    public void testDeletetWithNullElementThrowsNpe() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        Assertions.assertThrows(NullPointerException.class, () -> {
            tree.remove(null);
        });
    }

    private <T> List<T> toList(Iterator<T> iterator) {
        List<T> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    @Test
    public void testInsertOneElement() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        tree.insert(3);
        assertTrue(tree.iterator().hasNext(), "There must be an element to iterate with");
        assertEquals(Arrays.asList(3), toList(tree.iterator()), "There should be 1 element and the right one");

        tree.remove(2);
        assertEquals(Arrays.asList(3), toList(tree.iterator()), "Trying to delete an element that isn't there shall do nothing");
        tree.remove(5);
        assertEquals(Arrays.asList(3), toList(tree.iterator()), "Trying to delete an element that isn't there shall do nothing");
    }

    @Test
    public void testInsertHeightElement() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        tree.insert(3);
        tree.insert(7);
        tree.insert(2);
        tree.insert(8);
        tree.insert(1);
        tree.insert(4);
        tree.insert(6);
        tree.insert(5);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), toList(tree.iterator()),
                "Shall iterate in order over all inserted elements");
    }

    @Test
    public void testRemoveRootElement() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        tree.insert(3);

        tree.remove(3);
        assertEquals(Arrays.asList(), toList(tree.iterator()),
                "Removing the only element go back to an empty tree");
    }

    @Test
    public void testRemoveLeftElement() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        tree.insert(2);
        tree.insert(8);
        tree.insert(1);
        tree.remove(1);
        assertEquals(Arrays.asList(2, 8), toList(tree.iterator()), "1 shall have been removed");
    }

    @Test
    public void testParentElement() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        tree.insert(2);
        tree.insert(8);
        tree.insert(1);
        tree.remove(2);
        assertEquals(Arrays.asList(1, 8), toList(tree.iterator()), "2 shall have been removed");
    }

    @Test
    public void testRightElement() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        tree.insert(2);
        tree.insert(8);
        tree.insert(1);
        tree.remove(8);
        assertEquals(Arrays.asList(1, 2), toList(tree.iterator()), "8 shall have been removed");
    }

    @Test
    public void testMiddleNodeRemoveRightSide() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        tree.insert(2);
        tree.insert(1);
        tree.insert(8);
        tree.insert(7);
        tree.insert(9);

        tree.remove(8);
        assertEquals(Arrays.asList(1, 2, 7, 9), toList(tree.iterator()), "8 shall have been removed");
    }

    @Test
    public void testMiddleNodeRemoveLeftSide() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        tree.insert(10);
        tree.insert(12);
        tree.insert(8);
        tree.insert(7);
        tree.insert(9);
        tree.remove(8);
        assertEquals(Arrays.asList(7, 9, 10, 12), toList(tree.iterator()), "8 shall have been removed");
    }

    @Test
    public void testBiggerTree() {
        BasicBinaryTree<Integer> tree = new BasicBinaryTree<Integer>(Comparator.naturalOrder());
        List<Integer> allIntegers = IntStream.range(0, 1000).boxed().collect(Collectors.toList());
        Collections.shuffle(allIntegers);
        List<Integer> toRemove = new ArrayList<>(allIntegers.subList(0, allIntegers.size() / 2));
        List<Integer> remaining = new ArrayList<>(allIntegers.subList(allIntegers.size() / 2, allIntegers.size()));
        remaining.sort(Integer::compareTo);

        System.out.println("All" + allIntegers);
        System.out.println("ToRemove" + toRemove);
        System.out.println("Remaining" + remaining);
        for (Integer i : allIntegers) {
            tree.insert(i);
        }
        for (Integer i : toRemove) {
            tree.remove(i);
        }
        assertEquals(remaining, toList(tree.iterator()));
    }


}