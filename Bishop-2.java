/** The Bishop chess piece */
public class Bishop implements Piece {
	/** X Coordinate of this bishop's position */
	private int xPosition;

	/** Y Coordinate of this bishop's position */
	private int yPosition;

	/** Color of this bishop (white is true, black is false) */
	private boolean color;

	/* Character holding the type of piece this is */
	private char type;

	public Bishop(int xPosition, int yPosition, boolean color) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.color = color;
		this.type = 'B';
	} 

	@Override
	public boolean legalMove(int x, int y, Piece[][] board) {
		int xChange = x - xPosition;
		int yChange = y - yPosition;

		// returns false if not moving diagonally or if not moved at all
		if ((xChange == 0 && yChange == 0) || (Math.abs(xChange) != Math.abs(yChange))) {
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
		if (xChange > 0 && yChange > 0) {
			for (int i = 1; i < xChange; i++) {
				if (board[xPosition + i][yPosition + i] != null) {
					return false;
				}
			}
		} else if (xChange > 0 && yChange < 0) {
			for (int i = 1; i < xChange; i++) {
				if (board[xPosition + i][yPosition - i] != null) {
					return false;
				}
			}
		} else if (xChange < 0 && yChange > 0) {
			for (int i = 1; i < yChange; i++) {
				if (board[xPosition - i][yPosition + i] != null) {
					return false;
				}
			}
		} else if (xChange < 0 && yChange < 0) {
			for (int i = 1; i < (-xChange); i++) {
				if (board[xPosition - i][yPosition - i] != null) {
					return false;
				}
			}
		}

		return true; // if no illegal move detected return true
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
		// not used for bishops
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
		// not used for bishops
		return false;
	}

	@Override
	public char getType() {
		return type;
	}

	@Override
	public boolean getHasMovedOnce() {
		// not used for bishops
		return false;
	}

	@Override
	public void incrementMoveCount() {
		// not used for this piece
		
	}

	@Override
	public void decrementMoveCount() {
		// not used for this piece
		
	}

}
