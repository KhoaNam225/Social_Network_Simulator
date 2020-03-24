/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   UnitTestPerson.java
 * Date modified: 22/10/2019
 * Purpose:     Unit test for DSALinkedList class
 */
public class UnitTestDSALinkedList
{
    public static void main(String[] args)
    {
        DSALinkedList list = new DSALinkedList();
        int testNum = 20;

        // Inserting
        System.out.println();
        System.out.printf("Inserting %d numbers into the list (10: insertFirst - 10: insertLast)\n", testNum);
        for (int i = 0; i < testNum / 2; i++)
        {
            list.insertFirst(i);
        }

        for (int i = testNum / 2; i < testNum; i++)
        {
            list.insertLast(i);
        }
        System.out.printf("Finished inserting %d numbers into the list\n\n", testNum);

        // Iterator
        System.out.println("Using iterator to print all the numbers.");
        System.out.print("Expected Sequence: ");

        for (int i = testNum / 2 - 1; i >= 0; i--)
        {
            System.out.print(i + " - ");
        }

        for (int i = testNum / 2; i < testNum; i++)
        {
            System.out.print(i + " - ");
        }
        System.out.println();
        System.out.print("Actual Sequence: ");
        for (Object item : list)
        {
            System.out.print(item + " - ");
        }

        System.out.println();

        System.out.printf("\nPeeking and Removing %d numbers from the head\n", testNum / 2);
        for (int i = testNum / 2 - 1; i >= 0; i--)
        {
            System.out.printf("Peek: %d - %d\n", i, list.peekFirst());
            System.out.printf("Remove: %d - %d\n", i, list.removeFirst());
        }

        System.out.println();
        System.out.printf("\nPeeking and Removing %d numbers from the tail\n", testNum / 2);
        for (int i = testNum - 1; i >= testNum / 2; i--)
        {
            System.out.printf("Peek: %d - %d\n", i, list.peekLast());
            System.out.printf("Remove: %d - %d\n", i, list.removeLast());
        }

        System.out.printf("\nChecking emptiness after removing all %d numbers\n", testNum);
        System.out.println("isEmtpy - Expected = true, Actual = " + list.isEmpty());

        System.out.println("\nChecking removing one item from head and tail when the list is empty\n");
        try
        {
            System.out.print("Remove first: ");
            list.removeFirst();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            System.out.print("Remove last: ");
            list.removeLast();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            System.out.print("Peek first: ");
            list.peekFirst();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            System.out.print("Peek last: ");
            list.peekLast();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e.getMessage());
        }


    }
}
