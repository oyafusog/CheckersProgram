package com.checkersgame.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.checkersgame.Piece;

public class PlayableSpace extends JPanel implements MouseListener {

	
	
	/**
	 *	Keep eclipse happy...
	 */
	private static final long serialVersionUID = -6042600935132354683L;
	
	public final int id;
	CheckerBoard cb;
	
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
		} else {//show the piece
			g.setColor(cb.game.boardspot[id].c);
			g.fillOval(20, 20, piecesize, piecesize);
		}		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("==> "+id);
		if(!cb.holdpiece && (cb.game.boardspot[id] == Piece.EMPTY) ) {
			return;//do nothing
		} else if(cb.holdpiece) {//try to place the piece
			cb.PlacePiece(id);
		} else {//try to pick up the piece
			this.setBackground(Color.GREEN);	//show the piece trying to be picked up
			cb.PickUpPiece(id);
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
