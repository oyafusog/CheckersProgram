package com.checkersgame;

import java.util.List;


//agents view of the board
public class CheckersGame {
	
	public Piece[] boardspot;

	public CheckersGame() {
		boardspot = new Piece[] {
				Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK,
				Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, 
				Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, Piece.REG_BLACK, 
				Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,
				Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,     Piece.EMPTY,
				Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,
				Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,
				Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,   Piece.REG_RED,
		};
	}
	
	public int NumberOfBlackPieces() {
		int sum = 0;
		for(Piece i : boardspot) {
			if(i==Piece.REG_BLACK || i==Piece.KING_BLACK) {
				sum++;
			}
		}
		return sum;
	}
	
	public int NumberOfRedPieces() {
		int sum = 0;
		for(Piece i : boardspot) {
			if(i==Piece.REG_RED || i==Piece.KING_RED) {
				sum++;
			}
		}
		return sum;
	}
	
	public Piece GetSpace(int p) {
		return boardspot[p];
	}
	
	/**
	 *  Moves are jumps or single space moves
	 */
	public boolean ValidMove(Move m) {
		return ValidMove(m.start,m.end);
	}
	
	public boolean ValidSingleMove(Move m) {
		return ValidSingleMove(m.start,m.end);
	}
	/**
	 * TODO : NEED TO CHECK if the REGULAR piece is only moving forward
	 */
	public boolean ValidSingleMove(int start, int end) {
		int _direction = Math.abs(start-end);
		//if a red or black piece tries to move to an occupied space return false
		if( ( boardspot[start]==Piece.REG_RED || boardspot[start]==Piece.REG_BLACK)
				&& ! (boardspot[end] == Piece.EMPTY) ) {
			return false;
/****probably another if statement here to check if the regular piece is only moving forward***/
		//same logic, check kings
		} else if( ( boardspot[start]==Piece.KING_RED || boardspot[start]==Piece.KING_BLACK)
				&& ! (boardspot[end] == Piece.EMPTY) ) {
			return false;
		} else if( !(_direction==3 ||
				     _direction==4 ||
				     _direction==5   )) {
			//the only possible values for 1 of 4 directions from 
			//the board format
			return false;
		} else {//the move is next to the piece and valid
			return true;
		}
	}
	
	public boolean ValidMove(int start, int end) {
		//TODO
		return false;
	}
	
	//remember black starts at the lower numbers
	//red starts at the higher numbers
	public List<Move> AvailableMoves(Player p) {
		//get all moves available to player
		//if there are jumps available filter down the moves to those
		return null;
	}
	
	/**
	 * Only call after checks have been made
	 * @param start the position of the piece
	 * @param end the end of the piece
	 */
	public void Move(int start, int end) {
		if(start == end) {
			return;
		}
		Piece temp = boardspot[start];//save
		boardspot[end]=temp;//set
		//check if any pieces are removed
		boardspot[start]=Piece.EMPTY;//reset
	}
	public void Remove(int removeme) {
		boardspot[removeme] = Piece.EMPTY;
	}
}
