package spacebattle.battlefield;

import spacebattle.ship.*;

public class SpaceSector extends Sector{

    private Spaceship[][] sector;

    public SpaceSector(int size) {
        super(size);
        this.sector = new Spaceship[size][size];
    }

    public Spaceship[][] getSector() {
        return this.sector;
    }

    public void setSector(Spaceship[][] sector) {
        this.sector = sector;
    }

    //returns all the fields the ship can be placed at
    public boolean[][] getPossiblePlacements(Spaceship ship) {
        boolean[][] result = new boolean[this.size][this.size];
        for(int i = 0; i < this.size; i++) {
            for (int x = 0; x < this.size; x++) {
                if (placingShipPossible(ship, i, x)) {
                    result[i][x] = true;
                }
            }
        }
        return result;
    }

    //returns true if placing a ship in chosen field is allowed
    private boolean placingShipPossible(Spaceship ship, int row, int col) {

        //checks if field is in range of sector
        if (row < 0 || col < 0 || row >= this.size || col >= this.size) {
            return false;
        }

        if (this.sector[row][col] != null) {
            return false;
        }

        if (ship.getShape()[0].length + col > this.size) {
            return false;
        }

        if (ship.getShape().length + row > this.size) {
            return false;
        }

        //checks for possible ships nearby
        for (int i = 0; i < ship.getShape().length; i++) {
            for (int x = 0; x < ship.getShape()[0].length; x++) {

                if (ship.getShape()[i][x] == 1) {

                    //checks lower field
                    if (row + i < this.size - 1) {
                        if (this.sector[row + i + 1][col + x] != null) {
                            return false;
                        }
                        //checks lower left
                        if (col > 0) {
                            if (this.sector[row + i + 1][col + x - 1] != null) {
                                return false;
                            }
                        }
                        //checks lower right
                        if (col + x < this.size - 1) {
                            if (this.sector[row + i + 1][col + x + 1] != null) {
                                return false;
                            }
                        }
                    }

                    //checks upper field
                    if (row > 0) {
                        if (this.sector[row + i - 1][col + x] != null) {
                            return false;
                        }
                        //checks diagonal upper left
                        if (col > 0) {
                            if (this.sector[row + i - 1][col + x - 1] != null) {
                                return false;
                            }
                        }
                        //checks diagonal upper right
                        if (col + x < this.size - 1) {
                            if (this.sector[row + i - 1][col + x + 1] != null) {
                                return false;
                            }
                        }
                    }

                    //checks left field
                    if (col + x < this.size - 1) {
                        if (this.sector[row + i][col + x + 1] != null) {
                            return false;
                        }
                    }
                    //checks right field
                    if (col > 0) {
                        if (this.sector[row + i][col + x - 1] != null) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    //places ship in chosen field
    public void placeShip(Spaceship ship, int row, int col) {
        ship.setMarker(new int[] {row, col + 1});

        for (int i = 0; i < ship.getShape().length; i++) {
            for (int x = 0; x < ship.getShape()[i].length; x++) {
                if (ship.getShape()[i][x] == 1) {
                    this.sector[row + i][col + x] = ship;
                }
            }
        }
        ship.setPlaced(true);
    }

    //returns true if the ship exists in a Space sector
    public boolean shipInSector(Spaceship ship) {

        for (Spaceship[] line : sector) {
            for(Spaceship spaceship: line) {
                if (spaceship != null & spaceship == ship) {
                    return true;
                }
            }
        }
        return false;
    }

    //returns position of ship
    public int[] getPosition(Spaceship ship) {

        if (!shipInSector(ship)) {
            return null;
        }
        else {
            return ship.getMarker();
        }
    }

    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder();

        //Draws the sector in form of a chessboard
        bld.append("\n   ");
        for (int i = 1; i <= this.sector[0].length; i++) {
            if (i < 10) {
                bld.append(i).append("   ");
            }
            else {
                bld.append(i).append("  ");
            }
            if (i == this.sector[0].length) {
                bld.append("\n");
            }
        }
        for (int i = 0; i < this.sector.length; i++) {
            bld.append(rowToLetter(i)).append("  ");
            for (int x = 0; x < this.sector[0].length; x++) {
                if (this.sector[i][x] == null) {
                    bld.append("\u002D   ");
                }
                else if (this.sector[i][x].getType().equals(ShipType.WRECK)) {
                    bld.append("\u0058   ");
                }
                else {
                    bld.append("\u004F   ");
                }
                if (x == this.sector[0].length - 1) {
                    bld.append("\n");
                }
            }
        }
        return bld.toString();
    }
}
