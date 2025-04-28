import java.util.*;

/**
 * A simple generic Binary Search Tree (BST) implementation.
 * Supports insert (put), search (get), delete, and in-order iteration over keys.
 */
public class BST<K extends Comparable<K>, V> {
    // Node class for BST
    private class Node {
        K key;
        V val;
        Node left, right;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private Node root;

    /**
     * Insert a key-value pair into the BST.
     * If the key already exists, update its value.
     */
    public void put(K key, V val) {
        root = put(root, key, val);
    }

    // Recursive helper for put
    private Node put(Node x, K key, V val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)      x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        return x;
    }

    /**
     * Retrieve the value associated with the given key.
     * @return the value, or null if key not found
     */
    public V get(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)      x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }

    /**
     * Delete the node with the given key (if present).
     */
    public void delete(K key) {
        root = delete(root, key);
    }

    // Recursive helper for delete
    private Node delete(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = delete(x.left,  key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            // Found node to delete
            if (x.left  == null) return x.right;
            if (x.right == null) return x.left;
            // Two children: replace with successor
            Node t = x;
            Node min = min(t.right);
            x.key = min.key;
            x.val = min.val;
            x.right = deleteMin(t.right);
        }
        return x;
    }

    // Find minimum node in subtree
    private Node min(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    // Delete minimum node in subtree
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    /**
     * Return an iterable over the keys in ascending (in-order) order.
     */
    public Iterable<K> iterator() {
        List<K> keys = new ArrayList<>();
        inorder(root, keys);
        return keys;
    }

    // In-order traversal helper
    private void inorder(Node x, List<K> list) {
        if (x == null) return;
        inorder(x.left, list);
        list.add(x.key);
        inorder(x.right, list);
    }
}
