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

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener, ActionListener {
	
	private Timer timer;
	private Level2 l2 = new Level2();
	private int numberOfPlays = 0;
	
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
		l2.drawLevel2(g);
		
		//draw time
		g.setColor(Color.lightGray);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+numberOfPlays, 30,30);
	
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
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

		
	}

}
