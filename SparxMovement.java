public class SparxMovement extends Movement {

    private Sparx sparx;
    private int drag;

    private int tickCounter = 0;
    private boolean canMove = false;
    private PathNode node;

    private LinkedPath mainPath;

    public SparxMovement(Sparx sparx, PathNode node, LinkedPath mainPath, int drag) {
        this.sparx = sparx;
        this.drag = drag;
        this.node = node;
        this.mainPath = mainPath;
    }

    public void tick() {
        // handles sparx speed
        if (!canMove) {
            tickCounter++;
        }
        if (tickCounter == drag) {
            canMove = true;
            tickCounter = 0;
        }
        if (canMove == true) {
            move();
        }
    }

    public void move() {
        Tile previousTile = this.sparx.getTile();

        if (this.sparx.getTile() == this.mainPath.getNode(this.sparx.getTile(), true).tile) {
            this.node = this.mainPath.getNode(this.sparx.getTile(), true);
        }

        if (this.node.next != null && sparx.clockwise) {
            this.node = node.next;
            Tile nextTile = node.tile;
            sparx.setTile(nextTile);
        } else if (this.node.prev != null && !sparx.clockwise) {
            this.node = node.prev;
            Tile nextTile = node.tile;
            sparx.setTile(nextTile);
        }
        canMove = false;

        previousTile.setHasSparx(false);
        this.sparx.getTile().setHasSparx(true);
    }
}
