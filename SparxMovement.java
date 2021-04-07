public class SparxMovement extends Movement {

    private Sparx sparx;
    private int drag;
    
    private int tickCounter = 0;
    private boolean canMove = false;
    private int velX = 0, velY;

    public SparxMovement(Sparx sparx, int drag) {
        this.sparx = sparx;
        this.drag = drag;
        if (this.sparx.clockwise) {
            velY = -1;
        } else {
            velY = 1;
        }
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

    // Moves sparx if possible and rotates accordingly if not.
    public void move() {
        if (!inGrid(sparx.getTile().getRow() + velX, sparx.getTile().getCol() + velY)) {
            updateVel();
        }
        this.step();
        canMove = false;
    }

    private void updateVel() {
        if (this.sparx.clockwise) {
            if (velX == 0 && velY == -1) {
                velX = -1;
                velY = 0;
            } else if (velX == 0 && velY == 1) {
                velX = 1;
                velY = 0;
            } else if (velX == -1 && velY == 0) {
                velX = 0;
                velY = 1;
            } else if (velX == 1 && velY == 0) {
                velX = 0;
                velY = -1;
            }
        } else {
            if (velX == 0 && velY == -1) {
                velX = 1;
                velY = 0;
            } else if (velX == 0 && velY == 1) {
                velX = -1;
                velY = 0;
            } else if (velX == -1 && velY == 0) {
                velX = 0;
                velY = -1;
            } else if (velX == 1 && velY == 0) {
                velX = 0;
                velY = 1;
            }
        }
    }

    private void step() {
        Tile newTile = Grid.getTile(sparx.getTile().getRow() + velX, sparx.getTile().getCol() + velY);
        sparx.setTile(newTile);
    }

    private boolean inGrid(int row, int col) {
        return row >= 0 && row < Game.GRIDSIZE && col >= 0 && col < Game.GRIDSIZE;
    }
}
