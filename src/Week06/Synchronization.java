package Week06;

public class Synchronization {

    private static int count = 0;

    public static synchronized void increment(){

        count++;
    }

    public static void main(String[] args) throws InterruptedException{

        //Task Increment the counter
        Runnable Task = () -> {
            for (int i = 0; i < 100000; i++) {
                increment(); //synchronization -> thread safe
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

        System.out.println("Final count with Atomic:" + count);
    }
}

