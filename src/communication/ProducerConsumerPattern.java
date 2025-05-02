package communication;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerPattern {

    public static void main(String[] args) throws InterruptedException {

        final Processor processor = new Processor();
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consumer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread1.start();
        thread2.start();


    }


}

class Processor {

    private final Integer MIN_LIST_LENGTH = 0;
    private final Integer MAX_LIST_LENGTH = 5;

    private final List<Integer> listOfNumbers = new ArrayList<>();
    private final Object lock = new Object();

    private Integer currentValue = 0;

    public void produce() throws InterruptedException {

        synchronized (lock) {
            while (true) {
                if (MAX_LIST_LENGTH < listOfNumbers.size()) {
                    lock.wait();
                } else {
                    System.out.println("Adding current value to list: " + currentValue);
                    listOfNumbers.add(currentValue);
                    currentValue++;
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (MIN_LIST_LENGTH == listOfNumbers.size()) {
                    lock.wait();
                } else {
                    System.out.println("Removing current value to list: " + listOfNumbers.removeLast());
                    currentValue--;
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }
}
