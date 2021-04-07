import java.awt.Color;
import java.util.Random;

public enum TileID {

    EMPTY(Color.darkGray),
    PATH(Color.white),
    PUSH(Color.gray),
    CLAIM(getRandColor());

    private Color color;

    private TileID(Color color) {
        this.color = color;
    }

    private static Color getRandColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public Color getColor(){
        return color;
    }

}
