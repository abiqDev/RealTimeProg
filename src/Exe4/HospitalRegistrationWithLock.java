package Exe4;

import java.util.concurrent.locks.ReentrantLock;

public class HospitalRegistrationWithLock {
    private static int totalPatients = 0;
    // Create an instance of ReentrantLock
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Runnable registerPatient = () -> {
            for (int i = 0; i < 100000; i++) {
                lock.lock(); // Acquire exclusive lock
                try {
                    totalPatients++;
                } finally {
                    lock.unlock(); // Always release lock in finally block
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
