## verview

This exercise consists of the following parts:
- Complete an implementation of a separate chaining hash table data structure.完成一个单独的链式哈希表数据结构的实现。
- Write a good hash function for the Word class provided. 为提供的Word类编写一个好的哈希函数。
- Use your implementation of the hash table to complete an implementation of a word search algorithm, which searches through a grid of items for sequences that appear in a given dictionary.使用哈希表的实现来完成单词搜索算法的实现，该算法在项目网格中搜索出现在给定词典中的序列。

Parts 2 and 3 rely on each other, but part 1 can be done independently if you temporarily refactor some code to use Java’s HashMap instead of your own separate chaining hash table implementation. If you want to go this route for debugging, I’d recommend writing a “wrapper” class the implements the DeletelessDictionary interface, but simply uses the java HashMap class as the underlying data structure (or if you implement a contains method, you can use your AVL implementation from exercise 4!). 第2部分和第3部分相互依赖，但如果你暂时重构一些代码以使用Java的HashMap而不是你自己的单独链式哈希表实现，第1部分可以独立完成。如果你想走这条路进行调试，我建议你编写一个“包装器”类来实现DeletelessDictionary接口，但只使用java HashMap类作为底层数据结构（或者如果你实现了contains方法，你可以使用练习4中的AVL实现！）。


## Motivating Application: Word Search
For a newspaper word search puzzle, you’re given a grid of letters and a list of words. Your goal is to find all of the given words within the grid. The challenge is that the words do not necessarily appear in left-to-right order. Instead, the words can go in any of 8 directions: left-to-right, right-to-left, vertically down, vertically up, or any of 4 directions diagonally (up-left, up-right, down-left, down-right). 对于一个报纸单词搜索谜题，你会得到一个字母网格和一个单词列表。你的目标是在网格中找到所有给定的单词。挑战在于单词不一定按从左到右的顺序出现。相反，这些单词可以朝8个方向中的任何一个：从左到右、从右到左、垂直向下、垂直向上，或者对角线上的4个方向（左上、右上、左下、右下）。

For this assignment, in addition to implementing a separate chaining hash table, you will be completing an implementation of a word search solver. For this word search solver we use two text files. One containing our grid of letters, the other containing a list of potential words. This solver will use these slightly differently from the puzzles you might find in the newspaper. In the newspaper it is guaranteed that all words in the list appear somewhere in the grid. For this application our task will be to determine which of the words appear. Additionally, we’re implementing the algorithm using generics, so rather than a grid of letters we could instead do a wordsearch on a grid of integers, or doubles, or booleans, or pixels, or any other object we could dream of!对于这项任务，除了实现一个单独的链式哈希表外，您还将完成一个单词搜索求解器的实现。对于这个单词搜索求解器，我们使用两个文本文件。一个包含我们的字母网格，另一个包含潜在单词列表。这个求解器将使用这些与你在报纸上可能找到的谜题略有不同的谜题。在报纸上，可以保证列表中的所有单词都出现在网格中的某个位置。对于这个应用程序，我们的任务将是确定哪些单词出现。此外，我们正在使用泛型实现该算法，因此我们可以在整数、双精度、布尔值、像素或任何其他我们梦想中的对象的网格上进行单词搜索，而不是使用字母网格！

You’ll see in the starter code and in the list of provided files below that we give you two pairs of puzzles. One that is a small grid paired with a relatively short list of words (this is actually the list of words used by xkcd author Randall Munroe for his Thing Explainer book, but with the swear words removed). The other is a larger grid paired with a large list of words (this list of words is the official scrabble dictionary).您将在入门代码和下面提供的文件列表中看到，我们为您提供了两对谜题。一个是一个小网格，与一个相对较短的单词列表配对（这实际上是xkcd作者Randall Munroe在他的《事物解释者》一书中使用的单词列表，但去掉了脏话）。另一个是一个更大的网格，与一个大的单词列表配对（这个单词列表是官方的拼字词典）。

## Implementation Guidelines
Your implementations in this assignment must follow these guidelines
You may not add any import statements beyond those that are already present (except for where expressly permitted in this spec). This course is about learning the mechanics of various data structures so that we know how to use them effectively, choose among them, and modify them as needed. Java has built-in implementations of many data structures that we will discuss, in general you should not use them.您不得在已有的导入语句之外添加任何导入语句（除非本规范明确允许）。本课程是关于学习各种数据结构的机制，以便我们知道如何有效地使用它们，在它们之间进行选择，并根据需要进行修改。Java有许多我们将讨论的数据结构的内置实现，一般来说你不应该使用它们。

Do not have any package declarations in the code that you submit. The Gradescope autograder may not be able to run your code if it does. 您提交的代码中没有任何包声明。如果可以，Gradescope自动签名器可能无法运行您的代码。

