/*
    Class that represents a player in the game
*/

import java.awt.*;
import java.awt.geom.*;

public class Player extends GameObject {

    //the image used for the player
    private Image[] images = {
    	ImageLoader.loadCompatibleImage("sprites/player.png"),
    	ImageLoader.loadCompatibleImage("sprites/playerExplosion1.png"),
    	ImageLoader.loadCompatibleImage("sprites/playerExplosion2.png")
    	};
    private int imageNum = 0;
    private int explodeCountdown = -1;
    
    private int shootCountdown = 0;
    
    //keeps track of whether the player is moving to the left
    private boolean movingLeft = false;
    private boolean movingRight = false;
  

	public Player(int x, int y, int w, int h) {
        super(x, y, w, h);
	}

	public void update() {
		
        //if we're moving to the left, decrease the player's boundary x by 1
		if(movingLeft && getBounds().x > 0)
            getBounds().x-=1.5;
		if(movingRight && getBounds().x < 755)
        	getBounds().x+=1.5; 
		
		if(shootCountdown > 0){
			shootCountdown--;
		}
		
		if(explodeCountdown > 0){
			explodeCountdown--;
		}
		if(explodeCountdown == 9)
			imageNum = 2;
      
    }
	
	public void explode(){
		explodeCountdown = 15;
		imageNum = 1;
	}
	
	public boolean isDead(){
		if (explodeCountdown == 0)
			return true;
		return false;
	}
	
	public Laser shoot(){
		if(shootCountdown <= 0){
			shootCountdown = 40;
			Laser l = new Laser((int)getBounds().x + 19, (int)getBounds().y, 5, 20, -1, 4);
			return l; 
		}
		return null;
	}
    
    //tell the player if they should be moving left or right
    public void setMovingLeft(boolean ml) {
        movingLeft = ml;
    }
    
    public void setMovingRight(boolean mr){
    	movingRight = mr;
    }
    
    //draw the player with the player's image
	public void render(Graphics2D g) {

		g.drawImage(images[imageNum],
                    (int)getBounds().x,
                    (int)getBounds().y,
                    (int)getBounds().width,
                    (int)getBounds().height,
                    null);

	}

}