public class Main {
    public static void main(String[] args) {
        //Task 1


        Task_1 firstTask = new Task_1("First thread");
        Task_1 secondTask = new Task_1("Second thread");

        //initialize both threads
        Thread firstThread = new Thread(firstTask);
        Thread secondThread = new Thread(secondTask);

        //start both threads
        firstThread.start();
        secondThread.start();

        //Join first task threads before initialising second task
        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e){
            System.out.println("Error" + e);
        }
        /*There is an ability to send a thread to sleep using the Thread.Sleep(time in milliseconds) method*/

        //Task 2

        Counter firstCounter = new Counter();
        Counter secondCounter = new Counter();

        //Start both threads
        Thread firstCounterThread = new Thread(firstCounter);
        Thread secondCounterThread = new Thread(secondCounter);

        firstCounterThread.start();
        secondCounterThread.start();

        //Join both threads before printing out final tally
        try {
            firstCounterThread.join();
            secondCounterThread.join();
        } catch (InterruptedException e) {
            System.out.println("Error" + e);
        }
        System.out.println("\n\nFinal counter tally: " + Counter.tally);

        /*
         The reason why the tally is always between a value of 10000000-20000000 and not
         exactly 20000000 is because each thread is trying to increment the same variable
         at the same time meaning sometimes each thread writes the exact same number to
         the tally instead of incrementing one after the other.

         concept is called "interference"
        */
    }
}