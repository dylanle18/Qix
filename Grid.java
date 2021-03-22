import java.awt.Graphics;

public class Grid {

    public static Tile[][] map;
    private int rows, columns;

    public Grid(int x, int y, int tileSize, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        map = new Tile[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                map[row][col] = new Tile(col * tileSize + x, row * tileSize + y, tileSize, row, col, TileID.EMPTY);
            }
        }
    }

    public void render(Graphics g) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                map[row][col].render(g);
            }
        }
    }

    public static Tile getTile(int row, int col){
        return map[row][col];
    }
}