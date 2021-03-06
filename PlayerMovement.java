import java.util.LinkedList;

public class PlayerMovement extends Movement {

    private Player player;
    private TileHandler tileHandler;

    private int tickCounter = 0;
    private boolean canMove = false;

    public LinkedPath mainPath;

    public PlayerMovement(Player player, TileHandler tileHandler, LinkedPath mainPath) {
        this.player = player;
        this.tileHandler = tileHandler;
        this.mainPath = mainPath;
        this.startedPushing = false;
        this.pushingPath = new LinkedList<Tile>();
    }

    public void tick() {
        // handles player speed
        if (!canMove) {
            tickCounter++;
        }
        if (tickCounter == 4) {
            canMove = true;
            tickCounter = 0;
        }
        if (canMove == true) {
            move();
        }
    }

    public boolean startedPushing;
    public Tile startingPushTile = null;
    public LinkedList<Tile> pushingPath;

    public Tile getNextTile() {
        return Grid.getTile(this.player.getTile().getRow() + player.getVelY(), this.player.getTile().getCol() + player.getVelX());
    }

    public void move() {
        Tile tile = player.getTile();
        int newRow = tile.getRow() + player.getVelY();
        int newCol = tile.getCol() + player.getVelX();

        if (inGrid(newRow, newCol)) {
            Tile newTile = Grid.getTile(newRow, newCol);

            // if the player is moving to a new path tile
            if (isPath(newTile)) {
                if ((startingPushTile == null) || (startingPushTile != newTile))
                    player.setTile(newTile);
                // if the player is pressing space or currently pushing
            } else if (pressingPush() && !isClaim(newTile) && !isDeadPush(newTile)

                    || pushing() && !isClaim(newTile) && !isDeadPush(newTile)) {
                if (!startedPushing) {
                    startedPushing = true;
                    startingPushTile = this.player.getTile();
                    // System.out.printf("%d,%d\n", this.player.getTile().getCol(),
                    // this.player.getTile().getRow());
                }

                // makes sure the player cant walk backwards or go beside an existing push
                if (!isPush(newTile)) {
                    player.setTile(newTile);
                    newTile.setHasPush(true);
                    this.pushingPath.add(newTile);
                }
            }

            if (!isPath(tile) && isPath(newTile) && newTile != startingPushTile) {
                tileHandler.scan();
                mainPath.updatePath();
                startedPushing = false;

                for (Tile pushedTile : this.pushingPath) {
                    pushedTile.setHasPush(false);
                }
                this.pushingPath = new LinkedList<Tile>();

                // very hacky lol
                for (int i = 0; i < Game.GRIDSIZE; i++) {
                    for (int j = 0; j < Game.GRIDSIZE; j++) {
                        Tile t = Grid.map[i][j];
                        if (isPath(t)) {
                            t.setTileID(TileID.DEADPATH);
                        }
                    }
                }
                PathNode node = mainPath.getStart();
                do {
                    node.tile.setTileID(TileID.PATH);
                    node = node.next;
                } while (node != mainPath.getStart());

                this.startingPushTile = null;
            }
        }

        canMove = false;
    }

    private boolean isDeadPush(Tile tile) {
        return tile.getTileID() == TileID.DEADPATH;
    }

    // checks if the given tile is a claim tile
    private boolean isClaim(Tile tile) {
        return tile.getTileID() == TileID.CLAIM;
    }

    // checks if a tile is in the grid given row and col
    private boolean inGrid(int row, int col) {
        return row >= 0 && row < Game.GRIDSIZE && col >= 0 && col < Game.GRIDSIZE;
    }

    // checks if the given tile is a path tile
    private boolean isPath(Tile tile) {
        return tile.getTileID() == TileID.PATH;
    }

    // checks if the player is pressing push (spacebar)
    private boolean pressingPush() {
        return player.push;
    }

    // checks if the player currently in a push
    private boolean pushing() {
        return player.getTile().getTileID() == TileID.PUSH;
    }

    // checks if the given tile is a path tiles
    private boolean isPush(Tile tile) {
        return tile.getTileID() == TileID.PUSH;
    }

    // checks if the new tile is beside an existing push
    private boolean adjacentPush(Tile curTile, Tile nextTile) {
        // Boundry exmaple for when player is moving up
        // P = Player, t = boundry tiles
        //
        // t1 t2 t3
        // t4 t5
        // P
        //
        int curR = curTile.getRow();
        int curC = curTile.getCol();
        int nextR = nextTile.getRow();
        int nextC = nextTile.getCol();

        int r = nextR - curR;
        int c = nextC - curC;

        Tile t1, t2, t3, t4, t5;

        if (r == -1 || r == 1) {
            t1 = Grid.getTile(nextR + r, nextC - 1);
            t2 = Grid.getTile(nextR + r, nextC);
            t3 = Grid.getTile(nextR + r, nextC + 1);
            t4 = Grid.getTile(nextR, nextC - 1);
            t5 = Grid.getTile(nextR, nextC + 1);

            if (t1 == null || t2 == null || t3 == null || t4 == null || t5 == null) {
                return true;
            }
            if (isPush(t1) || isPush(t2) || isPush(t3) || isPush(t4) || isPush(t5)) {
                return true;
            }
        }

        if (c == -1 || c == 1) {
            t1 = Grid.getTile(nextR - 1, nextC + c);
            t2 = Grid.getTile(nextR, nextC + c);
            t3 = Grid.getTile(nextR + 1, nextC + c);
            t4 = Grid.getTile(nextR - 1, nextC);
            t5 = Grid.getTile(nextR + 1, nextC);

            if (t1 == null || t2 == null || t3 == null || t4 == null || t5 == null) {
                return true;
            }
            if (isPush(t1) || isPush(t2) || isPush(t3) || isPush(t4) || isPush(t5)) {
                return true;
            }
        }

        return false;
    }

}
