

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    public int[] arr; // This field is public for grading purposes. By coding conventions and best practice it should be private.
    // TODO: implement your code here
    private int inserted = 0;

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }
    
    @Override
    public Integer get(int index){
        // TODO: implement your code here
        if ( index >= inserted || index < 0 )
            throw new IllegalArgumentException("Invalid index");
    	return arr[index]; // temporal return command to prevent compilation error
    }

    @Override
    public Integer search(int k) {
        // TODO: implement your code here
        int min = 0;
        int max = inserted - 1;
        while ( min <= max ){
            int mid = (min + max) / 2;
            if ( arr[mid] == k )
                return mid;
            else if (arr[mid] > k )
                max = mid - 1;
            else
                min = mid + 1;
        }
    	return -1; // temporal return command to prevent compilation error
    }

    @Override
    public void insert(Integer x) {
        // TODO: implement your code here
        if ( inserted >= arr.length )
            throw new IllegalArgumentException("The array is full!");
        int index = 0;
        while ( index < inserted ){
            int key = arr[index];
            if ( x < key ) {
                arr[index] = x;
                stack.push(index);
                stack.push(x);
                while ( index < inserted ) {
                    int temp = arr[index+1];
                    arr[index+1] = temp;
                    index = index + 1;
                }
            }
            else
                index = index + 1;
        }
        inserted = inserted + 1;
    }

    @Override
    public void delete(Integer index) {
        // TODO: implement your code here
        stack.push(index + arr.length);
        stack.push(arr[index]);
        for (int i=index; i<inserted-1; i++){
            arr[i] = arr[i+1];
        }
        inserted = inserted - 1;
    }

    @Override
    public Integer minimum() {
        // TODO: implement your code here
        if ( inserted > 0 )
            return arr[0];
        else
    	    return null; // temporal return command to prevent compilation error
    }

    @Override
    public Integer maximum() {
        // TODO: implement your code here
        if (inserted > 0)
    	    return arr[inserted-1];
        else
            return null; // temporal return command to prevent compilation error
    }

    @Override
    public Integer successor(Integer index) {
        // TODO: implement your code here
        if ( index < 0 || index >= inserted - 1 )
            throw new IllegalArgumentException("Index out of bounds");
        else
    	    return arr[index+1]; // temporal return command to prevent compilation error
    }

    @Override
    public Integer predecessor(Integer index) {
        // TODO: implement your code here
        if ( index < 1 || index >= inserted )
            throw new IllegalArgumentException("Index out of bounds");
        else
            return arr[index-1]; // temporal return command to prevent compilation error
    }

    @Override
    public void backtrack() {
        // TODO: implement your code here
        if ( stack.isEmpty() )
            throw new IllegalArgumentException("There's no backtrack yet");
        int value = (int) stack.pop();
        int index = (int) stack.pop();
        if ( index >= arr.length ){
            index = index - arr.length;
            insert(value);
        }
        else
            delete(index);
    }

    @Override
    public void retrack() {
		/////////////////////////////////////
		// Do not implement anything here! //
		/////////////////////////////////////
    }

    @Override
    public void print() {
        // TODO: implement your code here
    }
    
}
