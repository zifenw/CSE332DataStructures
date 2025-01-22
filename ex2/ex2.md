### The objectives of this exercise are: 
- Understand the binary heap data structure by implementing it
- See how to modify the binary heap data structure to add new operations efficiently
- Apply the binary heap data structure to create a new data structure

### Overview
This exercise consists of the following parts:
1. Build a binary min heap
- In this section we will provide descriptions of methods you must implement for a binary min heap data structure, as well as the requirements for that implementation
2. Build a binary max heap
- After building your binary min heap you will modify that implementation in order to implement a binary max heap
3. Chess Leaderboard
- You will use your binary min heap and binary max heap implementations to create a new data structure that we’re calling a TopKHeap. We’ll use this as a data structure for tracking the best chess players of all time!
### Motivation: Chess Ranking
如果我们只想跟踪最好的棋手，堆可能是一个不错的选择。如果我们想对所有国际象棋选手进行完美排名，我们可能想保持一个排序列表。在本作业中，我们将创建一个数据结构，使我们能够有效地维护我们选择的任何k个前k个玩家。我们将把这个数据结构称为TopKHeap。
### Implementation Guidelines

Your implementations in this assignment must follow these guidelines
- You may not add any import statements beyond those that are already present. This course is about learning the mechanics of various data structures so that we know how to use them effectively, choose among them, and modify them as needed. Java has built-in implementations of many data structures that we will discuss, in general you should not use them.
- Your Heap implementations must store all items in arrays. These arrays should use index 0 for storing items. You should only create a list for the toList method. 
- Do not have any package declarations in the code that you submit. The Gradescope autograder may not be able to run your code if it does.
- Remove all print statements from your code before submitting. These could interfere with the Gradescope autograder (mostly because printing consumes a lot of computing time).
- Your code will be evaluated by the gradescope autograder to assess correctness. It will be evaluated by a human to verify running time. Please write your code to be readable. This means adding comments to your methods and removing unused code (including “commented out” code).
- For the sake of running times, you may consider all HashMap operations to run in constant time.

### Provided Code
Several java classes have been provided for you in this zip file. Here is a description of each.
- MyPriorityQueue
  - A priority queue interface. It is called MyPriorityQueue because java already has a built-in interface called PriorityQueue, and so this avoids any kind of naming issues.
  - Do not submit this file
- `BinaryMinHeap`
  - You will create a Binary Min Heap data structure which implements the MyPriorityQueue interface. Each method should satisfy the requirements described in the spec and the comments in the MyPriorityQueue interface.
  - You will submit this file to Gradescope
- `BinaryMaxHeap`
  - You will create a Binary Max Heap data structure which implements the MyPriorityQueue interface. It will be equivalent to BinaryMinHeap, except that it’s a max heap!
  - You will submit this file to Gradescope
- `TopKHeap`
  - This data structure makes it efficient to insert items, update the priorities of items, and find the top-k items (where k is a parameter given in the constructor). This will be used for the “Chess Players” problem.
  - You will submit this file to Gradescope
- ChessPlayer
  - For testing purposes, we will be inserting these ChessPlayer objects into the data structures above. Each object has a String representing the name and an int representing that player’s Elo score. We have implemented a compareTo method which orders according to Elo score. We have also implemented an equals method which considers two ChessPlayers to be equal if they have both the same name and Elo score. There is a hashCode method implemented for efficient use of HashMaps.
  - Do not submit this file
- Client
  - This class performs some basic testing of some methods within the above classes. The gradescope autograder will do more precise testing than this.
  - Do not submit this file

## Part 1: BinaryMinHeap
The TopKHeap data structure that we create will make use of binary heaps like we discussed in class. To get started, implement a binary min heap data structure as described here.

The BinaryMinHeap data structure uses java generics to store any classes which implement the Comparable interface, guaranteeing that they have a compareTo method. Recall that to use the compareTo method you invoke it as an instance method of an object, and provide a second object as an argument. The result is then an integer which indicates which object is “greater”. In particular, if we call thingA.compareTo(thingB) we expect the result to be a negative integer if thingA < thingB, it should be a positive integer if thingA > thingB, and it should be 0 if thingA == thingB. To keep it straight in my head, I remember that  thingA.compareTo(thingB) gives the sign of thingA - thingB.

