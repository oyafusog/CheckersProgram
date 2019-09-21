package com.checkersgame.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.checkersgame.CheckersGame;
import com.checkersgame.Player;
import com.checkersgame.Piece;

/**
 * The JFrame that represents the checkerboard
 * see the Board class for valid moves and piece removal/king
 */
public class CheckerBoard extends JFrame {
	private static final long serialVersionUID = -6737384592246616039L;
	
	public CheckersGame game;
	public PlayableSpace[] playableSpaces;
	private PlayableSpace nullspace = new PlayableSpace(-1,this);
	//stuff to move pieces manually
	public boolean holdpiece;
	public Player inquestion;
	@SuppressWarnings("unused")
	private int start,end;
	@SuppressWarnings("unused")
	private final Piece nonplayable=Piece.EMPTY;
	
	public PlayableSpace heldpieceoriginalspace;
	
	public CheckerBoard(CheckersGame b) {
		super();
		game =b;
		//init variables
		start =-1;
		end=-1;
		holdpiece = false;
		
		
		playableSpaces = new PlayableSpace[32];		//the array of panels representing
													//the playable spaces
		setSize(600, 600);
		
		int row = 8; 
		int col = 8; 
		this.setLayout(new GridLayout(row, col));
		
		
		DisplayBoard();								//setup the panels to be a checker board
		
		setResizable(false);						//JFrame stuff
		setDefaultCloseOperation(EXIT_ON_CLOSE);	//JFrame stuff
		setVisible(true);							//JFrame stuff
	}
	
	/*
	 * Debugging only
	 */
	public static List<Component> getAllComponents(final Container c) {
	    Component[] comps = c.getComponents();
	    List<Component> compList = new ArrayList<Component>();
	    for (Component comp : comps) {
	        compList.add(comp);
	        if (comp instanceof Container)
	            compList.addAll(getAllComponents((Container) comp));
	    }
	    return compList;
	}
	
	@SuppressWarnings("unused")
	public void PickUpPiece(int spaceid) {
		if(false) {//can this player pick up the piece
			return;//TODO
		} else {//if so go ahead
			holdpiece=true;
			heldpieceoriginalspace=playableSpaces[spaceid];
		}
	}
	
	@SuppressWarnings("unused")
	public void PlacePiece(int spaceid) {
		if(false) {//is this a valid move by the player
			return;//TODO
		} else {//else go ahead
			holdpiece=false;
			heldpieceoriginalspace.setBackground(Color.GRAY);
			game.Move(heldpieceoriginalspace.id, spaceid);
			heldpieceoriginalspace=nullspace;
		}
		this.repaint();
	}
	
	public boolean IsPlayersPiece(Player p, int i) {
		if(p==Player.RED &&
				(game.boardspot[i] == Piece.REG_RED || 
						game.boardspot[i] == Piece.KING_RED )
				) {
			return true;
		} 
		if (p==Player.BLACK &&
				(game.boardspot[i] == Piece.REG_RED || 
						game.boardspot[i] == Piece.KING_RED )
				){
			return true;
		} 
		System.err.println(p+" cannot grab a "+game.boardspot[i]+" piece.");
		return false;
	}
	
	private void DisplayBoard() {
		for(int i = 0 ; i < 32 ; i++) {
			PlayableSpace tmp = new PlayableSpace(i,this);
			tmp.setSize(75,75);
			tmp.setBackground(Color.GRAY);
			JPanel nonplayable = new JPanel();
			nonplayable.setSize(75,75);
			nonplayable.setBackground(Color.WHITE);
			if((i/4)%2==1) {
				this.add(tmp);
				this.add(nonplayable);
			} else {
				this.add(nonplayable);
				this.add(tmp);
			}
			playableSpaces[i] = tmp;
		}
	}
	
	//test the board
	public static void main(String[] args) {
		System.out.println("Starting");
		@SuppressWarnings("unused")
		CheckerBoard window = new CheckerBoard(new CheckersGame());//just for now
		System.out.println("End");
	}
}
