/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   SocialSim.java
 * Date modified: 19/10/2019
 * Purpose:     This class contains the function main to run the program.
 * It handles all the interaction with the user such as: prompt
 * for input, output message, etc (much like a user interface class).
 * Once the user input a choice, it will call a proper method from the Network class
 * to perform the action.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SocialSim
{
    // All the options in the main menu
    public static final int LOAD_NETWORK = 1;
    public static final int SET_FOLLOW_PROBS = 2;
    public static final int SET_LIKE_PROBS = 3;
    public static final int NODE_OPS = 4;
    public static final int EDGE_OPS = 5;
    public static final int NEW_POST = 6;
    public static final int DISPLAY_NET = 7;
    public static final int DISPLAY_STATS = 8;
    public static final int UPDATE = 9;
    public static final int SAVE_NETWORK = 10;
    public static final int EXIT = 11;

    // All the options in node operation
    public static final int FIND_PERSON = 1;
    public static final int ADD_PERSON = 2;
    public static final int REMOVE_PERSON = 3;
    public static final int EXIT_NODE_OPS = 4;

    // All the options in edge operation
    public static final int FOLLOW = 1;
    public static final int UNFOLLOW = 2;
    public static final int EXIT_EDGE_OPS = 3;

    /**
     * Print the program usage on the terminal
     *
     * @return - A string containing the usage of the program
     */
    private static String usage()
    {
        String str = "";
        str += "\nUsage: java SocialSim <mode> [network_file] [events_file] [like_prob] [follow_prob]\n\n";
        str += "\tWhere:\n";
        str += "\t\t<mode> is one of the following modes to run the program: \n";
        str += "\t\t\t-i:     run the program in interative mode\n";
        str += "\t\t\t-s:     run the program in simulation mode\n";
        str += "\n\tIf running in simulation mode, the input files and probabilites are needed\n\n";
        str += "\t\tThey are:\n";
        str += "\t\t\tnetwork_file:   The file containing the names of people in the network and connection between them\n";
        str += "\t\t\tevents_file:    The file containing all the events that need to be analysed\n";
        str += "\t\t\tlike_prob:      The probability that a person likes a post\n";
        str += "\t\t\tfollow_prob:    The probability that a person follows another person\n";

        return str;
    }

    /**
     * Prints the main menu in interactive mode
     *
     * @return - A string containing the main menu
     */
    private static String menu()
    {
        String str = "";
        str += "\nSelect one of the following options: \n";
        str += "1. Load network\n";
        str += "2. Set follow probability\n";
        str += "3. Set like probability\n";
        str += "4. Node operations\n";
        str += "5. Edge operations\n";
        str += "6. New post\n";
        str += "7. Display the network\n";
        str += "8. Display the statistics\n";
        str += "9. Update\n";
        str += "10.Save the network\n";
        str += "11.Exit\n";

        return str;
    }

    /**
     * Prompt user for inputting an integer between min and max.
     * Keeps prompting until the user enter a valid input
     *
     * @param prompt - The string used to promp the user
     * @param min    - The minimum accepted value
     * @param max    - The maximum accepted value
     * @return - The value input by the user
     */
    private static int integerInput(String prompt, int min, int max)
    {
        Scanner sc = new Scanner(System.in);
        int value = min - 1;
        String outStr = prompt;

        do
        {
            try
            {
                System.out.print(outStr);
                value = sc.nextInt();
                outStr = "!!! Value out of range! Please enter a number between " + min + " and " + max;
                outStr += "\n" + prompt;
            }
            catch (InputMismatchException e)
            {
                outStr = "!!! Error! Invalid data type! Please enter an integer!";
                outStr += "\n" + prompt;
                value = min - 1;
                sc.nextLine();
            }
        }
        while (value < min || value > max);

        return value;
    }

    /**
     * Prompt user for inputting a real number between min and max.
     * Keeps prompting until the user enter a valid input
     *
     * @param prompt - The string used to prompt the user
     * @param min    - The minimum accepted value
     * @param max    - The maximum accepted value
     * @return - The value input by the user
     */
    private static double realInput(String prompt, double min, double max)
    {
        Scanner sc = new Scanner(System.in);
        double value = min - 1;
        String outStr = prompt;

        do
        {
            try
            {
                System.out.print(outStr);
                value = sc.nextDouble();
                outStr = "!!! Value out of range! Please enter a number between " + min + " and " + max;
                outStr += "\n" + prompt;
            }
            catch (InputMismatchException e)
            {
                outStr = "!!! Error! Invalid data type! Please enter an integer!";
                outStr += "\n" + prompt;
                value = min - 1;
                sc.nextLine();
            }
        }
        while (value < min || value > max);

        return value;
    }

    /**
     * Prompt user for inputting a string
     *
     * @param prompt - The prompt string
     * @return - The string input by the user
     */
    private static String stringInput(String prompt)
    {
        Scanner sc = new Scanner(System.in);
        String str;
        System.out.print(prompt);
        str = sc.nextLine();

        return str;
    }

    /**
     * Prints the main menu in interactive mode and get the input from user
     *
     * @return - The user choice
     */
    private static int getMenuInput()
    {
        int choice;
        System.out.println(menu());
        choice = integerInput("==> Your choice: ", LOAD_NETWORK, EXIT);

        return choice;
    }

    /**
     * Perform all the node operations (base on user's choice)
     * and updates the given network
     *
     * @param network - The network to perform action on
     */
    private static void performNodeOps(Network network)
    {
        int choice;

        do
        {
            // All the options in node ops
            System.out.println("\nPlease choose one of the following node operations:");
            System.out.println("\t1. Find a person");
            System.out.println("\t2. Add a new person");
            System.out.println("\t3. Remove a person");
            System.out.println("\t4. Back to main menu");

            choice = integerInput("==> Your choice: ", FIND_PERSON, EXIT_NODE_OPS);

            // Take action base on user's choice
            switch (choice)
            {
                case FIND_PERSON:
                    findPerson(network);
                    break;

                case ADD_PERSON:
                    addPerson(network);
                    break;

                case REMOVE_PERSON:
                    removePerson(network);
                    break;

                default:
                    break;
            }
        }
        while (choice != EXIT_NODE_OPS);
    }

    /**
     * Performs all the edge operation base on user's choice
     * and make proper changes to the given network
     *
     * @param network - The network
     */
    private static void performEdgeOps(Network network)
    {
        int choice;

        do
        {
            // All the options in edge operation
            System.out.println("\nSelect one of the following edge operations: ");
            System.out.println("\t1. Follow");
            System.out.println("\t2. Unfollow");
            System.out.println("\t3. Back to main menu");

            choice = integerInput("==> Your choice: ", FOLLOW, EXIT_EDGE_OPS);
            // Take actions base on user's choice
            switch (choice)
            {
                case FOLLOW:
                    follow(network);
                    break;

                case UNFOLLOW:
                    unfollow(network);
                    break;

                default:
                    break;
            }
        } while (choice != EXIT_EDGE_OPS);
    }

    /**
     * Add a new post to the given network
     *
     * @param network - The network
     */
    private static void addPost(Network network)
    {
        String owner = stringInput("\n==> Enter the name of the owner of the post: ");
        String content = stringInput("\n==> Enter the content of the post: ");

        try
        {
            network.addPost(owner, content);
            System.out.println("\n==> Successfully added the post!");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\n!!! " + e.getMessage());
        }
    }

    /**
     * Display the given network
     *
     * @param network - The network to be displayed
     */
    private static void displayNetwork(Network network)
    {
        try
        {
            System.out.println("\n==> DISPLAYING THE NETWORK\n");
            System.out.println(network.display());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\n!!! " + e.getMessage());
        }
    }

    /**
     * Display the statistics about a network
     *
     * @param network - The network to be displayed
     */
    private static void displayStats(Network network)
    {
        try
        {
            System.out.println("\n==> DISPLAYING THE STATISTICS\n");
            System.out.println(network.displayStats());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\n!!! " + e.getMessage());
        }
    }

    /**
     * Update the network after a post has been added
     * (Spreading the post)
     *
     * @param network - The network to be updated
     */
    private static void update(Network network)
    {
        try
        {
            boolean step = true;

            network.update(step, 1.0, null);
            System.out.println("\n==> Finished updating the network.");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\n!!! " + e.getMessage());
        }
    }

    /**
     * Save the given network to an object file (Serialize it).
     *
     * @param network - The given network to be saved
     */
    private static void saveNetwork(Network network)
    {
        String filename = stringInput("==> Enter file name to save the network: ");
        try
        {
            FileIO.serializeNetwork(filename, network);
            System.out.println("\n==> Successfully saved the network.\n");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\n!!! " + e.getMessage());
        }
    }

    /**
     * Load the network from an object file
     *
     * @return - The network loaded from the object file
     */
    private static Network loadNetwork()
    {
        String filename = stringInput("==> Enter file name to load the network: ");
        Network network = null;
        try
        {
            network = FileIO.readObjectFile(filename);
            System.out.println("\n==> Successfully loaded the network");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\n==> " + e.getMessage());
        }

        // If the network cannot be read
        if (network == null)
        {
            System.out.println("\n==> Failed to load the network");
            System.out.println("Keep the state of the old network");
        }

        return network;
    }

    /**
     * Find person in the network and prints the information of that person
     *
     * @param network - The network to perform the search
     */
    private static void findPerson(Network network)
    {
        String name = stringInput("\n==> Enter the name of the person: ");

        Person person = network.findPerson(name);
        // If not found the person
        if (person == null)
        {
            System.out.println("\n==> The person " + name + " does not exist in the network\n");
        }
        else
        {
            System.out.println("\n==> Found the person:\n");
            System.out.println(person.toString());
        }
    }

    /**
     * Add a new person to the network
     *
     * @param network - The network to be updated
     */
    private static void addPerson(Network network)
    {
        String name = stringInput("\n==> Enter the name of the new person: ");
        try
        {
            network.addPerson(name);
            System.out.println("==> Successfully added the person to the network");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\n!!! " + e.getMessage());
        }
    }

    /**
     * Remove a person from the given network
     *
     * @param network - The network to be updated
     */
    private static void removePerson(Network network)
    {
        String name = stringInput("\n==> Enter the name of the person to be removed: ");
        try
        {
            // try removing the person
            network.removePerson(name);
            System.out.println("\n==> Successfully removed the person from the network");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\n!!! " + e.getMessage());
        }
    }

    /**
     * Perform a following operation in the given network
     *
     * @param network - The network to be updated
     */
    private static void follow(Network network)
    {
        // The follower
        String follower = stringInput("\n==> Enter follower name: ");
        // The follow-ee
        String followed = stringInput("==> Enter the person who is followed: ");

        try
        {
            // Perform the action and output the result
            network.follow(follower, followed);
            System.out.println("\n==> Successfully perform edge operation!");
            System.out.println("==> " + follower + " now follows " + followed + ".");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\n!!! " + e.getMessage());
        }
    }

    /**
     * Perform an unfollowing operation in the given network
     *
     * @param network - The network to be updated
     */
    private static void unfollow(Network network)
    {
        // The follower
        String follower = stringInput("\n==> Enter the follower name: ");
        // The follow-ee
        String followed = stringInput("==> Enter the person who is unfollowed: ");

        try
        {
            // Perform the operation and output a proper message
            network.unfollow(follower, followed);
            System.out.println("==> " + follower + " now no longer follows " + followed + ".");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("\n!!! " + e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        // If no argument is provided, output the usage
        if (args.length == 0)
        {
            System.out.println(usage());
        }
        else
        {
            // Initialize an empty network to be updated later on
            System.out.println("\n==> Initializing an empty network...");
            Network network = new Network();
            System.out.println("==> Finished initializing an empty network...");
            System.out.println("==> The default probabilities are: ");
            System.out.println("==>     Follow: 0.5");
            System.out.println("==>     Like: 0.5\n");

            // Interactive mode
            if (args[0].equals("-i"))
            {
                int menuChoice;
                do
                {
                    // Get menu input
                    menuChoice = getMenuInput();

                    // Take proper action base on the input
                    switch (menuChoice)
                    {
                        // Load network from object file
                        case LOAD_NETWORK:
                            Network newNetwork = loadNetwork();
                            // If the network is successfully loaded
                            // update the old network
                            // otherwise keep the old state
                            if (newNetwork != null)
                            {
                                network = newNetwork;
                            }
                            break;

                        // Update the following prob
                        case SET_FOLLOW_PROBS:
                            double followProb = realInput("==> Please enter following probability: ", 0.0, 1.0);
                            network.setFollowProb(followProb);
                            System.out.println("\n==> Successfully set the following probability");
                            break;

                        // Update the like prob
                        case SET_LIKE_PROBS:
                            double likeProb = realInput("==> Please enter like probability: ", 0.0, 1.0);
                            network.setLikeProb(likeProb);
                            System.out.println("\n==> Successfully set the like probability");
                            break;

                        // User choose to perform node operation
                        case NODE_OPS:
                            performNodeOps(network);
                            break;

                        // Edge operation
                        case EDGE_OPS:
                            performEdgeOps(network);
                            break;

                        // Add a new post
                        case NEW_POST:
                            addPost(network);
                            break;

                        // Display the network as an adjacency list
                        case DISPLAY_NET:
                            displayNetwork(network);
                            break;

                        // Display the statistics
                        case DISPLAY_STATS:
                            displayStats(network);
                            break;

                        // Spread the newest post
                        case UPDATE:
                            update(network);
                            break;

                        // Save network to an object file
                        case SAVE_NETWORK:
                            saveNetwork(network);
                            break;

                        // Exit the program
                        case EXIT:
                            System.out.println("\n==> Goodbye!\n");
                            break;
                    }
                }
                while (menuChoice != EXIT);
            }
            // Simulation mode
            else if (args[0].equals("-s"))
            {
                // In simulation mode, we need 4 more arguments (5 in total)
                // the network file, the event file, the like prob, the follow prob
                if (args.length != 5)
                {
                    System.out.println("!!! Error! Invalid number of arguments, please refer to the usage");
                }
                else
                {
                    FileOutputStream outputStream = null;
                    PrintWriter pw = null;
                    try
                    {
                        // Take the information from arguments
                        String networkFile = args[1];
                        String eventFile = args[2];
                        double probLike = Double.parseDouble(args[3]);
                        double probFoll = Double.parseDouble(args[4]);

                        // Generate a log file name to save the outputs
                        String logfile = FileIO.generateLogFileName(networkFile, eventFile);
                        outputStream = new FileOutputStream(logfile);
                        pw = new PrintWriter(outputStream);
                        network = new Network(probLike, probFoll);   // Create a new network from the given data

                        pw.printf("Like prob: %.2f\n", probLike);
                        pw.printf("Follow prob: %.2f\n\n", probFoll);
                        System.out.println("==> Reading the network file");

                        // Build a Runtime object to get the memory usage
                        Runtime runtime = Runtime.getRuntime();

                        long startNetworkTime = System.currentTimeMillis(); // Start the timing
                        FileIO.readNetworkFile(network, networkFile, pw);   // Read the network file and output to the log
                        long finishedNetworkTime = System.currentTimeMillis(); // End the timing

                        System.out.println("==> Finished processing the network file");

                        System.out.println("==> Reading the event file");

                        long startEventTime = System.currentTimeMillis();  // Start the timing
                        FileIO.readEventFile(network, eventFile, pw);  // Read the event file and output to the log
                        long finishedEventTime = System.currentTimeMillis(); // End the timing

                        System.out.println("==> Finished processing the event file");

                        System.out.println("\n==> Successfully saved the log.");
                        System.out.println("==> The log file is: " + logfile);

                        //FileIO.serializeNetwork("saved.dat", network);
                        //System.out.println("\n==> Finished reading the files, displaying the network\n");
                        //displayNetwork(network);  // Display the network after reading the file

                        System.out.println("\n==> TIMING:");
                        System.out.printf("==> Reading the network file: %d milliseconds\n", finishedNetworkTime - startNetworkTime);
                        System.out.printf("==> Reading the event file: %d milliseconds\n", finishedEventTime - startEventTime);

                        pw.close();
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.out.println("!!! " + e.getMessage());
                    }
                    catch (IOException e)
                    {
                        if (outputStream != null)
                        {
                            try
                            {
                                outputStream.close();
                            }
                            catch (IOException e2)
                            {
                            }
                        }
                        System.out.println("!!! Cannot create log file.");
                    }
                }
            }
            else
            {
                // If the user enter an invalid mode (something rather than -i and -s)
                System.out.println("!!! Unsupported mode!");
                System.out.println("!!! Please run the program with interactive or simulation mode");
            }
        }
    }
}
