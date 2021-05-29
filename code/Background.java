import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.net.URL;


public class Background{
	private Image fire;
	private BufferedImage brick;
	private BufferedImage ledge;
	Color redT = new Color(0.4f,0f,0f, 0.2f);
	Color greenT = new Color(0f,0.4f,0f, 0.2f);
	Color blueT = new Color(0f,0f,0.4f, 0.2f);
	Color blackT = new Color(0.4f,0.4f,0.4f, 0.7f);
	int ledgeY = 770;

	public Background(){
		fire = new ImageIcon("Fire.gif").getImage();
        try{
            brick = ImageIO.read(new File("Brick.png"));
            ledge = ImageIO.read(new File("Ledge.png"));
            
        }
        catch(IOException e){
            e.printStackTrace();
        }    
	}
	public void draw(Graphics g, int level){

		for(int ii = 0; ii < 30; ii++)
			for ( int i = 0; i < 32 ; i++ )
				g.drawImage(brick, 50 * i, 31 * ii, null);
		
		
		//g.setColor(ledge);
		//g.fillRect(0,ledgeY, 300, 130);
		if(level == 4)
			g.setColor(blackT);
		else if(level == 3)
			g.setColor(greenT);	
		else if(level == 2)
			g.setColor(blueT);
		else if(level == 1)
			g.setColor(redT);
		g.fillRect(0,0,1600,900);
		g.drawImage(ledge,0, ledgeY, null);
		g.drawImage(fire, 260	, ledgeY - 42, null);
		g.drawImage(fire, 10, ledgeY - 42, null);
	}
}