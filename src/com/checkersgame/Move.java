// This puts "Move.java" in the package "com.checkersgame"
package com.checkersgame;

// Imports the ArrayList class
import java.util.ArrayList;

// This class defines the Move
public class Move	{
	
	boolean help = true;
	
	// "start" and "end" will be the spot where you're at, and where you're trying to go
	public int start;
	public int end;
	
	// Boolean to check if you can jump
	public boolean isJump;
	
	// Keeps track of the indexs for a multi-jump
	public int[] multiJump;
	
	// This is an ArrayList of Moves made in the past
	public ArrayList<Move> pastMove;
	
	// Constructor to make a move
	public Move(int ... jump )	{	// throws Exception	{
		
		for(int i : jump)	{
			
/*			if(start < 0 || start > 31 || end < 0 || end > 31)	{
 *
 *				throw new Exception("Out Of Board");
 *			}
 */			
		}
		
		start = jump[0];
		end = jump[jump.length-1];
		multiJump = jump;
		this.pastMove = new ArrayList<Move>();
	}
	
	// Constructor to make a move from start to end (Cannot Jump)
	public Move(int start, int end)	{	// throws Exception	{
		
/*		if(start < 0 || start > 31 || end < 0 || end > 31)	{
 *		
 *			throw new Exception("Out Of Board");
 *		}
 */
		// Creates a new ArrayList of Past Moves
		this.pastMove = new ArrayList<Move>();
		
		// Set's Start and End
		this.start=start;
		this.end=end;
		
		// No jump is available for this move
		this.isJump=false;
	}
	
	// Constructor to make a move from start to end (decides if it can jump)
	public Move(int start, int end, boolean isJump) {	//throws Exception	{

/*		if(start < 0 || start > 31 || end < 0 || end > 31)	{
 *		
 *			throw new Exception("Out Of Board");
 *		}
 */		
		
		// Set's the Start, End, and if it can Jump or not
		this.start=start;
		this.end=end;
		this.isJump = isJump;
		
		// Creates a new ArrayList of Past Moves
		this.pastMove = new ArrayList<Move>();
	}
	
	// Tracks the Move made, and adds it to the Past Moves Array List
	public void Track(Move m)	{
		
		// Adding to pastMove Array List
		pastMove.add(m);
	}
	
	// To String method to print the moves actions, either with the actual index, or the boards index
	//hide indexes
	@Override
	public String toString()	{
		
		if(help)	{
			
			return "Move [ start=" + (start + 1) + ", end=" + (end + 1) + " ] " + (isJump ? "Jump Move" : "");
		}
		else	{
			
			return "Move [ start=" + start + ", end=" + end + " ] " + (isJump ? "Jump Move" : "");
		}
//		if(multiJump == null) {
//			return "Move [start=" + (start+1) + ", end=" + (end+1) + "]";
//		} else {
//			String m = "";
//			for(int i = 0 ; i < multiJump.length; i++) {
//				m += i;
//				if(i != (multiJump.length-1) ) {
//					m+="-";
//				}
//			}
//			return "Move [ "+m+" ] " + (isJump ?"Jump Move":"");
//		}
	}
}