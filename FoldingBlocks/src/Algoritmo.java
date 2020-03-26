import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.lang.Math;
import java.util.*;

public class Algoritmo {
	
	private int [][] init_level_board;
	private int[][] board;
	private Logic logic = new Logic();
	private int numberPieces;
	public String plays = new String();
	public int chosenPiece = 0;
	private ArrayList<Integer> maxSizePieces;
	private PriorityQueue<Node> unusedNodes;
	private List<Node> usedNodes;
	static final int maxDepth = 20;
	public boolean solutionfound=false;
	public Node solution;
	private int algoritmoEscolhido;
	private long timeStart;
	private long timeFinish;
	public int counter=0;

	public Algoritmo(int[][] board, int level, int numberPieces, int algoritmo) {
		this.board = board;
		this.numberPieces = numberPieces;
		this.solution=null;
		this.plays="";
		this.init_level_board=board;
		this.maxSizePieces= new ArrayList<Integer>();
		this.timeStart = System.currentTimeMillis();

		for(int i=1; i <=numberPieces; i++){
			int aux=calculateMaxPiece(i);
			maxSizePieces.add(aux);
		}
	    switch(algoritmo){
	    	case 1:
	    	/*
	    		O algoritmo é o Depth First Search, onde a fila n vai ser usada, mas tem de ser inicializada na mesma.
	    	*/
	 	    	this.unusedNodes = new PriorityQueue<>((Node n1, Node n2) -> {
				return n2.getCost() - n1.getCost();
			});
	    	break;
			
			case 2:
			// caso seja greedy a fila é organizada da seguinte forma;
			this.unusedNodes = new PriorityQueue<>((Node n1, Node n2) -> {
				return heuristica2(n2) - heuristica2(n1);
			});
			break;
			
			// caso seja Astar a fila é organizada da seguinte forma;
			case 3:
			// caso seja Astar a fila é organizada da seguinte forma;
				this.unusedNodes = new PriorityQueue<>((Node n1, Node n2) -> {
				return heuristicaCost2(n2) - heuristicaCost2(n1);
			});
			break;
			case 4:
			// caso seja greedy a fila é organizada da seguinte forma;
			this.unusedNodes = new PriorityQueue<>((new NodeComparator()));
			break;
		}
	
		this.usedNodes = new ArrayList<>();
		this.algoritmoEscolhido = algoritmo;
		
	}
	
	/*
		Cria o 1º no. O no Pai, que vai ser adicionado a priorityqueue, para ser explorado.
	*/
	public boolean algoritmo1(){
		Node parentNode = new Node(this.board, null, "", 0, 0);
		unusedNodes.add(parentNode);
	    return solve(parentNode);
	}
	
	/*
		Resolve o problema conforme o pedido do utilizador.
		1- o algoritmo escolhido foi o Depth First
		2- o algoritmo escolhido foi o Greedy
		3- o algoritmo escolhido foi o A*
	*/
	public boolean solve(Node parentNode){
			/*Verifica se o no ja se encontra no estado final. Caso nao se verifique, criam-se os filhos*/
			if(this.algoritmoEscolhido==1){
				checkwin(parentNode);
				addChildNodes(parentNode);
			}
			/*Vai a fila de prioridade buscar o primeiro elemento, verifica se esta no estado final, caso nao se verifique cria os filhos*/
			else{
				if(iteratorNodes()) return true;
				createChilds(parentNode);
			}
		return false;
	}
	
	/*
		Verifica se o no se encontra no estado final.
	*/
	public boolean checkwin(Node node){
		if(verifyFinalState(node.getBoard())){
			timeFinish = System.currentTimeMillis();
			System.out.println();
			System.out.println("The algorithm managed to finish the game:");
			System.out.println(" - Number of plays: "+ node.getDepth());
			System.out.println(" - Used nodes: " + usedNodes.size());
			System.out.println(" - Time used: " + (timeFinish - timeStart) + "ms");
			this.solution=node;
			this.solutionfound=true;
			getplays(node);
			return true;
		}
		
		return false;
	}
	
	/*Cria os filhos do no e adiciona as respetivas lista e fila*/
	public void createChilds(Node node){
		final int depth = node.getDepth();
        final int cost = node.getCost();
		int[][] calculatedBoard;
		usedNodes.add(node);
		for(int i=1; i<=this.numberPieces; i++){
			for(int j=1; j<=4; j++){
					calculatedBoard = logic.fold(node.getBoard(), j, i);
					if(!compareBoards(node.getBoard(), calculatedBoard)){
						Node childNode = new Node(calculatedBoard, node, j+","+ i + "|",depth+1 ,cost+1,maxSizePieces.get(i-1),heuristica3(node));
						if(!usedNodes.contains(childNode) && !unusedNodes.contains(childNode)){
							unusedNodes.add(childNode);
						}	
					}	
			}	
		}
	}
	
