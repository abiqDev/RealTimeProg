package Week_7;

import java.util.concurrent.locks.ReentrantLock;

public class LibraryCounterWithLock {

    // One lock represents the single borrowing counter
    private final ReentrantLock lock = new ReentrantLock();

    public void borrowBook(String studentName, String bookTitle) {
        // Lock is acquired again by the SAME thread (reentrancy!)
        lock.lock();
        try {
            System.out.println("  " + studentName + " borrowing: " + bookTitle
                    + "  [Lock count: " + lock.getHoldCount() + "]");
            Thread.sleep(500);
            System.out.println("  " + studentName + " done with: " + bookTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // Always release in finally
        }
    }

    public void borrowBooks(String studentName, String[] books) {
        lock.lock(); // Student acquires counter (first lock)
        try {
            System.out.println("\n>> " + studentName + " acquired the counter.");
            for (String book : books) {
                borrowBook(studentName, book); // Re-acquires lock (reentrancy)
            }
            System.out.println(">> " + studentName + " released the counter.\n");
        } finally {
            lock.unlock(); // Student leaves counter
        }
    }

    public static void main(String[] args) {
        LibraryCounterWithLock counter = new LibraryCounterWithLock();

        // Create multiple student threads
        Thread t1 = new Thread(() ->
                counter.borrowBooks("Alice", new String[]{"Ref Book A", "Ref Book B"}));

        Thread t2 = new Thread(() ->
                counter.borrowBooks("Bob", new String[]{"Ref Book C"}));

        Thread t3 = new Thread(() ->
                counter.borrowBooks("Carol", new String[]{"Ref Book D", "Ref Book E"}));

        t1.start();
        t2.start();
        t3.start();
    }
}