Remove all print statements from your code before submitting. These could interfere with the Gradescope autograder (mostly because printing consumes a lot of computing time). 在提交之前，从代码中删除所有打印语句。这些可能会干扰Gradescope的自动生成（主要是因为打印消耗了大量的计算时间）。

Your code will be evaluated by the gradescope autograder to assess correctness. It will be evaluated by a human to verify running time. Please write your code to be readable. This means adding comments to your methods and removing unused code (including “commented out” code).你的代码将由gradescope签名者评估其正确性。它将由人类进行评估，以验证运行时间。请编写可读的代码。这意味着在方法中添加注释并删除未使用的代码（包括“注释掉”的代码）。

## Provided Code
Several java classes have been provided for you in this zip file. Here is a description of each.

`DeletelessDictionary`  

A dictionary interface. This differs from the one discussed in class in the following ways:

It does not have a delete operation (hence being called a “deleteless” dictionary). This means that once a key-value pair has been added to the dictionary there is no way of removing that key later.它没有删除操作（因此被称为“deletes”字典）。这意味着一旦将键值对添加到字典中，以后就无法删除该键。

It requires the operations getKeys and getValues which return a list of the keys or values in the dictionary. These lists must be index-aligned with one another. By this we mean that index 0 of getKeys is associated with the value at index 0 of getValues. More generally, getKeys().get(i).equals(getValues().get(i)) should always be true.它需要操作getKeys和getValues，它们返回字典中的键或值的列表。这些列表必须彼此索引对齐。这意味着getKeys的索引0与getValues的索引0处的值相关联。更一般地说，getKeys（）.get（i）.equals（getValue（）.get（i））应该始终为true。

It requires the operation contains which returns true or false to indicate whether the given key is present in the dictionary.它要求操作包含返回true或false，以指示给定的键是否存在于字典中。

**`ChainingHashTable`**  

When you download the zip, this will contain the beginnings of an implementation of a separate chaining hash table. We have implemented a constructor as well as the isEmpty and size methods. You’re welcome to change any of those if you’d like. You may also add fields to this class if you wish.当您下载zip时，这将包含一个单独的链式哈希表实现的开始。我们已经实现了构造函数以及isEmpty和size方法。如果你愿意，欢迎你改变其中任何一个。如果你愿意，你也可以向这个类添加字段。

To help you with debugging, we have provided a toString method. We will not use this method in the autograder, so you’re welcome to change that if you wish as well.为了帮助您进行调试，我们提供了一个toString方法。我们不会在亲笔签名中使用这种方法，所以如果你愿意，欢迎你改变这种方法。

We’ve also provided an array of primes that will be useful for rehashing. Your implementation must be able to rehash to a size beyond these pre-compute primes. There’s more guidance on how to do this in the specification for insert below.我们还提供了一系列素数，这将有助于重新散列。您的实现必须能够重新散列到超出这些预计算素数的大小。在下面的插入规范中有更多关于如何做到这一点的指导。

You will submit this file to Gradescope 您将把此文件提交给Gradescope

`Item`  

This class just encapsulates a key-value pair for adding to the chaining hash table (think of it as playing a similar role as the nodes did in AVL trees). Overall, the chaining hash table will be an array of linked lists of these items. 这个类只是封装了一个键值对，用于添加到链式哈希表中（可以认为它扮演着与AVL树中的节点类似的角色）。总的来说，链式哈希表将是这些项目的链表数组.

**`Word`**

This class is intended to serve sort of like a generic form of a string. Essentially it is just an array of some generic type T that’s encapsulated in an object (you can think of it like a fixed-sized array list). 这个类旨在提供类似于字符串的泛型形式。本质上，它只是一个封装在对象中的泛型类型T的数组（你可以把它想象成一个固定大小的数组列表）。

We’ve added some methods, though, to make things a bit easier for the word search application. Some important but routine operations have been provided, including a toString and equals method (neither of which you should change in your final submission).不过，我们添加了一些方法，使单词搜索应用程序更容易一些。提供了一些重要但常规的操作，包括toString和equals方法（在最终提交时都不应更改）。

Most importantly here we’ve added a really handy constructor. You can construct a Word by either giving it an array, which it then just makes a shallow copy of, or by giving it a grid along with the start cell, length and direction. This latter constructor will shallow-copy all indices of the grid starting from the given cell and then preceding the indicated direction (any of the 8 valid directions for a word search). 最重要的是，我们在这里添加了一个非常方便的构造函数。你可以构造一个单词，要么给它一个数组，然后它只是做一个浅拷贝，要么给一个网格以及起始单元格、长度和方向。后一个构造函数将浅层复制网格的所有索引，从给定的单元格开始，然后在指示的方向（单词搜索的8个有效方向中的任何一个）之前。

