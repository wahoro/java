package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
	}

	int x;
	int y;
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 200;
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.setColor(c);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
}
