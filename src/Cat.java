import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Cat extends GameObject {

    Handler handler;
    private BufferedImage catImage;
    private int dumb = 0;
    private int smart = 1;

    private int state = dumb;

    private int right = 0;
    private int left = 1;
    private int up = 2;
    private int down = 3;

    private int direction = -1;
    private int counter = 0;

    private int speed = 1;

    public Random r = new Random();

    public Cat(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        direction = r.nextInt(4);
        counter = 4;
        catImage = ss.grabImage(2, 1, 20, 20);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }

    public void tick() {
        move();
    }

    public void move() {
       if(state == dumb){
           if(direction == right){
                if(collision(x+speed, y)){x += speed;}
                else{direction = r.nextInt(4);}
           }
           else if(direction == left){
                if(collision(x-speed, y)){x -= speed;}
                else{direction = r.nextInt(4);}
           }
           else if(direction == up){
                if(collision(x, y-speed)){y -= speed;}
                else{direction = r.nextInt(4);}
           }
           else if(direction == down){
               if(collision(x, y+speed)){y += speed;}
               else{direction = r.nextInt(4);}
           } 
        }else if(state == smart){}
    }

    public boolean collision(int nextX, int nextY) {
        Rectangle bounds = new Rectangle(nextX, nextY, 20, 20);
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Mouse) {
                if (bounds.intersects(tempObject.getBounds())) {
                    return false;
                }
            }
            if (tempObject.getId() == ID.Wall) {
                if (bounds.intersects(tempObject.getBounds())) {
                    return false;
                }
            }
        }

        return true;
    }

    public void render(Graphics g) {
        g.drawImage(catImage, x, y, null);

    }
}