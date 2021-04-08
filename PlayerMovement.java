public class PlayerMovement extends Movement {

    private Player player;
    private TileHandler tileHandler;

    private int tickCounter = 0;
    private boolean canMove = false;

    private PathNode pushStart = null;
    private PathNode pushEnd = null;
    private PathNode originalNext = null;

    public LinkedPath mainPath;
    public LinkedPath pushPath = null;

    public PlayerMovement(Player player, TileHandler tileHandler, LinkedPath mainPath) {
        this.player = player;
        this.tileHandler = tileHandler;
        this.mainPath = mainPath;
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

    public void move() {
        Tile tile = player.getTile();
        int newRow = tile.getRow() + player.getVelY();
        int newCol = tile.getCol() + player.getVelX();

        if (inGrid(newRow, newCol)) {
            Tile newTile = Grid.getTile(newRow, newCol);

            // if the player is moving to a new path tile
            if (isPath(newTile)) {
                player.setTile(newTile);
                // if the player is pressing space or currently pushing
            } else if (pressingPush() && !isClaim(newTile) || pushing() && !isClaim(newTile)) {
                // makes sure the player cant walk backwards or go beside an existing push
                if (!isPush(newTile) && !adjacentPush(tile, newTile)) {

                    // Strictly moving to start a push
                    if (isPath(tile) && !isPath(newTile)) {
                        // keep track of the path tiles
                        pushStart = mainPath.getNode(tile, true);
                        originalNext = new PathNode(newTile, null, null);
                        pushPath = new LinkedPath(originalNext);
                    } else {
                        pushPath.addAfter(tile, newTile);
                    }
                    // build sparx path with pushing player
                    player.setTile(newTile);
                }
            }

            if (!isPath(tile) && isPath(newTile)) {
                pushEnd = mainPath.getNode(newTile, true);
                PathNode currentNode = pushPath.getNode(tile, true);

                tileHandler.scan();
                // revisit start and end to close path in correct direction
                if (pushEnd.inRotation()) {
                    currentNode.next = pushEnd;
                    pushEnd.prev = currentNode;

                    pushStart.next = originalNext;
                    originalNext.prev = pushStart;
                    // TODO:
                    // fix
                    // mainPath.setStart(originalNext);
                    // mainPath.setEnd(pushStart);

                } else {
                    pushEnd.next = currentNode;
                    PathNode lastNode = pushEnd;
                    while (currentNode != null) {
                        // Reverse the path
                        currentNode.next = currentNode.prev;
                        currentNode.prev = lastNode;
                        lastNode = currentNode;
                        currentNode = currentNode.next;
                    }
                    lastNode.next = pushStart;
                    pushStart.prev = lastNode;
                    // TODO:
                    // fix
                    // mainPath.setStart(lastNode);
                    // mainPath.setEnd(pushStart);
                }
            }
        }

        canMove = false;
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

            if (isPush(t1) || isPush(t2) || isPush(t3) || isPush(t4) || isPush(t5)) {
                return true;
            }
        }

        return false;
    }

}
