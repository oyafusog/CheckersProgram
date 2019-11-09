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
	public boolean inJump = false;
	public int lastJumpPiece = -1;
	
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
	 * 	Utility to count the number of red pieces on the board
	 * 	@return
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
		// If the Move is Single, and not a jump
		else if(ValidSingleMove(m,p))	{
			
			// The Move is Valid
			return true;
		} 
		// If the Move is a valid Jump
		else if(ValidJump(m,p))	{
			
			// The Move is Valid
			return true;
		} 
		// The Move is Invalid
		else	{ 
			
			// Return Invalid
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
	 * 	Checks to see if the jump being attempted is valid.
	 * 
	 * It is assumed that if there is a multi jump that
	 * it is defined in an array in move at this point (STILL NEEDS TO BE DONE)
	 * @param m
	 * @param p
	 * @return
	 */
	public boolean ValidJump(Move m, Player p)	{
		
		// Checks for Single Jump Only
		if(m.multiJump == null)	{
			
			// If the Piece you're using is a King
			if(boardspot[m.start] == Piece.KING_BLACK || boardspot[m.start] == Piece.KING_RED)	{
				
				
				// Checks to see if the Move is valid
				return BoardUtility.isValidJump(m);
			}
			// Checks for Regular Pieces
			else	{
				
				// If the Player is black
				if(Player.BLACK == p)	{
					
					// Non-king Black Players can only go Increasing
					if(m.start < m.end)	{
						
						return false;
					} 
					else	{
						
						// Checks to see if the Move is valid
						return BoardUtility.isValidJump(m);
					}
				} 
				// Else, the Player is red
				else	{
					
					// Non-king Red Player can only go in decreasing 
					if(m.start > m.end)	{
						
						return false;
					} 
					else	{
						
						// Checks to see if the Move is valid
						return BoardUtility.isValidJump(m);
					}
				}
			}
		} 
		// Else it's a Multi-Jump 
		else	{
	
			// The Jump is either a start and end, or if the Multi-Jump shows all spots hit
			if (m.multiJump == null)	{
				
			} 
			else	{
				
				// tmp is the array of possible Multi-Jumps
				int[] tmp = m.multiJump;
				
				// Start is your current space
				int currentSpace = m.start;
				int nextSpace;
				
				// Cyclying through the Multi-Jump Index 
				for(int i = 1; i < tmp.length; i++)	{
					
					// The Next Space
					nextSpace = tmp[i];
					
					// If it's a valid jump and the next space is empty
					if(!(BoardUtility.isValidJump(currentSpace,nextSpace) && boardspot[nextSpace] == Piece.EMPTY ))	{
						
						return false;//if it is a validJump and the next spot is ok continue
					}
					
					// Jumped Pieces
					int jumpedPiece = getJumpedPiece(currentSpace,nextSpace);
					
					//relying on short-circuiting here, otherwise put a less than 0 check first
					if(jumpedPiece >= 0 && boardspot[jumpedPiece] != Piece.EMPTY)	{//check to see we are actually jumping over an opponent piece
						
						if((Player.BLACK == p ) && (boardspot[jumpedPiece] != Piece.KING_RED || boardspot[jumpedPiece] != Piece.REG_RED))	{
							
							//the jumped piece is not red
							return false;
						}
						if((Player.RED == p) && (boardspot[jumpedPiece] != Piece.KING_RED || boardspot[jumpedPiece] != Piece.REG_RED))	{
							
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
	 * 	SIDE NOTE: I don't think we need to check the color here, doesn't "isValidForwardMove" already do that?
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
			
			// Return with BoardUtility.java, using it's method "isValidMove" to see if the move is valid
			return BoardUtility.isValidMove(m);
		} 
		// If the Player is "BLACK" (and regular)
		else if(Player.BLACK == p)	{
			
			// Return with BoardUtility.java, using it's method "isValidForwardMove" to see if the move is valid
			return BoardUtility.isValidForwardMove(m,p);
		} 
		// If the Player is "RED" (and regular)
		else	{
			
			// Return with BoardUtility.java, using it's method "isValidForwardMove" to see if the move is valid
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
	 * 	This Method checks for the Available Moves based on which pieces are on the board.
	 * 	This Returns only Single Jumps, not Multi-Jumps in the form 1-2-3-4.
	 * 	Using a Loop when looking for Multi-Jumps
	 * 
	 * @param p the player
	 * @return
	 */
	public Move[] AvailableMoves(Player p)	{
		
		// Two ArrayLists of Moves, one for Single-Jumps, and another for Multi-Jumps
		ArrayList<Move> singleMoves = new ArrayList<Move>();
		ArrayList<Move> jumpMoves = new ArrayList<Move>();
		
		// Pieces is the amount of Pieces we have
		int[] pieces = PlayersPieces(p);
		
		// If the Player is "BLACK"
		if(Player.BLACK == p)	{
			
			// Loop through all the Pieces
			for(int index = 0; index < pieces.length; index++)	{
				
				// Put each piece into into the possible Moves and Jumps
				int[] possibleMoves = BoardUtility.singleMoves[pieces[index]];
				int[] possibleJumps = BoardUtility.singleJumps[pieces[index]];
				
				// If the Piece is a King
				if(boardspot[pieces[index]] == Piece.KING_BLACK)	{
					
					// Loop through the possible moves
					for(int i = 0; i < possibleMoves.length; i++)	{
						
						// If one of the possible moves is an empty piece
						if(boardspot[possibleMoves[i]] == Piece.EMPTY)	{
							
							// Add a Move to "singleMoves" array, from the piece to the possible moves for that piece
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					
					// Loop through the possible Jumps
					for(int i = 0; i < possibleJumps.length; i++)	{
						
						// The Jumped Piece is the Piece you're jumping
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
		
						/*
						 *  If the Possible Jump is Empty "AND" the Jumped Piece is greater than or equal to 0 "AND" 
						 *  the Piece being jumped is an opponent piece (RED). 
						 */
						if(boardspot[possibleJumps[i]] == Piece.EMPTY && jumpedPiece >= 0 && (boardspot[jumpedPiece] == Piece.KING_RED || boardspot[jumpedPiece]  == Piece.REG_RED)) {
							
							// Add a Move to "jumpMoves" array, from the piece to the possible jumps for that piece
							jumpMoves.add(new Move(pieces[index], possibleJumps[i],true));
						}
					}
				}
				// If the Piece is a Regular
				else	{
					
					// Loop through the possible moves
					for(int i = 0; i < possibleMoves.length; i++)	{
						
						// If one of the possible moves is in the correct direction "AND" the spot is empty
						if(possibleMoves[i] > pieces[index] && boardspot[possibleMoves[i]] == Piece.EMPTY)	{
							
							// Add the Move to "singleMoves" array, from the piece to the possible moves for that piece
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					
					// Loop through the possible Jumps
					for(int i = 0; i < possibleJumps.length; i++)	{
						
						// The Jumped Piece is the Piece you're jumping
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
						
						/*
						 * 	If the Possible Jump is Empty "AND" the Jumped Piece is greater than or equal to 0 "AND"
						 * 	the Piece being jumped is an opponent piece (RED) "AND" the piece is going in the 
						 *	right direction.
						 */
						if(boardspot[possibleJumps[i]] == Piece.EMPTY && jumpedPiece >= 0 && (boardspot[jumpedPiece] == Piece.KING_RED || boardspot[jumpedPiece] == Piece.REG_RED) && pieces[index] < possibleJumps[i])	{
							
							// Add a Move to "jumpMoves" array, from the piece to the possible jumps for that piece
							jumpMoves.add(new Move(pieces[index], possibleJumps[i],true));
						}
					}
				}
			}
		} 
		// Else, the Player is "RED"
		else	{
			
			/*
			 *	The same exact code from above correlates to what's happening
			 *	with the Red piece. Just compare this with the chunck above
			 *	pertaining to the Black piece. 
			 */
			for(int index = 0; index < pieces.length; index++)	{
				
				int[] possibleMoves = BoardUtility.singleMoves[pieces[index]];
				int[] possibleJumps = BoardUtility.singleJumps[pieces[index]];
				
				// If the Piece is a King
				if(boardspot[pieces[index]] == Piece.KING_RED)	{
					
					// Loop through the Possible Moves
					for(int i = 0; i < possibleMoves.length; i++)	{
						
						if(boardspot[possibleMoves[i]] == Piece.EMPTY)	{
							
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					
					// Loop through the possible Jumps
					for(int i = 0; i < possibleJumps.length; i++)	{
						
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
						
						/*
						 *  If the Possible Jump is Empty "AND" the Jumped Piece is greater than or equal to 0 "AND" 
						 *  the Piece being jumped is an opponent piece (BLACK). 
						 */
						if(boardspot[possibleJumps[i]] == Piece.EMPTY && jumpedPiece >= 0 && (boardspot[jumpedPiece] == Piece.KING_BLACK || boardspot[jumpedPiece] == Piece.REG_BLACK))	{
							
							jumpMoves.add(new Move(pieces[index], possibleJumps[i],true));
						}
					}
				} 
				// Else, the Piece is Regular
				else	{
					
					// Loop through possible moves
					for(int i = 0; i < possibleMoves.length; i++)	{
						
						if(possibleMoves[i] < pieces[index] && boardspot[possibleMoves[i]] == Piece.EMPTY)	{
							
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					
					// Loop through possible Jumps
					for(int i = 0; i < possibleJumps.length; i++)	{
						
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
						
						/*
						 * 	If the Possible Jump is Empty "AND" the Jumped Piece is greater than or equal to 0 "AND"
						 * 	the Piece being jumped is an opponent piece (BLACK) "AND" the piece is going in the 
						 *	right direction.
						 */
						if(boardspot[possibleJumps[i]] == Piece.EMPTY && jumpedPiece >= 0 && (boardspot[jumpedPiece] == Piece.KING_BLACK || boardspot[jumpedPiece]  == Piece.REG_BLACK) && pieces[index] > possibleJumps[i])	{
							
							// Add a Move to "jumpMoves" array, from the piece to the possible jumps for that piece
							jumpMoves.add(new Move(pieces[index], possibleJumps[i],true));//then add it
						}
					}
				}
			}
		}
		
		//get all moves available to player
		//if there are jumps available filter down the moves to those
		
		// Create a array of Moves named "tmp"
		Move[] tmp;

		// Filtering out Moves that are not apart of the last jump
		if(inJump)	{
			
			// Looping through the availble Jump Moves
			for(int i = 0; i < jumpMoves.size(); i++)	{
				
				// If the Jump Move Start isn't equal to lastJumpPiece
				if(jumpMoves.get(i).start != lastJumpPiece)	{
					
					// Then remove that Jump
					jumpMoves.remove(i);
				}
			}
		}
		
		// If Jump Moves are Empty
		if(jumpMoves.isEmpty())	{
			
			tmp = new Move[singleMoves.size()];
			singleMoves.toArray(tmp);
		} 
		else {
			
			tmp = new Move[jumpMoves.size()];
			jumpMoves.toArray(tmp);
		}
		
		// Returning an Array Moves availbe to the Player
		return tmp;
	}
	
	// Checks for all of the Players Pieces
	public int[] PlayersPieces(Player p)	{
		
		// Array List to keep track of Pieces
		ArrayList<Integer> piecesIndex = new ArrayList<Integer>();
		
		// Loop through every spot on the board
		for(int i = 0; i < boardspot.length; i++)	{
			
			// If the spot had a Black King or Regular piece "AND" the Player is BLACK
			if((boardspot[i] == Piece.KING_BLACK || boardspot[i] == Piece.REG_BLACK) && p == Player.BLACK)	{
				
				// Then add that Piece to the Index of Pieces
				piecesIndex.add(i);
			} 
			// If the spot has a Red King or Regular piece "AND" the Player is RED
			else if((boardspot[i] == Piece.KING_RED || boardspot[i] == Piece.REG_RED ) && p == Player.RED) {
				
				// Then add that Piece to the Index of Pieces
				piecesIndex.add(i);
			}
		}
		
		/*
		 * 	Is this part here redundant? Can't we just return the piecesIndex?
		 * 	Or is it because we're using an ArrayList instead of an Array?
		 */
		
		// New tmp Array of the same size as the Index of Pieces
		int[] tmp = new int[piecesIndex.size()];
		
		for(int i = 0 ; i < tmp.length ; i++ )	{
			
			// The Temp is being filled with the Index of Pieces
			tmp[i] = piecesIndex.get(i);
		}
		
		// Return the Tmp
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
