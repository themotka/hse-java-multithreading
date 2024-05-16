package src;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Elevator implements Runnable {
    final static int TOTAL_FLOORS = 12;
    private int currFloor = 0;
    BlockingQueue<Person> inside = new LinkedBlockingQueue<>();
    BlockingQueue<Person> outside = new LinkedBlockingQueue<>();

    public String getName() {
        return name;
    }

    private final String name;

    public Elevator(String name) {
        this.name = name;
    }

    public int getCurrFloor() {
        return currFloor;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!inside.isEmpty()) move(inside.peek());
            else while (outside.isEmpty()) {
                wait(1000);
            }
            if (!outside.isEmpty() && inside.isEmpty()){
                move(outside.peek());
            }
            for (Person p : inside) {
                if (p.getDestFloor() == currFloor) {
                    inside.remove(p);
                    System.out.printf("Пассажир (%d, %d) вышел из лифта %s, на %d этаже\n",
                            p.getCurrFloor(), p.getDestFloor(), name, currFloor);
                }
            }
            inside.removeIf(p -> p.getCurrFloor() == currFloor);
            for (Person p : outside) {
                if (p.getCurrFloor() == currFloor) {
                    outside.remove(p);
                    inside.add(p);
                    System.out.printf("Пассажир (%d, %d) вошел в лифт %s, на %d этаже\n",
                            p.getCurrFloor(), p.getDestFloor(), name, currFloor);
                }
            }
            wait(1000);
        }
    }

    public void move(Person p) {
        if (p.getDestFloor() > currFloor) {
            currFloor += 1;
            System.out.printf("%s лифт переехал на этаж %d\n", name, currFloor);
        }
        else if (p.getDestFloor() < currFloor) {
            currFloor -= 1;
            System.out.printf("%s лифт переехал на этаж %d\n", name, currFloor);
        }
        wait(500);
    }

    public static void wait(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
        }
    }
}
