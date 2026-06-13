package Week06;

class Account {
    private int balance; private final String name;
    public Account(String name, int balance) { this.name = name;
        this.balance = balance;
    }

    public void withdraw(int amount) { balance -= amount;
    }
    public void deposit(int amount) { balance += amount;
    }

    public String getName() { return name;
    }
}
public class Deadlock {
    public static void transfer(Account from, Account to, int amount) {
        synchronized (from) { System.out.println(Thread.currentThread().getName()
                + " locked " + from.getName());

            try { Thread.sleep(100); } catch (Exception e) {} synchronized (to) {
                System.out.println(Thread.currentThread().getName()
                        + " locked " + to.getName());
                from.withdraw(amount); to.deposit(amount);
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
