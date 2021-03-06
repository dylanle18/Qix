import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class HUD {

    public static final Rectangle backToMenuButton = new Rectangle(730, 385, 150, 50);
    private int lives, winPercent, levelNumber, claimPercent;

    public HUD(int lives, int winPercent, int levelNumber) {
        this.lives = lives;
        this.winPercent = winPercent;
        this.levelNumber = levelNumber;
        this.claimPercent = 0;
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(TileID.claimColor);
        g.setFont(new Font("SansSerif", Font.BOLD, 50));
        g.drawString("QIX" , 650, 50);
        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 36));
        g.drawString("Lives:" + String.valueOf(lives) , 655, 100);
        g.drawString("Level:" + String.valueOf(levelNumber) , 655, 150);
        g.drawString("Claimed:" + String.valueOf(claimPercent) + "%", 655, 200);
        g.drawString("Claim Goal:" + String.valueOf(winPercent) + "%", 655, 250);
        g.drawString("Move:", 655, 535);
        g.drawString("\u2191", 819, 520);
        g.drawString("\u2190\u2193\u2192", 787, 550);
        g.drawString("Push: Space", 655, 600);
        g.drawString("Quit", backToMenuButton.x + 33, backToMenuButton.y + 33);
        g2d.draw(backToMenuButton);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLevel() {
        return levelNumber;
    }

    public void setLevel(int level) {
        this.levelNumber = level;
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
