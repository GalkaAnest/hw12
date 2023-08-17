package hw12.ex1;
//Напишіть програму, яка кожну секунду відображає на екрані дані про час, що минув від моменту запуску програми.
//
//Другий потік цієї ж програми кожні 5 секунд виводить повідомлення Минуло 5 секунд.

public class TimeDisplay {

    public static void main(String[] args) {

        // Thread to display time every second
        Thread timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int secondsPassed = 0;
                while (true) {
                    try {
                        Thread.sleep(1000); // wait for 1 second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    secondsPassed++;
                    System.out.println(secondsPassed + " секунд(а/и) минуло від моменту запуску програми.");
                }
            }
        });

        // Thread to display message every 5 seconds
        Thread fiveSecondNotifierThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000); // wait for 5 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Минуло 5 секунд.");
                }
            }
        });

        // Start the threads
        timerThread.start();
        fiveSecondNotifierThread.start();
    }
}

