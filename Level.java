import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Level {

    private LinkedList<GameObject> object = new LinkedList<GameObject>();
    private LinkedList<Movement> movement = new LinkedList<Movement>();
    private int numOfQix, qixDrag, numOfSparx, sparxDrag;
    private Random rand = new Random();

    public Level(int numOfQix, int qixDrag, int numOfSparx, int sparxDrag) {
        this.numOfQix = numOfQix;
        this.qixDrag = qixDrag;
        this.numOfSparx = numOfSparx;
        this.sparxDrag = sparxDrag;
        startLevel();
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

    public void startLevel() {
        // for scalability, if more then 1 qix is never wanted
        for (int i = 0; i < numOfQix; i++) {
            Qix qix = new Qix(ID.QIX,  Grid.getTile(Game.GRIDSIZE / 2, Game.GRIDSIZE / 2));
            object.add(qix);
            movement.add(new QixMovement(qix, qixDrag));
        }
        // for (int i = 0; i < numOfSparx; i++) {
        //     Sparx sparx = new Sparx(ID.SPARX, Grid.getTile(0, rand.nextInt(Game.GRIDSIZE)), i % 2 == 0);
        //     object.add(sparx);
        //     movement.add(new SparxMovement(sparx, mainPath.getStart(), sparxDrag));
        // }
    }

}
