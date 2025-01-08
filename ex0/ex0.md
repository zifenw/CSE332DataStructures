# Project Objective
```
实现LinkedList和ArrayList风格的Queues和Stacks
完善ArrayStack、ArrayQueue、LinkedStack和LinkedQueue。
```
## ArrayStack
```
整体注意事项：

array是 Java 中的原生数据结构,不需要 import
ArrayList 是 Java 提供的动态数组类,需要 importjava.util

该练习只能使用Array,不能使用ArrayList

public class ArrayStack<T> implements MyStack<T>
T 是一个 泛型参数（Generic Type Parameter），用于定义类、接口或方法中的通用类型。泛型使代码更加灵活且可复用，同时在编译时提供类型安全检查。
```
### private variable
```
    private int capacity;
    private T[] stacks;
    private int items;
```
### InitArray()
```
@SuppressWarnings("unchecked")
    public void InitArray() {
        capacity = 10;
      **stacks = (T[])new Object[capacity];**
        items = 0;
    }
``` 
### size(), isEmpty() 
```
    // Returns the number of items in the stack
    public int size() {
        return items;
    }

    // Returns a boolean indicating whether the stack has items
    public boolean isEmpty() {
        return items == 0;
    }
```
### push()
```
    // Adds an item into the stack
    public void push(T item) {
        capacityCheck();
        stacks[items] = item;
        items++;
    }

    public void capacityCheck(){
        if(items + 1 <= capacity){
            return;
        }
        capacity *= 2;
        T[] newStatcks = (T[]) new Object[capacity]; 
        for (int i = 0; i <= items; i++){
            newStatcks[i] = stacks[i];
        }
        stacks = newStatcks;
    }
```
### Change InitArray() to constructor
```
    //initialize the constructor 
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        capacity = 10;
        stack = (T[])new Object[capacity];
        items = 0;
    }

    I also change all items to size
```
## ArrayQueue
```
整体注意事项：
Circular Array Queue Data Structure
存在front and back
```
### private variable
```
    private int capacity;
    private T[] queue;
    private int size;
    private int front;
    private int back;
```
### Constructor
```
    @SuppressWarnings("unchecked")
    public ArrayQueue(){
        capacity = 10;
        queue = (T[]) new Object[capacity];
        size = 0;
        front = -1;
        back = 0;
    }
```
### Method add
```
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
```
## LinkedStack
```
**新建的node是headnode,出现在linkedList的最前方，也可以作为LinkedStack的top/last/back**
**不需要capacity check**

链表基础方法：
public class LinkedList<T> {

    // Define the ListNode class as an inner class
    private static class ListNode<T> {
        T data;
        ListNode<T> next;

        // Constructor for ListNode
        ListNode(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private ListNode<T> head;  // Head of the list
    private int size;          // Size of the list

    // Constructor to initialize the linked list
    public LinkedList() {
        head = null;
        size = 0;
    }

    // Method to add a node at the end of the list
    public void add(T data) {
        ListNode<T> newNode = new ListNode<>(data);
        if (head == null) {
            head = newNode;  // If the list is empty, the new node becomes the head
        } else {
            ListNode<T> current = head;
            // Traverse to the last node
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;  // Link the last node to the new node
        }
        size++;
    }

    // Method to remove the first node of the list
    public T remove() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        T data = head.data;
        head = head.next;  // Move the head pointer to the next node
        size--;
        return data;
    }

    // Method to check if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Method to get the size of the list
    public int size() {
        return size;
    }

    // Method to get the first element (head)
    public T peek() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        return head.data;
    }

    // Method to print all elements in the list
    public void printList() {
        if (head == null) {
            System.out.println("The list is empty");
            return;
        }
        ListNode<T> current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Method to find an element in the list
    public boolean contains(T data) {
        ListNode<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Method to get an element at a specific index
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        ListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
}
```
### 内部私有类 ListNode
```
    private static class ListNode<T> {
        private final T data;
        private ListNode<T> next;
data：存储节点中的实际数据（栈的元素）。
next：指向下一个节点的引用，用于链接链表中的其他节点。

        private ListNode(T data, ListNode<T> next) {
            this.data = data;
            this.next = next;
        }
这个构造函数接收两个参数：一个数据（data）和一个指向下一个节点的引用（next）。它用于创建一个新的节点并初始化 data 和 next 字段。
        private ListNode(T data) {
            this.data = data;
        }
    }
这个构造函数只接收一个数据参数（data），并默认将 next 设置为 null。它实际上是调用了第一个构造函数，并将 next 设置为 null。
```
### private variable和初始化
```
    private ListNode<T> head;// Reference to the top of the stack (headnode of list)
    private int size;// Tracks the number of items in the stack

    public LinkedStack() {
        head = null;
        size = 0;
    }
```
### methods
```
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
```
## LinkedQueue
```
Java 的命名习惯为不同的泛型参数赋予了特定的字母：

T（Type）：通常用于表示通用的类型参数，例如 ArrayList<T> 或 LinkedList<T> 中的 T。
E（Element）：当泛型参数表示集合中的 元素 时，通常使用 E，例如 Queue<E> 或 Set<E>。
其他常见字母：
K（Key） 和 V（Value）：用于键值对，例如 Map<K, V>。
N（Number）：用于表示数字类型。

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
```

