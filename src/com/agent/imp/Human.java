package com.agent.imp;

import com.agent.Action;
import com.agent.Agent;
import com.agent.State;
import com.checkersgame.CheckersGame;
import com.checkersgame.Move;
import com.checkersgame.Piece;
import com.checkersgame.Player;
import com.checkersgame.gui.CheckerBoard;

public class Human implements Agent {
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
	public Action Percieve(State s) {
		//allow the gui to perceive or rather, the other player
		return null;
	}

}
