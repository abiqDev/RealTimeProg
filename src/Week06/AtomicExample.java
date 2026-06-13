package Week06;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws  InterruptedException {

        //Task Increment the counter
        Runnable Task = () -> {
            for (int i = 0; i < 100000; i++) {
                count.incrementAndGet();
            }
        };

        Thread t1 = new Thread(Task);
        Thread t2 = new Thread(Task);
        Thread t3 = new Thread(Task);
        Thread t4 = new Thread(Task);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Final count with Atomic:" + count.get());
    }
}
