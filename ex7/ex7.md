## Motivating Application: Directed Kidney Donation
Kidneys are organs in the human body whose function is critical for maintaining life. Put simply, the kidneys primarily serve to regulate the salinity, water content, and pH of one's blood, and also remove toxins from the body. If someone has no working kidneys then they must either receive frequent kidney dialysis (which cycles blood through a machine to perform the function of a kidney), or else receive a kidney transplant from an organ donor.肾脏是人体内的器官，其功能对维持生命至关重要。简单地说，肾脏主要用于调节血液的盐度、含水量和pH值，并从体内清除毒素。如果有人没有正常工作的肾脏，那么他们必须接受频繁的肾透析（通过机器循环血液以执行肾脏功能），或者接受器官捐献者的肾移植。

Humans are generally born with two kidneys, and one functioning kidney is sufficient for survival, so it is possible (though not risk-free) for a living person to donate a kidney to a patient in need. To protect from disease, the human immune system has evolved to recognize and attack cells from other individuals. As such, before donation, we must check whether a donor and recipient are compatible. Donor-patient compatibility is determined by weighing many factors, but one important factor is HLA match compatibility.人类通常天生就有两个肾脏，一个功能正常的肾脏足以维持生存，因此活着的人有可能（尽管并非没有风险）向有需要的患者捐献肾脏。为了预防疾病，人类免疫系统已经进化到识别和攻击其他个体的细胞。因此，在捐赠之前，我们必须检查捐赠者和接受者是否相容。供体-患者相容性是通过权衡许多因素来确定的，但一个重要因素是HLA匹配相容性。 

Human Leukocyte Antigens (HLAs) are proteins that attach to the membranes of cells to help the immune system identify which cells are "self" cells and which are "invader" cells. Each person has a collection of different HLAs, and the more HLAs that are shared between the donor and the recipient the safer the transplant will be. The similarity is measured by a "HLA match score". For this assignment, we will consider a patient and donor to be compatible in the case that their match score is at least 60.人类白细胞抗原（HLA）是附着在细胞膜上的蛋白质，有助于免疫系统识别哪些细胞是“自我”细胞，哪些是“入侵者”细胞。每个人都有一组不同的HLA，供体和受体之间共享的HLA越多，移植就越安全。相似性通过“HLA匹配评分”来衡量。对于这项任务，如果患者和捐赠者的匹配分数至少为60，我们将认为他们是相容的。

When someone wishes to donate a kidney, there are two main ways they can donate. They can give a “directed donation”, meaning they wish to donate to a specific person. They could also give a “non-directed donation”, meaning they choose to donate without knowing the recipient. 当有人想捐献肾脏时，有两种主要的捐赠方式。他们可以进行“定向捐赠”，这意味着他们希望捐赠给特定的人。他们也可以进行“非定向捐赠”，这意味着他们在不知道接受者的情况下选择捐赠。

Because of all the factors at play for donor-recipient compatibility, it may be the case that a recipient does not know anyone who would be a matching directed donor. To help patients find donors, donation networks have been set up. The idea is that if Recipient A needs a kidney, but their friend Donor A is not compatible, then perhaps Donor A could give a kidney to compatible Recipient B in exchange for Donor B giving a kidney to Recipient A. So in short, if Recipient A has no known donors, they can try to identify a "kidney trade" with another recipient using one of their directed donors. These trades could also be larger "kidney donation cycles", where Donor A gives to Recipient B, Donor B gives to recipient C, and Donor C gives to recipient A. For the sake of this assignment, our cycles can be of any length, but they must be cycles (the idea being that Donor A is a directed donor and so is only willing to donate a kidney if the result is Recipient A receiving one).由于影响供体-受体兼容性的所有因素，受体可能不知道谁是匹配的定向供体。为了帮助患者找到捐赠者，已经建立了捐赠网络。这个想法是，如果接受者A需要一个肾脏，但他们的朋友捐赠者A不兼容，那么捐赠者A可能会把肾脏送给兼容的接受者B，以换取捐赠者B把肾脏送给接受者A。简而言之，如果接受者甲没有已知的捐赠者，他们可以尝试使用他们指定的捐赠者之一与另一个接受者进行“肾脏交易”。这些交易也可能是更大的“肾脏捐赠周期”，其中捐赠者A给接受者B，捐赠者B给接受者C，捐赠者C给接受者A。为了这项任务，我们的周期可以是任何长度，但它们必须是周期（这个想法是捐赠者A是一个定向捐赠者，因此只有在结果是接受者A接受肾脏的情况下才愿意捐赠肾脏）。

