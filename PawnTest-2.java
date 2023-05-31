import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PawnTest {

	private Piece[][] board = new Piece[8][8];

	@Before
	public void setup() {
		board[2][0] = new Bishop(2, 0, true);
		board[2][7] = new Bishop(2, 7, false);
		board[0][1] = new Rook(0, 1, true);
		board[2][6] = new Pawn(2, 6, false);
		board[2][1] = new Pawn(2, 1, true);
		board[4][6] = new Pawn(4, 6, false);
		board[5][1] = new Pawn(5, 1, true);
	}

	@Test
	public void notMovedAtAll() {
		assertEquals(false, board[2][1].legalMove(2, 1, this.board));
		assertEquals(false, board[2][6].legalMove(2, 6, this.board));
	}

	@Test
	public void movingBackwards() {
		assertEquals(false, board[2][1].legalMove(2, 0, this.board));
		assertEquals(false, board[2][6].legalMove(2, 7, this.board));
	}

	@Test
	public void movesTwiceOnlyOnFirstTurn() {
		assertEquals(true, board[2][1].legalMove(2, 3, this.board));
		board[2][3] = board[2][1]; // moves pawn
		board[2][3].setHasMovedOnce();
		board[2][3].setY(3);
		board[2][1] = null;
		assertEquals(false, board[2][3].legalMove(2, 5, this.board));
		assertEquals(true, board[2][3].legalMove(2, 4, this.board));
		assertEquals(true, board[4][6].legalMove(4, 4, this.board));
		board[4][4] = board[4][6]; // moves pawn
		board[4][4].setHasMovedOnce();
		board[4][4].setY(4);
		board[4][6] = null;
		assertEquals(false, board[4][4].legalMove(4, 2, this.board));
		assertEquals(true, board[4][4].legalMove(4, 3, this.board));
	}

	@Test
	public void capturesCorrectly() {
		board[1][5] = new Rook(1, 5, true);
		assertEquals(true, board[2][6].legalMove(1, 5, this.board));
		assertEquals(false, board[2][6].legalMove(3, 7, this.board));
		board[3][7] = new Bishop(3, 7, false);
		assertEquals(false, board[2][6].legalMove(3, 7, this.board));
		board[1][2] = new Rook(1, 5, false);
		assertEquals(true, board[2][1].legalMove(1, 2, this.board));
		assertEquals(false, board[2][1].legalMove(3, 2, this.board));
		board[3][2] = new Bishop(3, 7, true);
		assertEquals(false, board[2][1].legalMove(3, 2, this.board));
	}

	@Test
	public void pieceBlocking() {
		board[4][5] = new Bishop(4, 5, true);
		assertEquals(false, board[4][6].legalMove(4, 5, this.board));
		board[2][2] = new Rook(2, 2, false);
		assertEquals(false, board[2][1].legalMove(2, 2, this.board));
	}

	@Test
	public void movesInStraightLineOnly() { // assuming its not capturing
		assertEquals(true, board[4][6].legalMove(4, 5, this.board));
		assertEquals(false, board[4][6].legalMove(3, 6, this.board));
		assertEquals(false, board[4][6].legalMove(5, 6, this.board));
		assertEquals(true, board[4][6].legalMove(4, 4, this.board));
		board[4][6].setHasMovedOnce();
		assertEquals(false, board[4][6].legalMove(4, 4, this.board));
		assertEquals(false, board[4][6].legalMove(3, 4, this.board));
		assertEquals(false, board[4][6].legalMove(5, 5, this.board));
		assertEquals(false, board[4][6].legalMove(0, 0, this.board));
	}

}
