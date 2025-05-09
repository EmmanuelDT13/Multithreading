package concepts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


// As the thread 1 is waiting for the lock2  which is locked by the thread 2 and
// the thread 2 is waiting ofr the lock1 which is locked by the thread 1. They are blocking each other.

//How to avoid it? I should've locked the both locks in the same order. So that, the threads wouldn't be blocking each other.
public class DeadLockExercise {

    public static void main(String[] args) {

        final DeadLock deadlock = new DeadLock();

        new Thread(deadlock::process1).start();
        new Thread(deadlock::process2).start();

    }

}

class DeadLock{

    Lock lock1 = new ReentrantLock(true);
    Lock lock2 = new ReentrantLock(true);

    public void process1(){

        lock1.lock();
        System.out.println("I'm the process 1 and I have locked the lock 1");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock2.lock();
        System.out.println("I'm the process 1 and I have locked the lock 2");

        lock1.unlock();
        lock2.unlock();

    }

    public void process2(){

        lock2.lock();
        System.out.println("I'm the process 2 and I have locked the lock 2");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock1.lock();
        System.out.println("I'm the process 2 and I have locked the lock 1");

        lock1.unlock();
        lock2.unlock();

    }


}
