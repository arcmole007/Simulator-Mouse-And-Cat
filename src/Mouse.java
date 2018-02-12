import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Window;
import java.util.Random;



public class Mouse extends GameObject {
    Random r = new Random();
    Handler handler;

    private BufferedImage mouseImage;
    public int cheeseEat = 0;

    public Mouse(int x, int y, ID id, Handler handler,SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        mouseImage = ss.grabImage(1, 1, 20, 20);
       

    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, 20, 20);
    }

    public void tick() {
        move();
       
        collision();
        transform();
    }

    public void move() {
        x += velX;
        y += velY;
    }

  

    public void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Wall){
                if(getBounds().intersects(tempObject.getBounds())){
                   x += velX * -1;
                   y += velY * -1;
                }
            }
            if(tempObject.getId() == ID.Cat){
                if(getBounds().intersects(tempObject.getBounds())){
                  
                    handler.removeObject(this);
                }
            }
            if(tempObject.getId() == ID.Cheese){
                if(getBounds().intersects(tempObject.getBounds())){
                   
                    cheeseEat++;
                   
                }
            }
            
                
        }
    }

    public int getCheeseEat(){
        return cheeseEat;
    }

    public void transform(){
        if(cheeseEat == 4){
     
           for(int i = 0 ; i < handler.object.size();i++){
               GameObject tempObject = handler.object.get(i);
               if(tempObject.getId() == ID.Mouse){
                   System.out.println(tempObject.getX());
                   handler.addObject(new SuperMouse((int)tempObject.getX(), (int)tempObject.getY(), ID.SuperMouse, handler,ss));
               }
           }

           handler.removeObject(this);
      
        }
    }

    public void render(Graphics g) {
        g.drawImage(mouseImage, x, y, null);
    }
}