package spacebattle.commander;

import spacebattle.ship.ShipType;
import spacebattle.ship.Spaceship;

import java.util.Random;

public class Alien extends Commander {

    private final Random RAN_NUM;

    public Alien(int sectorSize, int coins) {

        super(sectorSize, coins);
        this.RAN_NUM = new Random();
        this.addRandomShips();

        while (this.getShipsToPlace().length > 0) {
            this.placeShipRandom(this.getRanShipInFleet());
        }
    }

    //chooses a random ship in fleet
    private Spaceship getRanShipInFleet() {
        int x = this.RAN_NUM.nextInt(this.getFleet().length);

        while (this.getFleet()[x] == null) {
            x = this.RAN_NUM.nextInt(this.getFleet().length);
        }
        rotateRandom(this.getFleet()[x]);
        return this.getFleet()[x];
    }

    //rotates ship randomly between 0 and 3 times
    private void rotateRandom(Spaceship ship) {
        int x = this.RAN_NUM.nextInt(4);

        for (int i = 0; i <= x; i++) {
            ship.rotate();
        }
    }

    //places ship in randomly
    private void placeShipRandom(Spaceship ship) {

        boolean[][] posPlacements = this.getSpaceSector().getPossiblePlacements(ship);

        while(!ship.isPlaced()) {

            //if the ship can`t be placed, the fleet is restarted
            if (noPossiblePlacements(ship)) {
                this.setFleet(new Spaceship[]{});
                this.addRandomShips();
                while (this.getShipsToPlace().length > 0) {
                    this.placeShipRandom(this.getRanShipInFleet());
                }
                break;
            }

            int[] x = {this.RAN_NUM.nextInt(posPlacements.length), this.RAN_NUM.nextInt(posPlacements[0].length)};

            if (posPlacements[x[0]][x[1]]) {
                this.getSpaceSector().placeShip(ship, x[0], x[1]);
            }
        }
    }

    //adds random ships to fleet
    private void addRandomShips() {

        while (this.getCoins() > 2) {

            int x = this.RAN_NUM.nextInt(5);

            switch (x) {

                case 0:
                    if (this.getCoins() > 5) {
                        this.addShipToFleet(ShipType.CRUISER);
                    }
                    break;
                case 1:
                    if (this.getCoins() > 4) {
                        this.addShipToFleet(ShipType.CARRIER);
                    }
                    break;
                case 2:
                    if (this.getCoins() > 3) {
                        this.addShipToFleet(ShipType.BATTLESHIP);
                    }
                    break;
                case 3:
                    if (this.getCoins() > 3) {
                        this.addShipToFleet(ShipType.SUBMARINE);
                    }
                    break;
                case 4: {
                    this.addShipToFleet(ShipType.CORVETTE);
                    break;
                }
            }
        }
    }

    //gets random and accepted shot coordinates
    public int[] getShotCoordinates() {
        while (true) {
            int[] x = new int[]{this.RAN_NUM.nextInt(this.getEnemySector().getMap().length),
                    this.RAN_NUM.nextInt(this.getEnemySector().getMap().length)};
            if(this.getEnemySector().getMap()[x[0]][x[1]] == 0) {
                return x;
            }
        }
    }

    public void setRandom(long seed) {
        this.RAN_NUM.setSeed(seed);
    }
}
