
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
        if (root == null)
            root = node;
        else {
            boolean locationFound = false;
            BacktrackingBST.Node current = root;
            while (!locationFound) {
                if (node.key > current.key)
                    if (current.right == null) {
                        current.right = node;
                        locationFound = true;
                        node.parent = current;
                    }
                    else
                        current = current.right;
                else if (current.left == null) {
                    locationFound = true;
                    current.left = node;
                    node.parent = current;
                }
                else
                    current = current.left;
            }
        }
        stack.push(node);
        stack.push(-1);
    }

    public void delete(Node node) {
        // TODO: implement your code here
        if ( node == null )
            throw new IllegalArgumentException("node is null");
        stack.push(node);
        if ( node.left == null && node.right == null ){ // so node is a LEAF
            stack.push(0);
            if ( node.key == root.key )
                root = null;
            else if ( node.parent.left != null && node.parent.left.key == node.key ) { // so node is a LEFT child
                node.parent.left = null;
                node = null;
            }
            else {                                 // so node is a RIGHT child
                node.parent.right = null;
                node = null;
            }
        }
        else if ( node.right == null ){          // so node has one LEFT child
            stack.push(11);
            if ( node == root ) {
                root = root.left;
                root.left = null;
            }
            else if ( node.parent.left != null && node.parent.left.key == node.key ) { //?? so node is a LEFT child with one LEFT child
                node.parent.left = node.left;
                node.left.parent = node.parent;
            }
            else {                                 // node is a RIGHT child with one LEFT child
                node.parent.right = node.left;
                node.left.parent = node.parent;
            }
        }
        else if ( node.left == null ){ //?root? so node has one RIGHT child
            stack.push(12);
            if ( node.key == root.key ){
                root = root.right;
                root.right = null;
            }
            else if ( node.parent.right.key == node.key ) { // so node is a RIGHT child with one RIGHT child
                node.parent.right = node.right;
                node.right.parent = node.parent;
            }
            else {                                  // node is a LEFT child with one RIGHT child
                node.parent.right = node.left;
                node.right.parent = node.parent;
            }
        }
        else{
            replaceNode(node);
        }
/*        else{ // node has two children
            if ( node.key == root.key ){
                BacktrackingBST.Node baby = node.left; // baby is gonna replace the root
                while ( baby.right != null )
                    baby = baby.right;
                if ( baby.left != null ) {
                    baby.parent.right = baby.left;
                    baby.left.parent = baby.parent;
                }
                BacktrackingBST.Node tempLeft = root.left;
                BacktrackingBST.Node tempRight = root.right;
                root = baby;
                root.left = tempLeft;
                root.right = tempRight;
            }
            else{ // when node isn't the root and has two children
                BacktrackingBST.Node baby = node.left; //baby is gonna change the node
                while ( baby.right != null )
                    baby = baby.right; // then, baby doesn't have right child
//                BacktrackingBST.Node temp = baby;
//                if ( baby.left != null )
//                    baby.right = baby.parent.right;
//                    baby.parent.right = temp.right;
                if ( node.parent.left.key == node.key ) // node is left child
                    node.parent.left = baby;
                else                            // node is right child
                    node.parent.right = baby;
                baby.right = node.right;
            }
        }*/
    }

    private void replaceNode (Node node){ //we assume node has at least one child
        if ( node.left == null){ // so node has one RIGHT child
            BacktrackingBST.Node replace = node.right;
            replace.setParent(node);
        }
        if ( node.right == null){ // so node has one LEFT child
            BacktrackingBST.Node replace = node.left;
            replace.setParent(node);
        }
        else { // has 2 children
            BacktrackingBST.Node replace = node.left;
            while ( replace.right != null )
                replace = replace.right;
            delete(replace);
            replace.setLeft(node);
            replace.setRight(node);
            replace.setParent(node);
        }
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
            newNode.parent = this;
        }

        public void setLeft(Node newNode){
            this.left = newNode;
            newNode.parent = this;
        }

        public void setParent(Node oldNode){
            if(oldNode.parent != null) {
                if (oldNode.parent.right == oldNode) {
                    oldNode.parent.right = this;
                } else {
                    oldNode.parent.left = this;
                }
                this.parent = oldNode;
            }
            else
                this.parent = null;
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


