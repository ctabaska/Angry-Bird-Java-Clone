import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Obsticles{
	
	int randX;
	int randY;
	private BufferedImage wall;
	public Obsticles(){
		randX = (int)(Math.random()*100 + 500);
		randY = (int)(Math.random()*100 + 400);
		try{
            wall = ImageIO.read(new File("Wall.png"));
            
        }
        catch(IOException e){
            e.printStackTrace();
        }  
	}	
	public void draw(Graphics g){
		g.drawImage(wall, randX, randY, null);
	}
	public int getX(){
		return randX;
	}
	public int getY(){
		return randY;
	}
}