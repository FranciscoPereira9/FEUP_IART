package Levels;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Level1 extends JPanel {
	
	public Level1(Graphics g) {
		
		g.setColor(Color.white);
		
		//Paint game board
		g.fillRect(150, 100, 95, 95);
		g.fillRect(250, 100, 95, 95);
		g.fillRect(350, 100, 95, 95);
		g.fillRect(450, 100, 95, 95);
		
		g.fillRect(150, 200, 95, 95);
		g.fillRect(250, 200, 95, 95);
		g.fillRect(350, 200, 95, 95);
		g.fillRect(450, 200, 95, 95);
				
		g.fillRect(150, 300, 95, 95);
		g.fillRect(250, 300, 95, 95);
		g.fillRect(350, 300, 95, 95);
		g.fillRect(450, 300, 95, 95);
		
		g.fillRect(150, 400, 95, 95);
		g.fillRect(250, 400, 95, 95);
		g.fillRect(350, 400, 95, 95);
		g.fillRect(450, 400, 95, 95);
		
		
		//Paint pieces to move
		g.setColor(Color.CYAN);
		g.fillRect(150, 100, 95, 95);
	}
}
