package spacebattle.battlefield;

public abstract class Sector {

    protected int size;

    //constructor for Sector
    public Sector(int size) {
        if (size > 9 && size < 16) {
            this.size = size;
        }
        else {
            this.size = 10;
        }
    }

    //helps formatting
    public static String rowToLetter(int row) {
        char letter = (char) (row + 65);
        return Character.toString(letter);
    }

    @Override
    public abstract String toString();
}
