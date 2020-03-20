import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.util.Collections;

public class Algoritmo {

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
	private	int saver ;
	private	int value ;
	private boolean stuck = false;
	private int counterAux = 0;
	private int algoritmoEscolhido;

	public Algoritmo(int[][] board, int level, int numberPieces, int algoritmo) {
		this.board = board;
		this.numberPieces = numberPieces;
		this.solution=null;
		this.plays="";
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
				return heuristicaCost(n1) - heuristicaCost(n2);
			});
			break;
		}
	
		this.usedNodes = new ArrayList<>();
		this.saver = -1;
		this.value = this.board.length* this.board[0].length;
		this.algoritmoEscolhido = algoritmo;
		
	}
	
	public boolean algoritmo1(){
		Node parentNode = new Node(this.board, null, "", 0, 0);
		
		unusedNodes.add(parentNode);

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
		// profundidade
		if(this.algoritmoEscolhido == 1){

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
		
		// greedy ou A*
		else {
				if(verifyFinalState(node.getBoard())){
				this.solutionfound=true;
				this.solution=node;
				System.out.println("Game Won");
				System.out.println("Used nodes: " + usedNodes.size());
				getplays(node);
				return true;
			}
			
			addChildsGreedyAstar(node);
		
			Node newNode;

			do{
				
	        	newNode = unusedNodes.poll();
			
	        	if(newNode == null)
	           		return false;
			}
	        while(newNode.getDepth() >=20);

	        return analyzeGreedyAstar(newNode);
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
		}
	}

	/*iremos representar a heuristica como a distancia ao objetivo
		Ou seja
	Numero total de espaços - Numero de espaços preenchidos.
	*/
	public int heuristica(Node a){
			return a.getNodeBoardSize() - a.getPlacedPieces();
	}

	public void addChildsGreedyAstar(Node node){

		final int depth = node.getDepth();
        final int cost = node.getCost();
		int[][] calculatedBoard;
		
		for(int i=1; i<=this.numberPieces; i++){
			for(int j=1; j<=4; j++){	
				calculatedBoard = logic.fold(node.getBoard(), j, i);
				if(!compareBoards(node.getBoard(), calculatedBoard)){
					usedNodes.add(node);
					Node childNode = new Node (calculatedBoard, node, j+","+ i + "|",depth+1 ,cost+1);
					if(!unusedNodes.contains(childNode) && !usedNodes.contains(childNode)){
						unusedNodes.add(childNode);
					}
					else {
						System.out.println("already used");
					}		
				}
			}
		}

	}

	public boolean analyzeGreedyAstar(Node a){
	
		if(verifyFinalState(a.getBoard())){
			this.solutionfound=true;
			this.solution=a;
			System.out.println("Game Won");
			System.out.println("Used nodes: " + usedNodes.size());
			getplays(a);
			return true;
		}
		
		addChildsGreedyAstar(a);
	
		Node newNode;

		do{
			
        	newNode = unusedNodes.poll();
		
        	if(newNode == null)
           		return false;
		}
        while(newNode.getDepth() >=20);

        return analyzeGreedyAstar(newNode);
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
		/*
			1. Analisar tabuleiro atual se é o final
				1.1. Se sim, retornar jogadas que levaram ao atual
				1.2. Se não continuar algoritmo
			2. Gerar tabuleiros "filhos" usando operadores e guardar numa lista comum
					
			3. Analisar <lista> à procura do melhor valor e voltar a 1
			
			Cada node na lista precisa do seu tabuleiro e da forma como foi gerado
		 */
	
} 

