package Utils;
import GameApp.GameApp;
public class Point {
	private int x;
	private int y;
	private double absoluteX;
	private double absoluteY;
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.absoluteX = x* GameApp.BLOCK_SIZE;
		this.absoluteY = y* GameApp.BLOCK_SIZE;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public double getAbsoluteX() {
		return absoluteX;
	}
	public double getAbsoluteY() {
		return absoluteY;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")" + " Point Absolute "
				+ "absX: " + this.getAbsoluteX() + " absY: " + this.getAbsoluteY();
	}
	
}
