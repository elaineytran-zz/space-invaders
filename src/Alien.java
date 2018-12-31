/*
    This class represents a basic alien in the game
 */

import java.awt.*;
import java.awt.geom.*;

public class Alien extends GameObject {

	private Image[] images = {
			ImageLoader.loadCompatibleImage("sprites/alienA1.png"), 
			ImageLoader.loadCompatibleImage("sprites/alienA2.png"),
			ImageLoader.loadCompatibleImage("sprites/alienB1.png"), 
			ImageLoader.loadCompatibleImage("sprites/alienB2.png"),
			ImageLoader.loadCompatibleImage("sprites/alienC1.png"), 
			ImageLoader.loadCompatibleImage("sprites/alienC2.png"),
			ImageLoader.loadCompatibleImage("sprites/explosion.png")
	};
	private int imageNum = 0;

	private int updateCounter = 0;
	private int shootCountdown = 240;

	private boolean movingRight = true;
	private boolean movingLeft = false;
	private int speedFactor = 1;

	private int removeCounter = -1;


	public Alien(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void setImage(int i){
		imageNum = i;
	}
	
	public void setSpeedFactor(int i){
		speedFactor = i;
	}

	public void update() {

		//keep track of how many times this Alien has been updated
		updateCounter++;

		if (updateCounter % 40 == 0 && imageNum % 2 == 0 && imageNum < 6)
			imageNum++;

		else if (updateCounter % 40 == 0 && imageNum % 2 == 1)
			imageNum--;

		//every nth update, move the bounds of the alien half its width to the right
		if (updateCounter % (50/speedFactor) == 0 && movingRight){
			getBounds().x += getBounds().width/2;
		}

		else if (updateCounter % (50/speedFactor) == 0 && movingLeft){
			getBounds().x -= getBounds().width/2;
		}

		if(shootCountdown > 0)
			shootCountdown--;

		if(removeCounter > 0)
			removeCounter--;

	}

	public void explode(){
		removeCounter = 3;
		imageNum = 6;
	}

	public boolean isDead(){
		if (removeCounter == 0)
			return true;

		return false;
	}
	
	public int getPoints(){
		return 10;
	}

	public void setMovingRight(){
		movingRight = true;
		movingLeft = false;
	}

	public void setMovingLeft(){
		movingLeft = true;
		movingRight = false;
	}

	public int getShootCountdown(){
		return shootCountdown;
	}
	
	public void setShootCountdown(int n){
		shootCountdown = n;
	}

	public Laser shoot(){
		if(shootCountdown <= 0){
			shootCountdown = 240;
			Laser l = new Laser((int)getBounds().x + (int)getBounds().width/2, (int)getBounds().y + 10, 5, 15, 1, 0);
			return l;
		}

		return null;
	}


	//draw the image represented by the alien
	public void render(Graphics2D g) {

		g.drawImage(images[imageNum],
				(int)getBounds().x,
				(int)getBounds().y,
				(int)getBounds().width,
				(int)getBounds().height,
				null);



	}

}