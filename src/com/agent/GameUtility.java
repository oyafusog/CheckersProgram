package com.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.checkersgame.Move;
import com.checkersgame.Piece;
import com.checkersgame.Player;
import com.checkersgame.gui.BoardUtility;

public class GameUtility {
	
	/**
	 * Assumed that if the move is a jump it is already valid
	 * @param m the move
	 * @param state
	 * @return the new state
	 */
	public static Piece[] MovePiece(Move m,Piece[] state) {
		Piece[] newState = state.clone();
		if(m.isJump) {//have to remove something
			int jumped= getJumpedPiece(m.start, m.end);
			Remove(jumped, newState);
		}
		Move(m.start, m.end, newState);
		return newState;
	}
	
	//will return the state after making move m
	//or returns the set of moves if it is a jump, and states of the jump
	public static HashMap<Move,Piece[]> CheckersPieceMove(Move m, Player p,Piece[] state) {
		HashMap<Move,Piece[]> outcomes = new HashMap<Move,Piece[]>();
		if(!m.isJump) {
			Piece[] newState = MovePiece(m,state);
			outcomes.put(m, newState);
			return outcomes;//no need to try and complete jumps
		}
		//otherwise it is a jump lets try moving the piece
		Move tmpm = m;
		Piece[] tmps = state.clone();
		
		LinkedList<Move> contMove = new LinkedList<Move>();
		contMove.add(tmpm);
		LinkedList<Piece[]> nextStates = new LinkedList<Piece[]>();
		nextStates.add(tmps);
		
		while(!contMove.isEmpty()) {
			Move temp = contMove.removeFirst();//the previous move
			Piece[] newState = MovePiece(temp,nextStates.removeFirst());//simulate the move
			
			Move[] newerMoves = AvailableStarts(p, newState);//new moves from new state			
			System.out.println("On move --> "+ temp);
			int size=0;
			
			for(int i =0;i<newerMoves.length; i++) {
				if(temp.end==newerMoves[i].start && newerMoves[i].isJump) {
					size++;
					System.out.println(newerMoves[i]+"<--");
				}
			}
			if(size==0) {//this move will end here
				//maybe combine moves so far
				outcomes.put(temp, newState);
			} else {//it is connected and there is another jump
				for(int k = 0 ; k <newerMoves .length ;k++) {
					//Move must connect and must be another jump
					if(temp.end==newerMoves[k].start && newerMoves[k].isJump) {
						System.out.println("\n\n"+temp+"\n"+newerMoves[k]+"\n\n");
						for(Move past : temp.pastMove) {
							newerMoves[k].Track(past);
						}
						newerMoves[k].Track(temp);
						contMove.addLast(newerMoves[k]);
						nextStates.addLast(newState);
						//System.out.println(newerMoves[k]);
					}
				}
			}
		}
		//lets reformat the move
		for(Move tmp : outcomes.keySet()) {
			System.out.println("START-----> " + tmp);
			for(Move sss: tmp.pastMove) {
				System.out.println("TEMP -----> " + sss);
			}
//			System.out.println(tmp+" contains ");
			ArrayList<Integer> multimove = new ArrayList<Integer>();
			
			for(int i = 0 ; i < tmp.pastMove.size() ; i++) {
				Move inQuest = tmp.pastMove.get(i);
				multimove.add(inQuest.start);
//				if(i==0) {//grab the start
//					multimove.add(inQuest.start);
//				}
//				if(i+1==multimove.size()) {
//					multimove.add(tmp.end);
//				}else {
//					multimove.add(inQuest.end);
//				}
////				if(i+1==tmp.pastMove.size()) {
////					multimove.add(inQuest.end);
////				}
////				System.out.println(tmp.pastMove.get(i));
			}
			multimove.add(tmp.start);
			multimove.add(tmp.end);
			int[] multiJumpTMP = new int[multimove.size()];
			for(int i = 0 ; i  < multimove.size() ; i++) {
				multiJumpTMP[i] = multimove.get(i);
			}
			tmp.multiJump = multiJumpTMP;
		}
		return outcomes;
	}
	
	public static ArrayList<HashMap<Move,Piece[]>> GetCompletedStates(Player p, Piece[] state) {
		ArrayList<HashMap<Move,Piece[]>> outcomes = new ArrayList<HashMap<Move,Piece[]>>();
		Move[] starts = AvailableStarts(p,state);//Start of moves
		for(int i = 0 ; i < starts.length ; i++ ) {
			HashMap<Move,Piece[]> stuff = CheckersPieceMove(starts[i],p,state);
			for(Move m : stuff.keySet()) {
				System.out.println(m);
			}
			outcomes.add(stuff);
		}
		return outcomes;
	}
	
	/**
	 * Returns the available Moves on player turns start
	 * @param p
	 * @param state
	 * @return Move[] of players choices
	 */
	public static Move[] AvailableStarts(Player p,Piece[] state) {
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
		//return tmp, if jumpMoves is not empty only return those
		
		Move[] tmp ;
		if(jumpMoves.isEmpty()) {
			tmp = new Move[singleMoves.size()];
			singleMoves.toArray(tmp);
		} else {
			tmp = new Move[jumpMoves.size()];
			jumpMoves.toArray(tmp);

		}
		return tmp;
	}
	
	/**
	 * Returns where the players pieces are on the board in the state 
	 * DOES NOT TELL THE PLAYER IF THE PIECE IS A KING OR NOT
	 * @param p	which player
	 * @param state the state we are asking about
	 * @return int[] of indexes of where the players pieces are
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
	 * The moves start and end
	 * Should only be used for single moves
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
		//System.out.println("Removed "+piece+" on "+(removeme+1));
		if(removeme<0) {
			return;//not a space
		}
		state[removeme] = Piece.EMPTY;
	}
	
	/**
	 * This is for a single jump, returns the spot where a piece should exist
	 * does not check the jumped piece's existence
	 * @param start where does the jump start
	 * @param end where does it end
	 * @return the index of what is jumped
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
	
}
