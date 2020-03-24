/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   UnitTestPerson.java
 * Date modified: 22/10/2019
 * Purpose:     Unit test for DSAQueue class
 */
public class UnitTestDSAQueue
{
    public static void main(String[] args)
    {
        int capac = 10;
        System.out.printf("\nTesting initializing and inserting 10 numbers into the queue.\n");
        // Testing queque initialization
        DSAQueue queue = new DSAQueue();

        // Test adding items into the queue
        for (int i = 0; i < capac; i++)
        {
            queue.enqueue(i);
        }
        System.out.println("Finished initializing and inserting 10 numbers into the queue.");

        System.out.println("\nTesting the iterator");
        System.out.println("Print all the numbers using foreach operator:");
        for (Object item : queue)
        {
            System.out.print(item + " ");
        }

        // Checking the size of the queue and also emptiness
        System.out.printf("\nChecking the size of the queue\n");
        System.out.printf("Expected: %d \nActual: %d\n", capac, queue.count());

        System.out.printf("\nChecking emptiness\n");
        System.out.println("Expected: false \nActual: " + queue.isEmpty());


        // Test taking items out of the queue
        System.out.println("\nGetting 5 numbers out of the queue");
        for (int i = 0; i < capac / 2; i++)
        {
            System.out.println("Peek - Exptected: " + i + " = Actual: " + queue.peek());
            System.out.println("Dequeue - Expected: " + i + " = Actual: " + queue.dequeue());
        }


        // Check the size after taking all the items out
        System.out.println("\nChecking the size of the queue");
        System.out.println("Expected: 5 = Actual: " + queue.count());

        // Empty the queues
        System.out.println("\nEmpty the queue");
        for (int i = capac / 2; i < capac; i++)
        {
            System.out.println("Peek - Exptected: " + i + " = Actual: " + queue.peek());
            System.out.println("Dequeue - Expected: " + i + " = Actual: " + queue.dequeue());
        }

        System.out.println("\nChecking emptiness");
        System.out.println("Expected: true = Actual: " + queue.isEmpty());

        // Calling dequeue and peek with empty queue
        System.out.println("\nCALLING dequeue() WITH EMPTY QUEUE: ");
        try
        {
            queue.dequeue();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Actual message: " + e.getMessage());
        }

        System.out.println("\nCALLING peek() WITH EMPTY QUEUE: ");
        try
        {
            queue.peek();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Actual message: " + e.getMessage());
        }
    }
}
