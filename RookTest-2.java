import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class RookTest {

	private Piece[][] board = new Piece[8][8];

	@Before
	public void setup() {
		board[0][0] = new Rook(0, 0, true);
		board[0][1] = new Rook(0, 1, true);
		board[1][0] = new Pawn(1, 0,true);
		board[0][7] = new Queen(0, 7, false);
		board[5][1] = new King(5, 1, false);
	}

	@Test
	public void checksIllegalDirection() {
		assertEquals(false, board[0][0].legalMove(1, 1, this.board));
		assertEquals(false, board[0][1].legalMove(2, 4, this.board));
		assertEquals(false, board[0][1].legalMove(5, 7, this.board));
	}

	@Test
	public void movesHorizontallyLegally() {
		assertEquals(false, board[0][0].legalMove(3, 0, this.board));
		assertEquals(true, board[0][1].legalMove(4, 1, this.board));
		assertEquals(true, board[0][1].legalMove(2, 1, this.board));
	}

	@Test
	public void movesVerticalyLegally() {
		assertEquals(true, board[0][1].legalMove(0, 7, this.board));
		assertEquals(true, board[0][1].legalMove(0, 4, this.board));
	}

	@Test
	public void checksPieceBlocking() {
		assertEquals(false, board[0][0].legalMove(0, 5, this.board));
		assertEquals(false, board[0][1].legalMove(6, 1, this.board));
	}

	@Test
	public void checksForSameColorCapture() {
		assertEquals(false, board[0][0].legalMove(0, 1, this.board));
		assertEquals(false, board[0][0].legalMove(1, 0, this.board));
	}

	@Test
	public void checksForNoMovement() {
		assertEquals(false, board[0][0].legalMove(0, 0, this.board));
		assertEquals(false, board[0][1].legalMove(0, 1, this.board));

	}

	@Test
	public void checkForDifferentColorCapture() {
		assertEquals(true, board[0][1].legalMove(0, 7, this.board));
		assertEquals(true, board[0][1].legalMove(5, 1, this.board));
	}

}
