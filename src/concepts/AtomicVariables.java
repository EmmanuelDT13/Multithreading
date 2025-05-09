package concepts;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariables {

    public static void main(String[] args) throws InterruptedException {

    final AtomicExample atomicExample = new AtomicExample();

    Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            atomicExample.addInteger();
        }
    });

    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            atomicExample.addInteger();
        }
    });

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

    System.out.println(atomicExample.getInteger());

    }

}

class AtomicExample{

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public void addInteger(){
        for (int i = 0; i < 1000; i++){
            atomicInteger.getAndIncrement();
        }
    }

    public Integer getInteger(){
        return this.atomicInteger.get();
    }


}
