package Levels;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Level1 extends JPanel {
	
	
	public Level1(HashMap <Integer,Integer> board) {
		initializeBoard(board); 
		board.put(1,1);
	}
	
	public void initializeBoard(HashMap <Integer,Integer> board) {
		for(int i=1; i<=16; i++) {
			board.put(i,0);
		}
	}
	
	public void drawLevel1(Graphics g) {
		int x = 150;
		int y = 100;
		
		g.setColor(Color.white);
		
		//Paint game board
		for(int i=0; i<4; i++) {
			for(int j=0; j<4 ; j++) {
				g.fillRect(x,y,95,95);
				x+=100;
			}
			x=150;
			y+=100;
		}
			
	}
	
	public int getNumberPieces() {
		final int number = 1;
		return number;
	}
	public int getNumberRows() {
		final int number = 4;
		return number;
	}
	public int getNumberCols() {
		final int number = 4;
		return number;
	}

}