The only thing you need to do in this class is implement the hashCode method (see details below). You should not change anything else.在这个类中，你唯一需要做的就是实现hashCode方法（详见下文）。你不应该改变其他任何事情。

**`WordSearch`**
This is the class that actually does the word searching. We’ve provided the trickiest part of the code for you (the part that actually navigates the grid, that method is called wordSearch). We’ve just left out the parts that actually use the hash table.这是真正进行单词搜索的班级。我们为您提供了代码中最棘手的部分（实际导航网格的部分，该方法称为wordSearch）。我们只是省略了实际使用哈希表的部分。

To assist with debugging we added a method called printFoundWords that prints out all of the words found within the grid.为了帮助调试，我们添加了一个名为printFoundWords的方法，可以打印出网格中找到的所有单词。


`Client`
This class contains the main, which is what will make the word search happen! The main method invokes methods which will read the grid and dictionary (i.e. word list) files, then creates a WordSearch object with them, then does the word search! It currently does this for both of the small and large puzzles. To check correctness it only checks if the number of words found is correct, but does not check that the actual words were the correct ones. 这个类包含main，这将使单词搜索发生！main方法调用将读取网格和字典（即单词列表）文件的方法，然后使用它们创建一个WordSearch对象，然后进行单词搜索！目前，它对小型和大型谜题都这样做。为了检查正确性，它只检查找到的单词数量是否正确，但不检查实际单词是否正确。

Currently there are no tests provided specifically for the ChainingHashTable. The expectation is that you obtained experience on how to verify dictionary behavior from Exercise 4.目前还没有专门针对ChainingHashTable的测试。期望您从练习4中获得了如何验证词典行为的经验。

`Various text files`
These text files contain the information associated with each example puzzle. The ones whose names begin with the prefix “small” are associated with the small test. The rest with the big test. Each test has a “puzzle” file with the grid, a “words” file with the list of valid words, and a “solution” file which contains all of the words that your algorithm should find. 这些文本文件包含与每个示例谜题相关的信息。名字以前缀“small”开头的那些与小测试相关联。剩下的就是大考验。每个测试都有一个带网格的“谜题”文件、一个带有效单词列表的“单词”文件和一个包含算法应该找到的所有单词的“解决方案”文件。

## Part 1: ChainingHashTable
To obtain good performance of our DeletelessDictionary we will implement a separate chaining hash table data structure according to the comments provided for each method in the interface. You should finish all non-yet implemented methods in the DeletelessDictionary interface. 为了获得DeletelessDictionary的良好性能，我们将根据接口中为每个方法提供的注释实现一个单独的链式哈希表数据结构。您应该完成DeletelessDictionary接口中所有尚未实现的方法。

It will be helpful to know, for this assignment, that all objects in Java have a hashCode method. This method returns an integer that can then be used in various ways, such as selecting an index in a hash table! When implementing the ChainingHashTable class, you may assume that this hashCode method has been implemented to be “good” for the type that the data structure will contain. The integer given, though, is not guaranteed to be in the range of your underlying array.对于这个任务，知道Java中的所有对象都有一个hashCode方法会很有帮助。此方法返回一个整数，然后可以以各种方式使用，例如在哈希表中选择索引！在实现ChainingHashTable类时，您可以假设此hashCode方法已实现为数据结构将包含的类型“良好”。但是，不能保证给定的整数在基础数组的范围内。

Here is the list of methods you must implement, along with any guidelines for implementation:

`find`

This behavior should pretty much just match how we described it in class. You give it a key, it returns the associated value (if it exists)

`Contains`

This one is new, but the algorithm is almost exactly the same as find, but can be convenient in some circumstances. You give it a key, it returns true or false to indicate whether it appears in the dictionary at all.

`Insert`

This behaves just like we discussed, but we’ll review here. You give it a key and a value, it then pairs together that key and value in the dictionary. If the key already had some associated value, it should return the old value. If the key was not already present, it should return null.这与我们讨论过的一样，但我们将在这里进行回顾。你给它一个键和一个值，然后它在字典中将键和值配对在一起。如果键已经有一些关联值，它应该返回旧值。如果密钥不存在，则应返回null。

Most importantly, here, you will need to resize your array and rehash the items when the load factor gets too high. The size should be chosen to be a prime number from the provided list of pre-computed primes as long as possible. Once you run out of primes, you should use a different method for selecting the next size of the array (keeping sure that the running time is still constant amortized!). As a tip, numbers that are 1 more than a power of 2 tend to behave a lot like primes (and have a higher probability of being primes themselves)!最重要的是，在这里，当负载因子过高时，您需要调整数组大小并重新刷新项目。大小应尽可能选择为所提供的预计算素数列表中的素数。一旦素数用完，您应该使用不同的方法来选择数组的下一个大小（确保运行时间仍然是恒定的摊销！）。作为提示，比2的幂大1的数字往往表现得很像素数（并且本身成为素数的概率更高）！

