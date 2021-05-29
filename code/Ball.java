import java.awt.Graphics;
import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.net.URL;
import java.awt.*;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

public class Ball
{
	private BufferedImage[] ball;
	private Image rainbowBall;
	private BufferedImage coin;
	double x;
	double y;
	int xf;
	int yf;

	double xVel;
	double yVel;
	double initX;
	double initY;
	double time;
	double gravity;

	Color red;
	Timer timer = new Timer();

	int size = 20;
	Color menu;

	boolean visible;
	Color[] trail;
	boolean trailVisible;
	int[] price = {0, 5, 5, 5, 5, 5, 10, 10, 10, 10, 15, 15, 15, 30};
	boolean[] access;
	int inUse;
	boolean oobException = false;

	BufferedWriter writer;
	BufferedReader reader;
	Color brown;
	
	public Ball(double x, double y){

		
		brown = new Color(102, 51, 0);

		inUse = 0;
		access = new boolean[14];
		access[0] = true;
		for (int i = 1; i<access.length ; i++ ) {
			access[i] = false;
		}
		rainbowBall = new ImageIcon("rainbowBall.gif").getImage();
		ball = new BufferedImage[13];
		try{
            ball[1] = ImageIO.read(new File("blueBall.png"));
            ball[2] = ImageIO.read(new File("greenBall.png"));
            ball[3] = ImageIO.read(new File("lightblueBall.png"));
            ball[4] = ImageIO.read(new File("purpleBall.png"));
            ball[5] = ImageIO.read(new File("yellowBall.png"));

            ball[6] = ImageIO.read(new File("tennisBall.png"));
            ball[7] = ImageIO.read(new File("basketBall.png"));
            ball[8] = ImageIO.read(new File("baseBall.png"));
            ball[9] = ImageIO.read(new File("soccerBall.png"));

            ball[10] = ImageIO.read(new File("earthBall.png"));
            ball[11] = ImageIO.read(new File("eightBall.png"));
            ball[12] = ImageIO.read(new File("yingyangBall.png"));

            ball[0] = ImageIO.read(new File("redBall.png"));

            coin = ImageIO.read(new File("coin.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        menu = new Color(0.2f, 0.2f, 0.2f, 0.8f);
		visible = true;
		trailVisible = false;
		this.x = x;
		this.y = y;

		this.xVel = 0;
		this.yVel = 0;
		this.initX = x;
		this.initY = y;
		this.time = 0;
		this.gravity = -9.81;
		
		red = new Color(255,0,0);	 
		trail = new Color[20];
		for (int i = 0; i < trail.length ; i ++) {
			trail[i] = new Color(0f,0f,0f,0.05f * i);
		}
		 BufferedReader reader = null;

		try {
		    File file = new File("unlock.txt");
		    reader = new BufferedReader(new FileReader(file));

		    String line;
		    int w = 0;
		    while ((line = reader.readLine()) != null) {
		        int check = Integer.parseInt(line);
		        System.out.println(check);
		        if(check == 1){
		        	access[w] = true;
		        	price[w] = 0;
		        }
		        w++;
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
	}

	public void move(){
		//x = (xvel)(t) + xO
		x = (xVel)* (time) + initX;
		//y = -0.5(g)(t^2) - (yvel)(t) +yO
		y = (-0.5 * gravity * (Math.pow(time, 2))) - (yVel)*(time) + initY;
		timer.runTimer();
		time = timer.getTime();
	}

	public void setVelocity(double angle, double magnitude){
		xVel = magnitude * Math.cos( Math.toRadians(angle));
		yVel = magnitude * Math.sin( Math.toRadians(angle));
	}
	
	public void drawMe(Graphics g, int ballsLeft){
		g.setColor(brown);
		g.fillRect(180, 750, 5, 20);
		g.fillRect(140, 750, 5, 20);
		if (visible) {
			if(trailVisible){
				for (int i = 1; i <= trail.length ; i++ ) {
					g.setColor(trail[trail.length - i]);
					xf = (int)(Math.round((xVel)* (i/2.0) + initX)) + 10 -3;
					yf = (int)(Math.round((-0.5 * gravity * (Math.pow(i/2.0, 2))) - (yVel)*(i/2.0) + initY)) +10 -3;
					g.fillOval(xf, yf,6,6);

				}
				
			}
				
			
			// g.setColor(red);
	  //   	g.fillOval((int)(Math.round(x)), (int)(Math.round(y)), size, size);
			if(inUse !=13 && oobException == false)
	    		g.drawImage(ball[inUse], (int)(Math.round(x)), (int)(Math.round(y)), null);
	    	else if(oobException)
	    		g.drawImage(rainbowBall, (int)(Math.round(x)), (int)(Math.round(y)), null);
		}
		for ( int i = 1; i<ballsLeft ; i++ ) {
			g.setColor(Color.black);
	    	g.fillOval( 10 +(15 * i) - 1, 850, size, size);
	    	g.setColor(red);
	    	if(inUse !=13 && oobException == false)
	    		g.drawImage(ball[inUse], 10 +(15 * i), 850, null);
	    	else if(oobException)
	    		g.drawImage(rainbowBall, 10 +(15 * i), 850, null);
	    	
		}
		if(trailVisible == false){
			g.setColor(Color.white);
			g.drawLine(142, 752, 183, 752);
		}
		if(trailVisible){
			g.setColor(Color.white);
			g.drawLine(142, 752, (int)(Math.round(x))+10, (int)(Math.round(y))+10);
			g.drawLine(183, 752, (int)(Math.round(x))+10, (int)(Math.round(y))+10);	
		}
	}
	public void setVisible(boolean set){
		visible = set;
	}
	public boolean getVisible(){
		return visible;
	}
	public int getX(){
		return (int)(Math.round(x));
	}
	public int getY(){
		return (int)(Math.round(y));
	}
	public int getSize(){
		return size;
	}
	public void dragPosition(int x, int y){
		this.x = (int)(x - 10);
		this.y = (int)(y - 10);
	}
	public void fire(){
		timer.startTimer(0);
	}
	public void setTrailVisible(boolean vis){
		trailVisible = vis;
	}
	public void reset(){
		timer.stopTimer();
		visible = true;
	}
	public void drawShop(Graphics g){
		for (int i = 0 ; i< ball.length ; i++ ) {
			g.setColor(menu);
			g.fillRect(50 + (30 * i) - 3, 48,  28, 90 );
			g.drawImage(ball[i], 50 + (30 * i), 50, null);
			g.drawImage(coin, 50 + (30 * i) + 3, 50 + 30, null);
			g.setColor(Color.orange);
			g.drawString("" + price[i], 50 + (30 * i), 50 + 80);
			
			
		}
		g.setColor(menu);
		g.fillRect(50 + (30 * 13) - 3, 48,  28, 90 );
		g.drawImage(rainbowBall, 50 + (30 * ball.length), 50, null);
		g.drawImage(coin, 50 + (30 * ball.length) + 3, 50 + 30, null);
		g.setColor(Color.orange);
		g.drawString("" + price[13], 50 + (30 * (price.length -1)), 50 + 80);
	}
	public int purchaseBall(int i, int coins){
		if(coins >= price[i]){
			access[i] = true;
			if(i != 13){
				oobException = false;
				inUse = i;
			}
			if(i == 13)
				oobException = true;
			int left = coins - price[i];
			price[i] = 0;
			return left;
		}
		return coins;
	}
	public void writeUnlocks(){
		try{
			    File file = new File("unlock.txt");

			    writer = new BufferedWriter(new FileWriter(file));
			    for (int i = 0; i <access.length ; i++ ) {
			    	if(access[i] == true)
			    		writer.write("1");
			    	else
			    		writer.write("0");
			    	writer.newLine();
			    }
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
	public void resetAccess(){
		for (int i = 1; i<access.length ;i++ ) {
			access[i] = false;
		}
		price[1] = 5;
		price[2] = 5;
		price[3] = 5;
		price[4] = 5;
		price[5] = 5;
		price[6] = 10;
		price[7] = 10;
		price[8] = 10;
		price[9] = 10;
		price[10] = 15;
		price[11] = 15;
		price[12] = 15;
		price[13] = 30;
	}
}
