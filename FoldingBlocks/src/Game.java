import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import Levels.Level1;
import Levels.Level2;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener, ActionListener {
	
	private Timer timer;
	private HashMap<Integer,Integer> board = new HashMap<Integer,Integer>();
	private Level1 l1 = new Level1(board);
	private int numberOfPlays = 0;
	private int numberOfSquaresH = 1;
	private int numberOfSquaresV = 1;
	private int rowCalcAux;
	private boolean right = false;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;
	
	public Game() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(8, this);
		timer.start();

	}
	
	public void paint(Graphics g) {
			
		//Paint background
		g.setColor(Color.black);
		g.fillRect(1,1,692,592);
		
		//Game Level
		l1.drawLevel1(g);
		rowCalcAux = l1.getNumberRows();
		//draw time
		g.setColor(Color.lightGray);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+numberOfPlays, 30,30);
		
		
		//Duplica para a direita
		if(right == true) {
			for(int i=board.size(); i>0; i--) {
				if(board.get(i) == 1) {
					board.replace(i+numberOfSquaresH,1);
				}
			}
			right = false;
		}
		
		if(down == true) {
			
			for(int i=board.size(); i>0; i--) {
				if(board.get(i) == 1) {
					board.replace(i+numberOfSquaresV*4,1);
					
				}
				
			}
			down = false;
		}
		
		//Atualiza o estado do tabuleiro
		for(int i=1; i<=board.size(); i++) {
			if(board.get(i) != 0) {
				g.setColor(Color.CYAN);
				g.fillRect(150+(((i-1)%4))*100, 100+((i-1)/4)*100, 95, 95);
			}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//Aumenta o numero de jogadas;
		numberOfPlays++;
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//Do some movement;
			right = true;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			//Do some movement;
			left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			//Do some movement;
			up = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			//Do some movement;
			down = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//Do some movement;
			numberOfSquaresH = numberOfSquaresH*2;
			right = false;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			//Do some movement;
			numberOfSquaresH = numberOfSquaresH*2;
			left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			//Do some movement;
			numberOfSquaresV = numberOfSquaresV*2;
			up = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			//Do some movement;
			numberOfSquaresV = numberOfSquaresV*2;
			down = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

		
	}

}
