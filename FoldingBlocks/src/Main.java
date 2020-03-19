import java.awt.AWTException;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws AWTException {

		JFrame obj = new JFrame();
		obj.setBounds(10, 10, 800, 700);
		Game game = new Game();
		obj.setTitle("Folding Blocks");
		obj.setResizable(false);	
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(game);
		obj.setVisible(true);
	}
}
