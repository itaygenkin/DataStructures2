
public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {
        int counter = 0;
        for(int i = 0; i < arr.length; i++){
            if ( arr[i] == x )
                return i;
            counter = counter + 1;
            myStack.push(arr[i]);
            if ( counter == forward ){
                for (int j = 0; j < back; j++)
                    myStack.pop();
                i = i - back;
            }
        }
    	return -1; // temporal return command to prevent compilation error
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
    	int min = 0;
    	int max = arr.length - 1;
    	while ( min <= max ){
            int index = (min + max) / 2;
    	    int inconsistencies = Consistency.isConsistent(arr);

    	    while ( inconsistencies > 0 && !myStack.isEmpty() ){
    	        max = (int) myStack.pop();
    	        index = (int) myStack.pop();
    	        min = (int) myStack.pop();
    	        if (arr[index] == x)
    	            return index;
                inconsistencies = Consistency.isConsistent(arr); //to be assure that 'inconsistencies' is currently correct
            }
    	    if ( arr[index] == x ){
    	        return index;
            }
            else if ( arr[index] < x ) {
                min = index + 1;
            } else {
                max = index - 1;
            }
            myStack.push(min);
            myStack.push(index);
            myStack.push(max);
        }

    	return -1;
    }
    
}