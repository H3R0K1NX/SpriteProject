package spritePackage;

public class Point2D {
	public double x;
	public double y;
	/**
	 * Constructs and initializes a Point2D from the specified xy coordinates.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public Point2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	/**
	 * Constructs and initializes a Point2D to (0,0,0). 
	 */
	public Point2D() 
	{
		this.x = 0;
		this.y = 0;
	}
	/**
	 * Copy Constructor constructs and initializes a Point2D from the specified Point2D.
	 * @param p The Point3D containing the initialization x y data
	 */
	public Point2D(Point2D p)
	{
		this.x = p.x;
		this.y = p.y;
	}
	/**
	 * Returns the X coordinate of this Point2D.
	 * @return the X coordinate of this Point2D.
	 */
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	/** Translates this point, at location (x, y), by tX along the x axis and
     * tY along the y axis so that it now represents the point (x + tX, y + tY).
     * @param tX
     * @param tY
      */
    public void translate(double tX, double tY)
    {
        this.x += tX;
        this.y += tY;
    }
    /**
     * Performs scaling transformation, sX, sY - scale factors
     * @param sX
     * @param sY
     */
    public void scale(double sX,double sY)
    {
        this.x *= sX;
        this.y *= sY;
    }
    /**
     * Performs scaling transformation, sX, sY - scale factors
     * the fixed point doesn't changing
     * @param sX
     * @param sY
     */
    public void scale(double sX, double sY, Point2D fixed)
    {
        double tX = fixed.x;
        double tY = fixed.y;
        this.translate(-tX, -tY);
        this.scale(sX, sY);
        this.translate(tX, tY);
    }
    /**
     * Performs rotate around the origin to angle in radians.
     */
    public void rotate(double angle)
    {
        double cs = Math.cos(angle);
        double sn = Math.sin(angle);
        double x = this.x * cs - this.y *sn;
        double y = this.x * sn + this.y *cs;
        this.x = x;
        this.y = y;
        
    }
    /**
     * Performs rotate around the fixed point to angle in radians.
     * The fixed point doesn't change.
     */
    public void rotate(double angle, Point2D fixed)
    {
        double tX = fixed.x;
        double tY = fixed.y;
        this.translate(-tX, -tY);
        this.rotate(angle);
        this.translate(tX, tY);
    }
    public double getDistance(Point2D p)
    {
    	double x = this.x - p.x;
    	double y = this.y - p.y;
    	return Math.sqrt(x * x + y * y);
    }
    /**
	 * Returns a string that contains the values of this Point2D. The form is (x,y).
	 * @return The String representation of Point2D
	 */
	public String toString()
	{
		return String.format("%1$.2f\t%2$.2f", this.x, this.y);
	}

}
