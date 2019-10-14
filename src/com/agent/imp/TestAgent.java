package com.agent.imp;



import java.util.ArrayList;

import com.agent.Agent;
import com.checkersgame.CheckersGame;
import com.checkersgame.Move;
import com.checkersgame.Player;
import com.checkersgame.gui.CheckerBoard;


public class TestAgent implements Agent {
	
	public Player p;
	public CheckerBoard board;
	public CheckersGame game;
	public ArrayList<Move> move = new ArrayList<Move>();
	
	@Override
	public void Init(Player p,CheckersGame game,  CheckerBoard board) {
		this.p = p;
		this.game=game;
		this.board = board;
	}

	@Override
	public void GetMove() {
		Move[] moves = game.AvailableMoves(p);
		int index = (int)(Math.random()*(moves.length));
		board.playableSpaces[moves[index].start].AgentsMove(p);
		board.playableSpaces[moves[index].end].AgentsMove(p);
		System.out.println("----------> "+moves[index].start+"-"+moves[index].end);
		Collect(moves[index]);
	}

	@Override
	public void Collect(Move m) {
		move.add(m);
	}
/*****
 * 
 * START HERE
 */
	/**
	 * will print the move onto the console to be used by another program
	 */
	@Override
	public void ClearCollectMoves() {

		int [] tmpmove = new int[move.size() +1 ];
		for(int i =0 ;i < move.size() ; i++) {
			tmpmove[i]=move.get(i).start+1;
		}
		tmpmove[move.size()]=move.get(move.size()-1).end+1;//get the last move
		System.out.println();
		String printmove="";
		for(int i =0 ; i < tmpmove.length ; i++) {
			printmove+=tmpmove[i];
			if(i != (tmpmove.length -1)) { 
				printmove+="-";
			}
		}
		System.out.println("MOVE -> "+printmove);
		move=new ArrayList<Move>();
	}
}
