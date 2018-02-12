import java.awt.image.BufferedImage;

public class Animation{


    private BufferedImage[] frames;
    private int currentFrame;

    private long startTime;
    private long delay;

    public Animation(){

    }

    public void setFrames(BufferedImage[] images){
        frames =  image;
        if(currentFrame >= frames.length){
            currentFrame = 0;
        }
    }


    public void setDelay(long d){
        delay = d;
    }

    public void tick(){
        if(delay == -1){
            return;
        }

        long elapsed = (S)
    }
}