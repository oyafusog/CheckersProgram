package com.agent.imp;

import com.agent.Evaluator;
import com.checkersgame.Piece;
import com.checkersgame.Player;
import com.checkersgame.gui.BoardUtility;

public class Evaluator_1 implements Evaluator {

	@Override
	public int Utility(Piece[] state, Player p) {

		Player otherPlayer = ( Player.BLACK==p ? Player.RED : Player.BLACK);//figure out which color the opposing player is
		int myNum =	NumberOfPieces(p,state);//number of my pieces
		int opponentsNum = NumberOfPieces(otherPlayer,state);//number of opponents pieces
		int utility = myNum - opponentsNum;
		
		return utility;
	}

	public int NumberOfPieces(Player p, Piece[] state) {
		int sum=0;
		for( int i = 0 ; i < state.length ; i++ ) {
			sum += isPlayers(p,i,state) ? 1 : 0;
		}
		return sum;
	}
	
	public int NumberOfKings(Player p, Piece[] state) {
		int sum=0;
		for( int i = 0 ; i < state.length ; i++ ) {
			sum += isPlayersKing(p,i,state) ? 1 : 0;
		}
		return sum;
	}
	
	public boolean isPlayers(Player p, int index, Piece[] state) {
		if(p==Player.BLACK) {//black
			return state[index] == Piece.REG_BLACK || state[index] == Piece.KING_BLACK;
		} else {//red
			return state[index] == Piece.REG_RED || state[index] == Piece.KING_RED;
		}
	}
	public boolean isPlayersKing(Player p, int index, Piece[] state) {
		if(p==Player.BLACK) {//black
			return state[index] == Piece.KING_BLACK;
		} else {//red
			return state[index] == Piece.KING_RED;
		}
	}
	/*
	//method determines whether move will result in capture
	public int ImminentDanger(int space) {
		int[] possibleMoves = BoardUtility.singleMoves[space];
			if (IsProtected(space))
				return -1;
			else 
			{
				if (p == Player.RED)
				{
					if (game.boardspot[possibleMoves[1]] == Piece.REG_BLACK && game.boardspot[possibleMoves[2]] == Piece.EMPTY)
						return possibleMoves[2];
					else if (game.boardspot[possibleMoves[0]] == Piece.REG_BLACK && game.boardspot[possibleMoves[3]] == Piece.EMPTY)
						return possibleMoves[3];
					else if (game.boardspot[possibleMoves[1]] == Piece.KING_BLACK && game.boardspot[possibleMoves[2]] == Piece.EMPTY)
						return possibleMoves[2];
					else if (game.boardspot[possibleMoves[0]] == Piece.KING_BLACK && game.boardspot[possibleMoves[3]] == Piece.EMPTY)
						return possibleMoves[3];
					else if (game.boardspot[possibleMoves[2]] == Piece.KING_BLACK && game.boardspot[possibleMoves[1]] == Piece.EMPTY)
						return possibleMoves[1];
					else if (game.boardspot[possibleMoves[3]] == Piece.KING_BLACK && game.boardspot[possibleMoves[0]] == Piece.EMPTY)
						return possibleMoves[0];
					{
						
					}
				} else if (p == Player.BLACK)
				{
					if (game.boardspot[possibleMoves[2]] == Piece.REG_RED && game.boardspot[possibleMoves[1]] == Piece.EMPTY)
						return possibleMoves[1];
					else if (game.boardspot[possibleMoves[3]] == Piece.REG_RED && game.boardspot[possibleMoves[0]] == Piece.EMPTY)
						return possibleMoves[0];
					else if (game.boardspot[possibleMoves[2]] == Piece.KING_RED && game.boardspot[possibleMoves[1]] == Piece.EMPTY)
						return possibleMoves[1];
					else if (game.boardspot[possibleMoves[3]] == Piece.KING_RED && game.boardspot[possibleMoves[0]] == Piece.EMPTY)
						return possibleMoves[0];
					else if (game.boardspot[possibleMoves[1]] == Piece.KING_RED && game.boardspot[possibleMoves[2]] == Piece.EMPTY)
						return possibleMoves[2];
					else if (game.boardspot[possibleMoves[0]] == Piece.KING_RED && game.boardspot[possibleMoves[3]] == Piece.EMPTY)
						return possibleMoves[3];
				}
			}
		return -1;
	}	

	//method checks if space is _fully_ protected or not
	public boolean IsProtected(int space) {
		System.out.println("/" + space);
		if (isEdgeSpace(space))
		{
			return true;
		}
		else if (ProtectionByPieces(space) == 3)
		{
			return true;
		}
		else
		{
			return false;
		}	
	}	
	
	//method determines whether a space is on the edge of the board
	public boolean isEdgeSpace(int space) {
		if(space==0||space==1||space==2||space==3|| 
			space==4||space==11||space==12||space==19||		   
			space==20||space==27||space==28||space==29||
			space==30||space==31)
			return true;
		else
			return false;
	}
	
	//method quantifies protective capacity of space
	public int ProtectionByPieces(int space) {
		System.out.println("//" + space);
		int[] possibleMoves = BoardUtility.singleMoves[space];
		if (isEdgeSpace(space) == true)
		{
			return 2;
		}
		else if (p == Player.RED)
		{
			if((game.boardspot[possibleMoves[0]] == Piece.REG_RED || game.boardspot[possibleMoves[0]] == Piece.KING_RED) && (game.boardspot[possibleMoves[1]] == Piece.REG_RED || game.boardspot[possibleMoves[1]] == Piece.KING_RED))
				return 3;
			else if ((game.boardspot[possibleMoves[0]] == Piece.REG_RED || game.boardspot[possibleMoves[0]] == Piece.KING_RED) && (game.boardspot[possibleMoves[2]] == Piece.REG_RED || game.boardspot[possibleMoves[2]] == Piece.KING_RED))
				return 3;
			else if((game.boardspot[possibleMoves[3]] == Piece.REG_RED || game.boardspot[possibleMoves[3]] == Piece.KING_RED) && (game.boardspot[possibleMoves[1]] == Piece.REG_RED || game.boardspot[possibleMoves[1]] == Piece.KING_RED))
				return 3;
			else if ((game.boardspot[possibleMoves[3]] == Piece.REG_RED || game.boardspot[possibleMoves[3]] == Piece.KING_RED) && (game.boardspot[possibleMoves[2]] == Piece.REG_RED || game.boardspot[possibleMoves[2]] == Piece.KING_RED))
				return 3;
			else if((game.boardspot[possibleMoves[0]] == Piece.REG_RED || game.boardspot[possibleMoves[0]] == Piece.KING_RED) && (game.boardspot[possibleMoves[3]] == Piece.REG_RED || game.boardspot[possibleMoves[3]] == Piece.KING_RED))
				return 2;
			else if ((game.boardspot[possibleMoves[2]] == Piece.REG_RED || game.boardspot[possibleMoves[2]] == Piece.KING_RED) && (game.boardspot[possibleMoves[1]] == Piece.REG_RED || game.boardspot[possibleMoves[1]] == Piece.KING_RED))
				return 2;
			else if (game.boardspot[possibleMoves[0]] == Piece.REG_RED || game.boardspot[possibleMoves[0]] == Piece.KING_RED)
				return 1;
			else if (game.boardspot[possibleMoves[1]] == Piece.REG_RED || game.boardspot[possibleMoves[1]] == Piece.KING_RED)
				return 1;
			else if (game.boardspot[possibleMoves[2]] == Piece.REG_RED || game.boardspot[possibleMoves[2]] == Piece.KING_RED)
				return 1;
			else if (game.boardspot[possibleMoves[3]] == Piece.REG_RED || game.boardspot[possibleMoves[3]] == Piece.KING_RED)
				return 1;
			else
				return 0;
		}
		else if (p == Player.BLACK)
		{
			if((game.boardspot[possibleMoves[0]] == Piece.REG_BLACK || game.boardspot[possibleMoves[0]] == Piece.KING_BLACK) && (game.boardspot[possibleMoves[1]] == Piece.REG_BLACK || game.boardspot[possibleMoves[1]] == Piece.KING_BLACK))
				return 3;
			else if ((game.boardspot[possibleMoves[0]] == Piece.REG_BLACK || game.boardspot[possibleMoves[0]] == Piece.KING_BLACK) && (game.boardspot[possibleMoves[2]] == Piece.REG_BLACK || game.boardspot[possibleMoves[2]] == Piece.KING_BLACK))
				return 3;
			else if((game.boardspot[possibleMoves[3]] == Piece.REG_BLACK || game.boardspot[possibleMoves[3]] == Piece.KING_BLACK) && (game.boardspot[possibleMoves[1]] == Piece.REG_BLACK || game.boardspot[possibleMoves[1]] == Piece.KING_BLACK))
				return 3;
			else if ((game.boardspot[possibleMoves[3]] == Piece.REG_BLACK || game.boardspot[possibleMoves[3]] == Piece.KING_BLACK) && (game.boardspot[possibleMoves[2]] == Piece.REG_BLACK || game.boardspot[possibleMoves[2]] == Piece.KING_BLACK))
				return 3;
			else if((game.boardspot[possibleMoves[0]] == Piece.REG_BLACK || game.boardspot[possibleMoves[0]] == Piece.KING_BLACK) && (game.boardspot[possibleMoves[3]] == Piece.REG_BLACK || game.boardspot[possibleMoves[3]] == Piece.KING_BLACK))
				return 2;
			else if ((game.boardspot[possibleMoves[2]] == Piece.REG_BLACK || game.boardspot[possibleMoves[2]] == Piece.KING_BLACK) && (game.boardspot[possibleMoves[1]] == Piece.REG_BLACK || game.boardspot[possibleMoves[1]] == Piece.KING_BLACK))
				return 2;
			else if (game.boardspot[possibleMoves[0]] == Piece.REG_BLACK || game.boardspot[possibleMoves[0]] == Piece.KING_BLACK)
				return 1;
			else if (game.boardspot[possibleMoves[1]] == Piece.REG_BLACK || game.boardspot[possibleMoves[1]] == Piece.KING_BLACK)
				return 1;
			else if (game.boardspot[possibleMoves[2]] == Piece.REG_BLACK || game.boardspot[possibleMoves[2]] == Piece.KING_BLACK)
				return 1;
			else if (game.boardspot[possibleMoves[3]] == Piece.REG_BLACK || game.boardspot[possibleMoves[3]] == Piece.KING_BLACK)
				return 1;
			else
				return 0;
		}
		return 0;
	}
	*/
}
