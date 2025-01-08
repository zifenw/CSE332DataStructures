public class LinkedStack<T> implements MyStack<T> {

    private static class ListNode<T> {
        private final T data;
        private ListNode<T> next;

        private ListNode(T data, ListNode<T> next) {
            this.data = data;
            this.next = next;
        }

        private ListNode(T data) {
            this.data = data;
        }
    }

    private ListNode<T> head;// Reference to the top of the stack (headnode of list)
    private int size;// Tracks the number of items in the stack

    public LinkedStack() {
        head = null;
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
        // Create a new node and make it the head
        ListNode<T> newNode = new ListNode<>(item, head);
        head = newNode;
        size++;
    }

    // Removes and returns the most recently added item from the stack
    // throws an IllegalStateException if the stack is empty
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T data = head.data;// Retrieve the data from the head node
        head = head.next;// Move the head reference to the next node
        size--;
        return data;
    }

    // Returns the most recently added item in the stack
    // throws an IllegalStateException if the stack is empty
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return head.data;
    }

}
