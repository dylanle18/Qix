import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -1442798787354930462L;

    public static final int WIDTH = 1000, HEIGHT = 800, GRIDSIZE = 64, TILESIZE = 10;
    private Thread thread;
    private boolean running = false;

    private Handler handler;
    private Grid grid;
    private Player player;

    private PlayerInput playerInput;
    private TileHandler tileHandler;
    private Movement movement;

    //Nabil
    private Sparx sparc1;
    private Sparx sparc2;
    private SparxMovement sparcMovement1;
    private SparxMovement sparcMovement2;
    //

    public Game() {
        handler = new Handler();
        grid = new Grid(5, 5, TILESIZE, GRIDSIZE, GRIDSIZE);
        player = new Player(ID.PLAYER, Grid.getTile(GRIDSIZE - 1, GRIDSIZE / 2));
        
        playerInput = new PlayerInput(player);
        tileHandler = new TileHandler(player);
        movement = new Movement(player, tileHandler);

        //Nabil
        sparc1 = new Sparx(ID.SPARX,Grid.getTile(GRIDSIZE - 1, GRIDSIZE / 2), true);
        sparcMovement1 = new SparxMovement(sparc1, 5);
        handler.addObject(sparc1);

        sparc2 = new Sparx(ID.SPARX,Grid.getTile(GRIDSIZE - 1, GRIDSIZE / 2), false);
        sparcMovement2 = new SparxMovement(sparc2, 1);
        handler.addObject(sparc2);
        //
        
        this.addKeyListener(playerInput);
        new Window(WIDTH, HEIGHT, "GAME", this);
        
        handler.addObject(player);
    }

    private void tick() {
        playerInput.tick();
        handler.tick();
        tileHandler.tick();
        movement.tick();
        sparcMovement1.tick();
        sparcMovement2.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        grid.render(g);
        handler.render(g);

        g.dispose();
        bs.show();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        new Game();
    }

}