

public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int inserted = 0;

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }

    @Override
    public Integer get(int index){
    	return arr[index]; // temporal return command to prevent compilation error
    }

    @Override
    public Integer search(int k) {
        for (int i=0; i<inserted; i++){
            if (arr[i] == k)
                return i;
        }
    	return -1; // temporal return command to prevent compilation error
    }

    @Override
    public void insert(Integer x) { //What to do if arr is full?
        if (inserted >= arr.length)
            throw new IllegalArgumentException("The array is full!");
        stack.push(x);
        stack.push(-1);
        arr[inserted] = x;
        inserted = inserted + 1;
    }

    @Override
    public void delete(Integer index) {
        if (index >= arr.length)
            throw new IllegalArgumentException("Index out of bounds");
        stack.push(arr[index]);
        stack.push(index);
        for (int i=index; i<inserted-1; i++){
            arr[i] = arr[i+1];
        }
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
    	return min; // temporal return command to prevent compilation error
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
    	return max; // temporal return command to prevent compilation error
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
            if ( index == -1 ) {
                delete(inserted -1);
                stack.pop();
                stack.pop();
            }
            else{
                for (int i = inserted - 1; i >= index; i--){
                    arr[i+1] = arr[i];
                }
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
