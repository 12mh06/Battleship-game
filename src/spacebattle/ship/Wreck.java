package spacebattle.ship;

public class Wreck extends Spaceship{
    //empty fields are represented as a 0, fields where the ship is located as a 1
    public Wreck() {
        super(ShipType.WRECK, new int[][] { {1} }, 0);
    }
}
