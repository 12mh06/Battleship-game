package spacebattle.ship;

public class Carrier extends Spaceship {
    public Carrier() {
        //empty fields are represented as a 0, fields where the ship is located as a 1
        super(ShipType.CARRIER, new int[][] {{1, 0, 0}, {1, 1, 1}, {0, 0, 1}}, 5);
    }
}
