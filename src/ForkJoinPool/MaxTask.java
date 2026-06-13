package ForkJoinPool;

import java.util.concurrent.RecursiveTask; //Thread duk dalam recursive task
import java.util.concurrent.ForkJoinPool;

class MaxTask extends RecursiveTask<Integer> {

    //create array start and end
    int[]arr;
    int start, end;

    //create constructor - to receive array and index position
    public MaxTask(int[]arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }
    //Main Task = main method for fork/join
    @Override
    protected Integer compute() { //bersilang

        //if array is small (for small task condition - small value in array)
        if (end - start <= 2) {

            //assume first value is maximum value
            int max = arr[start];

            for (int i = start + 1; i < end; i++) {

                //compare value in the treasure
                if (arr[i] > max) {

                    //update the maximum value
                    max = arr[i];
                }
            }
            return max; //return the value of RecursiveTask()
        }
        //split the array into 2 parts - to find middle position
        int mid = (start + end)/2;

        //splitting into left and right tasks
        MaxTask left = new MaxTask (arr, start, mid);
        MaxTask right = new MaxTask (arr, mid, end);

        //fork process - run left task using thread
        left.fork();

        //fork process - run right task using thread
        int rightMax = right.compute();

        //join result - thread will wait for left's task result
        int leftMax = left.join();

        //Return Biggest Value - it will compare both result and return the value
        return Math.max(leftMax, rightMax);
    }}
