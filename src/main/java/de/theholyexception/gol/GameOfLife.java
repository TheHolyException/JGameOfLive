package de.theholyexception.gol;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GameOfLife {

	private static GOLWindow window;
	public static boolean paused = true;
	public static long cycleTime = 0;
	public static long renderTime = 0;
	public static long ups = 60;
	
	public static void main(String[] args) {

		window = new GOLWindow();
		BufferedImage baseImage = null;
		try {
			baseImage = ImageIO.read(GameOfLife.class.getResource("/Play-Pause-Button.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int imagewidth = baseImage.getWidth()/2;
		BufferedImage play = new BufferedImage(imagewidth, baseImage.getHeight(), baseImage.getType());
		BufferedImage pause = new BufferedImage(imagewidth, baseImage.getHeight(), baseImage.getType());
		
		Graphics2D g2d_play = play.createGraphics();
		Graphics2D g2d_pause = pause.createGraphics();

		g2d_play.drawImage(baseImage, 0, 0, imagewidth, baseImage.getHeight(), imagewidth, baseImage.getHeight(), 0, 0, null);
		g2d_pause.drawImage(baseImage, 0, 0, imagewidth, baseImage.getHeight(), imagewidth, baseImage.getHeight(), imagewidth, 0, null);
		
		g2d_play.dispose();
		g2d_pause.dispose();
		window.loadImage("start", play);
		window.loadImage("pause", pause);
		
		Cube.defaultColor = Color.WHITE;

		Cube.createNew(0, 0);
		Cube.createNew(3, 3);
		Cube.createNew(4, 3);
		Cube.createNew(5, 3);

		window.scale = 1;
		
		Thread rendering = new Thread(new Runnable() {

			long cycleTimer = 0;
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
						Thread.currentThread().interrupt();
					}
					
					renderTime = System.nanoTime();
					if (1 == 2) return;
					window.update();
					renderTime = System.nanoTime() - renderTime;
				}
			}
		});
		rendering.setName("Rendering");
		rendering.start();
		
		long cycleTimer = 0;
		while(true) {
			try {
				Thread.sleep(1000/ups);
			} catch (InterruptedException ex) {
				System.err.println(ex.getMessage());
				Thread.currentThread().interrupt();
			}
			
			cycleTimer = System.nanoTime();
			if (paused) continue;

			tick();

			Cube.createCubes();
			Cube.killCubes();
			cycleTime = System.nanoTime() - cycleTimer;
			
		}
		
	}
	
	public static GOLWindow getWindow() {
		return window;
	}

	
	
	public static void tick(/*String rule*/) {
		for (Cube cube : Cube.getCubeRegister().values()) {
			int px, y, py;
			{
				Vector2I a = cube.getLocation();
				px = a.X;
				py = a.Y;
			}
			{
				int neighbours = Cube.getNeighbourCount(px, py);
				
				if (neighbours < 2 || neighbours > 3) {
					cube.killNextGen();
				}
			}
			
			int endX = px+2;
			int endY = py+2;
			px--;
			py--;
			int nc;
			for (; px < endX; px++) {
				for (y = py; y < endY; y++) {
					nc = Cube.getNeighbourCount(px, y);
					if((nc == 3 || nc == 6) && Cube.getAt(px, y) == null) {
						Cube.createNextGen(px, y);
					}
				}
			}
		}
		window.update();
	}
	
	
}
