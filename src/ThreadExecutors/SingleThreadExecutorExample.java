package ThreadExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SingleThreadExecutorExample {
    public static void main(String[] args) {

        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Every single Task is going to be executed by the same thread. The SingleThreadExecutor works by a
        // sequential way because the ir only one thread.
        for(int i = 0; i < 5; i++){
            executorService.execute(new TaskSingleThread(i));
        }
        executorService.close();
    }
}

class TaskSingleThread implements Runnable{

    private final Integer id;

    public TaskSingleThread(Integer id){
        this.id = id;
    }

    @Override
    public void run() {
        // The log will print the same thread name for all the tasks.
        System.out.println("Hello, I am the task: " + id + " and my thread name is: " + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
