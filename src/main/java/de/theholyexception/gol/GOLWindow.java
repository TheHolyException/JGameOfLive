package de.theholyexception.gol;

import java.awt.MenuBar;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

public class GOLWindow extends JFrame {

	public static final int   CUBE_SIZE = 20;
	public static final float MIN_ZOOM = 0.01f;
	public static final float MAX_ZOOM = 20.0f;

	private static final long serialVersionUID = -6752222107558088023L;
	
	public static Map<String, BufferedImage> imageBuffer = new HashMap<>();
	
	public Grid grid;
	public List<Cube> cubes = new ArrayList<Cube>();
	
	public float scale = 4.0f;

	public GOLPanel panel;
	public JMenuBar menuBar;
	
	private boolean vkW, vkA, vkS, vkD, vkSpace, vkShift;
	public boolean drawGrid = false;
	public boolean drawDebugMessages = true;
	public boolean drawCubes = true;
	
	public GOLWindow() {
		System.out.println("Initialize Window");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 782);
		
		panel = new GOLPanel();
		getContentPane().add(panel);
		
		menuBar = new JMenuBar();
		buildMenuBar();
		setJMenuBar(menuBar);
		
		setVisible(true);
		
		grid = new Grid();
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				setKeyState(e.getKeyCode(), false);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				setKeyState(e.getKeyCode(), true);
			}
		});
		
		MouseMoveListener mouseListener = new MouseMoveListener();
		panel.addMouseListener(mouseListener);
		panel.addMouseWheelListener(mouseListener);
		panel.addMouseMotionListener(mouseListener);
		
		
	}
	
	private void setKeyState(int keyCode, boolean state) {
		switch(keyCode) {
		case KeyEvent.VK_W:
			vkW = state;
			break;
		case KeyEvent.VK_A:
			vkA = state;
			break;
		case KeyEvent.VK_S:
			vkS = state;
			break;
		case KeyEvent.VK_D:
			vkD = state;
			break;
		case KeyEvent.VK_SHIFT:
			vkShift = state;
			break;			
		}
	}
	
	public void update() {
		repaint();
	}
	
	public void keyUpdate(int speed) {
		if (vkShift) speed*=4;
//		if (vkW) offset.Y +=speed;
//		if (vkS) offset.Y -=speed;
//		if (vkA) offset.X +=speed;
//		if (vkD) offset.X -=speed;
	}

	
	public void addCube(Cube cube) {
		synchronized (cubes) {
			cubes.add(cube);
		}
	}
	
	public void removeCube(Cube cube) {
		synchronized (cubes) {
			cubes.remove(cube);
		}
	}
	
	public void loadImage(String key, BufferedImage image) {
		imageBuffer.put(key, image);
	}
	
	public Vector2I getPostionOnGrid(int x, int y) {
		return null;
	}
	
	public Vector2I getPostionOnGrid(Vector2D absolutePosition) {
		GOLWindow window = GameOfLife.getWindow();
		return new Vector2I(
//				(int)Math.floor((absolutePosition.X*(1/window.scale)-offset.X) / CUBE_SIZE),
//				(int)Math.floor((absolutePosition.Y*(1/window.scale)-offset.Y) / CUBE_SIZE)
				(int)Math.floor(((window.panel.zoomX + absolutePosition.X) / window.scale) / (CUBE_SIZE)),
				(int)Math.floor(((window.panel.zoomY + absolutePosition.Y) / window.scale) / (CUBE_SIZE))
				);
	}
	
	public void buildMenuBar() {
		JMenu file = new JMenu("File");
		{
			JMenuItem open = new JMenuItem("Open...");
			open.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			file.add(open);
			
			JMenuItem save = new JMenuItem("Save");
			save.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			file.add(save);
		}
		menuBar.add(file);
		
		JMenu options = new JMenu("Options");
		{
			JMenuItem freeze = new JMenuItem("Freeze");
			freeze.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					GameOfLife.paused = !GameOfLife.paused;
				}
			});
			freeze.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
			options.add(freeze);
			
			JMenuItem togglegrid = new JMenuItem("ToggleGrid");
			togglegrid.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					drawGrid = !drawGrid;
				}
			});
			togglegrid.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0));
			options.add(togglegrid);
			
			JMenuItem rule = new JMenuItem("Rule");
			rule.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			options.add(rule);
		}
		menuBar.add(options);
		
	}	

}
