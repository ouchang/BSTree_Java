import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

/**
 * Class responsible for creating Client's frame and support Client's wishes connected to a given binary tree
 */
public class TreeFrame extends JFrame {
    
    private JPanel controlPanel; //Panel for buttons

    private JPanel northPanel; //Panel for components placed in northen part of frame
    private JPanel southPanel; //Panel for components placed in southen part of frame
 
    private JLabel textLabel; //Informs where user should type in method's parameteres
    private JLabel typeLabel; //Shows which type of tree or method was chosen
    private JLabel outputLabel; //Informs if a given node was found in binary tree during search method

    private JScrollPane scrollPane;

    //Binary trees
    public Tree<Integer> treeInteger;
    public Tree<Double> treeDouble;
    public Tree<String> treeString;

    //External Panels used for binary trees
    public IntegerDrawing treeIntegerPanel;
    public DoubleDrawing treeDoublePanel;
    public StringDrawing treeStringPanel;

    private TextArea textArea;

    //Menu for type of tree
    final JMenuBar menuBar;
    private JMenuItem integerItem;
    private JMenuItem doubleItem;
    private JMenuItem stringItem;

    //Button for methods
    private JButton search;
    private JButton insert;
    private JButton delete;
    private JButton draw;

    //Communication with Server
    public Socket clientSocket;
    public OutputStream output;
    public PrintWriter out;
    public InputStream input;
    public BufferedReader in;

    String typeOfTree;

    /**
     * Constructor for Client's Frame. It creates streams, provides support for drawing the binary tree and binary tree's methods
     * @param treeInteger Integer binary tree
     * @param treeDouble Double binary tree
     * @param treeString String binary tree
     * @param clientSocket Client's socket
     */
    public TreeFrame(Tree<Integer> treeInteger, Tree<Double> treeDouble, Tree<String> treeString, Socket clientSocket) {
        this.clientSocket = clientSocket;
        
        //Creating streams
        try {
            output = clientSocket.getOutputStream();
            out = new PrintWriter(output, true);

            input = clientSocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(input));
        } catch(IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
            System.exit(1);
       }

        //Setting size and title of the frame       
        setTitle("Binary Tree");
        setSize(700,700);

