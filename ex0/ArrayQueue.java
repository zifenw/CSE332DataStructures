public class ArrayQueue<T> implements MyQueue<T>{

    private int capacity;
    private T[] queue;
    private int size;
    private int front;
    private int back;

    @SuppressWarnings("unchecked")
    public ArrayQueue(){
        capacity = 10;
        queue = (T[]) new Object[capacity];
        size = 0;
        front = 0;
        back = 0;
    }

    // Returns a boolean to indicate whether the queue has items
    public boolean isEmpty(){
        return size == 0;
    }

    // Return the number of items currently in the queue
    public int size(){
        return size;
    }

    // Adds an item into the queue.
    public void enqueue(T item){
        capacityCheck();
        queue[back] = item;
        size++;
        back = (back + 1)% capacity;
    }

    @SuppressWarnings("unchecked")
    public void capacityCheck(){
        if(size + 1 <= capacity){
            return;
        }
        int newCapacity = capacity * 2;
        T[] newQueue = (T[]) new Object[newCapacity];
        for(int i = 0; i< size; i++){
            newQueue[i] = queue[(front + i) % capacity];     //Circular Array, move front to newQueue[0]
        }
        capacity = newCapacity;
        queue = newQueue;
        front = 0;
        back = size;
    }

    // Returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public T peek(){
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue[front];
    }

    // Removes and returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public T dequeue(){
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T item = queue[front];
        //queue[front] = null; // Help garbage collection
        front = (front + 1) % capacity;
        size--;
        return item;
    }
    
}
