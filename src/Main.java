public class Main {
    public static void main(String[] args) throws InterruptedException {

        //Way 1 to create a thread

        Thread my_thread = new Thread(() -> {

            System.out.println("Hello, World! From the " + Thread.currentThread().getName() + " thread");
            System.out.println("The priority is: " + Thread.currentThread().getPriority());
            throw new RuntimeException("One error has happened");
        });
        my_thread.setName("My_created_thread");
        my_thread.setPriority(Thread.MAX_PRIORITY);
        my_thread.setUncaughtExceptionHandler((Thread, Throwable) -> {
            System.out.println("One error happened in the " + Thread.getName() + ": " + Throwable.getLocalizedMessage());
        });

        System.out.println("Hello, World! From the " + Thread.currentThread().getName() + " thread");
        System.out.println("The priority is: " + Thread.currentThread().getPriority());
        my_thread.start();

        System.out.println();
        My_SecondThread my_secondThread = new My_SecondThread();
        my_secondThread.start();



        my_secondThread.join();
        System.out.println("SALUDITES");

    }

    private static class My_SecondThread extends Thread {
        @Override
        public void run() {

            System.out.println("Hello, World! But this time from the custom " + this.getName() + " thread");
            System.out.println("The priority is: " + this.getPriority());
        }
    }

}