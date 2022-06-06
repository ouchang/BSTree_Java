import java.net.*;
import java.io.*;

/**
 * Class responsible of Server's work
 */
public class TreeServer {
    public static void main(String args[]) {
        try(ServerSocket SocketS = new ServerSocket(4444)) {
            System.out.println("Turning on the server");

            while(true) {
                Socket socket = SocketS.accept();
                System.out.println("New client connected");

                TreeThread thread = new TreeThread(socket);
                thread.start();
            }

        } catch(IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
