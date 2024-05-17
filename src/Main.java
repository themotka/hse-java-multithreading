package src;

public class Main {
    public static void main(String[] args) {
        Elevator elevator1 = new Elevator(ConsoleColor.ANSI_BLUE, "1");
        Elevator elevator2 = new Elevator(ConsoleColor.ANSI_BLUE, "                                     2 ");
        PeopleFactory personGenerator = new PeopleFactory(elevator1, elevator2);

        new Thread(elevator1).start();
        new Thread(elevator2).start();
        new Thread(personGenerator).start();
    }
}
