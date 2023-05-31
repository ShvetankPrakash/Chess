/** The Rook chess piece */
public class Rook implements Piece {

	/** X Coordinate of this rook's position */
	private int xPosition;

	/** Y Coordinate of this rook's position */
	private int yPosition;

	/** Color of this rook (white is true, black is false) */
	private boolean color;

	/** Holds if this rook has moved before */
	private boolean hasMovedOnce; // whenever a rook moves once we have to call the setHasMoved to true

	/** Character holding the type of piece this is */
	private char type;
	
	/** Counts number of moves rook has made */
	private int moveCount;

	public Rook(int xPosition, int yPosition, boolean color) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.color = color;
		this.type = 'R';
		this.moveCount = 0;
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
	public boolean legalMove(int x, int y, Piece[][] board) {
		int xChange = x - xPosition;
		int yChange = y - yPosition;

		// returns false if not moving in a straight line or if not moved at all
		if ((xChange == 0 && yChange == 0) || (xChange != 0 && yChange != 0)) {
			return false;
		}

		// checks if position x, y already has a piece there and if it is the same color
		// as the current piece
		if (board[x][y] != null) {
			if (color == board[x][y].getColor()) {
				return false;
			}
		}

		// checks if there is a piece blocking the move
		if (xChange != 0) {
			if (xChange < 0) {
				for (int i = xPosition - 1; i > x; --i) {
					if (board[i][yPosition] != null) {
						return false;
					}
				}
			}
			if (xChange > 0) {
				for (int i = xPosition + 1; i < x; ++i) {
					if (board[i][yPosition] != null) {
						return false;
					}
				}
			}
		} else if (yChange != 0) {
			if (yChange < 0) {
				for (int i = yPosition - 1; i > y; --i) {
					if (board[xPosition][i] != null) {
						return false;
					}
				}
			}
			if (yChange > 0) {
				for (int i = yPosition + 1; i < y; ++i) {
					if (board[xPosition][i] != null) {
						return false;
					}
				}
			}
		}

		return true; // if no illegal move detected return true
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
	public boolean getHasMovedOnce() {
		return hasMovedOnce;

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
	public boolean inCheck(Piece[][] board) {
		// not used for rook
		return false;
	}

	@Override
	public char getType() {
		return type;
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
