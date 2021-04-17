//dvskflkds

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

    	    if ( arr[index] == x ){
    	        return index;
            }
    	    int inconsistencies = Consistency.isConsistent(arr);
    	    while ( inconsistencies > 0 ){
    	        int temp = (int) myStack.pop();
                if ( temp > arr[index] ){
                    max = 2 * index - min + 1;
                }
                else
                    min = 2 * index - max - 1;
                inconsistencies = inconsistencies - 1;
            }
            if ( arr[index] > x )
                min = index + 1;
            else
                max = index - 1;
            myStack.push(arr[index]);
        }
    	// Your implementation should contain a this line:
//    	int inconsistencies = Consistency.isConsistent(arr);
    	return -1; // temporal return command to prevent compilation error
    }
    
}