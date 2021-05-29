import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Font;
import javax.swing.JButton;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

public class Screen extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{
	int coins;
	Ball b1;
	int ballX = 150;
	int ballY = 750;

	boolean moving = false;
	boolean done = false;
	int startX;
	int startY;
	int endX;
	int endY;
	double angle;
	double magnitude;

	Enemy[] enemyArray;
	Background bg;

	int level;
	int score;
	Obsticles ob;
	int ballsLeft;

	Color graphite;
	int type = 1;
	Boss boss;
	BufferedWriter writer;

	boolean drawShop = false;
	
	public Screen()
	{	
		setLayout(null);

		BufferedReader reader = null;

		try {
		    File file = new File("data.txt");
		    reader = new BufferedReader(new FileReader(file));

		    String line;
		    while ((line = reader.readLine()) != null) {
		        coins = Integer.parseInt(line);
		        System.out.println(coins);
		    }
		    reader.close();

		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

		boss = new Boss();
		graphite = new Color(62,62,62);
		
		ballsLeft = 10;
		ob = new Obsticles();
		level = 1;
		score = 0;
		bg = new Background();
		enemyArray = new Enemy[4];
		for (int i = 0 ; i<enemyArray.length ; i++ ) {
			enemyArray[i] = new Enemy();
		}
		b1 = new Ball(ballX, ballY);
		//b1.setVelocity(80, 80);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
	}
	
	public Dimension getPreferredSize(){
		//Sets the size of the panel
        return new Dimension(1600,900);
	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		// start.setVisible(false);
		bg.draw(g, level);
		ob.draw(g);

		//Draw the ball
		b1.drawMe( g, ballsLeft);
		
		if(level !=4){
			for (int i = 0; i<enemyArray.length ; i++ )
				enemyArray[i].draw(g);
		}
		
		boss.draw(g, level);
		

		g.setColor(Color.white);
		g.setFont(new Font("Impact", Font.PLAIN, 20));
		g.drawString("Score: " + score, 10, 30);
		g.drawString("level: " + level, 10, 50);
		g.drawString("Balls Left:" + ballsLeft, 20, 840);
		g.drawString("Coins: " + coins, 10, 90);
		
		if( type == 1){
			g.setColor(graphite);
			g.fillRect(0,0,1600,900);
			g.setColor(Color.orange);
			g.fillRect(400, 700, 800, 100);
			g.setFont(new Font("Impact", Font.PLAIN, 80));
			g.setColor(Color.white);
			g.drawString("Click to Start", 600, 780);
			g.drawString("Press S", 1100, 150);
			g.drawString("to access shop", 950, 230); 
			g.drawString("Castle Takeover", 300, 150);
			g.drawString("Press R to reset SaveData",395, 600);
		}
		if( type == 2){
			g.setColor(graphite);
			g.fillRect(0,0,1600,900);
			g.setColor(Color.red);
			g.fillRect(400, 700, 800, 100);
			g.setColor(Color.white);
			g.setFont(new Font("Impact", Font.PLAIN, 150));
			g.drawString("YOU RAN OUT OF BALLS", 200, 300);
			g.setFont(new Font("Impact", Font.PLAIN, 80));
			g.setColor(Color.white);
			g.drawString("Click to Restart", 600, 780);

			writeData();
		}
		if(drawShop){
			b1.drawShop(g);
		}
			
	}
	public void writeData(){
		try{
			    File file = new File("data.txt");

			    writer = new BufferedWriter(new FileWriter(file));
			    writer.write("" + coins);
			    writer.flush();

		   	}catch(IOException e){
						e.printStackTrace();
			}finally {
			    try {
					writer.close();
				}catch(IOException e){
					e.printStackTrace();
				}
				
		}
	}
	public void keyPressed(KeyEvent e){
		System.out.println("KeyCode:" + e.getKeyCode());
		if(e.getKeyCode() == 80){
			if(level < 4){
				System.out.println("why");
				level++;
			}
			else if(level == 4)
				level = 1;
			 b1.reset();
			
			//b1 = new Ball(ballX, ballY);
			ob = new Obsticles();
			ballsLeft = 10;
			score = 0;
			for (int i = 0 ; i<enemyArray.length ; i++ ) {
				enemyArray[i].reset();
			}
		}
		if(e.getKeyCode() == 27){
			writeData();
			b1.writeUnlocks();
			System.exit(1);
		}
		if(e.getKeyCode() == 83 && type == 0){
			drawShop = !drawShop;
		}
		if(e.getKeyCode() == 82 && type == 1){
			b1.resetAccess();
			coins = 0;
			writeData();
		}
		if(e.getKeyCode() == 81 && level == 4)
			boss.takeDamage();
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}


	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		if (moving == false && ballsLeft > 0) {
			if ((e.getX() >= ballX && e.getX() <= ballX + 20) && (e.getY() >= ballY && e.getY() <= ballY + 20)) {
				b1.setTrailVisible(true);
				moving = true;
				startX = ballX+10;
				startY = ballY+10;
				done = true;
				endX = e.getX();
				endY = e.getY();
			}
		}
		if(e.getX() >= 400 && e.getX() <= 1200 && e.getY() >=700 && e.getY() <= 800){
			if(type == 1)
				type = 0;
			if(type == 2){
				type = 0;
				ballsLeft = 10;
				ob = new Obsticles();
				level = 1;
				score = 0;
				bg = new Background();
				//enemyArray = new Enemy[4];
				for (int i = 0 ; i<enemyArray.length ; i++ )
					enemyArray[i].reset();
				b1 = new Ball(ballX, ballY);
			}
		}
		for (int i = 0; i< 14 ; i++ ) {
			if(e.getX() >= (50 + (30 * i) - 3) && e.getX() <= (50 + (30 * i) - 3) + 28 && e.getY() >= 48 && e.getY() <= 138)
				coins = b1.purchaseBall(i, coins);
		}
	}
	public void mouseReleased(MouseEvent e){
		if(done && ballsLeft > 0){
			b1.fire();
			b1.setTrailVisible(false);
			done = false;	
		}
	}
	public void mouseDragged(MouseEvent e){
		if (done && ballsLeft > 0) {	
			endX = e.getX();
			endY = e.getY();
			b1.dragPosition(e.getX(), e.getY());
			double deltaX = endX - startX;
			double deltaY = endY - startY;
			angle = (Math.toDegrees(Math.atan2(deltaY , deltaX))* -1) + 180;
			magnitude = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
			b1.setVelocity(angle, magnitude);
		}
	}
	public void mouseMoved(MouseEvent e){}
	
