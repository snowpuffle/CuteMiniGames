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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.*;

public class ConnectFourController implements Initializable {
	// GamePlay Attributes
	private Player player;
	private ConnectFourBoard gameBoard;

	// GamePlay Variables
	private boolean gameOver;
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
	private static final int ROWS = 6;
	private static final int COLUMNS = 7;
	private Image ArrowImage;
	private Image Token_X;
	private Image Token_O;

	// GameBoard Column Buttons
	public Button ColumnButton_0;
	public Button ColumnButton_1;
	public Button ColumnButton_2;
	public Button ColumnButton_3;
	public Button ColumnButton_4;
	public Button ColumnButton_5;
	public Button ColumnButton_6;

	// GameBoard Cell Panes
	public Pane GameCell_00;
	public Pane GameCell_01;
	public Pane GameCell_02;
	public Pane GameCell_03;
	public Pane GameCell_04;
	public Pane GameCell_05;
	public Pane GameCell_06;

	public Pane GameCell_10;
	public Pane GameCell_11;
	public Pane GameCell_12;
	public Pane GameCell_13;
	public Pane GameCell_14;
	public Pane GameCell_15;
	public Pane GameCell_16;

	public Pane GameCell_20;
	public Pane GameCell_21;
	public Pane GameCell_22;
	public Pane GameCell_23;
	public Pane GameCell_24;
	public Pane GameCell_25;
	public Pane GameCell_26;

	public Pane GameCell_30;
	public Pane GameCell_31;
	public Pane GameCell_32;
	public Pane GameCell_33;
	public Pane GameCell_34;
	public Pane GameCell_35;
	public Pane GameCell_36;

	public Pane GameCell_40;
	public Pane GameCell_41;
	public Pane GameCell_42;
	public Pane GameCell_43;
	public Pane GameCell_44;
	public Pane GameCell_45;
	public Pane GameCell_46;

	public Pane GameCell_50;
	public Pane GameCell_51;
	public Pane GameCell_52;
	public Pane GameCell_53;
	public Pane GameCell_54;
	public Pane GameCell_55;
	public Pane GameCell_56;

	// Default Class Constructor
	public ConnectFourController(Player player) {
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
		gameBoard = new ConnectFourBoard();

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
		Button[] ColumnButtons = { ColumnButton_0, ColumnButton_1, ColumnButton_2, ColumnButton_3, ColumnButton_4,
				ColumnButton_5, ColumnButton_6 };
		for (int columnIndex = 0; columnIndex < ColumnButtons.length; columnIndex++) {
			Button button = ColumnButtons[columnIndex];
			assignButtonListener(button, columnIndex);
		}
	}

