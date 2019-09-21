package com.checkersgame;

import com.agent.Action;

public class Move extends Action {
	public int start;
	public int end;
	public Move(int start, int end) throws Exception{
		if(start<1||start>32||end<1||end>32) {
			throw new Exception("Out Of Board ");
		}
		this.start=start;
		this.end=end;
	}
}