/*
    Screen implementation that models a game
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class GameOverScreen extends Screen
{
	private Image[] backgrounds = {
			ImageLoader.loadCompatibleImage("sprites/space1_01.gif"),
			ImageLoader.loadCompatibleImage("sprites/space1_02.gif"),
			ImageLoader.loadCompatibleImage("sprites/space1_03.gif")
	};
	private int[] backgroundPos = {0, 600, 1200};
	private int updateCounter = 0;

	public GameOverScreen(GameState s, int w, int h) {
		super(s, w, h);
	}

	public void render(Graphics2D g) {

		g.drawImage(backgrounds[2], 0, backgroundPos[2], 800, 600, null);
		g.drawImage(backgrounds[1], 0, backgroundPos[1], 800, 600, null);
		g.drawImage(backgrounds[0], 0, backgroundPos[0], 800, 600, null);

		g.setFont(new Font("Agency FB", Font.BOLD, 150));
		g.setColor(Color.WHITE);
		g.drawString("G A M E", 120, 200);
		g.drawString("O V E R", 120, 350);

		if(updateCounter == 5 || updateCounter == 6 || updateCounter == 25 || updateCounter == 26){
			g.setColor(Color.RED);
			g.drawString("G A M E", 115, 205);
			g.drawString("O V E R", 115, 355);
		}
	}

	public void update() {
		updateCounter++;
		for(int i = 0; i <= 2; i++){
			if(backgroundPos[i] <= -600)
				backgroundPos[i] = 1200;
			backgroundPos[i]-=2;
		}

	}

	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_ENTER)
			state.switchToWelcomeScreen();
			updateCounter = 0;
	}

	public void keyReleased(KeyEvent e)
	{
	}
	public void mousePressed(Point2D p)
	{
	}
	public void mouseReleased(Point2D p)
	{
	}
	public void mouseMoved(Point2D p)
	{
	}
	public void mouseDragged(Point2D p)
	{
	}
}