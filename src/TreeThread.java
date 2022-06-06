import java.net.*;
import java.io.*;

/**
 * Class responsible of support Client's wishes connected to Binary Tree
 */
public class TreeThread extends Thread {
    private Socket socket;

    /**
     * Constructor in class of thread inBinary Tree. 
     * @param socket
     */
    public TreeThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Main method used by thread. We are setting streams between Serwer and Client. Based on the command given by Client, thread starts proper instruction in Binary Tree
     */
    public void run() {

        try {
            //Reciving from Client
            InputStream input = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));

            //Sending to Client
            OutputStream output = socket.getOutputStream();
            PrintWriter out = new PrintWriter(output, true);

            String command; //Command given by Client
            String parameter; //Parameter given by Client
            String output_info; //Text which is shown on Client's terminal (information about an output from binary tree's instruction)
            String treeType; //Type of tree given by Client

            int paramInteger; //Converted Client's parameter into Integer
            double paramDouble; //Converted Client's parameter into Double

            //Creating binary trees for each type
            Tree<Integer> treeInteger = new Tree<Integer>();
            Tree<Double> treeDouble = new Tree<Double>();
            Tree<String> treeString = new Tree<String>();
            
            //Reading Client's type of tree
            treeType = in.readLine();        

            do {
                //Reading Client's command
                command = "";
                command = in.readLine();

                switch(treeType) {
                    case "Integer":

                        switch(command) {
                            case "search":
                                //Reading Client's parameter
                                parameter = in.readLine();
        
                                //Converting Client's parameter into Integer
                                paramInteger = Integer.parseInt(parameter);
        
                                //Saving output information from binary tree method
                                output_info = treeInteger.search(paramInteger);
                                
                                //Sending output information to Client
                                out.println(output_info);
                                break;
                            case "insert":
                                //Reading Client's parameter
                                parameter = in.readLine();
        
                                //Converting Client's parameter into Integer
                                paramInteger = Integer.parseInt(parameter);
        
                                //Starting binary tree's method
                                treeInteger.insert(paramInteger);
        
                                //Saving output information from binary tree method
                                output_info = treeInteger.draw();
        
                                //Sending output information to Client
                                out.println(output_info);
                                break;
                            case "delete":
                                //Reading Client's parameter
                                parameter = in.readLine();
                        
                                //Converting Client's parameter into Integer
                                paramInteger = Integer.parseInt(parameter);
        
                                //Starting binary tree's method
                                treeInteger.delete(paramInteger);
                                
                                //Saving output information from binary tree method
                                output_info = treeInteger.draw();
        
                                //Sending output information to Client
                                out.println(output_info);
                                break;
                            case "draw":
                                //Saving output information from binary tree method
                                output_info = treeInteger.draw();
        
                                //Sending output information to Client
                                out.println(output_info);
                                break;
                            case "end":
                                break;
                            default:
                                System.out.println("Wrong type of command!");
                                out.println("Wrong type of command!");
                        }

                        break;
                    case "Double":

                        switch(command) {
                            case "search":
                                //Reading Client's parameter
                                parameter = in.readLine();
                                
                                //Converting Client's parameter into Double
                                paramDouble = Double.parseDouble(parameter);
        
                                //Saving output information from binary tree method
                                output_info = treeDouble.search(paramDouble);
                                
                                //Sending output information to Client
                                out.println(output_info);
                                break;
                            case "insert":
                                //Reading Client's parameter
                                parameter = in.readLine();
        
                                //Converting Client's parameter into Double
                                paramDouble = Double.parseDouble(parameter);
        
                                //Starting binary tree's method
                                treeDouble.insert(paramDouble);
        
                                //Saving output information from binary tree method
                                output_info = treeDouble.draw();
        
                                //Sending output information to Client
                                out.println(output_info);

                                break;
                            case "delete":
                                //Reading Client's parameter
                                parameter = in.readLine();
        
                                //Converting Client's parameter into Double
                                paramDouble = Double.parseDouble(parameter);
        
                                //Starting binary tree's method
                                treeDouble.delete(paramDouble);
                                
                                //Saving output information from binary tree method
                                output_info = treeDouble.draw();
        
                                //Sending output information to Client
                                out.println(output_info);

                                break;
                            case "draw":
                                //Saving output information from binary tree method
                                output_info = treeDouble.draw();
        
                                //Sending output information to Client
                                out.println(output_info);

                                break;
                            case "end":
                                break;
                            default:
                                System.out.println("Wrong type of command!");
                                out.println("Wrong type of command!");
                        }

                        break;
                    case "String":

                        switch(command) {
                            case "search":
                                //Reading Client's parameter
                                parameter = in.readLine();
        
                                //Saving output information from binary tree method
                                output_info = treeString.search(parameter);
                                
                                //Sending output information to Client
                                out.println(output_info);
                                break;
                            case "insert":
                                //Reading Client's parameter
                                parameter = in.readLine();
        
                                //Starting binary tree's method
                                treeString.insert(parameter);
        
                                //Saving output information from binary tree method
                                output_info = treeString.draw();
        
                                //Sending output information to Client
                                out.println(output_info);

                                break;
                            case "delete":
                                //Reading Client's parameter
                                parameter = in.readLine();
        
                                //Starting binary tree's method
                                treeString.delete(parameter);
                                
                                //Saving output information from binary tree method
                                output_info = treeString.draw();
        
                                //Sending output information to Client
                                out.println(output_info);

                                break;
                            case "draw":
                                //Saving output information from binary tree method
                                output_info = treeString.draw();
        
                                //Sending output information to Client
                                out.println(output_info);
                                
                                break;
                            case "end":
                                break;
                            default:
                                System.out.println("Wrong type of command!");
                                out.println("Wrong type of command!");
                        }

                        break;
                    default:
                        System.out.println("Wrong type of tree!");
                        out.println("Wrong type of tree!");
                }

               
            } while(!command.equals("end"));

            socket.close();

        } catch(IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace(); 
        } catch(NumberFormatException ex) {
            System.out.println("Wrong type of parameter!");
        } catch(NullPointerException ex) {
            System.out.println("Client closed socket, exiting...");
            //System.exit(0);
        }
    }
}
