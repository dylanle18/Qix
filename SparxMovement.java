public class SparxMovement extends Movement {

    private Sparx sparx;
    private int drag;

    private int tickCounter = 0;
    private boolean canMove = false;
    private PathNode node;
    // private int velX = 0, velY;
    // private int velX, velY;

    final int[] ABOVE = { -1, 0 };
    final int[] BELOW = { 1, 0 };
    final int[] LEFT = { 0, -1 };
    final int[] RIGHT = { 0, 1 };

    public SparxMovement(Sparx sparx, PathNode node, int drag) {
        this.sparx = sparx;
        this.drag = drag;
        this.node = node;
        // if (this.sparx.clockwise) {
        // velY = -1;
        // } else {
        // velY = 1;
        // }
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

    public void move() { // Assume clockwise
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
    }

    // // Moves sparx if possible and rotates accordingly if not.
    // public void move() {
    // if (!inGrid(sparx.getTile().getRow() + velX, sparx.getTile().getCol() +
    // velY)) {
    // updateVel();
    // }
    // this.step();
    // canMove = false;
    // }

    // private void updateVel() {
    // if (this.sparx.clockwise) {
    // if (velX == 0 && velY == -1) {
    // velX = -1;
    // velY = 0;
    // } else if (velX == 0 && velY == 1) {
    // velX = 1;
    // velY = 0;
    // } else if (velX == -1 && velY == 0) {
    // velX = 0;
    // velY = 1;
    // } else if (velX == 1 && velY == 0) {
    // velX = 0;
    // velY = -1;
    // }
    // } else {
    // if (velX == 0 && velY == -1) {
    // velX = 1;
    // velY = 0;
    // } else if (velX == 0 && velY == 1) {
    // velX = -1;
    // velY = 0;
    // } else if (velX == -1 && velY == 0) {
    // velX = 0;
    // velY = -1;
    // } else if (velX == 1 && velY == 0) {
    // velX = 0;
    // velY = 1;
    // }
    // }
    // }

    // private void step() {
    // Tile newTile = Grid.getTile(sparx.getTile().getRow() + velX,
    // sparx.getTile().getCol() + velY);
    // sparx.setTile(newTile);
    // }

    // private boolean inGrid(int row, int col) {
    // return row >= 0 && row < Game.GRIDSIZE && col >= 0 && col < Game.GRIDSIZE;
    // }

    // private boolean isPath(int xVal, int yVal) {
    // // System.out.printf("G: %d,%d|C: %d,%d\n", Game.GRIDSIZE, Game.GRIDSIZE,
    // xVal,
    // // yVal);
    // Tile tile = Grid.getTile(xVal, yVal);
    // if (tile.getTileID() == TileID.PATH) {
    // return true;
    // } else {
    // return false;
    // }
    // }
}
