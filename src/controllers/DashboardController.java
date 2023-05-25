package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.*;

public class DashboardController implements Initializable {
	// Dashboard Attributes
	public Label MessageLabel;

	// User Attributes
	public TextField NameField;
	public Button TokenButton_O;
	public Button TokenButton_X;
	private char token;

	// Game Attributes
	public Button TicTacToeButton;
	public Button ConnectFourButton;
	public Button SudokuGameButton;

	// Default Class Constructor
	public DashboardController() {
		this.token = ' ';
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize OnClick Button Actions
		addListeners();
	}

	// Add Listeners to Buttons on Frame
	private void addListeners() {
		// Add Listener to O Token Button
		TokenButton_O.setOnAction(event -> {
			this.token = 'O';
			TokenButton_O.getStyleClass().add("selected-token"); // Apply CSS Class to Highlight the Button
			TokenButton_X.getStyleClass().remove("selected-token"); // Remove CSS Class from the Other Button
		});

		// Add Listener to X Token Button
		TokenButton_X.setOnAction(event -> {
			this.token = 'X';
			TokenButton_X.getStyleClass().add("selected-token"); // Apply CSS Class to Highlight the Button
			TokenButton_O.getStyleClass().remove("selected-token"); // Remove CSS Class from the Other Button
		});

		// "Tic-Tac-Toe" Game Button is Clicked
		TicTacToeButton.setOnAction(event -> handleTicTacToe());
		ConnectFourButton.setOnAction(event -> handleConnectFour());
	}

	// Event: "Tic-Tac-Toe" Button is Clicked
	private void handleTicTacToe() {
		// Get Name from Name Field
		String name = NameField.getText().trim();

		// Validate Sufficient Fields are Filled in
		if (validateName(name) && validateToken()) {

			// Create Player and Display Game Frame
			Player player = new Player(name, token);
			closeCurrentWindow();
			Model.getInstance().getViewFactory().showTicTacToe(player);
		}
	}

	// Event: "Tic-Tac-Toe" Button is Clicked
	private void handleConnectFour() {
		// Get Name from Name Field
		String name = NameField.getText().trim();

		// Validate Sufficient Fields are Filled in
		if (validateName(name) && validateToken()) {

			// Create Player and Display Game Frame
			Player player = new Player(name, token);
			closeCurrentWindow();
			Model.getInstance().getViewFactory().showConnectFour(player);
		}
	}

	// Validate Player's Name
	private boolean validateName(String name) {
		if (name.isEmpty() || name.isBlank()) {
			handleMessageLabel("Please Enter Player's Name!", false);
			return false;
		}
		return true;
	}

	// Validate Player's Token
	private boolean validateToken() {
		if (token == ' ') {
			handleMessageLabel("Please Select a Token!", false);
			return false;
		}
		return true;
	}

	// Handle Error
	private void handleMessageLabel(String message, boolean success) {
		if (success) {
			MessageLabel.setText("SUCCESS: " + message);
			MessageLabel.setTextFill(Color.GREEN); // Set Text Color to Green for Success
		} else {
			MessageLabel.setText("ERROR: " + message);
			MessageLabel.setTextFill(Color.RED); // Set Text Color to Red for Error
		}
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) MessageLabel.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}