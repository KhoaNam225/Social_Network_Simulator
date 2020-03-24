/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   Person.java
 * Date modified:   19/10/2019
 * Purpose:     This class represents a Person in the social network.
 * Each Person acts like a vertex in the network (the Graph).
 * Each Person will hold the list of his/her followers (like the adjacency list).
 * In addition, each Person will also hold a Table of the people his/her is following.
 */

import java.io.Serializable;
import java.util.Iterator;

public class Person implements Serializable
{
    private String name;  // The name of the person
    private DSAHashTable posts;  // The posts that this person posted
    private DSALinkedList postLiked;  // The posts that this person had
    // liked, a Person cannot like the post of his/herself
    private DSALinkedList followers;   // The people who follow this person
    private DSAHashTable following;  // The people this person is following
    private int followerCount;  // The number of followers
    private int followingCount; // The number of people this person is following
    private int postCount;   // How many post this person has
    private boolean visited;

    /**
     * Constructor
     * A name is required to construct a Person object. Each person in the network has a
     * unique name.
     *
     * @param inName - The name of the person to be created
     */
    public Person(String inName)
    {
        if (inName == null)
        {
            throw new IllegalArgumentException("Person cannot have NULL name.");
        }

        name = inName;

        /* The newly created Person does not have any followers and does not follows any people */
        posts = new DSAHashTable(DSAHashTable.DEFAULT_TABLE_SIZE);
        postLiked = new DSALinkedList();
        followers = new DSALinkedList();
        following = new DSAHashTable(DSAHashTable.DEFAULT_TABLE_SIZE);
        followerCount = 0;
        followingCount = 0;
        postCount = 0;
        visited = false;
    }

    // =========== THE GETTERS ============ //
    public String getName()
    {
        return name;
    }

    public int getPostCount()
    {
        return postCount;
    }

    public int getFollowerCount()
    {
        return followerCount;
    }

    public int getFollowingCount()
    {
        return followingCount;
    }

    /**
     * Returns all the post of the current person in increasing order of the number of likes for each post
     *
     * @return - A queue containing all the posts of the person in increasing order of the number of likes
     */
    public DSAQueue getPosts()
    {
        DSAQueue queue = null;
        if (hasAnyPosts())
        {
            DSALinkedList postList = posts.values();  // Get all the post (not in order yet)

            /**
             * Use a heap to get all the post in increasing order.
             * This can be done by using the number of likes of each post as keys
             * to insert the post to the heap.
             * After all the posts have been inserted, keep removing the post from the heap
             * and enqueue them to the queue.
             */
            DSAHeap heap = new DSAHeap(postCount);
            queue = new DSAQueue();

            // Get all the posts in nearly sorted order by using the heap
            for (Object item : postList)
            {
                Post post = (Post) item;
                heap.add(post.getLikeCount(), post);
            }

            // Build the queue from the heap
            while (!heap.isEmpty())
            {
                queue.enqueue(heap.remove());
            }
        }

        return queue;
    }

    public DSALinkedList getFollowers()
    {
        return followers;
    }

    public DSALinkedList getFollowing()
    {
        return following.values();
    }

    /**
     * Checks if this person has any followers.
     *
     * @return - True if there is someone who follows this person or false otherwise
     */
    public boolean hasAnyFollower()
    {
        return followerCount > 0;
    }

    /**
     * Checks if this person is following any people.
     *
     * @return - True if this person is following someone else or false otherwise.
     */
    public boolean isFollowingAnyPerson()
    {
        return followingCount > 0;
    }

    /**
     * Checks if this person has any posts.
     *
     * @return - True if this person has at least 1 post or false otherwise.
     */
    public boolean hasAnyPosts()
    {
        return postCount > 0;
    }

    /**
     * Get the post that has the most number of likes.
     *
     * @return - The post that has the most likes.
     * If the person doesn't have any posts, return null.
     */
    public Post getMostLikedPost()
    {
        Post post = null;
        DSAQueue queue = getPosts();
        if (!queue.isEmpty())
        {
            post = (Post) queue.dequeue();
        }

        return post;
    }

    /**
     * A Person can have a new post.
     * This method is used to add a new post posted by this person.
     *
     * @param post - The new post
     */
    public void addPost(Post post)
    {
        posts.put(post.getPostID(), post);
        postCount++;
    }

    /**
     * A Person can follow another person.
     *
     * @param person - The other person that the current person wants to follow
     */
    public void follow(Person person)
    {
        if (isFollowing(person.name))
        {
            throw new IllegalArgumentException(name + " is already following " + person.getName());
        }

        if (name.equals(person.name))
        {
            throw new IllegalArgumentException("A person cannot follows himself.");
        }

        following.put(person.name, person);
        followingCount++;  // Increase the number of following people
        person.addFollower(this);  // The current person is the follower of the other person

    }

