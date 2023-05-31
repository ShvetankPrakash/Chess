import java.util.Stack;

/** Logical model of a traditional chess game */
public class ChessModel {

	/** Game board holding pieces */
	private Piece[][] board;

	/** Holds whose turn it is; white is true, black is false */
	private boolean currentPlayer;

	/** White's king x location */
	private int whiteKingX;

	/** White's king y location */
	private int whiteKingY;

	/** Black's king x location */
	private int blackKingX;

	/** Black's king y location */
	private int blackKingY;

	/** Stack of previous state of board for undoing. */
	private Stack<ChessModel> previousBoards;
	
	/** Holds the character of the last piece moved (used for undo) */
	private Piece lastPieceMoved;
	
	/** Holds the character of the second to last piece moved (used for undo) */
	private Piece secondLastPieceMoved;

	public ChessModel() {
		board = new Piece[8][8];
		currentPlayer = true;
		previousBoards = new Stack<>();
	}

	/** Sets up the starting position of the pieces */
	public void setUpBoard() {
		board[0][7] = new Rook(0, 7, false);
		board[0][0] = new Rook(0, 0, true);
		board[7][7] = new Rook(7, 7, false);
		board[7][0] = new Rook(7, 0, true);
		board[2][7] = new Bishop(2, 7, false);
		board[2][0] = new Bishop(2, 0, true);
		board[5][7] = new Bishop(5, 7, false);
		board[5][0] = new Bishop(5, 0, true);
		board[1][7] = new Knight(1, 7, false);
		board[1][0] = new Knight(1, 0, true);
		board[6][7] = new Knight(6, 7, false);
		board[6][0] = new Knight(6, 0, true);
		board[4][7] = new King(4, 7, false);
		blackKingX = 4;
		blackKingY = 7;
		board[4][0] = new King(4, 0, true);
		whiteKingX = 4;
		whiteKingY = 0;
		board[3][7] = new Queen(3, 7, false);
		board[3][0] = new Queen(3, 0, true);
		board[0][6] = new Pawn(0, 6, false);
		board[1][6] = new Pawn(1, 6, false);
		board[2][6] = new Pawn(2, 6, false);
		board[3][6] = new Pawn(3, 6, false);
		board[4][6] = new Pawn(4, 6, false);
		board[5][6] = new Pawn(5, 6, false);
		board[6][6] = new Pawn(6, 6, false);
		board[7][6] = new Pawn(7, 6, false);
		board[0][1] = new Pawn(0, 1, true);
		board[1][1] = new Pawn(1, 1, true);
		board[2][1] = new Pawn(2, 1, true);
		board[3][1] = new Pawn(3, 1, true);
		board[4][1] = new Pawn(4, 1, true);
		board[5][1] = new Pawn(5, 1, true);
		board[6][1] = new Pawn(6, 1, true);
		board[7][1] = new Pawn(7, 1, true);
		saveCopy(); // push the initial set up onto the stack
	}

	/** Moves piece to new location and assigns it a new x and y */
	public void move(int initialX, int initialY, int finalX, int finalY) {
		board[finalX][finalY] = board[initialX][initialY];
		board[initialX][initialY] = null;
		board[finalX][finalY].setX(finalX);
		board[finalX][finalY].setY(finalY);
		board[finalX][finalY].incrementMoveCount();
	}

