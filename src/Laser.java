import java.awt.*;
import java.awt.geom.*;

public class Laser extends GameObject{
	
	private int laserNum;
	private Image[] lasers = {
			ImageLoader.loadCompatibleImage("sprites/alienStraightLaser1.png"),
			ImageLoader.loadCompatibleImage("sprites/alienStraightLaser2.png"),
			ImageLoader.loadCompatibleImage("sprites/alienWiggleLaser1.png"),
			ImageLoader.loadCompatibleImage("sprites/alienWiggleLaser2.png"),
			ImageLoader.loadCompatibleImage("sprites/playerLaser.png")};
	int updateCounter = 0;
	private int direction;
	private double speed = 5;
	
	
	public Laser (int x, int y, int w, int h, int d, int l){
		super(x, y, w, h);
		direction = d;
		laserNum = l;
	}
	
	
	public void update(){
		updateCounter++;
		getBounds().y += direction * speed;
		
		
		if(direction == -1){
			speed = 20;
		}
		
		if(updateCounter % 10 == 0 && laserNum % 2 == 0 && laserNum < 4)
			laserNum++;
		else if(updateCounter % 10 == 0 && laserNum % 2 == 1)
			laserNum--;
		
		
	
	}
	
	public int getDirection(){
		return direction;
	}
	
	public void render(Graphics2D g){

		g.drawImage(lasers[laserNum],
				(int)getBounds().x,
				(int)getBounds().y,
				(int)getBounds().width,
				(int)getBounds().height, 
				null);


	}
	


}
