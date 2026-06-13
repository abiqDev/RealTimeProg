package Exe3;

// --- Main Class ---
public class Main {
    public static void main(String[] args) {
        // Initialize bank account with $1000.0
        BankAccountWithLock account = new BankAccountWithLock(1000.0);

        // Create thread tasks using Lambda expressions
        Runnable readerTask = () -> {
            for (int i = 0; i < 2; i++) {
                account.getBalance();
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        };

        Runnable writerTask = () -> {
            account.deposit(500.0);
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            account.withdraw(300.0);
        };

        // Create multiple reader and writer threads to demonstrate concurrency
        Thread reader1 = new Thread(readerTask, "Reader-1");
        Thread reader2 = new Thread(readerTask, "Reader-2");
        Thread writer1 = new Thread(writerTask, "Writer-1");

        // Start the threads
        reader1.start();
        reader2.start();
        writer1.start();
    }
}
