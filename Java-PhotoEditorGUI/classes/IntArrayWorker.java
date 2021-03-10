import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
public class IntArrayWorker
{
    /** two dimensional matrix */
    private int[][] matrix = null;

    /** set the matrix to the passed one
     * @param theMatrix the one to use
     */
    public void setMatrix(int[][] theMatrix)
    {
        matrix = theMatrix;
    }

    /**
     * Method to return the total 
     * @return the total of the values in the array
     */
    public int getTotal()
    {
        int total = 0;
        for (int row = 0; row < matrix.length; row++)
        {
            for (int col = 0; col < matrix[0].length; col++)
            {
                total = total + matrix[row][col];
            }
        }
        return total;
    }

    /**
     * Method to return the total using a nested for-each loop
     * @return the total of the values in the array
     */
    public int getTotalNested()
    {
        int total = 0;
        for (int[] rowArray : matrix)
        {
            for (int item : rowArray)
            {
                total = total + item;
            }
        }
        return total;
    }

    /**
     * Method to fill with an increasing count
     */
    public void fillCount()
    {
        int numCols = matrix[0].length;
        int count = 1;
        for (int row = 0; row < matrix.length; row++)
        {
            for (int col = 0; col < numCols; col++)
            {
                matrix[row][col] = count;
                count++;
            }
        }
    }

    /**
     * print the values in the array in rows and columns
     */
    public void print()
    {
        for (int row = 0; row < matrix.length; row++)
        {
            for (int col = 0; col < matrix[0].length; col++)
            {
                System.out.print( matrix[row][col] + " " );
            }
            System.out.println();
        }
        System.out.println();
    }

    /** 
     * fill the array with a pattern
     */
    public void fillPattern1()
    {
        for (int row = 0; row < matrix.length; row++)
        {
            for (int col = 0; col < matrix[0].length; 
            col++)
            {
                if (row < col)
                    matrix[row][col] = 1;
                else if (row == col)
                    matrix[row][col] = 2;
                else
                    matrix[row][col] = 3;
            }
        }
    }

    /**
     * returns the count of the
     * number of times a passed integer value is found in the matrix.
     * 
     * @param x the integer it's looking for
     * @return count the count
     */
    public int getCount(int x)
    {
        int count = 0;
        for (int row = 0; row < matrix.length; row++)
        {
            for (int col = 0; col < matrix[0].length; col++)
            {
                if (matrix[row][col] == x)
                {
                    count ++;
                }
            }
        }
        return count;
    }

    /**
     * returns the largest value in the matix
     * 
     * @return largest the largest value in the matrix
     */
    public int getLargest()
    {
        int largest = matrix[0][0];
        for (int row = 0; row < matrix.length; row++)
        {
            for (int col = 0; col < matrix[0].length; col++)
            {
                if (matrix[row][col] > largest)
                {
                    largest = matrix[row][col];
                }
            }
        }
        return largest;
    }

    /**
     *  returns the total of all integers in a specified column.
     *  
     *  @param col the column to total
     *  @return colTotal the total of the column
     */
    public int getColTotal(int col)
    {
        int colTotal = 0;
        for (int row = 0; row < matrix.length; row++)
        {
            colTotal += matrix[row][col];
        }
        return colTotal;
    }

