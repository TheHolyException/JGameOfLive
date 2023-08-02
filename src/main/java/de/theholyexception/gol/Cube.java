package de.theholyexception.gol;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cube {
	
	private static Map<Long, Cube> cubeRegister = new HashMap<>();
	private static List<Cube> toKill = new ArrayList<>();
	private static Map<Long, Cube> toCreate = new HashMap<>();
	
	private Vector2I location;
	public static Color defaultColor = Color.BLACK;
	private Color color = defaultColor;
	
	
	private Cube(int x, int y) {
		location = new Vector2I(x, y);
	}
	
	public static void createNextGen(int x, int y) {
		long loc = convertToLong(x, y);
		
		if (!toCreate.containsKey(loc)) toCreate.put(loc, new Cube(x, y));
	}
	
	public static Cube createNew(int x, int y) {
		return new Cube(x, y).create();
	}
	
	public Vector2I getAbsoluteLocation() {
		GOLWindow window = GameOfLife.getWindow();
		return new Vector2I(
				(int)(location.X*GOLWindow.CUBE_SIZE*window.scale)-window.panel.zoomX,
				(int)(location.Y*GOLWindow.CUBE_SIZE*window.scale)-window.panel.zoomY
				);
	}
	
	public boolean draw(Graphics2D g2) {
		GOLWindow window = GameOfLife.getWindow();
		Vector2I absolute = getAbsoluteLocation();
		
		double gridcubesize = GOLWindow.CUBE_SIZE*window.scale;
		
		if (	absolute.X+gridcubesize < 0 || absolute.Y+gridcubesize < 0 ||
				absolute.X > window.panel.getSize().getWidth() || absolute.Y > window.panel.getSize().getWidth()
				) {
			return false;
		}
			

		g2.setColor(color);
		g2.fillRect(
				Math.round(((location.X*GOLWindow.CUBE_SIZE)*window.scale-window.panel.zoomX)),
				Math.round(((location.Y*GOLWindow.CUBE_SIZE)*window.scale-window.panel.zoomY)),
				Math.round(GOLWindow.CUBE_SIZE*window.scale),
				Math.round(GOLWindow.CUBE_SIZE*window.scale)
				);
		g2.setColor(Color.BLACK);
		g2.drawRect(
			Math.round(((location.X*GOLWindow.CUBE_SIZE)*window.scale-window.panel.zoomX)),
			Math.round(((location.Y*GOLWindow.CUBE_SIZE)*window.scale-window.panel.zoomY)),
			Math.round(GOLWindow.CUBE_SIZE*window.scale),
			Math.round(GOLWindow.CUBE_SIZE*window.scale)
			);
		return true;
	}
	
	public Vector2I getLocation() {
		return location;
	}
	
	public void setLocation(int x, int y) {
		cubeRegister.remove(convertToLong(location.X, location.Y));
		cubeRegister.put(convertToLong(x, y), this);
		location.X = x;
		location.Y = y;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Cube setColor(Color color) {
		this.color = color;
		return this;
	}
	
	public Cube[] getNeighbours() {
		Cube[] neighbours = new Cube[8];
		int sel = 0;
		for (int x = location.X -1; x <= location.X+1; x ++) {
			for (int y = location.Y -1; y <= location.Y+1; y ++) {
				if (x == location.X && y == location.Y) continue;
				neighbours[sel++] = getAt(x, y);
			}
		}
		return neighbours;
	}
	
	public static int getNeighbourCount(int posX, int posY) {
		int x, y;
		int result = 0;
		for (x = posX -1; x <= posX+1; x ++) {
			for (y = posY -1; y <= posY+1; y ++) {
				if (x == posX && y == posY) continue;
				//if (cubeRegister[x][y] != null) result++;
				if (cubeRegister.containsKey(convertToLong(x, y))) result++;
			}
		}
		return result;
	}
	
	public static int getNeighbourCount(Vector2I pos) {
		return getNeighbourCount(pos.X, pos.Y);
	}
	
	public void kill() {
		cubeRegister.remove(convertToLong(location.X, location.Y));
		GameOfLife.getWindow().removeCube(this);
	}
	
	public void killNextGen() {
		toKill.add(this);
	}
	
	private Cube create() {
		cubeRegister.put(convertToLong(location.X, location.Y), this);
		GameOfLife.getWindow().addCube(this);
		return this;
	}
	
	public static void killCubes() {
		for (Cube cube : toKill) {
			cube.kill();
		}
		toKill.clear();
	}
	
	public static void createCubes() {
		for (Cube cube : toCreate.values()) {
			cube.create();
		}
		toCreate.clear();
	}
	
	public static Cube getAt(int x, int y) {
		return cubeRegister.get(convertToLong(x, y));
	}
	
	public static Map<Long, Cube> getCubeRegister() {
		return cubeRegister;
	}
	
	@Override
	public String toString() {
		return "CubeColor: " + color + " Location: " + location;
	}
	
	public static long convertToLong(int a, int b) {
		return (long)a << 32 | b & 0xFFFFFFFFL;
	}
	
	public static int[] convertToInt(long c) {
		int[] ints = new int[2];
		ints[0] = (int)(c >> 32);
		ints[1] = (int)c;
		return ints;
	}

}
