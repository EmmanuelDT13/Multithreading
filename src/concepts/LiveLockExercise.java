package concepts;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LiveLockExercise {

    public static void main(String[] args) {

        final LiveLock liveLock = new LiveLock();

        new Thread(liveLock::process1).start();
        new Thread(liveLock::process2).start();

    }
}

class LiveLock {

    final Lock lock1 = new ReentrantLock();
    final Lock lock2 = new ReentrantLock();

    public void process1() {

        while (true) {
            try {
                lock1.tryLock(50, TimeUnit.MILLISECONDS);
                System.out.println("I am the process1 and I have locked the lock 1");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Trying to get the lock 2");

            if (lock2.tryLock()) {
                System.out.println("I am the process1 and I have locked the lock 2");
                lock2.unlock();
            } else {
                System.out.println("I couldn't get the lock 2");
                continue;
            }
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }

    public void process2() {

        while (true) {
            try {
                lock2.tryLock(50, TimeUnit.MILLISECONDS);
                System.out.println("I am the process2 and I have locked the lock 2");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Trying to get the lock 1");

            if (lock1.tryLock()) {
                System.out.println("I am the process2 and I have locked the lock 1");
                lock1.unlock();
            } else {
                System.out.println("I couldn't get the lock 1");
                continue;
            }
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }
}