`getKeys`
Returns a list of all keys in the dictionary

`getValues`
Returns a list of all values in the dictionary. The order of this list should parallel the list returned by getKeys. That is, the value at index i should be associated with the key at index i of getKeys.返回字典中所有值的列表。此列表的顺序应与getKeys返回的列表平行。也就是说，索引i处的值应与getKeys的索引i处键相关联。

## Part 2: Word.hashCode()
The only way to obtain good performance from hash tables is to use a good hash function. In Java, Object has a method called hashCode that serves as the default hash function. As with the default behavior of other Object methods like equals and toString, the default hashCode is almost certainly not going to do what we want, so we’ll need to override it.从哈希表获得良好性能的唯一方法是使用良好的哈希函数。在Java中，Object有一个名为hashCode的方法作为默认哈希函数。与equals和toString等其他Object方法的默认行为一样，默认的hashCode几乎肯定不会做我们想做的事情，所以我们需要重写它。

Write your own implementation of the hashCode method for the Word class to be a “good” hash function. Keep in mind the properties of a “good” hash function that we discussed in class. You may assume that the objects that the Word contains themselves have a “good” hash function implementation.为Word类编写自己的hashCode方法实现，使其成为一个“好”的哈希函数。记住我们在课堂上讨论过的“好”哈希函数的属性。您可以假设Word包含的对象本身具有“良好”的哈希函数实现。

Beware! When Java integers exceed a certain size they may become negative! This is called integer overflow.小心！当Java整数超过一定大小时，它们可能会变为负数！这被称为整数溢出。

## Part 3: WordSearch
Now for the main event – the WordSearch algorithm! For this part you will finish our implementation. Rest assured, though, the hardest parts are provided for you. The only things you need to implement are the parts that actually use the dictionary data structure! 现在是主要事件——WordSearch算法！对于这一部分，您将完成我们的实施。不过，请放心，最难的部分是为您提供的。您只需要实现实际使用字典数据结构的部分！

The most important fields for your pieces are a dictionary and a 2-d array. The dictionary (which will be your ChainingHashTable implementation) maps Word objects to booleans. Initially, it will contain all words from the list of words as keys, with each key associated with the value false. This dictionary is used to keep track of which words from the dictionary have been found in the grid (which is represented by the 2-d array).你作品中最重要的字段是字典和二维数组。字典（将是ChainingHashTable实现）将Word对象映射到布尔值。最初，它将包含单词列表中的所有单词作为关键字，每个关键字都与值false相关联。此词典用于跟踪

The WordSearch constructor does the following:
Its parameters are a two dimensional array to serve as the grid to search in and a list of Word objects to indicate the dictionary of words. In this context we mean “dictionary” as in a list of words in some language, e.g. Webster’s Dictionary.它的参数是一个二维数组，用作搜索的网格，以及一个Word对象列表，用于指示单词词典。在这种情况下，我们指的是“词典”，即某种语言中的单词列表，例如《韦氏词典》

It constructs a ChainingHashTable. In other words, it creates an instance of the class you implemented in Part 1.它构造了一个ChainingHashTable。换句话说，它创建了您在第1部分中实现的类的实例。

It adds each Word in the input list of Word objects as a key in the ChainingHashTable, with its value being false. The value indicates whether the Word was found in the grid, so before searching we haven’t seen it yet.它将Word对象输入列表中的每个Word作为键添加到ChainingHashTable中，其值为false。该值表示是否在网格中找到了单词，因此在搜索之前我们还没有看到它。

It calls the wordSearch method, which looks for each Word within the grid and updates the value associated with each Word in the ChainingHashTable to be true if it appears. 它调用wordSearch方法，该方法在网格中查找每个单词，并在ChainingHashTable中与每个单词关联的值出现时将其更新为true。

You need to implement the following methods:
`addIfWord`
The argument to the method will be a Word object. This Word object will be a sequence found by the wordSearch method. This method should update the dictionary so that, if the given word is present, it is associated with the value true to indicate it has been found in the grid. If the given Word is not already a key in the dictionary then this method should not modify the dictionary.该方法的参数将是Word对象。此Word对象将是wordSearch方法找到的序列。此方法应更新词典，以便在给定单词存在的情况下，将其与值true相关联，表示已在网格中找到该单词。如果给定单词还不是词典中的关键字，则此方法不应修改词典。
`countWords`
This method should return the number of words from the list of valid words that were found in the grid. 此方法应返回网格中找到的有效单词列表中的单词数。

`getWords`
This method returns a list of all of the words that were found within the grid. There is no particular requirement about the order that the words should appear.此方法返回在网格中找到的所有单词的列表。对单词的出现顺序没有特别要求。