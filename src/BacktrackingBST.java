
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

    //iteration serch as shown in class
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

    //iteration insert as shown in class
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
                        current.setRight(node);         //a helper function set at the nodes
                        locationFound = true;
                    }
                    else
                        current = current.right;
                else if (current.left == null) {
                    locationFound = true;
                    current.setLeft(node);              //a helper function set at the nodes
                }
                else
                    current = current.left;
            }
        }
        Object[] memoArray = {node};                     //will be used in backtracking
        stack.push(memoArray);
    }

    public void delete(Node node) {
        if ( node == null )
            throw new IllegalArgumentException("node is null");
        if(search(node.key) != null) {
            if (node.left == null && node.right == null) { // so node is a LEAF
                if (node == root)
                    root = null;
                else{
                    node.removeFromParent();
                }
                Object[] memoArray = {false, node};      //will be used in backtracking
                stack.push(memoArray);
            }
            else
                deleteNotLeaf(node);
        }
    }

    //a helper function to make sure when a node is deleted that the tree is still a legal binary tree
    private void deleteNotLeaf (Node node) { //we assume node has at least one child
        Object[] memoArray = new Object[3];
        memoArray[0] = false;
        memoArray[1] = node;
        if (node.right != null && node.left != null) {
            memoArray[2] = restructure(node);       //this will return another array but that is why we are using a Object array
            //need to be vary careful when using the backtrack later on
            memoArray[0] = true;
        }
        else{
            if (node.right != null) {
                if (node == root)
                    root = node.right;
                else {
                    if (node.isLeftChild() > 0)
                        node.parent.setLeft(node.right);
                    else
                        node.parent.setRight(node.right);
                }

            }
            else {
                if(node == root)
                    root = node.left;
                else{
                    if(node.isLeftChild() > 0)
                        node.parent.setLeft(node.left);
                    else
                        node.parent.setRight(node.left);
                }
            }
        }
        stack.push(memoArray);
    }

    private Object[] restructure(Node node){
        Node replacement = predecessor(node);       //since node has 2 children there will always be a predecessor
        Object[] memoArray = {replacement, replacement.left, replacement.parent}; //we will keep the pointers for the backtrack
        delete(replacement);
        stack.pop();                        //dont want to keep the replacement in the stack just yet
        if(replacement.replace(node))       //will happen only if node is the old root
            root = replacement;
        return memoArray;
    }

    public Node minimum() {
        if (root == null)
            throw new IllegalArgumentException("empty tree");
        BacktrackingBST.Node current = root;
        while(current.left != null){
            current = current.left;         //maximum will always be the left child
        }
        return current;
    }

    public Node maximum() {
        if ( root == null )
            throw new IllegalArgumentException("empty tree");
        BacktrackingBST.Node current = root;
        while ( current.right != null ){
            current = current.right;        //maximum will always be the right child
        }
        return current;
    }

    public Node successor(Node node) {
        if ( root == null || node == this.maximum() || search(node.key) == null )
            // if tree is empty or there is no successor or the node doesn't exist throw an error
            throw new IllegalArgumentException("node doesn't exist or is already the maximum");
        BacktrackingBST.Node current = node;
        if ( current.right != null ) {
            current = current.right;
            while (current.left != null)
                current = current.left;      // 1 right then all the way left
            return current;
        }
        while (current.parent.right != null && current.parent.right == current ){
            current = current.parent;
        }
        return current.parent;
    }

    public Node predecessor(Node node) {
        if(root == null || node == this.minimum() || this.search(node.key) == null)
            // if tree is empty or there is no successor or the node doesn't exist throw an error
            throw new IllegalArgumentException("node doesn't exist or is already the minimum");
        BacktrackingBST.Node current = node;
        if (current.left != null) {
            current = current.left;
            while (current.right != null)
                current = current.right;        // 1 left then all the way right
            return current;

        }
        else {
            while (current.parent.left != null && current.parent.left == current)
                current = current.parent;
        }
        return current.parent;
    }

    @Override
    public void backtrack() {       //we will assume here that the stack wasn't changed
        if (!stack.isEmpty()){
            Object[] arr1 = (Object[]) stack.pop();
            if(arr1.length == 1) {                      //then we need to backtrack from an insert
                delete((Node) arr1[0]);
                redoStack.push(stack.pop());            //in delete we update the stack but we want to keep the value in the redoStack
            }
            else {
                if (! ((boolean) arr1[0]))                //then we need to backtrack from a delete of 1
                    backtrackFromDelete((Node) arr1[1]);
                else {                                      //we are backtracking from a delete and a node that was moved
                    Object[] arr2 = (Object[]) arr1[2];     //we made sure that there is a true in the array only if we restructured
                    backtrackFromRestructure((Node) arr1[1], (Node) arr2[0], (Node) arr2[1], (Node) arr2[2]);
                }
            }
        }
    }

    //helper function to backtrack from a single delete
    //meaning the node only holds 1 child
    public void backtrackFromDelete(Node node){
        if(node.right == null & node.left == null){ // is a leaf
            insert(node);
            redoStack.push(stack.pop());        //we want to keep the information in retrack stack
        }
        else {
            if (node.left != null) {
                if (node.left.isLeftChild() == 0)
                    root = node;
                if (node.left.isLeftChild() > 0)
                    node.parent.setLeft(node);
                else
                    node.parent.setRight(node);
                node.setLeft(node.left);
            }
            else{
                if (node.right.isLeftChild() == 0)
                    root = node;
                if (node.right.isLeftChild() > 0)
                    node.parent.setLeft(node);
                else
                    node.parent.setRight(node);
                node.setRight(node.right);
            }
            Object[] memoArray = {node};
            redoStack.push(memoArray);
        }
    }

    //helper function to come back if we deleted 2 nodes
    public void backtrackFromRestructure(Node deleted, Node restructured, Node memoLeftChild, Node memoParent){
        if(deleted.replace(restructured))       //root has been changed during the replacement
            root = deleted;
        restructured.setLeft(memoLeftChild);
        restructured.setRight(null);
        if (memoParent == deleted)          //the restructured node will always be a right child unless it is deleted child
            deleted.setLeft(restructured);
        else
            memoParent.setRight(restructured);
        Object [] memoArray = {deleted};
        redoStack.push(memoArray);
    }

    @Override
    public void retrack() {
        if (!redoStack.isEmpty()){
            Object [] arr = (Object[]) redoStack.pop();
            if(arr.length == 1){
                delete((Node) arr[0]);      // need to retrack from an insert
            }
            else
                insert((Node) arr[1]);      //need to retrack from a delete
        }
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

        // a helper function that is used a lot to set the right child
        public void setRight(Node newNode){
            this.right = newNode;
            if (newNode != null)
                newNode.parent = this;
        }

        //same for left
        public void setLeft(Node newNode){
            this.left = newNode;
            if (newNode != null)
                newNode.parent = this;
        }

        // a function to set replace the oldNode will return true if it changed the root
        //the function will not change the oldNode
        public boolean replace(Node oldNode){
            this.setRight(oldNode.right);
            this.setLeft(oldNode.left);
            this.removeFromParent();
            if (oldNode.parent != null){
                if (oldNode.isLeftChild() > 0)
                    oldNode.parent.setLeft(this);
                else
                    oldNode.parent.setRight(this);
                return false;
            }

            this.parent = null;     //the node has no parent meaning it was a root
            return true;
        }

        // a function to set parent to replace the oldNode will return true if it changed the root
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

        //return 1 if ture -1 if false and 0 if there is no parent
        public int isLeftChild(){
            if (parent !=null) {
                if (this.parent.left == this)
                    return 1;
                return -1;
            }
            return 0;
        }

        public int isRightChild(){
            if (parent !=null) {
                if (this.parent.right == this)
                    return 1;
                return -1;
            }
            return 0;
        }

        //removes the node from the parent
        public void removeFromParent() {
            if (this.isRightChild() > 0)
                this.parent.right =null;
            if (this.isLeftChild() > 0)
                this.parent.left = null;
        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        //recursion function to print the tree
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


        public Node getParent() {
            return parent;
        }
    }

}


