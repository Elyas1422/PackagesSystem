module clientPackagesApp {
	exports App;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires java.desktop;
	opens App to javafx.graphics, javafx.fxml;
}