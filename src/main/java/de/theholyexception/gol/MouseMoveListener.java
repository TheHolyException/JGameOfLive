package de.theholyexception.gol;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseMoveListener extends MouseAdapter {

	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		GameOfLife.getWindow().panel.currentMouse = e.getPoint();
	}
	
	long lastDraw = 0;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = (lastPressedPoint.x - (int)e.getPoint().getX());
		int y = (lastPressedPoint.y - (int)e.getPoint().getY());
		
		GameOfLife.getWindow().panel.zoomX += x;
		GameOfLife.getWindow().panel.zoomY += y;
		
		lastPressedPoint = e.getPoint();
		if (System.currentTimeMillis() - lastDraw > 20) {
			GameOfLife.getWindow().repaint();
			lastDraw = System.currentTimeMillis();
		}
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		lastPressedPoint = e.getPoint();
		lastPressedPointStartPoint = e.getPoint();
		lastPressedTime = System.currentTimeMillis();
		
	}

	private Point lastPressedPoint = null;
	private Point lastPressedPointStartPoint = null;
	private long  lastPressedTime = 0;
	
	@Override 
	public void mouseReleased(MouseEvent e) {
		double distance = getDistance(lastPressedPointStartPoint, e.getPoint());
		long timediff = System.currentTimeMillis() - lastPressedTime;
		if (timediff < 300 && distance < 10.0d) {
			// Toggle Cube
			Vector2I gridPos = GameOfLife.getWindow().getPostionOnGrid(new Vector2D(e.getX(), e.getY()));
			if (GameOfLife.paused) {
				Cube cube = Cube.getAt(gridPos.X, gridPos.Y);
				if (cube != null) {
					cube.kill();
				} else {
					Cube.createNew(gridPos.X, gridPos.Y);
				}
				GameOfLife.getWindow().repaint();
			}
		}
		lastPressedPointStartPoint = null;
		lastPressedTime = 0;
	}
	
	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		GOLWindow window = GameOfLife.getWindow();
		
		double oldscale = window.scale;
		
		if (e.isShiftDown()) {
			if (e.getWheelRotation() < 0) GameOfLife.ups ++;
			else GameOfLife.ups = (GameOfLife.ups == 1 ? 1 : GameOfLife.ups-1);
		} else {
			window.panel.mouseZoom = e.getPoint();
			if (e.getWheelRotation() < 0) {
				window.scale += 0.1 * window.scale;
				if (window.scale > GOLWindow.MAX_ZOOM) window.scale = GOLWindow.MAX_ZOOM;
			}
			else {
				window.scale -= 0.1 * window.scale;
				if (window.scale < GOLWindow.MIN_ZOOM) window.scale = GOLWindow.MIN_ZOOM;
			}			

			window.panel.zoomX = (int)((window.panel.zoomX + e.getX()) / oldscale*window.scale) - e.getX();
			window.panel.zoomY = (int)((window.panel.zoomY + e.getY()) / oldscale*window.scale) - e.getY();
		}
		
		if (System.currentTimeMillis() - lastDraw > 20) {
			GameOfLife.getWindow().repaint();
			lastDraw = System.currentTimeMillis();
		}
	}
	
	private double getDistance(Point a, Point b) {
		double distance  = Math.pow((a.x - b.x), 2);
		distance        += Math.pow((a.y - b.y), 2);
		return Math.sqrt(distance);
	}
	
}
