import javax.swing.Timer;

public class Algoritmo {
	
	private int[][] board;
	private Level level;
	private int numberPieces;
	
	public int chosenPiece = 0;
	
	public Algoritmo(int[][] board, Level level) {
		this.board = board;
		this.level = level;
		this.numberPieces = level.getNumberPieces();
	}
	
	public int chooseBiggerPiece() {
		int piecesCounter = 0;;
		int auxiliar = 0;
		 for (int i=1; i<=this.numberPieces; i++) {
			 for(int j=0;j<level.getLevel_sizeX();j++) {
				 for(int k=0;k<level.getLevel_sizeY();k++) {
					 if(this.board[k][j] == i) {
						 auxiliar++;
					 }
				 }
				 
			 }
			 if(piecesCounter < auxiliar) {
				 piecesCounter = auxiliar;
				 chosenPiece = i;
			 }
			 auxiliar = 0;
		 }
		 return chosenPiece;
	}
	
	public int chooseMove(int piece) {
		int move=0;
		int posX=0;
		int posY=0;
		boolean isxOK = true;
		boolean isyOK = true;
		for(int j=0;j<level.getLevel_sizeX();j++) {
			 for(int k=0;k<level.getLevel_sizeY();k++) {
	
				 if(this.board[k][j] == piece) {
					 posX = j;
					 posY = k;
					 // Verifica se na linha em que se encontra a peça, está tudo vazio ou se é a continuação desta mesma peça
					 for(int i = posX+1; i<level.getLevel_sizeX(); i++) {
							if(this.board[posY][i]!=0 && this.board[posY][i]!=piece)isxOK = false;
						}
					 // Verifica se na coluna em que se encontra a peça, está tudo vazio ou se é a continuação desta mesma peça
					for(int i = posY+1; i<level.getLevel_sizeY(); i++) {
						if(this.board[i][posX]!=0 && this.board[i][posX]!=piece)isyOK = false;
					}
				 }
			 }
		}
		
		System.out.println("X OK?" + isxOK);
		System.out.println("Y OK?" + isyOK);
	
	// Move não faz nada para além das verificações para já
	return move;
	}
	
}
