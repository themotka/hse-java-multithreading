package src;

public class Person {
    private final int currFloor;
    private final int destFloor;

    public Person(int currFloor, int destFloor) {
        this.currFloor = currFloor;
        this.destFloor = destFloor;
    }

    public int getCurrFloor() {
        return currFloor;
    }

    public int getDestFloor() {
        return destFloor;
    }
}
