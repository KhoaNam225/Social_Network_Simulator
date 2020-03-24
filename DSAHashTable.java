/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   DSAHashTable.java
 * Date modified: 22/10/2019
 * Purpose:     Implementation of the Hash Table ADT using double hashing method
 * to handle collision.
 * <p>
 * NOTE:    This class is a REUSED class from the Practical 6.
 * However there are some additional methods in this class such as:
 * - values()
 * - isEmpty()
 */

import java.io.Serializable;

public class DSAHashTable implements Serializable
{
    public static final int DEFAULT_TABLE_SIZE = 7;  // The default table size if no table size specified
    public static final int MAX_STEP = 5;   // The maximum step in probing
    public static final double MAX_LOAD_FACTOR = 0.6; // The maximum load factor to maintain the performance
    public static final double MIN_LOAD_FACTOR = 0.25;  // The minimum load factor to avoid wasting space

    private DSAHashEntry[] hashArray;  // The table
    private int count;   // The number of entries so far

    /**
     * Alternate constructor.
     * Creates a HashTable with a given table size.
     *
     * @param tableSize - The initial size for the table
     */
    public DSAHashTable(int tableSize)
    {
        // The table must have at least 1 element
        if (tableSize < 1)
        {
            throw new IllegalArgumentException("Invalid table size.");
        }

        int actualSize = nextPrime(tableSize);  // Make sure the table size is prime number
        hashArray = new DSAHashEntry[actualSize];
        for (int i = 0; i < actualSize; i++)
        {
            hashArray[i] = new DSAHashEntry();
        }

        count = 0;
    }

    /**
     * Computes and returns the load factor
     */
    public double getLoadFactor()
    {
        return (double) count / (double) hashArray.length;
    }

    /**
     * Returns all the values stored in the table as a LinkedList.
     * If the table is empty, throws an exception.
     *
     * @return - The list containing all the values of the table
     */
    public DSALinkedList values()
    {
        if (isEmpty())
        {
            throw new IllegalArgumentException("Hash Table is empty.");
        }

        DSALinkedList list = new DSALinkedList();

        // Iterate through the table and insert each item to the list
        for (int i = 0; i < hashArray.length; i++)
        {
            if (hashArray[i].state == DSAHashEntry.USED)
            {
                list.insertLast(hashArray[i].value);
            }
        }

        return list;
    }

    /**
     * Checks if the table is empty or not
     *
     * @return - True if the table is empty or False otherwise
     */
    public boolean isEmpty()
    {
        return count == 0;
    }

    /**
     * Checks if the item with the given key is in the table
     *
     * @param key - The given key used to check
     * @return - true if the key is in the table or false otherwise
     */
    public boolean hasKey(String key)
    {
        int position = findKey(key);
        return position != -1;
    }

    /**
     * Puts a new item with the given key into the table.
     *
     * @param inKey   - The key used to compute the hash index
     * @param inValue - The value of the item
     */
    public void put(String inKey, Object inValue)
    {
        if (inKey == null)
        {
            throw new IllegalArgumentException("Null key adding to table.");
        }

        // If the key is already in the table
        if (hasKey(inKey))
        {
            throw new IllegalArgumentException("Key is already in the table");
        }

        DSAHashEntry entry = new DSAHashEntry(inKey, inValue);
        put(entry);
    }

    /**
     * Displays the table with the format <key>-<value> pairs.
     */
    public void display()
    {
        for (int i = 0; i < hashArray.length; i++)
        {
            DSAHashEntry entry = hashArray[i];
            if (entry.state == DSAHashEntry.USED)
            {
                System.out.println(entry.key + " - " + entry.value.toString());
            }
        }
    }

    /**
     * Get an item with the given key from the table without removing it
     * from the table.
     * If the key is not in the table, throws an exception.
     *
     * @param key - The key associate with the value
     * @return - The value if it is in the table
     */
    public Object get(String key)
    {
        if (key == null)
        {
            throw new IllegalArgumentException("Cannot search null Key");
        }

        Object value;
        int position = findKey(key); // Position of the key in the table

        // If not found
        if (position == -1)
        {
            throw new IllegalArgumentException("Key not found in the table");
        }
        else
        {
            value = hashArray[position].value;
        }

        return value;
    }

    /**
     * Removes an item associate with the given key from the table.
     * If the key is not in the table, throws an exception.
     *
     * @param key - The key of the item.
     */
    public void removeKey(String key)
    {
        if (key == null)
        {
            throw new IllegalArgumentException("Cannot remove null key");
        }

        int position = findKey(key); // Position of the key in the table

        // If not found in the table
        if (position == -1)
        {
            throw new IllegalArgumentException("Key not found in the table");
        }

        DSAHashEntry entry = hashArray[position];
        // Remove the hash entry, have to set the state to FOMERLY_USED
        entry.state = DSAHashEntry.FORMERLY_USED;
        count--;

        // If the load factor is too low
        if (hashArray.length > DEFAULT_TABLE_SIZE && getLoadFactor() < MIN_LOAD_FACTOR)
        {
            int newSize = previousPrime(hashArray.length);
            double newLoadFactor = (double) count / (double) newSize;
            while (count != 0 && newLoadFactor < MIN_LOAD_FACTOR)
            {
                newSize = previousPrime(newSize);
                newLoadFactor = (double) count / (double) newSize;
            }

            resize(newSize);
        }
    }

    /**
     * Returns a string representing the items stored in the table.
     * The string is in CSV format.
     *
     * @return - A CSV formatted string representing the values of items in the table
     */
    public String toString()
    {
        String str = "";
        for (int i = 0; i < hashArray.length; i++)
        {
            DSAHashEntry entry = hashArray[i];
            if (entry.state == DSAHashEntry.USED)
            {
                str += entry.key + "," + entry.value.toString() + "\n";
            }
        }

        return str;
    }

