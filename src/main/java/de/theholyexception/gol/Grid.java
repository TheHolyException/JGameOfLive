package de.theholyexception.gol;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Grid {
	private Color color = Color.GRAY;
		

	private Font gridFont = new Font("Terminal", 0, 15);
	private float lastFontSize = 0f;
	
	public long draw(Graphics2D g2) {
		GOLWindow window = GameOfLife.getWindow();
		if (!window.drawGrid || window.scale < 0.25) return 0;
		int lastDrawnSegments = 0;
		g2.setColor(color);
		
		double scaledCubeSize = GOLWindow.CUBE_SIZE*window.scale;
		
		for (    double x = (-window.panel.zoomX % scaledCubeSize) - scaledCubeSize; x < window.getWidth(); x+=scaledCubeSize) {
			for (double y = (-window.panel.zoomY % scaledCubeSize) - scaledCubeSize; y < window.getHeight(); y+=scaledCubeSize) {
				g2.setColor(Color.GREEN);
				g2.drawRect(
						(int)x, 
						(int)y, 
						(int)Math.round(scaledCubeSize), 
						(int)Math.round(scaledCubeSize)
				);
				if (window.scale >= 3) {

					int xGrid = (int) Math.round((x + window.panel.zoomX)/window.scale/GOLWindow.CUBE_SIZE);
					int yGrid = (int) Math.round((y + window.panel.zoomY)/window.scale/GOLWindow.CUBE_SIZE);
					Cube c = Cube.getAt(xGrid, yGrid);
					
					float newFontSize = (float)(scaledCubeSize/8);
					if (lastFontSize != newFontSize) {
						gridFont = new Font(Font.MONOSPACED, 0, 20);
						gridFont = gridFont.deriveFont(newFontSize);
						lastFontSize = newFontSize;
					}
					
					if (c == null) g2.setColor(Color.WHITE);
					else g2.setColor(Color.BLACK);
					g2.setFont(gridFont);
					g2.drawString("XGrid: " + xGrid, (int)(x+2), (int)(y+(newFontSize*2)));
					g2.drawString("YGrid: " + yGrid, (int)(x+2), (int)(y+(newFontSize*3)));
					g2.setColor(Color.GREEN);
				}				
				lastDrawnSegments ++;
			}
		}
		
		return lastDrawnSegments;
	}
	
	public Color getColor() {
		return color;
	}
}
