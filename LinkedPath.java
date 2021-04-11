import java.util.ArrayList;

public class LinkedPath {
    private PathNode start;
    private PathNode end;
    public int size = 0;

    public LinkedPath() {
        this.start = null;
        this.end = null;

        this.mapGrid();
    }

    public LinkedPath(boolean makeGrid) {
        this.start = null;
        this.end = null;

        if (makeGrid) {
            this.mapGrid();
        }
    }

    public void setStart(PathNode start) {
        this.start = start;
    }

    public void setEnd(PathNode end) {
        this.end = end;
    }

    private void mapGrid() {
        for (int i = 0; i < Game.GRIDSIZE; i++) {
            Tile tile = Grid.getTile(0, i);
            this.addEnd(tile);
        }
        for (int i = 1; i < Game.GRIDSIZE; i++) {
            Tile tile = Grid.getTile(i, Game.GRIDSIZE - 1);
            this.addEnd(tile);
        }
        for (int i = Game.GRIDSIZE - 2; i >= 0; i--) {
            Tile tile = Grid.getTile(Game.GRIDSIZE - 1, i);
            this.addEnd(tile);
        }
        for (int i = Game.GRIDSIZE - 2; i > 0; i--) {
            Tile tile = Grid.getTile(i, 0);
            this.addEnd(tile);
        }

    }

    public void addStart(Tile tile) {
        PathNode node = new PathNode(tile, null, null);
        this.size += 1;
        if (start == null) {
            node.setPrev(node);
            node.setNext(node);
            start = node;
            end = node;
        } else {
            node.setPrev(end);
            end.setNext(node);
            start.setPrev(node);
            node.setNext(start);
            start = node;
        }
    }

    public void addEnd(Tile tile) {
        PathNode node = new PathNode(tile, null, null);
        this.size += 1;
        if (start == null) {
            node.setPrev(node);
            node.setNext(node);
            start = node;
            end = node;
        } else {
            node.setPrev(end);
            end.setNext(node);
            start.setPrev(node);
            node.setNext(start);
            end = node;
        }
    }

    public void addAfter(Tile curTile, Tile newTile) {
        PathNode curNode = this.getNode(curTile, true);

        if (curNode != null) {
            size += 1;
            PathNode nextNode = new PathNode(newTile, curNode, null);
            curNode.next = nextNode;
        }
    }

    public void display() {
        if (size == 0) {
            System.out.println("Empty");
        } else if (size == 1) {
            System.out.println("(" + start.tile.getRow() + ", " + start.tile.getCol() + ")");
        } else {
            System.out.print("(" + start.tile.getRow() + ", " + start.tile.getCol() + ")");
            PathNode node = start.next;
            while (node != null && start.tile != node.tile) {
                System.out.print(" <=> (" + node.tile.getRow() + ", " + node.tile.getCol() + ")");
                node = node.next;
            }
            System.out.println("\n");
        }
    }

    public PathNode getStart() {
        return this.start;
    }

    public PathNode getNode(Tile tile, boolean forwardsSearch) {
        if (tile == this.start.tile) {
            return this.start;
        } else if (tile == this.end.tile) {
            return this.end;
        }

        PathNode curretNode = this.start.next;

        if (forwardsSearch) {
            while (curretNode != null && start.tile != curretNode.tile) {
                if (curretNode.tile != tile && curretNode.next != null) {
                    curretNode = curretNode.next;
                } else {
                    return curretNode;
                }
            }
        } else {
            while (curretNode != null && start.tile != curretNode.tile) {
                if (curretNode.tile != tile && curretNode.prev != null) {
                    curretNode = curretNode.prev;
                } else {
                    return curretNode;
                }
            }
        }
        if (tile == this.end.tile) {
            return curretNode;
        } else {
            return null;
        }
    }

    public void updatePath() {
        // Get the new tiles
        ArrayList<Tile> newPathTiles = new ArrayList<Tile>();
        Tile aTile;
        for (int i = 0; i < Game.GRIDSIZE; i++) {
            for (int j = 0; j < Game.GRIDSIZE; j++) {
                aTile = Grid.map[i][j];
                if (aTile.getTileID() == TileID.PATH) {
                    if (Grid.nextToEmpty(aTile) || Grid.cornerIsNextEmpty(aTile)) {
                        newPathTiles.add(aTile);
                    }
                }
            }
        }

        // Sort the tiles into a path
        ArrayList<Tile> sortedPathTiles = new ArrayList<Tile>();

        sortedPathTiles.add(newPathTiles.remove(0)); // Arbitrary starting point
        Tile nearestTile;

        // Search the newPath tiles until there's none left
        while (newPathTiles.size() > 0) {
            // Find the closest tile to the current tile in sortedPathTiles
            nearestTile = Grid.findNearestTile(sortedPathTiles.get(sortedPathTiles.size() - 1), newPathTiles);

            // Remove from the nearest from newPathTiles and add it to the sortedPathTiles
            sortedPathTiles.add(newPathTiles.remove(newPathTiles.indexOf(nearestTile)));
        }

        // Rebuild the LinkedPath with these new sorted tiles
        LinkedPath newPath = new LinkedPath(false);
        for (Tile tile : sortedPathTiles) {
            newPath.addEnd(tile);
        }

        // Switch to the new path
        this.start = newPath.start;
        this.end = newPath.end;
        // this.display();
    }

}
