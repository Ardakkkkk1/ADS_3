// MyHashTable.java
import java.util.Objects;

/**
 * A simple hash table implementation using separate chaining.
 */
public class MyHashTable<K, V> {
    /**
     * A node in the linked list for each bucket.
     */
    private static class HashNode<K, V> {
        K key;
        V value;
        HashNode<K, V> next;

        HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    private HashNode<K, V>[] chainArray;  // array of bucket heads

    private int M;      // number of buckets
    private int size;   // total number of key–value pairs

    /**
     * Default constructor: creates 11 buckets.
     */
    public MyHashTable() {
        this(11);
    }

    /**
     * Construct a hash table with a given number of buckets.
     *
     * @param M number of buckets
     */
    public MyHashTable(int M) {
        this.M = M;
        // Cannot create generic array directly, so create raw and cast
        HashNode<K, V>[] temp = (HashNode<K, V>[]) new HashNode[M];
        this.chainArray = temp;
        this.size = 0;
    }

    /**
     * Compute the index for a given key.
     * Ensures a non-negative result in [0, M).
     */
    private int hash(K key) {
        return (Objects.requireNonNull(key).hashCode() & 0x7fffffff) % M;
    }

    /**
     * Insert a key–value pair into the table.
     * If the key already exists, its value is updated.
     */
    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> head = chainArray[index];

        // Check if key is already present
        for (HashNode<K, V> curr = head; curr != null; curr = curr.next) {
            if (curr.key.equals(key)) {
                curr.value = value;  // update existing value
                return;
            }
        }

        // Otherwise insert new node at head of list
        HashNode<K, V> node = new HashNode<>(key, value);
        node.next = head;
        chainArray[index] = node;
        size++;
    }

    /**
     * Retrieve the value associated with a given key.
     *
     * @return the value, or null if key not found
     */
    public V get(K key) {
        int index = hash(key);
        for (HashNode<K, V> curr = chainArray[index]; curr != null; curr = curr.next) {
            if (curr.key.equals(key)) {
                return curr.value;
            }
        }
        return null;
    }

    /**
     * Remove the entry for a given key.
     *
     * @return the removed value, or null if key not found
     */
    public V remove(K key) {
        int index = hash(key);
        HashNode<K, V> curr = chainArray[index];
        HashNode<K, V> prev = null;

        // Find the node to remove
        while (curr != null && !curr.key.equals(key)) {
            prev = curr;
            curr = curr.next;
        }
        if (curr == null) {
            return null;  // key not found
        }

        size--;
        if (prev != null) {
            prev.next = curr.next;
        } else {
            chainArray[index] = curr.next;
        }
        return curr.value;
    }

    /**
     * Check whether a given value exists in the table.
     *
     * @return true if at least one key maps to this value
     */
    public boolean contains(V value) {
        for (HashNode<K, V> head : chainArray) {
            for (HashNode<K, V> curr = head; curr != null; curr = curr.next) {
                if (curr.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Find any key that maps to the given value.
     *
     * @return a key, or null if not found
     */
    public K getKey(V value) {
        for (HashNode<K, V> head : chainArray) {
            for (HashNode<K, V> curr = head; curr != null; curr = curr.next) {
                if (curr.value.equals(value)) {
                    return curr.key;
                }
            }
        }
        return null;
    }

    /**
     * @return the number of key–value pairs stored
     */
    public int size() {
        return size;
    }

    /**
     * For testing: return an array of bucket sizes.
     */
    public int[] bucketSizes() {
        int[] counts = new int[M];
        for (int i = 0; i < M; i++) {
            int cnt = 0;
            for (HashNode<K, V> curr = chainArray[i]; curr != null; curr = curr.next) {
                cnt++;
            }
            counts[i] = cnt;
        }
        return counts;
    }
}