	/**
	 * Returns true if piece successfully moved and switches player, also adds board
	 * state to stack if legal move made
	 */
	public boolean movePiece(int initialX, int initialY, int finalX, int finalY) {
		// checks for moving only currentPlayer's pieces
		if (board[initialX][initialY].getColor() != currentPlayer) {
			return false;
		}
		// finds rook pieces for castling purposes
		boolean whiteKingRook = false;
		boolean whiteQueenRook = false;
		boolean blackKingRook = false;
		boolean blackQueenRook = false;
		if (board[7][0] != null && board[7][0].getType() == 'R' && board[7][0].getHasMovedOnce() == false) {
			whiteKingRook = true;
		}
		if (board[0][0] != null && board[0][0].getType() == 'R' && board[0][0].getHasMovedOnce() == false) {
			whiteQueenRook = true;
		}
		if (board[7][7] != null && board[7][7].getType() == 'R' && board[7][7].getHasMovedOnce() == false) {
			blackKingRook = true;
		}
		if (board[0][7] != null && board[0][7].getType() == 'R' && board[0][7].getHasMovedOnce() == false) {
			blackQueenRook = true;
		}
		// if the current player is not in check they can make any legal move
		if (!isInCheck(currentPlayer)) {
			// checks to make sure the move being made is legal and not accessing a null
			// pointer
			if (board[initialX][initialY] != null && board[initialX][initialY].legalMove(finalX, finalY, this.board)) {
				// this case checks for correct king side castling
				if (board[initialX][initialY].getType() == 'K' && initialX == 4 && (finalX == 6 || finalX == 2)) {
					// handles king side castling
					if (finalX == 6 && whiteKingRook && board[initialX][initialY].getColor()) {// white castling
						move(initialX, initialY, finalX, finalY);
						whiteKingX = finalX;
						whiteKingY = finalY;
						board[finalX][finalY].setHasMovedOnce();
						secondLastPieceMoved = board[finalX][finalY];
						move(7, 0, 5, 0);
						board[5][0].setHasMovedOnce();
						lastPieceMoved = board[5][0];
						saveCopy();
						switchCurrentPlayer();
						return true;
					} else if (finalX == 6 && blackKingRook && !board[initialX][initialY].getColor()) {// black castling
						move(initialX, initialY, finalX, finalY);
						blackKingX = finalX;
						blackKingY = finalY;
						board[finalX][finalY].setHasMovedOnce();
						secondLastPieceMoved = board[finalX][finalY];
						move(7, 7, 5, 7);
						board[5][7].setHasMovedOnce();
						lastPieceMoved = board[5][7];
						saveCopy();
						switchCurrentPlayer();
						return true;
					}
					// handles queen side castling
					else if (finalX == 2 && (whiteQueenRook) && board[initialX][initialY].getColor()) { // white
																										// castling
						move(initialX, initialY, finalX, finalY);
						whiteKingX = finalX;
						whiteKingY = finalY;
						board[finalX][finalY].setHasMovedOnce();
						secondLastPieceMoved = board[finalX][finalY];
						move(0, 0, 3, 0);
						board[3][0].setHasMovedOnce();
						lastPieceMoved = board[3][0];
						saveCopy();
						switchCurrentPlayer();
						return true;
					} else if (finalX == 2 && blackQueenRook && !board[initialX][initialY].getColor()) { // black
																											// castling
						move(initialX, initialY, finalX, finalY);
						blackKingX = finalX;
						blackKingY = finalY;
						board[finalX][finalY].setHasMovedOnce();
						secondLastPieceMoved = board[finalX][finalY];
						move(0, 7, 3, 7);
						board[3][7].setHasMovedOnce();
						lastPieceMoved = board[3][7];
						saveCopy();
						switchCurrentPlayer();
						return true;
					}
					return false;
				}

				else {// handles all moves that are not castling
					Piece temp = board[finalX][finalY];
					move(initialX, initialY, finalX, finalY);
					int temp1W = whiteKingX;
					int temp2W = whiteKingY;
					int temp1B = blackKingX;
					int temp2B = blackKingX;

					// reset the king coordinates, if king moved
					if (board[finalX][finalY].getType() == 'K') {
						if (currentPlayer) { // white player king moves
							whiteKingX = finalX;
							whiteKingY = finalY;
						} else { // black player king moves
							blackKingX = finalX;
							blackKingY = finalY;
						}

					}
					// handles illegal moving of pinned pieces
					if (isInCheck(currentPlayer)) {
						move(finalX, finalY, initialX, initialY);
						board[initialX][initialY].decrementMoveCount();
						board[finalX][finalY] = temp;
						whiteKingX = temp1W;
						whiteKingY = temp2W;
						blackKingX = temp1B;
						blackKingY = temp2B;
						return false;
					}
					// checks for pawn being promoted
					if (board[finalX][finalY].getType() == 'P') {
						pawnPromotion(currentPlayer, finalX, finalY);
					}

					// set king, pawn, or rook to has moved once
					if (board[finalX][finalY].getType() == 'K' || board[finalX][finalY].getType() == 'R'
							|| board[finalX][finalY].getType() == 'P') {
						board[finalX][finalY].setHasMovedOnce();
					}
					// moves king location coordinates
					if (board[finalX][finalY].getType() == 'K') {
						if (currentPlayer) { // white player king moves
							whiteKingX = finalX;
							whiteKingY = finalY;
						} else { // black player king moves
							blackKingX = finalX;
							blackKingY = finalY;
						}

					}
					secondLastPieceMoved = null;
					lastPieceMoved = board[finalX][finalY];
					saveCopy();
					switchCurrentPlayer();
					return true;
				}
			} else {
				return false;
			}
		}
		// if the current player is in check they must make a legal move that takes them
		// out of check
		else {
			// checks to make sure the move is legal according to way pieces move
			if (board[initialX][initialY] != null && board[initialX][initialY].legalMove(finalX, finalY, this.board)) {
				// temporary holds this location on board
				Piece temp = board[finalX][finalY];
				// moves piece
				move(initialX, initialY, finalX, finalY);
				if (board[finalX][finalY].getType() == 'K') {
					if (currentPlayer) { // white player king moves
						whiteKingX = finalX;
						whiteKingY = finalY;
					} else { // black player king moves
						blackKingX = finalX;
						blackKingY = finalY;
					}
				}
				// checks to see after the move if player is still in check
				if (isInCheck(currentPlayer)) {
					// reset board back to what it was since this move doesn't take them out of
					// check
					move(finalX, finalY, initialX, initialY);
					board[initialX][initialY].decrementMoveCount();
					if (board[initialX][initialY].getType() == 'K') {
						if (currentPlayer) { // white player king moves
							whiteKingX = initialX;
							whiteKingY = initialY;
						} else { // black player king moves
							blackKingX = initialX;
							blackKingY = initialY;
						}
					}
					board[finalX][finalY] = temp;
					return false;
				} else {// if this move takes them out of check allow the move
					temp = null;
					secondLastPieceMoved = null;
					lastPieceMoved = board[finalX][finalY];
					saveCopy();
					switchCurrentPlayer();
					return true;
				}

			}
		}
		return false;
	}

