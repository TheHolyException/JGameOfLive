package de.theholyexception.gol;

import java.awt.Point;

public class Vector2D {
	
	public double X;
	public double Y;
	
	public Vector2D(double x, double y) {
		this.X = x;
		this.Y = y;
	}
	
	public double distance(Vector2D target) {
		double distance  = Math.pow((X - target.X), 2);
		distance        += Math.pow((Y - target.Y), 2);
		return Math.sqrt(distance);
	}
	
	public String toString() {
		return "X: " + X + " | Y: " + Y;
	}
	
	
	public void subtract(double a) {
		X-=a;
		Y-=a;
	}
	
	public void subtract(double x, double y) {
		X-=x;
		Y-=y;
	}
	
	public void subtract(Vector2D vector) {
		X-=vector.X;
		Y-=vector.Y;
	}
	
	public void subtract(Point vector) {
		X-=vector.x;
		Y-=vector.y;
	}
	
	public void add(double a) {
		X+=a;
		Y+=a;
	}
	
	public void add(double x, double y) {
		X+=x;
		Y+=y;
	}

	public void add(Vector2D vector) {
		X+=vector.X;
		Y+=vector.Y;
	}
	
	public void add(Point vector) {
		X+=vector.x;
		Y+=vector.y;
	}
	
	public void divide(double a) {
		X/=a;
		Y/=a;
	}
	
	public void divide(double x, double y) {
		X/=x;
		Y/=y;
	}
	
	public void divide(Vector2D vector) {
		X/=vector.X;
		Y/=vector.Y;
	}
	
	public void divide(Point vector) {
		X/=vector.x;
		Y/=vector.y;
	}
	
	public void multiply(double a) {
		X*=a;
		Y*=a;
	}
	
	public void multiply(double x, double y) {
		X*=x;
		Y*=y;
	}
	
	public void multiply(Vector2D vector) {
		X*=vector.X;
		Y*=vector.Y;
	}
	
	public void multiply(Point vector) {
		X*=vector.x;
		Y*=vector.y;
	}

}
