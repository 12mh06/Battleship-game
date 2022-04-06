package spacebattle.ship;

public class Cruiser extends Spaceship {
    //empty fields are represented as a 0, fields where the ship is located as a 1
    public Cruiser() {
        super(ShipType.CRUISER, new int[][] {{0, 0, 0, 1}, {1, 1, 1, 1}, {0, 0, 0, 1}}, 6);
    }
}
