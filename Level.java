import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Level {

    public enum SCREEN_STATE {
        PLAYING, COMPLETE, WIN, LOSE, NEXT_LEVEL, PAUSE
    };

    public static SCREEN_STATE screenState = SCREEN_STATE.PLAYING;

    private LevelHandler levelHandler;

    private Grid grid;
    private Player player;
    private Game game;
    private int currentLevel;
    private int currentLives;
    private int currentWinPercent;

    private int sparxNumber;
    private int sparxSpeed;
    private int qixSpeed;

    private PlayerInput playerInput;

    public Level(Game game) {
        this.grid = new Grid(5, 5, Game.TILESIZE, Game.GRIDSIZE, Game.GRIDSIZE);
        this.player = new Player(ID.PLAYER, Grid.getTile(Game.GRIDSIZE - 1, Game.GRIDSIZE / 2));
        this.currentLevel = 1;
        this.currentLives = 5;
        this.currentWinPercent = 1;
        this.sparxNumber = 3;
        this.sparxSpeed = 10;
        this.qixSpeed = 10;
        this.levelHandler = new LevelHandler(player, this.currentLives, this.currentWinPercent, this.currentLevel,
                this.sparxNumber, this.sparxSpeed, this.qixSpeed);
        this.playerInput = new PlayerInput(player);
        this.game = game;
        this.game.addKeyListener(this.playerInput);

        screenState = SCREEN_STATE.PLAYING;
    }

    private void nextLevel() {
        ++this.currentLevel;
        this.currentWinPercent += 1;
        ++this.sparxNumber;
        --this.sparxSpeed;
        --this.qixSpeed;

        this.grid = new Grid(5, 5, Game.TILESIZE, Game.GRIDSIZE, Game.GRIDSIZE);
        this.player = new Player(ID.PLAYER, Grid.getTile(Game.GRIDSIZE - 1, Game.GRIDSIZE / 2));
        this.levelHandler = new LevelHandler(player, this.currentLives, this.currentWinPercent, this.currentLevel,
                this.sparxNumber, this.sparxSpeed, this.qixSpeed);
        this.playerInput = new PlayerInput(player);
        this.game.addKeyListener(this.playerInput);
    }

    public void render(Graphics g) {
        this.showPauseButton(g);
        grid.render(g);
        levelHandler.render(g);
        if (screenState == SCREEN_STATE.COMPLETE) { // Level won
            this.showLevelComplete(g);
        }
        if (screenState == SCREEN_STATE.PAUSE) { // Level won
            this.showUnPauseButton(g);
        } else if (screenState != SCREEN_STATE.PAUSE) {
            this.showPauseButton(g);
        }
    }

    public void tick() {
        // System.out.println(screenState);
        if (screenState == SCREEN_STATE.PLAYING) {
            levelHandler.tick();
            playerInput.tick();
        } else if (screenState == SCREEN_STATE.NEXT_LEVEL) {
            this.nextLevel();
            screenState = SCREEN_STATE.PLAYING;
        }

        if (levelHandler.hud.getClaimPercent() >= levelHandler.hud.getWinPercent()) { // Level won
            screenState = SCREEN_STATE.COMPLETE;
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerInput getPlayerInput() {
        return this.playerInput;
    }

    private void showLevelComplete(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(TileID.claimColor.brighter());
        g2d.fill(nextLevelButton);

        g.setFont(new Font("SansSerif", Font.BOLD, 50));
        g.drawString("Level: " + String.valueOf(this.currentLevel) + " Complete!", (int) (Game.GRIDSIZE / 5) * 10,
                ((int) (Game.GRIDSIZE / 2) * 10) + 25);

        g.setColor(TileID.claimColor.darker());
        g.setFont(new Font("SansSerif", Font.BOLD, 36));
        g.drawString("Next Level", ((int) (Game.GRIDSIZE / 3) * 10) + 50, ((int) (Game.GRIDSIZE / 2) * 10) + 85);
    }

    private void showPauseButton(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 36));
        g.drawString("Pause", pauseButton.x + 35, pauseButton.y + 33);
        g2d.draw(pauseButton);
    }

    private void showUnPauseButton(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.black);
        g2d.fill(pauseButton);
        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 36));
        g.drawString("Unpause", pauseButton.x + 10, pauseButton.y + 33);
        g2d.draw(pauseButton);

        g.setColor(TileID.claimColor.brighter());
        g.setFont(new Font("SansSerif", Font.BOLD, 50));
        g.drawString("Paused", ((int) (Game.GRIDSIZE / 2) * 10) - 75, ((int) (Game.GRIDSIZE / 2) * 10) + 25);
    }

    public static final Rectangle nextLevelButton = new Rectangle(((int) (Game.GRIDSIZE / 3) * 10) + 40,
            ((int) (Game.GRIDSIZE / 2) * 10) + 50, 200, 50);

    public static final Rectangle pauseButton = new Rectangle(715, 325, 175, 50);

}
