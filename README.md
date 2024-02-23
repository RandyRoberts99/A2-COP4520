# Assignment 2 (COP 4520) - Randall Roberts

### Overview
Problem 1 and Problem 2 solutions for Assignment 2 are given in Problem1.java and Problem2.java

### Prerequisites
1. Install the Java Development Kit (JDK 8 or Higher)
2. Install Git
2. Have a code editor with a terminal such as Visual Studio Code installed. (https://code.visualstudio.com/download)

### How to run the file
1. Clone the repository from Git to your machine. (https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)
2. In your terminal, run the following commands:
```
javac Problem1.java
java Problem1
```
### OR (For Problem2.java)
```
javac Problem2.java
java Problem2
```

### Analysis

For problem 1, the strategy I chose for finding out when all guests have completed the maze was simple. When a guest has completed the maze for the first time,
they will take a cupcake. Any subsequent times, they will not take the cupcake. This will ensure that each person only grabs one cupcake, and once N cupcakes have been
taken, will be a signal to the minotaur that all guests have gone through the maze. The implementation involved the use of Javas built in semaphore type, which allowed
me to easily keep track of when someone is in the maze and when someone is not in the maze.

For problem 2, I chose to go with the second strategy for a few reasons. The first one being: I found that solution to be the easiest for me to understand. The second
one being efficiency. Guests don't waste time trying to enter when the showroom is busy. The last one being fairness. Guests have equal opportunity to enter the showroom 
based on the sign status. A couple disadvantages include guests appearing in a random order, and bottlenecks considering the amount of time between guests in the showroom.
The implementation came down to the use of a lock for ensuring that only one guest could enter the showroom at a time from the queue.