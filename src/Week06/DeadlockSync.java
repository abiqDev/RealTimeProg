package Week06;

class AccountSync {
    private int balance; private final String name;
    public AccountSync(String name, int balance) { this.name = name;
        this.balance = balance;
    }

    public void withdraw(int amount) { balance -= amount;
    }
    public void deposit(int amount) { balance += amount;
    }

    public String getName() { return name;
    }
}
public class DeadlockSync {
    public static void transfer(Account from, Account to, int amount) {
        // Determine a consistent order to prevent circular wait
        Account first = from.getName().compareTo(to.getName()) < 0 ? from : to;
        Account second = first == from ? to : from;

        synchronized (first) { // Always lock in same order
            synchronized (second) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    public static void main(String[] args) {
        Account A = new Account("A", 1000); Account B = new Account("B", 1000);
        Thread t1 = new Thread(() -> transfer(A, B, 100)); Thread t2 = new Thread(() -> transfer(B, A, 200));

        t1.start();
        t2.start();
    }
}
