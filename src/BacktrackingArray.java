

public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int inserted = 0;       // a new field to keep track of how many values are inserted

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }

    @Override
    public Integer get(int index){
    	return arr[index];
    }

    @Override
    public Integer search(int k) {
        for (int i=0; i<inserted; i++){
            if (arr[i] == k)
                return i;
        }
    	return -1;
    }

    @Override
    public void insert(Integer x) { //What to do if arr is full?
        if (inserted >= arr.length)
            throw new IllegalArgumentException("The array is full!");
        stack.push(x);
        stack.push(-1);         // a flag to remember that an insert happened
        arr[inserted] = x;
        inserted = inserted + 1;
    }

    @Override
    public void delete(Integer index) {
        if (index >= arr.length)
            throw new IllegalArgumentException("Index out of bounds");
        stack.push(arr[index]);         //an index if the delete will never be negative
        stack.push(index);
        arr[index] = arr[inserted-1]; //our array is unsorted, that means we don't care about the order
        inserted = inserted - 1;
    }

    @Override
    public Integer minimum() {
        if (inserted == 0)
            throw new IllegalArgumentException("There is no value");
        int min = 0;
        for (int i=1; i<inserted; i++){
            if ( arr[min] > arr[i] )
                min = i;
        }
    	return min;
    }

    @Override
    public Integer maximum() {
        if (inserted == 0)
            throw new IllegalArgumentException("There is no value");
        int max = 0;
        for (int i=1; i<inserted; i++){
            if ( arr[max] < arr[i] )
                max = i;
        }
    	return max;
    }

    @Override
    public Integer successor(Integer index) {
        if (inserted < index || index < 0 || inserted < 2 || this.maximum() == index)
            throw new IllegalArgumentException("No successor");
        int output = maximum();
        for (int i=0; i<inserted; i++){
            if (arr[index] < arr[i] && arr[i] < arr[output])
                output = i;
        }
    	return output;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (inserted < index || index < 0 || inserted < 2 || this.minimum() == index)
            throw new IllegalArgumentException("No predecessor");
        int output = minimum();
        for (int i=0; i<inserted; i++){
            if (arr[index] > arr[i] && arr[i] > arr[output])
                output = i;
        }
    	return output;
    }

    @Override
    public void backtrack() {
        if ( !stack.isEmpty() ){
            int index = (int) stack.pop();
            int value = (int) stack.pop();
            if ( index == -1 ) {            //then we are backtracking from an insert
                delete(inserted -1);   //insert will always be the last value
                stack.pop();            // we got 2 new unwanted objects
                stack.pop();
            }
            else{
                arr[inserted] = arr[index];
                arr[index] = value;
                inserted = inserted + 1;
            }
        }
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
        if ( inserted > 0 )
            str = str + Integer.toString(arr[0]);
        for (int i=1; i<inserted; i++){
                str = str + " " + Integer.toString(arr[i]);
        }
        System.out.println(str);
    }
    
}
