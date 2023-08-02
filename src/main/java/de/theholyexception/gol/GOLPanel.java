package de.theholyexception.gol;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

public class GOLPanel extends JPanel {	
	
	private static final long serialVersionUID = -1293572432655067182L;
	
	public long renderedGridSegments;
	public long renderedCubes;
	
	
	private Font debugFont = new Font("LucidaSans", 0, 15);
	
	
	public int zoomX, zoomY;
	public Point currentMouse;
	public Point mouseZoom;
	public float oldzoom;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		GOLWindow window = GameOfLife.getWindow();
		
		renderedCubes = 0;
		
		// Draw Backplate
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, window.panel.getWidth(), window.panel.getHeight());
		
		// Draw Grid
		if (window.drawGrid) {
			renderedGridSegments = window.grid.draw(g2);
		}
		// Draw Cubes
		if (window.drawCubes) {
			synchronized (window.cubes) {
				for (Cube cube : window.cubes) {
					if (cube.draw(g2)) renderedCubes++;
				}
			}
		}

		
		// Draw Debugmessages
		if (window.drawDebugMessages) {
			g2.scale(1.5d, 1.5d);
			g2.setFont(debugFont);
			g2.setColor(Color.RED); 
			String[] debugMessages = new String[] {
					"Rendered Cubes: " + renderedCubes + " | " + window.cubes.size(),
					"Rendered Grid Segements: " + renderedGridSegments,
					"Rendertime: " + GameOfLife.renderTime/1000 + " us",
					"Scale: " + window.scale,
					"UPS: " + GameOfLife.ups,
					"CycleTime: " + GameOfLife.cycleTime/1000 + " us",
					(GameOfLife.paused ? "Paused!!!!" : ""),
					(currentMouse != null ? currentMouse.toString() : "")
					
			};
			int x = 30;
			int y = 30;
			for (int i = 0; i < debugMessages.length; i++) {
				if (debugMessages[i].length() == 0) continue;
				g2.drawString(debugMessages[i], x, y+=15);
			}
		}
		
		g2.dispose();

	}
}
