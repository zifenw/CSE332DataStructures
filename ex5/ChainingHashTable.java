import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChainingHashTable <K,V> implements DeletelessDictionary<K,V>{
    private List<Item<K,V>>[] table; // the table itself is an array of linked lists of items.
    private int size;
    private static int[] primes = {11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853};
    private static double LoadFactor = 0.7;

    @SuppressWarnings("unchecked")
    public ChainingHashTable(){
        table = (LinkedList<Item<K,V>>[]) Array.newInstance(LinkedList.class, primes[0]);
        for(int i = 0; i < table.length; i++){
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }    

    public int size(){
        return size;
    }

    // TODO
    public V insert(K key, V value){
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        int index = getIndex(key);
        for (Item<K, V> item : table[index]) {
            if (item.key.equals(key)) {
                V oldValue = item.value;
                item.value = value;
                return oldValue;
            }
        }

        table[index].add(new Item<>(key, value));
        size++;

        // Resize if the load factor is exceeded
        if ((double) size / table.length > LoadFactor) {
            resize();
        }

        return null;
    }

    // TODO
    public V find(K key){
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        int index = getIndex(key);
        for (Item<K, V> item : table[index]) {
            if (item.key.equals(key)) {
                return item.value;
            }
        }
        return null;
    }

    // TODO
    public boolean contains(K key){
        return find(key) != null;
    }

    // TODO
    public List<K> getKeys(){
        List<K> keys = new ArrayList<>();
        for (List<Item<K, V>> bucket : table) {
            for (Item<K, V> item : bucket) {
                keys.add(item.key);
            }
        }
        return keys;
    }

    // TODO
    public List<V> getValues(){
        List<V> values = new ArrayList<>();
        for (List<Item<K, V>> bucket : table) {
            for (Item<K, V> item : bucket) {
                values.add(item.value);
            }
        }
        return values;
    }

    public String toString(){
        String s = "{";
        s += table[0];
        for(int i = 1; i < table.length; i++){
            s += "," + table[i];
        }
        return s+"}";
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    // Resize the hash table when the load factor is exceeded
    private void resize() {
        int newSize = getNextPrime(table.length);
        @SuppressWarnings("unchecked")
        List<Item<K, V>>[] newTable = (LinkedList<Item<K, V>>[]) Array.newInstance(LinkedList.class, newSize);
        for (int i = 0; i < newTable.length; i++) {
            newTable[i] = new LinkedList<>();
        }

        // Rehash all items into the new table
        for (List<Item<K, V>> bucket : table) {
            for (Item<K, V> item : bucket) {
                int index = Math.abs(item.key.hashCode()) % newSize;
                newTable[index].add(item);
            }
        }

        table = newTable;
    }

    // Helper method to find the next prime number larger than the current size
    private int getNextPrime(int currentSize) {
        for (int prime : primes) {
            if (prime > currentSize) {
                return prime;
            }
        }
        // Fallback: return the next power of 2 plus 1
        return nextPowerOfTwoPlusOne(currentSize);
    }

    // Helper method to calculate the next power of 2 plus 1
    private int nextPowerOfTwoPlusOne(int currentSize) {
        int powerOfTwo = 1;
        while (powerOfTwo <= currentSize) {
            powerOfTwo <<= 1; // Multiply by 2
        }
        return powerOfTwo + 1;
    }
}
