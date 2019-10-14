package com.agent;

import com.checkersgame.CheckersGame;
import com.checkersgame.Move;
import com.checkersgame.Player;
import com.checkersgame.gui.CheckerBoard;

public interface Agent {
	public void Init(Player p,CheckersGame game, CheckerBoard board);
	public void GetMove();
	public void ClearCollectMoves();
	public void Collect(Move m);
}
