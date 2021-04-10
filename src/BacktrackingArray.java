
public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int inserted;
    // TODO: implement your code here

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        inserted = 0;
    }

    @Override
    public Integer get(int index){
    	return arr[index];
    }

    @Override
    public Integer search(int k) {
    	for(int i = 0; i < inserted; i ++)
    	    if (arr[i] == k)
    	        return i;
    	return -1;
    }

    @Override
    public void insert(Integer x) {
        if(inserted  == arr.length)
            throw new IllegalArgumentException("array is full");
        arr[inserted] = x;
        stack.push(x);
        stack.push(inserted + 1);
        inserted = inserted + 1;
    }

    @Override
    public void delete(Integer index) {
        if (index <= arr.length)
            throw new IllegalArgumentException("array too small");
        stack.push(arr[index]);
        stack.push(-index - 1);
        for( int i = index; i < inserted - 1; i++)
            arr[i]= arr[i+1];
        inserted = inserted - 1;
    }

    @Override
    public Integer minimum() {
        if (inserted == 0)
            throw new IllegalArgumentException("there are no values");
        int min = 0;
        for (int i = 1; i < inserted; i++)
            if (arr[min] > arr[i])
                min = i;
        return min;
    }

    @Override
    public Integer maximum() {
        if (inserted == 0)
            throw new IllegalArgumentException("there are no values");
        int max = 0;
        for (int i = 1; i < inserted; i++)
            if (arr[max] < arr[i])
                max = i;
        return max;
    }

    @Override
    public Integer successor(Integer index) {
        if(inserted < index || inserted <=1 || index < 0 || this.maximum() == arr[index])
            throw new IllegalArgumentException("wrong input");
        int ans = maximum();
        for (int i = 0; i < inserted; i++){
            if(arr [index] < arr[i] && arr[i] <arr[ans])
                ans = i;
        }
        return ans;
    }

    @Override
    public Integer predecessor(Integer index) {
        if(inserted < index || inserted <=1 || index < 0 || this.minimum() == arr[index])
            throw new IllegalArgumentException("wrong input");
        int ans = minimum();
        for (int i = 0; i < inserted; i++){
            if(arr [index] > arr[i] && arr[i] > arr[ans])
                ans = i;
        }
        return ans;
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()){
            int index = (int) stack.pop();
            int value = (int) stack.pop();
            if (index > 0)
                delete(index -1);
            else{
                index = 1 -index;
                for(int i = inserted; i > index; i --)
                    arr[i+1] = arr[i];
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
        if (inserted != 0)
            str = Integer.toString(arr[0]);
        for(int i = 1; i<inserted; i++){
            str = str + " " +Integer.toString(arr[i]);
        }
        System.out.println(str);
    }
}
