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