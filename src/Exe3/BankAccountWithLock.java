package Exe3;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

class BankAccountWithLock {
    private double balance;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock;
    private final Lock writeLock;

    public BankAccountWithLock(double initialBalance) {
        this.balance = initialBalance;
        // Corrected initialization of locks inside the constructor
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }

    // Read balance (shared lock)
    public double getBalance() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reads balance: " + balance);
            return balance;
        } finally {
            readLock.unlock(); // Fixed placement inside the finally block
        }
    }

    // Deposit money (exclusive lock)
    public void deposit(double amount) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " deposits: " + amount);
            balance += amount; // Fixed syntax error (balance + amount)
        } finally {
            writeLock.unlock(); // Fixed placement inside the finally block
        }
    }

    // Withdraw money (exclusive lock)
    public void withdraw(double amount) {
        writeLock.lock();
        try {
            if (balance >= amount) {
                System.out.println(Thread.currentThread().getName() + " withdraws: " + amount);
                balance -= amount; // Fixed syntax error (balance amount)
            } else {
                System.out.println(Thread.currentThread().getName() + " insufficient funds for: " + amount);
            }
        } finally {
            writeLock.unlock();
        }
    }
}
