import java.awt.Graphics;

public class Grid {

    private static final int[] ABOVE = { -1, 0 };
    private static final int[] BELOW = { 1, 0 };
    private static final int[] LEFT = { 0, -1 };
    private static final int[] RIGHT = { 0, 1 };

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

    public static Tile getTile(int row, int col) {
        return map[row][col];
    }

    public static boolean nextToEmpty(Tile tile) {
        if (Grid.inGrid(tile.getRow() + ABOVE[0], tile.getCol() + ABOVE[1])
                && Grid.getTile(tile.getRow() + ABOVE[0], tile.getCol() + ABOVE[1]).getTileID() == TileID.EMPTY) {
            return true;
        }
        if (Grid.inGrid(tile.getRow() + BELOW[0], tile.getCol() + BELOW[1])
                && Grid.getTile(tile.getRow() + BELOW[0], tile.getCol() + BELOW[1]).getTileID() == TileID.EMPTY) {
            return true;
        }
        if (Grid.inGrid(tile.getRow() + LEFT[0], tile.getCol() + LEFT[1])
                && Grid.getTile(tile.getRow() + LEFT[0], tile.getCol() + LEFT[1]).getTileID() == TileID.EMPTY) {
            return true;
        }
        if (Grid.inGrid(tile.getRow() + RIGHT[0], tile.getCol() + RIGHT[1])
                && Grid.getTile(tile.getRow() + RIGHT[0], tile.getCol() + RIGHT[1]).getTileID() == TileID.EMPTY) {
            return true;
        }
        return false;
    }

    public static boolean cornerIsNextEmpty(Tile tile) {
        if (Grid.inGrid(tile.getRow() + BELOW[0], tile.getCol() + RIGHT[1])
                && Grid.getTile(tile.getRow() + BELOW[0], tile.getCol() + RIGHT[1]).getTileID() == TileID.EMPTY) {
            return true;
        }
        if (Grid.inGrid(tile.getRow() + ABOVE[0], tile.getCol() + RIGHT[1])
                && Grid.getTile(tile.getRow() + ABOVE[0], tile.getCol() + RIGHT[1]).getTileID() == TileID.EMPTY) {
            return true;
        }
        if (Grid.inGrid(tile.getRow() + ABOVE[0], tile.getCol() + LEFT[1])
                && Grid.getTile(tile.getRow() + ABOVE[0], tile.getCol() + LEFT[1]).getTileID() == TileID.EMPTY) {
            return true;
        }
        if (Grid.inGrid(tile.getRow() + BELOW[0], tile.getCol() + LEFT[1])
                && Grid.getTile(tile.getRow() + BELOW[0], tile.getCol() + LEFT[1]).getTileID() == TileID.EMPTY) {
            return true;
        }
        return false;
    }

    public static boolean inGrid(int row, int col) {
        return row >= 0 && row < Game.GRIDSIZE && col >= 0 && col < Game.GRIDSIZE;
    }
}