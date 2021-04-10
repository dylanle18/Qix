import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;  
import javax.swing.JFrame;
import java.awt.Toolkit;

public class Window extends Canvas {

    private static final long serialVersionUID = -8255319694373975038L;

    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        Image icon = Toolkit.getDefaultToolkit().getImage("img/icon.png");    
        frame.setIconImage(icon);    
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }
    
}
