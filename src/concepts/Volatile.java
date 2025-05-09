package concepts;

public class Volatile {

    public static void main(String[] args) throws InterruptedException {

        final Worker worker = new Worker();

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    worker.printingMessage();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();

        Thread.sleep(3000);
        worker.setRunning(false);
        System.out.println("Should worker be running? " + (worker.isRunning() ? "Yes": "No"));

    }
}

class Worker {

    private volatile boolean running = true;

    public void printingMessage() throws InterruptedException {

        while(running){
            Thread.sleep(500);
            System.out.println("I am still running");
        }
    }

    public boolean isRunning(){
        return running;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

}