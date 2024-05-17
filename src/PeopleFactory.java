package src;

public class PeopleFactory implements Runnable {
    Elevator elevator1, elevator2;

    PeopleFactory(Elevator elevator1, Elevator elevator2) {
        this.elevator1 = elevator1;
        this.elevator2 = elevator2;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Person person = new Person(getRandFloor(Elevator.TOTAL_FLOORS), getRandFloor(Elevator.TOTAL_FLOORS));
            if (!elevator2.isBusy() && elevator1.isBusy()) {
                callElevator(person, elevator2);
            } else if (elevator2.isBusy() && !elevator1.isBusy()) {
                callElevator(person, elevator1);
            } else if (elevator2.isBusy() && elevator1.isBusy() || !elevator2.isBusy() && !elevator1.isBusy()) {
                if (Math.abs(elevator1.getCurrFloor() - person.getCurrFloor()) < Math.abs(elevator2.getCurrFloor() - person.getCurrFloor())) {
                    callElevator(person, elevator1);
                } else callElevator(person, elevator2);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static int getRandFloor(int n) {
        return (int) (Math.random() * n) + 1;
    }

    public void callElevator(Person person, Elevator e) {
        e.outside.add(person);
        System.out.printf(ConsoleColor.ANSI_CYAN + "%s лифт вызван на этаж %d, целевой %d\n" + ConsoleColor.ANSI_RESET, e.getName(), person.getCurrFloor(), person.getDestFloor());
    }

}
