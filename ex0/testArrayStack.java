public class testArrayStack<T> implements MyStack<T> {

    private int capacity;
    private T[] stack;
    private int size;

    //initialize the constructor 
    @SuppressWarnings("unchecked")
    public testArrayStack() {
        capacity = 10;
        stack = (T[])new Object[capacity];
        size = 0;
    }

    // Returns the number of items in the stack
    public int size() {
        return size;
    }

    // Returns a boolean indicating whether the stack has items
    public boolean isEmpty() {
        return size == 0;
    }

    // Adds an item into the stack
    public void push(T item) {
        capacityCheck();
        stack[size] = item;
        size++;
    }

    //check the stack capacity, double it when not enough
    @SuppressWarnings("unchecked")
    public void capacityCheck(){
        if(size + 1 <= capacity){
            return;
        }
        capacity += 5;
        T[] newStatck = (T[]) new Object[capacity]; 
        for (int i = 0; i < size; i++){
            newStatck[i] = stack[i];
        }
        stack = newStatck;
    }

    // Returns the most recently added item in the stack
    // throws an IllegalStateException if the stack is empty
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack[size - 1];
    }
    
    // Removes and returns the most recently added item from the stack
    // throws an IllegalStateException if the stack is empty
    public T pop(){
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        size--;
        T item = stack[size];
        return item;
    }


}
