import java.io.Serializable;

/**
 * Author: Khoa Nam Pham
 * Student ID: 19305875
 * File name: Post.java
 * Date modified:   19/10/2019
 * Purpose: This class represents the post that each person can have.
 */
public class Post implements Serializable
{
    // The ID of this post (to distinguish it with other
    // posts of the same person, hence allowing that person to have 2 or more
    // posts that have content that is exactly the same
    private String postID;
    private String owner; // The person who posted this post
    private String content; // The content of the post
    private DSAHashTable likedBy;  // People who liked this post
    private int likeCount;   // The number of people who liked this post

    /**
     * Constructor, each post contains its ID, its owner (who posts it) and the content
     *
     * @param inID      - The ID of the post
     * @param inOwner   - The person who posted the post
     * @param inContent - The content of the post
     */
    public Post(String inID, String inOwner, String inContent)
    {
        postID = inID;
        owner = inOwner;
        content = inContent;
        likeCount = 0;
        likedBy = new DSAHashTable(DSAHashTable.DEFAULT_TABLE_SIZE);
    }

    //====== ALL NECESSARY GETTERS ============= //
    public String getOwner()
    {
        return owner;
    }

    public String getContent()
    {
        return content;
    }

    public DSALinkedList likedBy()
    {
        return likedBy.values();
    }

    public int getLikeCount()
    {
        return likeCount;
    }

    public String getPostID()
    {
        return postID;
    }

    /**
     * Check if two posts are the same.
     * Two posts are considered the same when they have the same owner and the same ID.
     *
     * @param otherPost - The other post to compare this post with
     * @return - True if the 2 posts are the same and false otherwise
     */
    public boolean equals(Post otherPost)
    {
        boolean equal = true;
        if (!this.postID.equals(otherPost.postID))
        {
            equal = false;
        }
        else if (!this.owner.equals(otherPost.owner))
        {
            equal = false;
        }

        return equal;
    }

    /**
     * Returns a string containing all necessary information of a post (ID, content, number of likes).
     *
     * @return - A string containing all the information
     */
    public String toString()
    {
        String str = "";
        str += "Owner: " + owner + "\n";
        str += "Content: " + content + "\n";
        str += "Likes: " + likeCount;

        return str;
    }

    /**
     * When a person likes this post, add that person to the list of people who liked it.
     *
     * @param liker - The person who liked this post
     */
    public void addPeopleLiked(Person liker)
    {
        if (liker == null)
        {
            throw new IllegalArgumentException("Cannot add NULL liker to Post");
        }

        likedBy.put(liker.getName(), liker);
        likeCount++;
    }

    /**
     * Checks if this post is already liked by a person.
     * This method is useful to prevent a person from liking a post twice.
     *
     * @param person - The person that needs checking
     * @return - True if the person already liked this post or false otherwise
     */
    public boolean isLikeBy(String person)
    {
        return likedBy.hasKey(person);
    }

}
