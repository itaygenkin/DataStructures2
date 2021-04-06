
public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {
        // TODO: implement your code here
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
        // TODO: implement your code here
    	int min = 0;
    	int max = arr.length - 1;
    	while ( min <= max ){
            int index = (min + max) / 2;

    	    int inconsistencies = Consistency.isConsistent(arr);
    	    while ( inconsistencies > 0 && !myStack.isEmpty() ){
    	        index = (int) myStack.pop();
                inconsistencies = inconsistencies - 1;
            }
    	    if ( arr[index] == x ){
    	        return index;
            }
            else if (arr[index] <= x) {
                max = index - 1;
            } else {
                min = index + 1;
            }
            myStack.push(index);
        }
    	// Your implementation should contain a this line:
//    	int inconsistencies = Consistency.isConsistent(arr);
    	return -1; // temporal return command to prevent compilation error
    }
    
}