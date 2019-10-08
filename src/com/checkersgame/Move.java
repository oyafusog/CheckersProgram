package com.checkersgame;

import com.agent.Action;

public class Move extends Action {
	public int start;
	public int end;
	public Move(int start, int end) throws Exception{
		if(start<0||start>31||end<0||end>31) {
			throw new Exception("Out Of Board ");
		}
		this.start=start;
		this.end=end;
	}
	
	//hide indexes
	@Override
	public String toString() {
		return "Move [start=" + (start+1) + ", end=" + (end+1) + "]";
	}
	
	
}