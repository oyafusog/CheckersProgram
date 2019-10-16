package com.agent.imp;

import java.util.ArrayList;
import java.util.List;


import com.agent.Agent;
import com.agent.State;
import com.checkersgame.CheckersGame;
import com.checkersgame.Move;
import com.checkersgame.Piece;
import com.checkersgame.Player;
import com.checkersgame.gui.BoardUtility;
import com.checkersgame.gui.CheckerBoard;
/**
 * TODO, this really does nothing at the moment
 */
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
		int index = FindBestMove();
		//int index = (int)(Math.random()*(moves.length));
		board.playableSpaces[moves[index].start].AgentsMove(p);
		board.playableSpaces[moves[index].end].AgentsMove(p);
		System.out.println("----------> "+moves[index].start+"-"+moves[index].end);
		Collect(moves[index]);
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


	
	//a method to find the "best move", defined by many parameters (feel free to edit weights)
	public int FindBestMove() {
		int bestMove = 0;
		int currentBest = -50; //set the default best low
		int current = 0;
		Move[] moves = game.AvailableMoves(p);
		for(int i = 0; i <= (moves.length -1); i++)
		{
			Piece currentType = game.boardspot[(moves[i]).start];
			game.boardspot[(moves[i]).start] = Piece.EMPTY;
			if (moves[i].isJump == true)
			{
				game.boardspot[(moves[i]).start] = currentType;
				return FindBestJump();
			}
			else
			{
				current = 0;
				if (IsProtected(moves[i].start)) //penalize moving away from "protected" spots
					current += -2;
				if (IsProtected(moves[i].end)) //encourage moving into "protected" spots
					current += 4;
				if (LookForJumps(moves[i].start)[0] == -1) //check if move is leaving a piece vulnerable
					current += -3;
				int result;
				if ((result = ImminentDanger(moves[i].end)) > 0) //check if place you are moving to is stupid
				{
					current += -4;
					if(currentType == Piece.KING_BLACK || currentType == Piece.KING_RED)
						current += -6; //disincentivize loosing kings
					if (LookForJumps(result)[0] == -1) //checks two deep (TODO: add recursion eventually but needs tuning for no infinite loop, projecting future board states)
						current += -5;
				}
				current += (ProtectionByPieces(moves[i].start) - ProtectionByPieces(moves[i].end));
				
				System.out.println(moves[i].start + " " + current);
				if ((currentBest < current && p == Player.RED) || (currentBest <= current && p == Player.BLACK))
				{
					currentBest = current;
					bestMove = i;
					System.out.println("current best move " + bestMove);
				}
				game.boardspot[(moves[i]).start] = currentType;
					
			}
		}
		
		return bestMove;
		
	}

	//determines whether immediately adjacent spaces contain a piece in danger
	public int[] LookForJumps(int space) {
		int[] possibleMoves = BoardUtility.singleMoves[space];
		int[] jumps = {-1, -1, -1};
		int totalJumps = 0;
		if(space==0||space==3||space==4||space==27||space==28||space==31)
			return jumps;
		else if (p == Player.RED)
		{
			if(space==1||space==2)
			{
				if(game.boardspot[possibleMoves[0]] == Piece.REG_RED)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[0]);
					totalJumps += 1;
				}
				if(game.boardspot[possibleMoves[1]] == Piece.REG_RED)
					jumps[totalJumps] = ImminentDanger(possibleMoves[1]);
			}
			else if(space==12||space==20)
			{
				if(game.boardspot[possibleMoves[0]] == Piece.REG_RED)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[0]);	
					totalJumps += 1;
				}
					if(game.boardspot[possibleMoves[1]] == Piece.REG_RED)
					jumps[totalJumps] = ImminentDanger(possibleMoves[1]);
			}
			else if(space==11||space==19)
			{
				if(game.boardspot[possibleMoves[1]] == Piece.REG_RED)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[1]);
					totalJumps += 1;
				}
				if(game.boardspot[possibleMoves[0]] == Piece.REG_RED)
					jumps[totalJumps] = ImminentDanger(possibleMoves[0]);
			}
			else if(space==29||space==30)
			{
				if(game.boardspot[possibleMoves[1]] == Piece.REG_RED)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[1]);
					totalJumps += 1;
				}
				if(game.boardspot[possibleMoves[0]] == Piece.REG_RED)
					jumps[totalJumps] = jumps[totalJumps] = ImminentDanger(possibleMoves[0]);
			}
			else if(game.boardspot[possibleMoves[1]] == Piece.REG_RED)
			{
				jumps[totalJumps] = ImminentDanger(possibleMoves[1]);
				totalJumps += 1;
			}
			if(game.boardspot[possibleMoves[0]] == Piece.REG_RED)
			{
				jumps[totalJumps] = ImminentDanger(possibleMoves[0]);
				totalJumps += 1;
			}
			try {
				if(game.boardspot[possibleMoves[2]] == Piece.REG_RED)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[2]);
					totalJumps += 1;
				}
				if(game.boardspot[possibleMoves[3]] == Piece.REG_RED)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[3]);
					totalJumps += 1;
				}
			} catch(ArrayIndexOutOfBoundsException ex) {
				System.out.println("array too small");
			}
			
		}
		else if (p == Player.BLACK)
		{
			if(space==1||space==2)
			{
				if(game.boardspot[possibleMoves[0]] == Piece.REG_BLACK)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[0]);	
					totalJumps += 1;
				}	
				if(game.boardspot[possibleMoves[1]] == Piece.REG_BLACK)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[1]);
					totalJumps += 1;
				}
			}
			else if(space==12||space==20)
			{
				if(game.boardspot[possibleMoves[0]] == Piece.REG_BLACK)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[0]);
					totalJumps += 1;
				}
				if(game.boardspot[possibleMoves[1]] == Piece.REG_BLACK)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[1]);
					totalJumps += 1;
				}
			}
			else if(space==11||space==19)
			{
				if(game.boardspot[possibleMoves[0]] == Piece.REG_BLACK)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[0]);
					totalJumps += 1;
				}
				if(game.boardspot[possibleMoves[1]] == Piece.REG_BLACK)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[1]);
					totalJumps += 1;
				}
			}
			else if(space==29||space==30)
			{
				if(game.boardspot[possibleMoves[1]] == Piece.REG_BLACK)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[1]);
					totalJumps += 1;
				}
				if(game.boardspot[possibleMoves[0]] == Piece.REG_BLACK)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[0]);
					totalJumps += 1;
				}
			}
			else if(game.boardspot[possibleMoves[1]] == Piece.REG_BLACK)
			{
				jumps[totalJumps] = ImminentDanger(possibleMoves[1]);
				totalJumps += 1;
			}
			if(game.boardspot[possibleMoves[0]] == Piece.REG_BLACK)
			{
				jumps[totalJumps] = ImminentDanger(possibleMoves[0]);
				totalJumps += 1;
			}
			try
			{
				if(game.boardspot[possibleMoves[2]] == Piece.REG_BLACK)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[2]);
					totalJumps += 1;
				}
				if(game.boardspot[possibleMoves[3]] == Piece.REG_BLACK)
				{
					jumps[totalJumps] = ImminentDanger(possibleMoves[3]);
					totalJumps += 1;
				}
			} catch(ArrayIndexOutOfBoundsException ex) {
				System.out.println("array too small");
			}
		}
		return jumps;
		
	}

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

	//currently just returns a random number but will be used to optimize jumps
	public int FindBestJump() {
			int bestMove = 0;
			int currentBest = -10;
			int current[];
			Move[] moves = game.AvailableMoves(p);
			return (int)(Math.random()*(moves.length)); //still random
		}
	
	//this uses LookForJumps() to search for the next jump for the AI, there's probably a better way to do this
	public int[] NextJump(int end) {
		if (p == Player.BLACK)
			p = Player.RED;
		else if (p == Player.RED)
			p = Player.BLACK;
		
		int nextJumps[] = LookForJumps(end);
		
		if (p == Player.BLACK)
			p = Player.RED;
		else if (p == Player.RED)
			p = Player.BLACK;
		return nextJumps;
	}

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

	@Override
	public void Collect(Move m) {
		move.add(m);
		
	}
}
