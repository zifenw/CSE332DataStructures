## Project Objective
```
实现LinkedList和ArrayList风格的Queues和Stacks
完善ArrayStack、ArrayQueue、LinkedStack和LinkedQueue。
```
## ArrayStack、ArrayQueue
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