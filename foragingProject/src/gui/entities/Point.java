package gui.entities;

public class Point {

	private double x;
	private double y;

	public Point() {
		super();
	}
	public Point(double x, double y) {
		super();
		this.setX(x);
		this.setY(y);
	}

	public Point(int x, int y) {
		super();
		this.setX(x);
		this.setY(y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