	/** Checks if the currentPlayer's king is in check */
	public boolean isInCheck(boolean currentPlayer) {
		int currentKingY = -1;
		int currentKingX = -1;
		if (currentPlayer) {
			currentKingX = whiteKingX;
			currentKingY = whiteKingY;
		} else {
			currentKingX = blackKingX;
			currentKingY = blackKingY;
		}
		// determine if this king is in check
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[0].length; ++j) {
				if (board[i][j] != null && board[i][j].getType() != 'K' && board[i][j].getColor() != currentPlayer) {
					if (board[i][j].legalMove(currentKingX, currentKingY, this.board)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/** Finds if there is a legal move to get the currentPlayer out of check */
	public boolean canGetOutOfCheck(boolean currentPlayer) {
		// make sure the currentPlayer is actually in check
		if (isInCheck(currentPlayer)) {
			int currentKingY = -1;
			int currentKingX = -1;
			if (currentPlayer) {
				currentKingX = whiteKingX;
				currentKingY = whiteKingY;
			} else {
				currentKingX = blackKingX;
				currentKingY = blackKingY;
			}
			// determine if there is a legal move to get him out of check
			// we use a quad nested for loop to check all the pieces and all potential moves
			// to see if any can get currPlayer out of check
			boolean outOfCheck = false;
			for (int i = 0; i < board.length; ++i) {
				for (int j = 0; j < board[0].length; ++j) {
					for (int potentialX = 0; potentialX < 8; ++potentialX) {
						for (int potentialY = 0; potentialY < 8; ++potentialY) {
							if (board[i][j] != null && board[i][j].getColor() == currentPlayer
									&& board[i][j].legalMove(potentialX, potentialY, board)) {
								// holds what was in this location temporarily so we can restore the last
								// position of the game after finding the check blocking move exists
								Piece temp = board[potentialX][potentialY];
								move(i, j, potentialX, potentialY);
								// have to handle the king piece differently
								if (board[potentialX][potentialY] != null
										&& board[potentialX][potentialY].getType() == 'K') {
									// check if still in check and restore game
									if (board[potentialX][potentialY].inCheck(board)) {
										outOfCheck = false;
										move(potentialX, potentialY, i, j);
										board[potentialX][potentialY] = temp;
									}
									// if not restore and return true
									else {
										move(potentialX, potentialY, i, j);
										board[potentialX][potentialY] = temp;
										outOfCheck = true;
										return outOfCheck;
									}
								}
								// if not a moving the king piece and still in check restore game
								else if (board[currentKingX][currentKingY].inCheck(this.board)) {
									outOfCheck = false;
									move(potentialX, potentialY, i, j);
									board[potentialX][potentialY] = temp;
								} else { // if out of check restore and return true
									move(potentialX, potentialY, i, j);
									board[potentialX][potentialY] = temp;
									outOfCheck = true;
									return outOfCheck;
								}
							}
						}
					}
				}
			}
			return outOfCheck; // this will return if no move found that can block or get out of check
		} else {
			return true;
		}
	}

	/**
	 * Determines winning state (i.e. checkmate where king can not get out of check)
	 */
	public boolean checkmate() {
		if (canGetOutOfCheck(currentPlayer)) {
			return false;
		}
		return true;
	}

	/** Returns board */
	public Piece[][] getBoard() {
		return board;
	}

	/** Returns currentPlayer */
	public boolean getCurrentPlayer() {
		return currentPlayer;
	}

	/** Switches current player */
	public void switchCurrentPlayer() {
		this.currentPlayer = !currentPlayer;
	}

	/** Switches pawn to queen when pawn gets to opposing side of board */
	public void pawnPromotion(boolean currentPlayer, int finalX, int finalY) {
		// white pawn promotion to queen
		if (currentPlayer) {
			if (finalY == 7) {
				board[finalX][finalY] = new Queen(finalX, finalY, true);
			}
		}
		// black pawn promotion to queen
		else {
			if (finalY == 0) {
				board[finalX][finalY] = new Queen(finalX, finalY, false);
			}
		}
	}

	/**
	 * Undoes the last move and restores board to previous state, only undoes one
	 * moved since this is all that we have decided is reasonable to allow
	 */
	public void undo() {
		if (previousBoards.size() > 1) { //need the initial board setup to remain
			previousBoards.pop(); // removes the current board
			ChessModel previous = previousBoards.peek(); // gets the previous board
			board = previous.getBoard(); //resets board to previous one
			//restores all the pieces coordinates
			for (int x = 0; x < 8; ++x) {
				for (int y = 0; y < 8; ++y) {
					if (board[x][y] != null) {
						board[x][y].setX(x);
						board[x][y].setY(y);
						//used to properly undo castling and allow it again after undo by resetting
						if (secondLastPieceMoved != null) {
							if (board[x][y].getType() == 'K' && board[x][y].getColor() == secondLastPieceMoved.getColor()) {
								board[x][y].decrementMoveCount();
							}
							if (board[x][y].getType() == 'R' && board[x][y].getColor() == secondLastPieceMoved.getColor()) {
								board[x][y].decrementMoveCount();
							}	
						}
						//used to undo rook move and allow it to castle still if that undone move was its first
						else if (lastPieceMoved.getType() == 'R') {
							     if (board[x][y].getType() == 'R' && board[x][y].getColor() == lastPieceMoved.getColor()) {
								     board[x][y].decrementMoveCount();
							     }
						}
						//used to undo king move and allow it to castle still if that undone move was its first
						else if (lastPieceMoved.getType() == 'K') {
							     if (board[x][y].getType() == 'K' && board[x][y].getColor() == lastPieceMoved.getColor()) {
								     board[x][y].decrementMoveCount();
							     } 
						}
						board[x][y].setHasMovedOnce();
					}
				}
			}
			//Resets kings coordinates
			whiteKingX = previous.whiteKingX;
			whiteKingY = previous.whiteKingY;
			blackKingX = previous.blackKingX;
			blackKingY = previous.blackKingY;
			switchCurrentPlayer();
			saveCopy();
		}

	} 

	/* Prints the current state of the board, used for debugging purposes */
	public void printBoard() {
		for (int y = 0; y < 8; ++y) {
			for (int x = 0; x < 8; ++x) {
				if (board[x][y] != null) {
					if (x == 7) {
						StdOut.println(board[x][y].getType());
					} else
						StdOut.print(board[x][y].getType());
				} else {
					if (x == 7) {
						StdOut.println("-");
					} else
						StdOut.print("-");
				}
			}
		}
	}

	/**
	 * Saves a copy of the current board state on a stack for undoing. (code
	 * referenced from Peter Drake's GO)
	 */
	public void saveCopy() {
		ChessModel copy = new ChessModel();
		//copies over pieces to new board
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				copy.board[x][y] = board[x][y];
			}
		}
		//copies king coordinates, current player, and then pushes on to stack
		copy.whiteKingX = whiteKingX;
		copy.whiteKingY = whiteKingY;
		copy.blackKingX = blackKingX;
		copy.blackKingY = blackKingY;
		copy.currentPlayer = currentPlayer;
		previousBoards.push(copy);
	}
}
