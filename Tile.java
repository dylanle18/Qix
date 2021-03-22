import java.awt.Graphics;

public class Tile {

    private int x, y, size, row, col;
    private TileID tileID;

    public Tile(int x, int y, int size, int row, int col, TileID tileID) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.row = row;
        this.col = col;
        this.tileID = tileID;
    }

    public void render(Graphics g) {
        g.setColor(tileID.getColor());
        g.fillRect(x, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public TileID getTileID() {
        return tileID;
    }

    public void setTileID(TileID tileID) {
        this.tileID = tileID;
    }
}
