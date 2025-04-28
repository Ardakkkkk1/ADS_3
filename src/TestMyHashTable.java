// TestMyHashTable.java
import java.util.Random;

/**
 * Test driver for MyHashTable.
 * Inserts 10,000 random keys and prints bucket load distribution.
 */
public class TestMyHashTable {
    public static void main(String[] args) {
        int M = 37;  // number of buckets (tweak to see different distributions)
        MyHashTable<MyTestingClass, Integer> table = new MyHashTable<>(M);

        Random rnd = new Random();
        // Insert 10,000 random keys
        for (int i = 0; i < 10_000; i++) {
            MyTestingClass key = new MyTestingClass(rnd.nextInt());
            table.put(key, i);
        }

        // Print the size of each bucket
        int[] sizes = table.bucketSizes();
        System.out.println("Bucket load distribution (bucket : count):");
        for (int i = 0; i < sizes.length; i++) {
            System.out.printf(" %2d : %4d%n", i, sizes[i]);
        }
    }
}
