/* *****************\************************************************************
 *  Name:    Em Albright
 *           maal6379@colorado.edu
 *
 *  Description:  Impliments a 'greedy' AI program to play a connect 4 game against a player
 *
 *  Written:      4/29/22 
 *  
 *
 *  Credits:
 *   Lily Gabriel
 *   Lily Battin
 *
 **************************************************************************** */




public class GreedyPlayer implements Player
	{
	    int id;
	    int cols;

	    /* @retrun name for the computer player 
	     */
	    public String name() {
	        return "Greedy Boi";
	    }

	    /*@para id- player
	     * @para msesPerMove - depth of board
	     * @para rows - how many rows in the board
	     * @para cols - how many columns in the board
	     */
	    public void init(int id, int msecPerMove, int rows, int cols) {
	    	this.id = id; //your id is id, opponents is 3-id
	    	this.cols = cols;
	    }

	    /*@para board- connect 4 board
	     * @para oppMoveCol - number of moves the oponent made
	     * @para arb - communicates between player and board
	     * 
	     * @retrun best score - best possible score for the player
	     *  
	     */
	    public void calcMove(
	        Connect4Board board, int oppMoveCol, Arbitrator arb) 
	        throws TimeUpException {
	        // Make sure there is room to make a move.
	        if (board.isFull()) {
	            throw new Error ("Complaint: The board is full!");
	        }
	       
	        // Make a random valid move.
	        //find maximum of relative scores for all possible moves
	      int col = 0;
	      int [] scoreKeep = new int[7]; //holds temporary scores for evaluating best options
	      int bestScore = -1000; //holds largest value for the array
	      // Find maximum score from all possible moves
	      for (int i=0 ; i<cols ; i++) { 
	    	  if (board.isValidMove(i)) {
	    		  board.move(i, 3-id); 
	              scoreKeep[i] = calcScore (board, 3-id);
	              board.unmove(i, 3-id);
	              if (scoreKeep[i]>bestScore) { //if score is larger than the max score, make that value equal to the max score
	                  bestScore=scoreKeep[i];
	                  col = i; 
	                    }
	                  }
	                }
	                arb.setMove(col);
	            }
	        
	     
	    
	    
	    
	    
	    
	    public int calcScore(Connect4Board board, int id)
		{
			final int rows = board.numRows();
			final int cols = board.numCols();
			int score = 0;
			// Look for horizontal connect-4s.
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c <= cols - 4; c++) {
					if (board.get(r, c + 0) != id) continue;
					if (board.get(r, c + 1) != id) continue;
					if (board.get(r, c + 2) != id) continue;
					if (board.get(r, c + 3) != id) continue;
					score++;
				}
			}
			// Look for vertical connect-4s.
			for (int c = 0; c < cols; c++) {
				for (int r = 0; r <= rows - 4; r++) {
					if (board.get(r + 0, c) != id) continue;
					if (board.get(r + 1, c) != id) continue;
					if (board.get(r + 2, c) != id) continue;
					if (board.get(r + 3, c) != id) continue;
					score++;
				}
			}
			// Look for diagonal connect-4s.
			for (int c = 0; c <= cols - 4; c++) {
				for (int r = 0; r <= rows - 4; r++) {
					if (board.get(r + 0, c + 0) != id) continue;
					if (board.get(r + 1, c + 1) != id) continue;
					if (board.get(r + 2, c + 2) != id) continue;
					if (board.get(r + 3, c + 3) != id) continue;
					score++;
				}
			}
			for (int c = 0; c <= cols - 4; c++) {
				for (int r = rows - 1; r >= 4 - 1; r--) {
					if (board.get(r - 0, c + 0) != id) continue;
					if (board.get(r - 1, c + 1) != id) continue;
					if (board.get(r - 2, c + 2) != id) continue;
					if (board.get(r - 3, c + 3) != id) continue;
					score++;
				}
			}
			return score;
		}
	}


