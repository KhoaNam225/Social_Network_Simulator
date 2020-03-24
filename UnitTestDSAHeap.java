/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   UnitTestPerson.java
 * Date modified: 22/10/2019
 * Purpose:     Unit test for DSAHeap class
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class UnitTestDSAHeap
{
    private static int getLineNum(String filename)
    {
        FileInputStream inputStream = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        int count = 0;
        try
        {
            inputStream = new FileInputStream(filename);
            reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();
            count = 0;
            while (line != null)
            {
                count++;
                line = bufferedReader.readLine();
            }

            inputStream.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return count;
    }

    private static int[] readFile(String filename)
    {
        FileInputStream inputStream = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        int[] arr = null;

        try
        {
            inputStream = new FileInputStream(filename);
            reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);
            int lineNum = getLineNum(filename);

            arr = new int[lineNum];

            String line = bufferedReader.readLine();
            int i = 0;
            while (line != null)
            {
                String[] info = line.split(",");
                arr[i] = Integer.parseInt(info[0]);
                line = bufferedReader.readLine();
                i++;
            }

            inputStream.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return arr;
    }

    public static void main(String[] args)
    {
        String[] values = {"John", "Tom", "Hank", "Suzi", "JoJo"};
        int[] priority = {1, 2, 3, 4, 5};

        System.out.println("\nInitializing the heap with size " + values.length);
        DSAHeap heap = new DSAHeap(values.length);
        System.out.println("Finished initializing the heap");

        System.out.println("\nInserting the following values along with their priorities:");
        for (int i = 0; i < values.length; i++)
        {
            System.out.println(values[i] + " - " + priority[i]);
            heap.add(priority[i], values[i]);
        }
        System.out.println("Finished adding 5 values to the heap");

        System.out.println("\nThe content of the heap is:");
        System.out.println(heap.toString());

        System.out.println("Try adding more stuffs to the heap\n");
        System.out.println("Adding Anna - 6");
        try
        {
            heap.add(6, "Anna");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("\nRemoving the content of the heap one by one...");
        for (int i = 0; i < values.length; i++)
        {
            Object value = heap.remove();
            System.out.println(value.toString());
        }

        System.out.println("\nTry removing when the heap is empty");
        try
        {
            heap.remove();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage() + "\n");
        }

        System.out.println("\nReads and sorts the students ID from input7000.csv...\n");
        int[] arr = readFile("input7000.csv");
        heap.sort(arr);
        for (int i = 0; i < arr.length; i++)
        {
            System.out.println(arr[i]);
        }
        System.out.println("Finished sorting the array\n");
    }
}
