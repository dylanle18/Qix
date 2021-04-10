import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject {

    private int velX, velY;
    public boolean push = false;

    public Player(ID id, Tile tile) {
        super(id, tile);
        velX = 0;
        velY = 0;
    }

    public void tick() {
        
    }

    public void render(Graphics g) {
        if (LevelHandler.playerState == PlayerState.IMMUNE) {
            g.setColor(new Color(0, 100 ,0));
        } else {
            g.setColor(Color.green);
        }
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
