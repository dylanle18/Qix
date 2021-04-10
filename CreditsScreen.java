import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

public class CreditsScreen {

    public static final Rectangle backToMenuButton = new Rectangle(425, 555, 150, 50);

    private Random rand = new Random();
    private int red = rand.nextInt(256);
    private int green = rand.nextInt(256);
    private int blue = rand.nextInt(256);
    private boolean incR, incG, incB = true;
    private int tickCounter = 0;

    public void render(Graphics g) {
        tickCounter++;
        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("SansSerif", Font.BOLD, 150);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("QIX", 360, 200);
        Font fnt1 = new Font("SansSerif", Font.BOLD, 30);
        g.setFont(fnt1);
        g.setColor(new Color(red, green, blue));
        g.drawString("Dylan Le", 425, 275);
        g.drawString("Ethan Hou", 410, 325);
        g.drawString("Jonathan Edey", 385, 375);
        g.drawString("Kevin Tran", 410, 425);
        g.drawString("Kyle Choo Mang", 385, 475);
        g.drawString("Nabil Nael Yacoub Mansour", 300, 525);
        g.setColor(Color.white);
        g.drawString("Menu", backToMenuButton.x + 33, backToMenuButton.y + 33);
        g2d.draw(backToMenuButton);

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
