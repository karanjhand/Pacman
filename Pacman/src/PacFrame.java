import java.awt.Color;

import javax.swing.JFrame;

/**
 * 
 * A class for making a new frame for Pacman
 * @author karan
 *
 */
public class PacFrame {
	public static void main(String[] args) {
		JFrame frame= new JFrame();
		frame.setSize(1000,1000);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Pacman knock off");
		
		Pacman p = new Pacman();
	
		frame.add(p);
		frame.setVisible(true);	
		
		
	}
}