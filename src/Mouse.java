import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Window;
import java.util.Random;

public class Mouse extends GameObject {
    private Random r = new Random();
    private Handler handler;

    //Image----------------------------
    private BufferedImage mouseImage;
    private BufferedImage superMouseImage;

    //----Movement------------------------
    private int right = 0;
    private int left = 1;
    private int up = 2;
    private int down = 3;

    private int direction = -1;
    private int speed = 4;

    private int counter = 0;
    
    // boolean SuperMouse = false;
    private int dumb = 0;
    private int smart = 1;

    private int state = dumb;
    public int cheeseEat = 0;

    public Mouse(int x, int y, ID id, Handler handler,SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        direction = r.nextInt(4);
        mouseImage = ss.grabImage(1, 1, 20, 20);
        superMouseImage = ss.grabImage(9, 1, 20, 20);
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, 20, 20);
    }

    public void tick() {
        move();
        eat();
        transform();
        die();
        
    }

    public void move() {
        if(this.id == ID.Mouse){
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
        }else if(this.id == ID.SuperMouse){
            if(direction == right){
                if(collision(x+speed, y)){
                    x += speed;
                    counter += speed;
                    System.out.println(counter);
                }
                else{direction = r.nextInt(4);}
           }
           else if(direction == left){
                if(collision(x-speed, y)){
                    x -= speed;
                    counter += speed;
                    System.out.println(counter);
                }
                else{direction = r.nextInt(4);}
           }
           else if(direction == up){
                if(collision(x, y-speed)){
                    y -= speed;
                    counter += speed;
                    System.out.println(counter);
                }
                else{direction = r.nextInt(4);}
           }
           else if(direction == down){
               if(collision(x, y+speed)){
                   y += speed;
                   counter += speed;
                   System.out.println(counter);
                }
               else{direction = r.nextInt(4);}
           }

        }
      
    }


    public boolean collision(int nextX, int nextY){
        Rectangle bounds = new Rectangle(nextX, nextY, 20, 20);
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Wall){
                if(bounds.intersects(tempObject.getBounds())){
                   return false;
                }
            }
        }
        return true;
    }

    public void eat(){
        for(int i = 0;i < handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Cheese){
                if(getBounds().intersects(tempObject.getBounds())){
                    cheeseEat++;
                }
            }
        }
    }
    public void die(){
        for(int i = 0;i < handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Cat){
                if(getBounds().intersects(tempObject.getBounds())){
                    handler.removeObject(this);
                }
            }
        }
    }

    public int getCheeseEat(){
        return cheeseEat;
    }

    public void transform(){
        if(cheeseEat == 4){
            this.id = ID.SuperMouse;
            cheeseEat = 0;
        }
        if(counter == 20){
            this.id = ID.Mouse;
            counter = 0;
        }
    }

    public void render(Graphics g) {
        
        if(this.id == ID.Mouse){
            g.drawImage(mouseImage, x, y, null);
        }
        if(this.id == ID.SuperMouse){
            g.drawImage(superMouseImage, x, y, null);
        }
    }
}