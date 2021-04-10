import java.awt.Graphics;
import java.util.LinkedList;

public class LevelHandler {

  public enum PLAYER_STATE {
    IMMUNE, NOT_IMMUNE
  };

  public static PLAYER_STATE playerState;

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
    playerState = PLAYER_STATE.NOT_IMMUNE;
    this.playerMovement = new PlayerMovement(player, tileHandler, mainPath);
    // objectHandler.addObject(player);
    movementHandler.addMovement(this.playerMovement);

    // QIX
    this.qix = new Qix(ID.QIX, Grid.getTile(Game.GRIDSIZE / 2, Game.GRIDSIZE / 2));
    objectHandler.addObject(qix);
    movementHandler.addMovement(new QixMovement(qix, qixSpeed));

    // SPARX
    boolean clockwise = true;
    for (int i = 0; i < sparxNumber; ++i) {
      Sparx sparx = new Sparx(ID.SPARX, mainPath.getStart().tile, clockwise);
      objectHandler.addObject(sparx);
      movementHandler.addMovement(new SparxMovement(sparx, mainPath.getStart(), mainPath, sparxSpeed - i));
      clockwise = !clockwise;
    }

    this.tilesToRemove = new LinkedList<Tile>();

  }

  private long startImmuneTime = 0;

  private void loseLife(Player player, HUD hud) {
    int newLives = hud.getLives();
    --newLives;
    hud.setLives(newLives);
    playerState = PLAYER_STATE.IMMUNE;
    startImmuneTime = System.currentTimeMillis();
  }

  private LinkedList<Tile> tilesToRemove;

  public void tick() {
    this.player.tick();
    objectHandler.tick();
    movementHandler.tick();
    tileHandler.tick();

    // HitSparx
    if (playerState == PLAYER_STATE.NOT_IMMUNE && this.player.tile.getHasSparx()) {
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

    if (playerState == PLAYER_STATE.IMMUNE) {
      if (System.currentTimeMillis() - startImmuneTime > 5000) {
        playerState = PLAYER_STATE.NOT_IMMUNE;
      }
    }

    if (!this.tilesToRemove.isEmpty()) { // Remove pushed tiles animation
      Tile toRemove = this.tilesToRemove.removeLast();
      toRemove.setTileID(TileID.EMPTY);
    }
  }

  private int immuneCounter = 0;

  public void render(Graphics g) {
    if (playerState == PLAYER_STATE.IMMUNE) {
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
