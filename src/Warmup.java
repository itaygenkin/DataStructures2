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
        int max = arr.length;
        int min = 0;
        while(max -1 <= min){       //never going to reach the max
            int index = (max + min) /2;
            if(arr[index] == x)
                return index;
            int inconsistencies = Consistency.isConsistent(arr);
            while (inconsistencies > 0 & !myStack.isEmpty()){
                int backTrack = (int) myStack.pop();                    // need to avoid using a pop without checking if not empty
                if (backTrack > index)
                    max = backTrack;
                else
                    min = backTrack;
                inconsistencies = Consistency.isConsistent(arr);        //the incorrect step may have been taken
            }
            if(arr[index] > x){         //remember where you came from
                myStack.push(max);
                max = (max - min)/2;
            }
            else {
                myStack.push(min);
                min = (max - min) / 2 + 1;
            }
        }
        if (arr[min] == x)          //maybe the key was in the last iteration
            return min;
        return -1;                  //key was not found
    }
}