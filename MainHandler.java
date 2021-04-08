import java.awt.Graphics;

public class MainHandler {

    private ObjectHandler objectHandler;
    private MovementHandler movementHandler;
    private TileHandler tileHandler;

    private HUD hud;

    public LinkedPath mainPath;

    public MainHandler(Player player) {
        objectHandler = new ObjectHandler();
        movementHandler = new MovementHandler();

        hud = new HUD(5, 50);

        tileHandler = new TileHandler(player, hud);

        mainPath = new LinkedPath();

        // PLAYER
        objectHandler.addObject(player);
        movementHandler.addMovement(new PlayerMovement(player, tileHandler, mainPath));

        // QIX
        Qix qix = new Qix(ID.QIX, Grid.getTile(Game.GRIDSIZE / 2, Game.GRIDSIZE / 2));
        objectHandler.addObject(qix);
        movementHandler.addMovement(new QixMovement(qix, 4));

        // SPARX
        Sparx sparx = new Sparx(ID.SPARX, mainPath.getStart().tile, true);
        objectHandler.addObject(sparx);
        movementHandler.addMovement(new SparxMovement(sparx, mainPath.getStart(), 1));
        Sparx sparx1 = new Sparx(ID.SPARX, mainPath.getStart().tile, false);
        objectHandler.addObject(sparx1);
        movementHandler.addMovement(new SparxMovement(sparx1, mainPath.getStart(), 1));
    }

    public void tick() {
        objectHandler.tick();
        movementHandler.tick();
        tileHandler.tick();

        // System.out.printf("head: %d, %d\n", this.mainPath.head.tile.getX(),
        // this.mainPath.head.tile.getY());
    }

    public void render(Graphics g) {
        objectHandler.render(g);
        hud.render(g);
    }

}
