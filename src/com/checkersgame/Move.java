package com.checkersgame;

import java.util.ArrayList;

public class Move{
	
	boolean help = true;//print indexs starting from 1
	public int start;
	public int end;
	public boolean isJump;//flag for jumps
	
	//variables used for tracking all jumps in a move
	public int[] multiJump;//reference this
	public ArrayList<Move> pastMove;//
	
	public Move(int ... jump ) {
		start = jump[0];
		end = jump[jump.length-1];
		multiJump = jump;
		this.pastMove = new ArrayList<Move>();
	}
	
	public Move(int start, int end) {
		this.pastMove = new ArrayList<Move>();
		this.start=start;
		this.end=end;
		this.isJump=false;
	}
	
	public Move(int start, int end,boolean isJump) {
		this.start=start;
		this.end=end;
		this.isJump = isJump;
		this.pastMove = new ArrayList<Move>();
	}
	
	public void Track(Move m) {
		pastMove.add(m);
	}
	
	//hide indexes
	@Override
	public String toString() {
		if(help) {
			return "Move [ start="+(start+1)+", end="+(end+1)+" ] " + (isJump ?"Jump Move":"");
		} else {
			return "Move [ start="+start+", end="+end+" ] " + (isJump ?"Jump Move":"");
		}
	}
}