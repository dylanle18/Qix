import java.awt.Graphics;

public class LevelHandler {

  private ObjectHandler objectHandler;
  private MovementHandler movementHandler;
  private TileHandler tileHandler;

  public HUD hud;

  public LinkedPath mainPath;
  private int lives, winPercent, levelNumber;

  public LevelHandler(Player player, int lives, int winPercent, int levelNumber, int sparxNumber, int sparxSpeed,
      int qixSpeed) {
    this.lives = lives;
    this.winPercent = winPercent;
    this.levelNumber = levelNumber;
    objectHandler = new ObjectHandler();
    movementHandler = new MovementHandler();

    hud = new HUD(this.lives, this.winPercent, this.levelNumber);

    tileHandler = new TileHandler(player, hud);

    mainPath = new LinkedPath();

    // PLAYER
    objectHandler.addObject(player);
    movementHandler.addMovement(new PlayerMovement(player, tileHandler, mainPath));

    // QIX
    Qix qix = new Qix(ID.QIX, Grid.getTile(Game.GRIDSIZE / 2, Game.GRIDSIZE / 2));
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
  }

  public void tick() {
    if (Game.state == Game.STATE.GAME) {
      objectHandler.tick();
      movementHandler.tick();
      tileHandler.tick();
    }
  }

  public void render(Graphics g) {
    if (Game.state == Game.STATE.GAME) {
      objectHandler.render(g);
      hud.render(g);
    }
  }

}