## Problem Statement
For this assignment you will implement an algorithm to identify whether a given recipient can receive a kidney through a donation cycle.对于这项任务，您将实现一种算法，以确定给定的接受者是否可以通过捐赠周期获得肾脏。

A donation cycle is defined to be a sequence of kidney recipients r0, r1, ..., rx, r0 such that for each choice of i there is a directed donor for recipient ri who has an HLA match score of at least 60 with recipient ri+1. In other words, it is a cycle of recipients such that each recipient in the cycle can receive a kidney from one of the donors associated with the recipient before them in the cycle.捐赠周期被定义为肾接受者r0、r1、…、r0的序列。。。，使得对于i的每一个选择都有一个与受体ri+1具有至少60的HLA匹配分数的受体ri的定向供体。换句话说，这是一个接受者的循环，循环中的每个接受者都可以从与接受者相关的捐赠者之一那里获得肾脏。

For this assignment we will give you a list of directed donors (and the recipients whom they wish to benefit) as well as HLA match scores for each donor-recipient pair. You will then convert that information into a graph, and then implement a method that will give a donor cycle which includes a given recipient, or else indicate that no such cycle exists. You may assume that no donor has a match with the recipient they wish to benefit.对于这项任务，我们将为您提供一份定向捐献者（以及他们希望受益的接受者）名单，以及每对捐献者-接受者的HLA匹配分数。然后，您将把这些信息转换为图形，然后实现一种方法，该方法将给出一个包括给定接受者的供体周期，或者表明不存在这样的周期。你可以假设没有捐赠者与他们希望受益的接受者相匹配。

Input Format: For this assignment, input will be encoded in txt files. Supposing that each test consists of n recipients and m donors, the files will contain m+4 lines as follows:输入格式：对于此任务，输入将以txt文件编码。假设每个测试由n个接收者和m个捐赠者组成，文件将包含m+4行，如下所示：

The first line contains the value n (the number of recipients)
The next line contains the value m (the number of donors)
The next line contains m comma-separated integers between 0 and n-1 to indicate the recipient whom each donor wishes to benefit (so if the ith integer is j, that means donor i wishes for recipient j to receive a kidney)
The next m lines each contain n comma-separated integers. Collectively, these n*m integers give the HLA match scores for all donor-recipient pairs. We consider a score of 60 or higher to be suitable for donation.

第一行包含值n（收件人数量）
下一行包含值m（捐赠者数量）
下一行包含m个逗号分隔的0到n-1之间的整数，表示每个捐赠者希望受益的接受者（因此，如果第i个整数是j，这意味着捐赠者i希望接受者j接受肾脏）
接下来的m行每行包含n个逗号分隔的整数。总的来说，这些n*m整数给出了所有供体-受体对的HLA匹配分数。我们认为60分或更高的分数适合捐赠。

The last line contains an integer between 0 and n-1 to indicate a recipient. This recipient will be given as input to your algorithm, which will attempt to find a cycle that includes that recipient.
For example, if the file’s contents were as shown on the left, then the graph would appear as on the right.
最后一行包含一个介于0和n-1之间的整数，表示收件人。此收件人将作为输入提供给您的算法，该算法将尝试找到包含该收件人的循环。

When processing the file line-by-line we see that:
There are 3 recipients (hence 3 nodes in the graph)
There are 9 donors
Donors 0, 1, and 2 wish for recipient 0 to receive a kidney, donors 3, 4, 5, and 6 wish to benefit recipient 1, and donors 7, and 8 wish to benefit recipient  2
Donor 0 does not have a strong enough match to donate to any of the three recipients (no scores are 60 or greater)
Donor 1 does not have a strong enough match to donate to any of the three recipients (no scores are 60 or greater)
Donor 2 is compatible with recipient 1, therefore we draw an edge from node 0 (Donor 2’s beneficiary) to node 1 (Donor 2’s match)
Repeat the above for donors 3-8
Since the last line has the value 0, your algorithm should find a cycle which includes recipient 0. In the graph this would be [0, 1, 2, 0].

