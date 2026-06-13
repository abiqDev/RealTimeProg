class Week4ThreadNew extends Thread {

    private int threadNumber;

    public Week4ThreadNew(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public void run() {
        for (int i = 0; i <= 5; i++) {
            System.out.println("Thread " + threadNumber + " is printing number: " + i + " from the list");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread " + threadNumber + " was interrupted.");
            }
        }
    }

    public static void main(String[] args) {

        // Create 6 threads numbered 0 to 5
        Week4ThreadNew[] threads = new Week4ThreadNew[6];

        for (int i = 0; i < 6; i++) {
            threads[i] = new Week4ThreadNew(i);
        }

        // Start all threads
        for (int i = 0; i < 6; i++) {
            threads[i].start();
        }
    }
}