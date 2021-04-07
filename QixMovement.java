import java.util.Random;

public class QixMovement extends Movement {

    private Qix qix;
    private int drag;

    private int tickCounter = 0;
    private int tickCounter2 = 0;

    private boolean canMove = false;
    private Random rand = new Random();

    public QixMovement(Qix qix, int drag) {
        this.qix = qix;
        this.drag = drag;
    }

    public void tick() {
        velRandomizer();

        // handles qix speed
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
        Tile tile = qix.getTile();
        int newRow = tile.getRow() + qix.getVelY();
        int newCol = tile.getCol() + qix.getVelX();

        if (inGrid(newRow, newCol)) {
            Tile newTile = Grid.getTile(newRow, newCol);

            if (!isPath(newTile)) {
                qix.getTile().setHasQix(false);
                qix.setTile(newTile);
                qix.getTile().setHasQix(true);
            } else {
                qix.setVelX(qix.getVelX() * -1);
                qix.setVelY(qix.getVelY() * -1);
                if (!isPath(newTile)) {
                    qix.getTile().setHasQix(false);
                    qix.setTile(newTile);
                    qix.getTile().setHasQix(true);
                }
            }
        }

        canMove = false;
    }

    private void velRandomizer() {
        tickCounter2++;
        if (tickCounter2 == 64) {
            tickCounter2 = 0;
            switch (rand.nextInt(7)) {
            case 0:
                qix.setVelX(1);
                qix.setVelY(0);
                break;
            case 1:
                qix.setVelX(-1);
                qix.setVelY(0);
                break;
            case 2:
                qix.setVelX(0);
                qix.setVelY(1);
                break;
            case 3:
                qix.setVelX(0);
                qix.setVelY(-1);
                break;
            case 4:
                qix.setVelX(1);
                qix.setVelY(1);
                break;
            case 5:
                qix.setVelX(-1);
                qix.setVelY(-1);
                break;
            case 6:
                qix.setVelX(1);
                qix.setVelY(-1);
                break;
            default:
                qix.setVelX(-1);
                qix.setVelY(1);
            }
        }
    }

    // checks if a tile is in the grid given row and col
    private boolean inGrid(int row, int col) {
        return row >= 0 && row < Game.GRIDSIZE && col >= 0 && col < Game.GRIDSIZE;
    }

    // checks if the given tile is a path tile
    private boolean isPath(Tile tile) {
        return tile.getTileID() == TileID.PATH;
    }
}
