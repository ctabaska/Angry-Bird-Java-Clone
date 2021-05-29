import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.*;

public class Sequences extends JPanel implements MouseListener, MouseMotionListener{
	Color graphite;
	int type = 1;
	public Sequences(){
		graphite = new Color(62,62,62);
        addMouseListener(this);
        addMouseMotionListener(this);
	}
	public void draw(Graphics g){
		if( type == 1){
			g.setColor(graphite);
			g.fillRect(0,0,1600,900);
			g.setColor(Color.red);
			g.fillRect(400, 700, 800, 100);
		}
	}
	public void mouseClicked(MouseEvent e){
		System.out.println("click");
		if(e.getX() >= 400 && e.getX() <= 1200 && e.getY() >= 700 && e.getY() <= 800){
			type = 0;
			System.out.println("click1");
		}
	}
	public void mouseEntered(MouseEvent e){
		System.out.println("enter");
	}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		System.out.println("press");
	}
	public void mouseReleased(MouseEvent e){
		System.out.println("release");
	}
	public void mouseDragged(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
	public int getType(){
		return type;
	}
}