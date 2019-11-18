package com.agent;

import com.checkersgame.Piece;
import com.checkersgame.Player;

public interface Evaluator {
	public int Utility(Piece[] state,Player p);
}
