package com.agent.imp;

import java.util.ArrayList;
import java.util.List;

import com.agent.Action;
import com.agent.Agent;
import com.agent.State;
import com.checkersgame.Move;
/**
 * TODO, this really does nothing at the moment
 */
public class TestAgent implements Agent {
	
	
	
	@Override
	public void SetColor(String color) {
		
	}

	@Override
	public Move GetMove() {
		//Lookup data in evaluator
		try {
			return new Move(0,1);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}
	public List<Move> FindAvailableMoves() {
		List<Move> moves = null;
		try {
			moves = new ArrayList<Move>();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (List<Move>)moves;
	}

	@Override
	public Action Percieve(State s) {
		return null;
	}
}
