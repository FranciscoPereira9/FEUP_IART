import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

//import sun.awt.AWTAccessor.KeyEventAccessor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings({ "serial", "unused" })

public class Game extends JPanel implements KeyListener, ActionListener {
	
	private final Timer timer;
	private final int num_level;
	private final Level l;
	private Level level_algoritmo; 
	public Algoritmo al;
	private int[][] mat;
	private final int[][] board_ai;

	
	private int move;
	private int ID_block;
	public boolean right;
	public boolean left;
	public boolean up;
	public boolean down;
	public Logic functional;
	private final ArrayList<Integer> playsMove = new ArrayList<>();
	private final ArrayList<Integer> playsID = new ArrayList<>();
	private boolean ai;
	private int ai_i;
	private boolean end;
	
  
	public Game(int levelNumber, int algoritmoNumber) throws AWTException {

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(8, this);
		timer.start();
		
		this.up=false;this.down=false;this.right=false;this.left=false;
		this.move=0;
		this.ID_block=1;
		this.num_level=levelNumber;
		this.ai=false;
		this.ai_i=0;
		this.end=false;

		this.functional= new Logic();
		l = new Level(num_level);
		this.mat=l.get_board();
		this.board_ai=l.get_board();
		final int [][] aux_board=cloneArray(this.mat);
		al = new Algoritmo(aux_board,l.getLevel(),l.getNumberPieces(),algoritmoNumber);
		al.algoritmo1();
		final String plays=al.plays;
		interpretPlays(plays);
	}
	
	public void paint(final Graphics g) {
		//Paint background
		g.setColor(Color.black);
		g.fillRect(1,1,792,692);
		this.l.drawLevel(g);
		if(this.ai && !this.end){
			try {
				makeAIplay(this.board_ai);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		timer.start();
	
		repaint();
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		
		this.mat=l.get_board();
		final int[][] board;
		
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
		if(e.getKeyCode() == KeyEvent.VK_6) {
			this.ID_block = 6;
		}
		if(e.getKeyCode() == KeyEvent.VK_7) {
			this.ID_block = 7;
		}
		if(e.getKeyCode() == KeyEvent.VK_8) {
			this.ID_block = 8;
		}
		if(e.getKeyCode() == KeyEvent.VK_9) {
			this.ID_block = 9;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.ai=true;
		}
		
		//Fazer movimento e dar update ao board
		this.mat=this.functional.fold(this.mat, this.move, this.ID_block);
		
		if(this.functional.isBoardFull(this.mat)){
			final JFrame frame = new JFrame();
			frame.setBounds(200, 100, 500, 350);
			frame.setTitle("You Won");
			final JButton start = new JButton("YOU WON!");
			frame.add(start);
			frame.setResizable(false);	
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
		
		this.l.update_board(this.mat);

	}

	@Override
	public void keyReleased(final KeyEvent e) {
		
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
	public void keyTyped(final KeyEvent arg0) {
	}

	public static int[][] cloneArray(final int[][] src) {
		final int length = src.length;
		final int[][] target = new int[length][src[0].length];
		for (int i = 0; i < length; i++) {
			System.arraycopy(src[i], 0, target[i], 0, src[i].length);
		}
		return target;
	}

	public void interpretPlays(final String plays){
		int j=0;
		for(int i=0; i < plays.length(); i++){
			if((plays.charAt(i) != ',') && (plays.charAt(i)!= '|')){
				final String a=String.valueOf(plays.charAt(i));
				final int x = Integer.parseInt(a);
				if(j%2 == 0) playsMove.add(x); //even
				else playsID.add(x); //odd			
				j++;
			}
		}
	}

	public void makeAIplay(int[][] board) throws AWTException{
		
		if(playsID.size() == playsMove.size() && ai_i < playsMove.size()){
			
			board=this.l.get_board();
			board=this.functional.fold(board, playsMove.get(this.ai_i), playsID.get(this.ai_i));
			this.l.update_board(board);
			try {
				Thread.sleep(500);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			this.ai_i++;
		

		if(this.functional.isBoardFull(board)){
			final JFrame frame = new JFrame();
			frame.setBounds(200, 100, 500, 350);
			frame.setTitle("You Won");
			final JButton start = new JButton("YOU WON!");
			frame.add(start);
			frame.setResizable(false);	
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			this.end=true;
		}
	}



	}

}