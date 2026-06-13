package Week_8;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class SumTask extends RecursiveTask<Long>{
    private int[] numbers;
    private int start, end;
    private static final int THRESHOLD = 1000;

    //Constructor
    public SumTask(int[] numbers, int start, int end){
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;

        // 1. Check if the task is small enough to compute directly
        if (length <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        }
        // 2. If the task is too large, split it
        else {
            // Find the middle point to split the array
            int mid = start + (length / 2);

            // Create two subtasks for the left and right halves
            SumTask leftTask = new SumTask(numbers, start, mid);
            SumTask rightTask = new SumTask(numbers, mid, end);

            // Fork one subtask (pushes it to the pool to run in parallel)
            leftTask.fork();

            // Compute the other subtask directly in the current thread
            long rightResult = rightTask.compute();

            // Join the forked task (wait for it to finish and get its result)
            long leftResult = leftTask.join();

            // Combine and return the results
            return leftResult + rightResult;
        }
    }
}

public class MyForkJoin {
    public static void main(String[] args){
        int[] nums = new int[10_000];
        for (int i = 0; i < nums.length; i++){
            nums[i] = i;
        }

        ForkJoinPool pool = new ForkJoinPool();
        long result = pool.invoke(new SumTask(nums,0,nums.length));

        System.out.println("Total sum:" +result);
    }
}
