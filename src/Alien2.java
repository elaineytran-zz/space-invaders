import java.awt.Image;


public class Alien2 extends Alien{
	
	public Alien2(int x, int y, int w, int h) {
		super(x, y, w, h);
		setImage(2);
	}
	
	public int getPoints(){
		return 20;
	}
}
