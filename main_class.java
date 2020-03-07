package Logic;

import java.io.IOException;

public class main_class {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		GFG level1= new GFG(); //create object level1
		logica control= new logica();
	
		int move=1;
		int ID_block=1;
		
		level1.create_mat();
		control.fold(level1.mat,move,ID_block);
		System.out.println();
		level1.print2D(level1.mat);

		
	}

}
