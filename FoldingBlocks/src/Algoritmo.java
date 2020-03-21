import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.util.Collections;

public class Algoritmo {
	
	private int [][] init_level_board;
	private int[][] board;
	private Logic logic = new Logic();
	private int numberPieces;
	public String plays = new String();
	public int chosenPiece = 0;
	private PriorityQueue<Node> unusedNodes;
	private List<Node> usedNodes;
	static final int maxDepth = 20;
	public boolean solutionfound=false;
	public Node solution;
	private int algoritmoEscolhido;
	private int level;

	public Algoritmo(int[][] board, int level, int numberPieces, int algoritmo) {
		this.board = board;
		this.numberPieces = numberPieces;
		this.solution=null;
		this.plays="";
		this.init_level_board=board;
		this.level=level;
	    switch(algoritmo){
	    	case 1:
	    	/*
	    		O algoritmo é o depth, onde a fila n vai ser usada, mas tem de ser inicializada na mesma.
	    	*/
	 	    	this.unusedNodes = new PriorityQueue<>((Node n1, Node n2) -> {
				return n2.getCost() - n1.getCost();
			});
			
	    	break;
			case 2:
			// caso seja greedy a fila é organizada da seguinte forma;
			this.unusedNodes = new PriorityQueue<>((Node n1, Node n2) -> {
				return n2.getCost() - n1.getCost();
			});
			break;
			// caso seja Astar a fila é organizada da seguinte forma;
			case 3:
				this.unusedNodes = new PriorityQueue<>((Node n1, Node n2) -> {
				return heuristicaCost(n2) - heuristicaCost(n1);
			});
			break;
		}
	
		this.usedNodes = new ArrayList<>();
		this.algoritmoEscolhido = algoritmo;
		
	}

	public boolean algoritmo1(){
		Node parentNode = new Node(this.board, null, "", 0, 0);
		unusedNodes.add(parentNode);
	    return solve(parentNode);
	}
	
	public boolean solve(Node parentNode){
		
			if(this.algoritmoEscolhido==1){
				checkwin(parentNode);
				addChildNodes(parentNode);
			}
			
			else{
				if(iteratorNodes()) return true;
			
				createChilds(parentNode);
			}
		return false;
	}
	
	public boolean checkwin(Node node){
		if(verifyFinalState(node.getBoard())){
			System.out.println("Game Won");
			System.out.println("Used nodes: " + usedNodes.size());
			this.solution=node;
			this.solutionfound=true;
			getplays(node);
			return true;
		}
		
		return false;
	}
	
	public void createChilds(Node node){
		final int depth = node.getDepth();
        final int cost = node.getCost();
		int[][] calculatedBoard;
		
		for(int i=1; i<=this.numberPieces; i++){
			for(int j=1; j<=4; j++){
					calculatedBoard = logic.fold(node.getBoard(), j, i);
					if(!compareBoards(node.getBoard(), calculatedBoard)){
						usedNodes.add(node);
						Node childNode = new Node(calculatedBoard, node, j+","+ i + "|",depth+1 ,cost+1);
						if(!usedNodes.contains(childNode) && !unusedNodes.contains(childNode)){
							unusedNodes.add(childNode);
						}	
					}	
			}	
		}
	}

	public boolean iteratorNodes(){
		Node node;
		while(unusedNodes.size()>=1){
			node = unusedNodes.poll();
			if(!checkwin(node)){
				createChilds(node);
			}
			else return true;
		}
		return false;
	}
	
	private void addChildNodes(Node node){
		final int depth = node.getDepth();
        final int cost = node.getCost();
		int[][] calculatedBoard;
		
		for(int i=1; i<=this.numberPieces; i++){
			for(int j=1; j<=4; j++){
					calculatedBoard = logic.fold(node.getBoard(), j, i);
					if(!compareBoards(node.getBoard(), calculatedBoard)){
						usedNodes.add(node);
						Node childNode = new Node(calculatedBoard, node, j+","+ i + "|",depth+1 ,cost+1);
						solve(childNode);
						if(this.solutionfound) break;	
					}
					if(this.solutionfound) break;
			}
			if(this.solutionfound) break;
		}
	}
	
	public boolean verifyFinalState(int[][]board){
		for (int i=0; i<board.length; i++){
			for(int j=0;j<board[0].length; j++){
				if(board[i][j] == 0) return false;
			}
		} 
		return true;
	}
	

