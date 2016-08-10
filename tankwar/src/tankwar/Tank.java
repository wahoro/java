package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tank {
	public void draw(Graphics g) {
		if (!isLive) {
			return;
		}

		Color c = g.getColor();
		if (isGood) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLUE);
		}
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);

		switch (ptdir) {
		case LEFT:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT / 2);
			break;
		case RIGHT:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT / 2);
			break;
		case UP:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y);
			break;
		case DOWN:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y + Tank.HEIGHT);
			break;
		case LEFTUP:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
			break;
		case LEFTDOWN:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT);
			break;
		case RIGHTUP:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y);
			break;
		case RIGHTDOWN:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT);
			break;
		case STOP:
			break;
		}

		move();
	}

	public void KeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
			isRight = true;
			break;
		case KeyEvent.VK_LEFT:
			isLeft = true;
			break;
		case KeyEvent.VK_UP:
			isUp = true;
			break;
		case KeyEvent.VK_DOWN:
			isDown = true;
			break;
		}

		locationDirection();
	}

	public void KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
			isRight = false;
			break;
		case KeyEvent.VK_LEFT:
			isLeft = false;
			break;
		case KeyEvent.VK_UP:
			isUp = false;
			break;
		case KeyEvent.VK_DOWN:
			isDown = false;
			break;
		case KeyEvent.VK_X:
			fire();
			break;
		case KeyEvent.VK_C:
			fireAll();
			break;
		case KeyEvent.VK_Z:
			isLive = true;
			break;
		}
		locationDirection();
	}

	// 坦克移动的像素
	private void move() {
		oldx = x;
		oldy = y;
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

		if (this.dir != Direction.STOP) {
			this.ptdir = this.dir;
		}

		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH)
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGTH)
			y = TankClient.GAME_HEIGTH - Tank.HEIGHT;

		// 敌军坦克随机改变方向
		if (!isGood) {
			Direction[] dirs = Direction.values();

			if (step == 0) {
				step = r.nextInt(TOTALSTEP) + 3;
				int rn = r.nextInt(dirs.length);
				this.dir = dirs[rn];
			}

			step--;

			if (r.nextInt(500) > 490) {
				this.fire();
			}
		}

	}

	private void fire() {
		if (!isLive) {
			return;
		}

		int mx = x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int my = y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(mx, my, isGood, ptdir, tc);
		tc.missiles.add(m);
	}

	private void fireAll() {
		if (!isLive) {
			return;
		}
		int mx = x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int my = y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Direction[] dirs = Direction.values();
		for (int i = 0; i < dirs.length - 1; i++) {
			Missile m = new Missile(mx, my, isGood, dirs[i], tc);
			tc.missiles.add(m);
		}
	}

	private void locationDirection() {
		if (!isLeft && isRight && !isUp && !isDown) {
			dir = Direction.RIGHT;
		} else if (isLeft && !isRight && !isUp && !isDown) {
			dir = Direction.LEFT;
		} else if (!isLeft && !isRight && isUp && !isDown) {
			dir = Direction.UP;
		} else if (!isLeft && !isRight && !isUp && isDown) {
			dir = Direction.DOWN;
		} else if (isLeft && !isRight && isUp && !isDown) {
			dir = Direction.LEFTUP;
		} else if (!isLeft && isRight && isUp && !isDown) {
			dir = Direction.RIGHTUP;
		} else if (isLeft && !isRight && !isUp && isDown) {
			dir = Direction.LEFTDOWN;
		} else if (!isLeft && isRight && !isUp && isDown) {
			dir = Direction.RIGHTDOWN;
		} else if (!isLeft && !isRight && !isUp && !isDown) {
			dir = Direction.STOP;
		}
	}

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Tank(int x, int y, boolean isGood) {
		this.x = x;
		this.y = y;
		this.oldx = x;
		this.oldy = y;
		this.isGood = isGood;
	}

	public Tank(int x, int y, boolean isGood, Direction dir, TankClient tc) {
		this(x, y, isGood);
		this.dir = dir;
		this.tc = tc;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public void stay() {
		this.x = oldx;
		this.y = oldy;
	}

	// 记录上一个位置如果撞墙后将坦克坐标还原为上一次的位置
	// 如何设置成stop的话，会导致坦克粘到墙上
	public void hitWall(Wall w) {
		if (this.isLive && this.getRect().intersects(w.getRect())) {
			stay();
		}
	}

	public void collidesTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);

			if (t != this && this.isLive && t.isLive() && this.getRect().intersects(t.getRect())) {
				stay();
				t.stay();
			}
		}

	}

	public boolean isGood() {
		return isGood;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	private int step = r.nextInt(TOTALSTEP) + 3;
	public static final int TOTALSTEP = 20;

	private static Random r = new Random();

	// 设置坦克方向
	enum Direction {
		LEFT, RIGHT, UP, DOWN, LEFTUP, RIGHTUP, LEFTDOWN, RIGHTDOWN, STOP
	};

	public static final int XSPEED = 3;
	public static final int YSPEED = 3;
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;

	private Direction dir = Direction.STOP;
	private boolean isLeft = false;
	private boolean isRight = false;
	private boolean isUp = false;
	private boolean isDown = false;
	int x;
	int y;
	int oldx;
	int oldy;
	TankClient tc;
	private Tank.Direction ptdir = Direction.DOWN;
	private boolean isGood = false;

	private boolean isLive = true;

}
