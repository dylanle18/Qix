public class PathNode {
    public Tile tile;
    public PathNode prev;
    public PathNode next;

    public PathNode(Tile tile, PathNode prev, PathNode next) {
        this.tile = tile;
        this.prev = prev;
        this.next = next;
    }

    public boolean inRotation() {
        Tile nextTile = this.next.tile;
        return Grid.nextToEmpty(nextTile) || Grid.cornerIsNextEmpty(nextTile);
    }

    public void setPrev(PathNode prev) {
        this.prev = prev;
    }

    public void setNext(PathNode next) {
        this.next = next;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
