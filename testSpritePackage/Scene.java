package testSpritePackage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import spritePackage.DirectedSprite;

public class Scene {
	public static final int CANVAS_WIDTH = 600;
	public static final int CANVAS_HEIGHT = 500;
	public final static int EXTRA_WIDTH = 200;
	protected DirectedSprite spr1;

	public Scene() {
		int[] dir = { 0, 1, 2, 3, 4, 5, 6, 7 };
		int rows = 8;
		int cols = 8;
		String path = "pic/walkMan.png";
		BufferedImage[][] picts = getTiels(path, rows, cols, dir);
		// System.out.print(picts);
		spr1 = new DirectedSprite(picts);
		spr1.setAnimSpeed(150);
		spr1.setMotionSpeed(50);
		spr1.setVelocity(2);

	}

	private static BufferedImage[][] getTiels(String path, int rows, int columns, int[] dir) {
		BufferedImage bigImage = null;
		try {
			bigImage = ImageIO.read(new File(path));
		} catch (IOException e) {
		}
		int imageHeight = bigImage.getHeight() / rows;
		int imageWidth = bigImage.getWidth() / columns;
		BufferedImage[][] spriteImages = new BufferedImage[rows][columns];
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < columns; col++)
				spriteImages[row][col] = bigImage.getSubimage(col * imageWidth, dir[row] * imageHeight, imageWidth,
						imageHeight);
		return spriteImages;
	}

	public void draw(Graphics2D g2) {
		spr1.draw(g2);

	}

	public void oneCicle() {
		spr1.oneCircle();

	}

}
