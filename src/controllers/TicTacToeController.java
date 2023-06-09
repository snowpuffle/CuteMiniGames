package controllers;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.TicTacToeBoard;
import models.Model;
import models.Player;

public class TicTacToeController implements Initializable {
	// GamePlay Attributes
	private Player player;
	private TicTacToeBoard gameBoard;

	// GamePlay Variables
	private final int MAX_TURNS = 9;
	private boolean gameOver;
	private int numberOfTurns;
	private int playerScore;
	private int computerScore;
	private char playerToken;
	private char computerToken;
	private boolean playerTurn;
	private boolean computerTurn;

	// Main Attributes
	public Label MessageLabel;
	public Label ComputerScoreField;
	public Label PlayerScoreField;
	public Button GoBackButton;
	public Button StartButton;
	public Button RestartButton;

	// Utility Attributes
	private Image Token_X;
	private Image Token_O;

	// GameBoard Cell Buttons
	public Button Button_00;
	public Button Button_01;
	public Button Button_02;
	public Button Button_10;
	public Button Button_11;
	public Button Button_12;
	public Button Button_20;
	public Button Button_21;
	public Button Button_22;

	// Default Class Constructor
	public TicTacToeController(Player player) {
		this.player = player;
	}

	// Initialize Method
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize GamePlay Frame and OnClick Button Actions
		initializeGamePlay();
		initializeImages();
		addListeners();
	}

	// Initialize Frame
	private void initializeGamePlay() {
		// Initialize Game Play Variables
		numberOfTurns = 0;
		playerTurn = false;
		computerTurn = false;

		// Initialize Scores
		playerScore = 0;
		computerScore = 0;
		PlayerScoreField.setText(String.valueOf(playerScore));
		ComputerScoreField.setText(String.valueOf(computerScore));

		// Initialize Player and Board
		playerToken = player.getToken();
		computerToken = (playerToken == 'X') ? 'O' : 'X';
		gameBoard = new TicTacToeBoard();

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
		StartButton.setOnAction(event -> handleStartGame());
		RestartButton.setOnAction(event -> handleRestartGame());

		// Loop Through and Assign Listeners to All Buttons On Game Board
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				Button button = getGameCell(row, column);
				assignButtonListener(button, row, column);
			}
		}
	}

	// Assign Button Listeners
	private void assignButtonListener(Button button, int row, int column) {
		button.setOnAction(event -> handlePlayerMove(row, column));
	}

	// Event: "Start" Button is Clicked
	private void handleStartGame() {
		// Hide the Start button and Show the Restart button
		StartButton.setVisible(false);
		RestartButton.setVisible(true);

		// Start the Game
		createNewGame();
	}

	// Event: "Restart" Button is Clicked
	private void handleRestartGame() {
		// Show the Start button and Hide the Restart button
		StartButton.setVisible(true);
		RestartButton.setVisible(false);

		// Restart the Game
		createNewGame();
	}

	// Create & Initialize a New Game
	private void createNewGame() {
		// Reset Game Play Variables
		gameOver = false;
		numberOfTurns = 0;
		playerTurn = false;
		computerTurn = false;

		// Reset and Clear the Game Board State
		gameBoard.startNewBoard();
		randomizeTurn();
		removeAllTokenImages();

		// Initialize Message Label
		MessageLabel.setTextFill(Color.BLACK);
		handleMessageLabel("Click Start to Begin!");
	}

	// Set Up for Player's Turn
	private void startPlayerTurn() {
		handleMessageLabel("It's Player's Turn!");
		playerTurn = true;
		computerTurn = false;
	}

	// Event: Player Places a Move on the GameBoard
	private void handlePlayerMove(int row, int column) {
		// Check if it is Player's Turn and Game is NOT Over
		if (playerTurn && !gameOver && numberOfTurns < MAX_TURNS) {

			// Set Token ONLY if Cell is NOT Occupied
			if (!isCellOccupied(row, column)) {
				insertToken(row, column, playerToken);

				// Check if Move is the Winning Move
				if (checkForWin(row, column, playerToken)) {
					handleWin("PLAYER"); // Winning Move!
				} else if (checkForDraw()) {
					handleWin("DRAW"); // Draw Move
				} else {
					startComputerTurn(); // Not a Winning Move, Start Computer's Turn
				}
			} else {
				handleMessageLabel("Please Select a Different Cell!");
			}
		}
	}

	// Set Up for Computer's Move
	private void startComputerTurn() {
		// Start Computer's Turn ONLY if Game is NOT Over
		if (!gameOver && numberOfTurns < MAX_TURNS) {

			// Set Computer's Turn
			handleMessageLabel("Computer's Turn!");
			playerTurn = false;
			computerTurn = true;

			// Delay the Computer's Move by 2 Seconds
			PauseTransition delay = new PauseTransition(Duration.seconds(1.0));
			delay.setOnFinished(event -> handleComputerMove());
			delay.play();
		}
	}

	// Computer's Turn to Place a Token
	private void handleComputerMove() {
		// Randomly Select a Position and Check if Position is Available
		int row, column;
		if (computerTurn) {
			do {
				// Select a Random Position
				Random random = new Random();
				row = random.nextInt(3);
				column = random.nextInt(3);
			} while (isCellOccupied(row, column));

			// Set Token at the Randomly Selected Available Position
			insertToken(row, column, computerToken);

			// Check if Move is the Winning Move
			if (checkForWin(row, column, computerToken)) {
				handleWin("COMPUTER"); // Winning Move!
			} else if (checkForDraw()) {
				handleWin("DRAW"); // Draw Move
			} else {
				startPlayerTurn(); // Not a Winning Move, Start Player's Turn
			}
		}
	}

	// Check if Move is the Winning move
	private boolean checkForWin(int row, int column, char token) {
		return gameBoard.checkForWin(row, column, token);
	}

	// Check for a Draw
	private boolean checkForDraw() {
		return numberOfTurns == MAX_TURNS;
	}

	// Check if Cell is Occupied
	private boolean isCellOccupied(int row, int column) {
		return gameBoard.getToken(row, column) != ' ';
	}

	// Set Token on a Row & Column
	private void insertToken(int row, int column, char token) {
		gameBoard.setToken(row, column, token);
		setTokenImage(getGameCell(row, column), token);
		numberOfTurns++;
	}

	// Get GameCell based on Row & Column
	private Button getGameCell(int row, int column) {
		Button[][] GameCells = { { Button_00, Button_01, Button_02 }, { Button_10, Button_11, Button_12 },
				{ Button_20, Button_21, Button_22 } };
		return GameCells[row][column];
	}

	// Set GameCell Button Image based on Token
	private void setTokenImage(Button button, char token) {
		// Set Image to Button based on Token
		if (token == 'X' && Token_X != null) {
			ImageView imageView = createImageView(Token_X, button.getWidth() / 2, button.getHeight() / 2);
			button.setGraphic(imageView);
		} else if (token == 'O' && Token_O != null) {
			ImageView imageView = createImageView(Token_O, button.getWidth() / 2, button.getHeight() / 2);
			button.setGraphic(imageView);
		}
	}

	// Handle a Win or Draw on the Game Board
	private void handleWin(String winner) {
		// Set Game State as Game Over
		gameOver = true;

		// Player Wins the Game
		if ("PLAYER".equalsIgnoreCase(winner)) {
			playerScore += 10;
			PlayerScoreField.setText(String.valueOf(playerScore));
			MessageLabel.setTextFill(Color.GREEN);
			handleMessageLabel("Player Wins! Restart?");
			return;
		}

		// Computer Wins the Game
		if ("COMPUTER".equalsIgnoreCase(winner)) {
			computerScore += 10;
			ComputerScoreField.setText(String.valueOf(computerScore));
			MessageLabel.setTextFill(Color.RED);
			handleMessageLabel("Computer Wins! Restart?");
			return;
		}

		// Game Ends with a Draw
		if ("DRAW".equalsIgnoreCase(winner)) {
			MessageLabel.setTextFill(Color.BLUE);
			handleMessageLabel("It's a Tie! Restart?");
			return;
		}

	}

	// Randomize Turn
	private void randomizeTurn() {
		// Randomize Player Turn
		boolean computerStarts = new Random().nextBoolean();
		if (computerStarts) {
			startComputerTurn();
		} else {
			startPlayerTurn();
		}
	}

	// Initialize Token Images
	private void initializeImages() {
		Token_X = getImage('X');
		Token_O = getImage('O');
	}

	// Clear All Tokens from Buttons
	private void removeAllTokenImages() {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				Button button = getGameCell(row, column);
				button.setGraphic(null);
			}
		}
	}

	// Get Image Based on Token
	private Image getImage(char token) {
		// Initialize Empty Location
		String mainLocation = System.getProperty("user.dir") + "\\resources\\images";
		String imageLocation = "";

		// Set Image Folder Location Based on Type
		if (token == 'X') {
			imageLocation = mainLocation + "\\icons\\letter-x.png";
		} else if (token == 'O') {
			imageLocation = mainLocation + "\\icons\\letter-o.png";
		}

		// Check if the Image File Exists
		File imageFile = new File(imageLocation);
		if (imageFile.exists()) {
			return new Image(imageFile.toURI().toString());
		} else {
			return null; // Image File Not Nound
		}
	}

	// Create ImageView Given Image
	private ImageView createImageView(Image image, double fitWidth, double fitHeight) {
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(fitWidth);
		imageView.setFitHeight(fitHeight);
		return imageView;
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