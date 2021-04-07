public class LinkedPath {
    private PathNode start;
    private PathNode end;
    public PathNode head;
    public int size;

    public LinkedPath() {
        this.start = null;
        this.end = null;
        this.head = null;
        this.size = 0;

        this.mapGrid();
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
        size += 1;
        if (start == null) {
            node.setPrev(node);
            node.setNext(node);
            start = node;
            end = node;
            head = node;
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
        size += 1;
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

    public void display() {
        if (size == 0) {
            System.out.println("Empty");
        } else if (size == 1) {
            System.out.println("(" + start.tile.getRow() + ", " + start.tile.getCol() + ")");
        } else {
            System.out.print("(" + start.tile.getRow() + ", " + start.tile.getCol() + ")");
            PathNode node = start.next;
            while (start.tile != node.tile) {
                System.out.print(" <=> (" + node.tile.getRow() + ", " + node.tile.getCol() + ")");
                node = node.next;
            }
            System.out.println("\n");
        }
    }

    public PathNode getStart() {
        return this.start;
    }
}
