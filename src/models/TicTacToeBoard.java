package models;

public class TicTacToeBoard {
	// Tic-Tac-Toe GameBoard Attributes
	private static final int SIZE = 3;
	private char[][] board;

	// Default Class Constructor
	public TicTacToeBoard() {
		board = new char[SIZE][SIZE];
		startNewBoard();
	}

	// Initialize Game Board
	public void startNewBoard() {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
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
			return true;
		} else if (checkForVerticalWin(column, token)) {
			return true;
		} else if (checkForDiagonalWin(row, column, token)) {
			return true;
		}

		// No Win Condition Met
		return false;
	}

	// Check for a Horizontal Win
	private boolean checkForHorizontalWin(int row, char token) {
		// Check the Row
		for (int column = 0; column < 3; column++) {
			if (getToken(row, column) != token) {
				break;
			}
			if (column == 2) {
				return true; // Winning Condition Met in the Row
			}
		}
		return false;
	}

	// Check for a Vertical Win
	private boolean checkForVerticalWin(int column, char token) {
		// Check the column
		for (int r = 0; r < 3; r++) {
			if (getToken(r, column) != token) {
				break;
			}
			if (r == 2) {
				return true; // Winning Condition Met in the Column
			}
		}
		return false;
	}

	// Check For Main or Secondary Diagonal Win
	private boolean checkForDiagonalWin(int row, int column, char token) {
		// Check the Main Diagonal
		if (row == column) {
			for (int i = 0; i < 3; i++) {
				if (getToken(i, i) != token) {
					break;
				}
				if (i == 2) {
					return true; // Winning Condition Met in the Main Diagonal
				}
			}
		}

		// Check the Secondary Diagonal
		if (row + column == 2) {
			for (int i = 0; i < 3; i++) {
				if (getToken(i, 2 - i) != token) {
					break;
				}
				if (i == 2) {
					return true; // Winning Condition Met in the Secondary Diagonal
				}
			}
		}
		return false;
	}
}