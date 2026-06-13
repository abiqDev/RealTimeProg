package LabTask2;

public class MyThread extends Thread{
    @Override
    public void run() {
        try {
            // Loop from 0 to 5
            for (int x = 0; x <= 5; x++) {
                System.out.println("Thread " + this.getId() + " is printing: " + x);
                Thread.sleep(1000); // Pause 1 second
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
}
