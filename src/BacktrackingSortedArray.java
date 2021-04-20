

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
        if ( inserted >= arr.length )
            throw new IllegalArgumentException("The array is full!");
        int min = 0;
        int max = inserted;
        int index = (min + max) / 2;
        if ( min == max ){
            arr[index] = x;
        }
        else if ( x < minimum() ){
            for (int i=inserted-1; i>=0; i--){
                arr[i+1] = arr[i];
            }
            index = min;
            arr[min] = x;
        }
        else if ( x > maximum() ) {
            index = max;
            arr[max] = x;
        }
        else{
            while ( min <= max ){
                index = (min + max) / 2;
                if ( x > arr[index] && x < arr[index+1] ) {
                    for (int i=inserted-1; i>index; i--){
                        arr[i+1] = arr[i];
                    }
                    arr[index+1] = x;
                }
                else if ( x < arr[index] )
                    max = index - 1;
                else
                    min = index + 1;
            }
        }
        stack.push(index);
        stack.push(x);
        inserted = inserted + 1;
    }

    @Override
    public void delete(Integer index) {
        if ( index < 0 || index >= inserted )
            throw new IllegalArgumentException("Index out of bounds");
        stack.push(-1);
        stack.push(arr[index]);
        for (int i=index; i<inserted-1; i++){
            arr[i] = arr[i+1];
        }
        inserted = inserted - 1;
    }

    @Override
    public Integer minimum() {
        if ( inserted > 0 )
            return arr[0];
        else
    	    return null; // temporal return command to prevent compilation error
    }

    @Override
    public Integer maximum() {
        if (inserted > 0)
    	    return arr[inserted-1];
        else
            return null; // temporal return command to prevent compilation error
    }

    @Override
    public Integer successor(Integer index) {
        if ( index < 0 || index >= inserted - 1 )
            throw new IllegalArgumentException("Index out of bounds");
        else
    	    return arr[index+1]; // temporal return command to prevent compilation error
    }

    @Override
    public Integer predecessor(Integer index) {
        if ( index < 1 || index >= inserted )
            throw new IllegalArgumentException("Index out of bounds");
        else
            return arr[index-1]; // temporal return command to prevent compilation error
    }

    @Override
    public void backtrack() {
        if ( stack.isEmpty() )
            throw new IllegalArgumentException("There's no backtrack yet");
        int value = (int) stack.pop();
        int sign = (int) stack.pop();
        if ( sign < 0 ){
            insert(value);
        }
        else
            delete(sign);
    }

    @Override
    public void retrack() {
		/////////////////////////////////////
		// Do not implement anything here! //
		/////////////////////////////////////
    }

    @Override
    public void print() {
        String str = "";
        str = str + Integer.toString(arr[0]);
        for (int i=1; i<inserted; i++){
            str = str + " " + Integer.toString(arr[i]);
        }
        System.out.println(str);
    }
    
}