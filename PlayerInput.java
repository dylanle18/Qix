import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerInput extends KeyAdapter {

    private Player player;
    private boolean up, down, left, right = false;

    public PlayerInput(Player player) {
        this.player = player;
    }

    public void tick() {
        if (!up && !down) {
            player.setVelY(0);
        }
        if (!left && !right) {
            player.setVelX(0);
        }
        if (up && !down && !right && !left) {
            player.setVelY(-1);
            player.setVelX(0);
        }
        if (down && !up && !right && !left) {
            player.setVelY(1);
            player.setVelX(0);
        }
        if (left && !right && !up && !down) {
            player.setVelX(-1);
            player.setVelY(0);
        }
        if (right && !left && !up && !down) {
            player.setVelX(1);
            player.setVelY(0);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            player.push = true;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            up = true;
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            down = true;
        }
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            left = true;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            right = true;
        }
        if (key == KeyEvent.VK_SPACE && Level.screenState == Level.SCREEN_STATE.COMPLETE) {
            Level.screenState = Level.SCREEN_STATE.NEXT_LEVEL;
        }
        if (key == KeyEvent.VK_SPACE && Level.screenState == Level.SCREEN_STATE.LOSE) {
            Level.screenState = Level.SCREEN_STATE.RESET_FST_LEVEL;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            player.push = false;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            up = false;
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            down = false;
        }
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            left = false;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            right = false;
        }
    }
}