	public void animate(){

		while( true ){
			//wait for .1 second
			try {
			    Thread.sleep(10);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
			if(done == false){
				
				if(b1.getX() <= -20 || b1.getX() >= 1620 || b1.getY() >= 920){ //Reset if ball is offscreen
					if(moving)
						ballsLeft--;
					moving = false;
					b1.reset();
					System.out.println("offscreen");
					//b1 = new Ball(ballX, ballY);
					
					if(ballsLeft == 0)
						b1.setVisible(false);
						
				}

				if( level != 4 && (b1.getX() + b1.getSize() >= ob.getX() && b1.getY() + b1.getSize() >= ob.getY()  && b1.getX() <= ob.getX() + 50)){ // reset if ball hits wall
					if(moving)
						ballsLeft--;
					moving = false;
					b1.reset();
					
					//b1 = new Ball(ballX, ballY);
					System.out.println("wall");
					
					if(ballsLeft == 0)
						b1.setVisible(false);
				}

				if(boss.checkCollision(b1, done)){ // reset if ball hits boss
					ballsLeft--;
					
					if(moving)
							score++;
					moving = false;
					b1.reset();
					if(ballsLeft == 0)
						b1.setVisible(false);
				}
				b1.move();
			}
			if(score == 4 && level < 4){ // resets level when progressing to next level
				System.out.println("whyx2");
				 b1.reset();
				
				//b1 = new Ball(ballX, ballY);
				ob = new Obsticles();
				ballsLeft = 10;
				
				score = 0;
				level++;
				if(level == 4)
					ballsLeft = 15;
				for (int i = 0 ; i<enemyArray.length ; i++ )
					enemyArray[i].reset();
				
			}
			
			
			
			if(level != 4){
				for (int i = 0; i<enemyArray.length ; i++ ) {

					if(enemyArray[i].checkCollision(b1, done)){
						score++;
						coins++;
						moving = false;
						 b1.reset();
						System.out.println("enemyArray");
						//b1 = new Ball(ballX, ballY);
						ballsLeft--;
						if(ballsLeft == 0)
							b1.setVisible(false);
					}
				}
			}
			if(ballsLeft == 0 && score < 4){
					type = 2;
			}
			boss.move();
			repaint();
			
		}
	}
}
