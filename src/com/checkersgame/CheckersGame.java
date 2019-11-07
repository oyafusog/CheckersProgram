// This puts "CheckersGame.java" into the package "com.checkersgame"
package com.checkersgame;

// Imports for Program, Array Lists
import java.util.ArrayList;

// Importing the package containing the GUI "BoardUtility.java"
// to be able to change the GUI to match it with this Game of Checkers
import com.checkersgame.gui.BoardUtility;

// The Class creates the Game of Checkers
public class CheckersGame	{
		
	// This object is the "Player", it's a Enum Class object
	// to represent the players control over their pieces.
	public Player playersTurn;
		
	// This is a list of "Piece"'s to build the checker board.
	// Each index in the array represents a space on the board,
	// whether if it's a piece or an empty space. 
	public Piece[] boardspot;
	
	// This will keep track whether someone has taken their turn,
	// it's set to false to let you take your turn.
	public static boolean turnTaken = false;
	
	// I THINK THIS IS TO KEEP TRACK OF JUMPING
	// REMEMBER TO COME BACK TO EXPLAIN THIS ONE BETTER
	public boolean inJump=false;
	public int lastJumpPiece=-1;
	
	// This is the Game of Checkers, setting the board up.
	public CheckersGame() {
		
		// This array of Piece's keep track of the board, and the pieces on it.
		boardspot = new Piece[] {
				
			// It's a row of four because we dismiss the spaces we can't touch, the grey squares.
			Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK,
			Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, 
			Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, 
			Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,
			Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,
			Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,
			Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,
			Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,
		};
		
		// The chunk of code below was for Testing I believe. Not 100% sure.
		/*		
		
		// The Board is setup for King Jumps
		boardspot = new Piece[]	{
		
			Piece.EMPTY, Piece.EMPTY, 	Piece.EMPTY, 	Piece.EMPTY,
			Piece.EMPTY, Piece.EMPTY, 	Piece.EMPTY, 	Piece.EMPTY, 
			Piece.EMPTY, Piece.EMPTY, 	Piece.EMPTY, 	Piece.EMPTY, 
			Piece.EMPTY, Piece.EMPTY, 	Piece.EMPTY, 	Piece.EMPTY,
			Piece.EMPTY, Piece.EMPTY, 	Piece.EMPTY, 	Piece.EMPTY,
			Piece.EMPTY, Piece.EMPTY, 	Piece.EMPTY, 	Piece.KING_BLACK,
			Piece.EMPTY, Piece.REG_RED, Piece.REG_RED,  Piece.EMPTY,
			Piece.EMPTY, Piece.EMPTY,   Piece.EMPTY,    Piece.EMPTY,
		};
		
		// The Board is setup for King Double Jumps
		boardspot = new Piece[] {
		
			Piece.EMPTY, Piece.EMPTY, 	Piece.EMPTY, 		Piece.EMPTY,
			Piece.EMPTY, Piece.EMPTY, 	Piece.EMPTY, 		Piece.EMPTY, 
			Piece.EMPTY, Piece.EMPTY,	Piece.EMPTY, 		Piece.EMPTY, 
			Piece.EMPTY, Piece.EMPTY,   Piece.EMPTY, 		Piece.EMPTY,
			Piece.EMPTY, Piece.REG_RED, Piece.EMPTY, 		Piece.EMPTY,
			Piece.EMPTY, Piece.EMPTY,   Piece.EMPTY, 		Piece.EMPTY,
			Piece.EMPTY, Piece.REG_RED, Piece.EMPTY, 		Piece.EMPTY,
			Piece.EMPTY, Piece.EMPTY,   Piece.KING_BLACK,   Piece.EMPTY,
		};
		
		// The Board is setup for more Jumps after a Jump, BLACK
		boardspot = new Piece[] {
		
			Piece.EMPTY, 		Piece.EMPTY, 	Piece.EMPTY, 		Piece.EMPTY,
			Piece.EMPTY, 		Piece.EMPTY,	Piece.EMPTY, 		Piece.EMPTY, 
			Piece.EMPTY, 		Piece.EMPTY, 	Piece.EMPTY, 		Piece.EMPTY, 
			Piece.REG_BLACK, 	Piece.REG_RED, 	Piece.REG_RED,    	Piece.EMPTY,
			Piece.REG_RED, 		Piece.EMPTY,	Piece.EMPTY,    	Piece.EMPTY,
			Piece.EMPTY, 		Piece.REG_RED, 	Piece.REG_RED,   	Piece.EMPTY,
			Piece.KING_BLACK, 	Piece.EMPTY,	Piece.EMPTY,		Piece.EMPTY,
			Piece.EMPTY, 		Piece.EMPTY,  	Piece.EMPTY,		Piece.EMPTY,
		};
		
		// The Board is setup for more Jumps after a Jump, RED
		boardspot = new Piece[] {
		
			Piece.EMPTY, 		Piece.EMPTY, 		Piece.EMPTY, 		Piece.REG_BLACK,
			Piece.EMPTY, 		Piece.EMPTY,		Piece.EMPTY, 		Piece.EMPTY, 
			Piece.EMPTY, 		Piece.EMPTY, 		Piece.EMPTY, 		Piece.EMPTY, 
			Piece.KING_RED, 	Piece.REG_BLACK, 	Piece.REG_BLACK,    Piece.EMPTY,
			Piece.REG_BLACK, 	Piece.REG_BLACK,	Piece.EMPTY,    	Piece.EMPTY,
			Piece.EMPTY, 		Piece.EMPTY, 	 	Piece.REG_BLACK,   	Piece.EMPTY,
			Piece.KING_RED, 	Piece.EMPTY,		Piece.EMPTY,		Piece.EMPTY,
			Piece.EMPTY, 		Piece.EMPTY,  		Piece.EMPTY,		Piece.EMPTY,
		};
		
		*/
	}
	
