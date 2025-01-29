## Exercise 4 Spec (25wi)
### Objectives
- 通过将AVL树实现为二叉搜索树的子类（从而尽可能地继承方法）来识别二叉搜索树木和AVL树之间的关系。
- 通过实现AVLTree数据结构来熟悉它。特别是：了解需要维护哪些信息来保持结构属性（子树的高度相差0或1），使用这些信息正确识别以执行旋转操作。
- 通过使用有序字典数据结构（如二叉搜索树）来实现新的数据结构，突出使用有序字典的优点

### Overview
This exercise consists of the following parts:  
- 通过执行插入操作完成AVL树数据结构
- 写操作findNextKey和findPrevKey，它们利用了二叉搜索树的顺序，在下一部分中起着重要作用。
- 完成一个RangeTree数据结构，用于维护一组不重叠的实数范围。
### Motivation: Event Scheduling
当我们完成这项任务时，我们将完成三个数据结构：二叉搜索树、AVL树和范围树。最后一个数据结构，范围树，将用于存储具有数据结构中没有范围可以重叠的属性的数字范围。这种数据结构可用于多个应用程序，但现在让我们只关注一个事件调度。  

假设我们负责出租一个活动空间。这个活动空间一次最多只能举办一个活动，因此在填写日程时，我们需要避免预订两个重叠的活动。我们的活动将由开始时间和结束时间表示。如果一个范围的开始时间落在另一个的开始时间和结束时间之间，则两个范围会发生冲突（在这里停下来说服自己没有其他情况可能是值得的）。范围树数据结构将允许我们有效地检查潜在事件是否与任何已预订的事件冲突，如果没有，则将该事件添加到数据结构中。

我们将通过使用二叉搜索树的排序特性来实现这种数据结构的效率。二分查找树的顺序属性（键左侧的所有项目都有较小的键，右侧的所有项目有较大的键）将提供一种简单的方法来检查事件之间的冲突。

In case you’re interested, here are some other applications of this data structure:
- Scheduling jobs on a cloud computer
- Collision detection in graphics/physics engines (this would likely require a 2d or 3d analog to this data structure, I encourage you to think through what that might look like after completing the assignment!)
- Union of ranges
  - Instead of maintaining a collection of disjoint ranges and refusing to insert conflicting ranges, we could merge together overlapping ranges in our data structure. This could be useful for something like calculating line-of-sight in graphics.

## Provided Code
Several java classes have been provided for you in this zip file. Here is a description of each.

1. OrderedDeletelessDictionary  
A dictionary interface. 这与课堂上描述的字典ADT有以下不同之处： 
- 它没有删除操作（因此被称为“deletes”字典）。这意味着一旦将键值对添加到字典中，以后就无法删除该键。Insert仍应更新以前添加的键的值。
- 它需要操作findNextKey和findPrevKey，这两个操作将一个键作为输入，然后分别返回字典中大于该输入的最小键或小于该输入的最大键。这些操作是我们称之为“有序”词典的原因。
- 它需要操作getKeys和getValues，它们返回字典中的键或值的列表。这些列表都将根据它们的键进行排序，这意味着getKeys的索引0包含最小的键，getValues的索引0则包含与最小键关联的值。


2. `BinarySearchTree`  
- 当你下载zip时，这将是一个几乎完整的二分查找树的实现。我们已经实现了满足OrderedDeletlessDictionary接口所需的所有方法，除了findNextKey和findPrevKey，您将在本练习中实现它们。
- 为了帮助您进行调试，我们提供了一个名为printSideways的方法，用于显示树的结构。树的根将位于最左侧的列中，其子节点位于列之后，子节点位于该列之后，以此类推。

3. `AVLTree`
- 完成此练习后，此类将表示AVL树数据结构的实现（减去删除操作）。它继承自BinarySearchTree，因为可以共享许多方法。特别是，由于AVL树与二分查找树具有相同的顺序属性，因此可以共享所有只读方法。只有修改树的方法才需要被覆盖。对于本练习，唯一这样的方法是insert，您将实现它（以及您选择的任何辅助方法）。

4. TreeNode  
- 这是AVLTree和BinarySearchTree都使用的节点类。它有用于节点的键、值、左子节点、右子节点和高度的字段.BinarySearchTree不使用任何高度字段，但它仍然正确地维护了它（这可能有助于在编写AVLTree的插入方法时参考）。
- 有一个名为updateHeight的方法，它将使用节点的子节点高度来正确更新自己的高度。它假设孩子的身高是正确的，所以在调用该方法之前，由您来满足这个假设！

5. Range  
- 这是一个表示RangeTree数据结构事件的对象。它包含一个开始字段、一个结束字段（均为双精度）和一个范围字符串。如果开始不严格小于结束，构造函数会抛出IllegalArgumentException。

6. `RangeTree`  
- 此类将是RangeTree数据结构的实现。您必须根据注释中的描述完成hasConflict并插入方法。

## Part 1: AVLTree
为了获得OrderedDeletelessDictionary的良好最坏情况性能，我们将实现AVL树数据结构。接口可能修改树的唯一方法是插入操作，因此这是我们将在AVLTree中覆盖的唯一方法（其余方法将从BinarySearchTree继承）。这部分的唯一任务是编写这个插入方法。此插入方法的运行时间应为O（log n）

