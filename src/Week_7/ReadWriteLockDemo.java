package Week_7;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    private static int counter = 0;

    // Create ReadWriteLock
    private static final ReentrantReadWriteLock lock =
            new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {

        Runnable task = () -> {
            for (int i = 0; i < 100000; i++) {

                // Acquire write lock before modifying counter
                lock.writeLock().lock();

                try {
                    counter++;
                } finally {
                    // Release write lock
                    lock.writeLock().unlock();
                }
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // Read operation using read lock
        lock.readLock().lock();

        try {
            System.out.println("Final Counter (ReadWriteLock): " + counter);
        } finally {
            lock.readLock().unlock();
        }
    }
}