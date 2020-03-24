/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   DSAHeap.java
 * Date modified: 19/10/2019
 * Purpose:     The implementation of the Heap ADT.
 * NOTE:  This class is a REUSED class from the Practical 7
 */

import java.io.Serializable;

public class DSAHeap implements Serializable
{
    private DSAHeapEntry[] heap;  // The heap array to store the data
    private int count;  // The number of elements stored

    /**
     * Alternate constructor.
     * Construct the heap with a given maximum size
     *
     * @param maxSize - The maximum size of the heap
     */
    public DSAHeap(int maxSize)
    {
        count = 0;
        heap = new DSAHeapEntry[maxSize];
    }

    /**
     * Add a new item with the associate priority to the heap and
     * place it in the correct position base on the priority.
     *
     * @param priority - The priority of the new item
     * @param value    - The new item
     */
    public void add(int priority, Object value)
    {
        // If the heap is full
        if (count == heap.length)
        {
            throw new IllegalArgumentException("The heap is already full.");
        }

        DSAHeapEntry newEntry = new DSAHeapEntry(priority, value);
        // Add the new entry to the end of the heap and trickle it up
        heap[count] = newEntry;
        trickleUp(count);  // Place it in the right position
        count++;
    }

    /**
     * Take the item at the top of the heap.
     * In this implementation, it's the item with highest priority.
     *
     * @return - The item at the top of the heap
     */
    public Object remove()
    {
        // Can't take item from an item heap
        if (count == 0)
        {
            throw new IllegalArgumentException("The heap is empty.");
        }

        Object temp = heap[0].value;  // The item to be taken out
        // Swap the top item with the lowest level item
        // Then move the new top item to the right position
        swap(0, count - 1);
        count--;
        trickleDown(0);

        return temp;
    }

    /**
     * Sorts an integer array using a heap
     *
     * @param arr - The array that needs to be sorted
     */
    public void sort(int[] arr)
    {
        heapSort(arr);
    }

    /**
     * Checks if the heap is empty or not
     *
     * @return - true if the heap is empty or false otherwise
     */
    public boolean isEmpty()
    {
        return count == 0;
    }

    /**
     * Checks if the heap is full or not
     *
     * @return - true if the heap is full or false otherwise
     */
    public boolean isFull()
    {
        return count == heap.length;
    }

    /**
     * Converts the integer array to be sorted to an HeapEntry array
     * (so that it can be used in the heap)
     *
     * @param arr - The integer array
     * @return - The HashEntry version of the given integer array
     */
    private DSAHeapEntry[] convertToHashEntry(int[] arr)
    {
        DSAHeapEntry[] entries = new DSAHeapEntry[arr.length];
        for (int i = 0; i < arr.length; i++)
        {
            entries[i] = new DSAHeapEntry(arr[i], arr[i]);
        }

        return entries;
    }

    /**
     * Heapify an integer array
     *
     * @param arr - The integer array to be heapified
     */
    private void heapify(int[] arr)
    {
        this.heap = convertToHashEntry(arr);
        this.count = arr.length;
        for (int i = arr.length / 2 - 1; i >= 0; i--)
        {
            trickleDown(i);
        }
    }

    /**
     * Sort an integer array using heap sort
     *
     * @param arr - The integer array to be sorted
     */
    private void heapSort(int[] arr)
    {
        int temp = count;
        heapify(arr);

        for (int i = heap.length - 1; i >= 0; i--)
        {
            swap(i, 0);
            count--;
            trickleDown(0);
        }

        count = temp;

        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = (int) heap[i].value;
        }
    }

    // Trickle up item at a given index
    private void trickleUp(int index)
    {
        if (index > 0)
        {
            int parent = (index - 1) / 2;
            if (less(heap[parent], heap[index]))
            {
                swap(parent, index);
            }
            trickleUp(parent);
        }
    }

    // Trickle down item at a given index
    private void trickleDown(int index)
    {
        if (index < count)
        {
            int leftChild = index * 2 + 1;
            int largeChild = leftChild;

            // If there is a left child
            if (leftChild < count)
            {
                // If there is a right child
                int rightChild = index * 2 + 2;
                // Choose the larger child between the 2 children node
                if (rightChild < count && less(heap[leftChild], heap[rightChild]))
                {
                    largeChild = rightChild;
                }

                // If the larger child is also larger than the parent, swap them
                if (less(heap[index], heap[largeChild]))
                {
                    swap(index, largeChild);
                }

                // Keep going down the tree
                trickleDown(largeChild);
            }
        }
    }

    // String representation of the heap
    public String toString()
    {
        String str = "";
        for (int i = 0; i < count; i++)
        {
            str += "[" + i + "] " + heap[i].priority + " - " + heap[i].value.toString() + "\n";
        }

        return str;
    }

    // Compare to entry
    private boolean less(DSAHeapEntry h1, DSAHeapEntry h2)
    {
        boolean less = false;
        less = h1.priority < h2.priority;

        return less;
    }

    // Swaps 2 entry at 2 position in the array
    private void swap(int i, int j)
    {
        DSAHeapEntry temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * The implementation of the HeapEntry
     */
    private class DSAHeapEntry implements Serializable
    {
        private int priority;
        Object value;

        public DSAHeapEntry(int priority, Object value)
        {
            this.priority = priority;
            this.value = value;
        }
    }
}
