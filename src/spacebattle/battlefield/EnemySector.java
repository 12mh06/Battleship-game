package spacebattle.battlefield;

public class EnemySector extends Sector{

    private int[][] map;

    public EnemySector(int size) {
        super(size);
        this.map = new int[size][size];
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int[][] getMap() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder();

        //Draws the sector in form of a chessboard
        bld.append("   ");
        for (int i = 1; i <= this.map[0].length; i++) {
            if (i < 10) {
                bld.append(i).append("   ");
            }
            else {
                bld.append(i).append("  ");
            }
            if (i == this.map[0].length) {
                bld.append("\n");
            }
        }

        for (int i = 0; i < this.map.length; i++) {
            bld.append(rowToLetter(i)).append("  ");
            for (int x = 0; x < this.map[0].length; x++) {
                if (this.map[i][x] == 0) {
                    bld.append("\u002D   ");
                }
                else if (this.map[i][x] == 1) {
                    bld.append("\u0058   ");
                }
                else {
                    bld.append("\u0023   ");
                }
                if (x == this.map[0].length - 1) {
                    bld.append("\n");
                }
            }
        }
        return bld.toString();
    }
}
