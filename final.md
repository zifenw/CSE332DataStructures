# Priority Queue ADT & Binary Heaps

## Structure & Ordering Properties
- **Priority Queue ADT**: Abstract data type where elements have priorities. Supports `insert`, `findMin`, `deleteMin`, etc.
- **Binary Heap**: A complete binary tree that satisfies the heap order property:
  - **Min-Heap**: Parent ≤ children (smallest element at root).
  - **Max-Heap**: Parent ≥ children (largest element at root).
- **Perfect Tree**: A binary tree where all internal nodes have two children and all leaves are at the same level.
- **Complete Tree**: A binary tree where all levels are fully filled except possibly the last level, which is filled from left to right.

## Operations & Run-Times

| Operation      | Description | Run-Time |
|---------------|------------|----------|
| **Insertion** | Insert at last position & percolate up. | **O(1)** expected, **O(log n)** worst-case |
| **findMin** | Return root (min element in a min-heap). | **O(1)** |
| **deleteMin** | Remove root, move last element to root, percolate down. | **O(log n)** |
| **increaseKey** | Increase value, percolate down. | **O(log n)** |
| **decreaseKey** | Decrease value, percolate up. | **O(log n)** |
| **remove** | Set to extreme value, percolate up/down, then delete. | **O(log n)** |
| **buildHeap** | Bottom-up heap construction. | **O(n)** |

### Intuition for `O(1)` Expected Insertion
If elements are inserted in random order, most insertions occur near the leaves and require little percolation up.

### Intuition for `O(n)` BuildHeap
Instead of inserting `n` elements one by one (`O(n log n)`), we start from the last internal node and apply percolate-down, reducing the cost.

## Array Representation
- **Left Child** of `i` = `2i + 1`
- **Right Child** of `i` = `2i + 2`
- **Parent** of `i` = `(i - 1) / 2`

## D-Heaps (vs. Binary Heaps)
- In a **D-Heap**, each node has **D children** instead of 2.
- **Trade-offs**:
  - **Shallower tree** → Faster `deleteMin` (O(log_D(n)))
  - **Slower insertion** (more children to check when percolating up)
- When `D = 2`, it's just a binary heap.

---

# Dictionary ADT
Supports **insert, find, delete** operations. Implemented using:
- **Unordered list** (O(1) insert, O(n) find/delete)
- **Sorted array** (O(log n) find, O(n) insert/delete)
- **Binary Search Tree (BST)** (O(log n) expected)
- **Hash Tables** (O(1) expected for insert/find/delete)

---

# Binary Search Trees (BSTs)

## Properties
- **Binary property**: Each node has at most two children.
- **BST ordering property**: Left subtree ≤ Node ≤ Right subtree

## Tree Traversals
- **Inorder (LNR)** → Sorted order (useful for printing in sorted order).
- **Preorder (NLR)** → Root first (used in tree copying).
- **Postorder (LRN)** → Root last (used in deleting trees).

## Operations & Run-Times

| Operation | Description | Run-Time |
|-----------|------------|----------|
| **Find** | Recursively search for key. | **O(log n)** (avg), **O(n)** (worst) |
| **Insert** | Recursively place in the correct position. | **O(log n)** (avg), **O(n)** (worst) |
| **Delete** | Remove node, handle 0/1/2 children cases. | **O(log n)** (avg), **O(n)** (worst) |

### Worst-case `O(n)`
If BST degenerates into a linked list (happens if inserted in sorted order).

---

# AVL Trees (Self-Balancing BSTs)

## Properties
- **BST + Balance Property**:
  - Height difference between left & right subtree of any node ≤ 1.
- **Height bound**: `O(log n)`
  - Ensures operations stay efficient.

## Rotations in AVL Insertion
- **Single Rotation (RR or LL cases)**: One rotation fixes the balance.
- **Double Rotation (RL or LR cases)**: Two rotations required.

## Operations & Run-Times

| Operation | Run-Time |
|-----------|----------|
| **Find** | O(log n) |
| **Insert** | O(log n) (may require rotations) |

**AVL trees guarantee balanced height, preventing O(n) worst-case BST behavior.**


# **Hash Tables**
## **Basics of Good Hash Function Design**
- Uniform distribution
- Minimize collisions
- Fast computation
- Deterministic output

## **Collision Resolution Strategies**
### **Separate Chaining**
- Uses linked lists (or balanced BSTs) at each bucket
- Pros: Handles high load factors well
- Cons: Extra memory overhead for pointers

### **Open Addressing**
#### **Linear Probing**
- Look for the next available slot linearly (`(h(k) + i) % m`)
- Pros: Simple and cache-friendly
- Cons: Clustering can degrade performance

#### **Quadratic Probing**
- Uses a quadratic function to find empty slots (`(h(k) + i²) % m`)
- Pros: Reduces primary clustering
- Cons: Can still suffer from secondary clustering

#### **Double Hashing**
- Uses a second hash function for probing (`(h1(k) + i * h2(k)) % m`)
- Pros: Reduces clustering significantly
- Cons: Slightly slower due to additional hash computation

## **Load Factor (α)**
- Defined as `α = n/m` (entries/table size)
- Determines when rehashing is needed
- Lower load factor → fewer collisions, but higher memory use

