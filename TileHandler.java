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
        for (int i = 0; i < Game.GRIDSIZE; i++) {
            for (int j = 0; j < Game.GRIDSIZE; j++) {
                Tile aTile = Grid.map[i][j];
                if (isPush(aTile)) {
                    aTile.setTileID(TileID.PATH);
                }
            }
        }
    }

    public boolean isPush(Tile tile) {
        return tile.getTileID() == TileID.PUSH;
    }

}