	// Assign Button Listeners
	private void assignButtonListener(Button button, int column) {
		button.setOnMouseEntered(e -> addImageOnButton(button));
		button.setOnMouseExited(e -> removeImageOnButton(button));
		button.setOnAction(e -> handlePlayerMove(column));
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
		playerTurn = false;
		computerTurn = false;

		// Reset and Clear the Game Board
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
	private void handlePlayerMove(int columnToInsert) {
		// Check if it is Player's Turn and Game is NOT Over
		if (playerTurn && !gameOver) {

			// Check if Column is NOT Full
			if (!isColumnFull(columnToInsert)) {

				// Find the Row to Insert and Insert the Token
				int rowToInsert = findEmptyRow(columnToInsert);
				if (rowToInsert != -1) {
					insertToken(rowToInsert, columnToInsert, playerToken);

					// Check if Move is the Winning Move
					if (checkForWin(rowToInsert, columnToInsert, playerToken)) {
						handleWin("PLAYER"); // Winning Move!
					} else if (checkForDraw()) {
						handleWin("DRAW"); // Draw Move
					} else {
						startComputerTurn(); // Not a Winning Move, Start Computer's Turn
					}
				}
			} else {
				handleMessageLabel("Column is Full!");
			}
		}
	}

	// Set Up for Computer's Move
	private void startComputerTurn() {
		// Start Computer's Turn ONLY if Game is NOT Over
		if (!gameOver && !checkForDraw()) {

			// Set Computer's Turn
			handleMessageLabel("Computer's Turn!");
			playerTurn = false;
			computerTurn = true;

			// Delay the Computer's Move by 1 Second
			PauseTransition delay = new PauseTransition(Duration.seconds(1.0));
			delay.setOnFinished(event -> handleComputerMove());
			delay.play();
		}
	}

	// Computer's Turn to Place a Token
	private void handleComputerMove() {
		// Randomly Select a Position and Check if Position is Available
		int columnToInsert, rowToInsert;
		if (computerTurn) {
			do {
				// Select a Random Position
				Random random = new Random();
				columnToInsert = random.nextInt(COLUMNS);
			} while (isColumnFull(columnToInsert));

			// Set Token at the Randomly Selected Column
			rowToInsert = findEmptyRow(columnToInsert);
			insertToken(rowToInsert, columnToInsert, computerToken);

			// Check if Move is the Winning Move
			if (checkForWin(rowToInsert, columnToInsert, computerToken)) {
				handleWin("COMPUTER"); // Winning Move!
			} else if (checkForDraw()) {
				handleWin("DRAW"); // Draw Move
			} else {
				startPlayerTurn(); // Not a Winning Move, Start Player's Turn
			}
		}
	}

	// Check if Move is the Winning Move
	private boolean checkForWin(int row, int column, char token) {
		return gameBoard.checkForWin(row, column, token);
	}

	// Check for a Draw
	private boolean checkForDraw() {
		for (int col = 0; col < COLUMNS; col++) {
			if (!isColumnFull(col)) {
				return false; // Not a Draw: At Least One Column is Not Full
			}
		}
		return true; // A Draw: All Columns are Full
	}

	// Find the First Empty Row in the Selected Column
	private int findEmptyRow(int columnToInsert) {
		int rowToInsert = -1;
		for (int row = ROWS - 1; row >= 0; row--) {
			if (gameBoard.getToken(row, columnToInsert) == ' ') {
				rowToInsert = row;
				break;
			}
		}
		return rowToInsert;
	}

	// Check if Column to Insert is Full
	private boolean isColumnFull(int columnToInsert) {
		return gameBoard.getToken(0, columnToInsert) != ' ';
	}

	// Set Token on a Row & Column
	private void insertToken(int row, int column, char token) {
		gameBoard.setToken(row, column, token);
		setTokenImage(getGameCell(row, column), token);
	}

	// Get GameCell based on Row & Column
	private Pane getGameCell(int row, int column) {
		Pane[][] GameCells = {
				{ GameCell_00, GameCell_01, GameCell_02, GameCell_03, GameCell_04, GameCell_05, GameCell_06 },
				{ GameCell_10, GameCell_11, GameCell_12, GameCell_13, GameCell_14, GameCell_15, GameCell_16 },
				{ GameCell_20, GameCell_21, GameCell_22, GameCell_23, GameCell_24, GameCell_25, GameCell_26 },
				{ GameCell_30, GameCell_31, GameCell_32, GameCell_33, GameCell_34, GameCell_35, GameCell_36 },
				{ GameCell_40, GameCell_41, GameCell_42, GameCell_43, GameCell_44, GameCell_45, GameCell_46 },
				{ GameCell_50, GameCell_51, GameCell_52, GameCell_53, GameCell_54, GameCell_55, GameCell_56 } };
		return GameCells[row][column];
	}

	// Set GameCell Pane based on Token
	private void setTokenImage(Pane pane, char token) {
		// Set Image to Button based on Token
		if (token == 'X' && Token_X != null) {
			ImageView imageView = createImageView(Token_X, pane.getWidth() / 2, pane.getHeight() / 2);
			addImageToPane(pane, imageView);
		} else if (token == 'O' && Token_O != null) {
			ImageView imageView = createImageView(Token_O, pane.getWidth() / 2, pane.getHeight() / 2);
			addImageToPane(pane, imageView);
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

	// Add Arrow when Column Button is Hovered
	private void addImageOnButton(Button button) {
		if (ArrowImage != null) {
			ImageView imageView = createImageView(ArrowImage, button.getWidth() / 2, button.getHeight() / 2);
			button.setGraphic(imageView);
		}
	}

	// Add Arrow when Column Button is Hovered
	private void removeImageOnButton(Button button) {
		button.setGraphic(null);
	}

	// Get and Initialize Arrow Image
	private void initializeImages() {
		Token_X = getImage("X");
		Token_O = getImage("O");
		ArrowImage = getImage("ARROW");
	}

	// Clear All Tokens from GameCells
	private void removeAllTokenImages() {
		// Remove token images from all GameCells
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				Pane pane = getGameCell(row, col);
				pane.getChildren().removeIf(node -> node instanceof ImageView);
			}
		}
	}

	// Get Image Based on Marker
	private Image getImage(String imageName) {
		// Initialize Empty Location
		String mainLocation = System.getProperty("user.dir") + "\\resources\\images";
		String imageLocation = "";

		// Set Image Folder Location Based on Type
		if ("X".equalsIgnoreCase(imageName)) {
			imageLocation = mainLocation + "\\icons\\letter-x.png";
		} else if ("O".equalsIgnoreCase(imageName)) {
			imageLocation = mainLocation + "\\icons\\letter-o.png";
		} else if ("ARROW".equalsIgnoreCase(imageName)) {
			imageLocation = mainLocation + "\\icons\\arrow.png";
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

	// Add and Center the ImageView within the Pane
	private void addImageToPane(Pane pane, ImageView imageView) {
		imageView.layoutXProperty().bind(pane.widthProperty().subtract(imageView.fitWidthProperty()).divide(2));
		imageView.layoutYProperty().bind(pane.heightProperty().subtract(imageView.fitHeightProperty()).divide(2));
		pane.getChildren().add(imageView);
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