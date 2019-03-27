package spritePackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class DirectedSprite extends MovableSprite {
	public static final int NO_DIRECTION = -1;
	public static final int WEST = 0;
	public static final int EAST = 1;
	public static final int NORTH = 2;
	public static final int SOUTH = 3;
	public static final int SOUTH_WEST = 4;
	public static final int SOUTH_EAST = 5;
	public static final int NORTH_WEST = 6;
	public static final int NORTH_EAST = 7;
	public static final double COS_45 = Math.cos(Math.PI / 4);
	public static final double[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0,-1}, {0, 1}, 
	{-COS_45, COS_45},{COS_45, COS_45},{-COS_45, -COS_45},{COS_45, -COS_45}};
	/////////////////////////////////////////////////////////////////////////////
	protected BufferedImage[][] spriteImages;
	protected boolean[][][][] spriteMaps;
	protected int currDir;
	// upper-left point 
	protected Point2D refPoint;
	protected double width;
	protected double height;
	/////////////////////////////////////////////////////////////////////
	public DirectedSprite(BufferedImage[][] spriteImages) {
		this.spriteImages = spriteImages;
		currDir = WEST;
		currFrame = 0;
		animClock = 0;
		animSpeed = 1;
		motionClock = 0;
		motionSpeed = 1;
		numFrames = spriteImages[0].length;
		if (numFrames == 0) {
			currFrame = -1;
			animSpeed = -1;
		}
		velocity = 1;
		
		int spriteImageWidth = spriteImages[0][0].getWidth();
		int spriteImageHeight = spriteImages[0][0].getHeight();
		width = spriteImageWidth;
		height = spriteImageHeight;
		refPoint = new Point2D(0, 0);
		//////////////////////////////////////////////////////
		boolean[][] map;
		int[] bitArr;
		spriteMaps = new boolean[spriteImages.length][spriteImages[0].length][][];
		for (int i = 0; i < spriteImages.length; i++) {
			
			for (int j = 0; j < numFrames; j++) {
				
				map = new boolean[spriteImageHeight][spriteImageWidth];
				bitArr = spriteImages[i][j].getRGB(0, 0, spriteImageWidth, spriteImageHeight, null, 0, spriteImageWidth);
				for (int row = 0; row < spriteImageHeight; row++) {
					for (int col = 0; col < spriteImageWidth; col++) {
						map[row][col] = (bitArr[row * spriteImageWidth + col] & 0xFF000000) != 0;
						spriteMaps[i][j] = map;
						
					}
				
				}
			}
			
		}
	}
	/**
	 * Copy Constructor
	 * Constructs a new DirectedSprite, initialized to match the values of the specified DirectedSprite.
	 * @param spr the DirectedSprite from which to copy initial values to a newly constructed DirectedSprite
	 */
	public DirectedSprite(DirectedSprite spr) {
		this.currDir = spr.currDir;
		this.currFrame = spr.currFrame;
		this.animClock = spr.animClock;
		this.animSpeed = spr.animSpeed;
		this.motionClock = spr.motionClock;
		this.motionSpeed = spr.motionSpeed;
		this.numFrames = spr.numFrames;
		this.velocity = spr.velocity;
		this.width = spr.width;
		this.height = spr.height;
		this.refPoint = new Point2D(spr.refPoint);
		////////The same references!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		this.spriteImages = spr.spriteImages;
		this.spriteMaps = spr.spriteMaps;
	}
	
	public int getCurrDir() {
		return currDir;
	}
	public boolean setCurrDir(int dir) {
		if (dir < 0 || dir >= spriteImages.length)
			return false;
		currDir = dir;
		return true;
	}
	// Override functions **************************************************
	@Override
	public boolean contains(int x, int y) {
		Rectangle r = getBounds();
		if (!r.contains(x, y))
			return false;
		int spriteImageWidth = spriteImages[0][0].getWidth();
		int spriteImageHeight = spriteImages[0][0].getHeight();
		double aspectX = spriteImageWidth / width;
		double aspectY = spriteImageHeight / height;
		int colSource = (int)(aspectX *(x - refPoint.x));
    	int rowSource = (int)(aspectX *(y - refPoint.y));
    	if (rowSource < 0 || rowSource >= spriteImageHeight)
    		return false;
    	if (colSource < 0 || colSource >= spriteImageWidth)
    		return false;
    	return spriteMaps[currDir][currFrame][rowSource][colSource];
	}
	@Override
	public Point2D getCenter() {
		return new Point2D(refPoint.x + 0.5 * width, refPoint.x + 0.5 * height);
	}
	@Override
	public void draw (Graphics2D g) {
		if (currDir == NO_DIRECTION || currFrame < 0)
			return;
		Rectangle r = getBounds();
		g.drawImage(spriteImages[currDir][currFrame], r.x, r.y, r.width, r.height, null);
		
	}
	@Override
	public void move() {
		double dX = DIRECTIONS[currDir][0] * velocity;
		double dY = DIRECTIONS[currDir][1] * velocity; 
		refPoint.x += dX;
		refPoint.y += dY;
		
	}
	@Override
	public void setLocation(double xLoc, double yLoc) {
		refPoint.x = xLoc;
		refPoint.y = yLoc;
	}
	@Override
	public Rectangle getBounds() {
		int x = (int)Math.floor(refPoint.x + 0.5);
		int y = (int)Math.floor(refPoint.y + 0.5);
		int w = (int)(width + 0.5);
		int h = (int)(height + 0.5);
		return new Rectangle(x, y, w, h);
	}
	@Override
	public boolean[][] getFrameMap(Rectangle rect){
		int spriteImageWidth = spriteImages[0][0].getWidth();
		int spriteImageHeight = spriteImages[0][0].getHeight();
		double aspectX =  spriteImageWidth / width ;
		double aspectY =  spriteImageHeight /  height;
		int rowSource, colSource;
		double dX = rect.x - refPoint.x;
		double dY = rect.y - refPoint.y;
		boolean[][] currMap = new boolean[rect.height][rect.width]; 
		for (int iX = 0; iX < rect.width; iX++)
            for (int jY = 0; jY < rect.height; jY++){
            	
            	colSource = (int)(aspectX * (dX + iX));
            	rowSource = (int)(aspectY * (dY + jY));
            	if (rowSource < 0 || rowSource >= spriteImageHeight)
            		continue;
            	if (colSource < 0 || colSource >= spriteImageWidth)
            		continue;
            	
            	currMap[jY][iX] = spriteMaps[currDir][currFrame][rowSource][colSource];
    
            }
		return currMap;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
	// How to check if a point is inside a rectangle?
}
