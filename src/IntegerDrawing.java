import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * Class responsible for creating Panel and drawing for Integer Binary Tree
 */
public class IntegerDrawing extends JPanel {
    public Tree<Integer> tree; //Integer Binary Tree
    public int maxHeight; //Height of Binary Tree
    public Font font; //Font used while printing values of Binary Tree in GUI
    public int verticalDistance = 150; //Distance between parent-child nodes
    public int horizontalDistance = 200; //Distance between child-child nodes
    public int start=0; //Starting index in the given sequence used in decoding

    /**
     * Constructor for Integer Binary Tree's Panel
     * @param tree Integer Binary Tree
     */
    public IntegerDrawing(Tree<Integer> tree) {
        this.tree = tree;
        this.font = new Font("Roman", 0, 25);
        this.maxHeight = 0;
    }

    /**
     * Method responsible for drawing whole Integer Binary Tree. Using recursion we draw one node at the time. 
     * @param g Graphics used in Panel
     * @param root  Tree node that we are currently drawing
     * @param x X coordinate of the center of the cirle for tree's node
     * @param y Y coordinate of the center of the cirle for tree's node
     * @param parentX X coordinate of the center of the cirle for current node's parent
     * @param parentY Y coordinate of the center of the cirle for current node's parent
     * @param amILeft Flag which indicates if the current node is a left child
     */
    private void drawTree(Graphics g, TreeNode<Integer> root, int x, int y, int parentX, int parentY, boolean amILeft) {
        if(root != null && root.val != null) { //
            String text = root.val.toString(); //Current node's value casted into String
            
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics(); //Details about font used in Panel's graphics
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            
            int nodeRadius = Math.max(textWidth/2, textHeight/2) + 5; //Radius of node's circle
            int radiusSide = (int) (nodeRadius / Math.sqrt(2)); //Length of the side of square which has nodeRadius as it's diagonal

            //If current node has a parent, then we are drawing egde between the circles
            if(root.parent != null) {
                int parentRadius = Math.max(fm.stringWidth(root.parent.val.toString())/2, textHeight/2) + 5;
                int parentRadiusSide = (int) (parentRadius / Math.sqrt(2));

                if(amILeft) {
                    //drawLine parameters: startX, startY, endX, endY
                    g.drawLine(parentX - parentRadiusSide, parentY - textHeight/3 + parentRadiusSide, x + radiusSide , y - textHeight/3 - radiusSide);
                } else {
                    g.drawLine(parentX + parentRadiusSide, parentY - textHeight/3 + parentRadiusSide, x - radiusSide , y - textHeight/3 - radiusSide);
                }
            }

            g.drawOval(x - nodeRadius, y - textHeight/3 - nodeRadius, 2*nodeRadius, 2*nodeRadius); //Drawing node's circle
            g.drawString(text, x - textWidth/2, y); //Printing node's value

            //Printing left subtree
            if(root.left != null) {
                drawTree(g, root.left, x - horizontalDistance, y + verticalDistance, x, y, true);
            }

            //Printing right subtree
            if(root.right != null) {
                drawTree(g, root.right, x + horizontalDistance, y + verticalDistance, x, y, false);
            }
        }
    }

    /**
     * Method which start process of drawing the tree
     * @param g Graphics used in tree's Panel
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Getting Graphic's width and height
        int width = getWidth();
        int height = getHeight();

        //Getting tree's height
        maxHeight = tree.getMaxHeight(tree.root);

        //Drawing tree
        drawTree(g, tree.root, width/2, height / (maxHeight+1), 0,0, false);
    }

    /**
     * Method used while converting tree from sequence of values and brackets (decoding)
     * @param s sequence of values and brackets (code)
     * @param parent parent of the currently analyzing node
     * @return updated/new created tree's node
     */
    public TreeNode<Integer>  convertToTree(String s, TreeNode<Integer> parent) {
        //Sequence is empty
        if(s.length() == 0 || s == null) 
            return null;

        //Index beyond string
        if(start >= s.length())
            return null;
        
        int value=0; //Converted integer value from the string
        boolean isZero = false; //Check if user type in value = 0
        boolean checked = false; //Flag which tells if we checked "isZero" case
        while(start < s.length() && Character.isDigit(s.charAt(start))) {
            int digit = Character.getNumericValue(s.charAt(start)); //digit converted from char

            //Check if value is actually zero
            if(!checked) {
                if(digit == 0) {
                    isZero = true;
                }
                checked = true;
            }

            value = value*10 + digit;
            start++;
        }

        //Creating new node in binary tree
        TreeNode<Integer> node = new TreeNode<Integer>(value);
        node.parent = parent;

        //Node is empty
        if(value == 0 && !isZero) {
            node.val = null;
        }

        if(start >= s.length())
            return node;
        
        //Going to the left subtree
        if(start < s.length() && s.charAt(start) == '(') {
            start++;
            node.left = convertToTree(s, node);
        }

        //End of analyzing subtree
        if(start < s.length() && s.charAt(start) == ')') {
            start++;
            return node;
        }

        //Going to the right subtree
        if(start < s.length() && s.charAt(start) == '(') {
            start++;
            node.right = convertToTree(s, node);
        }

        //End of analyzing subtree
        if(start < s.length() && s.charAt(start) == ')') {
            start++;
            return node;
        }

        return node;
    }
}
