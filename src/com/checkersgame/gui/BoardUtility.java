package com.checkersgame.gui;

import com.checkersgame.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.checkersgame.Move;

/**
 * 
 * http://www.bobnewell.net/nucleus/checkers.php?itemid=289
 * checker notation, indexes represent each single move, and jumps
 * available to kings.
 * 
 * To check for non kings determine if the moving is increasing or decreasing
 * based on player color.
 * 
 */
public class BoardUtility {

	
	public static void TEST_ASSERT_MOVEFORPLAYER(boolean expectedValue,Move m , Player p) {
		System.out.println("EXPECTED "+expectedValue+" : GOT "+isValidForwardMove(m,p));
	}
	
	
	public static String TESTMOVEFORPLAYER(Move m , Player p) {
		if(isValidForwardMove(m,p)) {
			return m+" for "+p+" is valid";
		} else {
			return m+" for "+p+" is not valid";
		}
	}
	
	
	public static void main(String ... p) throws Exception {
//		TESTMOVEFORPLAYER(false,new Move (1,32),Player.BLACK);
//		tryagain();
	}

	public static void tryagain( ) throws Exception {
		File f = new File("TEST_VALID_MOVES");
		PrintWriter pw = new PrintWriter(f);
		for(int i = 0 ; i < 32 ; i ++){
			for(int ii = 0 ; ii < 32 ; ii++ ) {
				pw.write(TESTMOVEFORPLAYER(new Move(i,ii) , Player.BLACK)+"\n");
			}
		}
		pw.write("//////////////////////////////////////////////////////\n");
		for(int i = 0 ; i < 32 ; i ++){
			for(int ii = 0 ; ii < 32 ; ii++ ) {
				pw.write(TESTMOVEFORPLAYER(new Move(i,ii) , Player.RED)+"\n");
			}
		}
		pw.flush();
		pw.close();
	}
	
