import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.HashMap;


@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener, ActionListener {
	
	private Timer timer;
	private int num_level = 1;
	private Level l = new Level(num_level);
	private int numberOfPlays = 0;
	private int numberOfSquaresH = 1;
	private int numberOfSquaresV = 1;

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
		g.fillRect(1,1,792,692);
		
		l.drawLevel(g);
		
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
