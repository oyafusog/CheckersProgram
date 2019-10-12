package com;
import java.util.Scanner;

import com.agent.Agent;
import com.checkersgame.*;
import com.checkersgame.gui.CheckerBoard;

/**
 * Main class
 */
public class RunAGame {
	public static void main(String ... args) throws Exception {
		
		CheckersGame theGame = new CheckersGame();
		CheckerBoard theBoard= new CheckerBoard(theGame);
		
		//assign players, and agents to play against
		Scanner in = new Scanner(System.in);
		System.out.println("Game Starting 0 if you are RED, 1 if BLACK");
//		int _player = Integer.parseInt(in.next());//0 red, 1 black
//		if(_player > 0) {
//			//BLACK
//		}
		//theGame.playersTurn = Player.BLACK ;
		boolean gameover = false;
		while(!gameover ) {
			
			////////BLACK TURN
			System.out.println("TURN BLACK ");theGame.playersTurn  = Player.BLACK ;//black takes turn, black always goes first
			//take turn
			CheckersGame.turnTaken=false;
			Move[] availableMovesBLACK = theGame.AvailableMoves(Player.BLACK);
			if(availableMovesBLACK.length==0) {
				theGame.GameOver(Player.RED, " No more moves for player : BLACK");
				gameover=true;
				continue;
			}
			for(Move m : availableMovesBLACK) {
				System.out.println(m);
			}
			while(!CheckersGame.turnTaken){synchronized (theGame) {}}//wait until turn is taken, maybe pause thread
			
			//update the gui
			System.out.println("BLACK has taken their turn");
			
			////////RED TURN
			
			System.out.println("TURN RED ");theGame.playersTurn  = Player.RED ;//red's turn
			//take turn
			CheckersGame.turnTaken=false;
			Move[] availableMovesRED = theGame.AvailableMoves(Player.RED);
			if(availableMovesRED.length==0) {
				theGame.GameOver(Player.BLACK, " No more moves for player : RED");
				gameover=true;
				continue;
			}
			for(Move m : availableMovesRED) {
				System.out.println(m);
			}
			while(!CheckersGame.turnTaken) {synchronized (theGame) {}}
			System.out.println("RED has taken their turn");
			//update the gui
			
			
			//check to see if the game is over
			//update gui, maybe display messages
		}
		System.out.println("RESTART NOW");
	}
}