In lecture we discussed that we can have a value distinct from its priority in a heap. For this data structure we will instead use the value itself as the priority using the compareTo method. There are some additional methods beyond those described in the ADT from class, however we have discussed at least the idea (and in some cases pseudocode) or all of them! See the method comments in MyPriorityQueue for the expected behavior and required running times. There are also headings for some recommended private helper methods in BinaryMinHeap (and comments describing the intended behavior). You don’t have to use them, but I think it will make your life easier to do so!
### compareTo
```java
public interface Comparable<T> {
    int compareTo(T o);
}
```
Positive integer: If `this` is greater than `o`.  
Zero: If `this` is equal to `o`.  
Negative integer: If `this` is less than `o`.  
```java
// move the item at index i "rootward" until
// the heap property holds
        //0 1 2 3 4 5 6 7 8 9
        //      0        
        //   1     2     (kids-1)/2 = parents
        // 3  4   5  6   parents*2 +2 = rightkids
        //78 9           parents*2 +1 = leftkids
    // move the item at index i "leafward" until
    // the heap property holds
    private void percolateDown(int i){
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;
        int smallest = i;
        // check whether have a leftchild and compare it with parent
        if (leftChild < size && arr[leftChild].compareTo(arr[smallest]) < 0) {
            smallest = leftChild;
        }
        // check whether have a rightchild and compare it with smallest num
        if (rightChild < size && arr[rightChild].compareTo(arr[smallest]) < 0) {
            smallest = rightChild;
        }
        if (smallest != i) {
            T temp = arr[i];
            arr[i] = arr[smallest];
            arr[smallest] = temp;
            i = smallest;
        }
    }
```
## System.arraycopy
System.arraycopy is a native method in Java used to efficiently copy elements from one array to another. Here's how it works:
```java
System.arraycopy(sourceArray, sourcePos, targetArray, targetPos, length);
```
sourceArray: The array to copy from.  
sourcePos: Starting position in the source array.  
targetArray: The array to copy into.  
targetPos: Starting position in the target array.  
length: Number of elements to copy.  
```java
    // copy all items into a larger array to make more room.
    private void resize(){
        T[] larger = (T[]) Array.newInstance(Comparable.class, arr.length*2);
            // Copy elements manually using a for loop
        for (int i = 0; i < arr.length; i++) {
            larger[i] = arr[i];
        }
        // Update the array reference
        arr = larger;
    }
```
Ctrl + Z
Ctrl + Shift + Z

## Part 2: BinaryMaxHeap
The TopKHeap data structure will actually be using BOTH a max heap and a min heap. Modify your BinaryMinHeap implementation to additionally implement a BinaryMaxHeap. You’ll see that almost all of the methods will be identical for the max heap. If you would prefer, you’re welcome to refactor your implementations to have a shared abstract class for those identical methods. This is not required, though. You’d be welcome to just copy-paste those implementations. If you do use an abstract class, make sure you submit it to Gradescope!



## Part 3: TopKHeap
Now the moment we’ve been waiting for, the TopKHeap! In this data structure we make use of a max heap and a min heap to maintain a collection of the top k chess players. Implement all methods provided. See the method comments for the expected behavior and required running times.


## Bug 
[Running] cd "c:\Users\Wang\Desktop\CSE332DataStructures\ex2\" && javac Client.java && java Client
Exception in thread "main" java.lang.NullPointerException: Cannot read field "elo" because "<parameter1>" is null
	at ChessPlayer.compareTo(ChessPlayer.java:16)
	at ChessPlayer.compareTo(ChessPlayer.java:1)
	at BinaryMinHeap.percolateDown(BinaryMinHeap.java:42)
	at BinaryMinHeap.extract(BinaryMinHeap.java:88)
	at Client.minHeapTester(Client.java:26)     
	at Client.main(Client.java:6)

at Client.minHeapTester(Client.java:26):
cp2 = pq.extract();
extract()
```java
    public T extract(){
        if (size == 0) {
            throw new IllegalStateException("Heap is empty.");
        }
        T min = arr[0];
        swap(0, size);  <—— size - 1
        size --;
        itemToIndex.remove(min);
        if (size > 1) {
            percolateDown(0);       // percolateDown(0);
        }
        return min;
    }
```
如果 arr[smallest] 不是 null，但依然抛出了 NullPointerException，问题可能出在堆的逻辑实现中。具体来说，可能存在以下原因：  

可能问题分析  
1. itemToIndex 和数组同步性问题  
如果 itemToIndex 映射的索引和 arr 数组中的元素不一致，就可能会导致错误的比较或操作。例如：  
- itemToIndex 中保存的索引值指向一个已经被删除或覆盖的元素。  
- 堆操作（如删除或替换元素）时，没有正确更新 itemToIndex。  

2. 堆操作边界检查问题  
在执行 percolateUp 或 percolateDown 时，如果操作索引超出了有效范围，可能会导致访问到未初始化的数组位置，从而间接触发 NullPointerException。  

3. 逻辑错误导致未初始化的对象被插入到堆中  
如果某些逻辑中插入了未正确初始化的对象（如 new ChessPlayer(null, 0)），可能会导致比较逻辑访问到空字段。  
