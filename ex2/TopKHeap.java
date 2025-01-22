import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class TopKHeap<T extends Comparable<T>> {
    private BinaryMinHeap<T> topK; // Holds the top k items
    private BinaryMaxHeap<T> rest; // Holds all items other than the top k
    private int size; // Maintains the size of the data structure
    private final int k; // The value of k
    private Map<T, MyPriorityQueue<T>> itemToHeap; // Keeps track of which heap contains each item.
    
    // Creates a topKHeap for the given choice of k.
    public TopKHeap(int k){
        topK = new BinaryMinHeap<>();
        rest = new BinaryMaxHeap<>();
        size = 0;
        this.k = k;
        itemToHeap = new HashMap<>();
    }

    // Returns a list containing exactly the
    // largest k items. The list is not necessarily
    // sorted. If the size is less than or equal to
    // k then the list will contain all items.
    // The running time of this method should be O(k).
    public List<T> topK(){
        return topK.toList();
    }

    // Add the given item into the data structure.
    // The running time of this method should be O(log(n)+log(k)).
    public void insert(T item){
        if (itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("Item already exists in the heap.");
        }
        if (topK.size() < k) {
            // Add to topK if it's not full
            topK.insert(item);
            itemToHeap.put(item, topK);
        } else {
            // Compare with the smallest item in topK
            T smallestTopK = topK.peek();
            if (item.compareTo(smallestTopK) > 0) {
                // Move smallestTopK to rest and add item to topK
                topK.extract();
                rest.insert(smallestTopK);
                itemToHeap.put(smallestTopK, rest);

                topK.insert(item);
                itemToHeap.put(item, topK);
            } else {
                // Add to rest if it's not in the top k
                rest.insert(item);
                itemToHeap.put(item, rest);
            }
        }
        size++;
    }

    // Indicates whether the given item is among the 
    // top k items. Should return false if the item
    // is not present in the data structure at all.
    // The running time of this method should be O(1).
    // We have provided a suggested implementation,
    // but you're welcome to do something different!
    public boolean isTopK(T item){
        return itemToHeap.get(item) == topK;
    }

    // To be used whenever an item's priority has changed.
    // The input is a reference to the items whose priority
    // has changed. This operation will then rearrange
    // the items in the data structure to ensure it
    // operates correctly.
    // Throws an IllegalArgumentException if the item is
    // not a member of the heap.
    // The running time of this method should be O(log(n)+log(k)).
    public void updatePriority(T item){
        if (!itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("Item not found in the heap.");
        }

        // Remove the item from its current heap
        MyPriorityQueue<T> heap = itemToHeap.get(item);
        heap.remove(item);
        itemToHeap.remove(item);
        size--;

        // Reinsert the item to ensure correct placement
        insert(item);
    }

    // Removes the given item from the data structure
    // throws an IllegalArgumentException if the item
    // is not present.
    // The running time of this method should be O(log(n)+log(k)).
    public void remove(T item){
        if (!itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("Item not found in the heap.");
        }
        // Remove the item from its respective heap
        MyPriorityQueue<T> heap = itemToHeap.get(item);
        heap.remove(item);
        itemToHeap.remove(item);
        size--;

        // If the item was in topK, rebalance the heaps if necessary
        if (heap == topK && topK.size() < k && !rest.isEmpty()) {
            // Move the maximum item from rest to topK
            T maxRest = rest.extract();
            topK.insert(maxRest);
            itemToHeap.put(maxRest, topK);
        }
    
    }
}
