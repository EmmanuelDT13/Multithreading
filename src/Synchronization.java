public class Synchronization {

    private static Integer counter1 = 0;
    private static Integer counter2 = 0;


    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    private static void addCounter1() {
        synchronized (lock1) {
            counter1++;
        }
    }

    private static void addCounter2() {
        synchronized (lock2) {
            counter2++;
        }
    }

    private static void process() throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    addCounter1();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    addCounter2();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter1);
        System.out.println(counter2);

    }

    public static void main(String[] args) throws InterruptedException {
        process();
    }
}
