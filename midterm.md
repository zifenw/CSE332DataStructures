# Midterm Review
## runing time
- most efficiant finding method for sorted array: Binary Search O(logn)
- To Convert the Binary Search Tree into a sorted array: Left-Rood-Right In-order traversal O(n)
- Floyd's build heap build min heap tree worse case: O(n)
- 累加insert to min heap, 不需要percolateUp, O(n); normally should be O(logn)
- if (n < 10000) {...} 我们可以确定run time 不会超过某个值所以O(1)
- O: Upperbound Ω: Lowwerbound Θ: Tiebound

## recurrence
- **Use variables appropriately for constants (e.g. c1, c2, etc.)**

## Tree
- The minimum number of nodes N(h) required for an AVL tree of height h is given by:
$$N(h)=N(h-1)+N(h-2)+1$$ 
  - N(0)=1; N(1)=2; N(2)=4; N(3)=7; N(4)=12; N(5)=20; N(6)=33 ...
- height – number of edges in path from node to deepest descendent  
- depth – number of edges in path from node to root  
- degree(B): The degree of a node in a tree is the number of children it has.  
- Min Heap: (i) Every node is less than all of its children (ii) The tree is complete   
- A BINARY min-heap that contains the elements 1,2,3,...,100. Which depth(s) of the heap can the value 4 be found in: The value 4 can occur at depths 1, 2, and 3.

## sort
Insertion Sort: 在array中，从i=0开始查看是否需要向前swap(如果小于前一个数就要swap)...  
Selection Sort: 从array中找到最小的值然后与第一位交换，再从剩余找最小值然后与第二位交换...  
Heap Sort：创建Min Heap O(N)，然后deleteMin到另一个数组  
Merge Sort归并排序：有Divide和Merge两个步骤，使用recursive，在merge中使用两个pointer，需要大量辅助数组   
Quick Sort：有Divide和Conquer两个步骤，找到privot，使用两个pointer将array分为小于privot和大于privot，recursive  
Radix Sort: 创造一个0~9的array，根据位数大小进行sort  
Bucket Sort: 所有值在1到B之间，创建一个size为B的array，然后每输入一个值放入对应的桶中。  


Stable: 如果一个排序算法在排序过程中，不会改变相等元素的相对顺序，那么它是稳定的排序算法。
In-place: 在本array中排序，不需要新的辅助数组
non-comparison based: 非比较排序Radix Sort和Bucket Sort

❌ 不稳定：
快速排序（Quick Sort）
堆排序（Heap Sort）
选择排序（Selection Sort）

### 案例1
我正试图制定一项广告策略，以最大限度地提高西雅图水手队棒球队的门票销售。为了帮助我，我使用了一个机器学习模型，其中输入是广告策略（例如，在电视、广播、广告牌等上购买多少广告，以及在哪里分发），输出是整个赛季门票销售的预期变化。我可能有点过头了，因为现在我有1000多万种不同的策略可以比较！
我知道，最好的策略将导致门票销售额增加40万张，最昂贵的策略将花费80万美元。
为了帮助我选择最好的一个，我想有两个策略列表——一个按成本排序，另一个按门票销售增长排序

1000多万base
知道array的最值？好用quick sort？
按成本和门票销售对1000万个广告策略进行排序，Radix sort是最佳选择。它以每位数O（n）的时间运行，使其比大型数据集的快速排序或堆排序等O（n log n）算法快得多。由于成本（80万美元）和门票销售额（40万美元）都是固定位数的整数，Radix Sort可以在几次操作中有效地处理它们。它的线性可扩展性确保了1000万个条目的最佳性能，而基于比较的排序，如插入排序（O（n²））是不切实际的。Radix Sort对整数键的适用性和速度使其成为理想的解决方案。

### 案例2
我设计T恤是一种爱好。每次我做一个设计，我只在网上卖一周。因为我不想从中致富，所以我不会事先为我的衬衫定价。相反，我要求人们至少支付衬衫的印刷和运输费用，但随后允许客户自愿支付额外金额，以表达他们的感激之情，并帮助我更舒适地支付账单。
然而，问题是上周的设计最终非常受欢迎，我收到了30多万件购买！我还需要一段时间才能真正制作出足够的衬衫来满足所有这些要求，所以我决定根据谁为他们的衬衫支付了最多的钱来发送它们。如果两个客户支付了相同的金额，那么我会把货物寄给第一个提交请求的客户。
目前，这些请求是按照提交的时间顺序列出的，因此我需要将它们重新排序到正确的发货顺序中。

30多万base
需要stable
为了高效地对客户订单进行排序，同时保持其原始提交订单的等额付款，合并排序是最佳选择。由于订单最初是按时间顺序排列的，我们需要一个稳定的排序算法来确保支付相同金额的客户保留其原始顺序。堆排序和快速排序是不稳定的，这意味着它们可能会错误地重新排序相等的元素。插入排序是稳定的，但对于300000个条目来说太慢了（O（n^2））。合并排序在O（nlogn）时间内运行并且是稳定的，尽管其空间使用量为O（n），但它仍是最佳选择。因此，合并排序是最好的选择。

### 案例3
我正在为西雅图艺术博物馆的虚拟现实展览制作一个雕塑的3D渲染。目前，我正试图决定使用哪种照明配置。
每个照明配置对象本身都占用了相当多的内存，我正在考虑使用相当多的照明配置。我有一个比较两种配置的过程，看看哪种配置更好，但这需要大量的计算。这意味着我真的想保持这些比较的数量很小。

必须比较？不能是non-comparison based？


### 案例4
我正在开发一个数据库，它将维护一个不断排序的对象列表。随着我们从用户那里收到更多项目，这个列表将会增长。快速更新列表并始终按顺序排序至关重要。如果我们快速连续地得到一批新对象，我们必须在得到第一个对象后立即开始排序，我们不能等待批中的所有对象到达。我想我会使用归并排序，这样我就可以“合并”新元素，但我的朋友认为我应该“插入”它们，我想这会建议插入排序。救命啊，我糊涂了！我应该使用合并排序还是插入排序？