import javax.swing.JButton;
import javax.swing.JFrame;

public class Logic {

		
	public int x_axis;
	public int y_axis;
	
	public int[] get_axis(int[][] mat, int move, int ID_block) {

		
		int x;
		int y;
		
            
        switch(move) {
          case 1: //left
        	int y_axis_before=mat[0].length;
              
           // Loop through all rows 
           for(x=0; x<mat.length; x++)
           { 
                  // Loop through all columns of current row 
                  for(y=0; y<mat[x].length; y++)
                  {
                	  
           			  if(mat[x][y]==ID_block) 
           			  {
           				  if (y <= y_axis_before) 
           				  {
           					  y_axis=y;
           					  y_axis_before=y_axis;
           					  x_axis=-1;
           				  }
           			  }
                   }
            }
          
            break;
            
          case 2: //right
          	 y_axis_before=0;
                
             // Loop through all rows 
             for(x=0; x<mat.length; x++)
             { 
                    // Loop through all columns of current row 
                    for(y=0; y<mat[x].length; y++)
                    {
                  	  
             			  if(mat[x][y]==ID_block) 
             			  {
             				  if (y >= y_axis_before) 
             				  {
             					  y_axis=y;
             					  y_axis_before=y_axis;
             					  x_axis=-1;
             				  }
             			  }
                     }
              }
             
              break;
              
          case 3: //up
           	 int x_axis_before=mat.length;
                 
              // Loop through all rows 
              for(x=0; x<mat.length; x++)
              { 
                     // Loop through all columns of current row 
                     for(y=0; y<mat[x].length; y++)
                     {
                   	  
              			  if(mat[x][y]==ID_block) 
              			  {
              				  if (x <= x_axis_before) 
              				  {
              					  x_axis=x;
              					  x_axis_before=x_axis;
              					  y_axis=-1;
              				  }
              			  }
                      }
               }
              
               break;
               
          case 4: //down
            	 x_axis_before=0;
                  
               // Loop through all rows 
               for(x=0; x<mat.length; x++)
               { 
                      // Loop through all columns of current row 
                      for(y=0; y<mat[x].length; y++)
                      {
                    	  
               			  if(mat[x][y]==ID_block) 
               			  {
               				  if (x >= x_axis_before) 
               				  {
               					  x_axis=x;
               					  x_axis_before=x_axis;
               					  y_axis=-1;
               				  }
               			  }
                       }
                }
               
                break;
              
        }
        
        int axis[]= {x_axis,y_axis}; //0 - eixo vertical; 1 - eixo horizontal
        return axis;

	}
	
public int[][] fold(int[][] mat, int move, int ID_block) {
		
		int axis[]=this.get_axis(mat, move, ID_block);
		int dist;
		int bloco_novo;
		int mat_aux[][]= Logic.cloneArray(mat);
		
    
    for(int x=0; x<mat.length; x++)
    { 
      for(int y=0; y<mat[x].length; y++)
      {
    		switch(move){
    			case 1: //left
    				if(mat[x][y]==ID_block) {
    					dist=(y-axis[1])+1;
    					bloco_novo=axis[1]-dist;
    					if(bloco_novo < 0 || bloco_novo >= mat[x].length || mat[x][bloco_novo] != 0) {
    						mat_aux=mat;
    						return mat;
    					}
    					mat_aux[x][bloco_novo]=ID_block;
    				}
    			break;
    			
    			  
    			case 2: //right
    				if(mat[x][y]==ID_block) {
    					dist=(axis[1]-y)+1;
    					bloco_novo=axis[1]+dist;
    					if(bloco_novo < 0 || bloco_novo >= mat[x].length || mat[x][bloco_novo] != 0) {
    							mat_aux=mat;
    							return mat;
    						}
    					mat_aux[x][bloco_novo]=ID_block;
    				}
    			break;
    			
    			case 3: //up
    				if(mat[x][y]==ID_block) {
    					dist=(x-axis[0])+1;
    					bloco_novo=axis[0]-dist;
    					if(bloco_novo < 0 || bloco_novo >= mat.length || mat[bloco_novo][y] != 0) {
    						mat_aux=mat;
    						return mat;
    					}
    					mat_aux[bloco_novo][y]=ID_block;
    				}
    			break;
    			
    			case 4: //down
    				if(mat[x][y]==ID_block) {
    					dist=(axis[0]-x)+1;
    					bloco_novo=axis[0]+dist;
    					if(bloco_novo < 0 || bloco_novo >= mat.length || mat[bloco_novo][y] != 0) {
    						mat_aux=mat;
    						return mat;
    					}
    					mat_aux[bloco_novo][y]=ID_block;
    				}
    			break;
    		}
      }
      
    }
    
    mat=mat_aux;
    if(isBoardFull(mat)){
		JFrame frame = new JFrame();
		frame.setBounds(200, 100, 500, 350);
		frame.setTitle("You Won");
		JButton start = new JButton("YOU WON!");
		frame.add(start);
		frame.setResizable(false);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
    return mat;

	}

	public boolean isBoardFull(int[][] board){
		for(int i=0; i<board[0].length;i++){
			for(int j=0; j<board.length; j++){
				if(board[j][i] == 0) return false;
			}
		}
		return true;
	}
public void print2D(int mat[][]) { 
    // Loop through all rows 
    for (int[] row : mat) { 

        // Loop through all columns of current row 
        for (int x : row) 
            System.out.print(x + " "); 
       System.out.println();
    }

}


public static int[][] cloneArray(int[][] src) {
    int length = src.length;
    int[][] target = new int[length][src[0].length];
    for (int i = 0; i < length; i++) {
        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
    }
    return target;
}

}
