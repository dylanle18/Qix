import java.awt.Color;
import java.awt.Graphics;

public class Qix extends GameObject {

    private int velX, velY;

    public Qix(ID id, Tile tile) {
        super(id, tile);
        velX = 0;
        velY = 0;
    }

    public void tick() {
        
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(tile.getX(), tile.getY(), Game.TILESIZE, Game.TILESIZE);
    }
    
    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }
}
