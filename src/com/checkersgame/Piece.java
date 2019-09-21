package com.checkersgame;

import java.awt.Color;
/*
 * REG pieces are normal pieces that should only go forward
 * KING are kings
 * EMPTY should be placed in  unoccupied spaces
 */
public enum Piece { 
	REG_BLACK	(Color.BLACK),
	REG_RED		(Color.RED),
	KING_RED	(Color.RED),
	KING_BLACK	(Color.BLACK),
	EMPTY		(Color.WHITE);
	
	public final Color c;
	Piece(Color c){
		this.c=c;
	}
}
