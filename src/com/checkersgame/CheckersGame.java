package com.checkersgame;

import java.util.ArrayList;
import com.checkersgame.gui.BoardUtility;

//The game of checkers
public class CheckersGame {
	
	public Player playersTurn;
	public Piece[] boardspot;
	public  boolean turnTaken=false;
	
	public boolean inJump=false;
	public int lastJumpPiece=-1;
	
	public CheckersGame() {
		
		boardspot = new Piece[] {
				Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK,
				Piece.REG_BLACK, Piece.EMPTY, Piece.REG_BLACK, Piece.REG_BLACK, 
				Piece.REG_BLACK, Piece.REG_RED, Piece.REG_BLACK, Piece.REG_BLACK, 
				Piece.REG_RED,     Piece.REG_RED,     Piece.EMPTY,     Piece.REG_RED,
				Piece.EMPTY,     Piece.REG_RED,     Piece.REG_RED,     Piece.EMPTY,
				Piece.EMPTY,   Piece.EMPTY,   Piece.EMPTY,   Piece.EMPTY,
				Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,
				Piece.EMPTY,   Piece.REG_RED,   Piece.KING_BLACK,   Piece.REG_RED,
		};
		
//		boardspot = new Piece[] {
//				Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK,
//				Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, 
//				Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, 
//				Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,
//				Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,
//				Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,
//				Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,
//				Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,
//		};
		
//king jumps
//		boardspot = new Piece[] {
//				Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
//				Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, 
//				Piece.EMPTY, 	 Piece.EMPTY,	  Piece.EMPTY, 	   Piece.EMPTY, 
//				Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,
//				Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,
//				Piece.EMPTY,   Piece.EMPTY,   Piece.EMPTY,   Piece.KING_BLACK,
//				Piece.EMPTY,   Piece.REG_RED,     Piece.REG_RED,   Piece.EMPTY,
//				Piece.EMPTY,   Piece.EMPTY,   Piece.EMPTY,   Piece.EMPTY,
//		};
		
//king double jumps
//		boardspot = new Piece[] {
//		Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
//		Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, 
//		Piece.EMPTY, 	 Piece.EMPTY,	  Piece.EMPTY, 	   Piece.EMPTY, 
//		Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,
//		Piece.EMPTY,     Piece.REG_RED,     Piece.EMPTY,     Piece.EMPTY,
//		Piece.EMPTY,   Piece.EMPTY,   Piece.EMPTY,   Piece.EMPTY,
//		Piece.EMPTY,   Piece.REG_RED,     Piece.EMPTY,   Piece.EMPTY,
//		Piece.EMPTY,   Piece.EMPTY,   Piece.KING_BLACK,   Piece.EMPTY,
//};
////more jumps available after first jump
//		boardspot = new Piece[] {
//		Piece.EMPTY, 	Piece.EMPTY, 	Piece.EMPTY, 		Piece.EMPTY,
//		Piece.EMPTY, 	Piece.EMPTY,	Piece.EMPTY, 		Piece.EMPTY, 
//		Piece.EMPTY, 	Piece.EMPTY, 	Piece.EMPTY, 		Piece.EMPTY, 
//		Piece.REG_BLACK, 	Piece.REG_RED, 	Piece.REG_RED,    	Piece.EMPTY,
//		Piece.REG_RED, 	Piece.EMPTY,	Piece.EMPTY,    	Piece.EMPTY,
//		Piece.EMPTY, 	Piece.REG_RED,  	Piece.REG_RED,   		Piece.EMPTY,
//		Piece.KING_BLACK, 	Piece.EMPTY,	Piece.EMPTY,		Piece.EMPTY,
//		Piece.EMPTY, 	Piece.EMPTY,  	Piece.EMPTY,		Piece.EMPTY,
//};
		
//more jumps available after first jump, RED
//			boardspot = new Piece[] {
//			Piece.EMPTY, 	Piece.EMPTY, 	Piece.EMPTY, 		Piece.REG_BLACK,
//			Piece.EMPTY, 	Piece.EMPTY,	Piece.EMPTY, 		Piece.EMPTY, 
//			Piece.EMPTY, 	Piece.EMPTY, 	Piece.EMPTY, 		Piece.EMPTY, 
//			Piece.KING_RED, 	Piece.REG_BLACK, 	Piece.REG_BLACK,    	Piece.EMPTY,
//			Piece.REG_BLACK, 	Piece.REG_BLACK,	Piece.EMPTY,    	Piece.EMPTY,
//			Piece.EMPTY, 	Piece.EMPTY,  	Piece.REG_BLACK,   		Piece.EMPTY,
//			Piece.KING_RED, 	Piece.EMPTY,	Piece.EMPTY,		Piece.EMPTY,
//			Piece.EMPTY, 	Piece.EMPTY,  	Piece.EMPTY,		Piece.EMPTY,
//	};
	}
	
