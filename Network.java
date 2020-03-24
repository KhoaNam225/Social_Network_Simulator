/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   Network.java
 * Date modified: 19/10/2019
 * Purpose:     This class represents the social network.
 * The idea used to build this class is similar to the DSAGraph class.
 * The network is actually a Graph in which each Person will be a vertex in the graph.
 * Inside each Person object, we store a linked list of the people who follow that person
 * (This is like the adjacency list in the Graph).
 * To spread the post across the network, we use breadth first search algorithm to bring it
 * to all the followers, if the followers decide to like the post, we bring the post to the
 * followers of that follower (bring it 1 level further from the source).
 */

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Scanner;

public class Network implements Serializable
{
    public static final double DEFAULT_LIKE_PROB = 0.5;  // The default value for like prob
    public static final double DEFAULT_FOLLOW_PROB = 0.5;  // Default value for follow prob

    private DSALinkedList people;   // The people in the network
    private double likeProb;    // The probability of liking a post
    private double followProb;  // The probability of following a person
    private int peopleNum;    // The number of people in the network

    // Assume the update() method only runs with the most recent post and post-er
    private Person mostRecentPoster;   // The most recent person who posted a post
    private Post mostRecentPost;    // The most recent post added to the network

    /**
     * Default constructor.
     * A default empty network will have like prob and follow prob to be 0.5 (both)
     */
    public Network()
    {
        people = new DSALinkedList();
        // If the probabilities are not provided, set them to 0.5
        likeProb = DEFAULT_LIKE_PROB;
        followProb = DEFAULT_FOLLOW_PROB;
        peopleNum = 0;
        mostRecentPost = null;
        mostRecentPoster = null;
    }

    /**
     * Alternate Constructor
     *
     * @param inLikeProb   - The probability of like a post
     * @param inFollowProb - The probability of following a person
     */
    public Network(double inLikeProb, double inFollowProb)
    {
        // Like prob and follow prob must be non-negative
        if (inLikeProb < 0)
        {
            throw new IllegalArgumentException("Like probability cannot be negative");
        }

        if (inFollowProb < 0)
        {
            throw new IllegalArgumentException("Follow probability cannot be negative");
        }

        people = new DSALinkedList();
        likeProb = inLikeProb;
        followProb = inFollowProb;
        peopleNum = 0;
        mostRecentPost = null;
        mostRecentPoster = null;
    }

    // =================== ALL THE GETTERS =====================
    public double getFollowProb()
    {
        return followProb;
    }

    public double getLikeProb()
    {
        return likeProb;
    }

    // This getter returns a queue that contains all the people in descending order of
    // popularity. (i.e The person with the most followers will appear first in the queue
    public DSAQueue getPeople()
    {
        // Continuously adding people to a heap using the number of followers of the priority
        // Since the heap is a max heap, the person at the root of the heap will be the most
        // popular in the network
        DSAHeap heap = new DSAHeap(peopleNum);
        DSAQueue queue = new DSAQueue();

        for (Object item : people)
        {
            Person person = (Person) item;
            heap.add(person.getFollowerCount(), person);
        }

        // Removing person from the heap and add to the queue
        // After this process, the queue will be in sorted order (in terms of popularity)
        while (!heap.isEmpty())
        {
            queue.enqueue(heap.remove());
        }

        return queue;
    }

    // This getter returns a queue containing all the posts in the network
    // All the posts in the queue are stored in descending order of their popularity (the number of likes)
    public DSAQueue getPosts()
    {
        // Continuously adding posts to a heap using the number of followers of the priority
        // Since the heap is a max heap, the post at the root of the heap will be the most
        // popular in the network
        DSAQueue posts = new DSAQueue();
        DSAHeap heap = new DSAHeap(getPostsCount());

        for (Object item : people)
        {
            Person person = (Person) item;
            if (person.hasAnyPosts())
            {
                for (Object postItem : person.getPosts())
                {
                    Post post = (Post) postItem;
                    heap.add(post.getLikeCount(), post);
                }
            }
        }

        // Removing person from the heap and add to the queue
        // After this process, the queue will be in sorted order (in terms of popularity)
        while (!heap.isEmpty())
        {
            posts.enqueue(heap.remove());
        }

        return posts;
    }

    /**
     * Returns the most popular person in the network.
     * If the network is empty, returns null.
     *
     * @return - The most popular person in the network
     */
    public Person mostPopPerson()
    {
        Person person = null;
        DSAQueue queue = getPeople();  // Get all people in descending order of their popularity
        if (!queue.isEmpty())
        {
            person = (Person) queue.dequeue();
        }

        return person;
    }

