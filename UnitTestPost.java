/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   UnitTestPerson.java
 * Date modified: 22/10/2019
 * Purpose:     Unit test for Post class
 */
public class UnitTestPost
{
    public static void main(String[] args)
    {
        int postNum = 3;
        Person[] likers = new Person[postNum];
        Post[] posts = new Post[postNum];
        String[] content = new String[postNum];

        likers[0] = new Person("John");
        likers[1] = new Person("Sara");
        likers[2] = new Person("Matthew");

        content[0] = "John's post";
        content[1] = "Sara's post";
        content[2] = "Matthew's post";

        System.out.println("\nInitializing 3 posts...\n");
        for (int i = 0; i < postNum; i++)
        {
            posts[i] = new Post(Integer.toString(i), likers[i].getName(), content[i]);
        }
        System.out.println("Finished initializing 3 posts...\n");

        System.out.println("\nPrint each post:");
        for (int i = 0; i < postNum; i++)
        {
            System.out.println(posts[i].toString() + "\n");
        }

        System.out.println("\nTesting people like the post...");
        for (int i = 0; i < postNum; i++)
        {
            System.out.printf("%s likes %s's post\n", likers[i].getName(), likers[(i + 1) % postNum].getName());
            posts[(i + 1) % postNum].addPeopleLiked(likers[i]);
        }

        System.out.println("\nEach post and people who like it...");
        for (int i = 0; i < postNum; i++)
        {
            System.out.println(posts[i].toString());
            DSALinkedList likedBy = posts[i].likedBy();
            System.out.println("This post is liked by: ");
            for (Object item : likedBy)
            {
                Person person = (Person) item;
                System.out.println(person.getName());
            }
            System.out.println();
        }
    }
}
