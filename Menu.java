import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

public class Menu{

    public static final Rectangle playButton = new Rectangle(425, 275, 150, 50);
    public static final Rectangle creditsButton = new Rectangle(425, 350, 150, 50);
    public static final Rectangle exitButton = new Rectangle(425, 425, 150, 50);
    
    private Random rand = new Random();
    private int red = rand.nextInt(256);
    private int green = rand.nextInt(256);
    private int blue = rand.nextInt(256);
    private boolean incR, incG, incB = true;
    private int tickCounter = 0;

    public void render(Graphics g){
        tickCounter++;
        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("SansSerif", Font.BOLD, 150);
        g.setFont(fnt0);
        g.setColor(new Color(red, green, blue));
        g.drawString("QIX", 360, 200);
        g.setColor(Color.white);
        Font fnt1 = new Font("SansSerif", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("Play", playButton.x + 45, playButton.y + 33);
        g.drawString("Credits", creditsButton.x + 25, creditsButton.y + 33);
        g.drawString("Exit", exitButton.x + 45, exitButton.y + 33);
        g2d.draw(playButton);
        g2d.draw(creditsButton);
        g2d.draw(exitButton);

        if (tickCounter == 32) {
            nextColor();
            tickCounter = 0;   
        }
    }

    public void nextColor(){
        int randIncR = rand.nextInt(3);
        int randIncG = rand.nextInt(3);
        int randIncB = rand.nextInt(3);
        if (incR) {
            if (red + randIncR <= 255) {
                red += randIncR;
            } else {
                incR = false;
            }
        } else {
            if (red - randIncR >= 0) {
                red -= randIncR;
            } else {
                incR = true;
            }
        }

        if (incG) {
            if (green + randIncG <= 255) {
                green += randIncG;
            } else {
                incG = false;
            }
        } else {
            if (green - randIncG >= 0) {
                green -= randIncG;
            } else {
                incG = true;
            }
        }

        if (incB) {
            if (blue + randIncB <= 255) {
                blue += randIncB;
            } else {
                incB = false;
            }
        } else {
            if (blue - randIncB >= 0) {
                blue -= randIncB;
            } else {
                incB = true;
            }
        }
    }
}
