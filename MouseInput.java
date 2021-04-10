import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MouseInput implements MouseListener {

  public void mousePressed(MouseEvent e) {
    int mx = e.getX();
    int my = e.getY();

    if (Game.state == Game.STATE.MENU) { // PLAY
      if (mx >= Menu.playButton.x && mx <= Menu.playButton.x + Menu.playButton.width) {
        if (my >= Menu.playButton.y && my <= Menu.playButton.y + Menu.playButton.height) {
          Game.state = Game.STATE.GAME;
        }
      }
    }

    if (Game.state == Game.STATE.MENU) { // Credits
      if (mx >= Menu.creditsButton.x && mx <= Menu.creditsButton.x + Menu.creditsButton.width) {
        if (my >= Menu.creditsButton.y && my <= Menu.creditsButton.y + Menu.creditsButton.height) {
          Game.state = Game.STATE.CREDITS;
        }
      }
    }

    if (Game.state == Game.STATE.MENU) { // EXIT
      if (mx >= Menu.exitButton.x && mx <= Menu.exitButton.x + Menu.exitButton.width) {
        if (my >= Menu.exitButton.y && my <= Menu.exitButton.y + Menu.exitButton.height) {
          System.exit(1);
        }
      }
    }

    if (Game.state == Game.STATE.GAME) { // BACK TO MENU
      if (mx >= HUD.backToMenuButton.x && mx <= HUD.backToMenuButton.x + HUD.backToMenuButton.width) {
        if (my >= HUD.backToMenuButton.y && my <= HUD.backToMenuButton.y + HUD.backToMenuButton.height) {
          Game.state = Game.STATE.MENU;
        }
      }
    }

    if (Game.state == Game.STATE.CREDITS) { // BACK TO MENU (In Credits)
      if (mx >= CreditsScreen.backToMenuButton.x
          && mx <= CreditsScreen.backToMenuButton.x + CreditsScreen.backToMenuButton.width) {
        if (my >= CreditsScreen.backToMenuButton.y
            && my <= CreditsScreen.backToMenuButton.y + CreditsScreen.backToMenuButton.height) {
          Game.state = Game.STATE.MENU;
        }
      }
    }

    if (Level.screenState == Level.SCREEN_STATE.COMPLETE) { // NEXT LEVEL
      if (mx >= Level.nextLevelButton.x && mx <= Level.nextLevelButton.x + Level.nextLevelButton.width) {
        if (my >= Level.nextLevelButton.y && my <= Level.nextLevelButton.y + Level.nextLevelButton.height) {
          Level.screenState = Level.SCREEN_STATE.NEXT_LEVEL;
        }
      }
    }

    if (Level.screenState == Level.SCREEN_STATE.LOSE) { // PLAY AGAIN
      if (mx >= Level.nextLevelButton.x && mx <= Level.nextLevelButton.x + Level.nextLevelButton.width) {
        if (my >= Level.nextLevelButton.y && my <= Level.nextLevelButton.y + Level.nextLevelButton.height) {
          Level.screenState = Level.SCREEN_STATE.RESET_FST_LEVEL;
        }
      }
    }

    if (Level.screenState == Level.SCREEN_STATE.PLAYING) { // PAUSE
      if (mx >= Level.pauseButton.x && mx <= Level.pauseButton.x + Level.pauseButton.width) {
        if (my >= Level.pauseButton.y && my <= Level.pauseButton.y + Level.pauseButton.height) {
          Level.screenState = Level.SCREEN_STATE.PAUSE;
        }
      }
    }

    else if (Level.screenState == Level.SCREEN_STATE.PAUSE) { // UNPAUSE
      if (mx >= Level.pauseButton.x && mx <= Level.pauseButton.x + Level.pauseButton.width) {
        if (my >= Level.pauseButton.y && my <= Level.pauseButton.y + Level.pauseButton.height) {
          Level.screenState = Level.SCREEN_STATE.PLAYING;
        }
      }
    }

  }

  public void mouseReleased(MouseEvent e) {

  }

  public void mouseEntered(MouseEvent e) {

  }

  public void mouseExited(MouseEvent e) {

  }

  public void mouseClicked(MouseEvent e) {

  }

}
