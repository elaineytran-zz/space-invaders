import java.awt.Graphics2D;
import java.awt.Image;


public class Ship extends GameObject {

	
	
	private Image ship = ImageLoader.loadCompatibleImage("sprites/mysteryShip.png");
	private int updateCounter = 0;
	private boolean move = false;
	private int shipMoving = (int)(Math.random()*2000+3000);

	public Ship (int x, int y, int w, int h){
		super(x, y, w, h);
	}


	public void update(){
		updateCounter++;
		if (updateCounter % shipMoving == 0){
			move = true;
		}
		if(move == true)
			getBounds().x += getBounds().width/20;
	}

	public void render(Graphics2D g){

		g.drawImage(ship,
				(int)getBounds().x,
				(int)getBounds().y,
				(int)getBounds().width,
				(int)getBounds().height, 
				null);


	}

}