    /**
     * Inserts a new entry to the table.
     *
     * @param entry - The entry to be inserted
     */
    private void put(DSAHashEntry entry)
    {
        // If the table is already full
        if (count == hashArray.length)
        {
            throw new IllegalArgumentException("Table is full");
        }

        String key = entry.key;
        int hashIndex = hash(key);  // The index
        int step = stepHash(key);  // The step to resolve collision

        // Keep probing until getting to an empty entry
        while (hashArray[hashIndex].state == DSAHashEntry.USED)
        {
            hashIndex += step;
            hashIndex = hashIndex % hashArray.length;
        }

        hashArray[hashIndex] = entry;
        count++;

        // If exceed maximum allowed load factor, double the size
        if (getLoadFactor() > MAX_LOAD_FACTOR)
        {
            int newSize = nextPrime(hashArray.length);
            while ((double) count / (double) newSize > MAX_LOAD_FACTOR)
            {
                newSize = nextPrime(newSize);
            }
            resize(newSize);
        }
    }

    /**
     * Base on the given key, computes and returns the position of that key
     * in the table.
     * If the key is not found in the table, returns -1
     *
     * @param key - The given key used to search for the item
     * @return - The index of the key in the table or -1 if the key is not in the table
     */
    private int findKey(String key)
    {
        int hashIndex = hash(key); // hash the key
        int step = stepHash(key);
        boolean found = false;   // found or not?
        int result = -1;    // the index of the key in the hash table if found
        DSAHashEntry entry = hashArray[hashIndex];   // The current entry

        // Keep searching until found the key or reach to a never used entry
        while (!found && entry.state != DSAHashEntry.NEVER_USED)
        {
            // If found
            if (entry.key.equals(key) && entry.state == DSAHashEntry.USED)
            {
                found = true;
                result = hashIndex;
            }
            // else jump to the next entry
            else
            {
                hashIndex = (hashIndex + step) % hashArray.length;
                entry = hashArray[hashIndex];
            }
        }

        return result;
    }

    /**
     * Base on the given key, computes and returns the hash index
     */
    private int hash(String inKey)
    {
        long hashIndex = 0;
        for (int i = 0; i < inKey.length(); i++)
        {
            // The number 11 is used hear to avoid integer overflow
            hashIndex = (11 * hashIndex) + inKey.charAt(i);
        }

        return (int) (hashIndex % hashArray.length);
    }

    /**
     * Base on the given key, computes and return the hash step.
     * This hash step is used in double hashing to handle collision.
     */
    private int stepHash(String inKey)
    {
        int hashIndex = hash(inKey);
        return MAX_STEP - (hashIndex % MAX_STEP);
    }

    /**
     * Finds the next prime number of the given number
     */
    private int nextPrime(int number)
    {
        int primeVal;  // The next prime number

        // If the given number is even, decrease it by 1
        // so that we could check the next odd value after (in the do-while loop)
        if (number % 2 == 0)
        {
            primeVal = number - 1;
        }
        else
        {
            primeVal = number;
        }

        boolean isPrime = false;
        do
        {
            primeVal += 2;  // The next odd number
            isPrime = true;
            int i = 3;
            double rootVal = Math.sqrt(primeVal);  // No need to check values above the square root

            // Check for every odd number below the current odd number
            while (isPrime && i <= rootVal)
            {
                if (primeVal % i == 0)
                {
                    isPrime = false;
                }
                else
                {
                    i += 2;  // No need to check for the even numbers
                }
            }
        }
        while (!isPrime);

        return primeVal;
    }

    /*
     * Find the previous prime number of the given number
     */
    private int previousPrime(int number)
    {
        int primeVal;  // The previous prime number

        // If the given number is even, increase it by 1
        // so that we could check the previous odd value after (in the do-while loop)
        if (number % 2 == 0)
        {
            primeVal = number + 1;
        }
        else
        {
            primeVal = number;
        }

        boolean isPrime = false;
        do
        {
            primeVal -= 2;  // The previous odd number
            isPrime = true;
            int i = 3;
            double rootVal = Math.sqrt(primeVal);  // No need to check values above the square root

            // Check for every odd number below the current odd number
            while (isPrime && i <= rootVal)
            {
                if (primeVal % i == 0)
                {
                    isPrime = false;
                }
                else
                {
                    i += 2;  // No need to check for the even numbers
                }
            }
        }
        while (!isPrime);

        return primeVal;
    }

    /**
     * Resize the hash array if the load factor is too high or too low
     */
    private void resize(int newSize)
    {
        DSAHashEntry[] temp = hashArray;
        // Reset everything
        this.hashArray = new DSAHashEntry[newSize];
        this.count = 0;

        for (int i = 0; i < newSize; i++)
        {
            hashArray[i] = new DSAHashEntry();
        }

        // Re-hash every entry to the new table
        for (int i = 0; i < temp.length; i++)
        {
            if (temp[i].state == DSAHashEntry.USED)
            {
                put(temp[i]);
            }
        }
    }

    /**
     * Private class representing entries in the hash table
     */
    private class DSAHashEntry implements Serializable
    {
        /**
         * ALl the states that an entry can have
         */
        public static final int NEVER_USED = 0;
        public static final int USED = 1;
        public static final int FORMERLY_USED = -1;

        private String key;
        private Object value;
        private int state;

        public DSAHashEntry()
        {
            key = "";
            value = null;
            state = NEVER_USED;
        }

        public DSAHashEntry(String inKey, Object inValue)
        {
            key = inKey;
            value = inValue;
            state = USED;
        }
    }
}
