package org.nbousquet.algorithms.binarytree;

import java.util.Comparator;
import java.util.Iterator;

/**
 * A Basic binary tree that make no effort to remains balanced.
 * A binary tree is good to keep data sorted and to iterate on it in order.
 * It can be used to implement other data structure such as priority queues or TreeMap. But in the general case,
 * TreeMap are slower that HashMap.
 * <p>
 * Complexity for adding/finding/removing an element is O(k) where k is the deph of the tree. But if the tree isn't
 * necessarily balanced, then in the worst case k=n meaning this is done O(N).
 * <p>
 * More advanced version of a BinaryTree ensure that the tree stay balanced after every insertion/deletion. This then
 * ensure O(log N) worst case scenario. Examples includes AVL tree or RedBlack tree.
 * <p>
 * Remark current version of the BinaryTree does not support duplicate entries.
 *
 * @param <T> The type of data stored in the tree.
 */
public class BasicBinaryTree<T> implements Iterable<T> {

    /**
     * A node in the tree. For convenience, the three use double chaining. parent reference children &
     * children reference parent.
     * <p>
     * Also the assumption is that elements lower than the current node are on the left and elements bigger are on the
     * right.
     *
     * @param <T>
     */
    public static class Node<T> {
        private Node<T> parent;
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(Node<T> parent, T data) {
            this.parent = parent;
            this.data = data;
        }

        public Node<T> getParent() {
            return parent;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getLeft() {
            return left;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public Node<T> getRight() {
            return right;
        }

        public void setRight(Node<T> right) {
            this.right = right;
        }
    }

    /**
     * The ordering used to construct the tree
     */
    protected final Comparator<T> comparator;
    /**
     * Root of the tree. may be null
     */
    protected Node<T> root;

    /**
     * The constructor allow to choose how elements are compared.
     *
     * @param comparator comparator used to build the tree.
     */
    public BasicBinaryTree(Comparator<T> comparator) {
        if (comparator == null) {
            throw new NullPointerException("Provided comparator shall not be null");
        }
        this.comparator = comparator;
    }


    /**
     * Insert an element inside the tree.
     *
     * @param element element to insert.
     */
    public void insert(T element) {
        assertElementNotNull(element);
        if (root == null) {
            root = new Node<T>(null, element);
            return;
        }
        Node<T> current = root;
        while (true) {
            int cmp = comparator.compare(element, current.getData());
            if (cmp == 0) {
                current.setData(element);
            } else if (cmp < 0) {
                if (current.getLeft() == null) {
                    current.setLeft(new Node<>(current, element));
                    return;
                }
                current = current.getLeft();
            } else if (current.getRight() == null) {
                current.setRight(new Node<>(current, element));
                return;
            } else {
                current = current.getRight();
            }
        }
    }

    /**
     * Remove the provided element if it is actually present.
     *
     * @param element element to remove
     */
    public void remove(T element) {
        assertElementNotNull(element);
        remove(root, element);
    }

    /**
     * Throw an NPE is the provided element is null. This BinaryTree implementation does not support null as a valid
     * value.
     *
     * @param element element to check against.
     */
    protected void assertElementNotNull(T element) {
        if (element == null) {
            throw new NullPointerException("Provided element is null");
        }
    }

    /**
     * Auxilary function for removal of node. Replace the current node ot be deleted (node) with newChild.
     *
     * @param node     node to replace
     * @param newChild new node.
     */
    private void updateParentWithChild(Node<T> node, Node<T> newChild) {
        if (node.getParent() == null) {
            root = newChild;
            if (newChild != null) {
                newChild.setParent(null);
            }
            return;
        }
        if (newChild != null) {
            newChild.setParent(node.getParent());
        }
        if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(newChild);
        } else {
            node.getParent().setRight(newChild);
        }
    }

    /**
     * Remove the prvoided element from the tree represented by "node".
     *
     * @param node    tree
     * @param element element
     */
    private void remove(Node<T> node, T element) {
        // Nothing to remove: not found.
        if (node == null) {
            return;
        }
        int cmp = comparator.compare(element, node.getData());
        if (cmp == 0) {
            // We found our element to remove.
            if (node.getLeft() == null) {
                // If there no left tree, we can just remove the current node and replaced it with the right subtree.
                updateParentWithChild(node, node.getRight());
            } else if (node.getRight() == null) {
                // If there no right tree, we can just remove the current node and replaced it with the left subtree.
                updateParentWithChild(node, node.getLeft());
            } else {
                // If both nodes are present then we need to keep the current node. The strategy is to replace with
                // another node that we now have twice and to remove this one.
                // Good candidates for mon effort and keep order is to replace an element to the nearest on the left or
                // right.
                T minRight = min(node.getRight()).getData();
                node.setData(minRight);
                remove(node.getRight(), minRight);
            }
        } else {
            // We didn't find the element yet, depending search on the left or right part of the tree.
            remove(cmp < 0 ? node.getLeft() : node.getRight(), element);
        }
    }

    /**
     * Compute the smallest element in the provided Tree.
     */
    protected Node<T> min(Node<T> node) {
        if (node == null) {
            return null;
        }
        Node<T> current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    /**
     * Compute the biggest element in the provided Tree.
     */
    protected Node<T> max(Node<T> node) {
        if (node == null) {
            return null;
        }
        Node<T> current = node;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }

    /**
     * An iterator that go throuth the elements in order.
     */
    private class TreeIterator implements Iterator<T> {

        // The next element when we create our iterator is the smallest element in the tree.
        private Node<T> next = min(root);

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            T result = next.getData();
            // The next element of a node is next bigger in order:
            // - The smallest element on the right of current element if any
            // - The parent of current node itself if we come from the left.
            // - The smallest element of the parent excluding the left part otherwise (recursivity).
            if (next.getRight() != null) {
                next = min(next.getRight());
            } else {
                while (true) {
                    if (next.getParent() == null) {
                        next = null;
                        break;
                    } else if (next == next.getParent().getLeft()) {
                        next = next.getParent();
                        break;
                    } else {
                        next = next.getParent();
                    }
                }
            }
            return result;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeIterator();
    }


}