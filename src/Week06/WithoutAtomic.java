package Week06;
//Use the data 100,000 increments

public class WithoutAtomic {

    private static int count = 0;

    public static void main(String[] args) throws  InterruptedException {

        Runnable Task = () -> {
            for (int i = 0; i < 100000; i++) {
                count++;
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

        System.out.println("Final count with no Atomic:" + count);
    }

}
