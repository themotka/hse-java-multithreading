package src;

public class PeopleFactory implements Runnable{
    Elevator elevator1, elevator2;

    PeopleFactory(Elevator elevator1, Elevator elevator2) {
        this.elevator1 = elevator1;
        this.elevator2 = elevator2;
    }

    public void run() {
        while (true) {
            // Генерация нового человека
            Person person = new Person(getRandFloor(Elevator.TOTAL_FLOORS), getRandFloor(Elevator.TOTAL_FLOORS));
            if (Math.abs(elevator1.getCurrFloor() - person.getCurrFloor()) > Math.abs(elevator2.getCurrFloor() - person.getCurrFloor()) || (elevator2.inside.isEmpty() && !elevator1.inside.isEmpty())){
                callElevator(person, elevator2);
            }
            else {
                callElevator(person, elevator1);
            }
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }
    }
    public static int getRandFloor(int n){
        return (int) (Math.random() * n) + 1;
    }
    public void callElevator(Person person, Elevator e){
        e.outside.add(person);
        System.out.printf("Вызван лифт %s на этаж %d, целевой %d\n", e.getName(), person.getCurrFloor(), person.getDestFloor());
    }

}
