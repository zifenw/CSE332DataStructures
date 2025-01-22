import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BinaryMaxHeap <T extends Comparable<T>> implements MyPriorityQueue<T> {    
    private int size; // Maintains the size of the data structure
    private T[] arr; // The array containing all items in the data structure
                     // index 0 must be utilized
    private Map<T, Integer> itemToIndex; // Keeps track of which index of arr holds each item.

    @SuppressWarnings("unchecked")
    public BinaryMaxHeap(){
        // This line just creates an array of type T. We're doing it this way just
        // because of weird java generics stuff (that I frankly don't totally understand)
        // If you want to create a new array anywhere else (e.g. to resize) then
        // You should mimic this line. The second argument is the size of the new array.
        arr = (T[]) Array.newInstance(Comparable.class, 10);
        size = 0;
        itemToIndex = new HashMap<>();
    }
    // move the item at index i "rootward" until
    // the heap property holds
    private void percolateUp(int i){
        // (kids-1)/2 = parents
        while (i > 0 && arr[i].compareTo(arr[(i - 1) / 2]) > 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    // move the item at index i "leafward" until
    // the heap property holds
    private void percolateDown(int i){
        while (true) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            int biggest = i;

            if (leftChild < size && arr[leftChild] != null && arr[leftChild].compareTo(arr[biggest]) > 0) {
                biggest = leftChild;
            }
            if (rightChild < size && arr[rightChild] != null && arr[rightChild].compareTo(arr[biggest]) > 0) {
                biggest = rightChild;
            }
            if (biggest == i) {
                break;
            }
            swap(i, biggest);
            i = biggest;
        }
    }

    // copy all items into a larger array to make more room.
    @SuppressWarnings("unchecked")
    private void resize(){
        T[] larger = (T[]) Array.newInstance(Comparable.class, arr.length*2);
        // Copy elements manually using a for loop
        for (int i = 0; i < arr.length; i++) {
            larger[i] = arr[i];
        }
        // Update the array reference
        arr = larger;
    }

    public void insert(T item){
        if (size == arr.length){
            resize();
        }
        arr[size] = item;
        itemToIndex.put(item, size);
        percolateUp(size);
        size++;
    }

    public T extract(){
        if (size == 0) {
            throw new IllegalStateException("Heap is empty.");
        }
        T max = arr[0];
        swap(0, size - 1);
        size --;
        itemToIndex.remove(max);
        if (size > 1) {
            percolateDown(0);
        }
        return max;
    }

    // Remove the item at the given index.
    // Make sure to maintain the heap property!
    private T remove(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        T removedItem = arr[index];
        swap(index, size - 1);
        size --;
        itemToIndex.remove(removedItem);

        if (index < size) {
            updatePriority(index);
        }
        return removedItem;
    }


    // helper function
    // swap two value in maxheap and change the hashMap
    private void swap(int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        // Update the index map
        itemToIndex.put(arr[i], i);
        itemToIndex.put(arr[j], j);
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void remove(T item){
        remove(itemToIndex.get(item));
    }

    // Determine whether to percolate up/down
    // the item at the given index, then do it!
    private void updatePriority(int index){
        percolateUp(index);
        percolateDown(index);
    }

    // This method gets called after the client has 
    // changed an item in a way that may change its
    // priority. In this case, the client should call
    // updatePriority on that changed item so that 
    // the heap can restore the heap property.
    // Throws an IllegalArgumentException if the given
    // item is not an element of the priority queue.
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void updatePriority(T item){
        if(!itemToIndex.containsKey(item)){
            throw new IllegalArgumentException("Given item is not present in the priority queue!");
        }
        updatePriority(itemToIndex.get(item));
    }
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public boolean isEmpty(){
        return size == 0;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public int size(){
        return size;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public T peek(){
        if (size == 0) {
            throw new IllegalStateException("Heap is empty.");
        }
        return arr[0];
    }
    
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public List<T> toList(){
        List<T> copy = new ArrayList<>();
        for(int i = 0; i < size; i++){
            copy.add(i, arr[i]);
        }
        return copy;
    }

    // For debugging
    public String toString(){
        if(size == 0){
            return "[]";
        }
        String str = "[(" + arr[0] + " " + itemToIndex.get(arr[0]) + ")";
        for(int i = 1; i < size; i++ ){
            str += ",(" + arr[i] + " " + itemToIndex.get(arr[i]) + ")";
        }
        return str + "]";
    }
}
