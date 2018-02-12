import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;

public class Tile extends GameObject{

    private BufferedImage floorImage;
    private BufferedImage wallImage;

    public Tile(int x, int y, ID id, SpriteSheet ss){
        super(x, y, id, ss);
        floorImage = ss.grabImage(6, 1, 20, 20);
        wallImage = ss.grabImage(3, 1, 20, 20);
    }

    public void tick(){

    }

    public void render(Graphics g){
        if(this.id == ID.Wall){
            g.drawImage(wallImage, x, y, null);
        }
        if(this.id == ID.Floor){
            g.drawImage(floorImage, x, y, null);
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, 20, 20);
    }
}