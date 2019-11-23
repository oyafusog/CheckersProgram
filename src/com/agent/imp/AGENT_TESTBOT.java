package com.agent.imp;

import com.agent.GenericAgent;
import com.checkersgame.Player;

public class AGENT_TESTBOT extends GenericAgent {

	@Override
	public void EvaluateEndStatesAndSelect() {
		int ply=2;
		MiniMax minimax = new MiniMax(
							game.boardspot,
							ply,
							p,
							(p==Player.BLACK?Player.RED:Player.BLACK)
							);
		minimax.SetEvaluator(new Evaluator_1());
		//Use minimax
		this.execute_this = minimax.MINIMAX_DECISION();
		System.out.println("CHOSE "+execute_this);
		
	}

}
