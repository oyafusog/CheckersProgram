package com.agent.imp;

import com.agent.GenericAgent;
import com.checkersgame.Player;
/**
 * This is an example class, to setup any agent, and evaluator
 * Follow these steps
 * 
 * 1. Create a class that implements com.agent.evaluator 
 * 	  and make sure that Utility() gives an int value back
 * 
 * 2. Extend the abstract class GenericAgent as shown below,
 *    Ensure that minimax is instantiated as below
 * 
 * 3. use minimax.SetEvSetEvaluator(Evaluator) with the evaluator of choice
 * 
 * 4. Change RunAGame.java to have Agent player2 as the newly extended agent class
 * 
 */
public class AGENT_TESTBOT extends GenericAgent {

	
	/*
	 * I've left a few things in this class to seperate the game logic from
	 * the MiniMax Algorithm
	 */
	@Override
	public void EvaluateEndStatesAndSelect() {
		int ply=2;								//Use this to change the depth minimax will search
		//Could move this to Generic agent, not sure yet
		MiniMax minimax = new MiniMax(
							game.boardspot,
							ply,
							p,
							(p==Player.BLACK?Player.RED:Player.BLACK)
							);
		minimax.SetEvaluator(new Evaluator_1()); //Set your evaluator here
		
		//this can probably be moved into GenericAgent
		this.execute_this = minimax.MINIMAX_DECISION();//be sure to get a move from minimax
		System.out.println("CHOSE "+execute_this);//debug
		
	}

}
