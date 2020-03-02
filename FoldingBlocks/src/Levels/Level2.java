package Levels;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Level2 extends JPanel {
	
	public Level2() {
	
	}
	
	public void drawLevel2(Graphics g) {
		g.setColor(Color.white);
		
		//Paint game board
		g.fillRect(100, 100, 95, 95);
		g.fillRect(200, 100, 95, 95);
		g.fillRect(300, 100, 95, 95);
		g.fillRect(400, 100, 95, 95);
		g.fillRect(500, 100, 95, 95);
		
		g.fillRect(100, 200, 95, 95);
		g.fillRect(200, 200, 95, 95);
		g.fillRect(300, 200, 95, 95);
		g.fillRect(400, 200, 95, 95);
		g.fillRect(500, 200, 95, 95);
		
		g.fillRect(100, 300, 95, 95);
		g.fillRect(200, 300, 95, 95);
		g.fillRect(300, 300, 95, 95);
		g.fillRect(400, 300, 95, 95);
		g.fillRect(500, 300, 95, 95);
		
		g.fillRect(100, 400, 95, 95);
		g.fillRect(200, 400, 95, 95);
		g.fillRect(300, 400, 95, 95);
		g.fillRect(400, 400, 95, 95);
		g.fillRect(500, 400, 95, 95);
		
		//Paint pieces to move
		g.setColor(Color.CYAN);
		g.fillRect(100, 100, 95, 95);
		
		g.setColor(Color.MAGENTA);
		g.fillRect(500, 400, 95, 95);
	}
	
	public int getFBlocksNumber() {
		final int number = 2;
		return number;
	}
	public int getNumberRows() {
		final int numRows = 5;
		return numRows;
	}
	public int getNumberCols() {
		final int numCols = 4;
		return numCols;
	}

	
	
}
