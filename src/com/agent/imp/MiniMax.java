package com.agent.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import com.agent.GameUtility;
import com.checkersgame.Move;
import com.checkersgame.Piece;
import com.checkersgame.Player;

class Node {

	public Node parent;//null if at the top of a tree
	public ArrayList<Node> children;//the 
	int level;
	Move id;//the move
	Piece[] state;//the state that move puts you in
	
	public Node(Node parent,Move m , Piece[] state) {
		this.id = m;
		this.state=state.clone();//make sure we duplicate the state	
		children = new ArrayList<Node>();

		if(m==null) {
			level=0;
		} else {
			this.parent=parent;
			level=parent.level+1;
		}
	}

	@Override
	public String toString() {
		if(parent==null) {
			return "root";
		}else {
			return "[ Parent = "+parent.id +", \nlevel ="+level+"\nMove = "+id+" ]";
		}
	}	
	
}

/**
 * Giving up on recursion
 * build the tree and go from there
 *
 */
public class MiniMax {
	
	Player MAX;
	Player MIN;
	int _PLY;
	int depth;
	ArrayList<Node> test;
	Node ROOT;
	public MiniMax(Piece[] state,int PLY,Player MAX, Player MIN){

		assert MAX!=MIN:"Players should not be the same in minimax";
		this.MAX=MAX;
		this.MIN=MIN;
		//_PLY=0;
		//this.depth=depth;
		//_PLY=depth*2;
		//create the parent
		Node tree = new Node(null,null,state);
		//got to my next nodes (MAX PLAYER duh)
		//THEN GOTRHOUGH MIN PLAYERS
		//ONE TURN DONE
		Construct(tree,PLY,MAX);//should always start with MAX
		ROOT=tree;
	}
	
	public void Construct(Node root, int ply,Player p){
		//int level = 0;	//init our level to zero

		LinkedList<Node> frontier = new LinkedList<Node>();
		frontier.add(root);
		
		while(!frontier.isEmpty()) {
			Node n = frontier.pop();//dont add back into queue
			//System.out.println("MINIMAX--> "+n.level);
			if(n.level>=ply) {
				//do nothing we are done
			} else {
				CreateChildren(n, (n.level%2==0?MAX:MIN));
				for(int i = 0 ; i < n.children.size(); i++) {
					frontier.add(n.children.get(i));
				}
			}
		}
	}
	
	public void CreateChildren(Node parent,Player p) {
		//System.out.println("CreateChildren");
		HashMap<Move,Piece[]> map = GameUtility.ACTIONS(p, parent.state);
		for(Move m : map.keySet()) {
			//System.out.println("--"+m+"--");
			//PrintState(map.get(m));
			Node c = new Node(parent,m,map.get(m));
			c.parent=parent;
			parent.children.add(c);
		}
	}
	
//SETUP
	static Piece[] board = new Piece[] {
			   Piece.REG_BLACK,   Piece.REG_BLACK,   Piece.REG_BLACK,   Piece.REG_BLACK,
			   Piece.REG_BLACK,       Piece.EMPTY,   Piece.REG_BLACK,   Piece.REG_BLACK,
			   Piece.REG_BLACK,     Piece.REG_RED,   Piece.REG_BLACK,   Piece.REG_BLACK,
			     Piece.REG_RED,     Piece.REG_RED,       Piece.EMPTY,     Piece.REG_RED,
			       Piece.EMPTY,     Piece.REG_RED,     Piece.REG_RED,       Piece.EMPTY,
			       Piece.EMPTY,       Piece.EMPTY,       Piece.EMPTY,       Piece.EMPTY,
			     Piece.REG_RED,     Piece.REG_RED,     Piece.REG_RED,     Piece.REG_RED,
			       Piece.EMPTY,     Piece.REG_RED,  Piece.KING_BLACK,     Piece.REG_RED
	};
	
	public static void main(String ... args) {
		MiniMax algo = new MiniMax(board,2,Player.BLACK,Player.RED);
		System.out.println("------------------------------------------");
		PrintNodes(algo.ROOT,0);//DEBUGGING
		
		System.out.println("\nDone");
	}
	
	public static void PrintNodes(Node root,int at) {
		
		LinkedList<Node> toPrint =new LinkedList<Node>();
		toPrint.add(root);
		while (!toPrint.isEmpty()) { 	
			Node current = toPrint.pop();//get a node
			
			System.out.println(current);//print the move
			PrintState(current.state);//print state	
			
			if( !current.children.isEmpty() ) { //if there are children
				for(Node child : current.children) {
					toPrint.add(child);
				}
			}
		}
		
	}
	
	public static void PrintState(Piece[] state) {
		for(int i = 0 ; i < 32 ;i++) {
			System.out.print((i%4==0?"\n":""));
			System.out.printf("%14s", state[i]);
			//System.out.printf("%19s", "Piece."+state[i]+",");//format so i can see
		}
		System.out.println("\n");
	}
	
}