public class LinkedQueue<E> implements MyQueue<E>{
    
    private static class ListNode<E>{
        private final E data;
        private ListNode<E> next;

        private ListNode(E data, ListNode<E> next){
            this.data = data;
            this.next = next;
        }

        private ListNode(E data){
            this.data = data;
        }
    }

    private ListNode<E> head;
    private ListNode<E> tail;
    private int size;

    public LinkedQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    // Return the number of items currently in the queue
    public int size(){
        return size;
    }

    // Returns a boolean to indicate whether the queue has items
    public boolean isEmpty(){
        return size == 0;
    }
    
    // Adds an item into the queue.
    public void enqueue(E item){
        ListNode<E> newNode = new ListNode<>(item);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // Returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public E peek(){
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return head.data;
    }

    // Removes and returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public E dequeue(){
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        E data = head.data;
        head = head.next;
        size--;
        if(isEmpty()){
            tail = null;
        }
        return data;
    }

}
