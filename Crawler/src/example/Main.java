package example;

import java.io.IOException;
import java.text.ParseException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				// Setting up GUI
				JFrame frame = new MainFrame("Min Festival Crawler");

				frame.setSize(600,800);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);		
			}
		});
	}
}	

