package Exe4;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HospitalRegistrationWithReadWriteLock {
    private static int totalPatients = 0;
    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {
        Runnable registerPatient = () -> {
            for (int i = 0; i < 100000; i++) {
                rwLock.writeLock().lock(); // Acquire exclusive write lock
                try {
                    totalPatients++;
                } finally {
                    rwLock.writeLock().unlock(); // Release write lock
                }
            }
        };

        Thread counter1 = new Thread(registerPatient, "Counter-1");
        Thread counter2 = new Thread(registerPatient, "Counter-2");

        counter1.start();
        counter2.start();

        counter1.join();
        counter2.join();

        System.out.println("Total Registered Patients: " + totalPatients);
    }
}
