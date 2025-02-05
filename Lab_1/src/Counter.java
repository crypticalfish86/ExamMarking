public class Counter implements  Runnable{
    public static int tally = 0;

    public void run() {
        for (int i = 0; i < 10000000 ; i++) {
            tally++;
        }
    }
}
