// This puts "Piece.java" into the package "com.checkersgame"
package com.checkersgame;

// Import for the program
import java.awt.Color;

/*
 * This is another Enum Class, it creates an object known as a "Piece".
 * A Piece can either be regular "REG" or a king "KING". They will also
 * have a color attached to them. Lastly, there is "EMPTY" to represent
 * the blank spaces on the board (unoccupied spaces). 
 */

// The Enum Class defines a "Piece"
public enum Piece { 
	
	// A "Piece" will be either "RED" or "BLACK", "REG" or "KING"
	// and it could be an "EMPTY" space.
	REG_BLACK	(Color.BLACK),
	REG_RED		(Color.RED),
	KING_RED	(Color.RED),
	KING_BLACK	(Color.BLACK),
	EMPTY		(Color.WHITE);
	
	// Defining Color
	public final Color c;
	
	// Creating a Piece requires a "Color" "c" to intialize the Piece.
	Piece (Color c)	{
		
		this.c=c;
	}
}
