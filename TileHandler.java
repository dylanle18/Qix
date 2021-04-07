import java.util.LinkedList;

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
        Tile qixTile = null;
        for (int i = 0; i < Game.GRIDSIZE; i++) {
            for (int j = 0; j < Game.GRIDSIZE; j++) {
                Tile aTile = Grid.map[i][j];
                // turns all push tiles to path tiles
                if (isPush(aTile)) {
                    aTile.setTileID(TileID.PATH);
                }
                // claims everything
                if (isEmpty(aTile)) {
                    aTile.setTileID(TileID.CLAIM);
                }
                //sets the qix tile
                if (aTile.getHasQix()) {
                    qixTile = aTile;
                }
            }
        }
        //unclaims the land with the qix in it
        unclaim(qixTile);
    }

    public void unclaim(Tile qixTile) {
        if (qixTile == null) {
            return;
        }

        LinkedList<Tile> queue = new LinkedList<>();

        queue.add(qixTile);

        while (queue.size() != 0) {
            Tile tile = queue.poll();
            int row = tile.getRow();
            int col = tile.getCol();
        
            if (tile.getTileID() != TileID.CLAIM) {
                continue;
            }
            
            tile.setTileID(TileID.EMPTY);
            
            queue.add(Grid.getTile(row + 1, col));
            queue.add(Grid.getTile(row - 1, col));
            queue.add(Grid.getTile(row, col + 1));
            queue.add(Grid.getTile(row, col - 1));
        }

        // qixTile.setTileID(TileID.EMPTY);

        // int row = qixTile.getRow();
        // int col = qixTile.getCol();

        // if (Grid.getTile(row + 1, col).getTileID() == TileID.CLAIM) {
        //     unclaim(Grid.getTile(row + 1, col));
        // }
        // if (Grid.getTile(row - 1, col).getTileID() == TileID.CLAIM) {
        //     unclaim(Grid.getTile(row - 1, col));
        // }
        // if (Grid.getTile(row, col + 1).getTileID() == TileID.CLAIM) {
        //     unclaim(Grid.getTile(row, col + 1));
        // }
        // if (Grid.getTile(row, col - 1).getTileID() == TileID.CLAIM) {
        //     unclaim(Grid.getTile(row, col - 1));
        // }
    }

    public boolean isPush(Tile tile) {
        return tile.getTileID() == TileID.PUSH;
    }

    public boolean isEmpty(Tile tile) {
        return tile.getTileID() == TileID.EMPTY;
    }
}
