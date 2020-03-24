************************* DESCRIPTIONS OF ALL FILES *********************************************

== SocialSim.java:      Contains the main method to execute the program.
                        This class is used to compile and run the program.

== Post:                Represents a Post in the Network.
                        Each post will have: the name of the person who posted it, the number of likes
                        and the names of all people who liked it.

== Person:              Represents a Person in the Network.
                        Each Person will have: a unique name, a list of Posts, a list of followers and a list of people
                        that this person is following

== Network:             Represents the network.
                        Each network will have a list of people.

== FileIO:              Handles all the file Input/Output operations (save/load the network in interactive mode,
                        read and construct the network from network files and event files in simulation mode)

== DSAStack:            Implementation of Stack ADT using LinkedList

== DSAQueue:            Implementation of Queue ADT using LinkedList

== DSALinkedList:       Implementation of Linked List (double-ended, doubly linked list)

== DSAHashTable:        Implementation of Hash Table ADT

== DSAHeap:             Implementation of Heap ADT

== UnitTestX:           Unit test for class X (e.g: UnitTestPerson will be the unit test for class Person, same for
                        other classes).

== DataGenerator:       Generate proper datasets for investigation.


*************************** HOW TO COMPILE AND RUN THE PROGRAM ****************************************

== Compiling: To compile the program, first run this command:

    javac SocialSim.java

    All the dependencies will automatically be compiled.

== Running: The program has 2 modes for executing:

Before running any commands, move to the directory that contains all the compiled class files, then choose one of the
following modes:

    1. Interactive mode: To run the program in interactive mode, run this command (in the current directory):

    java SocialSim -i

    -i tells the program to run in "interactive mode"

    2. Simulation mode: To run the program in simulation mode, run this command (in the current directory):

    java SocialSim -s <network_file> <event_file> <like_prob> <follow_prob>

    -s tells the program to run in "simulation mode"

    * <network_file> is the name of the network file

    * <event_file> is the name of the event file

    * <like_prob> is a real number indicating the probability that a person can like a post

    * <follow_prob> is a real number indicating the probability that a person can follow another person after liking his/her post
