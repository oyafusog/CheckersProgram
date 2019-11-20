package com.agent.imp;

import java.util.ArrayList;
import java.util.HashMap;

import com.agent.Agent;
import com.agent.GameUtility;
import com.checkersgame.CheckersGame;
import com.checkersgame.Move;
import com.checkersgame.Piece;
import com.checkersgame.Player;
import com.checkersgame.gui.CheckerBoard;

public class GREG implements Agent {

	public Player p;
	public CheckerBoard board;
	public CheckersGame game;
	ArrayList<HashMap<Move,Piece[]>> futureMoveStates;
	boolean turn =false;//flag
	Move execute_this;
	int movecounter=0;
	
	@Override
	public void Init(Player p, CheckersGame game, CheckerBoard board) {
		this.p=p;
		this.game=game;
		this.board=board;
	}

	@Override
	public void GetMove() {
		if(!turn) {
			turn=true;
			movecounter=0;
			futureMoveStates = GameUtility.GetCompletedStates(p, game.boardspot);//get the possible moves
			EvaluateEndStatesAndSelect();
		} //dont set turn to false until that last move is taken
			//need to return the move execute this, if multi jump
			//then need to separate the move\
		if(execute_this.multiJump==null) {
			board.playableSpaces[execute_this.start].AgentsMove(p);
			board.playableSpaces[execute_this.end].AgentsMove(p);
			turn=false;
		} else {
			board.playableSpaces[execute_this.multiJump[movecounter]].AgentsMove(p);
			board.playableSpaces[execute_this.multiJump[movecounter+1]].AgentsMove(p);
			movecounter++;
			if(movecounter==(execute_this.multiJump.length-1)) {
				turn=false;
			}
		}
	}
	/**
	 * Will set execute_this
	 */
	public void EvaluateEndStatesAndSelect() {
		
		
//		ArrayList<Move> moves = new ArrayList<Move>();
//		for(HashMap<Move,Piece[]> map : futureMoveStates) {
//			for(Move m : map.keySet()) {
//				moves.add(m);
//			}
//		}
//		System.out.println("DEBUG --> "+moves.size());
		
//		HashMap<Move,Piece[]> movestates = GameUtility.ACTIONS(p, game.boardspot);
//		for(Move m : movestates.keySet()) {
//			System.out.println("EVAL --> "+m);
//			execute_this =m;
//		}
		
		
//		//random move for now
//		execute_this = moves.get((int) ((Math.random()*(moves.size()))));
//		System.out.println("CHOSE "+execute_this);
		
		int ply=4;
		MiniMax minimax = new MiniMax(
							game.boardspot,
							ply,
							p,
							(p==Player.BLACK?Player.RED:Player.BLACK)
							);
		minimax.SetEvaluator(new Evaluator_1());
		//Use minimax
		execute_this = minimax.MINIMAX_DECISION();
		System.out.println("CHOSE "+execute_this);
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
