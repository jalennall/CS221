****************
* Project Double Linked List
* Class CS221 -3 Mason Vail
* Date 04/14/219
* Jalen Nall
**************** 

OVERVIEW:


This program runs off of 3 classes that all work together to test the functionality of a double linked list class.


INCLUDED FILES:

 e.g.
 * LinearNode.java - source file - Gives the linked list class a node to work with.
 * IUDoubleLinkedList.java -File that is tested by the list tester.
 * ListTester.java Contains the main method and is has the test for the double linked list.
 * README - this file.


COMPILING AND RUNNING:

This should be a very simple program to compile and run.
First thing to check for is that all files listed above are in the same folder
or directory. the next step is to compile the Main class with the following command.

$javac ListTester.java

This should compile the rest of the required files with it but if not, do this command,

$javac ListTester.java IUDoubleLinkedList.java LinearNode.java

After the program's classes have been compiled, enter the following command to run the program.

$java ListTester

The console output will give the results of the test after the program finishes.
 

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

The ListTester contains the main method, special scenarios that test certain functions of the double
linked list. each scenario then test every methods functionality to see if the special function
does its correct commands. Theirs 4 main test types for the List Tester, 0 element - 3 element
list are tested and all have a general set of test it runs.

The organization is easy to follow in this program. the ListTester class runs thousands of
test on a list program, this one happens to be double linked list, but the list type can change.
The Double Linked List class uses the LinearNode class to create the nodes for the list.
The double linked list uses the IndexUnsortedList interface to get all of its methods.

 
I didn't get to choose the specific organization of this project, but I don't think'
I would have changed anything. Each class has it's use and nothing gets put to waste.

 This is the sort of information someone who really wants to
 understand your program - possibly to make future enhancements -
 would want to know.

TESTING:
This was a very simple program to test. Considering the main method for this program was a test class
I've spent the past month writing and perfecting. While I didn't get the chance to test
every single scenario possible, I feel like I got enough to know that my program won't
break. The user can't really change much and if they do tamper with the test class it will
cause issues. but if they tamper with the other two classes, the tester will either 
handle the exceptions or show that the extra code works. The program handles bad input very well
and is easy to use considering it just runs without user input so it has the one function it will do
after being compiled.


DISCUSSION:

Close to 90% of the time spent on this project is with the list iterators add method.
I've had around 5 different list iterator add methods all together with odd results being the ones that 
passed the most test. Simply calling addToRear and increasing the itermodcount was my most successful for a week.
It failed all but one test scenario, and i knew that it would fail more test I can't think of.
It took countless hours and iterations of the add method to get one that finally worked.As of right now, my 
tester class is passing all 6800 test so i hope it continues on its perfect path.


 The rest of the project wasn't overly challenging and I was able to get the rest without
 much struggle. No other method took me more than 45 minutes to debug. unlike the 
 list iterator add method which has been almost 5 days in the works.
 
 
EXTRA CREDIT:

Theirs a redemption opportunity after my first attempt at this project.


----------------------------------------------------------------------------


