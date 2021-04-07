public class TileHandler {

    private Player player;

    public TileHandler(Player player) {
        this.player = player;

        for (int i = 0; i < Game.GRIDSIZE; i++) {
            Grid.map[i][0].setTileID(TileID.PATH);
            Grid.map[0][i].setTileID(TileID.PATH);
            Grid.map[i][Game.GRIDSIZE - 1].setTileID(TileID.PATH);
            Grid.map[Game.GRIDSIZE - 1][i].setTileID(TileID.PATH);
        }
    }

    public void tick() {
        Tile playerTile = player.getTile();
        if (playerTile.getTileID() == TileID.EMPTY) {
            playerTile.setTileID(TileID.PUSH);
        }
    }

    public void scan() {
        Tile qixTile = Grid.getTile(0, 0);
        for (int i = 0; i < Game.GRIDSIZE; i++) {
            for (int j = 0; j < Game.GRIDSIZE; j++) {
                Tile aTile = Grid.map[i][j];
                if (isPush(aTile)) {
                    aTile.setTileID(TileID.PATH);
                }
                if (isEmpty(aTile)) {
                    aTile.setTileID(TileID.CLAIM);
                }
                if (aTile.getHasQix()) {
                    qixTile = aTile;
                }
            }
        }
        unclaim(qixTile);
    }

    public void unclaim(Tile tile) {
        tile.setTileID(TileID.EMPTY);

        int row = tile.getRow();
        int col = tile.getCol();
        if (Grid.getTile(row + 1, col).getTileID() == TileID.CLAIM) {
            unclaim(Grid.getTile(row + 1, col));
        }
        if (Grid.getTile(row - 1, col).getTileID() == TileID.CLAIM) {
            unclaim(Grid.getTile(row - 1, col));
        }
        if (Grid.getTile(row, col + 1).getTileID() == TileID.CLAIM) {
            unclaim(Grid.getTile(row, col + 1));
        }
        if (Grid.getTile(row, col - 1).getTileID() == TileID.CLAIM) {
            unclaim(Grid.getTile(row, col - 1));
        }
    }

    public boolean isPush(Tile tile) {
        return tile.getTileID() == TileID.PUSH;
    }

    public boolean isEmpty(Tile tile) {
        return tile.getTileID() == TileID.EMPTY;
    }
}
