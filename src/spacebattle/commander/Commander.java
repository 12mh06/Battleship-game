package spacebattle.commander;

import spacebattle.battlefield.EnemySector;
import spacebattle.battlefield.SpaceSector;
import spacebattle.ship.*;

import java.util.ArrayList;

public class Commander {

    private Spaceship[] fleet;
    private SpaceSector spaceSector;
    private EnemySector enemySector;
    private int coins;

    public Commander(int sectorSize, int coins) {
        this.spaceSector = new SpaceSector(sectorSize);
        this.enemySector = new EnemySector(sectorSize);
        this.coins = coins;
        this.fleet = new Spaceship[0];
    }

    //Getter and Setter Methods
    public Spaceship[] getFleet() {
        return fleet;
    }

    public void setFleet(Spaceship[] fleet) {
        this.fleet = fleet;
    }

    public SpaceSector getSpaceSector() {
        return spaceSector;
    }

    public void setSpaceSector(SpaceSector spaceSector) {
        this.spaceSector = spaceSector;
    }

    public EnemySector getEnemySector() {
        return enemySector;
    }

    public void setEnemySector(EnemySector enemySector) {
        this.enemySector = enemySector;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    //returns true if all the ships in a commander`s sector are wrecks
    public boolean isDefeated() {
        for(Spaceship[] line : this.spaceSector.getSector()) {
            for (Spaceship ship : line) {
                if (ship != null && !ship.isDestroyed()) {
                    return false;
                }
            }
        }
        return true;
    }

    //returns array containing all ships in fleet that haven`t been placed yet
    public Spaceship[] getShipsToPlace() {
        ArrayList<Spaceship> unplacedShips = new ArrayList<>();
        for (Spaceship ship : fleet) {
            if (!ship.isPlaced()) {
                unplacedShips.add(ship);
            }
        }
        Spaceship[] result = new Spaceship[unplacedShips.size()];
        return unplacedShips.toArray(result);
    }

    public void updateEnemyMap(int row, int col, boolean hit) {
        if (hit) {
            this.enemySector.getMap()[row][col] = 1;
        }
        else {
            this.enemySector.getMap()[row][col] = -1;
        }
    }

    //adds ships to fleet
    public void addShipToFleet(ShipType type) {

        Spaceship[] copy = new Spaceship[this.fleet.length + 1];
        System.arraycopy(this.fleet, 0, copy, 0, this.fleet.length);

        switch (type) {
            case BATTLESHIP:
                copy[this.fleet.length] = new Battleship();
                this.coins -= copy[this.fleet.length].getPrice();
                break;
            case CARRIER:
                copy[this.fleet.length] = new Carrier();
                this.coins -= copy[this.fleet.length].getPrice();
                break;
            case CORVETTE:
                copy[this.fleet.length] = new Corvette();
                this.coins -= copy[this.fleet.length].getPrice();
                break;
            case CRUISER:
                copy[this.fleet.length] = new Cruiser();
                this.coins -= copy[this.fleet.length].getPrice();
                break;
            case SUBMARINE:
                copy[this.fleet.length] = new Submarine();
                this.coins -= copy[this.fleet.length].getPrice();
                break;
        }
        this.fleet = copy;
    }
    //returns true if ship can`t be placed
    public boolean noPossiblePlacements(Spaceship ship) {
        for (boolean[] line : this.getSpaceSector().getPossiblePlacements(ship)) {
            for (boolean bol : line) {
                if (bol) {
                    return false;
                }
            }
        }
        return true;
    }

    //draws game
    @Override
    public String toString() {

        return "\nYour sector:\n" + this.spaceSector.toString() + "\n\n" +
                "Your enemy`s sector:\n\n" + this.enemySector.toString();
    }
}