    /**
     * Unfollow a person.
     *
     * @param person - The other person that the current person wants to unfollow
     */
    public void unfollow(Person person)
    {
        // Cannot unfollow the person you are not following
        if (isFollowing(person.name))
        {
            person.removeFollower(this);  // The current person is not the other's follower anymore
            following.removeKey(person.name);
            followingCount--;  // Update the number of people this person is following
        }
        else
        {
            throw new IllegalArgumentException(name + " is not following " + person.getName());
        }
    }

    /**
     * Checks if this person is following another person when given the name.
     *
     * @param name - The name of the other person that needs checking
     * @return - True if this person is currently following that person
     */
    public boolean isFollowing(String name)
    {
        return following.hasKey(name);
    }

    public boolean isVisited()
    {
        return visited;
    }

    /**
     * Like a post.
     *
     * @param post - The post that this person wants to like
     */
    public void likePost(Post post)
    {
        // Cannot like a same post twice
        if (!post.isLikeBy(this.name))
        {
            post.addPeopleLiked(this);
            postLiked.insertLast(post);
        }
    }

    /**
     * Base on the chance of follow, decide whether this person follows another person or not.
     *
     * @param followChance - The probability that this person follows another
     * @return - True if this person wants to follow or false otherwise
     */
    public boolean decideFollow(double followChance)
    {
        double rand = Math.random();
        return rand < followChance;
    }

    /**
     * Base on the chance of like, decide whether this person likes a post or not.
     *
     * @param likeChance - The probability that this person likes a post
     * @return - True if this person wants to like or false otherwise
     */
    public boolean decideLike(double likeChance)
    {
        double rand = Math.random();
        return rand < likeChance;
    }

    /**
     * Set the state of the current person to unvisited
     * (used in breadth first search)
     */
    public void resetVisited()
    {
        visited = false;
    }

    /**
     * Visit this person (set the state of this person to be visited)
     * (used in breadth first search)
     */
    public void visit()
    {
        visited = true;
    }

    // String representation of a person
    public String toString()
    {
        String str = "";
        str += "Name: " + name + "\n";
        str += "Number of follower: " + followerCount + "\n";
        str += "Number of posts: " + postCount + "\n";
        // If this person has any followers, include all their names
        // Otherwise just writes "N/A"
        str += "Follower: ";
        if (hasAnyFollower())
        {
            for (Object item : followers)
            {
                Person follower = (Person) item;
                str += follower.getName() + " ";
            }
        }
        else
        {
            str += "N/A";
        }
        str += "\n";

        // If this person is following anybody, include all their names
        // Otherwise just write "N/A"
        str += "Following: ";
        if (isFollowingAnyPerson())
        {
            for (Object item : following.values())
            {
                Person person = (Person) item;
                str += person.getName() + " ";
            }
        }
        else
        {
            str += "N/A";
        }
        str += "\n";

        // If this person has any posts, include all the posts
        // Otherwise just write "N/A"
        str += "Posts: ";
        if (hasAnyPosts())
        {
            str += "\n";
            for (Object item : getPosts())
            {
                Post post = (Post) item;
                str += post.toString() + "\n";
            }
        }
        else
        {
            str += "N/A";
        }
        str += "\n";

        return str.trim();
    }

    /**
     * Checks if the given person is following this person.
     * 
     * @param name - The name of the person that needs checking
     * @return - true if the given person is actually following this person
     *           otherwise returns false
     */
    public boolean hasFollower(String name)
    {
        return findPerson(followers, name) != null;
    }

     /**
     * Remove follower from the list of people who are following this person
     * (When the follower decides to unfollow this person).
     *
     * @param follower - The follower
     */
    public void removeFollower(Person follower)
    {
        DSALinkedList newFollowerList = new DSALinkedList();  // The new list that does not contain the follower

        // Add all the current followers except for the given follower
        for (Object item : followers)
        {
            Person per = (Person) item;
            if (!per.name.equals(follower.name))
            {
                newFollowerList.insertLast(per);
            }
        }

        followers = newFollowerList;
        followerCount--;
    }

    /**
     * Find a person in a list of people, returns the Person object if that
     * person is in the list otherwise returns null.
     *
     * @param people - The list of people
     * @param name   - The name of the person
     * @return - The Person object if the person is in the list otherwise returns null.
     */
    private Person findPerson(DSALinkedList people, String name)
    {
        boolean found = false;  // Found or not?
        Iterator iterator = people.iterator();
        Person person = null;
        // Iterate through the list to find the person
        // Stop when found or reach the end of the list
        while (!found && iterator.hasNext())
        {
            person = (Person) iterator.next();
            found = name.equals(person.name);
        }

        // If not found
        if (!found)
        {
            person = null;
        }

        return person;
    }

    /**
     * Add a follower to the list of people who are following this person
     *
     * @param follower - The follower
     */
    private void addFollower(Person follower)
    {
        followers.insertLast(follower);
        followerCount++;
    }
}
