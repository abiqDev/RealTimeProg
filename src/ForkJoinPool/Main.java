package ForkJoinPool;

import java.util.concurrent.ForkJoinPool;

    public class Main {
        public static void main(String[] args) {
            int[] numbers = {10, 25, 80, 15, 55, 99, 45, 77, 1, 4};

            //create threads pool
            ForkJoinPool pool = new ForkJoinPool();

            //create task for the whole array
            MaxTask task = new MaxTask(numbers, 0, numbers.length);

            //ask to execute task for the whole array pool.invoke()
            int result = pool.invoke(task);

            //display the result
            System.out.println("Maximum Number: " +  result);
        }
    }


