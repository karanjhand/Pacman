import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.util.Random;
import javax.swing.*;

/**
 * A class for creating a pacman that runs on the screen with the help of 
 * the mouse and is dragged according to the position of the pointer of the 
 * mouse on the panel and when the it hits the ghosts before hitting the pellet
 * the user lose the game and if it hits the ghosts after hitting the pellet
 * within the time limit of 5 seconds the user wins.
 * @author karan
 *
 */
public class Pacman extends JPanel{
	private ImageIcon pac,ghost,pellet,pinky,blinky,clyde,inky,blue;
	private Point pacPoint, locationG, pelletPoint, mouseClick, mouseDrag;
	private Rectangle pacGaurd, ghostGaurd, pelletGaurd; 
	private Timer ghostTimer;
	private Random gen2;
	private int xDir = 5, yDir = 5;
	private int time;
	private boolean actionTimer, ghostCapture;
	
	/**
	 * A constructor for creating a pacman and the moving ghosts 
	 */
	public Pacman() {
		time = 0;
		actionTimer = false;
		
		pacPoint = null;
		mouseDrag = null;
		
		
		int pointX1 = -100;
		int pointY1 = -100;
		pacPoint = new Point(pointX1, pointY1);
		
		addMouseListener(new PacmanListener());
		addMouseMotionListener(new PacmanListener());
		
		
		gen2 = new Random();
		int pointX2 = gen2.nextInt(900) + 1;
		int pointY2 = gen2.nextInt(600) + 1;
		pelletPoint = new Point(pointX2,pointY2);
		
		pac = new ImageIcon("pacPhotos/pacman.png");
		
		locationG = new Point(500,500);
		ghost = new ImageIcon("pacPhotos/blinky.png");
		
		ghostTimer = new Timer(50, new TimerListener());
		ghostTimer.start();
		
		pellet = new ImageIcon("pacPhotos/pellet.png");
		
		blue = new ImageIcon("pacPhotos/blue.png");
		blinky = new ImageIcon("pacPhotos/blinky.png");
		clyde = new ImageIcon("pacPhotos/clyde.png");
		pinky = new ImageIcon("pacPhotos/pinky.png");
		inky = new ImageIcon("pacPhotos/inky.png");
		
		addKeyListener(new PBICListener());
		setFocusable(true);

		
		
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(1000,1000));
		
	}
	
	/**
	 * A method for drawing the image icons on the panel
	 * @param g : An object for the Graphics class
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if(pacPoint != null ) {
				pac.paintIcon(this, g2, pacPoint.x - 40, pacPoint.y - 40);
		}
		
		
		ghost.paintIcon(this, g2, locationG.x, locationG.y);
		
		
		pellet.paintIcon(this, g2, pelletPoint.x, pelletPoint.y);
	}
	
	/**
	 * A private class for creating a timer event for the ghosts which makes
	 * the ghosts moving and it also checks for the intersection of the 
	 * pacman and the ghosts and the timer stops at the time of the 
	 * intersection
	 * @author karan
	 *
	 */
	private class TimerListener implements ActionListener{
		/**
		 * A method for performing the random movement of the ghosts and
		 *  checking the collision between the pacman and the ghosts
		 *  @param e: An object for the ActionEvent
		 */
		public void actionPerformed(ActionEvent e) {
			if(actionTimer) {
				time += 50;
			}
			if(locationG.getX() >= 900 || locationG.getX() <= 0) {
				xDir *= -1;
			}
			if(locationG.getY() >= 600 || locationG.getY() <= 0) {
				yDir *= -1;
			}
			locationG.x += xDir;
			locationG.y += yDir;
			
			pacGaurd = new Rectangle(pacPoint.x ,pacPoint.y, pac.getIconWidth() - 10,pac.getIconHeight() - 10);
			ghostGaurd = new Rectangle(locationG.x,locationG.y, ghost.getIconWidth() - 10,ghost.getIconHeight() - 10);
			pelletGaurd = new Rectangle(pelletPoint.x,pelletPoint.y,pellet.getIconWidth(),pellet.getIconHeight());
			if(ghostGaurd.intersects(pacGaurd) ) {
				if(ghostCapture) {
					ghostTimer.stop();
					JOptionPane.showMessageDialog(null, "You Won");
				}else {
					ghostTimer.stop();
					JOptionPane.showMessageDialog(null, "You Lose");
				}
			}
			
			if(pacGaurd.intersects(pelletGaurd)) {
				pelletPoint.x = -900;
				pelletPoint.y = -900;
				ghost = blue;
				actionTimer = true;
				ghostCapture = true;
			
			}
			
			if(time >= 5000) {
				int pointX2 = gen2.nextInt(900) + 1;
				int pointY2 = gen2.nextInt(600) + 1;
				pelletPoint.x = pointX2;
				pelletPoint.y = pointY2;
				ghost = blinky;
				actionTimer = false;
				ghostCapture = false;
				time = 0;
			}
			repaint();
			

		}
	}
	
	/**
	 * A private class for the keyboard events that change the colors 
	 * of the ghosts upon pressing certain keys
	 * @author karan
	 *
	 */
	private class PBICListener extends KeyAdapter{
		/**
		 * A method for changing the colors of the ghosts upon pressing the 
		 * PBIC keys. P changes the ghost to pinky, B to blinky
		 * I to Inky and C to clyde
		 * @param e : An object for the KeyEvent 
		 */
		public void keyPressed(KeyEvent e) {
			if((pacGaurd.intersects(pelletGaurd) && (time >= 5000)) || !(pacGaurd.intersects(pelletGaurd))) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_P:
					ghost = pinky;
					break;
				case KeyEvent.VK_B:	
					ghost = blinky;
					break;
				case KeyEvent.VK_I:
					ghost = inky;
					break;
				case KeyEvent.VK_C:
					ghost = clyde;
					break;
				}
			}
			repaint();
			
		}
	}
	
	/**
	 * A private class for the Mouse events that occurs upon the click and the 
	 * motion of the mouse on the panel
	 * @author karan
	 *
	 */
	private class PacmanListener implements MouseListener, MouseMotionListener{
		/**
		 * A method for the mouse press event that gets the coordinate of
		 * the mouse click
		 * @param e: An object for the MouseEvent
		 */
		public void mousePressed(MouseEvent e) {
			pacPoint = e.getPoint();
			repaint();
		}
		
		/**
		 * A method for the mouse movement and calculates the coordinate as
		 * the mouse moves over the panel and it is responsible for the 
		 * movement of the pacman over the panel as the mouse over the panel
		 * @param e : An object for the MouseEvent
		 */
		public void mouseMoved(MouseEvent e) {
			pacPoint = e.getPoint();
			repaint();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseDragged(MouseEvent e) {}
	}
	
	
}