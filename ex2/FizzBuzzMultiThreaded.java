package hw12.ex2;

//Напишіть програму, що виводить в консоль рядок, що складається з чисел від 1 до n, але з заміною деяких значень:
//
//якщо число ділиться на 3 - вивести fizz
//якщо число ділиться на 5 - вивести buzz
//якщо число ділиться на 3 і на 5 одночасно - вивести fizzbuzz
//Наприклад, для n = 15, очікується такий результат: 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz.
//
//Програма повинна бути багатопотоковою, і працювати з 4 потоками:
//
//Потік A викликає fizz(), щоб перевірити, чи ділиться число на 3, і якщо так - додати в чергу на виведення для потоку D рядок fizz.
//Потік B викликає buzz(), щоб перевірити, чи ділиться число на 5, і якщо так - додати в чергу на виведення для потоку D рядок buzz.
//Потік C викликає fizzbuzz(), щоб перевірити, чи ділиться число на 3 та 5 одночасно, і якщо так - додати в чергу на виведення для потоку D рядок fizzbuzz.
//Потік D викликає метод number(), щоб вивести наступне число з черги, якщо є таке число для виведення.

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FizzBuzzMultiThreaded {

    private int n;
    private AtomicInteger counter = new AtomicInteger(1);
    private BlockingQueue<String> results = new LinkedBlockingQueue<>();

    public FizzBuzzMultiThreaded(int n) {
        this.n = n;
    }

    public void fizz() throws InterruptedException {
        while (counter.get() <= n) {
            int current = counter.get();
            if (current % 3 == 0 && current % 5 != 0) {
                results.put("fizz");
                counter.incrementAndGet();
            }
        }
    }

    public void buzz() throws InterruptedException {
        while (counter.get() <= n) {
            int current = counter.get();
            if (current % 5 == 0 && current % 3 != 0) {
                results.put("buzz");
                counter.incrementAndGet();
            }
        }
    }

    public void fizzbuzz() throws InterruptedException {
        while (counter.get() <= n) {
            int current = counter.get();
            if (current % 3 == 0 && current % 5 == 0) {
                results.put("fizzbuzz");
                counter.incrementAndGet();
            }
        }
    }

    public void number() throws InterruptedException {
        while (counter.get() <= n) {
            int current = counter.get();
            if (current % 3 != 0 && current % 5 != 0) {
                results.put(Integer.toString(current));
                counter.incrementAndGet();
            }
        }
    }

    public void startThreads() throws InterruptedException {
        Thread threadA = new Thread(() -> {
            try {
                fizz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                buzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzbuzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                number();
                while (counter.get() <= n || !results.isEmpty()) {
                    System.out.println(results.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        threadA.join();
        threadB.join();
        threadC.join();
        threadD.join();
    }

    public static void main(String[] args) throws InterruptedException {
        new FizzBuzzMultiThreaded(15).startThreads();
    }
}
