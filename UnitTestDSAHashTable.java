/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   UnitTestPerson.java
 * Date modified: 22/10/2019
 * Purpose:     Unit test for DSAHashTable class
 */
public class UnitTestDSAHashTable
{
    public static void main(String[] args)
    {
        System.out.println("\nInitializing the hash table...\n");
        DSAHashTable table = new DSAHashTable(5);
        System.out.println("\nFinished initializing the hash table with minimum size of 5...\n");

        String[] keys = {"John", "Sara", "Mike", "Matthew", "Jack"};
        int[] values = {1, 2, 3, 4, 5};

        System.out.println("Putting 5 key-value pairs into the table...");
        for (int i = 0; i < keys.length; i++)
        {
            table.put(keys[i], values[i]);
        }
        System.out.println("Finished putting 5 keys, the table's content is: ");
        table.display();

        System.out.println("\nTry inserting duplicate keys");
        System.out.println("Insert " + keys[0]);
        try
        {
            table.put(keys[0], values[0]);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("\nInsert " + keys[1]);
        try
        {
            table.put(keys[1], values[1]);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("\nFind every key in the table and display the value found:");
        for (int i = 0; i < keys.length; i++)
        {
            System.out.printf("%s: %s\n", keys[i], table.get(keys[i]).toString());
        }

        System.out.printf("\nLoad factor: %.2f - Size: %d\n", table.getLoadFactor());

        System.out.printf("\nRemoving the first 2 keys: \n");
        for (int i = 0; i < keys.length - 3; i++)
        {
            System.out.println("Delete " + keys[i]);
            table.removeKey(keys[i]);
            System.out.println("\nTable's content after removing " + keys[i]);
            table.display();
            System.out.printf("Load factor: %.2f - Size: %d\n\n", table.getLoadFactor());
        }

        System.out.println("Has key " + keys[4] + "? " + table.hasKey(keys[4]));
        System.out.println("Has key " + keys[1] + "? " + table.hasKey(keys[1]));
        System.out.println("Has key " + keys[0] + "? " + table.hasKey(keys[0]));
    }
}
