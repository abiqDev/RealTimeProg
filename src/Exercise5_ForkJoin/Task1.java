package Exercise5_ForkJoin;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class MinMaxTask extends RecursiveTask<int[]> { //
    private int[] arr;
    private int start, end;
    // Choose your own THRESHOLD value [cite: 38]
    private static final int THRESHOLD = 4;

    public MinMaxTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected int[] compute() {
        int length = end - start;

        // Base case: If segment is small, compute directly [cite: 39]
        if (length <= THRESHOLD) {
            int min = arr[start];
            int max = arr[start];
            for (int i = start + 1; i < end; i++) {
                if (arr[i] < min) min = arr[i];
                if (arr[i] > max) max = arr[i];
            }
            return new int[]{min, max};
        }
        // Recursive case: Split task [cite: 40]
        else {
            int mid = start + (length / 2);
            MinMaxTask leftTask = new MinMaxTask(arr, start, mid);
            MinMaxTask rightTask = new MinMaxTask(arr, mid, end);

            leftTask.fork(); // [cite: 41]
            int[] rightResult = rightTask.compute(); // [cite: 41]
            int[] leftResult = leftTask.join(); // [cite: 41]

            // Combine results
            int combinedMin = Math.min(leftResult[0], rightResult[0]);
            int combinedMax = Math.max(leftResult[1], rightResult[1]);

            return new int[]{combinedMin, combinedMax}; //
        }
    }
}

public class Task1 {
    public static void main(String[] args) {
        int[] data = {12, 5, 88, 19, 20, 3, 40, 7, 18, 21, 50, 60}; // [cite: 36]
        ForkJoinPool pool = new ForkJoinPool();

        int[] finalResult = pool.invoke(new MinMaxTask(data, 0, data.length));

        System.out.println("Final Minimum: " + finalResult[0]); //
        System.out.println("Final Maximum: " + finalResult[1]); //
    }
}
