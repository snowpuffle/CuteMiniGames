package models;

import java.util.Random;

public class SudokuBoard {
	// Sudoku Board Attributes
	private static final int SIZE = 4;
	private int[][] board;
	private int[][][] PRESET_BOARDS = { { { 0, 0, 0, 4 }, { 0, 0, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 3, 0, 0, 0 }, { 0, 0, 4, 0 }, { 0, 0, 0, 0 }, { 0, 1, 0, 0 } },
			{ { 0, 1, 2, 3 }, { 4, 0, 0, 0 }, { 0, 0, 0, 0 }, { 3, 0, 0, 0 } },
			{ { 3, 0, 0, 1 }, { 0, 0, 4, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 4 } },
			{ { 0, 0, 0, 0 }, { 0, 3, 0, 0 }, { 0, 0, 2, 0 }, { 0, 0, 0, 0 } },
			{ { 2, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 3 } },
			{ { 0, 0, 4, 0 }, { 0, 0, 0, 3 }, { 3, 0, 0, 0 }, { 0, 2, 0, 0 } } };

	// Default Class Constructor
	public SudokuBoard() {
		board = new int[SIZE][SIZE];
		startNewBoard();
	}

	// Initialize Game Board
	public void startNewBoard() {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				board[row][col] = 0;
			}
		}
	}

	// Set Number at Position
	public void setNumber(int row, int col, int number) {
		board[row][col] = number;
	}

	// Get Number at Position
	public int getNumber(int row, int col) {
		return board[row][col];
	}

	// Check if Position is Filled
	public boolean isFilled(int row, int col) {
		return board[row][col] != 0;
	}

	// Check if Number is Valid in Row
	private boolean isValidRow(int row) {
		boolean[] used = new boolean[SIZE + 1]; // Array to track the used digits

		for (int col = 0; col < SIZE; col++) {
			int number = board[row][col];
			if (number < 1 || number > SIZE || used[number]) {
				return false; // Invalid digit or duplicate found
			}
			used[number] = true;
		}

		return true; // Row is valid
	}

	// Check if Number is Valid in Column
	private boolean isValidColumn(int col) {
		boolean[] used = new boolean[SIZE + 1]; // Array to Track the Used Digits

		for (int row = 0; row < SIZE; row++) {
			int number = board[row][col];
			if (number < 1 || number > SIZE || used[number]) {
				return false; // Invalid Digit or Duplicate Found
			}
			used[number] = true;
		}

		return true; // Column is Valid
	}

	// Check if Number is Valid in Subgrid
	private boolean isValidSubgrid(int startRow, int startCol) {
		boolean[] used = new boolean[SIZE + 1]; // Array to Track the Used Digits

		for (int row = startRow; row < startRow + 2; row++) {
			for (int col = startCol; col < startCol + 2; col++) {
				int number = board[row][col];
				if (number < 1 || number > SIZE || used[number]) {
					return false; // Invalid Digit or Duplicate Found
				}
				used[number] = true;
			}
		}

		return true; // Subgrid is Valid
	}

	// Check for Win State
	public boolean checkForWin() {
		return checkIsFullBoard() && checkIsValidBoard();
	}

	// Check if the Board is Completely Filled
	public boolean checkIsFullBoard() {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (board[row][col] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	// Check if the Board Satisfies the Sudoku Rules
	private boolean checkIsValidBoard() {
		// Check each row
		for (int row = 0; row < SIZE; row++) {
			if (!isValidRow(row)) {
				return false;
			}
		}

		// Check each column
		for (int col = 0; col < SIZE; col++) {
			if (!isValidColumn(col)) {
				return false;
			}
		}

		// Check each subgrid
		int subgridSize = (int) Math.sqrt(SIZE);
		for (int startRow = 0; startRow < SIZE; startRow += subgridSize) {
			for (int startCol = 0; startCol < SIZE; startCol += subgridSize) {
				if (!isValidSubgrid(startRow, startCol)) {
					return false;
				}
			}
		}

		return true;
	}

	// Get a Random Preset Board
	public int[][] getRandomPresetBoard() {
		Random rand = new Random();
		int randomIndex = rand.nextInt(PRESET_BOARDS.length);
		int[][] selectedBoard = PRESET_BOARDS[randomIndex];

		return selectedBoard;
	}

}
