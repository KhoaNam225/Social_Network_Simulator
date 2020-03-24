/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   FileIO.java
 * Date Modified: 19/10/2019
 * Purpose:     This class is used to handle all the operation related to File Input/Output
 * such as:
 * - Load network from object file (in interactive mode)
 * - Save network to object file (in interactive mode)
 * - Read and construct network from network file (in simulation mode)
 * - Read and perform events from event file (in simulation mode)
 */

import java.io.*;
import java.util.Calendar;

public class FileIO
{
    /**
     * All the operations in the event file
     */
    public static final char ADD_PERSON = 'A';  // Add a person
    public static final char ADD_POST = 'P';   // Add a new post
    public static final char FOLLOW = 'F';     // A person follows another person
    public static final char UNFOLLOW = 'U';   // A person unfollows another person
    public static final char REMOVE = 'R';     // Remove a person from the network

    /**
     * Load the network from an object file when given the file name
     *
     * @param filename - The object file name
     * @return - The constructed network
     */
    public static Network readObjectFile(String filename)
    {
        Network network = null;
        FileInputStream inputStream = null;
        ObjectInputStream objStream = null;

        try
        {
            // Open the stream and read the network
            inputStream = new FileInputStream(filename);
            objStream = new ObjectInputStream(inputStream);

            network = (Network) objStream.readObject();

            objStream.close();
        }
        // If the file is not found
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
        // The file is found but something wrong happened when reading the data
        catch (IOException e)
        {
            throw new IllegalArgumentException("Error when reading the object file");
        }
        // Cannot cast the read object to the Network class
        catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Class Network not found");
        }

