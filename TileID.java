import java.awt.Color;
import java.util.Random;

public enum TileID {

    EMPTY(Color.darkGray),
    PATH(Color.white),
    PUSH(Color.gray),
    CLAIM(getRandColor());

    private Color color;
    public static Color claimColor = getRandColor();

    private TileID(Color color) {
        this.color = color;
    }

    public static Color getRandColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public Color getColor(){
        return color;
    }

}
