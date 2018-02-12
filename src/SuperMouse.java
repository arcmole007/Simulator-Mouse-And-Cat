import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
 
public class SuperMouse extends Mouse{

    Handler handler;
    private BufferedImage superMouseImage; 


        super(x, y, id, handler, ss);
        superMouseImage = ss.grabImage(9, 1, 20, 20);
         
    }

    public void tick(){
        move();
        outOfWall();
    }

    public void move() {
        x += velX;
        y += velY;
    }

    public void outOfWall() {
        x = Simulator.clamp(x, 0, Simulator.WIDTH - 45);
        y = Simulator.clamp(y, 0,  Simulator.HEIGHT - 70);
    }

    public void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObjec t = handler.object.get(i);

            if(tempObject.getId() == ID.Wall){
                if(getBounds().intersects(tempObject.getBounds())){
                   x += velX * -1;
                   y += velY * -1;
                }
            }
            if(tempObject.getId() == ID.Cat){
                if(getBounds().intersects(tempObject.getBounds())){
                  
                }
            }
            if(tempObject.getId() == ID.Cheese){
                if(getBounds().intersects(tempObject.getBounds())){
                   
                }
            }      
        }
    }


    public void render(Graphics g){
        g.drawImage(superMouseImage, x, y ,null);
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, 20, 20);
    }
}