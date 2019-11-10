// This places "MinMax.java" into the package "com.agent"
package com.agent;

// Import "com.checkersgame" package
import com.checkersgame.*;

/*
 * 	Apologies for how far I've got with min/max, I
 * attempted my best from the understanding 
 * of the code. I've only gone through the
 * package com.checkersgame and BoardUtility.java
 * and I'll have to continue to go through everything. 
 * This is one of my  first large scale projects, 
 * besides the TSP we've done thus far. I've 
 * learned a lot already from just going through this code.
 * Again, I'm sorry if my skills can't offer
 * as much as necessary, but I'm willing to put
 * in the time to do what I can. Anyway, I hope 
 * that his will help contribute. I'll keep
 * trying to figure it out. 
 * 	
 * 								- Nick Calleja
 */

// This is MinMax Class
public class MinMax {
	
	// The A.I. Player
	Player computer;
	
	// The State of the Game
	Piece[] state;
	
	// The Game of Checkers
	CheckersGame game;
	
	int currentDepth = 0;
	int bestVal;
	int bestMove;
	
	// Constructor for the Min Max Tree
	public MinMax(CheckersGame game)	{
		
		this.game = game;
		this.state = game.boardspot;
		this.computer = game.playersTurn;
	}
	
	
	
	/*	Min/Max Setup (2-Ply):	
	 * 
	 * Ex:
	 * 	MAX:				STATE: Chooses "2"
	 * 	MIN:		STATE: 1	STATE: 2
	 * 	MAX:	(STATE: 4	STATE: 1)	(STATE: 2	STATE: 4)
	 * 
	 * 	We'll want to choose the Maximum of the worst move,
	 * because that's going to be the move our opponent chooses. 
	 * So choose the maximum of the minimums.
	 */
	
	// Quick Evaluator that gets the difference between the pieces
	public int evaluate(Piece[] board)	{
		
		// Keeping track of number of black and red pieces
		int blackPieces = 0;
		int redPieces = 0;
		
		// Counting all the Red and Black Pieces
		for(int i = 0; i < board.length; i++)	{
			
			// Counting Black Pieces
			if(board[i] == Piece.REG_BLACK || board[i] == Piece.KING_BLACK)	{
				
				blackPieces++;
			}
			// Counting Red Pieces
			else if(board[i] == Piece.REG_RED || board[i] == Piece.KING_RED)	{
				
				redPieces++;
			}
		}
		
		// Evaluator Value
		int heuristic;
		
		// If the A.I. is Black
		if(computer == Player.BLACK)	{
			
			// Evaluator Value = Black Pieces - Red Pieces
			heuristic = blackPieces - redPieces;
		}
		// Else, the A.I. is Red
		else	{
			
			// Evaluator Value = Red Pieces - Black Pieces
			heuristic = redPieces - blackPieces;
		}
		
		// Return the Evaluator Value for the current state
		return heuristic;
	}
}
