import java.util.concurrent.locks.*;

class Showroom {
    private String sign = "AVAILABLE";
    private final Lock lock = new ReentrantLock();
    private final Condition available = lock.newCondition();

    public void enter() {
        lock.lock();
        try {
            while (!sign.equals("AVAILABLE")) {
                available.await();
            }
            sign = "BUSY";
            System.out.println(Thread.currentThread().getName() + " enters the showroom.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void exit() {
        lock.lock();
        try {
            sign = "AVAILABLE";
            System.out.println(Thread.currentThread().getName() + " exits the showroom.");
            available.signal(); // Notify the next guest in line
        } finally {
            lock.unlock();
        }
    }
}

class Guest extends Thread {
    private final Showroom showroom;

    public Guest(String name, Showroom showroom) {
        super(name);
        this.showroom = showroom;
    }

    @Override
    public void run() {
        showroom.enter();
        // Simulate viewing the vase
        try {
            Thread.sleep(1000); // Viewing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showroom.exit();
    }
}

public class Problem2 {
    public static void main(String[] args) {
        Showroom showroom = new Showroom();
        int numGuests = 5; // You can change the number of guests here

        for (int i = 1; i <= numGuests; i++) {
            new Guest("Guest " + i, showroom).start();
        }
    }
}
