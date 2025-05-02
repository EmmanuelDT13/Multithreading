package lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {

    public static void main(String[] args) throws InterruptedException {

        final Processor processor = new Processor();

        final Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        final Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

    }

}

class Processor {

    private Integer currentValue = 0;
    private final List<Integer> listNumbers = new ArrayList<>();
    private final Integer MIN_LENGTH_SIZE = 0;
    private final Integer MAX_LENGTH_SIZE = 5;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void produce() throws InterruptedException {

        lock.lock();
        while (true) {
            if (currentValue > MAX_LENGTH_SIZE) {
                condition.await();
            } else {
                System.out.println("Adding number: " + currentValue);
                listNumbers.add(currentValue);
                currentValue++;
                condition.signal();
            }
            Thread.sleep(1000);
        }
    }

    public void consume() throws InterruptedException {

        lock.lock();
        while (true) {
            if (currentValue.equals(MIN_LENGTH_SIZE)) {
                condition.await();
            } else {
                System.out.println("Removing number: " + listNumbers.removeLast());
                currentValue--;
                condition.signal();
            }
            Thread.sleep(1000);
        }
    }

}