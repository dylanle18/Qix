import java.awt.Graphics;

public class Tile {

    private int x, y, size, row, col;
    private TileID tileID;
    private boolean hasQix = false;
    private boolean hasSparx = false;
    private boolean hasPush = false;

    public Tile(int x, int y, int size, int row, int col, TileID tileID) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.row = row;
        this.col = col;
        this.tileID = tileID;
    }

    public void render(Graphics g) {
        if (tileID == TileID.CLAIM) {
            g.setColor(TileID.claimColor);
        } else if (tileID == TileID.DEADPATH) {
            g.setColor(TileID.claimColor.darker());
        } else {
            g.setColor(tileID.getColor());
        }
        g.fillRect(x, y, size, size);
    }

    public boolean getHasQix() {
        return hasQix;
    }

    public boolean getHasSparx() {
        return hasSparx;
    }

    public boolean getHasPush() {
        return hasPush;
    }

    public void setHasQix(boolean hasQix) {
        this.hasQix = hasQix;
    }

    public void setHasSparx(boolean hasSparx) {
        this.hasSparx = hasSparx;
    }

    public void setHasPush(boolean hasPush) {
        this.hasPush = hasPush;
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
