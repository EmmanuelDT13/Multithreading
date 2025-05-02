public class DeamonThread {

    public static void main(String[] args) throws InterruptedException {


    Thread normalThread = new Thread(new UserThread());
    Thread daemonThread = new Thread(new DaemonThread());
    daemonThread.setDaemon(true);


    normalThread.start();
    normalThread.join();
    daemonThread.start();


    }

}

class UserThread implements Runnable{
    @Override
    public void run() {
        for(int i = 0; i < 5; i++){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("User thread " + i);
        }
        System.out.println("Normal thread has finished");
    }
}

class DaemonThread implements Runnable{
    @Override
    public void run() {
        for(int i = 0; i < 50; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Daemon Thread: " + i);
        }
    }
}

class My_hilo extends Thread {


}
