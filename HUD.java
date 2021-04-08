import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {
    
    private int lives, winPercent, level, claimPercent;

    public HUD(int lives, int winPercent) {
        this.lives = lives;
        this.winPercent = winPercent;
        this.level = 1;
        this.claimPercent = 0;
    }

    public void render(Graphics g) {
        g.setColor(TileID.claimColor);
        g.setFont(new Font("SansSerif", Font.BOLD, 50));
        g.drawString("QIX" , 650, 50);
        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 36));
        g.drawString("Lives:" + String.valueOf(lives) , 650, 100);
        g.drawString("Level:" + String.valueOf(level) , 650, 150);
        g.drawString("Claimed:" + String.valueOf(claimPercent) + "%", 650, 200);
        g.drawString("Claim Goal:" + String.valueOf(winPercent) + "%", 650, 250);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getClaimPercent() {
        return claimPercent;
    }

    public void setClaimPercent(int claimPercent) {
        this.claimPercent = claimPercent;
    }

    public int getWinPercent() {
        return winPercent;
    }

    public void setWinPercent(int winPercent) {
        this.winPercent = winPercent;
    }

}
