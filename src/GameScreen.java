/*
    Screen implementation that models a game
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class GameScreen extends Screen
{
	private int score;
	private int highScore;
	Sound laserSound = new Sound("sprites/laser sound.wav");
	Sound gameOver = new Sound("sprites/game over.wav");
	Sound powerUp = new Sound("sprites/power up.wav");

	//variables that represent the different GameObjects in the game
	private ArrayList<Alien> aliens;
	private ArrayList<Laser> lasers;
	private Image[] backgrounds = {
			ImageLoader.loadCompatibleImage("sprites/space1_01.gif"),
			ImageLoader.loadCompatibleImage("sprites/space1_02.gif"),
			ImageLoader.loadCompatibleImage("sprites/space1_03.gif")
	};
	private int[] backgroundPos = {0, 600, 1200};

	private Player player;
	private Ship ship;

	private int updateCounter = 0;
	private int moveDownTracker = 0;
	private int speedChangeTracker = 0;
	private int explodeTracker = 0;
	private int pierceShootCount = 0;
	private boolean pierceLaser = false;
	private int laserMass = 400;

	//this class inherits the following final variables (so you can't change them!)
	//
	//  GameState state;    //variable that lets you switch to another screen
	//int width, height;  //the width and height of this screen


	public GameScreen(GameState s, int w, int h) {
		super(s, w, h);
		initGame();
	}

	//initialize our variables before the next game begins
	public void initGame() {
		score = 0;

		aliens = new ArrayList<Alien>();
		lasers = new ArrayList<Laser>();

		player = new Player(width/2 - 23, height - 24, 45, 24);
		ship = new Ship(-80, 50, 60, 35);

		for(int i = 20; i <= 420; i+=50){
			aliens.add(new Alien3(i, 50, 37, 23));
			for(int k = 100; k <= 150; k+=50)
				aliens.add(new Alien2(i, k, 37, 25));
			for(int l = 200; l <= 250; l+=50)
				aliens.add(new Alien(i, l, 37, 25));
		}



	}

	//render all the game objects in the game
	public void render(Graphics2D g) {
		g.drawImage(backgrounds[2], 0, backgroundPos[2], 800, 600, null);
		g.drawImage(backgrounds[1], 0, backgroundPos[1], 800, 600, null);
		g.drawImage(backgrounds[0], 0, backgroundPos[0], 800, 600, null);

		g.setFont(new Font("Courier New", Font.BOLD, 25));
		g.setColor(Color.WHITE);
		g.drawString("S C O R E <" + score + ">", 15, 30);
		g.drawString("H I G H - S C O R E <" + highScore + ">", 350, 30);


		for(Alien a: aliens)
			a.render(g);
		for(Laser l: lasers)
			l.render(g);
		player.render(g);
		ship.render(g);

	}



	//update all the game objects in the game
	public void update() {
		
		if(aliens.size() == 0){
			gameOver();
		}
		
		moveDownTracker = 0;
		updateCounter++;
		for(int i = aliens.size() - 1; i >= 0; i--) {
			Alien a = aliens.get(i);
			if (a.getBounds().x < 20){
				moveDownTracker++;
				changeRight();
			}
			if (a.getBounds().x > 750){
				moveDownTracker++;
				changeLeft();
			}
			if (a.getBounds().y > 350){
				speedChangeTracker++;
				changeSpeed();
				laserMass = 200;
			}
			a.update();
			if(a.isDead())
				aliens.remove(i);

			Laser las = null;

			if(0 == (int)(Math.random()*laserMass))
				las = a.shoot();

			if(las != null){
				lasers.add(las);
			}
		}
		
		if(pierceShootCount > 2)
			pierceLaser = false;

		for(Laser l: lasers){
			l.update();
			if(l.getBounds().y <= 0 && l.getBounds().y >= -19 && pierceLaser)
				pierceShootCount++;
		}
		
		for(int i = lasers.size()-1; i >= 0; i--){
			if(lasers.get(i).intersects(player) && lasers.get(i).getDirection() == 1){
				gameOver.play();
				player.explode();
			}
			
			if(lasers.get(i).intersects(ship) && lasers.get(i).getDirection() == -1){
				pierceLaser = true;
				powerUp.play();
			}
			

			for(int j = aliens.size()-1; j >= 0; j--){

				//Explodes when aliens reach bottom
				if(aliens.get(j).getBounds().y >= 580 && explodeTracker == 0){
					explodeTracker++;
					gameOver.play();
					player.explode();
				}

				if(aliens.get(j).intersects(lasers.get(i)) && lasers.get(i).getDirection() == -1){
					score += aliens.get(j).getPoints();
					if(score > highScore)
						highScore = score;
					if(pierceLaser == false)
						lasers.remove(i);
					aliens.get(j).explode();
					break;
				}


			}
		}

		for(int i = 0; i <= 2; i++){
			if(backgroundPos[i] <= -600)
				backgroundPos[i] = 1200;
			backgroundPos[i]-=2;
		}

		player.update();
		if(player.isDead())
			gameOver();

		ship.update();


	}


	public void changeRight(){
		if(moveDownTracker == 1){
			for(Alien a: aliens){
				a.setMovingRight();
				a.getBounds().y += 0.8;
			}
		}
	}

	public void changeLeft(){
		if(moveDownTracker == 1){
			for(Alien a: aliens){
				a.setMovingLeft();
				a.getBounds().y += 0.8;
			}
		}
	}

	public void changeSpeed(){
		if(speedChangeTracker == 1){
			for(Alien a: aliens){
				a.setSpeedFactor(3);
			}
		}   
	}

	//handles key press events
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_Q)
			state.switchToWelcomeScreen();
		else if(code == KeyEvent.VK_LEFT)
			player.setMovingLeft(true);
		else if(code == KeyEvent.VK_RIGHT)
			player.setMovingRight(true);
		else if(code == KeyEvent.VK_SPACE){
			laserSound.play();
			Laser l = player.shoot();
			if(l != null)
				lasers.add(l);
		}
	}

	//handles key released events
	public void keyReleased(KeyEvent e)
	{
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_LEFT)
			player.setMovingLeft(false);
		else if(code == KeyEvent.VK_RIGHT)
			player.setMovingRight(false);
	}

	//should be called when the game is over
	public void gameOver() {
		//sets up the next game
		initGame();

		//switch to the game over screen
		state.switchToGameOverScreen();
	}

	//implement these methods if your player can use the mouse
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