	private boolean compareBoards(int[][] b1, int[][] b2){
		if(b1.length != b2.length) return false;
		if(b1[0].length != b2[0].length) return false;

		for(int i=0; i<b1.length; i++){
			for(int j=0; j< b1[0].length; j++){
				if(b1[i][j] != b2[i][j]) return false;
			}
		}
		return true;
	}

	public void getplays(Node solution){
		if(solution.parentNode != null){
			getplays(solution.parentNode);
		}
		if(solution.operation != null) {
			plays=plays+solution.operation;
		}
	}
		/*
	iremos representar a heuristica como a distancia ao objetivo.
		Ou seja, ter todo o tabuleiro preenchido
	Numero total de espaços - Numero de espaços vazios.
	*/

	public int heuristica(Node a){
			return a.getNodeBoardSize() - (a.getNodeBoardSize() - a.getPlacedPieces());
	}


	public int heuristicaCost(Node node){
		return heuristica(node) + node.getCost();
	}

	public boolean depthLimiter(Node node){
		if(node.getDepth()>=maxDepth){
			System.out.println("ERROR: Depth reached limit value");
			System.out.println("Try it yourself!");
			JFrame frame = new JFrame();
			frame.setBounds(200, 100, 500, 350);
			frame.setTitle("Couldn't find solution");
			JButton start = new JButton("Depth reached limit value!");
			frame.add(start);	
			JButton start2 = new JButton("Let me try then");
			frame.add(start2);			
			frame.setResizable(false);	

			frame.setVisible(true);
			return true;
		}
		return false;
	}
	
	public int calculateMaxPiece(int block_ID){

		int dist_i_ant=this.init_level_board.length;
		int dist_j_ant=this.init_level_board[0].length;
		int dist_i=0;
		int dist_j=0;
		System.out.println("Peca: "+ block_ID);
		for(int i=0; i < this.init_level_board.length; i++){
			for(int j=0; j < this.init_level_board[i].length; j++){
				if(this.init_level_board[i][j] == block_ID){
					dist_i=expand_vert(block_ID, i, j);
					if (dist_i < dist_i_ant){
						dist_i_ant=dist_i;
					}
					dist_j=expand_hor(block_ID, i, j);
					if(dist_j < dist_j_ant){
						dist_j_ant=dist_j;
					}
				}
			}
		}

		return dist_i*dist_j;
	}

	public int expand_vert(int block_ID,int i,int j){
		int dist=0;
		//expandir para cima
		for(int a=i; a>0;a--) {
			if( (this.init_level_board[a][j] != 0) && (this.init_level_board[a][j] != block_ID)) {
				dist=dist+1;
			}	
		}
		//expandir para baixo
		for(int a=i; a < this.init_level_board.length ;a++) {
			if( (this.init_level_board[a][j] == 0) || (this.init_level_board[a][j] == block_ID)) {
				dist=dist+1;
			}
			else break;
		}
		System.out.println("Distancia Vertical: "+ dist);
		return dist+1;
	}

	public int expand_hor(int block_ID,int i,int j){
		int dist=0;
		//expandir para lado esquerdo
		for(int a=j; a > 0;a--) {
			if( (this.init_level_board[i][a] == 0) || (this.init_level_board[i][a] == block_ID)) {
				dist=dist+1;
			}	
			else break;	
		}
		//expandir para lado direito
		for(int a=j; a < this.init_level_board[i].length ;a++) {
			if( (this.init_level_board[i][a] == 0) || (this.init_level_board[i][a] == block_ID)) {
				dist=dist+1;
			}	
			else break;
		}
		System.out.println("Distancia Horzontal: "+ dist);
		return dist+1;
	}
	private class Node{

		private int[][] board;
		private Node parentNode;
		private String operation;
		private int depth;
		private int cost;

		public Node(int[][] board, Node parentNode, String operation, int depth, int cost) {
            this.board = board;
            this.parentNode = parentNode;
            this.operation = operation;
            this.depth = depth;
			this.cost = cost;
		}
		
		public int[][] getBoard(){
			return this.board;
		}
		public Node getParentNode(){
			return this.parentNode;
		}
		public String getOperation(){
			return this.operation;
		}
		public int getDepth(){
			return this.depth;
		}
		public int getCost(){
			return this.cost;
		}

		public int getPlacedPieces(){
			int counter = 0;
			for(int i=0; i<this.board.length; i++){
				for(int j=0; j<this.board[0].length; j++){
					if(this.board[i][j] != 0 )counter++;
				}
			}
			return counter;
		}

		public int getNodeBoardSize(){
			return this.board.length * this.board[0].length;
		}

	}

} 
