package tankwar;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
	}

	int x;
	int y;
	private boolean isLive = true;
	int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};
	int step = 0;
	
	public void draw(Graphics g) {
		if (!isLive) return;
		
		if (step == diameter.length) {
			isLive = false;
			step = 0;
		}
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step++;
	}

	public boolean isLive() {
		return isLive;
	}
}
