package de.theholyexception.gol;

public class Vector2I implements Cloneable {

	public int X;
	public int Y;
	
	public Vector2I(int x, int y) {
		this.X = x;
		this.Y = y;
	}
	
	public void subtract(int x, int y) {
		X-=x;
		Y-=y;
	}
	
	public void subtract(Vector2I vector) {
		X-=vector.X;
		Y-=vector.Y;
	}
	
	public void add(int x, int y) {
		X+=x;
		Y+=y;
	}

	public void add(Vector2I vector) {
		X+=vector.X;
		Y+=vector.Y;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public String toString() {
		return "X: " + X + " | Y: " + Y;
	}
}
