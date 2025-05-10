package ThreadExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedThreadPoolExample {

    public static void main(String[] args) {

        // As you can see, we are going to have teen threads within our fixed thread pool.
        // They both are going to be re utilized to perform the tasks.
        final ExecutorService executorServiceInterrupted = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            executorServiceInterrupted.execute(new TaskFixedThreadPool(i));
        }
        executorServiceInterrupted.shutdown();

        try {
            if(!executorServiceInterrupted.awaitTermination(1000, TimeUnit.MILLISECONDS)){
                System.out.println("Interrupted Tasks:");
                System.out.println(executorServiceInterrupted.shutdownNow());
            }
        } catch (InterruptedException e) {
            System.out.println(executorServiceInterrupted.shutdownNow());
        }

    }
}

class TaskFixedThreadPool implements Runnable {

    private final Integer id;

    public TaskFixedThreadPool(Integer id) {
        this.id = id;
    }

    @Override
    public void run() {
        // The log will print the same two thread name for all the tasks.
        System.out.println("Hello, I am the task: " + id + " and my thread name is: " + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}