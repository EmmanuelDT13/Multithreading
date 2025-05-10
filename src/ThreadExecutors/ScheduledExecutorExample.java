package ThreadExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorExample {

    public static void main(String[] args) {

        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        // Every three seconds, the executor will run the task.
        // We can define the Runnable, Delay, Period and Time Unit
        executorService.scheduleAtFixedRate (new TaskScheduledExecutor(1), 0,3, TimeUnit.SECONDS);

    }

}

class TaskScheduledExecutor implements Runnable{

    private final Integer id;

    public TaskScheduledExecutor(Integer id){
        this.id = id;
    }

    @Override
    public void run() {
        // The log will print the same two thread name for all the tasks.
        System.out.println("Hello, I am the task: " + id + " and my thread name is: " + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}