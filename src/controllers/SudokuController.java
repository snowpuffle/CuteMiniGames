package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Model;
import models.SudokuBoard;

public class SudokuController implements Initializable {
	// GamePlay Attributes
	private SudokuBoard gameBoard;
	private TextField[][] GameCells;
	private static final int SIZE = 4;

	private boolean gameOver;

	// Main Attributes
	public Label MessageLabel;
	public Button GoBackButton;
	public Button StartButton;
	public Button RestartButton;
	public Button SubmitButton;

	// GameBoard Cell TextFields
	public TextField TextField_00;
	public TextField TextField_01;
	public TextField TextField_02;
	public TextField TextField_03;
	public TextField TextField_10;
	public TextField TextField_11;
	public TextField TextField_12;
	public TextField TextField_13;
	public TextField TextField_20;
	public TextField TextField_21;
	public TextField TextField_22;
	public TextField TextField_23;
	public TextField TextField_30;
	public TextField TextField_31;
	public TextField TextField_32;
	public TextField TextField_33;

	// Initialize Method
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		GameCells = new TextField[][] { { TextField_00, TextField_01, TextField_02, TextField_03 },
				{ TextField_10, TextField_11, TextField_12, TextField_13 },
				{ TextField_20, TextField_21, TextField_22, TextField_23 },
				{ TextField_30, TextField_31, TextField_32, TextField_33 } };

		// Initialize Frame and OnClick Button Actions
		initializeFrame();
		initializeGamePlay();
		addListeners();
	}

	// Initialize Frame
	private void initializeFrame() {
		lockGameBoard();
	}

	// Initialize Frame
	private void initializeGamePlay() {
		// Initialize Player and Board
		gameBoard = new SudokuBoard();
		gameOver = true;

		// Show the Start button and Hide the Restart button
		StartButton.setVisible(true);
		RestartButton.setVisible(false);

		// Initialize Message Label
		handleMessageLabel("Click Start to Begin!");
	}

	// Add Listeners to Buttons on Frame
	private void addListeners() {
		// Add Listeners to All Buttons
		GoBackButton.setOnAction(event -> handleGoBack());
		SubmitButton.setOnAction(event -> handleSubmit());
		StartButton.setOnAction(event -> handleStartGame());
		RestartButton.setOnAction(event -> handleRestartGame());

	}

	// Event: "Start" Button is Clicked
	private void handleStartGame() {
		// Initialize Message Label
		MessageLabel.setTextFill(Color.BLACK);
		handleMessageLabel("Ready? Go!");

		// Hide the Start button and Show the Restart button
		StartButton.setVisible(false);
		RestartButton.setVisible(true);

		// Start the Game
		unlockGameBoard();
		createNewGame();
	}

	// Event: "Restart" Button is Clicked
	private void handleRestartGame() {
		// Initialize Message Label
		MessageLabel.setTextFill(Color.BLACK);
		handleMessageLabel("Click Start to Begin!");

		// Show the Start button and Hide the Restart button
		StartButton.setVisible(true);
		RestartButton.setVisible(false);

		// Restart the Game
		lockGameBoard();
		resetGameBoard();
	}

	// Create & Initialize a New Game
	private void createNewGame() {
		// Reset Game Play Variables
		gameOver = false;

		// Reset and Clear the Game Board
		gameBoard.startNewBoard();
		resetGameBoard();
		presetGameBoard();
	}

	// Event: "Submit" Button is Clicked
	private void handleSubmit() {
		if (!gameOver) {
			// Read Game Cell Values into GameBoard
			readGameCellValues();

			// Check if Board is Full
			if (checkIsFullBoard() && checkForWin()) {
				handleWin();
			}
		}
	}

	// Check if Board is Full
	private boolean checkIsFullBoard() {
		if (gameBoard.checkIsFullBoard()) {
			return true;
		}
		handleMessageLabel("Please Fill the Board!");
		return false;
	}

	// Check if Board is Win
	private boolean checkForWin() {
		if (gameBoard.checkForWin()) {
			return true;
		}
		handleMessageLabel("Oh No! Board is NOT Correct!");
		return false;
	}

	// Unlock Game Board
	private void unlockGameBoard() {
		for (TextField[] row : GameCells) {
			for (TextField cell : row) {
				cell.setEditable(true);
				addGameCellRestriction(cell);
			}
		}
	}

	// Lock Game Board
	private void lockGameBoard() {
		for (TextField[] row : GameCells) {
			for (TextField cell : row) {
				cell.setEditable(false);
			}
		}
	}

	// Reset Game Board
	private void resetGameBoard() {
		for (TextField[] row : GameCells) {
			for (TextField cell : row) {
				cell.setText("");
				cell.setEditable(true);
			}
		}
		gameBoard.startNewBoard(); // Reset the Underlying Game Board Data
	}

	// Read All Values to the Board
	private void readGameCellValues() {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				TextField textField = GameCells[row][col];
				String text = textField.getText();
				if (!text.isEmpty()) {
					int digit = Integer.parseInt(text);
					gameBoard.setNumber(row, col, digit);
				} else {
					gameBoard.setNumber(row, col, 0);
				}
			}
		}
	}

	// Set Preset Game Board
	private void presetGameBoard() {
		int[][] board = gameBoard.getRandomPresetBoard();

		// Apply the preset board to the game board
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				int cellValue = board[row][col];

				if (cellValue != 0) {
					gameBoard.setNumber(row, col, cellValue);
					TextField textField = GameCells[row][col];
					textField.setText(String.valueOf(board[row][col]));
				}
			}
		}
	}

	// Restrict Input Length to 1 Digit Number
	private void addGameCellRestriction(TextField textField) {
		textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
			String input = event.getCharacter();
			if (!input.matches("[1-9]")) {
				event.consume();
			} else if (textField.getText().length() >= 1) {
				event.consume();
			}
		});
	}

	// Handle a Win or Draw on the Game Board
	private void handleWin() {
		// Set Game State and Lock Board
		gameOver = true;
		lockGameBoard();

		// Display Winner Message
		MessageLabel.setTextFill(Color.GREEN);
		handleMessageLabel("You Win! Restart?");
	}

	// Event: "Go Back" Button is Clicked
	private void handleGoBack() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showDashboardFrame();
	}

	// Handle Error
	private void handleMessageLabel(String message) {
		MessageLabel.setText(message);
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) MessageLabel.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}