## **Deletion in Hash Tables**
- Separate chaining: Remove from linked list
- Open addressing: Use a special **tombstone** marker to preserve search efficiency

## **Rehashing**
- Resizing the table (usually doubling) and re-inserting all elements

# **Sorting Algorithms**
Stable: 如果一个排序算法在排序过程中，不会改变相等元素的相对顺序，那么它是稳定的排序算法。

In-place: 在本array中排序，不需要新的辅助数组

non-comparison based: 非比较排序Radix Sort和Bucket Sort
## **Simple Sorts**
### **Insertion Sort**
- Builds a sorted array one element at a time
- Time Complexity: **O(n²)**
- Stable: **Yes**
- In-Place: **Yes**

### **Selection Sort**
- Repeatedly selects the smallest element
- Time Complexity: **O(n²)**
- Stable: **No**
- In-Place: **Yes**

## **Efficient Sorts**
### **Heap Sort**
- Uses a binary heap to sort elements
- Time Complexity: **O(n log n)**
- Stable: **No**
- In-Place: **Yes**

### **Merge Sort**
- Recursively divides the array and merges sorted subarrays
- Time Complexity: **O(n log n)**
- Stable: **Yes**
- In-Place: **No** (requires extra memory)

### **Quick Sort**
- Picks a pivot, partitions array around it
- Time Complexity: **O(n log n)** (average), **O(n²)** (worst-case)
- Stable: **No**
- In-Place: **Yes**

### **Non-Comparison Sorts**
#### **Bucket Sort**
- Distributes elements into buckets, then sorts each bucket
- Time Complexity: **O(n + k)**
- Stable: **Yes**
- In-Place: **No**

#### **Radix Sort**
- Sorts numbers digit by digit
- Time Complexity: **O(nk)** (where k is the digit count)
- Stable: **Yes**
- In-Place: **No**

## **Lower Bound for Comparison-Based Sorting**
- **Ω(n log n)** is the minimum for comparison-based sorting
- Uses **decision tree** model to prove this bound

# **Graphs**
## **Graph Basics**
- **Definition:** Set of vertices (V) and edges (E)
- **Weighted/Unweighted:** Edges may have weights
- **Directed/Undirected:** Direction matters in directed graphs
- **Degree:** Number of edges connected to a vertex
- **Paths & Cycles:** Routes through the graph
- **Connectedness:** Whether all vertices are reachable from any other vertex
- **DAGs (Directed Acyclic Graphs):** No cycles, useful for scheduling

## **Graph Representations**
### **Adjacency List**
- Stores lists of neighbors for each vertex
- Pros: Space-efficient for sparse graphs
- Cons: Slower lookups for adjacency queries

### **Adjacency Matrix**
- 2D matrix where `M[i][j] = 1` if edge exists
- Pros: Fast edge lookup
- Cons: Wastes space for sparse graphs

## **Graph Traversals**
### **Breadth-First Search (BFS)**
- Uses a queue (FIFO)
- Finds shortest paths in **unweighted graphs**
- Time Complexity: **O(V + E)**

### **Depth-First Search (DFS)**
- Uses a stack (or recursion)
- Time Complexity: **O(V + E)**

## **Graph Algorithms**
### **Dijkstra’s Algorithm**
- Finds shortest paths in weighted graphs
- Uses a priority queue
- Time Complexity: **O((V + E) log V)**

### **Topological Sort**
- Linear ordering of vertices in a **DAG**
- Uses DFS or Kahn's Algorithm

### **Minimum Spanning Trees**
#### **Prim’s Algorithm**
- Greedy approach using a priority queue
- Time Complexity: **O((V + E) log V)**

#### **Kruskal’s Algorithm**
- Greedy approach using edge sorting
- Uses **Disjoint Sets** for efficiency
- Time Complexity: **O(E log E)**

# **Parallelism**
## **Fork-Join Parallelism**
- **Work:** Total operations performed
- **Span:** Longest sequence of dependent tasks
- **Parallelism:** `Work / Span`

### **Fork-Join Applications**
- Parallel sum
- Reduce operations (min, max, multiply)
- Map operations (bit vector, string length)

## **Parallel Algorithms**
### **Parallel Prefix Sum**
- Used for scan operations
- Runs in **O(log n) time** with **O(n) processors**

## **Amdahl’s Law**
- Speedup limit based on serial vs parallel portion:
  \[
  S = \frac{1}{(1 - p) + \frac{p}{N}}
  \]
  where \( p \) is the parallel fraction, and \( N \) is the number of processors.

# **Concurrency**
## **Race Conditions & Data Races**
- **Race Condition:** Outcome depends on timing
- **Data Race:** Two threads access shared data unsafely

## **Synchronizing Code**
### **Locks**
- **Reentrant Locks:** Allow same thread to re-acquire
- **Granularity:** Coarse (large locks) vs fine (small locks)

### **Java’s Synchronized Statement**
- `synchronized` keyword ensures mutual exclusion

## **Deadlocks**
- Occurs when two threads hold locks and wait for each other

## **Java Threads & Locks (Pseudocode)**
```java
class SharedResource {
    private Lock lock = new ReentrantLock();
    
    void criticalSection() {
        lock.lock();
        try {
            // Critical section
        } finally {
            lock.unlock();
        }
    }
}