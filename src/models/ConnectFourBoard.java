package models;

public class ConnectFourBoard {
	// Connect-Four GameBoard Attributes
	private static final int ROWS = 6;
	private static final int COLUMNS = 7;
	private char[][] board;

	// Default Class Constructor
	public ConnectFourBoard() {
		board = new char[ROWS][COLUMNS];
		startNewBoard();
	}

	// Initialize Game Board
	public void startNewBoard() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				board[row][col] = ' ';
			}
		}
	}

	// Return Game Board
	public char[][] getGameBoard() {
		return board;
	}

	// Return Token at Position
	public char getToken(int row, int column) {
		return board[row][column];
	}

	// Set Token at Position
	public void setToken(int row, int column, char token) {
		board[row][column] = token;
	}

	// Check for Win State
	public boolean checkForWin(int row, int column, char token) {
		// Check Row, Column, and Diagonals for Win
		if (checkForHorizontalWin(row, token)) {
			System.out.println("Horizontal Win!");
			return true;
		} else if (checkForVerticalWin(column, token)) {
			System.out.println("Vertical Win!");
			return true;
		} else if (checkForAscDiagonalWin(row, column, token)) {
			System.out.println("Diagonal Win!");
			return true;
		} else if (checkForDescDiagonalWin(row, column, token)) {
			System.out.println("Diagonal Win!");
			return true;
		}

		// No Win Condition Met
		return false;
	}

	// Check for a Horizontal Win
	private boolean checkForHorizontalWin(int row, char token) {
		int count = 0;
		for (int col = 0; col < COLUMNS; col++) {
			if (board[row][col] == token) {
				count++;
				if (count == 4) {
					return true; // Winning Condition Found
				}
			} else {
				count = 0;
			}
		}
		return false;
	}

	// Check for a Vertical Win
	private boolean checkForVerticalWin(int column, char token) {
		int count = 0;
		for (int row = 0; row < ROWS; row++) {
			if (board[row][column] == token) {
				count++;
				if (count == 4) {
					return true; // Winning Condition Found
				}
			} else {
				count = 0;
			}
		}
		return false;
	}

	// Check for a Ascending Diagonal Win
	private boolean checkForAscDiagonalWin(int row, int column, char token) {
		// Check for Ascending Diagonal Win
		int count = 0;
		int startRow = row;
		int startCol = column;

		// Check Top-Left Side of the Current Position
		while (startRow > 0 && startCol > 0 && board[startRow - 1][startCol - 1] == token) {
			startRow--;
			startCol--;
		}

		// Count Consecutive Tokens in the Ascending Diagonal
		while (startRow < ROWS && startCol < COLUMNS && board[startRow][startCol] == token) {
			count++;
			if (count == 4) {
				return true; // Winning Condition Found
			}
			startRow++;
			startCol++;
		}
		return false;
	}

	// Check for a Descending Diagonal Win
	private boolean checkForDescDiagonalWin(int row, int column, char token) {
		// Check for Descending Diagonal Win
		int count = 0;
		int startRow = row;
		int startCol = column;

		// Check Top-Right Side of the Current Position
		while (startRow > 0 && startCol < COLUMNS - 1 && board[startRow - 1][startCol + 1] == token) {
			startRow--;
			startCol++;
		}

		// Count Consecutive Tokens in the Descending Diagonal
		while (startRow < ROWS && startCol >= 0 && board[startRow][startCol] == token) {
			count++;
			if (count == 4) {
				return true; // Winning Condition Found
			}
			startRow++;
			startCol--;
		}

		return false;
	}

}