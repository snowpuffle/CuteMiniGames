package models.tictactoe;

public class GameBoard {
	// Tic-Tac-Toe Game-Board Attributes
	private static final int SIZE = 3;
	private char[][] board;

	// Default Class Constructor
	public GameBoard() {
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
	public boolean checkWin(int row, int column, char token) {
		// Check Row, Column, and Diagonals for Win
		if (checkWinningRow(row, column, token)) {
			return true;
		} else if (checkWinningColumn(row, column, token)) {
			return true;
		} else if (checkWinningDiagonal(row, column, token)) {
			return true;
		}

		// No Win Condition Met
		return false;
	}

	// Check Row for a Win
	private boolean checkWinningRow(int row, int column, char token) {

		for (int c = 0; c < 3; c++) {
			if (getToken(row, c) != token) {
				break;
			}
			if (c == 2) {
				return true; // Winning Condition Met in the Row
			}
		}
		return false;
	}

	// Check Column for a Win
	private boolean checkWinningColumn(int row, int column, char token) {
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

	// Check Main and Secondary Diagonal for a Win
	private boolean checkWinningDiagonal(int row, int column, char token) {
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