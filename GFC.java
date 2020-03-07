package Logic;

class GFG { 
	
	public int mat [][];
  
    public void print2D(int mat[][]) 
    { 
        // Loop through all rows 
        for (int[] row : mat) { 
  
            // Loop through all columns of current row 
            for (int x : row) 
                System.out.print(x + " "); 
           System.out.println();
        }

        
        
    } 
  
    public void create_mat()
    { 
    	int mat_aux [][]= {{0,0,0,0},
    						{1,1,0,0},
    						{0,0,0,0},
    						{2,2,0,0}};
    	this.mat=mat_aux;
    	
        print2D(this.mat); 
    } 
    
    
} 