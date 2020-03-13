import java.util.List;
import java.util.PriorityQueue;

public class Algoritmo {
		
	private int[][] board;
	private Level level;
	private int numberPieces;
	private String plays = new String();
	public int chosenPiece = 0;
	private List<Node> usedNodes;
	private PriorityQueue<Node> unusedNodes;
	static final int maxDepth = 15;

	public Algoritmo(int[][] board, Level level) {
		this.board = board;
		this.level = level;
		this.numberPieces = level.getNumberPieces();
	}
	
	public boolean verifyFinalState(int[][]board){
		for (int i=0; i<board.length; i++){
			for(int j=0;j<board[0].length; j++){
				if(board[j][i] == 0) return false;
			}
		} 
		return true;
	}

	private boolean algoritmo1(){
		Node parentNode = new Node(this.board, null, "", 0, 0);

		return solve(parentNode);
	}
	
	private boolean solve(Node node){
		if(verifyFinalState(node.getBoard())){
			//Grab the solutions;
			return true;
		}

		//addChildNodes(node);

		Node newNode;
        do {
            newNode = unusedNodes.poll();
            if(newNode == null)
                return false;
        }    
        while(newNode.getDepth() >= maxDepth);

        return solve(newNode);
	}

	private class Node{

		private int[][]board;
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

