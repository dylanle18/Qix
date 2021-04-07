public class SparxMovement {

    private Sparx sparc;
    private int tickCounter = 0;
    private boolean canMove = false;
    // private int velX = 0, velY;
    private int speed;

    private PathNode node;

    public SparxMovement(Sparx sparc, PathNode node, int speed) {
        this.sparc = sparc;
        this.speed = speed;
        // if (this.sparc.clockwise) {
        // velY = -1;
        // } else {
        // velY = 1;
        // }
        this.node = node;
    }

    public void tick() {
        // handles player speed
        if (!canMove) {
            tickCounter++;
        }
        if (tickCounter == speed) {
            canMove = true;
            tickCounter = 0;
        }
        if (canMove == true) {
            move();
        }
    }

    // Moves sparc if possible and rotates accordingly if not.
    // public void move() {
    // if (!inGrid(sparc.getTile().getRow() + velX, sparc.getTile().getCol() +
    // velY)) {
    // updateVel();
    // }
    // this.step();
    // canMove = false;
    // }

    public void move() {
        this.node = node.next;
        Tile nextTile = node.tile;
        sparc.setTile(nextTile);
        canMove = false;
    }

    // private void updateVel() {
    // if (this.sparc.clockwise) {
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
    // Tile newTile = Grid.getTile(sparc.getTile().getRow() + velX,
    // sparc.getTile().getCol() + velY);
    // sparc.setTile(newTile);
    // }

    // private boolean inGrid(int row, int col) {
    // return row >= 0 && row < Game.GRIDSIZE && col >= 0 && col < Game.GRIDSIZE;
    // }
}
