
public class Alien3 extends Alien{
	
	public Alien3(int x, int y, int w, int h) {
		super(x, y, w, h);
		setImage(4);
	}
	
	public int getPoints(){
		return 30;
	}
	
	public Laser shoot(){
		if(getShootCountdown() <= 0){
			setShootCountdown(240);
			Laser l = new Laser((int)getBounds().x + (int)getBounds().width/2, (int)getBounds().y + 10, 7, 25, 1, 2);
			return l;
		}

		return null;
	}
}