    /**
     * Returns the most popular post in the network.
     * If there are no posts in the network, returns null.
     *
     * @return - The most post popular in the network
     */
    public Post mostPopPost()
    {
        Post post = null;
        DSAQueue queue = getPosts();  // Get all the posts in descending order of popularity
        if (!queue.isEmpty())
        {
            // The post in the front of the queue will be the most popular one
            post = (Post) queue.dequeue();
        }

        return post;
    }

    // Change the probability of liking a post
    public void setLikeProb(double inLikeProb)
    {
        // Cannot set the like prob to a negative number
        if (inLikeProb < 0)
        {
            throw new IllegalArgumentException("Like Probability cannot be negative");
        }

        likeProb = inLikeProb;
    }

    // Change the probability of following a person
    public void setFollowProb(double inFollowProb)
    {
        // Cannot set the follow prob to a negative number
        if (inFollowProb < 0)
        {
            throw new IllegalArgumentException("Follow Probability cannot be negative");
        }

        followProb = inFollowProb;
    }

    /**
     * Add a new person to the network.
     * Assuming all the people in the network have unique names.
     * Therefore, if the name of the new person to be added is the same as a name
     * of a person in a network, throws an error.
     *
     * @param name - The name of the new person
     */
    public void addPerson(String name)
    {
        // 2 people in the network cannot have the same names
        if (!hasPerson(name))
        {
            Person newPerson = new Person(name);
            people.insertLast(newPerson);
            peopleNum++;
        }
        else
        {
            throw new IllegalArgumentException("Person " + name + " already in the network.");
        }
    }

    /**
     * Remove a person from the network given the name.
     * If the person does not exist in the network, throws an error.
     *
     * @param name - The name of the person to be removed
     */
    public void removePerson(String name)
    {
        Person person = findPerson(name);  // First find the person

        // If he is in the network
        if (person != null)
        {
            if (person.isFollowingAnyPerson())
            {
                DSALinkedList following = person.getFollowing();  // The people this person is following

                // Remove this person from the follower list of all the people
                // that he/she is following
                for (Object item : following)
                {
                    Person followed = (Person) item;
                    followed.removeFollower(person);
                }
            }

            if (person.hasAnyFollower())
            {
                DSALinkedList followers = person.getFollowers();  // The people who follow this person

                // Remove this person from the following list of all his/her followers
                for (Object item : followers)
                {
                    Person follower = (Person) item;
                    follower.unfollow(person);
                }
            }

            DSALinkedList newList = new DSALinkedList();
            // Remove the person from the list of people in the network
            for (Object item : people)
            {
                Person p = (Person) item;
                if (!p.getName().equals(name))
                {
                    newList.insertLast(p);
                }
            }
            people = newList;
        }
        else
        {
            throw new IllegalArgumentException("Person " + name + " is not in the network.");
        }
    }

    /**
     * Add a new post when given the name of the owner of the post and the content of the post.
     *
     * @param owner       - The name of the owner
     * @param postContent - The content of the new post
     */
    public void addPost(String owner, String postContent)
    {
        Person person = findPerson(owner);

        // If the owner is in the network
        if (person == null)
        {
            throw new IllegalArgumentException("Person " + owner + " is not in the network.");
        }

        Post post = createPost(person, postContent);
        person.addPost(post);
        // Change the most recent post and the post-er
        mostRecentPost = post;
        mostRecentPoster = person;
    }

    /**
     * A person follows another person in the network.
     *
     * @param follower - The name of the follower
     * @param followed - The name of the person who is followed
     */
    public void follow(String follower, String followed)
    {
        Person followerPer = findPerson(follower);
        Person followedPer = findPerson(followed);

        // If either of the given name exist in the network
        if (followedPer == null)
        {
            throw new IllegalArgumentException("Person " + followed + " is not in the network.");
        }

        if (followerPer == null)
        {
            throw new IllegalArgumentException("Person " + follower + " is not in the network.");
        }

        followerPer.follow(followedPer);
    }

    /**
     * A person unfollows another person in the network.
     *
     * @param follower - The name of the follower
     * @param followed - The name of the person who is followed
     */
    public void unfollow(String follower, String followed)
    {
        Person followerPer = findPerson(follower);
        Person followedPer = findPerson(followed);

        // If either of the name exist in the network
        if (followedPer == null)
        {
            throw new IllegalArgumentException("Person " + followed + " is not in the network.");
        }

        if (followerPer == null)
        {
            throw new IllegalArgumentException("Person " + follower + " is not in the network.");
        }

        followerPer.unfollow(followedPer);
    }

