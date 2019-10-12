package com.checkersgame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.checkersgame.Player;
import com.checkersgame.CheckersGame;
import com.checkersgame.Move;
import com.checkersgame.Piece;

public class PlayableSpace extends JPanel implements MouseListener {

	
	
	/**
	 *	Keep eclipse happy...
	 */
	private static final long serialVersionUID = -6042600935132354683L;
	
	public final int id;
	CheckerBoard cb;
	final Color orginalColor = Color.GRAY;
	static int piecesize=35;
	
	
	public PlayableSpace(int id,CheckerBoard cb) {
		this.id = id;
		this.cb = cb;
		this.addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString(" "+(id+1), 0, 10);//10 so we can see the number	
		if(cb.game.boardspot[id]==Piece.EMPTY) {
			//do nothing
		} else if(cb.game.boardspot[id]==Piece.REG_BLACK || 
				  cb.game.boardspot[id]==Piece.REG_RED){//show the piece
			g.setColor(cb.game.boardspot[id].c);
			g.fillOval(20, 20, piecesize, piecesize);
		} else {
			if(cb.game.boardspot[id] == Piece.KING_BLACK) {
				g.setColor(cb.game.boardspot[id].c);
				g.fillOval(20, 20, piecesize, piecesize);
				g.setColor(Color.WHITE);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
				g.drawString("k", 30,47);
			} else {//it is a red king
				g.setColor(cb.game.boardspot[id].c);
				g.fillOval(20, 20, piecesize, piecesize);
				g.setColor(Color.WHITE);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
				g.drawString("k", 30,47);
			}
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		boolean pieceKingedThisTurn=false;
		Move[] moves;
		if(cb.game.playersTurn == Player.BLACK) {
			moves = cb.game.AvailableMoves(Player.BLACK);
		} else {//red
			moves = cb.game.AvailableMoves(Player.RED);
		}
//		Move[] black = cb.game.AvailableMoves(Player.BLACK);
//		Move[] red = cb.game.AvailableMoves(Player.RED);
		System.out.println("==> "+id+" PlayersTurn : "+cb.game.playersTurn);
		if(cb.holdpiece && cb.heldpieceoriginalspace.id==id ) {
			//reset holding a piece
			this.setBackground(orginalColor);
			cb.ResetHolding();
		} else if(!cb.holdpiece && (cb.game.boardspot[id] == Piece.EMPTY) ) {
			return;//do nothing
		} else if(cb.holdpiece) {//try to place the piece
			
			
			if( Player.BLACK == cb.game.playersTurn &&
				CheckersGame.pieceExistsInAvailableMovesEnd(cb.heldpieceoriginalspace.id, id, Player.BLACK, moves)
				//cb.game.pieceExistsInAvailableMovesEnd(cb.heldpieceoriginalspace.id,id,Player.BLACK)	
				) {
				//remove piece
				int removedpiece = cb.game.getJumpedPiece(cb.heldpieceoriginalspace.id, id);
				cb.PlacePiece(id);//place the piece
				//check if it is a king now
				if(cb.game.boardspot[id] != Piece.KING_BLACK && (id == 31 || id == 30 || id == 29 || id == 28 )) {
					cb.game.boardspot[id] = Piece.KING_BLACK;
					pieceKingedThisTurn=true;//need to end turn taking
				}
				//else if is already a king
				if(removedpiece>=0) {
					
					cb.game.Remove(removedpiece);
					//check for more jumps
					cb.game.inJump = true;
					cb.game.lastJumpPiece=id;
					moves = cb.game.AvailableMoves(Player.BLACK);
					for(Move m : moves) {
						System.out.println("new moves "+m);
					}
				} else {
					moves=null;
				}

				//check for more jumps

			} else if( Player.RED == cb.game.playersTurn &&
					   CheckersGame.pieceExistsInAvailableMovesEnd(cb.heldpieceoriginalspace.id, id, Player.RED, moves)
					   //cb.game.pieceExistsInAvailableMovesEnd(cb.heldpieceoriginalspace.id,id,Player.RED)	
					   ){//RED
				//remove piece
				int removedpiece = cb.game.getJumpedPiece(cb.heldpieceoriginalspace.id, id);
				cb.PlacePiece(id);
				//check if it kinged
				if(cb.game.boardspot[id] != Piece.KING_RED && (id == 0 || id == 1 || id == 2 || id == 3) ) {
					cb.game.boardspot[id] = Piece.KING_RED;
					pieceKingedThisTurn=true;
				}
				if(removedpiece>=0) {
					cb.game.Remove(removedpiece);
					//check for more jumps
					cb.game.inJump = true;
					cb.game.lastJumpPiece=id;
					moves = cb.game.AvailableMoves(Player.RED);
					for(Move m : moves) {
						System.out.println("new moves "+m);
					}
				} else {
					moves=null;
				}
				
			} else {
				return;
			}
			
			//cb.PlacePiece(id);
			if(moves==null) {
//				System.out.println("MOVES NULL");
				CheckersGame.turnTaken=true;
				cb.game.inJump = false;//reset stuff
				cb.game.lastJumpPiece=-1;//reset stuff
			} else {
				boolean jumpMovesLeft=false;
				for(Move m : moves) {
					if(m.isJump) {
						jumpMovesLeft=true;
					}
				}
				if(!jumpMovesLeft) {
//					System.out.println("!jumpMovesLeft");
					CheckersGame.turnTaken=true;
					cb.game.inJump = false;//reset stuff
					cb.game.lastJumpPiece=-1;//reset stuff
				}
			}
			if(pieceKingedThisTurn) {
				CheckersGame.turnTaken=true;
				cb.game.inJump = false;//reset stuff
				cb.game.lastJumpPiece=-1;//reset stuff
			}
		} else {//try to pick up the piece
			if( (
					( cb.game.boardspot[id] == Piece.KING_BLACK || 
					  cb.game.boardspot[id] == Piece.REG_BLACK   ) &&
					  cb.game.playersTurn == Player.BLACK &&
					  CheckersGame.pieceExistsInAvailableMovesStart(id,Player.BLACK,moves)
				) 
					||
				(
					( cb.game.boardspot[id] == Piece.KING_RED || 
					  cb.game.boardspot[id] == Piece.REG_RED   ) &&
					  cb.game.playersTurn == Player.RED &&
					  CheckersGame.pieceExistsInAvailableMovesStart(id,Player.RED,moves)
				)	
			) {
				this.setBackground(Color.GREEN);	//show the piece trying to be picked up
				cb.PickUpPiece(id);
			} else {
				//wrong players piece, do nothing
			}
		}
	}

	
	//dont really care about the rest of the mouse actions
	
	
	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println(e.toString());		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println(e.toString());	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println(e.toString());	
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.println(e.toString());
	}
}
