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

@SuppressWarnings({ "serial", "unused" })

public class Game extends JPanel implements KeyListener, ActionListener {
	
	private Timer timer;
	private int num_level;
	private Level l;
	private int[][] mat;
	
	private int move;
	private int ID_block;
	public boolean right;
	public boolean left;
	public boolean up;
	public boolean down;
	public Logic functional;
  
	public Game() {

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(8, this);
		timer.start();
		
		this.up=false;this.down=false;this.right=false;this.left=false;
		this.move=0;
		this.ID_block=1;
		this.num_level=6;
		this.functional= new Logic();
		l = new Level(num_level);
		this.mat=l.get_board();

	}
	
	public void paint(Graphics g) {
		//Paint background
		g.setColor(Color.black);
		g.fillRect(1,1,792,692);
		this.l.drawLevel(g);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();

		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		this.mat=l.get_board();
		int[][] board;
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//Do some movement;
			this.right = true;
			this.move=2;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			//Do some movement;
			this.left = true;
			this.move=1;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			//Do some movement;
			this.up = true;
			this.move=3;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			//Do some movement;
			this.down = true;
			this.move=4;
		}
		
		
		
		//Identificar os blocos a jogar
		if(e.getKeyCode() == KeyEvent.VK_1) {
			this.ID_block = 1;
		}
		if(e.getKeyCode() == KeyEvent.VK_2) {
			this.ID_block = 2;
		}
		if(e.getKeyCode() == KeyEvent.VK_3) {
			this.ID_block = 3;
		}
		if(e.getKeyCode() == KeyEvent.VK_4) {
			this.ID_block = 4;
		}
		if(e.getKeyCode() == KeyEvent.VK_5) {
			this.ID_block = 5;
		}
		
		//Fazer movimento e dar update ao board
		board=this.functional.fold(this.mat, this.move, this.ID_block);
		this.l.update_board(board);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//Do some movement;
			this.right = false;
			this.move=0;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			//Do some movement;
			this.left = false;
			this.move=0;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			//Do some movement;
			this.up = false;
			this.move=0;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			//Do some movement;
			this.down = false;
			this.move=0;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

		
	}

}
