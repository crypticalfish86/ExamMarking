import java.util.Random;

public class Task_1 implements Runnable {
    private String message;
    public Task_1(String message) {
        this.message = message;
    }
    public void run() {
        System.out.println(this.message + ": I am going to sleep!");

        //pick a random sleep time between 0-5000 ms
        Random random = new Random();
        int sleepTime = random.nextInt(5000);

        try {
            Thread.sleep(sleepTime);
            System.out.println(this.message + ": I'm awake!");
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
