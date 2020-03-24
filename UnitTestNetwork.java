/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   UnitTestPerson.java
 * Date modified: 22/10/2019
 * Purpose:     Unit test for Network class
 */
public class UnitTestNetwork
{
    private int peopleNum = 5;
    private String[] names = {"John", "Sara", "Matthew", "Jack", "Kevin"};
    private String[] post;
    private Network network;
    private static final double FOLLOW_PROB = 0.5;
    private static final double LIKE_PROB = 0.5;

    public UnitTestNetwork()
    {
        post = new String[peopleNum];
        for (int i = 0; i < peopleNum; i++)
        {
            post[i] = names[i] + "'s post";
        }

        network = new Network(LIKE_PROB, FOLLOW_PROB);
    }

    private void testAddPeople()
    {
        for (int i = 0; i < peopleNum; i++)
        {
            System.out.println("Adding " + names[i] + "...");
            network.addPerson(names[i]);
        }

        System.out.println("\nTest adding an already existed person");
        try
        {
            System.out.println("Adding " + names[0] + "...");
            network.addPerson(names[0]);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("\nThe network after adding 5 people");
        System.out.println(network.display());
    }

    private void testAddPost()
    {
        System.out.println("\nTest adding post to people\n".toUpperCase());
        boolean step = false;
        for (int i = 0; i < peopleNum; i++)
        {
            System.out.println("\nAdding " + post[i] + "...");
            network.addPost(names[i], post[i]);
            network.update(step, 1.0, null);
        }

        System.out.println("\nThe network after adding post\n".toUpperCase());
        System.out.println(network.display());
    }

    private void testFollow()
    {
        System.out.println("\nTest following, each person follow another person\n".toUpperCase());
        for (int i = 0; i < peopleNum; i++)
        {
            int j = (i + 1) % peopleNum;
            System.out.println(names[i] + " follows " + names[j] + "\n");
            network.follow(names[i], names[j]);
            System.out.println();
        }
        System.out.println("\nThe network after adding edges\n".toUpperCase());
        System.out.println(network.display());
    }

    private void testFindPerson()
    {
        System.out.println("\nTesting find person\n".toUpperCase());
        System.out.println("Find " + names[0] + ":\n");
        System.out.println(names[0] + "'s information: \n");
        System.out.println(network.findPerson(names[0]).toString());
        System.out.println();

        System.out.println("Find James: \n");
        if (!network.hasPerson("James"))
        {
            System.out.println("James is not in the network");
        }
    }

    private void testRemovePerson()
    {
        System.out.println("\nTesting removing person from the network\n".toUpperCase());
        System.out.println("Removing " + names[0] + "...\n");
        network.removePerson(names[0]);
        System.out.println("Removing James... \n");
        try
        {
            network.removePerson("James");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("\nThe network after removing");
        System.out.println(network.display());
    }

    private void testUnfollow()
    {
        System.out.println("\nTesting unfollowing\n".toUpperCase());
        System.out.println("Try " + names[1] + " unfollows " + names[2]);
        network.unfollow(names[1], names[2]);

        System.out.println("Try " + names[4] + " unfollows " + names[1]);
        try
        {
            network.unfollow(names[4], names[1]);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("\nThe network after unfollowing: ");
        System.out.println(network.display());
    }

    private void testPeopleStats()
    {
        try
        {
            System.out.println("\nTesting displaying people's statistics\n".toUpperCase());
            System.out.println(network.peopleStat());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void testPostStats()
    {
        try
        {
            System.out.println("\nTesting displaying posts' statistics\n".toUpperCase());
            System.out.println(network.postStats());
            System.out.println();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        UnitTestNetwork test = new UnitTestNetwork();
        System.out.println("\nConstructing the network\n".toUpperCase());
        test.testAddPeople();
        test.testFollow();
        test.testAddPost();
        test.testPeopleStats();
        test.testPostStats();
        test.testFindPerson();
        test.testRemovePerson();
        test.testUnfollow();
    }
}