    // sorting algorithms, for 1d arrays ig
    /**
     * my idea for one: scan an array, find min and max values, break array into a given number of chunks (number == stair), 
     * then go through the array putting numbers into the chunk they fit in, then sort the chunks
     */
    public static void stairSort(int arr[], int stair) {
        //with O notation
        //i think it's O(3bn+4n+bc+3b+3)
        // b = number of stairs
        
        if (stair <= 2) stair = 2;

        int n = arr.length; //length of the array
        int min = arr[0]; //smallest number of the array
        int max = arr[0]; //largest number of the array

        //things for the stair
        int[] stairSizes = new int[stair]; //the highest values each stair bin will store
        
        //find the min and max values
        //O(2n)
        for (int e : arr) {
            if (e > max) max = e;
            if (e < min) min = e;
        }

        //scale the array up or down so that the min is zero
        //O(n + 1) + O(1)
        for (int i = 0; i < n; i++) arr[i] -= min;
        max -= min;

        //calculate the stair sizes
        //O(b+1)
        for (int i = 1; i <= stair-1; i++) {
            stairSizes[i] = ((i)*max) / stair;
        }

        //create a new sorted array
        int[] sorted = new int[n];
        int currSortIndex = 0;
        ArrayList<Integer>[] stairBins = new ArrayList[stair]; //array list array to store the stair
        //initialize the bins
        //O(b)
        for (int i = 0; i < stair; i++) stairBins[i] = new ArrayList<Integer>();
        
        //O((n+1)(b + 1))
        for (int i = 0; i < n; i++) //parse array, putting things into the bins as needed
        {
            //for loop, checks which bin the item at the current index in arr should go into
            int bestBin = 0;
            for (int x = 0; x < stair; x++)
            {
                if (arr[i] > stairSizes[x]) {
                    bestBin = x;
                }
            }
            stairBins[bestBin].add(arr[i]);
        }
        //  sort each bin 
        //count sort of the bins
        //O(b(c + 2n))
        for(int x=0; x < stairBins.length; x++) {
            //convert arraylist to array, count sort that
            int[] binArr = stairBins[x].stream().mapToInt(i -> i).toArray();
            
            //sort the bin, you can use any algorithm for this
            countSort(binArr);
            //heapSort(binArr);
            //quickSort(binArr);
            
            
            //apply sorted array
            for (int e : binArr) {
                arr[currSortIndex++] = e;
            }
            stairBins[x].clear();
        }
        
        //scale the array back to how it was (this should be the last thing done)
        //O(n+1)
        for (int i = 0; i < n; i++) arr[i] += min;
    }

    /**
     * heap sort
     */
    public static void heapSort(int arr[]) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }
    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private static void heapify(int arr[], int n, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    /**
     * count sort
     */
    public static void countSort(int[] arr) {
        //bin start and end values
        int binStartVal = Arrays.stream(arr).min().getAsInt();
        int binEndVal = Arrays.stream(arr).max().getAsInt();
        // range of values
        int range = binEndVal - binStartVal + 1;

        //count and temp arrays
        int[] countArr = new int[range];
        int[] tmpArr = new int[arr.length];

        //count occurances of each number in the arr
        for (int f = 0; f < arr.length; f++) { //step through the bin array
            countArr[arr[f] - binStartVal]++;
        }

        for (int f = 1; f < countArr.length; f ++) {
            countArr[f] += countArr[f-1];
        }

        //build tmpArr with countArr
        for (int f = arr.length - 1; f >= 0; f--) { // step through bin array
            //fancy math stuff to avoid uneeded for loops
            tmpArr[countArr[arr[f] - binStartVal] - 1] = arr[f];
            countArr[arr[f] - binStartVal]--;
        }

        for (int f=0;f<arr.length;f++) {
            arr[f] = tmpArr[f];
        }
    }
    
    /**
     * quick sort
     */
    public static void quickSort(int arr[]) 
    { 
        int low = 0;
        int high = arr.length-1;
        
        if (low < high) { 
            /* pi is partitioning index, arr[pi] is 
            now at right place */
            int pi = partition(arr, low, high); 
  
            // Recursively sort elements before 
            // partition and after partition 
            qSort(arr, low, pi - 1); 
            qSort(arr, pi + 1, high); 
        } 
    }
    /* The main function that implements QuickSort() 
    arr[] --> Array to be sorted, 
    low --> Starting index, 
    high --> Ending index */
    private static void qSort(int arr[], int low, int high) 
    { 
        if (low < high) { 
            /* pi is partitioning index, arr[pi] is 
            now at right place */
            int pi = partition(arr, low, high); 
  
            // Recursively sort elements before 
            // partition and after partition 
            qSort(arr, low, pi - 1); 
            qSort(arr, pi + 1, high); 
        } 
    }
    /* This function takes last element as pivot, 
    places the pivot element at its correct 
    position in sorted array, and places all 
    smaller (smaller than pivot) to left of 
    pivot and all greater elements to right 
    of pivot */
    private static int partition(int arr[], int low, int high) 
    { 
        int pivot = arr[high]; 
        int i = (low - 1); // index of smaller element 
        for (int j = low; j <= high - 1; j++) { 
            // If current element is smaller than or 
            // equal to pivot 
            if (arr[j] <= pivot) { 
                i++; 
  
                // swap arr[i] and arr[j] 
                int temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        int temp = arr[i + 1]; 
        arr[i + 1] = arr[high]; 
        arr[high] = temp; 
  
        return i + 1; 
    } 
  
}