package com.agent;

import com.checkersgame.Move;

public interface Agent {
	public void SetColor(String color);
	public Move GetMove();
	public Action Percieve(State s);
}
