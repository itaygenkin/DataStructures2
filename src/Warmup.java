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
    	// Your implementation should contain a this line:
        //int inconsistencies = Consistency.isConsistent(arr);
    	return consistentBinSearch(arr, x, myStack, 0, arr.length, arr.length % 2);
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack, int min, int max, int odd){
        int inconsistencies = Consistency.isConsistent(arr);
        int index = (max + min) /2;
        myStack.push(arr[index]);
        while (inconsistencies > 0 && !myStack.isEmpty()){
            int temp =(int) myStack.pop();
            if (temp < arr[index])
                min = max - 2 * min + odd;
            else
                max = 2 * max - min + odd;
            inconsistencies = inconsistencies - 1;
        }
        index = (min + max) / 2;
        if (min + 1 >= max && inconsistencies == 0){
            if (arr[index] == x)
                return index;
            return -1;
        }
        if (arr[index] < x)
            return consistentBinSearch(arr, x,myStack ,index + 1 ,max,(max+min) % 2);
        return consistentBinSearch(arr, x,myStack ,min ,index,(max+min) % 2);
    }
    
}