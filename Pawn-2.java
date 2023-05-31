/** The Pawn chess piece */
public class Pawn implements Piece {

	/** X Coordinate of this pawn's position */
	private int xPosition;

	/** Y Coordinate of this pawn's position */
	private int yPosition;

	/** Color of this pawn (white is true, black is false) */
	private boolean color;

	/** Holds if this pawn has moved before */
	private boolean hasMovedOnce; // whenever a pawn moves once we have to call the setHasMoved to true

	/* Character holding the type of piece this is */
	private char type;

	public Pawn(int xPosition, int yPosition, boolean color) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.color = color;
		this.hasMovedOnce = false;
		this.type = 'P';
	}

	@Override
	public boolean legalMove(int x, int y, Piece[][] board) {
		int xChange = x - xPosition;
		int yChange = y - yPosition;
		// returns false if not moved at all
		if (xChange == 0 && yChange == 0) {
			return false;
		} 

		// checks for white pawns moving backwards
		if (this.color == true) {
			if (yChange < 0) {
				return false;
			}
		}

		// checks for black pawns moving backwards
		if (this.color == false) {
			if (yChange > 0) {
				return false;
			}
		}

		// Checks for white right capture
		if (xChange == 1 && yChange == 1) {
			if (board[x][y] != null) {
				if (board[x][y].getColor() != this.color) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		// Checks for white left capture
		if (xChange == -1 && yChange == 1) {
			if (board[x][y] != null) {
				if (board[x][y].getColor() != this.color) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		// Checks for black right capture
		if (xChange == 1 && yChange == -1) {
			if (board[x][y] != null) {
				if (board[x][y].getColor() != this.color) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		// Checks for black left capture
		if (xChange == -1 && yChange == -1) {
			if (board[x][y] != null) {
				if (board[x][y].getColor() != this.color) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		// Checks if piece is blocking white pawn from moving forward one
		if (yPosition< 7 && board[xPosition][yPosition + 1] != null && yChange > 0) {
			return false;
		}

		// Checks if piece is blocking black pawn from moving forward one
		if (yPosition> 0 && board[xPosition][yPosition - 1] != null && yChange < 0) {
			return false;
		}

		// Checks if piece is blocking white pawn from moving forward two
		if (this.color == true && yPosition< 6 && board[xPosition][yPosition + 2] != null && yChange > 1) {
			return false;
		}

		// Checks if piece is blocking black pawn from moving forward two
		if (this.color == false && yPosition > 1 && board[xPosition][yPosition - 2] != null && yChange < -1) {
			return false;
		}

		// checks that a pawn doesn't move more than one square after its first move
		if (hasMovedOnce && Math.abs(yChange) > 1) {
			return false;
		}

		// Allows pawn to be moved two squares forward on its first move
		if (!hasMovedOnce && Math.abs(yChange) <= 2 && xChange == 0) {
			return true;

		}

		// Makes sure pawn moves in a straight line one square only at a time after
		// first turn if not capturing
		if (Math.abs(yChange) != 1 || xChange != 0) {
			return false;
		}

		return true; // if no illegal move detected return true
	}

	@Override
	public void setHasMovedOnce() {
		//if pawn is white and in its starting row
		if (yPosition == 1 && color == true) {
			this.hasMovedOnce = false;
		}
		//if pawn is black and in its starting row
		else if (yPosition == 6 && color == false) {
			this.hasMovedOnce = false;
		} 
		else {
			this.hasMovedOnce = true; 
		}
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
	public void setX(int newX) {
		xPosition = newX;

	}

	@Override
	public void setY(int newY) {
		yPosition = newY;

	}

	@Override
	public boolean inCheck(Piece[][] board) {
		// not used for pawns
		return false;
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
		// not used for this piece
		
	}

	@Override
	public void decrementMoveCount() {
		// not used for this piece
		
	}

}