	/*
	 *  Utility to count the number of black pieces on the board
	 *  @return
	 */
	public int NumberOfBlackPieces()	{
		
		int sum = 0;
		
		// Enhanced for loop checking every Piece "i" on the board
		for(Piece i : boardspot)	{
			
			// If a regular piece or a king piece is "BLACK"
			if (i == Piece.REG_BLACK || i == Piece.KING_BLACK)	{
				
				// Count the Piece
				sum++;
			}
		}
		
		// Return the number of Black Pieces on the board
		return sum;
	}
	
	/**
	 * Utility to count the number of red pieces on the board
	 * @return
	 */
	public int NumberOfRedPieces()	{
		
		int sum = 0;
		
		// Enhanced for loop checking every Piece "i" on the board
		for(Piece i : boardspot)	{
			
			// If a regular piece or a king piece is "RED"
			if(i == Piece.REG_RED || i == Piece.KING_RED)	{
				
				// Count the Piece
				sum++;
			}
		}
		
		// Return the number of Red Pieces on the board
		return sum;
	}
	
	/**
	 *	Checks to see if the "Move" selected by the "Player" is valid
	 * 	- Move m, represents the Move
	 *  - Player p, represents the Player making the move
	 *  - Return true or false, whether the move is valid or not
	 *  
	 * @param m the move
	 * @param p the player taking the move
	 * @return if the move is Valid
	 */
	public boolean isValidMove(Move m, Player p)	{
		
		// If you select a piece that isn't on the board, or select a destination that isn't on the board
		if(m.start > 31 || m.start < 0 || m.end > 31 || m.end < 0)	{
			
			// The Move is Invalid
			return false;
		}
		// If you're NOT trying to move one of your Pieces		
		else if(!isPlayersPiece(m, p))	{
			
			// The Move is Invalid
			return false;
		} 
		// If you're trying to move your piece onto another piece
		else if(boardspot[m.end] != Piece.EMPTY) {
			
			// The Move is Invalid
			return false;
		} 
		// 
		else if( ValidSingleMove(m,p)) {//single move is ok?
			
			return true;
		} 
		else if( ValidJump(m,p) ) {//jump move ok?
			
			return true;
		} 
		else { //the move is valid
			
			return false;
		}
	}
	
	/**
	 *	Checks to see if you've selected your Piece
	 *	- Move m, represents the Move
	 *	- Player p, represents the Player making the move
	 *	- Returns True or False, depending on if you've selected your piece
	 * 
	 * @param m the move
	 * @param p the player
	 * @return true if the piece belongs to the player, false if it does not or if the start is EMPTY
	 */
	public boolean isPlayersPiece(Move m, Player p)	{
		
		// If the Player is "BLACK"
		if(p == Player.BLACK)	{
			
			// Is the Start of the Move on a Black King or Is the Start of the Move a Regular Black Piece?
			return (boardspot[m.start] == Piece.KING_BLACK) || ( boardspot[m.start] == Piece.REG_BLACK );
		} 
		// Else, the Player is "RED"
		else	{
			
			// Is the Start of the Move on a Red King or Is the Start of the Move a Regular Red Piece?
			return (boardspot[m.start] == Piece.KING_RED) || ( boardspot[m.start] == Piece.REG_RED );
		}
	}
	