	/*itera sobre a fila de prioridade ate criando os filhos e avaliando os nos que vao sendo colocados na primeira posiçao
	ate encontrar soluçao
	*/
	public boolean iteratorNodes(){
		Node node;
		while(unusedNodes.size()>=1){
			counter++;
			node = unusedNodes.poll();
			//System.out.printf("Node: "+counter+" Value: %.4f \n",node.nodeValue);
			if(!checkwin(node)){
				createChilds(node);
			}
			else return true;
		}
		return false;
	}
	
	/*Utilizado para o DFS. Responsavel por criar os filhos e analizar o no criado. percorrendo a arvore em profundidade ate encontrar a soluçao*/
	private void addChildNodes(Node node){
		final int depth = node.getDepth();
        final int cost = node.getCost();
		int[][] calculatedBoard;
		usedNodes.add(node);
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
	
	/*Verifica se o tabuleiro esta no estado final*/
	public boolean verifyFinalState(int[][]board){
		for (int i=0; i<board.length; i++){
			for(int j=0;j<board[0].length; j++){
				if(board[i][j] == 0) return false;
			}
		} 
		return true;
	}
	
	/*Compara dois tabuleiros*/
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
	/*Responsavel por ir buscar aos nos as respetivas operaçoes.*/
	public void getplays(Node solution){
		if(solution.parentNode != null){
			getplays(solution.parentNode);
		}
		if(solution.operation != null) {
			plays=plays+solution.operation;
		}
	}
	
	
	/*Declaraçao de duas heuristicas a ser utilizadas nas filas de prioridade - greedy*/
	public int heuristica1(Node a){
		return a.getPlacedPieces();
	}
	
	public int heuristica2(Node node){
		return node.getMaxPiece() + node.getPlacedPieces();
	}

	public double heuristica3(Node node){
		return log2((double)node.getNodeBoardSize()/(double)node.getPlacedPieces())*this.numberPieces;
	}

	/*representa a heuristica combinada com o custo - A* */
	public int heuristicaCost1(Node node){
		return heuristica1(node) - node.getCost();
	}	

	public int heuristicaCost2(Node node){
		return heuristica2(node) - node.getCost();
	}	
	
	/*Limitador de profundidade. 
	N~ao esta a ser utilizado
	*/
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
		int a1_ant=0;
		int a2_ant=0;
		int a1=0;
		int a2=0;

		for(int i=0; i < this.init_level_board.length; i++){
			for(int j=0; j < this.init_level_board[i].length; j++){
				if(this.init_level_board[i][j] == block_ID){
					a1=expand_vert1(block_ID, i, j);
					if(a1>a1_ant) a1_ant=a1;
					a2=expand_hor2(block_ID, i, j);
					if(a2>a2_ant) a2_ant=a2;
				}
			}
		}

		if(a1_ant>a2_ant)return a1_ant;
		else return a2_ant;
	}

	public int expand_vert1(int block_ID,int line,int col){
		int distv=0;
		int limit_up=0;
		int limit_down=this.init_level_board.length-1;

		//expandir para cima
		for(int a=line; a>=0;a--) {
			if( (this.init_level_board[a][col] == 0) || (this.init_level_board[a][col] == block_ID)) {
				distv=distv+1;
				limit_up=a;
			}	
			else if ((this.init_level_board[a][col] != block_ID) && (this.init_level_board[a][col] != 0)) {
				limit_up=a+1;
				break;
			}
			else {
				limit_up=a;
				break;
			}
		}
		//expandir para baixo
		for(int b=line+1; b < this.init_level_board.length ;b++) {
			if( (this.init_level_board[b][col] == 0) || (this.init_level_board[b][col] == block_ID)) {
				distv=distv+1;
				limit_down=b;
			}
			else if ((this.init_level_board[b][col] != block_ID) && (this.init_level_board[b][col] != 0)) {
				limit_down=b-1;
				break;
			}
			else{
				limit_down=b;
				break;
			} 
		}

		int disth=0;
		int disth_ant=this.init_level_board[0].length;
		for(int i=limit_up; i <= limit_down; i++){
			disth=expand_hor1(block_ID, i, col);
			if(disth < disth_ant){
				disth_ant=disth;
			}
		}

		int c=0;
		for(c=0; Math.pow(2,c) <= distv; c++);
		distv=(int) Math.pow(2, c-1);

		return distv*disth_ant;
	}

	public int expand_hor1(int block_ID,int line,int col){
		int dist=0;
		int limit_left=0;
		int limit_right=this.init_level_board[0].length-1;
		//expandir para lado esquerdo
		for(int a=col; a >= 0;a--) {
			if( (this.init_level_board[line][a] == 0) || (this.init_level_board[line][a] == block_ID)) {
				dist=dist+1;
				limit_left=a;
			}	
			else if ((this.init_level_board[line][a] != block_ID) && (this.init_level_board[line][a] != 0)) {
				limit_left=a+1;
				break;
			}
			else{
				limit_left=a;
				break;
			} 
		}
		//expandir para lado direito
		for(int b=col+1; b < this.init_level_board[line].length ;b++) {
			if( (this.init_level_board[line][b] == 0) || (this.init_level_board[line][b] == block_ID)) {
				dist=dist+1;
				limit_right=b;
			}	
			else if ((this.init_level_board[line][b] != block_ID) && (this.init_level_board[line][b] != 0)) {
				limit_right=b-1;
				break;
			}
			else{
				limit_right=b;
				break;
			} 
		}

		int c=0;
		for(c=0; Math.pow(2,c) <= dist; c++);
		dist= (int) Math.pow(2, c-1);

		return dist;
	}

