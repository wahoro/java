package tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;


public class TankClient extends Frame {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGTH = 600;

	Image offscreen = null;
	
	Tank myTank = new Tank(50, 50, true, this);
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> tanks = new ArrayList<Tank>();

	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.drawString("missiled count: " + missiles.size(), 10, 40);
		g.drawString("explode count: " + explodes.size(), 10, 55);
		g.drawString("tank count: " + tanks.size(), 10, 70);
		g.setColor(c);
		
		//»­±¬Õ¨Ð§¹û
		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			if (!e.isLive()) {
				explodes.remove(e);
			} else {
				e.draw(g);
			}
		}
		
		//»­×Óµ¯
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			if (!m.isLive()) {
				missiles.remove(m);
			} else {
				m.hitTanks(tanks);
				m.draw(g);
			}
		}
		
		// »­µÐ·½Ì¹¿Ë
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (!t.isLive()) {
				tanks.remove(t);
			} else {
				t.draw(g);
			}
		}
		
		myTank.draw(g);
	}
	
	public void update(Graphics g) {
		if (offscreen == null) {
			offscreen = this.createImage(GAME_WIDTH, GAME_HEIGTH);
		}
		Graphics gOffScreen = offscreen.getGraphics();
	    Color c = gOffScreen.getColor();
	    gOffScreen.setColor(Color.WHITE);
	    gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGTH);
	    g.setColor(c);
		paint(gOffScreen);
		g.drawImage(offscreen, 0, 0, null);
	}

	private static final long serialVersionUID = 1L;

	public void LanchFrame() {
		for (int i = 0; i < 10; i++) {
			tanks.add(new Tank(50 + 40 * (i + 1), 50, false, this));
		}
		
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HEIGTH);
		this.setResizable(false);
		this.setTitle("tank war");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setVisible(true);

		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.LanchFrame();
	}

	// ÖØ»­´°¿Ú
	private class PaintThread implements Runnable {
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}
	
	//¼àÌý¼üÅÌ°´¼ü
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.KeyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.KeyPressed(e);
		}
		
	}
}
