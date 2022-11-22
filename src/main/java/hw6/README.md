# Discussion

## Unit testing TreapMap
First difficulty is to control the randomness in each node's priority.
Since the priority of each node is generated using Random from Java.util, 
a particular priority cannot be designated to each node. I resolved this issue
by adding a setUpTree() method where it takes seed as a parameter. 
In this way, I can make sure that a constant sequence of random numbers are
assigned to each node's priority whenever I run the program.

Second difficulty is to find a correct structure to test. 
Since TreapMap does not gaurantee a height of O(log n), the structure
differs by which priority each node is assigned to (dependent on seed value). 
In particular, when I have to test for removal, I have to start with a complete 
tree using several insert methods before performing a remove. 

                                            
                          remove(2)          
                 2       --------->          3
               /   \                          \
             1      3                          1
But, I don't know which seed will create the same tree structure. 
In order to resolve this, I use an iteration to assign 10 different numbers 
for seed and print out 10 different trees. Then I picked the one that could
be used for my removal testing. 



## Benchmarking

For Hotel_california:
Benchmark                  Mode  Cnt  Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  0.737          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  0.766          ms/op
JmhRuntimeTest.bstMap      avgt    2  0.419          ms/op
JmhRuntimeTest.treapMap    avgt    2  0.570          ms/op

Federalist01:
Benchmark                  Mode  Cnt  Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  5.445          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  7.191          ms/op
JmhRuntimeTest.bstMap      avgt    2  2.418          ms/op
JmhRuntimeTest.treapMap    avgt    2  2.941          ms/op

Moby_dick.txt
Benchmark                  Mode  Cnt     Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  9142.872          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  3147.040          ms/op
JmhRuntimeTest.bstMap      avgt    2   327.837          ms/op
JmhRuntimeTest.treapMap    avgt    2   387.166          ms/op

Pride_and_prejudice.txt
Benchmark                  Mode  Cnt     Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  1800.156          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2   706.508          ms/op
JmhRuntimeTest.bstMap      avgt    2   205.806          ms/op
JmhRuntimeTest.treapMap    avgt    2   291.216          ms/op

Hotel_california.txt and Federalist01.txt have relatively smaller
numbers of words than the other two text files. Even more, Hotel_california.txt
has a smaller number of words than Federlist01, in which the average score through
4 trees in Hotel_california.txt is smaller than Federalist01.txt.

It can be observed that arrayMap and AVL have higher scores than BST and TreapMap. 
ArrayMap implements ArrayList, which is a Java's built-in array that employs
linear search. This makes its time complexity O(n). 

AVL has a score even larger than ArrayMap in the first two text files where 
number of words are small. This is because AVL map involves extra 
searches for each node's height and calculations for bf. Such extra steps 
make the score higher, especially in the text with smaller words since 
searching among a few words requires small costs, whereas modifying the structure
of map requires relatively more costs. That is why AVL scores higher than Array<ap
(and higher than other two maps). 

But as the number of words gets larger, the score of AVL becomes smaller than
that of arrayMap because we had to deal with lots of number with a higher
possibility of repeated numbers, in which structural change/rotation in map 
makes the search of words much easier/faster. 

The scores for BST and Treap map are similar, with Treap map's score being
slightly higher than BST. They are relatively smaller than ArrayMap and AVL
because their operations are O(Log(n)), and they do not force their structure 
to be in balanced. This could imply that in a real world, balancing tree 
is not really necessary because extreme cases where single-sided tree would
unlikely be created -- Forcing a map to always be balanced requires more cost
than searching. 

In addition, the reason behinds treapMap's score being slightly higher than 
that of BSTMap may be due to treapMap's priority field, since Random util 
is used to generate each node's priority. 
