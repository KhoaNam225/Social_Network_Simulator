import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DataGenerator
{
    private static void writeNetworkFile(int vertexNum, double connectPercent)
    {
        FileOutputStream outputStream = null;
        PrintWriter pw = null;
        String filename = "Network_" + vertexNum + "_" + connectPercent + ".txt";
        int neighborNumEach = (int) (vertexNum * connectPercent);
        try
        {
            outputStream = new FileOutputStream(filename);
            pw = new PrintWriter(outputStream);
            for (int i = 0; i < vertexNum; i++)
            {
                pw.println(i);
            }

            for (int i = 0; i < vertexNum; i++)
            {
                for (int j = 1; j < neighborNumEach + 1; j++)
                {
                    int k = (i + j) % vertexNum;
                    pw.println(i + ":" + k);
                }
            }

            System.out.printf("Finished creating network file with %d vertices and %.2f percent connection\n", vertexNum, connectPercent * 100);
            pw.close();
        }
        catch (IOException e)
        {
            System.out.println("Cannot create network file");
        }
    }

    private static void writeEventFile(int vertexNum, double connectionPercent)
    {
        FileOutputStream outputStream = null;
        PrintWriter pw = null;
        String filename = "Event_" + vertexNum + "_" + connectionPercent + ".txt";
        try
        {
            outputStream = new FileOutputStream(filename);
            pw = new PrintWriter(outputStream);

            int poster = (int) (Math.random() * vertexNum);
            pw.println("P:" + poster + ":" + poster + "'s new post.");

            System.out.printf("Finished creating event file with %d vertices\n", vertexNum);
            pw.close();
        }
        catch (IOException e)
        {
            System.out.println("Cannot create event file");
        }
    }

    public static void main(String[] args)
    {
        int vertexNum = Integer.parseInt(args[0]);
        double connectingPercent = Double.parseDouble(args[1]);
        int neighborNumEach = (int) (vertexNum * connectingPercent);

        writeNetworkFile(vertexNum, connectingPercent);
        writeEventFile(vertexNum, connectingPercent);
    }
}