有3个收件人（因此图中有3个节点）
有9名捐赠者
捐赠者0、1和2希望接受者0接受肾脏，捐赠者3、4、5和6希望使接受者1受益，捐赠者7和8希望使接受者2受益
捐赠者0没有足够强的匹配项可以捐赠给三个接受者中的任何一个（没有得分为60或更高）
捐赠者1没有足够强的匹配能力捐赠给三个接受者中的任何一个（没有得分达到或超过60分）
捐赠者2与接受者1兼容，因此我们从节点0（捐赠者2的受益人）到节点1（捐赠者2匹配）画一条边
对捐赠者3-8重复上述步骤
由于最后一行的值为0，您的算法应该找到一个包含收件人0的循环。在图中，这将是[0,1,2,0]。


## Provided Code
Several java classes have been provided for you in this zip file. Here is a description of each.

- Client
This class contains the main method, which does the following:
Reads the input file named in the field testFileName
Parses the file and converts all the information into 2 arrays:
donorToBenefit: an array of ints with the property that index i contains the recipient whom donor i wishes to benefit 
matchScores: a 2-d array with the property that index x,y contains the match score of donor x and recipient y. Donation is permitted if that score is at least 60.
Calls the constructor that you will implement, providing these arrays as input
Invokes the findDonorCycle method you will implement, giving the int on the last line of the file as the argument
读取字段testFileName中命名的输入文件
解析文件并将所有信息转换为2个数组：
donorToBenefit：一个int数组，其属性为索引i包含捐赠者希望受益的接收者
matchScores：一个二维数组，其属性为索引x，y包含捐赠者x和接受者y的匹配分数。如果分数至少为60，则允许捐赠。
调用您将要实现的构造函数，提供这些数组作为输入
调用您将要实现的findDonorCycle方法，将文件最后一行的int作为参数
Do not submit this file

- Match
These objects will be used to indicate a potential donation. It’s primary role is just to encapsulate 3 fields:
donor: A donor
beneficiary: Whom that donor seeks to benefit
recipient: A recipient compatible with the donor
In effect, these objects will also be used to store edges in our adjacency list representation of a graph, specifically representing an edge from beneficiary to recipient
这些物品将用于表示潜在的捐赠。它的主要作用只是封装3个字段：
捐赠者：捐赠者
受益人：捐赠者希望使谁受益
接受者：与捐赠者相容的接受者
实际上，这些对象也将用于在图的邻接表表示中存储边，具体表示从受益人到接收者的边
Do not submit this file

`DonorGraph`
This is the file that you will be modifying for this assignment. You will create a constructor in part 1, the method hasCycle in part 2, and the method findCycle in part 3.
It additionally has a method called isAdjacent which returns a boolean to indicate whether the given pair of recipients form an edge in the graph.
Finally, it has a method called getDonor which returns an int representing the donor who enabled edge between the pair of recipients given (or returns -1 if the pair are not adjacent).
这是您将为此任务修改的文件。您将在第1部分中创建一个构造函数，在第2部分中创建hasCycle方法，在第3部分中创建findCycle方法。
它还有一个名为isAdjacent的方法，该方法返回一个布尔值，以指示给定的接收者对是否在图中形成边。
最后，它有一个名为getDonor的方法，该方法返回一个int，表示在给定的一对接收者之间启用边的捐赠者（如果这对接收者不相邻，则返回-1）。
You will submit this file to Gradescope

## Various text files
example.txt
The file described above
straightline.txt
Produces a 10-node graph that is just a line of nodes. No cycles should be present
slides.txt 
The directed graph from the slides with the right-most node as the query. No cycles should be found for the query node, but some other choices of  the query should result in cycles.
biggerloop.txt 
10 vertices in a circle. Every node should have a cycle (which is the whole graph)
almostcycle.txt 
10 vertices in a circle, but with one edge in the opposite direction from the rest. No cycles should be present
complete.txt 
10 vertices that are each connected to all the other vertices. Every node should have a cycle (and there are many options for what that cycle may be)
lollypop0.txt 
A graph with vertices in a "lollipop" shape, meaning there is a straight line of nodes connected to nodes in a loop. For this file, the query node is node 0 which is at the start of the "stick" of the lollipop, so no cycle should be present.
lollypop5.txt 
The same lollypop graph as above, but now the query node is in the candy portion of the lollipop. There should be a cycle found.

## Part 1: Making the graph

