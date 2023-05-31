import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class QueenTest {

	private Piece[][] board = new Piece[8][8];

	@Before
	public void setup() {
		board[0][0] = new Queen(0, 0, true);
		board[3][2] = new Queen(3, 2, true);
		board[3][3] = new Rook(3, 3, true);
		board[2][2] = new Rook(2, 2, true);
		board[0][7] = new Queen(0, 7, false);
		board[1][0] = new Pawn(1, 0, false);
	}

	@Test
	public void checksIllegalDirection() {
		assertEquals(false, board[0][0].legalMove(1, 3, this.board));
		board[5][6] = new Queen(5, 6, true);
		assertEquals(false, board[5][6].legalMove(3, 7, this.board));
	}

	@Test
	public void movesDiagonallyLegally() {
		assertEquals(true, board[3][2].legalMove(5, 4, this.board));
		assertEquals(true, board[3][2].legalMove(2, 1, this.board));
		assertEquals(true, board[0][0].legalMove(1, 1, this.board));
	}

	@Test
	public void movesHorizontallyLegally() {
		assertEquals(false, board[0][0].legalMove(3, 0, this.board));
		assertEquals(true, board[3][2].legalMove(7, 2, this.board));
		assertEquals(true, board[3][2].legalMove(4, 2, this.board));
	}

	@Test
	public void movesVerticalyLegally() {
		assertEquals(true, board[0][0].legalMove(0, 5, this.board));
		assertEquals(true, board[3][2].legalMove(3, 1, this.board));
		assertEquals(true, board[0][0].legalMove(0, 3, this.board));
	}

	@Test
	public void checksPieceBlocking() {
		assertEquals(false, board[3][2].legalMove(1, 2, this.board));
		assertEquals(false, board[3][2].legalMove(3, 6, this.board));
	}

	@Test
	public void checksForSameColorCapture() {
		assertEquals(false, board[3][2].legalMove(2, 2, this.board));
		assertEquals(false, board[3][2].legalMove(3, 3, this.board));
	}

	@Test
	public void checkForDifferentColorCapture() {
		assertEquals(true, board[0][0].legalMove(0, 7, this.board));
		assertEquals(true, board[0][0].legalMove(1, 0, this.board));
	}

	@Test
	public void checksForNoMovement() {
		assertEquals(false, board[0][0].legalMove(0, 0, this.board));
		assertEquals(false, board[3][2].legalMove(3, 2, this.board));
		assertEquals(false, board[2][2].legalMove(2, 2, this.board));
	}

}
