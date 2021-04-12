import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Level {

    public enum SCREEN_STATE {
        PLAYING, COMPLETE, WIN, LOSE, NEXT_LEVEL, RESET_FST_LEVEL, PAUSE
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
        this.setFirstLevel();
        this.levelHandler = new LevelHandler(player, this.currentLives, this.currentWinPercent, this.currentLevel,
                this.sparxNumber, this.sparxSpeed, this.qixSpeed);
        this.playerInput = new PlayerInput(player);
        this.game = game;
        this.game.addKeyListener(this.playerInput);

        screenState = SCREEN_STATE.PLAYING;
    }

    private void setFirstLevel() {
        this.currentLevel = 1;
        this.currentLives = 10;
        this.currentWinPercent = 35;
        this.sparxNumber = 1;
        this.sparxSpeed = 10;
        this.qixSpeed = 5;
    }

    private void nextLevel() {
        ++this.currentLevel;
        ++this.sparxNumber;
        --this.sparxSpeed;
        --this.qixSpeed;
        if (this.currentWinPercent < 85) {
            this.currentWinPercent += 5;
        }

        this.grid = new Grid(5, 5, Game.TILESIZE, Game.GRIDSIZE, Game.GRIDSIZE);
        this.player = new Player(ID.PLAYER, Grid.getTile(Game.GRIDSIZE - 1, Game.GRIDSIZE / 2));
        this.levelHandler = new LevelHandler(player, this.levelHandler.hud.getLives(), this.currentWinPercent,
                this.currentLevel, this.sparxNumber, this.sparxSpeed, this.qixSpeed);
        this.playerInput = new PlayerInput(player);
        this.game.addKeyListener(this.playerInput);
    }

    private void resetToFstLevel() {
        this.grid = new Grid(5, 5, Game.TILESIZE, Game.GRIDSIZE, Game.GRIDSIZE);
        this.player = new Player(ID.PLAYER, Grid.getTile(Game.GRIDSIZE - 1, Game.GRIDSIZE / 2));
        this.setFirstLevel();
        this.levelHandler = new LevelHandler(player, this.currentLives, this.currentWinPercent, this.currentLevel,
                this.sparxNumber, this.sparxSpeed, this.qixSpeed);
        this.playerInput = new PlayerInput(player);
        this.game.addKeyListener(this.playerInput);

        screenState = SCREEN_STATE.PLAYING;
    }

    public void render(Graphics g) {
        this.showPauseButton(g);
        grid.render(g);
        levelHandler.render(g);
        if (screenState == SCREEN_STATE.COMPLETE) { // Level won
            this.showLevelComplete(g);
        }
        if (screenState == SCREEN_STATE.LOSE) {
            this.showLoseScreen(g);
        } else if (screenState == SCREEN_STATE.PAUSE) { // Level won
            this.showUnPauseButton(g);
        } else if (screenState != SCREEN_STATE.PAUSE) {
            this.showPauseButton(g);
        }
    }

    public void tick() {
        if (screenState == SCREEN_STATE.PLAYING) {
            levelHandler.tick();
            // if (this.levelHandler.tilesToRemove.isEmpty()) {
                playerInput.tick();
            // }
        } else if (screenState == SCREEN_STATE.NEXT_LEVEL) {
            this.nextLevel();
            screenState = SCREEN_STATE.PLAYING;
        } else if (screenState == SCREEN_STATE.RESET_FST_LEVEL) {
            this.resetToFstLevel();
            screenState = SCREEN_STATE.PLAYING;
        }

        // Check end states
        if (levelHandler.hud.getClaimPercent() >= levelHandler.hud.getWinPercent()) { // Level won
            screenState = SCREEN_STATE.COMPLETE;
        }

        if (levelHandler.hud.getLives() == 0) { // Lose
            screenState = SCREEN_STATE.LOSE;
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
        g.drawString("Next Level", ((int) (Game.GRIDSIZE / 3) * 10) + 45, ((int) (Game.GRIDSIZE / 2) * 10) + 85);
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

    private void showLoseScreen(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(TileID.claimColor.darker());
        g2d.fill(nextLevelButton);

        g.setFont(new Font("SansSerif", Font.BOLD, 50));
        g.drawString("You Lose :(", (int) (Game.GRIDSIZE / 3) * 10, ((int) (Game.GRIDSIZE / 2) * 10) + 25);

        g.setColor(TileID.claimColor.brighter());
        g.setFont(new Font("SansSerif", Font.BOLD, 36));
        g.drawString("Play Again", ((int) (Game.GRIDSIZE / 3) * 10) + 45, ((int) (Game.GRIDSIZE / 2) * 10) + 85);
    }

    public static final Rectangle nextLevelButton = new Rectangle(((int) (Game.GRIDSIZE / 3) * 10) + 35,
            ((int) (Game.GRIDSIZE / 2) * 10) + 50, 200, 50);

    public static final Rectangle pauseButton = new Rectangle(715, 325, 175, 50);

}
