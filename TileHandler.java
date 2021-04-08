import java.util.LinkedList;

public class TileHandler {

    private Player player;
    private HUD hud;

    public TileHandler(Player player, HUD hud) {
        this.player = player;
        this.hud = hud;

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
        TileID.claimColor = TileID.getRandColor();

        Tile qixTile = null;
        for (int i = 0; i < Game.GRIDSIZE; i++) {
            for (int j = 0; j < Game.GRIDSIZE; j++) {
                Tile tile = Grid.map[i][j];
                // turns all push tiles to path tiles
                if (isPush(tile)) {
                    tile.setTileID(TileID.PATH);
                }
                // claims everything
                if (isEmpty(tile)) {
                    tile.setTileID(TileID.CLAIM);
                }
                // sets the qix tile
                if (tile.getHasQix()) {
                    qixTile = tile;
                }
            }
        }
        // unclaims the land with the qix in it
        unclaim(qixTile);

        hud.setClaimPercent(getClaimPercent());
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
        // unclaim(Grid.getTile(row + 1, col));
        // }
        // if (Grid.getTile(row - 1, col).getTileID() == TileID.CLAIM) {
        // unclaim(Grid.getTile(row - 1, col));
        // }
        // if (Grid.getTile(row, col + 1).getTileID() == TileID.CLAIM) {
        // unclaim(Grid.getTile(row, col + 1));
        // }
        // if (Grid.getTile(row, col - 1).getTileID() == TileID.CLAIM) {
        // unclaim(Grid.getTile(row, col - 1));
        // }
    }

    public int getClaimPercent() {
        return (int) ((Math.pow(Game.GRIDSIZE-2, 2) - countEmpty()) / Math.pow(Game.GRIDSIZE-2, 2) * 100);
    }

    public int countEmpty() {
        int count = 0;
        for (int i = 0; i < Game.GRIDSIZE; i++) {
            for (int j = 0; j < Game.GRIDSIZE; j++) {
                Tile tile = Grid.map[i][j];
                if (isEmpty(tile)) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isPush(Tile tile) {
        return tile.getTileID() == TileID.PUSH;
    }

    public boolean isEmpty(Tile tile) {
        return tile.getTileID() == TileID.EMPTY;
    }
}
