import java.util.*;

/**
 * A generic Binary Search Tree (BST) with support for size tracking,
 * in-order traversal, and iteration over key-value entries.
 */
public class BST<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {
    // Node class
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
    private int size = 0;

    /**
     * An entry in the BST containing both key and value.
     */
    public static class Entry<K, V> {
        private final K key;
        private final V value;
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() { return key; }
        public V getValue() { return value; }
    }

    /**
     * @return the number of key-value pairs in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Insert or update a key-value pair.
     */
    public void put(K key, V val) {
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) {
            size++;
            return new Node(key, val);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0)      x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        return x;
    }

    /**
     * Retrieve the value for a given key.
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
     * Delete the node with the specified key, if present.
     */
    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)      x.left  = delete(x.left,  key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            size--;
            if (x.left == null)  return x.right;
            if (x.right == null) return x.left;
            Node t = x;
            Node min = min(t.right);
            x.key = min.key;
            x.val = min.val;
            x.right = deleteMin(t.right);
        }
        return x;
    }

    private Node min(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    /**
     * Return an iterator over the entries (key-value pairs) in sorted order.
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        List<Entry<K, V>> list = new ArrayList<>();
        inorder(root, list);
        return list.iterator();
    }

    private void inorder(Node x, List<Entry<K, V>> list) {
        if (x == null) return;
        inorder(x.left, list);
        list.add(new Entry<>(x.key, x.val));
        inorder(x.right, list);
    }
}
