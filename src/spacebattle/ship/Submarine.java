package spacebattle.ship;

public class Submarine extends Spaceship{
    //empty fields are represented as a 0, fields where the ship is located as a 1
    public Submarine() {
        super(ShipType.SUBMARINE, new int[][] {{1}}, 4);
    }
}
