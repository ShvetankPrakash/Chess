import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ChessModelTest {

	private ChessModel chess;

	@Before
	public void setUp() {
		chess = new ChessModel();
		chess.setUpBoard();
	}

	@Test
	public void GetOutOfCheck1() {
		// this test manually moves pieces because the movePiece method had not been
		// implemented yet
		Piece[][] board = chess.getBoard();
		board[4][3] = board[4][1];
		board[4][1] = null;
		board[4][3].setY(3);
		board[4][4] = board[4][6];
		board[4][6] = null;
		board[4][4].setY(4);
		board[2][3] = board[5][0];
		board[5][0] = null;
		board[2][3].setY(3);
		board[2][3].setX(2);
		board[2][4] = board[5][7];
		board[5][7] = null;
		board[2][4].setY(4);
		board[2][4].setX(2);
		board[7][4] = board[3][0];
		board[3][0] = null;
		board[7][4].setY(4);
		board[7][4].setX(7);
		board[7][5] = board[6][7];
		board[6][7] = null;
		board[7][5].setY(5);
		board[7][5].setX(7);
		board[5][6] = board[7][4];
		board[7][4] = null;
		board[5][6].setY(6);
		board[5][6].setX(5);
		chess.switchCurrentPlayer();
		assertEquals(true, chess.canGetOutOfCheck(chess.getCurrentPlayer()));

	}

	@Test
	public void GetOutOfCheck2() {
		// this test manually moves pieces because the movePiece method had not been
		// implemented yet
		Piece[][] board = chess.getBoard();
		board[4][3] = board[4][1];
		board[4][1] = null;
		board[4][3].setY(3);
		board[4][4] = board[4][6];
		board[4][6] = null;
		board[4][4].setY(4);
		board[2][3] = board[5][0];
		board[5][0] = null;
		board[2][3].setY(3);
		board[2][3].setX(2);
		board[2][4] = board[5][7];
		board[5][7] = null;
		board[2][4].setY(4);
		board[2][4].setX(2);
		board[7][4] = board[3][0];
		board[3][0] = null;
		board[7][4].setY(4);
		board[7][4].setX(7);
		board[4][6] = board[3][7];
		board[3][7] = null;
		board[4][6].setY(6);
		board[4][6].setX(4);
		board[5][6] = board[7][4];
		board[7][4] = null;
		board[5][6].setY(6);
		board[5][6].setX(5);
		chess.switchCurrentPlayer();
		assertEquals(true, chess.canGetOutOfCheck(chess.getCurrentPlayer()));
	}

	@Test
	public void FourMoveCheckmate() {
		chess.movePiece(4, 1, 4, 3);
		chess.movePiece(4, 6, 4, 4);
		chess.movePiece(5, 0, 2, 3);
		chess.movePiece(5, 7, 2, 4);
		chess.movePiece(3, 0, 7, 4);
		assertEquals(true, chess.movePiece(3, 7, 7, 3));
		chess.movePiece(7, 4, 5, 6);
		assertEquals(true, chess.canGetOutOfCheck(chess.getCurrentPlayer()));
		assertEquals(true, chess.movePiece(4, 7, 3, 7));
		;
		chess.switchCurrentPlayer();
		assertEquals(false, chess.isInCheck(chess.getCurrentPlayer()));
	}

	@Test
	public void moreComplicatedCheckmate() {
		chess.movePiece(4, 1, 4, 2);
		chess.movePiece(0, 6, 0, 5);
		chess.movePiece(6, 0, 5, 2);
		chess.movePiece(4, 6, 4, 5);
		chess.movePiece(5, 2, 6, 4);
		chess.movePiece(6, 7, 4, 6);
		chess.movePiece(6, 4, 4, 3);
		chess.movePiece(6, 6, 6, 5);
		chess.movePiece(4, 3, 5, 5);
		assertEquals(true, chess.checkmate());
	}

	@Test
	public void moveLocationOfPiecesLegally() {
		assertEquals(false, chess.movePiece(0, 0, 1, 1));
		chess.movePiece(1, 0, 2, 2);
		Piece[][] board = chess.getBoard();
		assertEquals(2, board[2][2].getX());
		assertEquals(2, board[2][2].getY());
		chess.movePiece(2, 2, 3, 3);
		assertEquals(2, board[2][2].getX());
		assertEquals(2, board[2][2].getY());
	}

	@Test
	public void movesPieceToNotBeChecked() {
		chess.movePiece(4, 1, 4, 3);
		assertEquals(false, chess.movePiece(4, 3, 4, 5));
		chess.movePiece(4, 6, 4, 4);
		chess.movePiece(5, 0, 2, 3);
		chess.movePiece(5, 7, 2, 4);
		chess.movePiece(3, 0, 7, 4);
		chess.movePiece(6, 7, 7, 5);
		chess.movePiece(7, 4, 5, 6);
		assertEquals(false, chess.movePiece(3, 6, 3, 4));
		assertEquals(true, chess.movePiece(7, 5, 5, 6));
		assertEquals(false, chess.checkmate());
	}

	@Test
	public void movesPiecesToCastle() {
		chess.movePiece(3, 1, 3, 2);
		chess.movePiece(6, 7, 5, 5);
		assertEquals(false, chess.movePiece(4, 7, 6, 7));
		chess.movePiece(0, 1, 0, 3);
		chess.movePiece(6, 6, 6, 5);
		chess.movePiece(1, 1, 1, 3);
		chess.movePiece(5, 7, 6, 6);
		chess.movePiece(2, 1, 2, 3);
		chess.movePiece(4, 7, 6, 7);
		Piece[][] board = chess.getBoard();
		Piece temp = board[7][7];
		assertEquals(null, temp);
		assertEquals('R', board[5][7].getType());
		assertEquals(5, board[5][7].getX());
		assertEquals(7, board[5][7].getY());
	}

	@Test
	public void falseFourMoveCheckmate() {
		chess.movePiece(4, 1, 4, 3);
		chess.movePiece(4, 6, 4, 4);
		chess.movePiece(5, 0, 2, 3);
		chess.movePiece(5, 7, 2, 4);
		chess.movePiece(3, 0, 7, 4);
		chess.movePiece(3, 7, 7, 3);
		chess.movePiece(7, 4, 5, 6);
		chess.movePiece(4, 7, 3, 7);
		assertEquals(false, chess.isInCheck(chess.getCurrentPlayer()));
		chess.switchCurrentPlayer();
		assertEquals(false, chess.isInCheck(chess.getCurrentPlayer()));
	}

	@Test
	public void undoMove() {
		chess.movePiece(4, 1, 4, 3); 
		chess.movePiece(4, 6, 4, 4);
		chess.undo();
		Piece[][] board = chess.getBoard();
		Piece temp = board[4][4];
		assertEquals(null, temp);
	}
	

	

}
