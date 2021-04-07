import java.util.LinkedList;

public class MovementHandler {

    private LinkedList<Movement> movement = new LinkedList<Movement>();

    public void tick() {
        for (int i = 0; i < movement.size(); i++) {
            Movement tempMovement = movement.get(i);
            tempMovement.tick();
        }
    }

    public void addMovement(Movement movement) {
        this.movement.add(movement);
    }

    public void removeMovement(Movement movement){
        this.movement.remove(movement);
    }
    
}
