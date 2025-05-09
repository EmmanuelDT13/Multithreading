package concepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    public static void main(String[] args) {

        final ExecutorService executorService = Executors.newCachedThreadPool();
        final SemaphoreWorker semaphoreWorker = new SemaphoreWorker();

        for (int i = 0; i < 12; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    semaphoreWorker.downloadData();
                }
            });
        }
    }
}

class SemaphoreWorker {

    // Semaphores are not mutually exclude, but you can define how many threads are going to be able to access the resource at the same time.
    final Semaphore semaphore = new Semaphore(3, true);

    public void downloadData() {
        try {
            semaphore.acquire();
            System.out.println("Downloading data...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

}