        //Creating northen and southen Panel in Frame
        northPanel = new JPanel(new BorderLayout());
        southPanel = new JPanel(new BorderLayout());

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        //Window closing support
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                try {
                    clientSocket.close();
                    System.exit(0);
                } catch(IOException ex) {
                    System.out.println("Client exception: " + ex.getMessage());
                    System.exit(1);
               }
            }        
        });    

        //Creating panel for buttons
        controlPanel = new JPanel(); 
        controlPanel.setLayout(new GridLayout(1,4)); 
        add(controlPanel);

        //Creating label for showing which type/method user chose
        typeLabel = new JLabel("", JLabel.CENTER);
        northPanel.add(typeLabel, BorderLayout.NORTH);

        //Creating label for showing output info from search method
        outputLabel = new JLabel("", JLabel.CENTER);
        northPanel.add(outputLabel, BorderLayout.SOUTH);
        outputLabel.setForeground(Color.RED);

        //Creating MenuBar
        menuBar = new JMenuBar();

        //Creating Menu
        JMenu fileMenu = new JMenu("Tree Type");
        
        //Creating MenuItems
        integerItem = new JMenuItem("Integer");
        integerItem.setActionCommand("Integer");

        stringItem  = new JMenuItem("String");
        stringItem.setActionCommand("String");

        doubleItem = new JMenuItem("Double");
        doubleItem.setActionCommand("Double");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");

        MenuItemListener menuItemListener = new MenuItemListener();
        integerItem.addActionListener(menuItemListener);
        stringItem.addActionListener(menuItemListener);
        doubleItem.addActionListener(menuItemListener);
        exitMenuItem.addActionListener(menuItemListener);

        //Adding MenuItems to Menu
        fileMenu.add(integerItem);
        fileMenu.add(stringItem);
        fileMenu.add(doubleItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);        

        //Adding Menu to MenuBar
        menuBar.add(fileMenu);

        //Ading MenuBar to Frame
        setJMenuBar(menuBar);

        //Creating buttons for methods
        search = new JButton("Search");
        insert = new JButton("Insert");
        delete = new JButton("Delete");
        draw = new JButton("Draw");

        ButtonListener buttonListener = new ButtonListener();
        search.addActionListener(buttonListener);
        insert.addActionListener(buttonListener);
        draw.addActionListener(buttonListener);
        delete.addActionListener(buttonListener);

        //Adding buttons to panel
        controlPanel.add(search);
        controlPanel.add(insert);
        controlPanel.add(delete);
        controlPanel.add(draw);

        //Creating Text Area
        textArea = new TextArea();
        textArea.setBounds(10, 30, 20, 100);

        //Creating label for TextArea's info
        textLabel = new JLabel("Enter parameter for the chosen method: ", JLabel.CENTER);
        
        //Adding textArea, label for TextArea's info and buttons' panel
        southPanel.add(textLabel, BorderLayout.NORTH);
        southPanel.add(textArea, BorderLayout.CENTER);
        southPanel.add(controlPanel, BorderLayout.SOUTH);

        //Setting binary trees
        this.treeInteger = treeInteger;
        this.treeDouble = treeDouble;
        this.treeString = treeString;

        //Creating binary trees' panes
        treeIntegerPanel = new IntegerDrawing(treeInteger);
        treeDoublePanel = new DoubleDrawing(treeDouble);
        treeStringPanel = new StringDrawing(treeString);

        //Setting Frame as visible
        setVisible(true);
    }

    /**
     * Class responsible for MenuItems support. Thanks to it, user can choose type of binary tree using Menu.
     * After choosing type of tree, it sets proper panel in the frame
     */
    class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {   
           typeLabel.setText("Type " + e.getActionCommand() + " was chosen");

           if(e.getSource() == integerItem) {
                typeOfTree = "Integer"; //Setting chosen type of tree

                treeIntegerPanel.setPreferredSize(new Dimension(1700, 1700));

                //Creating ScrollPane based on binary tree's panel
                scrollPane = new JScrollPane(treeIntegerPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                add(scrollPane, BorderLayout.CENTER);

                //Hidding Menu with tree's types
                menuBar.setVisible(false);

                //Sending tree's type to Server
                out.println("Integer");
           } else if(e.getSource() == stringItem) {
                typeOfTree = "String"; //Setting chosen type of tree

                treeStringPanel.setPreferredSize(new Dimension(1700, 1700));

                //Creating ScrollPane based on binary tree's panel
                scrollPane = new JScrollPane(treeStringPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                add(scrollPane, BorderLayout.CENTER);

                //Hidding Menu with tree's types
                menuBar.setVisible(false);

                //Sending tree's type to Server
                out.println("String");
           } else if(e.getSource() == doubleItem) {
                typeOfTree = "Double"; //Setting chosen type of tree

                treeDoublePanel.setPreferredSize(new Dimension(1700, 1700));

                //Creating ScrollPane based on binary tree's panel
                scrollPane = new JScrollPane(treeDoublePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                add(scrollPane, BorderLayout.CENTER);

                //Hidding Menu with tree's types
                menuBar.setVisible(false);

                //Sending tree's type to Server
                out.println("Double");
           }
        }    
    }

    /**
     * Class responsible for support the methods' buttons.
     */
    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {     
            outputLabel.setVisible(false); //Hidding label with output info from search method    
            typeLabel.setText("Method " + e.getActionCommand() + " was chosen");
            String parameter, encodedTree, outputInfo; //user's parameter used in method; tree saved as string using brackets; output info from search method

            try {
                if(e.getSource() == search) {
                    out.println("search"); //Sending type of command to Server

                    //Reading parameter from TextArea, cleaning TextArea and sending it to Server
                    parameter = textArea.getText();
                    textArea.setText("");
                    out.println(parameter);

                    //Reading output info from Server and putting it in label
                    outputInfo = in.readLine();
                    System.out.println(outputInfo);
                    outputLabel.setText(outputInfo);
                    outputLabel.setVisible(true);
            } else if(e.getSource() == insert) {
                    out.println("insert"); //Sending type of command to Server

                    //Reading parameter from TextArea, cleaning TextArea and sending it to Server
                    parameter = textArea.getText();
                    textArea.setText("");
                    out.println(parameter);

                    //Reading string with tree saved as sequence of values and brackets (code)
                    encodedTree = in.readLine();
                    System.out.println(encodedTree);

                    //Decoding recived sequence (code )and coverting it into binary tree, then repainting the tree's panel
                    runDecodeTree(typeOfTree, encodedTree);
            } else if(e.getSource() == delete) {
                    out.println("delete"); //Sending type of command to Server

                    //Reading parameter from TextArea, cleaning TextArea and sending it to Server
                    parameter = textArea.getText();
                    textArea.setText("");
                    out.println(parameter);
                    
                    //Reading string with tree saved as sequence of values and brackets
                    encodedTree = in.readLine();
                    System.out.println(encodedTree);

                    //Decoding recived sequence (code) and coverting it into binary tree, then repainting the tree's panel
                    runDecodeTree(typeOfTree, encodedTree);
            } else if(e.getSource() == draw) {
                    out.println("draw"); //Sending type of command to Server
                    
                    //Reading string with tree saved as sequence of values and brackets
                    encodedTree = in.readLine();
                    System.out.println(encodedTree);
                    //Decoding recived sequence (code) and coverting it into binary tree, then repainting the tree's panel
                    runDecodeTree(typeOfTree, encodedTree);
                }
            } catch(IOException ex) {
                System.out.println("Client exception: " + ex.getMessage());
                System.exit(1);
           }
        }    
    }

    /**
     * Method used to decode sequence in which binary tree is saved as mix of values and brackets
     * @param typeTree type of tree
     * @param encodedTree tree saved as sequence of values and brackets
     */
    private void runDecodeTree(String typeTree, String encodedTree) {
        if(typeTree == "Integer") {
            //Converting code into binary tree
            treeInteger.root = treeIntegerPanel.convertToTree(encodedTree, null);
            treeIntegerPanel.start = 0;

            //Repainting tree's panel
            treeIntegerPanel.repaint();
        } else if(typeTree == "Double") {
            //Converting code into binary tree
            treeDouble.root = treeDoublePanel.convertToTree(encodedTree, null);
            treeDoublePanel.start = 0;

            //Repainting tree's panel
            treeDoublePanel.repaint();
        } else if(typeTree == "String") {
            //Converting code into binary tree
            treeString.root = treeStringPanel.convertToTree(encodedTree, null);
            treeStringPanel.start = 0;

            //Repainting tree's panel
            treeStringPanel.repaint();
        }
    }
}
