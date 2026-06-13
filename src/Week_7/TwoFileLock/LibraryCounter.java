package Week_7.TwoFileLock;

import java.util.concurrent.locks.ReentrantLock;

public class LibraryCounter extends Thread {

    private final String studentName;
    private final String[] books;
    private static final ReentrantLock lock = new ReentrantLock(); // static = shared among ALL threads

    // Constructor
    public LibraryCounter(String studentName, String[] books) {
        this.studentName = studentName;
        this.books = books;
    }

    // Borrow individual book
    public void borrowBook(String bookTitle) {
        lock.lock(); // reentrant — same thread locks again
        try {
            System.out.println(studentName + " is borrowing: " + bookTitle);
            Thread.sleep(500); // simulate time taken
            System.out.println(studentName + " has borrowed: " + bookTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // always release
        }
    }

    // Borrow all books in one session
    public void borrowBooks() {
        lock.lock(); // student acquires counter
        try {
            System.out.println("\n" + studentName + " approached the counter.");
            for (String book : books) {
                borrowBook(book); // reentrant lock inside
            }
            System.out.println(studentName + " finished and left the counter.\n");
        } finally {
            lock.unlock(); // student leaves counter
        }
    }

    // run() called when thread starts
    @Override
    public void run() {
        borrowBooks();
    }
}