## Θ not Big-O
- Big-O Upper bound，主要用于衡量 最坏情况 的复杂度。
- Θ Tight bound 描述的是确切的复杂度，包括最佳情况。
```java
// counts the number of elements of arr 
// that are divisible by denom.
public static int countDivisible(int[] arr, int denom){
    if (denom == 0)
        return 0; //Θ(1)
    int count = 0;  
    for (i = 0; i < arr.length; i++) { 
        if(arr[i] % denom == 0){
            count++; 
        }   
    } //Θ(N)
    return count; 
}
```
- Best-case complexity: Θ(1).
- Worst-case complexity: Θ(N).
---
```java
int doStuff(List<Integer> numbers){
    int n = numbers.size(); //Θ(1) or Θ(N)
    int count = 0;
    if(n < 150){
        for (int i = 0; i < n; i++){
            for (int j = i; j < n; j++){
                count++;
            }
        } //Θ(N^2)
    }
    else{
        for (int i = 0; i < n; i++){
            count += n;
        } //Θ(N)
    }
    return count;
}
```
- Best-case complexity: Θ(N).
- Worst-case complexity: Θ(N^2).

在 Java 中，List 接口有多个实现方式，其中常见的有：

1. ArrayList：
- 底层由数组支持。
- size() 方法仅返回内部维护的元素个数，这是一个简单的变量读取操作。
- 该操作的时间复杂度是 常数时间 Θ(1)。
2. LinkedList：
- 底层由双向链表支持。
- 如果 size 没有被缓存（默认情况下没有缓存），size() 方法需要遍历链表节点来计算元素个数。
- 该操作的时间复杂度是 线性时间 Θ(N)，其中N是链表中的元素数量。
---
```java
public static void something(List<Integer> lst){
    int x = 0;
    int n = lst.size();  
    for (i = 0; i < n; i++) {  //Θ(N)
        for (j = 0; j < i*i; j++) {  
            x++;
        } //Θ(N*N^2)
    }  
}
```
- Best-case complexity: Θ(N^3).
- Worst-case complexity: Θ(N^3).  

We can use induction Rue to find sth. like this: 
$$ \sum_{i=0}^{n-1} i^2 = \frac{(n-1)n(2n-1)}{6} $$  
or we can just think this is about $n^3$

---
```java
public static int f(List<Integer> lst, int count){
   for(int i = lst.size(); i > 0 ; i--){
       count++;
   } //Θ(N)
   return count
}


public static void mystery(List<Integer> lst){
    int n = lst.size();
    int x = Math.min(lst.get(0), n); //That's possible lst.get(0) = 0, so that even not get in to the loop, so best case is constant Θ(1)
    int count = 0;
    for (i = 0; i < x; i++) {  //Θ(N)
        count = f(lst, count);
    }  //Θ(N*N)
}
```
- Best-case complexity: Θ(1).
- Worst-case complexity: Θ(N^2). 
## Big-O Proofs
submit a proof that $f(n)\in O(g(n))$

$f(n)=n^3$  
$g(n)=n^3-n$


We want to find constants $c$ and $n_{0}$ such that for all $n\geq n_{0}$: $$n^3\leq c(n^3-n)$$ Simplify the inequality function, divide both sides by $n^3$: $$1\leq c(1-\frac{1}{n^2})$$ when $n\rightarrow\infty$, the term $(1-\frac{1}{n^2})$ will approaches 1.  
 
We can choose $c=2$ and $n_{0}=2$ $$1\leq 2(1-\frac{1}{4})=2\cdot\frac{3}{4}=1.5$$
When n increase, $(1-\frac{1}{n^2})$ will increase. When $n\rightarrow\infty$, right side will approached 2.

So, for $n\geq 2$, the inequality $n^3\leq c(n^3-n)$ holds.  

Thus, we have shown that there exist $c=2$ and $n_{0}=2$ such that for all $n\geq n_{0}$, the inequality $f(n)\leq cg(n)$ holds true. Therefore, we can conclude that: $$f(n)\in O(g(n))$$

1/13/2025 Zifeng Wang write the .md file and screen shot the mdPreview 

$f(n)=log_{2}(n^4)$  
$g(n)=log_{2}(n^{1/4})$



We want to find constants $c$ and $n_{0}$ such that for all $n\geq n_{0}$: $$log_{2}(n^4)\leq c\cdot log_{2}(n^{1/4})$$ Simplify the inequality function: $$4log_{2}(n)\leq \frac{c}{4}log_{2}(n)$$ $$4\leq \frac{c}{4}$$ $$16\leq c$$ Thus for any $c\geq 16$, the inequality holds. And there is no additional restriction on $n_{0}$ other than ensuring $n_{0}\geq 1$

We can choose $c=16$ and $n_{0}=1$, and for all $n\geq n_{0}$, the inequality $f(n)\leq cg(n)$ holds true. Therefore, we can conclude that: $$f(n)\in O(g(n))$$

1/13/2025 Zifeng Wang write the .md file and screen shot the mdPreview 

$f(n)=log_{2}(n)$  
$g(n)=log_{10}(n)$



We want to find constants $c$ and $n_{0}$ such that for all $n\geq n_{0}$: $$log_{2}(n)\leq c\cdot log_{10}(n)$$ 
We can convert $log_{2}(n)$ from base 2 to base 10, $log_{2}(n) / log_{2}(10) = log_{10}(n)$.

Simplify the inequality function, divide both sides by $log_{2}(10)$: 

$$log_{10}(n)\leq \frac{c\cdot log_{10}(n)}{log_{2}(10)}$$ 


$$log_{2}(10)\leq c$$ 
 
$log_{2}(10)$ is approximately 3.323, thus for any integer $c\geq 4$, the inequality holds. And there is no additional restriction on $n_{0}$ other than ensuring $n_{0}\geq 1$

We can choose $c=4$ and $n_{0}=1$, and for all $n\geq n_{0}$, the inequality $f(n)\leq cg(n)$ holds true. Therefore, we can conclude that: $$f(n)\in O(g(n))$$

1/13/2025 Zifeng Wang write the .md file and screen shot the mdPreview 