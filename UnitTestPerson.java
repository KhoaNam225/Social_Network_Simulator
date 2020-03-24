/**
 * Author:      Khoa Nam Pham
 * StudentID:   19305875
 * File name:   UnitTestPerson.java
 * Date modified: 22/10/2019
 * Purpose:     Unit test for Person class
 */
public class UnitTestPerson
{
    public static void main(String[] args)
    {
        int peopleNum = 5;
        double likeChance = 0.5, followChance = 0.5;

        String[] names = {"John", "Sara", "Matthew", "Jack", "Kevin"};
        String[] post = new String[peopleNum];
        for (int i = 0; i < peopleNum; i++)
        {
            post[i] = names[i] + "'s post";
        }

        System.out.println("\nInitializing 5 people...".toUpperCase());
        Person[] people = new Person[peopleNum];
        for (int i = 0; i < peopleNum; i++)
        {
            System.out.println("Initializing " + names[i] + "...");
            people[i] = new Person(names[i]);
        }
        System.out.println("Finished initializing 5 people...\n".toUpperCase());

        System.out.println("\nAdding post to each person...\n".toUpperCase());
        for (int i = 0; i < peopleNum; i++)
        {
            System.out.println("Adding " + post[i] + "...");
            Post p = new Post(Integer.toString(people[i].getPostCount() + 1), names[i], post[i]);
            people[i].addPost(p);
        }
        System.out.println("Finished adding posts to each person...".toUpperCase());

        System.out.println("\nEach person likes other people's post...\n".toUpperCase());
        for (int i = 0; i < peopleNum; i++)
        {
            for (int j = 0; j < peopleNum; j++)
            {
                if (i != j && people[i].decideLike(likeChance))
                {
                    System.out.printf("%s likes %s's post... \n", people[i].getName(), people[j].getName());
                    people[i].likePost((Post) people[j].getPosts().dequeue());
                }
            }
            System.out.println();
        }
        System.out.println("Finished likes each other's post...\n".toUpperCase());

        System.out.println("\nMost liked post from each person\n".toUpperCase());
        for (int i = 0; i < peopleNum; i++)
        {
            System.out.println(people[i].getMostLikedPost().toString() + "\n");
        }

        System.out.println("\nEach person follows other people\n".toUpperCase());
        for (int i = 0; i < peopleNum; i++)
        {
            for (int j = 0; j < peopleNum; j++)
            {
                if (i != j && people[i].decideFollow(followChance))
                {
                    System.out.printf("%s follows %s...\n", people[i].getName(), people[j].getName());
                    people[i].follow(people[j]);
                }
            }
            System.out.println();
        }

        System.out.println("\nPrinting each person...".toUpperCase());
        for (int i = 0; i < peopleNum; i++)
        {
            System.out.println(people[i].toString() + "\n");
        }

        System.out.println("\nEach person unfollows some people...\n".toUpperCase());
        for (int i = 0; i < peopleNum; i++)
        {
            if (people[i].isFollowingAnyPerson())
            {
                DSALinkedList following = people[i].getFollowing();
                for (Object item : following)
                {
                    Person person = (Person) item;
                    if (people[i].decideFollow(followChance))
                    {
                        System.out.printf("%s unfollows %s...\n", people[i].getName(), person.getName());
                        people[i].unfollow(person);
                    }
                }
                System.out.println();
            }
        }

        System.out.println("\nPrinting each person after unfollowing process...\n".toUpperCase());
        for (int i = 0; i < peopleNum; i++)
        {
            System.out.println(people[i].toString() + "\n");
        }
    }
}
