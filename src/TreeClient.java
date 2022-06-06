import java.net.*;
import java.io.*;

/**
 * Main class for client's support
 */
public class TreeClient {
    public static void main(String[] main) {
        try {
            //Connecting Client's socket with Server's socket using port 4444
            Socket socket = new Socket("localhost", 4444);

            //Creating binary trees
            Tree<Integer> treeInteger = new Tree<Integer>();
            Tree<Double> treeDouble = new Tree<Double>();
            Tree<String> treeString = new Tree<String>();

            //Creating Client's frame
            new TreeFrame(treeInteger, treeDouble, treeString, socket);
        } catch(UnknownHostException ex) {
            System.out.println("Unknown host");
            System.exit(1);
       } catch(IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
            System.exit(1);
       }
    }
}