    /**
     * Display the network as an adjacency list.
     * The information to be display is:
     * - The people in the network along with their followers
     *
     * @return - A string representing the network.
     */
    public String display()
    {
        if (people.isEmpty())
        {
            throw new IllegalArgumentException("The network is empty.");
        }

        String str = "";
        str += "Like prob: " + likeProb + "\n";
        str += "Follow prob: " + followProb + "\n";
        // For each person in the network, display their names
        // and the name of all their followers
        for (Object item : people)
        {
            Person person = (Person) item;
            str += person.getName() + " --> ";
            if (person.hasAnyFollower())
            {
                DSALinkedList followers = person.getFollowers();
                for (Object obj : followers)
                {
                    Person follower = (Person) obj;
                    str += follower.getName() + ", ";
                }
            }
            str += "\n";
        }

        return str;
    }

    /**
     * Display the statistic about the network.
     * All the information to be display is:
     * - The people in the network in order of their popularity
     * - The posts in the network in order of their popularity
     *
     * @return - A string representing all the necessary information
     */
    public String displayStats()
    {
        String str = "";
        // Cannot display the statistics of an empty network
        if (people.isEmpty())
        {
            throw new IllegalArgumentException("The network is empty");
        }

        str += "**** DISPLAYING PEOPLE IN DESCENDING ORDER OF POPULARITY ****\n";
        str += peopleStat();  // The statistics about people
        str += "\n\n";
        str += "**** DISPLAYING POSTS IN DECSCENDING ORDER OF POPULARITY ****\n";
        str += postStats();  // The statistics about posts

        return str;
    }

    /**
     * Update the network after a post has been added.
     * This method will spreads the post across the network
     * (along the people who follower the owner of the post) and make any appropriate change
     * if someone likes the post and/or decides to follow the owner.
     *
     * @param step      - The mode to spread the post, if step = true, after each timestep, the program will pause
     *                  and ask the user to continue another timestep or to stop completely, otherwise the program
     *                  will keep spreading the post as far as possible.
     * @param clickBait - The clickBait factor
     * @param pw        - The PrintWriter object used to output the message to an external file. If pw is not null,
     *                  all the message will be output to a file, otherwise they will be displayed on the terminal
     */
    public void update(boolean step, double clickBait, PrintWriter pw)
    {
        if (mostRecentPoster == null || mostRecentPost == null)
        {
            throw new IllegalArgumentException("There are no new posts");
        }
        spreadPost(mostRecentPoster, mostRecentPost, step, clickBait, pw);  // Spread the post with STEP mode turned on
    }

    /**
     * Displays the statistic about all the posts in the network.
     * The statistics are:
     * - The owner of the post
     * - Content of the post
     * - The number of likes of each post
     *
     * @return - The string representing the statistics about the posts.
     */
    public String postStats()
    {
        // Cannot get statistics with an empty network
        if (people.isEmpty())
        {
            throw new IllegalArgumentException("The network is empty");
        }

        DSAQueue posts = getPosts(); // Get all the posts in ascending order of popularity
        String str = "";
        if (!posts.isEmpty())
        {
            // For each post, append its information to the returned string
            // The information includes:
            // - The person who posted it
            // - The content of the post
            // - The number of like of each post
            for (Object item : posts)
            {
                Post post = (Post) item;
                str += post.getOwner() + "\n";
                str += post.toString() + "\n";
                str += "Like: " + post.getLikeCount() + "\n";
                str += "\n";
            }
        }
        else
        {
            str = "There are no posts in the network";
        }

        return str;
    }

    /**
     * Display the statistics about all the people in the network.
     * The information included is:
     * - The name of the person
     * - The number of followers of each person
     * - The people who are following the person
     * - The people this person is following
     *
     * @return - The String representing the statistics
     */
    public String peopleStat()
    {
        if (people.isEmpty())
        {
            throw new IllegalArgumentException("The network is empty");
        }

        // Get all the people in the network in descending order of popularity
        DSAQueue queue = getPeople();
        String str = "";
        for (Object item : queue)
        {
            // For each person in the network, display the information of that person
            // This includes:
            // - The person'name
            // - The number of followers
            // - The name of people who follows the person
            Person person = (Person) item;
            str += person.getName() + "\n";
            str += "Number of followers: " + person.getFollowerCount() + "\n";
            str += "Follower: ";

            // If this person has any followers
            if (person.hasAnyFollower())
            {
                DSALinkedList followers = person.getFollowers();
                for (Object obj : followers)
                {
                    Person follower = (Person) obj;
                    str += follower.getName() + " ";
                }
                str += "\n";
            }
            else
            {
                str += "N/A\n";  // Otherwise, just say N/A
            }

            str += "Following: ";
            // If this person is following any people
            if (person.isFollowingAnyPerson())
            {
                DSALinkedList following = person.getFollowing();
                for (Object obj : following)
                {
                    Person followed = (Person) obj;
                    str += followed.getName() + " ";
                }
                str += "\n";
            }
            else
            {
                str += "N/A";
            }

            str += "\n\n";
        }

        return str;
    }

