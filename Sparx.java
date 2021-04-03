import java.awt.Color;
import java.awt.Graphics;

public class Sparx extends GameObject {

    public Boolean clockwise;

    public Sparx(ID id, Tile tile, Boolean clockwise) {
        super(id, tile);
        this.clockwise = clockwise;
    }

    public void tick() {
        
    }

    public void render(Graphics g) {
        g.setColor(Color.magenta);
        g.fillRect(tile.getX(), tile.getY(), Game.TILESIZE, Game.TILESIZE);
    }

}