	/**
	 * It is assumed that if there is a multi jump that
	 * it is defined in an array in move at this point (STILL NEEDS TO BE DONE)
	 * @param m
	 * @param p
	 * @return
	 */
	public boolean ValidJump(Move m, Player p) {
		if(m.multiJump==null) {//check single jumps
			if( boardspot[m.start] == Piece.KING_BLACK || boardspot[m.start] == Piece.KING_RED) {
				//check what piece is removed
				return BoardUtility.isValidJump(m);
			} else {
				if(Player.BLACK == p) {//black player
					//non king black pieces can only go in increasing order
					if(m.start<m.end) {
						return false;
					} else {
						return BoardUtility.isValidJump(m);
					}
				} else {//red player
					//non king red pieces can only go in decreasing order
					if(m.start>m.end) {
						return false;
					} else {
						return BoardUtility.isValidJump(m);
					}
				}
			}
		} else {//it is a multijump, search for move
			//the jump is either a start and end or, if multi jump will show all spots hit
			if(m.multiJump==null) {
				
			} else {
				
				//just go through the array
				int[] tmp = m.multiJump;
				
				int currentSpace = m.start;
				int nextSpace;
				for(int i = 1 ; i < tmp.length ; i++ ) {//start on the next index
					nextSpace = tmp[i];//the next space
					if(!(BoardUtility.isValidJump(currentSpace,nextSpace) && boardspot[nextSpace] == Piece.EMPTY )) {
						return false;//if it is a validJump and the next spot is ok continue
					}
					int jumpedPiece = getJumpedPiece(currentSpace,nextSpace);
					//relying on short-circuiting here, otherwise put a less than 0 check first
					if( jumpedPiece >=0 && 
						boardspot[jumpedPiece] != Piece.EMPTY) {//check to see we are actually jumping over an opponent piece
						if( (Player.BLACK == p ) && 
							(boardspot[jumpedPiece] != Piece.KING_RED || boardspot[jumpedPiece] != Piece.REG_RED )) {
							//the jumped piece is not red
							return false;
						}
						if( (Player.RED == p )  &&
							(boardspot[jumpedPiece] != Piece.KING_RED || boardspot[jumpedPiece] != Piece.REG_RED )) {
							//the jumped piece is not black
							return false;
						}
					} 
					//the single jump is valid and will remove an opponents piece,
					//move to next jump
					currentSpace = nextSpace;
				}
				//if here the set of jumps is valid
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 	This checks to see if the move is valid
	 * 	- This isn't for multi-jumps, only for single jumps
	 * 
	 * @param m
	 * @param p
	 * @return
	 */
	public boolean ValidSingleMove(Move m, Player p)	{
		
		// Checks to see if this Piece is a King or not
		boolean isKing = (boardspot[m.start] == Piece.KING_BLACK) || (boardspot[m.start] == Piece.KING_RED);
		
		// If it's a king
		if(isKing)	{
			
			return BoardUtility.isValidMove(m);
		} 
		else if(Player.BLACK == p)	{	//non king black piece
			
			return BoardUtility.isValidForwardMove(m,p);
		} 
		else	{	//non-king red piece
			
			return BoardUtility.isValidForwardMove(m,p);
		}
 	}
	
	//remember black starts at the lower numbers
	//red starts at the higher numbers
	//gives all available moves reguardless of last move
	//*****NED SOMETHING THAT USES PIECE'S INDEX, to prevent a piece from being removed
	//and when checking for other moves, other jumps are put in that are not the associated piece that just jumped
	//*****************************************************************
	/**
	 * Will Return available moves based on the players pieces,
	 * does not return moves in the form 1-2-3-4, only single jumps
	 * use a loop when looking for multi jumps
	 * @param p the player
	 * @return
	 */
	public Move[] AvailableMoves(Player p) {
		ArrayList<Move> singleMoves = new ArrayList<Move>();
		ArrayList<Move> jumpMoves = new ArrayList<Move>();
		int[] pieces = PlayersPieces(p);
		if(Player.BLACK == p ) {
			for(int index = 0 ; index < pieces.length ; index++ ) {
				int[] possibleMoves = BoardUtility.singleMoves[pieces[index]];
				int[] possibleJumps = BoardUtility.singleJumps[pieces[index]];
				if(boardspot[pieces[index]] == Piece.KING_BLACK) {//the piece is a king
					//check moves
					for(int i=0 ; i < possibleMoves.length ; i++ ) {
						if(boardspot[possibleMoves[i]] == Piece.EMPTY) {
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					//check jumps
					for(int i = 0 ; i < possibleJumps.length ; i++ ) {
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
						if( boardspot[possibleJumps[i]] == Piece.EMPTY && //
							jumpedPiece >=0 && //there is a piece to jump?
							//opponents piece?
							(boardspot[jumpedPiece] == Piece.KING_RED|| boardspot[jumpedPiece]  == Piece.REG_RED)) {
							jumpMoves.add(new Move(pieces[index], possibleJumps[i],true));
						}
					}
				} else {//the piece is a regular piece
					//check moves
					for(int i=0 ; i < possibleMoves.length ; i++ ) {
						if(possibleMoves[i] > pieces[index] && boardspot[possibleMoves[i]] == Piece.EMPTY) {
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					//checkjumps
					for(int i=0 ; i < possibleJumps.length ; i++ ) {
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
						if( boardspot[possibleJumps[i]] == Piece.EMPTY && //is the landing spot empty
							jumpedPiece >=0  && //is there a piece to jump
							//opponent piece?
							(boardspot[jumpedPiece] == Piece.KING_RED|| boardspot[jumpedPiece]  == Piece.REG_RED) &&
							pieces[index] < possibleJumps[i]) {//is the piece going in the correct direction
							jumpMoves.add(new Move(pieces[index], possibleJumps[i],true));//then add it
						}
					}
				}
			}
		} else {//RED PLAYER
			for(int index = 0 ; index < pieces.length ; index++ ) {
				int[] possibleMoves = BoardUtility.singleMoves[pieces[index]];
				int[] possibleJumps = BoardUtility.singleJumps[pieces[index]];
				if(boardspot[pieces[index]] == Piece.KING_RED) {//the piece is a king
					//check moves
					for(int i=0 ; i < possibleMoves.length ; i++ ) {
						if(boardspot[possibleMoves[i]] == Piece.EMPTY) {
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					//check jumps
					for(int i = 0 ; i < possibleJumps.length ; i++ ) {
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
						if( boardspot[possibleJumps[i]] == Piece.EMPTY && //
							jumpedPiece >=0 && //there is a piece to jump?
							//opponents piece?
							(boardspot[jumpedPiece] == Piece.KING_BLACK|| boardspot[jumpedPiece]  == Piece.REG_BLACK)) {
							jumpMoves.add(new Move(pieces[index], possibleJumps[i],true));
						}
					}
				} else {//the piece is a regular piece
					//check moves
					for(int i=0 ; i < possibleMoves.length ; i++ ) {
						if(possibleMoves[i] < pieces[index] && boardspot[possibleMoves[i]] == Piece.EMPTY) {
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					//checkjumps
					for(int i=0 ; i < possibleJumps.length ; i++ ) {
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
						if( boardspot[possibleJumps[i]] == Piece.EMPTY && //is the landing spot empty
							jumpedPiece >=0  && //is there a piece to jump
							//opponent piece?
							(boardspot[jumpedPiece] == Piece.KING_BLACK || boardspot[jumpedPiece]  == Piece.REG_BLACK) &&
							pieces[index] > possibleJumps[i]) {//is the piece going in the correct direction
							jumpMoves.add(new Move(pieces[index], possibleJumps[i],true));//then add it
						}
					}
				}
			}
		}

		//get all moves available to player
		//if there are jumps available filter down the moves to those
		Move[] tmp ;// = new Move[moves.size()];

		//filter out moves that are not apart of the last jump
		if(inJump) {
			for(int i = 0; i < jumpMoves.size() ; i++ ) {
				if(jumpMoves.get(i).start!=lastJumpPiece) {
					jumpMoves.remove(i);
				}
			}
		}
		
		if(jumpMoves.isEmpty()) {
			tmp = new Move[singleMoves.size()];
			singleMoves.toArray(tmp);
		} else {
			tmp = new Move[jumpMoves.size()];
			jumpMoves.toArray(tmp);
		}
		return tmp;
	}
	
	public int[] PlayersPieces(Player p) {
		ArrayList<Integer> piecesIndex = new ArrayList<Integer>();
		for(int i = 0 ; i < boardspot.length ; i++ ) {
			if( (boardspot[i]==Piece.KING_BLACK || boardspot[i]==Piece.REG_BLACK) &&
					p==Player.BLACK
					) {
				piecesIndex.add(i);
			} else if((boardspot[i]==Piece.KING_RED || boardspot[i]==Piece.REG_RED ) &&
						p==Player.RED) {
				piecesIndex.add(i);
			}
		}
		int[] tmp = new int[piecesIndex.size()];
		for(int i = 0 ; i < tmp.length ; i++ ) {
			tmp[i] = piecesIndex.get(i);
		}
		return tmp;
	}
	/**
	 * returns the index of the piece that will get jumped/taken,
	 * invalid jumps will return a number less than 0
	 * @param start
	 * @param end
	 * @return
	 */
	public int getJumpedPiece(int start , int end) {
		int piece = -2;
		int rowNum=start/4;//rows : 0, 1, 2, 3, 4, 5, 6, 7
		for(int i = 0 ; i < BoardUtility.singleMoves[start].length ; i++ ) {
			if(rowNum%2==0) {//even row to even row
				int a = BoardUtility.singleMoves[start][i];
				if( (a + 4 == end && start+9 == end) || 
					(a + 3 == end && start+7 == end) ||
					(a - 5 == end && start-9 == end) ||
					(a - 4 == end && start-7 == end) ){
					piece = a;
				}
			} else {//odd row to odd row
				int a = BoardUtility.singleMoves[start][i];
				if( (a + 5 == end && start + 9 == end) ||
					(a + 4 == end && start + 7 == end) ||
					(a - 4 == end && start - 9 == end) ||
					(a - 3 == end && start - 7 == end) ) {
					piece = a;
				}
			}
		}
		//add/subtract 3,4,5
		//then check index, and see if it is opponent pice
		return piece;
	}
	
	/**
	 * Only call after checks have been made
	 * @param start the position of the piece
	 * @param end the end of the piece
	 */
	public void Move(int start, int end) {
		if(start == end) {
			return;//can't pass
		}
		Piece temp = boardspot[start];//save
		//check if any pieces are removed
		boardspot[start]=Piece.EMPTY;//reset, will always be empty unless the piece manages to return here
		boardspot[end]=temp;//set
	}
	
	public void KingPiece(int piece, Player p) {
		if(Player.BLACK == p) {//black
			if(boardspot[piece] == Piece.REG_BLACK && 
					(piece / 4 == 7) ) {
				boardspot[piece] = Piece.KING_BLACK;
			}
		} else {//RED
			if(boardspot[piece] == Piece.REG_RED && 
					(piece / 4 == 0) ) {
				boardspot[piece] = Piece.KING_RED;
			}
		}
	}
	
	public static boolean pieceExistsInAvailableMovesStart(int start,Player p,Move[] m) {
		boolean flag=false;
		//Move[] m = AvailableMoves(p);
		for(int i = 0 ; i < m.length ; i++) {
			if(start == m[i].start ) {
				flag=true;
			}
		}
		return flag;
	}
	
	public static boolean pieceExistsInAvailableMovesEnd(int start, int end,Player p,Move[] m) {
		boolean flag=false;
		//Move[] m = AvailableMoves(p);
		for(int i = 0 ; i < m.length ; i++) {
			if( start == m[i].start && end == m[i].end) {
				flag=true;
			}
		}
		return flag;
	}
	
	
	public void Remove(int removeme) {
		String piece="";
		switch(boardspot[removeme]) {
			case KING_RED :
				piece = "RED KING";
				break;
			case KING_BLACK :
				piece = "BLACK KING";
				break;
			case REG_RED :
				piece = "RED PIECE";
				break;
			case REG_BLACK :
				piece = "BLACK PIECE";
				break;
			case EMPTY : 
				break;//do nothing
		}
		System.out.println("Removed "+piece+" on "+(removeme+1));
		if(removeme<0) {
			return;//not a space
		}
		boardspot[removeme] = Piece.EMPTY;
	}
	
	public void GameOver(Player winner,String reason) {
		System.out.println(reason);
		System.out.println(winner+" wins");
	}
}
