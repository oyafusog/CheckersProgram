package com.agent.imp;

import com.agent.Agent;
import com.checkersgame.CheckersGame;
import com.checkersgame.Move;
import com.checkersgame.Player;
import com.checkersgame.gui.CheckerBoard;

public class OtherBot implements Agent {
	
	public Player p;
	public CheckerBoard board;
	public CheckersGame game;
	@Override
	public void Init(Player p,CheckersGame game,  CheckerBoard board) {
		this.p = p;
		this.game=game;
		this.board = board;
	}

	@Override
	public void GetMove() {
		//allow the gui to handle stuff
	}

	@Override
	public void ClearCollectMoves() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Collect(Move m) {
		// TODO Auto-generated method stub
		
	}

}
