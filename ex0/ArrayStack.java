public class ArrayStack<T> implements MyStack<T> {

    private int capacity;
    private T[] stack;
    private int items;

    //initialize the constructor 
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        capacity = 10;
        stack = (T[])new Object[capacity];
        items = 0;
    }

    // Returns the number of items in the stack
    public int size() {
        return items;
    }

    // Returns a boolean indicating whether the stack has items
    public boolean isEmpty() {
        return items == 0;
    }

    // Adds an item into the stack
    public void push(T item) {
        capacityCheck();
        stack[items] = item;
        items++;
    }

    //check the stack capacity, double it when not enough
    @SuppressWarnings("unchecked")
    public void capacityCheck(){
        if(items + 1 <= capacity){
            return;
        }
        capacity *= 2;
        T[] newStatck = (T[]) new Object[capacity]; 
        for (int i = 0; i < items; i++){
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
        return stack[items - 1];
    }
    
    // Removes and returns the most recently added item from the stack
    // throws an IllegalStateException if the stack is empty
    public T pop(){
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        items--;
        T item = stack[items];
        return item;
    }


}
