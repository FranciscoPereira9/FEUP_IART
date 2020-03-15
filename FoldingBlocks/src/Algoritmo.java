//import java.util.ArrayList;
//import java.util.List;
//import java.util.PriorityQueue;
import java.util.HashMap;

public class Algoritmo {
		
	private int[][] board;
	private Logic logic = new Logic();
	private int numberPieces;
	public String plays = new String();
	public int chosenPiece = 0;
	//private List<Node> usedNodes;
	//private PriorityQueue<Node> unusedNodes;
	static final int maxDepth = 15;
	public boolean solutionfound=false;
	public Node solution;

	public Algoritmo(int[][] board, int level, int numberPieces) {
		this.board = board;
		//this.level = level;
		this.numberPieces = numberPieces;
		this.solution=null;
		this.plays=null;
		//this.usedNodes = new ArrayList<>();
		//this.unusedNodes = new PriorityQueue<>();
	}
	
	public boolean algoritmo1(){
		Node parentNode = new Node(this.board, null, "", 0, 0);
		
		return solve(parentNode);
	}

	public boolean verifyFinalState(int[][]board){
		for (int i=0; i<board.length; i++){
			for(int j=0;j<board[0].length; j++){
				if(board[i][j] == 0) return false;
			}
		} 
		return true;
	}

	private boolean solve(Node node){
		if(verifyFinalState(node.getBoard())){
			this.solutionfound=true;
			this.solution=node;
			getplays(node);
			return true;
		}
		else {
			addChildNodes(node);
			return false;
		}

	}

	private void addChildNodes(Node node){
		final int depth = node.getDepth();
        final int cost = node.getCost();
		int[][] calculatedBoard;
		
		for(int i=1; i<=this.numberPieces; i++){
			for(int j=1; j<=4; j++){
					calculatedBoard = logic.fold(node.getBoard(), j, i);
					if(!compareBoards(node.getBoard(), calculatedBoard)){
						Node childNode = new Node(calculatedBoard, node, j+","+ i + "|",depth+1 ,cost+1);
						solve(childNode);
						if(this.solutionfound) break;	
					}
					if(this.solutionfound) break;
			}
			if(this.solutionfound) break;
		}
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
			System.out.println(solution.getOperation());
		}
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
	}
		/*
			1. Analisar tabuleiro atual se é o final
				1.1. Se sim, retornar jogadas que levaram ao atual
				1.2. Se não continuar algoritmo
			2. Gerar tabuleiros "filhos" usando operadores e guardar numa lista comum
					
			3. Analisar <lista> à procura do melhor valor e voltar a 1
			
			Cada node na lista precisa do seu tabuleiro e da forma como foi gerado
		 */
	
} 

