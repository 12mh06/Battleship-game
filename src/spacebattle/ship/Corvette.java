package spacebattle.ship;

public class Corvette extends Spaceship {

    public Corvette() {
        //empty fields are represented as a 0, fields where the ship is located as a 1
        super(ShipType.CORVETTE, new int[][] { {1}, {1}, {1} }, 3);
    }

}
