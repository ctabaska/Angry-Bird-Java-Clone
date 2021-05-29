import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Boss{
	int x;
	int y;
	int height = 200;
	int width = 128;
	int move;
	boolean visible;
	private BufferedImage king;
	int bossHealth;
	boolean setVisible;
	public Boss(){
		setVisible = false;
		bossHealth = 5;
		visible = false;
		move = 1;
		try{
            king = ImageIO.read(new File("King.png"));
            
        }
        catch(IOException e){
            e.printStackTrace();
        }  
        x = 1254;
        y = 300;

	}
	public void draw(Graphics g, int level){
		if(level == 4 && setVisible == false)
			visible = true;
		if(visible)
			g.drawImage(king, x, y, null);
		//g.drawRect(x,y,width,height);
	}
	public void move(){
		y += move;
		if(y <= 0){
			move = 1;

		}
		if(y>= 700){
			move = -1;
		}
	}
	public boolean checkCollision(Ball b1, boolean drag){
		if(visible){
			if(( b1.getX()+b1.getSize() >= x && b1.getX() <= x + width  && b1.getY()+b1.getSize() >= y && b1.getY() <= y + height) && drag == false ){
				visible = false;
				takeDamage();
				return true;

			}
		}
		
		return false;
	}
	public void takeDamage(){
		System.out.println("damage");
		bossHealth--;
		if(bossHealth == 0)
			visible = false;
	}
}