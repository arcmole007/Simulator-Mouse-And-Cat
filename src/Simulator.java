import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;

public class Simulator extends Canvas implements Runnable {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 630;

    private Thread thread;
    private boolean running = false;

    private Handler handler;
    Random r = new Random();

    private SpriteSheet ss;
    private BufferedImage spriteSheet = null;
    public int[][] maze;

    public int noOfCheese = 20;
    public int spawnCheese = 0;
    public int spawnCat = 0;
    public int noOfCat = 2;
    public int transform = 0;
    Mouse mouse;

    ArrayList<CoordinateSaver> coorSaver = new ArrayList<CoordinateSaver>();

    public Simulator() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        new Window(WIDTH, HEIGHT, "OOAD Assignment!", this);

        ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<>();

        //This 3 lines handle the sprite sheet images
        BufferedImageLoader loader = new BufferedImageLoader();
        spriteSheet = loader.loadImage("test2.png");
        ss = new SpriteSheet(spriteSheet);
       
        //Start Handling the maze
        try (BufferedReader br = new BufferedReader(new FileReader("Maze2.txt"))) {
            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                if (currentLine.isEmpty())
                    continue;

                ArrayList<Integer> row = new ArrayList<>();

                String[] values = currentLine.trim().split(" ");

                for (String string : values) {
                    if (!string.isEmpty()) {
                        int id = Integer.parseInt(string);

                        row.add(id);
                    }
                }

                tempLayout.add(row);
            }
        } catch (IOException e) {}

        int width = tempLayout.get(0).size();
        int height =  tempLayout.size();
        

        maze = new int[width][height];

        for(int y = 0; y < height; y++){
            for(int x = 0; x< width; x++){
                maze[y][x] = tempLayout.get(y).get(x);
                if (maze[y][x] == 0) {
                    handler.addObject(new Tile(x * 20, y * 20, ID.Floor, ss));
                    coorSaver.add(new CoordinateSaver(y, x));
                }
                if (maze[y][x] == 1) {
                    handler.addObject(new Tile(x * 20, y * 20, ID.Wall, ss));
                }
            }
        }
        //End of handling maze
        //=============================================
        
        //handling the spawn of the asset
        while (spawnCheese < noOfCheese) {
            int randNO = r.nextInt(coorSaver.size());
            handler.addObject(new Cheese(coorSaver.get(randNO).getCoordinateY() * 20, coorSaver.get(randNO).getCoordinateX() * 20, ID.Cheese, handler, ss));
            spawnCheese++;
        }
        
        while (spawnCat < noOfCat) {
            int randNo = r.nextInt(coorSaver.size());
            handler.addObject(new Cat(coorSaver.get(randNo).getCoordinateY() * 20, coorSaver.get(randNo).getCoordinateX() * 20, ID.Cat, handler, ss));
            spawnCat++;
        }
        handler.addObject(new Mouse(20, 20, ID.Mouse, handler, ss)); 
        
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
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public synchronized void run() {
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
                frames = 0;
            }
        }
        stop();
    }

    public void tick() {
        handler.tick();
       
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);

        g.dispose();
        bs.show();
    }

    /**Method to clamp the object from moving outside the wall */
    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min) {
            return var = min;
        } else {
            return var;
        }
    }

   

    public static void main(String[] args) {
        new Simulator();
    }
}