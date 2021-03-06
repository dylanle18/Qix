import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -1442798787354930462L;

    public static final int WIDTH = 1000, HEIGHT = 690, GRIDSIZE = 64, TILESIZE = 10;
    private Thread thread;
    private boolean running = false;

    // private MainHandler mainHandler;

    // private Grid grid;
    // private Player player;

    // private PlayerInput playerInput;

    public enum STATE {
        MENU, GAME, CREDITS
    };

    private Menu menu = new Menu();
    private CreditsScreen credits = new CreditsScreen();
    private MouseInput mouseInput = new MouseInput();;

    public static STATE state = STATE.MENU;

    private boolean gameStarted = false;
    private boolean creditsStarted = false;
    private Level level;

    public Game() {
        // grid = new Grid(5, 5, TILESIZE, GRIDSIZE, GRIDSIZE);
        // player = new Player(ID.PLAYER, Grid.getTile(GRIDSIZE - 1, GRIDSIZE / 2));

        // mainHandler = new MainHandler(player);
        // playerInput = new PlayerInput(player);
        level = new Level(this);
        // this.addKeyListener(playerInput);
        this.addMouseListener(mouseInput);
        new Window(WIDTH, HEIGHT, "QIX", this);
    }

    private void resetGame() {
        level = new Level(this);
    }

    private void tick() {
        if (state == STATE.GAME) {
            // mainHandler.tick();
            // playerInput.tick();
            level.tick();
        }
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

        if (state == STATE.GAME) {
            // grid.render(g);
            // mainHandler.render(g);
            this.gameStarted = true;
            level.render(g);

        } else if (state == STATE.MENU) {
            if (this.gameStarted) {
                this.resetGame();
                this.gameStarted = false;

            } else if (creditsStarted) {
                creditsStarted = false;
            }
            menu.render(g);

        } else if (state == STATE.CREDITS) {
            credits.render(g);
            creditsStarted = true;
        }

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
        // int frames = 0;
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
            // frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                // System.out.println("FPS: " + frames);
                // frames = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        new Game();
    }

}
