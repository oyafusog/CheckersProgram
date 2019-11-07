// This puts "BoardUtility.java" into the package "com.checkersgame.gui
package com.checkersgame.gui;

// Imports "Player.java" and "Move.java"
import com.checkersgame.Player;
import com.checkersgame.Move;

/**
 * 	This Class is the Board Utility, holds available Moves and Jumps
 * 
 * http://www.bobnewell.net/nucleus/checkers.php?itemid=289
 * checker notation, indexes represent each single move, and jumps
 * available to kings.
 * 
 * To check for non kings determine if the moving is increasing or decreasing
 * based on player color.
 * 
 */
public class BoardUtility {
	
	// Represents Spaces visually adjacent
	public static int[][] singleMoves = new int[][]	{
		
		/**0**/  new int[] {4,5},
		/**1**/  new int[] {5,6},
		/**2**/  new int[] {6,7},
		/**3**/  new int[] {7},
		/**4**/  new int[] {0,8},
		/**5**/  new int[] {0,1,8,9},
		/**6**/  new int[] {1,2,9,10},
		/**7**/  new int[] {2,3,10,11},
		/**8**/  new int[] {4,5,12,13},
		/**9**/  new int[] {5,6,13,14},
		/**10**/ new int[] {6,7,14,15},
		/**11**/ new int[] {7,15},
		/**12**/ new int[] {8,16},
		/**13**/ new int[] {8,9,16,17},
		/**14**/ new int[] {9,10,17,18},
		/**15**/ new int[] {10,11,18,19},
		/**16**/ new int[] {12,13,20,21},
		/**17**/ new int[] {13,14,21,22},
		/**18**/ new int[] {14,15,22,23},
		/**19**/ new int[] {15,23},
		/**20**/ new int[] {16,24},
		/**21**/ new int[] {16,17,24,25},
		/**22**/ new int[] {17,18,25,26},
		/**23**/ new int[] {18,19,26,27},
		/**24**/ new int[] {20,21,28,29},
		/**25**/ new int[] {21,22,29,30},
		/**26**/ new int[] {22,23,30,31},
		/**27**/ new int[] {23,31},
		/**28**/ new int[] {24},
		/**29**/ new int[] {24,25},
		/**30**/ new int[] {25,26},
		/**31**/ new int[] {26,27} 
	};
	
	// Represents Spaces that are visually jumps
	public static int[][] singleJumps = new int[][]	{
		
		/**0**/  new int[] {9},
		/**1**/  new int[] {8,10},
		/**2**/  new int[] {9,11},
		/**3**/  new int[] {10},
		/**4**/  new int[] {13},
		/**5**/  new int[] {12,14},
		/**6**/  new int[] {13,15},
		/**7**/  new int[] {14},
		/**8**/  new int[] {1,17},
		/**9**/  new int[] {0,2,16,18},
		/**10**/ new int[] {1,3,17,19},
		/**11**/ new int[] {2,18},
		/**12**/ new int[] {5,21},
		/**13**/ new int[] {4,6,20,22},
		/**14**/ new int[] {5,7,21,23},
		/**15**/ new int[] {6,22},
		/**16**/ new int[] {9,25},
		/**17**/ new int[] {8,10,24,26},
		/**18**/ new int[] {9,11,25,27},
		/**19**/ new int[] {10,26},
		/**20**/ new int[] {13,29},
		/**21**/ new int[] {12,14,28,30},
		/**22**/ new int[] {13,15,29,31},
		/**23**/ new int[] {14,30},
		/**24**/ new int[] {17},
		/**25**/ new int[] {16,18},
		/**26**/ new int[] {17,19},
		/**27**/ new int[] {18},
		/**28**/ new int[] {21},
		/**29**/ new int[] {20,22},
		/**30**/ new int[] {21,23},
		/**31**/ new int[] {22}
	};
	
	// Simply translating move from 1-32 into how it's stored into the array 0-31
	public static Move TranslateMove(Move m) throws Exception {
		
		// Return the Move as how it's stored in an Array Index
		return new Move(m.start-1,m.end-1);
	}
	
	/**
	 * 	This checks to see if a regular piece can make a move,
	 * 	regardless of there being a piece in the end position
	 * 
	 * @param m
	 * @param p
	 * @return
	 */
	public static boolean isValidForwardMove(Move m, Player p)	{
		
		// Boolean to keep track if it can make this move
		boolean isInAvailableMoves = false;
		
		// Boolean to keep track if a piece is moving in the right direction
		boolean isCorrectDirection = false;
		
		// If the Player is "BLACK"
		if(p == Player.BLACK)	{
			
			// If the End is greater than the Start
			if(m.end > m.start)	{
				
				// You're going in the right direction
				isCorrectDirection = true;
			}
			
			// Checking all the SingleMoves you can do with i
			for(int i = 0; i < singleMoves[m.start].length; i++)	{
				
				// If the End is in the array of moves Start can make
				if(m.end == singleMoves[m.start][i])	{
					
					// Then the move is Available
					isInAvailableMoves = true; 
				}
			}
		} 
		// If the Player is "RED"
		else	{
			
			// If the End is less than the Start
			if(m.end < m.start)	{
				
				// You're going in the right direction
				isCorrectDirection = true;
			}
			
			// Checking all the SingleMoves you can do with i
			for(int i = 0; i < singleMoves[m.start].length; i++)	{
				
				// If the End is in the array of moves Start can make
				if(m.end == singleMoves[m.start][i])	{
					
					isInAvailableMoves = true; 
				}
			}
		}
		
		// Return True if you're allowed to make that move, and if it's in the right direction
		return isInAvailableMoves && isCorrectDirection;
	}
	
	/**
	 * 	Checks to see if the Jump is valid, using another isValidJump Method
	 * 
	 * @param m
	 * @return
	 */
	public static boolean isValidJump(Move m) {
		
		// Calls isValidJump using the start and end of the Move m
		return isValidJump(m.start, m.end);
	}
	
	/**
	 * 	Checks to see if you can make the jump, by looking at the array
	 * 
	 * @param start start number
	 * @param end end number
	 * @return
	 */
	public static boolean isValidJump(int start, int end)	{
		
		// Assume the Jump isn't valid
		boolean isJump = false;
		
		// Check the array of SingleJumps 
		for(int i = 0; i < singleJumps[start].length; i++)	{
			
			// If the End is on the list of SingleJumps
			if(singleJumps[start][i] == end)	{
				
				// Then the Jump is valid
				isJump = true;
			}
		}
		
		// Return whether the Jump is valid or not.
		return isJump;
	}
	
	/**
	 * 	Checks to see if you make this move by checking the array of "singleMoves"
	 * 
	 * @param m
	 * @return
	 */
	public static boolean isValidMove(Move m)	{
		
		// Assume the Move isn't valid
		boolean isMove = false;
		
		// Check the array of SingleMoves
		for(int i = 0; i < singleMoves[m.start].length; i++)	{
			
			// If the End is on the list of "singleMoves"
			if(singleMoves[m.start][i] == m.end)	{
				
				// Then the Move is valid
				isMove = true;
			}
		}
		
		// Return whether the Move is valid or not.
		return isMove;
	}
}

