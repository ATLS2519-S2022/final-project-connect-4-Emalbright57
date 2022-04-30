/* *****************\************************************************************
 *  Name:    Em Albright
 *           maal6379@colorado.edu
 *
 *  Description:  refines minmax player implimentation with alpha beta pruning
 *  Written:      4/29/22 
 *  
 *
 *  Credits:
 *  https://stackoverflow.com/questions/36146480/implementing-a-minimax-algorithm-in-java-for-connect-4
 *
 **************************************************************************** */



public class AlphaBetaPlayer implements Player {
	int id; 
	int opponent_id;
    int cols; 
    int alpha;
    int beta;
    
    /* @retrun name for the computer player 
     */
    public String name() {
        return "Prudance";
    }

    /*@para id- player
     * @para msesPerMove - depth of board
     * @para rows - how many rows in the board
     * @para cols - how many columns in the board
     */
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id; //id is your player's id, opponent's id is 3-id
    	this.cols = cols;
    	opponent_id = 3-id;
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
        
        int move = 0; 
        int maxDepth = 1;
        
        // while there's time left and maxDepth <= number of moves remaining
        // while there's time left and maxDepth <= number of moves remaining
        while(!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) {
	        int bestScore = -1000;
        	int [] scoreKeep = new int[7]; //array that stores temporary scores to eventually see which score is the highest
            // Find maximum score for all possible moves  
            for (int i = 0 ; i < cols ; i++) { //Runs through each column and finds the highest score if there is a possible move
            	if (board.isValidMove(i)) { //make sure move is valid
    	        	board.move(i, id);
    	        	scoreKeep[i] = alphabetapruning(board, maxDepth - 1, alpha, beta, false, arb);
    	            board.unmove(i, id);
                    if (scoreKeep[i] > bestScore) {
                	bestScore = scoreKeep[i];
    	            move = i;
                }                
              }           	
            }
            arb.setMove(move);
            maxDepth++;
        }        
    }
    /*@para board- connect 4 board
     * @para depth - depth of board
     * @para alpha - value of alpha node
     * @beta - value of beta node
     * @para isMaximizing - boolean value
     * @para arb - communicates between player and board
     * 
     * 
     * @retrun best score - best possible score for the player
     *  
     */
    public int alphabetapruning(Connect4Board board, int depth, int alpha, int beta, boolean isMaximizing, Arbitrator arb) {
    	
//    	if depth = 0 or there's no moves left or time is up
//    			return the heuristic value of node 
    	
    	if (depth == 0 || board.numEmptyCells() == 0 || arb.isTimeUp()) {
    		return score(board);
    	}
    	
//    	
    	
    	
    	if(isMaximizing) {
    		int bestScore = -1000;
    		for (int i = 0 ; i < cols ; i++) { //iterates through columns
    			if (board.isValidMove(i)) { //if move exists
    			board.move(i, id);
    			bestScore = Math.max(bestScore, alphabetapruning(board, depth - 1, alpha, beta, false, arb));
    			board.unmove(i, id);
                alpha = Math.max(alpha, bestScore);  
                	if (alpha >= beta) {
                    break;   //do not make move is alpha node is greater than beta node
                	}
    			}
            }
            return bestScore;
        }
    	
    	 else {
    		 int bestScore = 1000; 
     		for (int i = 0 ; i < cols ; i++) {
     			if (board.isValidMove(i)) {
     			board.move(i, opponent_id);
     			bestScore = Math.min(bestScore, alphabetapruning(board, depth - 1,alpha, beta, true, arb));
     			board.unmove(i, opponent_id);
    	        beta = Math.min(beta, bestScore);
    	            if(alpha >= beta) {  
    	                break;  //do not make move is alpha node is greater than beta node
    	            }
    	        }
    	 }
    	        return bestScore;
    	    }
    	}
    
    
    // your score - opponent's score
    public int score(Connect4Board board) {
    	return calcScore(board, id) - calcScore(board, opponent_id);
    }
    
    
	// Return the number of connect-4s that player #id has.
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