	//used to turn 1-32 to 0-31
	public static Move TranslateMove(Move m) throws Exception {
		return new Move(m.start - 1, m.end - 1);
	}
	
	
	public static boolean isValidForwardMove(Move m, Player p) {
		boolean isInAvailableMoves = false;
		boolean isCorrectDirection = false;
		if( p == Player.BLACK) {//Player.BLACK
			//is it the correct direction
			if(m.end > m.start) {
				isCorrectDirection = true;
			}
			//must be increaseing
			for(int i = 0 ; i < singleMoves[m.start].length ; i++ ) {
				if(m.end == singleMoves[m.start][i] ) {//need to make sure it is actually next to the start
					isInAvailableMoves=true; 
				}
			}
		} else { //Player.RED 
			//is it the correct direction
			if(m.end < m.start) {
				isCorrectDirection = true;
			}
			//must be decreasing
			for(int i = 0 ; i < singleMoves[m.start].length ; i++ ) {
				if(m.end == singleMoves[m.start][i] ) {//need to make sure it is actually next to the start
					isInAvailableMoves=true; 
				}
			}
		}
		//did not return false so far, only thing that matters is that the
		//end is in the set of adjacent spaces
		return isInAvailableMoves && isCorrectDirection;
	}
	//really just spaces visually adjacent
	public static int[][] singleMoves = new int[][]{
		/**0**/  new int[] {4,5},
		/**1**/  new int[] {5,6},
		/**2**/  new int[] {6,7},
		/**3**/  new int[] {7},
		/**4**/  new int[] {0,8},
		/**5**/  new int[] {0,1,8,9},
		/**6**/  new int[] {1,2,9,10},
		/**7**/  new int[] {2,3,10,11},
		/**8**/  new int[] {4,5,12,13},
		/**9**/  new int[] {5,6,13,14},
		/**10**/ new int[] {6,7,14,15},
		/**11**/ new int[] {7,15},
		/**12**/ new int[] {8,16},
		/**13**/ new int[] {8,9,16,17},
		/**14**/ new int[] {9,10,17,18},
		/**15**/ new int[] {10,11,18,19},
		/**16**/ new int[] {12,13,20,21},
		/**17**/ new int[] {13,14,21,22},
		/**18**/ new int[] {14,15,22,23},
		/**19**/ new int[] {15,23},
		/**20**/ new int[] {16,24},
		/**21**/ new int[] {16,17,24,25},
		/**22**/ new int[] {17,18,25,26},
		/**23**/ new int[] {18,19,26,27},
		/**24**/ new int[] {20,21,28,29},
		/**25**/ new int[] {21,22,29,30},
		/**26**/ new int[] {22,23,30,31},
		/**27**/ new int[] {23,31},
		/**28**/ new int[] {24},
		/**29**/ new int[] {24,25},
		/**30**/ new int[] {25,26},
		/**31**/ new int[] {26,27} 
	};
	//spaces that are visually a jump
	public static int[][] singleJumps = new int[][] {
		/**0**/ new int[] {9},
		/**1**/ new int[] {8,10},
		/**2**/ new int[] {9,11},
		/**3**/ new int[] {10},
		/**4**/ new int[] {13},
		/**5**/ new int[] {12,14},
		/**6**/ new int[] {13,15},
		/**7**/ new int[] {14},
		/**8**/ new int[] {1,17},
		/**9**/ new int[] {0,2,16,18},
		/**10**/ new int[] {1,3,17,19},
		/**11**/ new int[] {2,18},
		/**12**/ new int[] {5,21},
		/**13**/ new int[] {4,6,20,22},
		/**14**/ new int[] {5,7,21,23},
		/**15**/ new int[] {6,22},
		/**16**/ new int[] {9,25},
		/**17**/ new int[] {8,10,24,26},
		/**18**/ new int[] {9,11,25,27},
		/**19**/ new int[] {10,26},
		/**20**/ new int[] {13,29},
		/**21**/ new int[] {12,14,28,30},
		/**22**/ new int[] {13,15,29,31},
		/**23**/ new int[] {14,30},
		/**24**/ new int[] {17},
		/**25**/ new int[] {16,18},
		/**26**/ new int[] {17,19},
		/**27**/ new int[] {18},
		/**28**/ new int[] {21},
		/**29**/ new int[] {20,22},
		/**30**/ new int[] {21,23},
		/**31**/ new int[] {22}
	};
//	public static void main (String ...strings ) {
////		for(int i = 0 ; i < 32 ; i++) {
////			System.out.println("/**"+(i+1)+"**/ new int[] {}, ");
////		}
//		for(int i = 0 ; i < 32 ; i++ ) {
//			System.out.print("/**"+i+"**/ new int[] {");
//			for(int l = 0 ; l < WEBPAGE_singleJumps[i].length ; l++ ) {
//				System.out.print((WEBPAGE_singleJumps[i][l]-1));
//				if(l==WEBPAGE_singleJumps[i].length-1) {
//					
//				} else {
//					System.out.print(",");
//				}
//			}
//			System.out.print("},\n");
//		}
	}
	
	
//	static int[][] WEBPAGE_singleJumps = new int[][] {
//		/**1**/  new int[] {10}, 
//		/**2**/  new int[] {9,11}, 
//		/**3**/  new int[] {10,12}, 
//		/**4**/  new int[] {11}, 
//		/**5**/  new int[] {14}, 
//		/**6**/  new int[] {13,15}, 
//		/**7**/  new int[] {14,16}, 
//		/**8**/  new int[] {15}, 
//		/**9**/  new int[] {2,18}, 
//		/**10**/ new int[] {1,3,17,19}, 
//		/**11**/ new int[] {2,4,18,20}, 
//		/**12**/ new int[] {3,19}, 
//		/**13**/ new int[] {6,22}, 
//		/**14**/ new int[] {5,7,21,23}, 
//		/**15**/ new int[] {6,8,22,24}, 
//		/**16**/ new int[] {7,23}, 
//		////////////////////////////
//		/**17**/ new int[] {10,26}, 
//		/**18**/ new int[] {9,11,25,27}, 
//		/**19**/ new int[] {10,12,26,28}, 
//		/**20**/ new int[] {11,27}, 
//		/**21**/ new int[] {14,30}, 
//		/**22**/ new int[] {13,15,29,31}, 
//		/**23**/ new int[] {14,16,30,32}, 
//		/**24**/ new int[] {15,31}, 
//		/**25**/ new int[] {18}, 
//		/**26**/ new int[] {17,19}, 
//		/**27**/ new int[] {18,20}, 
//		/**28**/ new int[] {19}, 
//		/**29**/ new int[] {22}, 
//		/**30**/ new int[] {21,23}, 
//		/**31**/ new int[] {22,24}, 
//		/**32**/ new int[] {23}
//	};
//}
