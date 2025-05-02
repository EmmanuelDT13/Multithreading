package lock;

import java.util.ArrayList;
import java.util.List;

public class CommunicationBetweenThreads {


    public static void main(String[] args) {

        final UserFacade facade = new UserFacade();

        final Thread processUsers = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    System.out.println("Process users thread");
                    try {
                        facade.processUsers();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        final Thread getUsers = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    System.out.println("Get users thread");
                    try {
                        facade.findUsers();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        processUsers.setPriority(Thread.MAX_PRIORITY);
        processUsers.start();
        getUsers.start();
    }

}

class UserFacade {

    private List<User> users = new ArrayList<>();

    static class User {

        private final String name;

        private User(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

    public void findUsers() throws InterruptedException {
        synchronized (this){
            System.out.println("Finding users from DB...");
            Thread.sleep(3000);
            this.users = List.of(new User("Emmanuel"), new User("Orlando"));
            notify();
        }
    }

    public void processUsers() throws InterruptedException {
        synchronized (this){
            System.out.println("Processing users method:");
            wait();
            System.out.println("Processing users method again:");
            this.users.forEach(user -> System.out.println(user.getName()));
        }
    }
}