	/**
	 * Utility to count pieces black has
	 * @return
	 */
	public int NumberOfBlackPieces() {
		int sum = 0;
		for(Piece i : boardspot) {
			if(i==Piece.REG_BLACK || i==Piece.KING_BLACK) {
				sum++;
			}
		}
		return sum;
	}
	/**
	 * Utility to count pieces red has
	 * @return
	 */
	public int NumberOfRedPieces() {
		int sum = 0;
		for(Piece i : boardspot) {
			if(i==Piece.REG_RED || i==Piece.KING_RED) {
				sum++;
			}
		}
		return sum;
	}

	/**
	 * 
	 * @param m the move
	 * @param p the player taking the move
	 * @return if the move is Valid
	 */
	public boolean isValidMove(Move m,Player p) {
		if(m.start > 31 || m.start < 0|| m.end > 31|| m.end < 0) { //not a valid spot
			return false;
		} else if(!isPlayersPiece(m,p)) {//can't move other players pieces, or non-existent pieces
			return false;
		} else if(boardspot[m.end] != Piece.EMPTY) {//cannot put pieces on other pieces
			return false;
		} else if( ValidSingleMove(m,p)) {//single move is ok?
			return true;
		} else if( ValidJump(m,p) ) {//jump move ok?
			return true;
		} else { //the move is valid
			return false;
		}
	}
	
	/**
	 * @param m the move
	 * @param p the player
	 * @return true if the piece belongs to the player, false if it does not or if the start is EMPTY
	 */
	public boolean isPlayersPiece(Move m, Player p) {
		if(p == Player.BLACK) {
			return (boardspot[m.start] == Piece.KING_BLACK)||
					( boardspot[m.start] == Piece.REG_BLACK );
		} else {//RED
			return (boardspot[m.start] == Piece.KING_RED) ||
					( boardspot[m.start] == Piece.REG_RED );
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
	 *  The isPlayerPiece should already be checked at this point so the 
	 *  checker piece is already player p's piece
	 * @param m
	 * @param p
	 * @return
	 */
	public boolean ValidSingleMove(Move m, Player p) {
		//is the starting piece a king
		boolean isKing = ( boardspot[m.start]==Piece.KING_BLACK )|| 
						 (boardspot[m.start]==Piece.KING_RED );
		if(isKing) {//king piece
			return BoardUtility.isValidMove(m);
		} else if(Player.BLACK == p){//non king black piece
			return BoardUtility.isValidForwardMove(m,p);
		} else {//non-king red piece
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
	
	public boolean pieceExistsInAvailableMovesStart(int start,Player p,Move[] m) {
		boolean flag=false;
		//Move[] m = AvailableMoves(p);
		for(int i = 0 ; i < m.length ; i++) {
			if(start == m[i].start ) {
				flag=true;
			}
		}
		return flag;
	}
	
	public boolean pieceExistsInAvailableMovesEnd(int start, int end,Player p,Move[] m) {
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
