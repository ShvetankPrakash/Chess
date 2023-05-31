import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BishopTest {

	private Piece[][] board = new Piece[8][8];

	@Before
	public void setup() {
		board[2][0] = new Bishop(2, 0, true);
		board[2][7] = new Bishop(2, 7, false);
		board[3][6] = new Pawn (3, 6, false);
		board[1][6] = new Pawn (1, 6, true);
		board[0][1] = new Rook(0, 1, true);
		board[0][0] = new Queen(0, 0, true);
		board[0][7] = new Queen(0, 7, false);
		board[7][2] = new Pawn(7, 2, false);
		board[1][1] = new Pawn(1, 1, true);
		board[3][1] = new Pawn(3, 1, false);
	}

	@Test
	public void checksIllegalDirection() {
		assertEquals(false, board[2][0].legalMove(1, 3, this.board));
		assertEquals(false, board[2][0].legalMove(7, 3, this.board));
	}

	@Test

	public void movesDiagonallyLegally() {
		assertEquals(true, board[2][7].legalMove(1, 6, this.board));
		assertEquals(false, board[2][7].legalMove(0, 5, this.board));
		assertEquals(false, board[2][7].legalMove(4, 5, this.board));
		assertEquals(false, board[2][0].legalMove(1, 1, this.board));
	}

	@Test
	public void checksPieceBlocking() {
		assertEquals(false, board[2][0].legalMove(5, 3, this.board));
		assertEquals(false, board[2][0].legalMove(0, 2, this.board));
	}

	@Test
	public void checksForSameColorCapture() {
		assertEquals(false, board[2][7].legalMove(7, 2, this.board));
		assertEquals(false, board[2][7].legalMove(3, 6, this.board));
	}

	@Test

	public void checkForDifferentColorCapture() {
		assertEquals(true, board[2][0].legalMove(3, 1, this.board));
		assertEquals(true, board[2][7].legalMove(1, 6, this.board));
	}

	@Test
	public void checksForNoMovement() {
		assertEquals(false, board[2][0].legalMove(2, 0, this.board));
		assertEquals(false, board[2][7].legalMove(2, 7, this.board));
	}

}