    /**
     * Checks if the given name exists in the network.
     *
     * @param name - The person that needs checking
     * @return - True if this person exists in the network or false otherwise
     */
    public boolean hasPerson(String name)
    {
        return findPerson(name) != null;
    }

    /**
     * Finds the given person (by name) in the network.
     * If the person does not exist in the network, returns null otherwise returns a Person object.
     *
     * @param name - The name of the person that needs searching
     * @return - Null if the person does not exist in the network otherwise returns a Person object.
     */
    public Person findPerson(String name)
    {
        Person person = null;
        boolean found = false;
        Iterator iterator = people.iterator();

        // While not found that person and haven't reached the end of the list
        while (iterator.hasNext() && !found)
        {
            person = (Person) iterator.next();
            found = person.getName().equals(name);
        }

        if (!found)
        {
            person = null;
        }

        return person;
    }

    // The total number of post in the network
    private int getPostsCount()
    {
        int postCount = 0;
        for (Object item : people)
        {
            Person person = (Person) item;
            postCount += person.getPostCount();
        }

        return postCount;
    }

    /**
     * Spreads a post across the network through the follower of the owner of the post.
     * If someone likes the post, prints a message indicates that event.
     * If someone follows the post, prints a message indicates that event.
     *
     * @param owner - The owner of the post
     * @param post  - The post
     * @param step  - The mode to spread the post. If it is turned on, after each time step
     *              (i.e After the post went one level further away from the source), the program will stop
     *              and ask user to enter 'y' to continue. If it is turned off, the program just try to spread the post
     *              as far as it can.
     */
    private void spreadPost(Person owner, Post post, boolean step, double clickBait, PrintWriter pw)
    {
        // Use the breadth first search algorithm to spread the post out
        Scanner sc = new Scanner(System.in);
        String continued = "";
        owner.visit();  // A person cannot likes his/her own post
        DSAQueue queue = new DSAQueue();
        queue.enqueue(owner);

        while (!queue.isEmpty() && continued.equals(""))
        {
            Person current = (Person) queue.dequeue();

            // Show the post to every followers
            for (Object neighbor : current.getFollowers())
            {
                Person follower = (Person) neighbor;

                // If the follower has not seen the post before and decides to like it,
                // show the post to all of his/her followers
                if (follower.decideLike(likeProb * clickBait) && !follower.isVisited())
                {
                    if (pw != null)
                    {
                        pw.println("*** " + follower.getName() + " likes the post");
                    }
                    else
                    {
                        System.out.println("*** " + follower.getName() + " likes the post");
                    }
                    queue.enqueue(follower);
                    follower.visit();
                    follower.likePost(post);

                    // If the follower is not following the owner and decides to follow the owner
                    // after liking the post
                    if (!follower.isFollowing(owner.getName()) && follower.decideFollow(followProb * clickBait))
                    {
                        // If in simulation mode, all the message will be output to a file
                        if (pw != null)
                        {
                            pw.println("*** " + follower.getName() + " follows " + owner.getName());
                        }
                        // Or the messages will be displayed on the terminal
                        else
                        {
                            System.out.println("*** " + follower.getName() + " follows " + owner.getName());
                        }

                        follower.follow(owner); // Perform the following operation
                    }
                }
            }

            // If in step mode, stop and ask user after every level
            if (step)
            {
                System.out.print("\nFinished one time step, press ENTER to continue or any characters to stop: ");
                continued = sc.nextLine();
            }
        }

        // Reset the state of the network
        resetVisited();
        mostRecentPost = null;
        mostRecentPoster = null;
    }

    /**
     * Create a post when given the name of the owner and the content
     *
     * @param owner   - The owner of the post
     * @param content - The content of the post
     * @return - The Post object
     */
    private Post createPost(Person owner, String content)
    {
        // Generate a new ID for the new post
        // Assuming that a person cannot delete old posts
        // Therefore, we can guarantee that post ID is always incremented
        String id = Integer.toString(owner.getPostCount() + 1);
        return new Post(id, owner.getName(), content);
    }

    /**
     * Reset the state of the network, all the people in the network will
     * be set to unvisited.
     */
    private void resetVisited()
    {
        for (Object item : people)
        {
            Person person = (Person) item;
            person.resetVisited();
        }
    }
}
