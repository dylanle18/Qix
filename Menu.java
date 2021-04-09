import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;

public class Menu{

    public static final Rectangle playButton = new Rectangle(425, 275, 150, 50);
    public static final Rectangle creditsButton = new Rectangle(425, 350, 150, 50);
    public static final Rectangle exitButton = new Rectangle(425, 425, 150, 50);

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("Onyx", Font.BOLD, 150);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("QIX", 360, 200);
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("Play", playButton.x + 45, playButton.y + 33);
        g.drawString("Credits", creditsButton.x + 25, creditsButton.y + 33);
        g.drawString("Exit", exitButton.x + 45, exitButton.y + 33);
        g2d.draw(playButton);
        g2d.draw(creditsButton);
        g2d.draw(exitButton);
    }
}
