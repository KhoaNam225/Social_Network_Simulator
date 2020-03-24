/**
 * Author:  Khoa Nam Pham
 * StudentID:   19305875
 * File name:   DSAQueue.java
 * Date modified: 19/10/2019
 * Purpose:     Queue implementation using DSALinkedList
 * NOTE:        This class is a REUSED class from Practical 3
 */

import java.io.Serializable;
import java.util.Iterator;

public class DSAQueue implements Iterable, Serializable
{
    private DSALinkedList list;  // The list to store items
    private int count;  // The number of items stored

    /**
     * Default constructor.
     * An empty queue will have an empty list.
     */
    public DSAQueue()
    {
        list = new DSALinkedList();
        count = 0;
    }

    /**
     * Checks if the queue is empty or not
     *
     * @return - returns true if the queue is empty, otherwise return false
     */
    public boolean isEmpty()
    {
        return count == 0;
    }

    /**
     * Get the total number of items currently stored in the queue
     *
     * @return - the number of items inside the queue
     */
    public int count()
    {
        return count;
    }

    /**
     * Enqueues a new item into the queue.
     *
     * @param inItem - The item to be enqueued
     */
    public void enqueue(Object inItem)
    {
        // Cannot add a null item
        if (inItem == null)
        {
            throw new IllegalArgumentException("Calling enqueue() with null object.");
        }

        list.insertLast(inItem);
        count++;
    }

    /**
     * Takes the item at the front and removes it from the queue
     *
     * @return - The item at the front of the queue
     */
    public Object dequeue()
    {
        // Cannot take item from an empty queue
        if (isEmpty())
        {
            throw new IllegalArgumentException("Calling dequeue() with empty queue.");
        }

        Object outItem = list.removeFirst();
        count--;

        return outItem;
    }

    /**
     * Gets an item at the front of the queue but not removing it.
     *
     * @return - The item at the front
     */
    public Object peek()
    {
        // Cannot take item from an empty queue
        if (isEmpty())
        {
            throw new IllegalArgumentException("Calling peek() with empty queue.");
        }

        Object outItem = list.peekFirst();
        return outItem;
    }

    /**
     * Returns an iterator to iterates through the queue
     *
     * @return - The iterator
     */
    public Iterator iterator()
    {
        return list.iterator();
    }
}
