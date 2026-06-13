package Exercise5_ForkJoin;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

class GradeCountTask extends RecursiveTask<int[]> { //
    private int[] scores; // [cite: 56]
    private int start, end; // [cite: 56]
    private static final int THRESHOLD = 5; // [cite: 57]

    public GradeCountTask(int[] scores, int start, int end) {
        this.scores = scores;
        this.start = start;
        this.end = end;
    }

    @Override
    protected int[] compute() {
        int length = end - start;

        // Base case: Compute grade counts directly [cite: 58]
        if (length <= THRESHOLD) {
            // Indices: 0=A, 1=B, 2=C, 3=D, 4=F
            int[] counts = new int[5];

            for (int i = start; i < end; i++) {
                int score = scores[i];
                if (score >= 85) counts[0]++;      // A (85 - 100) [cite: 48]
                else if (score >= 70) counts[1]++; // B (70 - 84) [cite: 49]
                else if (score >= 55) counts[2]++; // C (55 - 69) [cite: 50]
                else if (score >= 40) counts[3]++; // D (40 - 54) [cite: 51]
                else counts[4]++;                  // F (0 - 39) [cite: 52]
            }
            return counts;
        }
        // Recursive case: Split into two subtasks [cite: 59, 60]
        else {
            int mid = start + (length / 2);
            GradeCountTask leftTask = new GradeCountTask(scores, start, mid);
            GradeCountTask rightTask = new GradeCountTask(scores, mid, end);

            leftTask.fork(); // [cite: 61]
            int[] rightResult = rightTask.compute(); // [cite: 62]
            int[] leftResult = leftTask.join(); // [cite: 63]

            // Combine results from left and right
            int[] combinedCounts = new int[5];
            for (int i = 0; i < 5; i++) {
                combinedCounts[i] = leftResult[i] + rightResult[i]; //
            }
            return combinedCounts;
        }
    }
}

public class Task2 {
    public static void main(String[] args) {
        // Test program using the sample array [cite: 65, 66]
        int[] scores = {75, 88, 92, 55, 63, 79, 100, 82, 45, 38, 67, 73, 89, 95, 50};

        ForkJoinPool pool = new ForkJoinPool();
        int[] results = pool.invoke(new GradeCountTask(scores, 0, scores.length));

        // Print sample output [cite: 67]
        System.out.println("Grade Distribution:");
        System.out.println("A (85-100): " + results[0]);
        System.out.println("B (70-84) : " + results[1]);
        System.out.println("C (55-69) : " + results[2]);
        System.out.println("D (40-54) : " + results[3]);
        System.out.println("F (0-39)  : " + results[4]);
    }
}