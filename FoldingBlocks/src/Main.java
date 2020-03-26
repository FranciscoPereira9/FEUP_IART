import java.awt.AWTException;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws AWTException {

		if(args.length != 2){
			System.out.println("Usage");
			System.out.println("java Main <level><algorithm>");
			System.out.println("<level> : [1-12] & <algorithmNumber> : [1-4]");
		}
		if(Integer.parseInt(args[0]) == 0 || Integer.parseInt(args[0])>12){
			System.out.println("Invalid Level");
		}
		if(Integer.parseInt(args[1]) == 0 || Integer.parseInt(args[1])>4){
			System.out.println("Invalid Algorithm");
		}
		JFrame obj = new JFrame();
		obj.setBounds(10, 10, 800, 700);
		Game game = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		obj.setTitle("Folding Blocks");
		obj.setResizable(false);	
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(game);
		obj.setVisible(true);
	}
}
