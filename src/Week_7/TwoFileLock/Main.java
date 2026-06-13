package Week_7.TwoFileLock;

public class Main {

    public static void main(String[] args) {

        // Create student threads — no need to pass lock, it is static inside LibraryCounter
        LibraryCounter t1 = new LibraryCounter("Alice", new String[]{"Web Engineering", "OpenCV"});
        LibraryCounter t2 = new LibraryCounter("Bob",   new String[]{"YOLO"});

        System.out.println("===== University Library Borrowing System =====\n");

        // Start all threads
        t1.start();
        t2.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("===== All students have finished borrowing =====");
    }
}