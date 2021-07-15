package window;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Main {

	public static void main(String[] args) {

	
		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		 } 
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	    }    

		// Creating instance of JFrame
		JFrame frame = new JFrame();
		Image icon = Toolkit.getDefaultToolkit().getImage(".//res//money.png");    // adding new logo icon
		frame.setIconImage(icon); 
		
		// Setting the width and height of frame
		frame.setSize(300, 420);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Creating instance of JPanel
		JPanel panel = new JPanel();
		frame.add(panel);

		// calling user defined method for adding components to the panel.
		MyWindow.placeComponents(panel);

		// Setting the frame visibility to true
		frame.setVisible(true);
	}
}
