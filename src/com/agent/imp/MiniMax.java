package com.agent.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.agent.Evaluator;
import com.agent.GameUtility;
import com.checkersgame.Move;
import com.checkersgame.Piece;
import com.checkersgame.Player;


/**
 * Node of minimax tree
 */
class Node {
	
	public Player p;
	
	public Node parent;//null if at the top of a tree
	//Child nodes represent a move and the state that move results in
	public ArrayList<Node> children;
	int level;//the ply
	Move id;//the move
	Piece[] state;//the state id puts the board state into
	
	public Node(Player p, Node parent,Move m , Piece[] state) {
		this.p=p;
		this.id = m;
		//make sure we duplicate the state, so not to break everything	
		this.state=state.clone();
		//instantiate the ArrayList,maybe create another class for A-B
		children = new ArrayList<Node>();

		if(m==null) {//if the move is null then this is the starting state(the root)
			level=0;
		} else {//otherwise set the parent and increment the level(ply) the node is at
			this.parent=parent;
			level=parent.level+1;
		}
	}

	/**
	 * ToString for debugging
	 */
	@Override
	public String toString() {
		if(parent==null) {
			return "root";
		}else {
			return "[ Player"+p+", Parent = "+parent.id +", \nlevel ="+level+"\nMove = "+id+" ]";
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
	Evaluator eval;
	
	public MiniMax(Piece[] state,int PLY,Player MAX, Player MIN){
		assert MAX!=MIN:"Players should not be the same in minimax";
		this.MAX=MAX;
		this.MIN=MIN;
		//create top node
		Node tree = new Node(null,//no players here,if needed this would be MAX
				null,
				null,
				state);
		//Create the tree of next states
		Construct(tree,PLY,MAX);//should always start with MAX
		ROOT=tree;
	}
	
	public Move MINIMAX_DECISION() {
		int v = Integer.MIN_VALUE;
		Node bestutility=null;
		for(Node n : ROOT.children) {
			int utilityofn = MIN_VALUE(n);
			if(v<utilityofn) {
				v=utilityofn;
				bestutility=n;
			}
		}
		return bestutility.id;
	}
	
	public int MAX_VALUE(Node n) {
		if(TERMINAL_TEST(n)) {
			return eval.Utility(n.state, MAX);
		} else {
			//set as low as possible
			int v = Integer.MIN_VALUE;
			
			//go through the children and see what has the maximizing value
			for(Node c : n.children) {
				int utilityofc = MIN_VALUE(c);
				if(v<utilityofc) {
					v=utilityofc;
				}
			}
			return v;
		}
	}
	
	public int MIN_VALUE(Node n) {
		if(TERMINAL_TEST(n)) {
			return eval.Utility(n.state, MAX);
		} else {
			//set value as high as possible
			int v = Integer.MAX_VALUE;
			
			//go through
			for(Node c : n.children) {
				int utilityofc = MAX_VALUE(c);
				if(v>utilityofc) {
					v = utilityofc;
				}
			}
			return v;
		}
	}
	
	/**
	 * return true if we have devled far enough
	 */
	public boolean TERMINAL_TEST(Node n) {
		return !(n.level<_PLY);
	}
	
//	/**
//	 * 
//	 * @return the move that, depending on the ply, will maximize MAX's utility
//	 *         or minimize MIN's utility
//	 */
//	public Move Decision(int ply) {
//		//steps
//		//determine how we will go
//		//go through the tree and collect nodes at the correct depth
//		
//		ArrayList<Node> nodesAtDepth = new ArrayList<Node>();
//		LinkedList<Node> frontier = new LinkedList<Node>();
//		frontier.add(ROOT);
//		while(!frontier.isEmpty()) {
//			Node lookingat = frontier.pop();
//			if(lookingat.level < ply && !lookingat.children.isEmpty()) {
//				for(Node c : lookingat.children) {
//					//System.out.println("Adding to frontier"+c);
//					frontier.add(c);
//				}
//			} else {//it is at the correct ply
//				nodesAtDepth.add(lookingat);
//			}
//			
//		}
//		for(Node e : nodesAtDepth) {
//			System.out.println("\n"+e);
//		}
//		
//		return null;
//	}
	
	public void Construct(Node root, int ply,Player p) {
		//create a queue
		LinkedList<Node> frontier = new LinkedList<Node>();
		frontier.add(root);//add the root(top node or beginning state)
		
		while(!frontier.isEmpty()) {
			Node n = frontier.pop();//don't add back into queue
			if(n.level>=ply) {
				//do nothing, we are done
				//The node has reached the correct depth(or ply)
			} else {
				//otherwise create the nodes children
				//make sure we switch players based on even or odd levels(ply)
				CreateChildren(n, (n.level%2==0?MAX:MIN));
				for(int i = 0 ; i < n.children.size(); i++) {
					//put the child nodes into the queue to be computed
					frontier.add(n.children.get(i));
				}
			}
		}
	}
	
	/**
	 * create the child nodes from GameUtility
	 */
	public void CreateChildren(Node parent,Player p) {
		//System.out.println("CreateChildren");
		//Remember ACTIONS will give a complete list of moves and states from
		//a state given the player p's turn
		HashMap<Move,Piece[]> map = GameUtility.ACTIONS(p, parent.state);
		for(Move m : map.keySet()) {
			//new node
			Node c = new Node(p,
					parent,
					m,map.get(m));//new node
			c.parent=parent;//set the parent
			parent.children.add(c);//add the parents children into the arraylist
		}
	}
	
	public void SetEvaluator(Evaluator e) {
		eval=e;
	}
	
//BOARD SETUP
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
	
	//Test the minimax construction of the tree
	public static void main(String ... args) {
		int PLY = 2;
		
		MiniMax algo = new MiniMax(board,PLY,Player.BLACK,Player.RED);
		algo.SetEvaluator(new Evaluator_1());
		System.out.println("------------------------------------------");
		//PrintNodes(algo.ROOT,0);//DEBUGGING
		//algo.Decision(PLY);
		Move k = algo.MINIMAX_DECISION();
		System.out.println(k);
		System.out.println("\nDone");
	}
	
	/**
	 * Traverse the tree and print the nodes
	 * print the move
	 * print the Piece[]
	 * breadth first
	 */
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
	
	/**
	 * Prints the Piece[] in a nice format
	 */
	public static void PrintState(Piece[] state) {
		for(int i = 0 ; i < 32 ;i++) {
			System.out.print((i%4==0?"\n":""));
			System.out.printf("%14s", state[i]);
			//System.out.printf("%19s", "Piece."+state[i]+",");//format for copypaste
		}
		System.out.println("\n");
	}
	
}