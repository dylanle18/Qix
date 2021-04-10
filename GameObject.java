import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

    protected ID id;
    protected Tile tile;

    public GameObject(ID id, Tile tile) {
        this.id = id;
        this.tile = tile;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public ID getID() {
        return id;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setTile(int row, int col) {
        this.tile = Grid.getTile(row, col);
    }
}
