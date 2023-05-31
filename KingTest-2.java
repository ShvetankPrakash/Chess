import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class KingTest {

	private Piece[][] board= new Piece [8][8];
	
	@Before 
	
	public void setup() {
	    board[3][7] = new Queen (3,7,false);
	    board[4][0] = new King (4,0,true);
	    board[4][7] = new King (4,7,false);
	}
	
	@Test
	public void capturesCorrectly() {
		board[4][1] = new Pawn (4, 1, true);
		assertEquals(false, board[4][0].legalMove(4, 1, board));
		board[5][0] = new Bishop (5, 0, false);
		assertEquals(true, board[4][0].legalMove(5, 0, board));
	}
	
	@Test 
	public void castlesCorrectly() {
		assertEquals(true, board[4][0].legalMove(6, 0, board));
		assertEquals(false, board[4][0].legalMove(2, 0, board));
		board [1][7] = board [3][7]; //moves queen
		board [3][7] = null;
		board[1][7].setX(1);
		assertEquals(true, board[4][0].legalMove(2, 0, board));
		assertEquals(true, board[4][0].legalMove(5, 0, board));
		assertEquals(false, board[4][0].legalMove(6, 1, board));
		board[4][0].setHasMovedOnce();
		assertEquals(false, board[4][0].legalMove(6, 0, board));
		assertEquals(false, board[4][0].legalMove(2, 0, board));
	}
	
	@Test 
	public void movesLegally() {
		assertEquals(false, board[4][0].legalMove(3, 0, board));
		assertEquals(false, board[4][0].legalMove(3, 1, board));
		assertEquals(true, board[4][0].legalMove(4, 1, board));
		assertEquals(true, board[4][0].legalMove(5, 1, board));
		assertEquals(true, board[4][0].legalMove(5, 0, board));
		assertEquals(false, board[4][0].legalMove(2, 2, board));
		assertEquals(false, board[4][0].legalMove(4, 0,  board));
		assertEquals(false, board[4][0].legalMove(3, 3, board));
		board [3][1] = new Pawn (3, 1, true);
		assertEquals(false, board[4][0].legalMove(3, 1, board));
		assertEquals(true, board[4][0].legalMove(3, 0, board));
		board [4][1] = new Pawn (3, 1, false);
		assertEquals(true, board[4][0].legalMove(4, 1, board));

	}
	
	@Test 
	public void movingIntoCheck() {
		board[7][5] = new Bishop (7, 5, true);
		assertEquals(false, board[4][7].legalMove(5, 7, board));
		board[3][5] = new Bishop (3, 5, false);
		assertEquals(true, board[4][7].legalMove(4, 6, board));
	}
	
	@Test
	public void movingNextToOpposingKing() {
		board [6][0] = new King(6, 0, false);
		assertEquals(false, board[4][0].legalMove(5, 0, board));
	}
	
	

}
