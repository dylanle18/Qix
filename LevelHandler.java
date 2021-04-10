import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class LevelHandler {

  public static PlayerState playerState;

  private ObjectHandler objectHandler;
  private MovementHandler movementHandler;
  private TileHandler tileHandler;

  public HUD hud;
  private Qix qix;
  private Player player;
  private PlayerMovement playerMovement;

  public LinkedPath mainPath;

  public LevelHandler(Player player, int lives, int winPercent, int levelNumber, int sparxNumber, int sparxSpeed,
      int qixSpeed) {
    objectHandler = new ObjectHandler();
    movementHandler = new MovementHandler();

    hud = new HUD(lives, winPercent, levelNumber);

    tileHandler = new TileHandler(player, hud);

    mainPath = new LinkedPath();

    // PLAYER
    this.player = player;
    playerState = PlayerState.NOT_IMMUNE;
    this.playerMovement = new PlayerMovement(player, tileHandler, mainPath);
    // objectHandler.addObject(player);
    movementHandler.addMovement(this.playerMovement);

    // QIX
    this.qix = new Qix(ID.QIX, Grid.getTile(Game.GRIDSIZE / 2, Game.GRIDSIZE / 2));
    objectHandler.addObject(qix);
    if (qixSpeed < 1) {
      movementHandler.addMovement(new QixMovement(qix, 1));
    } else {
      movementHandler.addMovement(new QixMovement(qix, qixSpeed));
    }

    // SPARX
    boolean clockwise = true;
    Random randInt = new Random();
    for (int i = 0; i < sparxNumber; ++i) {
      Tile randomTile = Grid.getRandomEdgeTile();
      Sparx sparx = new Sparx(ID.SPARX, randomTile, clockwise);
      int newSpeed = sparxSpeed + randInt.nextInt(sparxNumber)/2 - randInt.nextInt(sparxNumber)/2;
      if (newSpeed <= 0) {
        newSpeed = 1;
      }
      objectHandler.addObject(sparx);
      movementHandler.addMovement(new SparxMovement(sparx, mainPath.getStart(), mainPath, newSpeed));
      clockwise = !clockwise;
    }

    this.tilesToRemove = new LinkedList<Tile>();

  }

  private long startImmuneTime = 0;

  private void loseLife(Player player, HUD hud) {
    int newLives = hud.getLives();
    --newLives;
    hud.setLives(newLives);
    playerState = PlayerState.IMMUNE;
    startImmuneTime = System.currentTimeMillis();
  }

  private LinkedList<Tile> tilesToRemove;

  public void tick() {
    objectHandler.tick();
    movementHandler.tick();
    tileHandler.tick();
    this.player.tick();

    // HitSparx
    if (playerState == PlayerState.NOT_IMMUNE && this.player.tile.getHasSparx()) {
      this.loseLife(this.player, this.hud);
    }

    // Qix hit push path
    if (this.qix.getTile().getHasPush() && playerMovement.startedPushing) {
      player.setTile(playerMovement.startingPushTile);
      playerMovement.startedPushing = false;

      for (Tile pushedTile : this.playerMovement.pushingPath) {
        pushedTile.setHasPush(false);
        this.tilesToRemove.add(pushedTile);
      }

      this.playerMovement.pushingPath = new LinkedList<Tile>();
      this.loseLife(this.player, this.hud);
    }

    if (playerState == PlayerState.IMMUNE) {
      if (System.currentTimeMillis() - startImmuneTime > 5000) {
        playerState = PlayerState.NOT_IMMUNE;
      }
    }

    if (!this.tilesToRemove.isEmpty()) { // Remove pushed tiles animation
      Tile toRemove = this.tilesToRemove.removeLast();
      toRemove.setTileID(TileID.EMPTY);
    }
  }

  private int immuneCounter = 0;

  public void render(Graphics g) {
    if (playerState == PlayerState.IMMUNE) {
      if (immuneCounter < 10) {
        this.player.render(g);
      } else if (immuneCounter >= 20) {
        immuneCounter = 0;
      }
      ++immuneCounter;

    } else {
      this.player.render(g);
    }

    objectHandler.render(g);
    hud.render(g);
  }

}
