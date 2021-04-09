import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;

public class CreditsScreen {

    public static final Rectangle backToMenuButton = new Rectangle(425, 555, 150, 50);

    public Color randColor = TileID.getRandColor();

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("Onyx", Font.BOLD, 150);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("QIX", 360, 200);
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.setColor(randColor);
        g.drawString("Dylan Le", 425, 275);
        // g.setColor(Color.red);
        g.drawString("Ethan Hou", 410, 325);
        // g.setColor(Color.green);
        g.drawString("Jonathan Edey", 385, 375);
        // g.setColor(Color.orange);
        g.drawString("Kevin Tran", 410, 425);
        // g.setColor(Color.cyan);
        g.drawString("Kyle Choo Mang", 385, 475);
        // g.setColor(Color.yellow);
        g.drawString("Nabil Nael Yacoub Mansour", 300, 525);
        g.setColor(Color.white);
        g.drawString("Menu", backToMenuButton.x + 33, backToMenuButton.y + 33);
        g2d.draw(backToMenuButton);
    }
}
