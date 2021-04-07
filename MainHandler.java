import java.awt.Graphics;

public class MainHandler {
    
    private ObjectHandler objectHandler;
    private MovementHandler movementHandler;
    private TileHandler tileHandler;

    public MainHandler(Player player){
        objectHandler = new ObjectHandler();
        movementHandler = new MovementHandler();
        tileHandler = new TileHandler(player);

        //PLAYER
        objectHandler.addObject(player);
        movementHandler.addMovement(new PlayerMovement(player, tileHandler));

        //QIX
        Qix qix = new Qix(ID.QIX, Grid.getTile(Game.GRIDSIZE / 2, Game.GRIDSIZE / 2));
        objectHandler.addObject(qix);
        movementHandler.addMovement(new QixMovement(qix, 2));
    }

    public void tick() {
        objectHandler.tick();
        movementHandler.tick();
        tileHandler.tick();
    }

    public void render(Graphics g) {
        objectHandler.render(g);
    }

}
