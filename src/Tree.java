/**
 * Class responsible for creating binary tree's node
 */
class TreeNode<T extends Comparable<T>>  {
    public T val; //Value of the node
    TreeNode<T> left; //Node's left childe
    TreeNode<T> right; //Node's right child
    TreeNode<T> parent; //Node's parent

    TreeNode(T element) {
        this.val = element;
        left = null;
        right = null;
    }
}

/**
 * Class responsible for creating binary tree
 */
public class Tree<T extends Comparable<T>>{
    public TreeNode<T> root; //Binry tree's root
    String output; //Output information recived from tree's methods

    Tree() {
        root = null;
    }

    /**
     * Method responsible for starting the process of searching for a given node
     * @param element value of the node we are searching for
     * @return output information if the given node was found inside the tree or not
     */
    public String search(T element) {
        boolean found_element = searchTree(element, root);
        String output = "";

        if(found_element)
            output = "We found element: " + element + " inside the tree!";
        else 
            output = element + " is not inside the tree!";
        return output;
    }

    /**
     * Method which searchs for a given node inside the binary tree
     * @param element
     * @param currNode
     * @return
     */
    private boolean searchTree(T element, TreeNode<T> currNode) {
        //Node does not exist
        if(currNode == null)
            return false;
        
        //We found the node
        if(element.compareTo(currNode.val) == 0) 
            return true;
        
        if(element.compareTo(currNode.val) < 0) { //Going to the left subtree
            return searchTree(element, currNode.left);
        } else { //Going to the right subtree
            return searchTree(element, currNode.right);
        }
    }   

    /**
     * Method responsible for starting the process of insertion
     * @param element value of the new node
     */
    public void insert(T element) {
        this.root = insertTree(element, this.root, null);
    }

    /**
     * Method which inserts node given by user into the binary tree
     * @param element value of the new node
     * @param root node we are currently analyzing
     * @param parent current node's parent
     * @return
     */
    private TreeNode<T> insertTree(T element, TreeNode<T> root, TreeNode<T> parent) {
        //Inserting the node
        if(root == null) {
            root = new TreeNode<T>(element);
            root.parent = parent;
            return root;
        }

        if(element.compareTo(root.val) < 0) { //Going to the left subtree
            root.left = insertTree(element, root.left, root);
        } else { //Going to the right subtree
            root.right = insertTree(element, root.right, root);
        }

        return root;
    }

    /**
     * Method responsible for startin the process of deletion
     * @param element value of the node we are deleting
     */
    public void delete(T element) {
        this.root = deleteTree(element, this.root);
    }

    /**
     * Method which deletes a given node
     * @param element value of the node we are deleting
     * @param root node we are currently analyzing
     * @return updated node after deletion
     */
    public TreeNode<T> deleteTree(T element, TreeNode<T> root) {
        //Node does not exist
        if(root == null)
            return root; //No changes made inside the binary tree
        
        if(element.compareTo(root.val) < 0) { //Going to the left subtree
            root.left = deleteTree(element, root.left);
        } else if(element.compareTo(root.val) > 0) { //Going to the right subtree
            root.right = deleteTree(element, root.right);
        } else { //element == root.val

            if(root.left == null && root.right == null) { //Node without children
                return null;
            } else if(root.right != null) {
                //Node has right child -> we are looking for the smallest number bigger than deleting node
                root.val = findSuccessor(root);
                root.right = deleteTree(root.val, root.right);
            } else {
                //We are looking for the biggest number smaller than the deleting node
                root.val = findPredecessor(root);
                root.left = deleteTree(root.val, root.left);
            }
        }

        return root;
    }

    /**
     * Method which search for the successor (the smallest value still bigger than the root's value)
     * @param root node we are currently analyzing
     * @return successor
     */
    private T findSuccessor(TreeNode<T> root) {
        root = root.right;
        while(root.left != null) {
            root = root.left;
        }

        return root.val;
    }

    /**
     * Method which search for the predecessor (the biggest value still smaller than the root's value)
     * @param root node we are currently analyzing
     * @return predecessor
     */
    private T findPredecessor(TreeNode<T> root) {
        root = root.left;
        while(root.right != null) {
            root = root.right;
        }

        return root.val;
    }

    /**
     * Method responsible for starting the process of drawing the binary tree
     * @return string with nodes printed lexicographically
     */
    public String draw() {
        this.output = "";
        this.output = Drawing(root);
        return this.output;
    }

    /**
     * Method which prints tree's nodes lexicographically
     * @param root node we are currently analyzing
     */
    private String Drawing(TreeNode<T> root) {
        if(root != null) {
            return (root.val + "(" + Drawing(root.left) + ")(" + Drawing(root.right) + ")");
        }
        return "()";
    }


    /**
     * Method whichs counts the height of the binary tree
     * @param root node we are currently analyzing
     * @return height of the binary tree
     */
    public int getMaxHeight(TreeNode<T> root) {
        int leftSubtreeHeight, rightSubtreeHeigt;
        
        if(root == null)
            return 0;
        
        leftSubtreeHeight = getMaxHeight(root.left);
        rightSubtreeHeigt = getMaxHeight(root.right);

        return Math.max(leftSubtreeHeight, rightSubtreeHeigt) + 1;
    }
}
