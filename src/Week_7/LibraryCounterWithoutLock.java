package Week_7;

public class LibraryCounterWithoutLock {

    public void borrowBook(String studentName, String bookTitle) {
        System.out.println(studentName + " is borrowing: " + bookTitle);
        try {
            Thread.sleep(500); // simulate time taken
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(studentName + " has borrowed: " + bookTitle);
    }

    public void borrowBooks(String studentName, String[] books) {
        System.out.println("\n" + studentName + " approached the counter.");
        for (String book : books) {
            borrowBook(studentName, book);
        }
        System.out.println(studentName + " finished and left the counter.\n");
    }

    public static void main(String[] args) {
        LibraryCounterWithoutLock counter = new LibraryCounterWithoutLock();
        counter.borrowBooks("Alice", new String[]{"Web Engineering", "OpenCV"});
        counter.borrowBooks("Bob",   new String[]{"YOLO"});
    }
}
