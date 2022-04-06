package spacebattle.ship;

public class Battleship extends Spaceship {
    public Battleship() {
        //empty fields are represented as a 0, fields where the ship is located as a 1
        super(ShipType.BATTLESHIP, new int[][] {{1, 1, 1, 1, 1}}, 4);
    }
}