回想一下，在插入AVL树时，一般过程是：
- 像使用find一样导航树，直到找到键（在这种情况下，您更新值并返回旧值），或者到达树中的一个空点（在这种情形下，您将新的键值对作为新的叶子节点添加到该点并返回null）。
- 如果添加了节点，请根据需要更新祖先节点的高度，检查是否有任何节点变得不平衡。
- 如果节点变得不平衡，请执行必要的旋转以纠正不平衡（确保在旋转后更新节点的高度！）

要实现此过程，您可能需要尝试以下两种方法之一：

1. 递归方法：
- 对于这种方法，您将采用您在CSE123或CSE143中学到的“x=change（x）”模式，前提是您在华盛顿大学修过这些课程。这个想法是，你会有一个递归的辅助方法，它接受键、值和树节点的参数。此辅助方法将执行插入到以给定节点为根的子树中，然后在插入完成后返回该子树的新根（因为如果需要旋转，这可能会改变）。
- 要做到这一点，您可能会有一个基本情况，即当前根节点何时与我们正在寻找的键匹配（这意味着您将更新值），以及另一个情况，即当当前根为空时（这意味着您将创建一个新节点）。
- 在递归的情况下，您需要确定当前根的哪个子树应该包含给定的键（如果键小于根，则向左）。
- 递归插入完成后，检查返回的节点是否平衡。如果没有，则识别并执行适当的旋转，然后返回正确的根（旋转可能已更改）。
2. 迭代法

- 对于这种方法，您将像查找一样在树中导航。对于沿途访问的每个节点，将该节点推到堆栈上（这样您就可以沿着路径向后移动，以便稍后检查平衡）。如果你选择这种方法，欢迎你导入一个专门用于这种方式的堆栈数据结构（例如java.util.stack、java.util.ArrayList或java.util.LinkedList）。
- 如果你找到了你要找的密钥，你将更新该值并返回旧值。
如果你创建一个新的叶子，然后一次创建一个节点，从堆栈中弹出一个节点、更新该节点的高度、检查平衡。如果节点不平衡，则执行必要的旋转。

### TreeNode
```java
public class TreeNode<K,V> {
    K key;
    V value;
    public int height;
    public TreeNode<K,V> left;
    public TreeNode<K,V> right;

    public TreeNode(K key, V value){
        this.key = key;
        this.value = value;
        height = 0;
        this.left = null;
        this.right = null;
    }

    public TreeNode(K key, V value, TreeNode<K,V> left, TreeNode<K,V> right){
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
        updateHeight();
    }

    // Recalculates the height of this node.
    // Assumes that the height of both child nodes
    // are correct.
    public void updateHeight(){
        int leftHeight = left==null ? -1 : left.height;
        int rightHeight = right==null ? -1 : right.height;
        height = Math.max(leftHeight, rightHeight)+1;
    }

    public String toString(){
        return "[" + "key: " + key + ", value: " + value + ", height: " + height + "]";
    }
    
}

```
### helper function rotate
```java
    private TreeNode<K, V> rotateLeft(TreeNode<K, V> root) {
        TreeNode<K, V> temp = root.right;
        root.right = temp.left;
        temp.left = root;
        root.updateHeight();  // need this to find the new hight for temp
        temp.updateHeight();
        root = temp;
        return root;
    }
/*
root:
    a
 x     b
      y   z 
temp:
    b
 y     z 

root:
    a
 x     y
temp: 
      b
   a     z
x   y
*/    

```
## Part 2: findNextKey and findPrevKey
因为我们的字典是有序的（这意味着我们根据键在数据结构中的出现位置获得了关于键之间比较的信息），所以我们有额外的机会实现利用这种顺序的方法。在本部分中，您将为BinarySearchTree实现两个新的查找操作——findNextKey和findPrevKey。

这些操作类似于find，因为您将使用其顺序属性导航二分查找树。不过，它们与find不同，因为我们将返回一个键而不是一个值。

对于findNextKey，我们希望返回比给定键大的最小键。换句话说，我们希望从当前数据结构中的给定键中返回“下一个键向上”。如果给定的键恰好在数据结构中，那么我们仍然会返回一个严格大于给定键的键（即该键的后继键）。如果给定的键大于或等于数据结构中的最大键，则返回null。

对于findPrevKey，我们也这样做，但所有比较都是相反的。也就是说，我们希望返回比给定密钥更小的最大密钥。如果给定的键小于或等于数据结构中最小的键，那么我们将返回null。

如果findNextKey和findPrevKey中的树为空，则返回null。

这两个操作的运行时间在二叉搜索树的高度上应该是线性的（因此对于AVL树来说，树的大小将是对数的）。
## Part 3: RangeTree
Now the moment we’ve been waiting for, the RangeTree! In this data structure we make use of our OrderedDeletelessDictionary implementations. Implement the hasConflict and insert methods according to the descriptions in the method comments. 现在，我们一直在等待的那一刻，RangeTree！在这个数据结构中，我们使用了OrderedDeletelessDictionary实现。根据方法注释中的描述实现hasConflict和插入方法。