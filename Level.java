import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Level {

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
        this.currentWinPercent = 40;
        this.sparxNumber = 3;
        this.sparxSpeed = 10;
        this.qixSpeed = 10;
        this.levelHandler = new LevelHandler(player, this.currentLives, this.currentWinPercent, this.currentLevel,
                this.sparxNumber, this.sparxSpeed, this.qixSpeed);
        this.playerInput = new PlayerInput(player);
        this.game = game;
        this.game.addKeyListener(this.playerInput);
    }

    private void nextLevel() {
        ++this.currentLevel;
        this.currentWinPercent += 5;
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
        grid.render(g);
        levelHandler.render(g);
    }

    public void tick() {
        if (levelHandler.hud.getClaimPercent() >= levelHandler.hud.getWinPercent()) { // Level won
            this.nextLevel();
        }

        levelHandler.tick();
        playerInput.tick();
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerInput getPlayerInput() {
        return this.playerInput;
    }

    // private LinkedList<GameObject> object = new LinkedList<GameObject>();
    // private LinkedList<Movement> movement = new LinkedList<Movement>();
    // private int numOfQix, qixDrag, numOfSparx, sparxDrag;
    // private Random rand = new Random();

    // public Level(int numOfQix, int qixDrag, int numOfSparx, int sparxDrag) {
    // this.numOfQix = numOfQix;
    // this.qixDrag = qixDrag;
    // this.numOfSparx = numOfSparx;
    // this.sparxDrag = sparxDrag;
    // startLevel();
    // }

    // public void tick() {

    // }

    // public void render(Graphics g) {

    // }

    // public void startLevel() {
    // // for scalability, if more then 1 qix is never wanted
    // // for (int i = 0; i < numOfQix; i++) {
    // // Qix qix = new Qix(ID.QIX, Grid.getTile(Game.GRIDSIZE / 2, Game.GRIDSIZE /
    // 2));
    // // object.add(qix);
    // // movement.add(new QixMovement(qix, qixDrag));
    // // }
    // // for (int i = 0; i < numOfSparx; i++) {
    // // Sparx sparx = new Sparx(ID.SPARX, Grid.getTile(0,
    // rand.nextInt(Game.GRIDSIZE)), i % 2 == 0);
    // // object.add(sparx);
    // // movement.add(new SparxMovement(sparx, mainPath.getStart(), sparxDrag));
    // // }
    // }

}
