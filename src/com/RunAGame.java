package com;
import java.util.Scanner;

import com.agent.Agent;
import com.agent.imp.OtherBot;
import com.agent.imp.TestAgent;
import com.checkersgame.*;
import com.checkersgame.gui.CheckerBoard;

/**
 * Main class
 * 
 */
public class RunAGame {
	public static void main(String ... args) throws Exception {
		
		//main game logic
		CheckersGame theGame = new CheckersGame();
		//the gui, and some logic...
		CheckerBoard theBoard= new CheckerBoard(theGame);
		
		Agent player1=null;
		Agent player2=null;
		
		//assign players, and agents to play against
		Scanner in = new Scanner(System.in);
		System.out.println("Game Starting 0 if you are RED, 1 if BLACK");
		int _player = Integer.parseInt(in.next());//0 red, 1 black
		
		//setup the game with two players
		if(_player > 0) {//the player is the black player
			player1 = new OtherBot();//BLACK
			player2 = new TestAgent(); //RED
			player2.Init(Player.RED, theGame,theBoard);
		} else {//other wise red
			player1 = new TestAgent(); //BLACK
			player1.Init(Player.BLACK,theGame,theBoard);
			player2 = new OtherBot(); //RED
		}
		
		boolean gameover = false;
		while(!gameover ) {
			
			////////BLACK TURN
			System.out.println("TURN BLACK ");theGame.playersTurn  = Player.BLACK ;//black takes turn, black always goes first
			//take turn
			CheckersGame.turnTaken=false;
			Move[] availableMovesBLACK = theGame.AvailableMoves(Player.BLACK);
			if(availableMovesBLACK.length==0) {//if no moves available the player has lost
				theGame.GameOver(Player.RED, " No more moves for player : BLACK");
				gameover=true;
				continue;//leave the loop
			}
			//hints
			for(Move m : availableMovesBLACK) {
				System.out.println(m);
			}
			while(!CheckersGame.turnTaken){
				synchronized (theGame) {
					player1.GetMove();
				}
			}
			player1.ClearCollectMoves();
			//print what player1 did
			
			
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
				continue;//leave the loop
			}
			//hints
			for(Move m : availableMovesRED) {
				System.out.println(m);
			}
			while(!CheckersGame.turnTaken) {
				synchronized (theGame) {
					player2.GetMove();
				}
			}
			player2.ClearCollectMoves();
			//print what player2 did
			
			
			System.out.println("RED has taken their turn");
		}
		System.out.println("RESTART NOW");
	}
}
