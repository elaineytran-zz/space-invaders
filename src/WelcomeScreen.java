import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class WelcomeScreen extends Screen
{
	//image to draw on the welcome screen
	private static Image img = ImageLoader.loadCompatibleImage("sprites/space-invaders-8b.png");
	private Image alienPoints = ImageLoader.loadCompatibleImage("sprites/point-screen.png");
	private Image[] backgrounds = {
			ImageLoader.loadCompatibleImage("sprites/space1_01.gif"),
			ImageLoader.loadCompatibleImage("sprites/space1_02.gif"),
			ImageLoader.loadCompatibleImage("sprites/space1_03.gif")
	};
	private int[] backgroundPos = {0, 600, 1200};
	private int titleX = 10;
	private boolean moveUp;
	private boolean moveDown;
	private int updateCounter = 0;

	//the position to draw a message
	private int x, y;

	public WelcomeScreen(GameState s, int w, int h) {
		super(s, w, h);
	}

	//draw the welcome screen
	public void render(Graphics2D g) {
		g.drawImage(backgrounds[2], 0, backgroundPos[2], 800, 600, null);
		g.drawImage(backgrounds[1], 0, backgroundPos[1], 800, 600, null);
		g.drawImage(backgrounds[0], 0, backgroundPos[0], 800, 600, null);

		//draw our image in the middle of the screen
		//g.drawImage(img, width/2 - img.getWidth(null)/2, height/2 - img.getHeight(null)/2, null);
		g.drawImage(img, 0, titleX, 800, 600, null);
		g.drawImage(alienPoints, 280, titleX + 600, 200, 150, null);

		//set the drawing font and color
		g.setFont(new Font("Geneva", Font.BOLD, 42));
		g.setColor(Color.RED);

		//draw a message
		g.drawString("Press Enter to Play", x, y);
	}

	//updates the welcome screen
	public void update() {
		updateCounter++;
		if(titleX>=5){
			moveDown = false;
			
			moveUp = true;
		}
		
		else if(titleX <= -225){
			moveUp = false;
			
			moveDown = true;
		}
		
		
		if(moveUp && updateCounter % 2 == 0)
			titleX--;
		else if(moveDown && updateCounter % 2 == 0)
			titleX++;

		for(int i = 0; i <= 2; i++){
			if(backgroundPos[i] <= -600)
				backgroundPos[i] = 1200;
			backgroundPos[i]-=2;
		}


		//move the message 1 pixel down and to the right every update
		x++;
		y++;

		//bring the message back on to the screen if it leaves
		if(x > width)
			x = 0;
		if(y > height)
			y = 0;
	}

	//called when a key is pressed
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_ENTER)
			state.switchToGameScreen();
	}

	public void keyReleased(KeyEvent e) {
	}

	public void mousePressed(Point2D p) {
	}

	public void mouseReleased(Point2D p) {
	}

	public void mouseMoved(Point2D p) {
	}

	public void mouseDragged(Point2D p) {
	}
}
