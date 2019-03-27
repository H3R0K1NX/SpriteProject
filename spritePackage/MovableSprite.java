package spritePackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class MovableSprite {
	protected double velocity;
	protected int animSpeed;
	protected int animClock;
	protected int motionSpeed;
	protected int motionClock;
	//////////////////////////////////////////////////////////////
	protected int currFrame;
	protected int numFrames;
	///////////////////////////////////////////////////////////////
	/**
	 * Draws the current frame of the specified MovableSprite by current location. 
	 * @param g Graphics2D
	 */
	public abstract void draw(Graphics2D g);
	/**
	 * Moves this MovableSprite by speed and current direction.
	 */
	public abstract void move();
	/**
	 * Gets the center of this MovableSprite.
	 * @return
	 */
	public abstract Point2D getCenter();
	/**
	 * Gets the bounding Rectangle of this MovableSprite.
	 * @return bounding Rectangle for this MovableSprite.
	 */
	public abstract Rectangle getBounds();
	/**
	 * Moves this MovableSprite to the specified location
	 * @param xLoc  the X coordinate of the new location
	 * @param yLoc  the Y coordinate of the new location
	 */
	public abstract void setLocation(double xLoc, double yLoc);
	/**
	 * Gets the map of current frame visible pixel in specified Rectangle
	 * @param rect specified Rectangle
	 * @return map of current frame visible pixel in specified Rectangle
	 */
	public abstract boolean[][] getFrameMap(Rectangle rect);
	public abstract double getWidth();
	public abstract double getHeight();
	//////////////////////////////////////////////////////////////////
	/**
	 * Shows or hides this MovableSprite depending on the value of parameter b.
	 * @param b if true, makes the MovableSprite visible, otherwise hides the MovableSprite. 
	 */
	public void setVisible(boolean b) {
		if (b)
			currFrame = 0;
		else
			currFrame = -1;
	}
	public boolean isVisible() {
		
		return currFrame >= 0;
	}
	/**
	 * Checks whether or not this MovableSprite contains the point at the specified location (x,y).
	 * @param x  the specified X coordinate
	 * @param y  the specified Y coordinate
	 * @return true if the point (x,y) is inside this MovableSprite; false otherwise.
	 */
	public abstract boolean contains(int x, int y);
	/**
	 * Determines whether or not this MovableSprite and the specified MovableSprite intersect. 
	 * @param spr the specified MovableSprite
	 * @return true if the specified MovableSprite and this MovableSprite intersect; false otherwise.
	 */
	public boolean intersects(MovableSprite spr) {
		Rectangle thisBounds = this.getBounds();
		Rectangle sprBounds = spr.getBounds();
		Rectangle rect = thisBounds.intersection(sprBounds);
		if (rect.isEmpty())
			return false;
		//Two bounds intersect if their intersection is nonempty.
		boolean[][] thisMap = this.getFrameMap(rect);
		boolean[][] sprMap = spr.getFrameMap(rect);
		for(int row = 0; row < rect.height; row++)
			for(int col = 0; col < rect.width; col++)
				if (thisMap[row][col] && sprMap[row][col])
					return true;
		return false;
	}
	/**
	 * Determines whether or not this MovableSprite and the specified Rectangle intersect.
	 * @param r  the specified Rectangle
	 * @param internal internal or external part of specified Rectangle is intersected.
	 * @return true if the specified Rectangle and this MovableSprite intersect; false otherwise.
	 */
	public boolean intersects(Rectangle r, boolean internal) {

		Rectangle bounds = this.getBounds();
		Rectangle intr = bounds.intersection(r);
		if (intr.isEmpty())
			if (internal)
				return false;
			else
				return true;
		
		//Check if the Rectangle bounds is contained entirely inside Rectangle r
		if (intr.width == bounds.width && intr.height == bounds.height)
			if (internal)
				return true;
			else
				return false;
		
		intr.x -= bounds.x;
     	intr.y -= bounds.y;
     	boolean[][] thisMap = this.getFrameMap(bounds);
     	if (internal) {
     		// Checks if Rectangle r contains any visible pixel of this MovableSprite.
     		thisMap = this.getFrameMap(intr);
     		for (int row = 0; row < intr.height; row++)
     			for (int col = 0; col < intr.width; col++) {
     				if (thisMap[row][col])
     					return true;
     			}
     		return false;
     	}
     	// Checks if Rectangle r do not contains any visible pixel of this MovableSprite.
     	// There is some visible pixel outside of Rectangle r;
     	thisMap = this.getFrameMap(bounds);
     	for (int row = 0; row < bounds.height; row++)
			for (int col = 0; col < bounds.width; col++) {
				if(!intr.contains(col, row) && thisMap[row][col])
					return true;
			}
		return false;
	}
	public void setNextFrame(){
		if (currFrame >= 0)
			currFrame = (currFrame + 1) % numFrames; 
	}
	public void oneCircle() {
		if (animSpeed > 0) {
			animClock++;
			if (animClock == animSpeed){ 
				animClock = 0;
				setNextFrame();
			}
		}
		if (motionSpeed > 0) {
			motionClock++;
			if (motionClock == motionSpeed) {
				motionClock = 0;
				move();
			}
		}
		
	}
	public int getAnimSpeed() {
		return animSpeed;
	}
	public void setAnimSpeed(int animSpeed) {
		this.animSpeed = animSpeed;
	}
	public int getMotionSpeed() {
		return motionSpeed;
	}
	public void setMotionSpeed(int motionSpeed) {
		this.motionSpeed = motionSpeed;
	}
	public int getMotionClock() {
		return motionClock;
	}
	public double getVelocity() {
		return velocity;
	}
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	public int getCurrFrame() {
		return currFrame;
	}
	
	
	


}
