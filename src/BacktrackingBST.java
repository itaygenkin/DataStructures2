

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

    public void insert(Node node) {
        if (node == null)
            throw new IllegalArgumentException("node is null");
        boolean locationFound = false;
        BacktrackingBST.Node current = root;
        while (!locationFound){
            if (node.key > current.key)
                if (current.right == null) {
                    current.right = node;
                    locationFound = true;
                }
                else
                    current = current.left;
            else
                if (current.left == null){
                    locationFound = true;
                    current.left = node;
                }
                else
                    current = current.left;
        }
        stack.push(node);
        stack.push(-1);
    }

    public void delete(Node node) {
        if(node == null)
            throw new IllegalArgumentException("node is null");
        int depth = 0;
        boolean nodeFound = false;
        BacktrackingBST.Node current = root;
        while (!nodeFound && root != null){
            if (current.key == node.key){
                deleteThisNode(node);
                nodeFound = true;
                stack.push(node);
                stack.push(depth);
            }
            depth = depth + 1;
            if (current.key > node.key && current.left != null)
                current = current.left;
            else if (current.key < node.key && current.right != null)
                current = current.left;
            else
                nodeFound = true;
        }
    }

    public void deleteThisNode(Node node){
        BacktrackingBST.Node parent = node.parent;
        if (node.right == null && node.left == null){
            if(parent.left.key == node.key)
                parent.left
        }


    }

    public Node minimum() {
        // TODO: implement your code here
    	return null; // temporal return command to prevent compilation error
    }

    public Node maximum() {
        // TODO: implement your code here
    	return null; // temporal return command to prevent compilation error
    }

    public Node successor(Node node) {
        // TODO: implement your code here
    	return null; // temporal return command to prevent compilation error
    }

    public Node predecessor(Node node) {
        // TODO: implement your code here
    	return null; // temporal return command to prevent compilation error
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
        // TODO: implement your code here
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

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
        
    }

}
