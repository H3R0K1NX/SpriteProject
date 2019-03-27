package testSpritePackage;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class TestMainPanel {

	public static void main(String[] args) {
		MainPanel mainPanel = new MainPanel();
		JFrame frame = new JFrame("Directed Sprite Test");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.setResizable(false);
		frame.requestFocusInWindow();
		frame.add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to exit the program?", "Exit Program Message Box",
						JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION) {
					frame.dispose();
					System.exit(0);
				}

			}
		});
		
		frame.setVisible(true);
		

	}

}
