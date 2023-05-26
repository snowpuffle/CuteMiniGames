package views;

import controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;

public class ViewFactory {

	// Show Tic Tac Toe Game
	public void showDashboardFrame() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/Dashboard.fxml"));
		DashboardController DashboardController = new DashboardController();
		fxmlLoader.setController(DashboardController);
		createStage(fxmlLoader, "Main Dashboard");
	}

	// Show Tic-Tac-Toe Game
	public void showTicTacToe(Player player) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/TicTacToe.fxml"));
		TicTacToeController TicTacToeController = new TicTacToeController(player);
		fxmlLoader.setController(TicTacToeController);
		createStage(fxmlLoader, "Tic-Tac-Toe Game");
	}

	// Show Connect-Four Game
	public void showConnectFour(Player player) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/ConnectFour.fxml"));
		ConnectFourController ConnectFourController = new ConnectFourController(player);
		fxmlLoader.setController(ConnectFourController);
		createStage(fxmlLoader, "Connect-Four Game");
	}

	// Show Sudoku Game
	public void showSudokuGame() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/SudokuGame.fxml"));
		SudokuController SudokuController = new SudokuController();
		fxmlLoader.setController(SudokuController);
		createStage(fxmlLoader, "Sudoku Game");
	}

	// Generic: Create Stage
	private void createStage(FXMLLoader fxmlLoader, String stageTitle) {
		Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Cute Mini Games");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Issues with Creating Stage " + stageTitle + "!");
		}
	}

	// Generic: Close Stage
	public void closeStage(Stage stage) {
		stage.close();
	}
}