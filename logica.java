package Logic;


public class logica{
		
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
        
        int axis[]= {x_axis,y_axis};
        return axis;

	}
	
	public void fold(int[][] mat, int move, int ID_block) {
		
		int axis[]=this.get_axis(mat, move, ID_block);
		int dist;
		int bloco_novo;
		int mat_aux[][]=mat;
		
		switch(move){
			case 1: //left
			
			  for(int x=0; x<mat.length; x++)
	          { 
	                 // Loop through all columns of current row 
	                 for(int y=0; y<mat[x].length; y++)
	                 {
	                	 if(mat[x][y]==ID_block) 
	          			  {
	                		 dist=(y-axis[1])+1;
	                		 bloco_novo=axis[1]-dist;
	                		 if(bloco_novo < 0 || bloco_novo >= mat[x].length)
	                		 {
	                			 mat=mat_aux;
	                			 return;
	                		 }
	                		 mat[x][bloco_novo]=ID_block;
	          			  }
	                 }
	          }
			  break;
			  
			case 2: //right
				for(int x=0; x<mat.length; x++)
		          { 
		                 // Loop through all columns of current row 
		                 for(int y=0; y<mat[x].length; y++)
		                 {
		                	 if(mat[x][y]==ID_block) 
		          			  {
		                		 dist=(axis[1]-y)+1;
		                		 bloco_novo=axis[1]+dist;
		                		 if(bloco_novo < 0 || bloco_novo >= mat[x].length)
		                		 {
		                			 mat=mat_aux;
		                			 return;
		                		 }
		                		 mat[x][bloco_novo]=ID_block;
		          			  }
		                 }
		          }
				
			break;
			
			case 3: //up
				for(int x=0; x<mat.length; x++)
		          { 
		                 // Loop through all columns of current row 
		                 for(int y=0; y<mat[x].length; y++)
		                 {
		                	 if(mat[x][y]==ID_block) 
		          			  {
		                		 dist=(x-axis[0])+1;
		                		 bloco_novo=axis[0]-dist;
		                		 if(bloco_novo < 0 || bloco_novo >= mat.length)
		                		 {
		                			 mat=mat_aux;
		                			 return;
		                		 }
		                		 mat[bloco_novo][y]=ID_block;
		          			  }
		                 }
		          }
			break;
			
			case 4: //down
				for(int x=0; x<mat.length; x++)
		          { 
		                 // Loop through all columns of current row 
		                 for(int y=0; y<mat[x].length; y++)
		                 {
		                	 if(mat[x][y]==ID_block) 
		          			  {
		                		 dist=(axis[0]-x)+1;
		                		 bloco_novo=axis[0]+dist;
		                		 if(bloco_novo < 0 || bloco_novo >= mat.length)
		                		 {
		                			 mat=mat_aux;
		                			 return;
		                		 }
		                		 mat[bloco_novo][y]=ID_block;
		          			  }
		                 }
		          }
				break;
		}
		
	}
	
	
	
	
}
