package src;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Elevator implements Runnable {
    final static int TOTAL_FLOORS = 12;
    private final String color;
    private int currFloor = 0;
    private boolean isBusy = false;

    public boolean isBusy() {
        return isBusy;
    }

    BlockingQueue<Person> inside = new LinkedBlockingQueue<>();
    BlockingQueue<Person> outside = new LinkedBlockingQueue<>();

    public String getName() {
        return name;
    }

    private final String name;

    public Elevator(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public int getCurrFloor() {
        return currFloor;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!inside.isEmpty()) {
                isBusy = true;
                move(inside.peek().getDestFloor());
            } else while (outside.isEmpty()) {
                isBusy = false;
                wait(1000);
            }
            if (!outside.isEmpty() && inside.isEmpty()) {
                isBusy = true;
                move(outside.peek().getCurrFloor());

            }
            for (Person p : outside) {
                if (p.getCurrFloor() == currFloor) {
                    boolean ignored = outside.remove(p);
                    inside.add(p);
                    System.out.printf(ConsoleColor.ANSI_GREEN + "%s лифт, пассажир (%d, %d) вошел, на %d этаже\n" + ConsoleColor.ANSI_RESET,
                            name, p.getCurrFloor(), p.getDestFloor(), currFloor);
                }
            }

            for (Person p : inside) {
                if (p.getDestFloor() == currFloor) {
                    boolean ignored = inside.remove(p);
                    System.out.printf(ConsoleColor.ANSI_RED + "%s лифт, пассажир (%d, %d) вышел, на %d этаже\n" + ConsoleColor.ANSI_RESET,
                            name, p.getCurrFloor(), p.getDestFloor(), currFloor);
                }
            }
            wait(1000);
        }
    }

    public void move(int floor) {
        if (floor > currFloor) {
            currFloor += 1;
            System.out.printf(color + "%s лифт переехал %d -> %d\n" + ConsoleColor.ANSI_RESET,
                    name, currFloor - 1, currFloor);
        } else if (floor < currFloor) {
            currFloor -= 1;
            System.out.printf(color + "%s лифт переехал %d -> %d\n" + ConsoleColor.ANSI_RESET,
                    name, currFloor + 1, currFloor);
        }
        wait(500);
    }

    public static void wait(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException ignored) {
        }
    }
}
