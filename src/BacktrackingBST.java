
import java.util.NoSuchElementException;

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    private BacktrackingBST.Node root = null;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
        if (root == null) {
            throw new NoSuchElementException("empty tree has no root");
        }
        return root;
    }

    public Node search(int k) {
        if (root == null)
            return null;
        BacktrackingBST.Node current = root;
        while(current.key != k){
            if(current.left != null && current.key > k)
                current = current.left;
            else if (current.right != null)
                current = current.right;
            else
                return null;
        }
        return current;
    }

    public int searchDepth(Node node){
        if (root == null)
            return -1;
        int depth = 0;
        BacktrackingBST.Node current = root;
        while(current.key != node.key){
            if(current.left != null && current.key > node.key) {
                current = current.left;
                depth = depth +1;
            }
            else if (current.right != null) {
                current = current.right;
                depth = depth +1;
            }
            else
                return -1;
        }
        return depth;
    }

    public void insert(Node node) {
        if (node == null)
            throw new IllegalArgumentException("node is null");
        int depth = 0;
        if (root == null)
            root = node;
        else {
            boolean locationFound = false;
            BacktrackingBST.Node current = root;
            while (!locationFound) {
                if (node.key > current.key)
                    if (current.right == null) {
                        current.setRight(node);
                        locationFound = true;
                    }
                    else {
                        current = current.right;
                        depth = depth +1;
                    }
                else if (current.left == null) {
                    locationFound = true;
                    current.setLeft(node);
                }
                else {
                    current = current.left;
                    depth = depth +1;
                }
            }
        }
        Object[] memoArray = {node, depth};                     //will be used in backtracking
        stack.push(memoArray);
    }

    public void delete(Node node) {
        if ( node == null )
            throw new IllegalArgumentException("node is null");
        int depth = searchDepth(node);
        if(depth != -1) {
            if (node.left == null && node.right == null) { // so node is a LEAF
                if (node == root)
                    root = null;
                else{
                    node.removeFromParent();
                }
                Object[] memoArray = {node, depth, false};      //will be used in backtracking
                stack.push(memoArray);
            }
            else
                replaceNode(node,depth);
        }
    }

    private void replaceNode (Node node, int depth){ //we assume node has at least one child
        boolean deleted2 = false;
        Node replace = node.right;
        if (node.right == null)         //if it only has child in left
            replace = node.left;
        if (node.left != null && node.right != null){ // has 2 children
            deleted2 = true;
            replace = node.left;
            while ( replace.right != null) {
                replace = replace.right;
            }
            delete(replace);                //making sure we keep the predecessor children
            replace.setLeft(node.left);
            replace.setRight(node.right);
        }
        if(replace.setParent(node))         //this will happen if the root hase been changed
            root = replace;
        Object[] memoArray = {node, depth, deleted2};       //will be used in backtracking
        stack.push(memoArray);
    }

    public Node minimum() {
        if (root == null)
            throw new IllegalArgumentException("empty tree");
        BacktrackingBST.Node current = root;
        while(current.left != null){
            current = current.left;
        }
        return current;
    }

    public Node maximum() {
        if ( root == null )
            throw new IllegalArgumentException("empty tree");
        BacktrackingBST.Node current = root;
        while ( current.right != null ){
            current = current.right;
        }
        return current;
    }

    public Node successor(Node node) {
        if ( root == null || node.key == this.maximum().key || search(node.key) == null )
            throw new IllegalArgumentException("node doesn't exist or is already the maximum");
        BacktrackingBST.Node current = this.search(node.getKey());
        if ( current.right != null ) {
            current = current.right;
            while (current.left != null)
                current = current.left;
            return current;
        }
        while ( current.parent != null && current.parent.left.key != current.key ){
            current = current.parent;
        }
        return current.parent;
    }

    public Node predecessor(Node node) {
        if(root == null || node.key == this.minimum().key || this.search(node.key) == null)
            throw new IllegalArgumentException("node doesn't exist or is already the minimum");
        BacktrackingBST.Node current = this.search(node.key);
        if (current.left != null) {
            current = current.left;
            while (current.right != null)
                current = current.right;
        }
        else {
            current = current.parent;
            while (current.parent != null && current.parent.key < current.key)
                current = current.parent;
        }
        return current;
    }

    @Override
    public void backtrack() {
        // TODO: implement your code here

    }

    @Override
    public void retrack() {
        // TODO: implement your code here
    }

    public void printPreOrder(){
        if ( root == null )
            System.out.println("");
        else
            System.out.println(root.toString());
    }

    @Override
    public void print() {
        printPreOrder();
    }

    public static class Node {
        // These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;

        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public void setRight(Node newNode){
            this.right = newNode;
            if (newNode != null)
                newNode.parent = this;
        }

        public void setLeft(Node newNode){
            this.left = newNode;
            if (newNode != null)
                newNode.parent = this;
        }

        public boolean setParent(Node oldNode){
            if(oldNode.parent != null) {
                if (oldNode.parent.right == oldNode) {
                    oldNode.parent.right = this;
                } else {
                    oldNode.parent.left = this;
                }
                this.parent = oldNode.parent;
                return false;
            }
            this.parent = null;
            return true;
        }

        public void removeFromParent() {
            if (this.parent != null){
                if (this.parent.right == this)
                    this.parent.right = null;
                if (this.parent.left == this)
                    this.parent.left = null;
            }

        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public String toString(){
            String str = Integer.toString(key);
            if ( left == null && right == null )
                return str;
            else {
                if ( left != null )
                    str = str + " " + left.toString();
                if ( right != null )
                    str = str + " " + right.toString();
            }
            return str;
        }


    }

}


