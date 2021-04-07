import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -1442798787354930462L;

    public static final int WIDTH = 1000, HEIGHT = 800, GRIDSIZE = 150, TILESIZE = 5;
    private Thread thread;
    private boolean running = false;

    private MainHandler mainHandler;

    private Grid grid;
    private Player player;

    private PlayerInput playerInput;

    public Game() {
        grid = new Grid(5, 5, TILESIZE, GRIDSIZE, GRIDSIZE);
        player = new Player(ID.PLAYER, Grid.getTile(GRIDSIZE - 1, GRIDSIZE / 2));

        mainHandler = new MainHandler(player);
        playerInput = new PlayerInput(player);

        this.addKeyListener(playerInput);
        new Window(WIDTH, HEIGHT, "GAME", this);
    }

    private void tick() {
        mainHandler.tick();
        playerInput.tick();
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
        mainHandler.render(g);

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
                // System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        new Game();
    }

}