        return network;
    }

    /**
     * Save (serialized) the given network to an object file
     *
     * @param filename - The destination file
     * @param network  - The network to be serialized
     */
    public static void serializeNetwork(String filename, Network network)
    {
        // Cannot serialized a null network
        if (network == null)
        {
            throw new IllegalArgumentException("Cannot save null object.");
        }

        FileOutputStream outputStream = null;
        ObjectOutputStream objStream = null;

        try
        {
            // open the stream and serialized the network
            outputStream = new FileOutputStream(filename);
            objStream = new ObjectOutputStream(outputStream);

            objStream.writeObject(network);

            objStream.close();
        }
        // If anything wrong happen when serializing the network
        catch (IOException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Reads the network file and makes proper proper changes to an empty network base on the content of
     * the file
     *
     * @param network  - The network perform operation on
     * @param filename - The name of the network file
     * @param pw       - The PrintWriter object to prints the proper message to indicate the action taken
     *                 This parameter is used in simulation mode (when we want to output the message to a file)
     *                 In simulation mode, this parameter is null to indicate that all the messages will be
     *                 displayed on the terminal.
     */
    public static void readNetworkFile(Network network, String filename, PrintWriter pw)
    {
        FileInputStream inputStream = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try
        {
            // Open the stream and read the network file
            inputStream = new FileInputStream(filename);
            reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);


            String line = bufferedReader.readLine();
            // Keep reading until the end of the file
            while (line != null)
            {
                try
                {
                    // process each line in the file and output a proper message
                    processNetworkLine(network, line, pw);
                }
                catch (IllegalArgumentException e)
                {
                    // If anything wrong happened, output a proper error message
                    pw.println("!!! " + e.getMessage() + "\n");
                }
                line = bufferedReader.readLine();
            }

            inputStream.close();
        }
        // Cannot find the file
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException("Network file not found");
        }
        catch (IOException e)
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e2)
                {
                }
            }
            throw new IllegalArgumentException("Error when reading the network file");
        }
    }

    /**
     * Reads the event file and makes proper changes to the given network.
     *
     * @param network  - The network to be updated
     * @param filename - The name of the event file
     * @param pw       - The PrintWriter object to prints the proper message to indicate the action taken
     *                 This parameter is used in simulation mode (when we want to output the message to a file)
     *                 In simulation mode, this parameter is null to indicate that all the messages will be
     *                 displayed on the terminal.
     */
    public static void readEventFile(Network network, String filename, PrintWriter pw)
    {
        FileInputStream inputStream = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try
        {
            // Open the stream and start reading
            inputStream = new FileInputStream(filename);
            reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();
            // Keep reading until the end of the file
            while (line != null)
            {
                try
                {
                    // Process each line and take proper action
                    processEventLine(network, line, pw);

                }
                catch (IllegalArgumentException e)
                {
                    pw.println("\n!!! " + e.getMessage());
                }
                line = bufferedReader.readLine();
            }

            inputStream.close();
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException("Event file not found");
        }
        catch (IOException e)
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e2)
                {
                }
            }
            throw new IllegalArgumentException("Error when reading the event file");
        }
    }

    /**
     * Generate a log file name base on the current date time and the names of the
     * network file and the event file.
     * The log file name will have the format:
     * networkfile_eventfile_HOUR_MINUTE_SECOND_DAY-MONTH-YEAR.log
     *
     * @param networkFile - The name of the network file
     * @param eventFile   - The name of the event file
     * @return - The name of the log file
     */
    public static String generateLogFileName(String networkFile, String eventFile)
    {
        // Get the current date time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        // Construct a proper file name
        String logfile = networkFile + "_" + eventFile + "_" + hour + ":" + minute + ":" + second + "_" + date + "-" + month + "-" + year + ".log";
        return logfile;
    }

    /**
     * Process each line in the network file and make proper changes to the given network
     *
     * @param network - The network
     * @param line    - The line in the network file
     * @param pw      - The PrintWriter object to prints the proper message to indicate the action taken
     *                This parameter is used in simulation mode (when we want to output the message to a file)
     *                In simulation mode, this parameter is null to indicate that all the messages will be
     *                displayed on the terminal.
     */
    private static void processNetworkLine(Network network, String line, PrintWriter pw)
    {
        String[] infos = line.split(":");
        // If the line has only 1 element, that is the name of a new person to be added to the network
        if (infos.length == 1)
        {
            network.addPerson(infos[0]);
            pw.println("==> NET: Added " + infos[0] + " to the network\n");
        }
        // If there are two elements, that indicates a person follows another person in the network
        else if (infos.length == 2)
        {
            String follower = infos[1];
            String followed = infos[0];
            try
            {
                network.follow(follower, followed);
            }
            catch (IllegalArgumentException e)
            {
                throw new IllegalArgumentException("NET: " + e.getMessage() + "\n!!! Error at line: " + line);
            }
            pw.println("==> NET: " + follower + " follows " + followed + "\n");  // Output a proper message
        }
        // The line has an incorrect format
        else
        {
            throw new IllegalArgumentException("NET: Invalid line format in network file.\n!!! Error at line: " + line);
        }
    }

    /**
     * Process each line in the event file and make proper changes to the given network
     *
     * @param network - The network
     * @param line    - The line in the network file
     * @param pw      - The PrintWriter object to prints the proper message to indicate the action taken
     *                This parameter is used in simulation mode (when we want to output the message to a file)
     *                In simulation mode, this parameter is null to indicate that all the messages will be
     *                displayed on the terminal.
     */
    private static void processEventLine(Network network, String line, PrintWriter pw)
    {
        String[] infos = line.split(":");

        // If the first elements has more than 1 character => incorrect format
        if (infos[0].length() != 1)
        {
            throw new IllegalArgumentException("\nEVE: Invalid line format in event file.\n!!! Error at line: " + line);
        }

        char action = infos[0].charAt(0);  // Take the action
        switch (action)
        {
            // If the line indicates "ADD PERSON"
            case ADD_PERSON:
                // to add a person, we only need 1 names of the new people
                if (infos.length != 2)
                {
                    throw new IllegalArgumentException("\nEVE: Invalid line format in event file.\n!!! Error at line: " + line);
                }

                try
                {
                    // Add person and output proper message to the log
                    network.addPerson(infos[1]);
                    pw.println("\n==> EVE: Added " + infos[1] + " to the network");
                }
                catch (IllegalArgumentException e)
                {
                    throw new IllegalArgumentException("\nEVE: Error! " + e.getMessage() + "\n!!! Error at line: " + line);
                }
                break;

            // Add a new post
            case ADD_POST:
                // We need the name of the owner and the content of the post
                // The clickbait factor is optional (default value is 1)
                if (infos.length != 3 && infos.length != 4)
                {
                    throw new IllegalArgumentException("\nEVE: Invalid line format in event file.\n!!! Error at line: " + line);
                }

                try
                {
                    double clickBait;
                    network.addPost(infos[1], infos[2]); // Add a new post to the network
                    boolean step = false;  // Keep spreading the post without asking after each time step
                    pw.println("\n==> EVE: " + infos[1] + " posted: " + infos[2]);  // Output proper message
                    // Clickbait factor detected
                    if (infos.length == 4)
                    {
                        clickBait = Double.parseDouble(infos[3]);
                        pw.printf("==> EVE: Clickbait factor detected. Clickbait value: %.2f. Like prob for this post now is: %.2f\n", clickBait, network.getLikeProb() * clickBait);
                    }
                    else
                    {
                        clickBait = 1.0;  // Default values for clickbait factor is 1.0
                        pw.println("==> EVE: No clickbait factor detected.");
                    }
                    // Spread the post with the given clickBait factor and output the message to the log
                    network.update(step, clickBait, pw);
                    printPopPersonInfo(network, pw);   // Output the information about the most popular person
                    printMostPopPostInfo(network, pw); // Output the information about the most popular post
                }
                catch (IllegalArgumentException e)
                {
                    throw new IllegalArgumentException("Error! " + e.getMessage() + "\n!!! Error at line: " + line);
                }
                break;

            // Follow event
            case FOLLOW:
                // We need two people in this event
                if (infos.length != 3)
                {
                    throw new IllegalArgumentException("EVE: Invalid line format in event file.\n!!! Error at line: " + line);
                }

                try
                {
                    // Update the network and output a message
                    network.follow(infos[2], infos[1]);
                    pw.println("\n==> EVE: " + infos[2] + " follows " + infos[1]);
                }
                catch (IllegalArgumentException e)
                {
                    throw new IllegalArgumentException("EVE: Error! " + e.getMessage() + "\n!!! Error at line: " + line);
                }
                break;

            // Unfollow event
            case UNFOLLOW:
                // Same with follow event, we need 2 people
                if (infos.length != 3)
                {
                    throw new IllegalArgumentException("EVE: Invalid line format in event file.\n!!! Error at line: " + line);
                }

                try
                {
                    // Update the network and output the message
                    network.unfollow(infos[2], infos[1]);
                    pw.println("\n==> EVE: " + infos[2] + " unfollows " + infos[1]);
                }
                catch (IllegalArgumentException e)
                {
                    throw new IllegalArgumentException("EVE: Error! " + e.getMessage() + "\n!!! Error at line: " + line);
                }
                break;

            // Remove a person from the network
            case REMOVE:
                // We need a person, the one which will be removed from the network
                if (infos.length != 2)
                {
                    throw new IllegalArgumentException("EVE: Invalid line format in event file.\n!!! Error at line: " + line);
                }

                try
                {
                    // Update the network and remove the person
                    network.removePerson(infos[1]);
                    pw.println("\n==> EVE: Removed " + infos[1] + " from the network.");
                }
                catch (IllegalArgumentException e)
                {
                    throw new IllegalArgumentException("EVE: Error! " + e.getMessage() + "\n!!! Error at line: " + line);
                }
                break;

            default:
                // If the line contain a wrong letter indicating an unrecognized event
                throw new IllegalArgumentException("EVE: Error! Unrecognized event in event file.\n!!! Error at line: " + line);
        }
    }

    /**
     * Prints the information about the most popular person in the network to a file
     *
     * @param network - The network
     * @param pw      - The PrintWriter used to print the information to a file
     */
    private static void printPopPersonInfo(Network network, PrintWriter pw)
    {
        Person mostPopPer = network.mostPopPerson();  // Get the most popular person
        DSALinkedList followers = null;
        // Output the information of the person
        // This includes:
        // - The name
        // - The number of followers
        // - The name of the followers
        if (mostPopPer.hasAnyFollower())
        {
            followers = mostPopPer.getFollowers();
        }
        pw.println("\n==> Most popular person so far: ");
        pw.println("*** Name: " + mostPopPer.getName());
        pw.println("*** Number of followers: " + mostPopPer.getFollowerCount());
        pw.print("*** Followers: ");
        // If we don't have any follower, output "N/A"
        if (followers == null)
        {
            pw.println("N/A");
        }
        else
        {
            for (Object item : followers)
            {
                Person person = (Person) item;
                pw.print(person.getName() + ", ");
            }
            pw.println();
        }
    }

    /**
     * Prints the information of the most popular post in the network to a file
     *
     * @param network - The network
     * @param pw      - The PrintWriter object used to print the information
     */
    private static void printMostPopPostInfo(Network network, PrintWriter pw)
    {
        try
        {
            // Get the most popular post and print it to a file
            // The information includes:
            // - Name of the person who posted
            // - The content of the post
            // - The number of likes of the post
            Post mostPopPost = network.mostPopPost();
            pw.println("\n==> Most popular post so far: ");
            pw.println("*** Name: " + mostPopPost.getOwner());
            pw.println("*** Content: " + mostPopPost.getContent());
            pw.println("*** Likes: " + mostPopPost.getLikeCount());
        }
        // If there is no post in the network yet
        catch (IllegalArgumentException e)
        {
            pw.println("==> No posts in the network.");
        }
    }
}
