module CuteMiniGames {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;

	opens gamesystem to javafx.graphics, javafx.fxml;
	opens controllers to javafx.graphics, javafx.fxml;
}
