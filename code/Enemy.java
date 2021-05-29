import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Enemy{
	int x;
	int y;
	int width = 50;
	int height = 50;

	boolean visible;
	private BufferedImage knight;
	Color step = new Color(80, 80, 80);
	public Enemy(){
		x = (int)(Math.random()*700 + 850);
		y = (int)(Math.random()*700 + 100);
		visible = true;
		try{
            knight = ImageIO.read(new File("Knight.png"));
            
        }
        catch(IOException e){
            e.printStackTrace();
        }  
	}
	public void draw(Graphics g){
		if (visible) {
			g.setColor(Color.orange);
			g.drawImage(knight, x, y, null);
			//g.drawRect(850, 350, 700 + 50, 500 + 50);
		}
		g.setColor(step);
		g.fillRect(x - 20, y + height + 1, 40 + width, 10 );
		
	}	
	public void setVisible(boolean set){
		visible = set;
	}
	public boolean getVisible(){
		return visible;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public boolean checkCollision(Ball b1, boolean drag){
		if (b1.getVisible() == true && visible == true) {
			if(( b1.getX()+b1.getSize() >= x && b1.getX() <= x + width  && b1.getY()+b1.getSize() >= y && b1.getY() <= y + height) && drag == false ){
				visible = false;
				return true;
			}
		}
		return false;
	}
	public void reset(){
		x = (int)(Math.random()*700 + 850);
		y = (int)(Math.random()*700 + 100);
		visible = true;
	}
}