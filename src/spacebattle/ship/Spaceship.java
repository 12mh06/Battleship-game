package spacebattle.ship;

public abstract class Spaceship {

    private final ShipType TYPE;
    private int[][] shape;
    private final int PRICE;
    private int[] marker;
    private boolean placed = false;


    //Constructor for class Spaceship
    public Spaceship(ShipType type, int[][] shape, int price) {
        this.TYPE = type;
        this.shape = shape;
        this.PRICE = price;
    }

    //getter methods and setter methods
    public ShipType getType() {
        return this.TYPE;
    }

    public int[][] getShape() {
        return this.shape;
    }

    public int getPrice() { return this.PRICE; }

    public void setMarker(int[] marker) {
        this.marker = marker;
    }

    public int[] getMarker() {
        return marker;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    //returns true if Spaceship object is destroyed
    public boolean isDestroyed() {
        return this.getType() == ShipType.WRECK;
    }

    //rotates Spaceship clockwise
    public void rotate() {
        int[][] changedShape = new int[this.shape[0].length][this.shape.length];
        for(int i = 0; i < this.shape.length; i++) {
            for(int x = 0; x < this.shape[0].length; x++) {
                changedShape[x][this.shape.length - 1 - i] = this.shape[i][x];
            }
        }
        this.shape = changedShape;
    }

    //draws spaceship
    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder();

        for (int[] ints : this.shape) {
            for (int x = 0; x < this.shape[0].length; x++) {

                if (ints[x] == 1) {
                    bld.append("\u004F   ");
                }
                if (ints[x] == 0) {
                    bld.append("\u002D   ");
                }
                if (x == this.shape[0].length - 1) {
                    bld.append("\n");
                }
            }
        }
        return bld.toString();
    }
}
