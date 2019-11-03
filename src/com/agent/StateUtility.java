package com.agent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.checkersgame.CheckersGame;
import com.checkersgame.Move;
import com.checkersgame.Piece;
import com.checkersgame.Player;
import com.checkersgame.gui.BoardUtility;

/**
 *	Need a way to compound the moves as the main loop would to predict multiple jumps 
 *
 */
public class StateUtility {
	
	public static Player playersTurn;
	public static boolean cb_holdpiece = false;
	
	public static boolean cg_turnTaken = false;
	
	public static boolean inJump=false;
	public static int lastJumpPiece=-1;
	
	/**
	 * Returns where the players pieces are on the board in the state 
	 * @param p	which player
	 * @param state the state we are asking about
	 * @return
	 */
	public static int[] PlayersPieces(Player p, Piece[] state) {
		ArrayList<Integer> piecesIndex = new ArrayList<Integer>();
		for(int i = 0 ; i < state.length ; i++ ) {
			if( (state[i]==Piece.KING_BLACK || state[i]==Piece.REG_BLACK) &&
					p==Player.BLACK
					) {
				piecesIndex.add(i);
			} else if((state[i]==Piece.KING_RED || state[i]==Piece.REG_RED ) &&
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
	 * This is for a single jump, returns the spot where a piece should exist
	 * does not check the jumped piece's existance
	 * @param start where does the jump start
	 * @param end where does it end
	 * @return
	 */
	public static int getJumpedPiece(int start , int end) {
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
	
	public static Move[] GetCompleteMoves(Player p, Piece[] state) {
		
		//save the state so we can use it for each move
		Piece[] tmp =  state;//we dont actually want to change the state until the move is taken
		
		//the intial single moves or jumps
/**->**/Move[] startMoves = AvailableSingleMoves(p,state);
		
/**->**/boolean startIsJump = false;
		for(Move m : startMoves) {
			if(m.isJump) {
				startIsJump = true;
			}
		}
		//just return the single step moves
		if(!startIsJump) {//there are not jumps in the set
			return startMoves;//just return the move[]
		}
		//set of states each move will leave the board in
/**->**/ArrayList<Piece[]> endingStates =  new ArrayList<Piece[]>(); 
		//otherwise we have to complete the jumps
		//should really return the state that the jumps will leave the board in
		Move[] tmpMoves = startMoves.clone();//copy moves into tmp
		LinkedList<Move> uncompleted = new LinkedList<Move>();//basically a queue
		for(int i=0; i < tmpMoves.length; i ++) {
			uncompleted.add(tmpMoves[i]);
		}
//Complete the moves
		HashMap<Move,Piece[]> move = new HashMap<Move,Piece[]>();
		//for(Move m : tmpMoves) {
		while(!uncompleted.isEmpty()) {	
			Move m = uncompleted.removeFirst();
			//ArrayList<Integer> move_nums = new ArrayList<Integer>();
			Piece[] newState = state.clone();//copy the state into a new variable, so we dont disturb the board
			//go until done
			cg_turnTaken=false;//flag to ensure the move continues until that piece can no long move(jump)
			while(!cg_turnTaken) {
				System.out.println("Checking "+m);
				//move into next state
				newState = SimulateMove(m,p,newState);//move the piece, newState will have the next state
				//get next available jumps, make sure it is the start (flags?)
				Move[] contMoves = AvailableSingleMoves(p, newState);
				//contMoves will be length 0, 1 or more
				for(int i = 0 ; i < contMoves.length ; i++) {
					//uncompleted.add(contMoves[i]);
				}
				if(contMoves==null || contMoves.length==0) {
					cg_turnTaken=false;
				}
			}
			
			//collect, and 
			move.put(m,newState);//
			//next move
		}
		return (Move[]) move.keySet().toArray();
		//return null;
	}
	
	//cb_holdingpiece= false
	public static void ResetHolding() {
		cb_holdpiece=false;
		//heldpieceoriginalspace=nullspace;
	}
	
	/**
	 * True if the int start exists in the set of moves m's start indices
	 * @param start
	 * @param p
	 * @param m
	 * @return
	 */
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
	
	/**
	 * True if the move given by start and end exists in the set of moves m for player p
	 * @param start
	 * @param end
	 * @param p
	 * @param m
	 * @return
	 */
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
	/**
	 * Removes a piece from the board, print to console what is removed
	 * does not check if there is a piece there
	 * @param removeme
	 * @param state
	 */
	public static void Remove(int removeme,Piece[] state) {
		String piece="";
		switch(state[removeme]) {
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
		state[removeme] = Piece.EMPTY;
	}
	
	/**
	 * The moves start and end
	 * Should only be used for 
	 * @param start
	 * @param end
	 * @param state
	 */
	public static void Move(int start, int end,Piece[] state) {
		if(start == end) {
			return;//can't pass
		}
		Piece temp = state[start];//save
		//check if any pieces are removed
		state[start]=Piece.EMPTY;//remove the piece from the start
		state[end]=temp;//put the piece on the end index
	}
	
	
	public static Piece[] SimulateMove(Move agentsMove,Player p, Piece[] state) {
		Piece[] newState = state;
		
		int start = agentsMove.start;
		int end = agentsMove.end;
		boolean pieceKingedThisTurn=false;
		Move[] moves;
		
		if(playersTurn == Player.BLACK) {
			moves = AvailableSingleMoves(Player.BLACK,newState);
		} else {//red
			moves = AvailableSingleMoves(Player.RED,newState);
		}
		
		if(!cb_holdpiece && (newState[end] == Piece.EMPTY) ) {//probably dont need this
			return newState;//do nothing
		} else if(cb_holdpiece) {//try to place the piece
			if( Player.BLACK == playersTurn &&
				pieceExistsInAvailableMovesEnd(agentsMove.start, end, Player.BLACK, moves)
				//cb.game.pieceExistsInAvailableMovesEnd(cb.heldpieceoriginalspace.id,id,Player.BLACK)	
				) {
				//remove piece
				int removedpiece = getJumpedPiece(start,end);
				Move(agentsMove.start,end,newState);//place the piece
				//check if it is a king now
				if(newState[end] != Piece.KING_BLACK && (end == 31 || end == 30 || end == 29 || end == 28 )) {
					newState[end] = Piece.KING_BLACK;
					pieceKingedThisTurn=true;//need to end turn taking
				}
				//else if is already a king
				if(removedpiece>=0) {
					
					Remove(removedpiece,newState);
					//check for more jumps
					inJump = true;
					lastJumpPiece=end;
					moves = AvailableSingleMoves(Player.BLACK,newState);
					for(Move m : moves) {
						System.out.println("new moves "+m);
					}
				} else {
					moves=null;
				}

				//check for more jumps

			} else if( Player.RED == playersTurn &&
					   CheckersGame.pieceExistsInAvailableMovesEnd(agentsMove.start, end, Player.RED, moves)
					   //cb.game.pieceExistsInAvailableMovesEnd(cb.heldpieceoriginalspace.id,id,Player.RED)	
					   ){//RED
				//remove piece
				int removedpiece = getJumpedPiece(start, end);
				Move(agentsMove.start,end,newState);
				//check if it kinged
				if(newState[end] != Piece.KING_RED && (end == 0 || end == 1 || end == 2 || end == 3) ) {
					newState[end] = Piece.KING_RED;
					pieceKingedThisTurn=true;
				}
				if(removedpiece>=0) {
					Remove(removedpiece,newState);
					//check for more jumps
					inJump = true;
					lastJumpPiece=end;
					moves = AvailableSingleMoves(Player.RED,newState);
					for(Move m : moves) {
						System.out.println("new moves "+m);
					}
				} else {
					moves=null;
				}
				
			} else {
				return newState;
			}
			
			//cb.PlacePiece(id);
			if(moves==null) {
//				System.out.println("MOVES NULL");
				cg_turnTaken=true;
				inJump = false;//reset stuff
				lastJumpPiece=-1;//reset stuff
			} else {
				boolean jumpMovesLeft=false;
				for(Move m : moves) {
					if(m.isJump) {
						jumpMovesLeft=true;
					}
				}
				if(!jumpMovesLeft) {
//					System.out.println("!jumpMovesLeft");
					cg_turnTaken=true;
					inJump = false;//reset stuff
					lastJumpPiece=-1;//reset stuff
				}
			}
			if(pieceKingedThisTurn) {
				cg_turnTaken=true;
				inJump = false;//reset stuff
				lastJumpPiece=-1;//reset stuff
			}
		}
		//after this the new state is returned
		return newState; 
	}
	
	/**
	 * Copy of Checkers game avaiablemoves,
	 * will return an array of Move,
	 * if there are jump moves the single available moves are ignored
	 * DOES not return Move[] of completed jumps
	 * (if a multijump exists this does not return it)
	 * @param p
	 * @param state
	 * @return
	 */
	public static Move[] AvailableSingleMoves(Player p,Piece[] state) {
		ArrayList<Move> singleMoves = new ArrayList<Move>();
		ArrayList<Move> jumpMoves = new ArrayList<Move>();
		
		int[] pieces = StateUtility.PlayersPieces(p,state);
		if(Player.BLACK == p ) {
			//go through player's checkers
			for(int index = 0 ; index < pieces.length ; index++ ) {
				//grab possible locations where the pieces could move or jump
				int[] possibleMoves = BoardUtility.singleMoves[pieces[index]];
				int[] possibleJumps = BoardUtility.singleJumps[pieces[index]];
				
				if(state[pieces[index]] == Piece.KING_BLACK) {//the piece is a king
					//check moves
					for(int i=0 ; i < possibleMoves.length ; i++ ) {
						if(state[possibleMoves[i]] == Piece.EMPTY) {
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					//check jumps
					for(int i = 0 ; i < possibleJumps.length ; i++ ) {
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
						if( state[possibleJumps[i]] == Piece.EMPTY && //
							jumpedPiece >=0 && //there is a piece to jump?
							//opponents piece?
							(state[jumpedPiece] == Piece.KING_RED|| state[jumpedPiece]  == Piece.REG_RED)) {
							jumpMoves.add(new Move(pieces[index], possibleJumps[i],true));
						}
					}
				} else {//the piece is a regular piece
					//check moves
					for(int i=0 ; i < possibleMoves.length ; i++ ) {
						if(possibleMoves[i] > pieces[index] && state[possibleMoves[i]] == Piece.EMPTY) {
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					//checkjumps
					for(int i=0 ; i < possibleJumps.length ; i++ ) {
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);//negative if EMPTY
						if( state[possibleJumps[i]] == Piece.EMPTY && //is the landing spot empty
							jumpedPiece >=0  && //is there a piece to jump
							//opponent piece?
							(state[jumpedPiece] == Piece.KING_RED || state[jumpedPiece]  == Piece.REG_RED) &&
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
				if(state[pieces[index]] == Piece.KING_RED) {//the piece is a king
					//check moves
					for(int i=0 ; i < possibleMoves.length ; i++ ) {
						if(state[possibleMoves[i]] == Piece.EMPTY) {
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					//check jumps
					for(int i = 0 ; i < possibleJumps.length ; i++ ) {
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
						if(state[possibleJumps[i]] == Piece.EMPTY && //
							jumpedPiece >=0 && //there is a piece to jump?
							//opponents piece?
							(state[jumpedPiece] == Piece.KING_BLACK|| state[jumpedPiece]  == Piece.REG_BLACK)) {
							jumpMoves.add(new Move(pieces[index], possibleJumps[i],true));
						}
					}
				} else {//the piece is a regular piece
					//check moves
					for(int i=0 ; i < possibleMoves.length ; i++ ) {
						if(possibleMoves[i] < pieces[index] && state[possibleMoves[i]] == Piece.EMPTY) {
							singleMoves.add(new Move(pieces[index], possibleMoves[i]));
						}
					}
					//checkjumps
					for(int i=0 ; i < possibleJumps.length ; i++ ) {
						int jumpedPiece = getJumpedPiece(pieces[index], possibleJumps[i]);
						if( state[possibleJumps[i]] == Piece.EMPTY && //is the landing spot empty
							jumpedPiece >=0  && //is there a piece to jump
							//opponent piece?
							(state[jumpedPiece] == Piece.KING_BLACK || state[jumpedPiece]  == Piece.REG_BLACK) &&
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
	
//	public static Move[] AvailableMoves(Player p, Piece[] state) {
//		Move[] starts;
//		for() {
//			
//		}
		
		/*/idea
		 * get next move if it is a jump, and add it into the current move being explored
		 * are there more jumps after?
		 * if there are add the first one given by available single moves and if there is more than one available move
		 * create a new Move (the same as the current one) and add the other possible jumps
		 * continue in this manner until kinged or a flag is unset/set (look above to design)
		 * then return the starts array which can grow if there are multiple jumps that can have mulitple options
		*/
//		
//		return null;
//	}
}