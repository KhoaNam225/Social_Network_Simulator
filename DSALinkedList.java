/**
 * Name:    Khoa Nam Pham
 * StudentID:   19305875
 * File name:   DSALinkedList.java
 * Date Modified: 19/10/2019
 * Purpose:     Implementation of double-ended doubly-linked list
 * NOTE:        This class is a REUSED class from the Practical 3
 */

import java.io.Serializable;
import java.util.Iterator;

public class DSALinkedList implements Iterable, Serializable
{
    private DSAListNode head;  // The node at the front
    private DSAListNode tail;  // The node at the end

    /**
     * Default constructor.
     * An empty list has both head and tail node being null.
     */
    public DSALinkedList()
    {
        this.head = null;
        this.tail = null;
    }

    /**
     * Checks if the list is empty or not
     *
     * @return - true if the list is empty or false otherwise
     */
    public boolean isEmpty()
    {
        return this.head == null;
    }

    /**
     * Insert a new item at the start of the linked list
     *
     * @param inValue - The item to be inserted
     */
    public void insertFirst(Object inValue)
    {
        DSAListNode newNode = new DSAListNode(inValue);
        if (isEmpty())   // If the list is emtpy, set both the head and tail to the new node
        {
            head = newNode;
            tail = newNode;
        }
        // Updates the links at the start of the list
        else
        {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
    }

    /**
     * Insert a new item at the end of the linked list
     *
     * @param inValue - The item to be inserted
     */
    public void insertLast(Object inValue)
    {
        DSAListNode newNode = new DSAListNode(inValue);
        if (isEmpty())   // If the list is empty, set both the head and tail to the new node
        {
            head = newNode;
            tail = newNode;
        }
        // Update the links at the end of the list
        else
        {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    /**
     * Get the item at the start of the list but not remove it from the list.
     *
     * @return - The item at the start of the list
     */
    public Object peekFirst()
    {
        // Cannot take item from an empty list
        if (isEmpty())
        {
            throw new IllegalStateException("Error: Calling peekFirst() on empty list.");
        }

        Object outItem = head.value;
        return outItem;
    }

    /**
     * Get the item at the start of the list but not remove it from the list.
     *
     * @return - The item at the start of the list
     */
    public Object peekLast()
    {
        // Cannot take item from an empty list
        if (isEmpty())
        {
            throw new IllegalStateException("Error: Calling peekLast() on empty list.");
        }

        Object outItem = tail.value;
        return outItem;
    }

    /**
     * Remove the first item from the list and return it.
     *
     * @return - The item at the start of the list
     */
    public Object removeFirst()
    {
        // Cannot take item from an empty list
        if (isEmpty())
        {
            throw new IllegalStateException("Error: Calling removeFirst() on empty list.");
        }

        Object outItem = head.value;
        head = head.next;

        // If the list is empty after removing, set the tail to null
        // because the tail still point to the last item.
        if (head == null)
        {
            tail = null;
        }
        else
        {
            head.prev = null;
        }

        return outItem;
    }

    /**
     * Remove the last item from the list and return it.
     *
     * @return - The item at the start of the list
     */
    public Object removeLast()
    {
        // Cannot take item from an empty list
        if (isEmpty())
        {
            throw new IllegalStateException("Error: Calling removeLast() on empty list.");
        }

        Object outItem = tail.value;
        tail = tail.prev;

        // If the list is empty after removing, set the head to null
        // because the head still point to the last item.
        if (tail == null)
        {
            head = null;
        }
        else
        {
            tail.next = null;
        }

        return outItem;
    }

    /**
     * Return the iterator of the list.
     *
     * @return - The iterator to iterate through the list
     */
    public Iterator iterator()
    {
        return new DSAListIterator(this);
    }

    /**
     * ListNode implementation.
     * Each node will have a value and the reference to the next and previous node in the list.
     */
    private class DSAListNode implements Serializable
    {
        public Object value;
        public DSAListNode next;  // Next node in the list
        public DSAListNode prev;  // Previous node in the list

        public DSAListNode(Object inValue)
        {
            if (inValue == null)
            {
                throw new IllegalArgumentException("Create ListNode with null value.");
            }

            value = inValue;
            next = null;
            prev = null;
        }
    }

    /**
     * The iterator implementation, the remove operation is not supported.
     */
    private class DSAListIterator implements Iterator, Serializable
    {
        private DSAListNode current;

        // When first created, the iterator will point at the first item
        public DSAListIterator(DSALinkedList theList)
        {
            current = theList.head;
        }

        public boolean hasNext()
        {
            return current != null;
        }

        // Remove operation is not supported
        public void remove()
        {
            throw new UnsupportedOperationException("Not supported operation.");
        }

        // Returns the current item and point to the next item
        public Object next()
        {
            Object outItem;
            if (!hasNext())
            {
                outItem = null;
            }
            else
            {
                outItem = current.value;
                current = current.next;
            }

            return outItem;
        }
    }
}