For this part you will make the DonorGraph constructor. The main method in Client reads the text file, then provides its contents as arguments to your constructor. These arguments are:对于这部分，您将创建DonorGraph构造函数。Client中的main方法读取文本文件，然后将其内容作为参数提供给构造函数。这些论点是：

`donorToBenefit`: an array of ints with the property that index i contains the recipient whom donor i wishes to benefit  一个int数组，其属性为索引i包含捐赠者希望受益的接收者

`matchScores`: a 2-d array with the property that index x,y contains the match score of donor x and recipient y. Donation is permitted if that score is at least 60. 一个二维数组，其属性为索引x，y包含捐赠者x和接受者y的匹配分数。如果分数至少为60，则允许捐赠。

Your constructor should use those two arrays to populate the adjList field with Match objects to become an adjacency list representation of a directed graph. All of the Match objects at adjList.get(i) should have i as the value in the beneficiary field. It will then represent an edge to node j provided that j is in the recipient field. The match object should only exist if donor has a match score of at least 60 with recipient. None of the tests used will include a case where a donor is a suitable match for their beneficiary.你的构造函数应该使用这两个数组用Match对象填充adjList字段，使其成为有向图的邻接列表表示。adjList.get（i）中的所有Match对象在受益人字段中的值都应该是i。然后，它将表示节点j的边，前提是j在接收方字段中。只有当捐赠者与接受者的匹配分数至少为60分时，匹配对象才应存在。所使用的测试都不包括捐赠者与受益人匹配的情况。

There are multiple ways you can implement this constructor. If you would like for the graph to be a simple graph, note that having duplicate Match objects with the same beneficiary and recipient (but different donors) will not change the presence or identity of any cycles. This means you can make sure there is only one Match object to represent each edge in the graph. There’s nothing wrong with having multiple, but you may need to remember that this will cause the graph to be non-simple in parts 2 and 3.有多种方法可以实现此构造函数。如果您希望该图是一个简单的图，请注意，具有相同受益人和收件人（但不同捐赠者）的重复Match对象不会改变任何周期的存在或身份。这意味着您可以确保只有一个Match对象来表示图形中的每条边。有多个并没有错，但你可能需要记住，这将导致第2部分和第3部分中的图不简单。


## Part 2: hasCycle
For this part you will write the method hasCycle. The argument to this method is a recipient, and the method should return true if there is a donation cycle which includes that recipient, and false if there is not. It will be most helpful to review cycle detection from class to help here. The only difference between the problem solved in class and the behavior of this method is that in class we wanted to return true whenever any cycle was present, for this method you want to return true if there is a cycle which includes a specified node. The running time of the algorithm should be O(n*m) where n is the number of recipients and m is the number of donors. 
对于这一部分，您将编写hasCycle方法。此方法的参数是收件人，如果存在包含该收件人的捐赠周期，则该方法应返回true，如果没有，则返回false。从课堂上复习循环检测将非常有帮助。类中解决的问题与此方法的行为之间的唯一区别是，在类中，只要存在任何循环，我们都希望返回true，对于此方法，如果存在包含指定节点的循环，则希望返回true。算法的运行时间应该是O（n*m），其中n是接受者的数量，m是捐赠者的数量。


## Part 3: findCycle
For this part you will write the method findCycle. The argument to this method is a recipient, and the method should return a list of matches to indicate the cycle. If you think of each Match object as an edge in the graph, this list of matches is the list of edges followed to create the cycle. The list should be ordered such that the recipient given as the argument is the beneficiary of the first match in the list and also is the recipient of the last match of the list. For all matches in between the recipient of one match must be the beneficiary of the next (i.e. the edges can be followed in the order they are listed). The running time of the algorithm should be O(n*m) where n is the number of recipients and m is the number of donors.
对于这一部分，您将编写findCycle方法。此方法的参数是收件人，该方法应返回一个匹配列表以指示循环。如果将每个Match对象视为图中的边，则此匹配列表是创建循环所遵循的边列表。列表的排序应确保作为参数给出的收件人是列表中第一个匹配项的受益人，也是列表中最后一个匹配的收件人。对于两次匹配之间的所有匹配，一次匹配的接收者必须是下一次的受益人（即边缘可以按照列出的顺序排列）。算法的运行时间应该是O（n*m），其中n是接受者的数量，m是捐赠者的数量。