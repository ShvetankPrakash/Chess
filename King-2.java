/** The King chess piece */
public class King implements Piece {

	/** X Coordinate of this king's position */
	private int xPosition;

	/** Y Coordinate of this king's position */
	private int yPosition;

	/** Color of this king (white is true, black is false) */
	private boolean color;

	/** Holds if this king has moved before */
	private boolean hasMovedOnce; // whenever a king moves once we have to call the setHasMoved to true

	/** Character holding the type of piece this is */
	private char type;
	
	/** Counts number of moves rook has made */
	private int moveCount;


	public King(int xPosition, int yPosition, boolean color) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.color = color;
		this.hasMovedOnce = false;
		this.type = 'K';
		this.moveCount = 0;
	}

	@Override
	public boolean inCheck(Piece[][] board) {
		// use double nested loop and check if the opposing piece at this square has a
		// legal move to attack this king
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[0].length; ++j) {
				if (board[i][j] != null && board[i][j].getType() != 'K' && board[i][j].getColor() != this.color) {
					if (board[i][j].legalMove(this.xPosition, this.yPosition, board)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean legalMove(int x, int y, Piece[][] board) {
		int xChange = Math.abs(x - xPosition);
		int yChange = Math.abs(y - yPosition);

		// checks for no movement
		if (xChange == 0 && yChange == 0) {
			return false;
		}

		// checks to see if moving the king to this square will put him in check
		Piece possibleKing = new King(x, y, this.color); // where king is thinking of moving
		Piece temp = null; // may hold piece at location king is thinking about moving if it is capturing
		if (board[x][y] != null) {
			temp = board[x][y];
		}
		board[x][y] = possibleKing; // move king to position
		// check if it is in check at that position
		if (board[x][y].inCheck(board)) {
			// if it is restore by using replacing temp back where it was and return false
			board[x][y] = temp;
			return false;
		}
		// If not in check still restore position but don't return false
		board[x][y] = temp;

		// checks if moving king to this square puts it next to another king
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (y + j < 0 || y + j > 7 || x + i > 7 || x + i < 0) {
					// don't check this square as it is out of bounds
				} else if (board[x + i][y + j] != null && board[x + i][y + j].getType() == 'K'
						&& board[x + i][y + j].getColor() != this.color) {
					return false;
				}
			}
		}

		// allows for castling
		if (!hasMovedOnce && yChange == 0 && xChange == 2) {
			// checks for legal king side castling
			if (x > xPosition && board[x][y] == null && board[xPosition + 1][y] == null) {// checks no pieces in the way
				Piece possibleKing1 = new King(xPosition + 1, y, this.color); // places king over one square to see if
																				// castling through check
				Piece temp1 = null; // used to store piece temporarily where king in line above is moving
				if (board[xPosition + 1][y] != null) {
					temp1 = board[xPosition + 1][y]; // store piece if king capturing something
				}
				board[xPosition + 1][y] = possibleKing1; // move possibleKing
				if (!board[xPosition + 1][y].inCheck(board) && board[x][y] == null) { // checks if possibleKing would be
																						// in check
					// if not restore and return true
					board[xPosition + 1][y] = temp1;
					return true;
				} else { // if in check, castling thru check returns false
					board[xPosition + 1][y] = temp1;
					return false;
				}
			}
			// checks for legal queen side castling
			else if (x < xPosition && board[x][y] == null && board[xPosition - 1][y] == null) {
				Piece possibleKing2 = new King(xPosition - 1, y, this.color); // places king over one square to see if
																				// castling through check
				Piece temp2 = null; // used to store piece temporarily where king in line above is moving
				if (board[xPosition - 1][y] != null) {
					temp2 = board[xPosition - 1][y]; // store piece if king capturing something
				}
				board[xPosition - 1][y] = possibleKing2; // move possibleKing
				if (!board[xPosition - 1][y].inCheck(board) && board[x][y] == null) { // checks if possibleKing would be
																						// in check
					// if not restore and return true
					board[xPosition - 1][y] = temp2;
					return true;
				} else {// if in check, castling thru check returns false
					board[xPosition - 1][y] = temp2;
					return false;
				}
			} else {
				return false;
			}
		}

		// checks to see if king is moving more than one square
		if (xChange > 1 || yChange > 1) {
			return false;
		}

		// checks to see if square king is moving to is occupied by its own colored
		// piece
		if (board[x][y] != null && board[x][y].getColor() == this.color) {
			return false;
		}

		return true; // if no illegal cases detected return true
	}

	@Override
	public void setX(int newX) {
		xPosition = newX;
	}

	@Override
	public void setY(int newY) {
		yPosition = newY;
	}

	@Override
	public int getX() {
		return xPosition;
	}

	@Override
	public int getY() {
		return yPosition;
	}

	@Override
	public boolean getColor() {
		return color;
	}

	@Override
	public void setHasMovedOnce() {
		if (moveCount == 0) {
			this.hasMovedOnce = false;
		}
		else {
		    this.hasMovedOnce = true;
		}
	}
	
	

	@Override
	public char getType() {
		return type;
	}

	@Override
	public boolean getHasMovedOnce() {
		return hasMovedOnce;
	}

	@Override
	public void incrementMoveCount() {
		moveCount = 1;	
	}

	@Override
	public void decrementMoveCount() {
		moveCount = 0;
	}

}
