package testSpritePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
///////////////////////////////////////////////////////
//import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements KeyListener, MouseListener {

	private Scene scene;
	private boolean threadPause;
	Thread spritesThread = null;
	long threadSleepTime;

	public MainPanel() {
		super();
		scene = new Scene();
		threadPause = false;
		spritesThread = null;
		threadSleepTime = 1;
		setIgnoreRepaint(true);
		/////////////////////////////////////////////////////
		this.addKeyListener(this);
		addMouseListener(this);
		this.setFocusable(true);
		this.setRequestFocusEnabled(true);
		spritesThreadStart();

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	};

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Scene.CANVAS_WIDTH + Scene.EXTRA_WIDTH, Scene.CANVAS_HEIGHT + Scene.EXTRA_WIDTH);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		scene.draw(g2);

	}

	/**
	 * Start the game.
	 */
	public void spritesThreadStart() {
		if (spritesThread != null)
			return;
		// Create a new thread
		spritesThread = new Thread() {
			// Override run() to provide the running behavior of this thread.
			@Override
			public void run() {
				spritesThreadLoop();
			}
		};
		// Start the thread calls run(), which in turn calls gameLoop().
		spritesThread.start();
	}

	/**
	 * Run the game loop here.
	 */
	public void spritesThreadLoop() {
		while (true) {
			// Update the state and position of all the game objects, detect collisions.
			if (!threadPause)
				scene.oneCicle();
			// Refresh the display
			repaint();
			try {
				Thread.sleep(threadSleepTime);
			} catch (InterruptedException e) {

			}

		}
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode(); // keyboard code for the key that was pressed
		switch (key) {
		case KeyEvent.VK_LEFT:
			scene.spr1.setCurrDir(0);
			break;
		case KeyEvent.VK_RIGHT:
			scene.spr1.setCurrDir(1);
			break;
		case KeyEvent.VK_UP:
			scene.spr1.setCurrDir(2);
			break;
		case KeyEvent.VK_DOWN:
			scene.spr1.setCurrDir(3);
			break;
		case KeyEvent.VK_PAGE_DOWN:

			break;
		case KeyEvent.VK_END:

			break;
		case KeyEvent.VK_PAGE_UP:

			break;
		case KeyEvent.VK_HOME:

			break;
		case KeyEvent.VK_SPACE:
			threadPause = !threadPause;
			break;
		case KeyEvent.VK_ENTER:

			break;
		default:
			return;
		}
		if (threadPause)
			this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent evt) {

	}

	@Override
	public void keyTyped(KeyEvent evt) {
	}

}
