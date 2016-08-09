package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Missile {
	public Missile(int x, int y, boolean isGood, Tank.Direction dir, TankClient tc) {
		this.x = x;
		this.y = y;
		this.isGood = isGood;
		this.dir = dir;
		this.tc = tc;
	}

	public void draw(Graphics g) {		
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);

		move();
	}

	private void move() {
		switch (dir) {
		case LEFT:
			x -= XSPEED;
			break;
		case RIGHT:
			x += XSPEED;
			break;
		case UP:
			y -= YSPEED;
			break;
		case DOWN:
			y += YSPEED;
			break;
		case LEFTUP:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case LEFTDOWN:
			x -= XSPEED;
			y += YSPEED;
			break;
		case RIGHTUP:
			x += XSPEED;
			y -= YSPEED;
			break;
		case RIGHTDOWN:
			x += XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}

		if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGTH) {
			isLive = false;
		}
	}
	
	public boolean hitTank(Tank t) {
		if (this.isLive && this.getRect().intersects(t.getRect()) && t.isLive() && this.isGood != t.isGood()) {
			t.setLive(false);
			this.isLive = false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for (int i=0; i<tanks.size(); i++) {
			if (hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	private int x;
	private int y;
	private TankClient tc;
	private boolean isLive = true;
	private boolean isGood;

	public boolean isLive() {
		return isLive;
	}

	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;

	private Tank.Direction dir;
}
