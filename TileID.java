import java.awt.Color;

public enum TileID {

    EMPTY(Color.darkGray),
    PATH(Color.white),
    PUSH(Color.gray),
    CLAIM(Color.blue);

    private Color color;

    private TileID(Color color) {
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

}