	public int expand_hor2(int block_ID,int line,int col){
		int disth=0;
		int limit_left=0;
		int limit_right=this.init_level_board[0].length-1;;
		//expandir para lado esquerdo
		for(int a=col; a >= 0;a--) {
			if( (this.init_level_board[line][a] == 0) || (this.init_level_board[line][a] == block_ID)) {
				disth= disth + 1;
				limit_left = a;
			} else if ((this.init_level_board[line][a] != block_ID) && (this.init_level_board[line][a] != 0)) {
				limit_left = a + 1;
				break;
			} else {
				limit_left = a;
				break;
			}
		}
		// expandir para lado direito
		for (int b = col + 1; b < this.init_level_board[line].length; b++) {
			if ((this.init_level_board[line][b] == 0) || (this.init_level_board[line][b] == block_ID)) {
				disth = disth + 1;
				limit_right = b;
			} else if ((this.init_level_board[line][b] != block_ID) && (this.init_level_board[line][b] != 0)) {
				limit_right = b - 1;
				break;
			} else {
				limit_right = b;
				break;
			}
		}

		int distv = 0;
		int distv_ant = this.init_level_board.length;
		for (int i = limit_left; i <= limit_right; i++) {
			distv = expand_vert2(block_ID, line, i);
			if (distv < distv_ant) {
				distv_ant = distv;
			}
		}

		int c = 0;
		for (c = 0; Math.pow(2, c) <= disth; c++);
		disth = (int) Math.pow(2, c - 1);

		return disth*distv_ant;
	}

	public int expand_vert2(int block_ID,int line,int col){
		int distv=0;
		int limit_up=0;
		int limit_down=this.init_level_board.length-1;

		//expandir para cima
		for(int a=line; a>=0;a--) {
			if( (this.init_level_board[a][col] == 0) || (this.init_level_board[a][col] == block_ID)) {
				distv=distv+1;
				limit_up=a;
			}	
			else if ((this.init_level_board[a][col] != block_ID) && (this.init_level_board[a][col] != 0)) {
				limit_up=a+1;
				break;
			}
			else {
				limit_up=a;
				break;
			}
		}
		//expandir para baixo
		for(int b=line+1; b < this.init_level_board.length ;b++) {
			if( (this.init_level_board[b][col] == 0) || (this.init_level_board[b][col] == block_ID)) {
				distv=distv+1;
				limit_down=b;
			}
			else if ((this.init_level_board[b][col] != block_ID) && (this.init_level_board[b][col] != 0)) {
				limit_down=b-1;
				break;
			}
			else{
				limit_down=b;
				break;
			} 
		}

		int c=0;
		for(c=0; Math.pow(2,c) <= distv; c++);
		distv=(int) Math.pow(2, c-1);
	
		return distv;
	}

	public static double log2(double x) {
    	return (Math.log(x) / Math.log(2));
	}

	private class Node{

		private int[][] board;
		private Node parentNode;
		private String operation;
		private int depth;
		private int cost;
		private int maxPieceSize;
		private double nodeValue;

		public Node(int[][] board, Node parentNode, String operation, int depth, int cost) {
            this.board = board;
            this.parentNode = parentNode;
            this.operation = operation;
            this.depth = depth;
			this.cost = cost;
			this.maxPieceSize=0;
			this.nodeValue=0;
		}

		

		public Node(int[][] board, Node parentNode, String operation, int depth, int cost, int maxPieceSize, double heur) {
            this.board = board;
            this.parentNode = parentNode;
            this.operation = operation;
            this.depth = depth;
			this.cost = cost;
			this.maxPieceSize=maxPieceSize;
			this.nodeValue= heur;
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
					if(this.board[i][j] != 0 ) counter++;
				}
			}
			return counter;
		}
		
		public int getMaxPiece() {
			return this.maxPieceSize;
		}

		public int getNodeBoardSize(){
			return this.board.length * this.board[0].length;
		}

		/**
		 * @return the nodeValue
		 */
		public double getNodeValue() {
			return this.nodeValue;
		}


	}

	class NodeComparator implements Comparator<Node>{ 
              
		// Overriding compare()method of Comparator  
		// for descending order of node.value
		public int compare(Node n1, Node n2) { 
			return (int) (n1.getNodeValue() - n2.getNodeValue());
		}
	}
	
} 
