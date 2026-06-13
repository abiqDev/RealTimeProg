package Week11;

import java.util.ArrayList;
import java.util.List;


public class CountEvenNumbersNormal {

    static List<Integer> evenNumbers =
            new ArrayList<>();

    public static void main(String[] args)
            throws InterruptedException {

        Runnable task = () -> {

            for (int i = 1; i <= 100; i++) {

                if (i % 2 == 0) {

                    evenNumbers.add(i);

                }
            }
        };

        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(
                "Total numbers stored: "
                        + evenNumbers.size());

        System.out.println(evenNumbers);
    }
}
