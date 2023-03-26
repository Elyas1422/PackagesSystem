module SWE316Homework2 {
	exports Application;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires java.desktop;
	opens Application to javafx.graphics, javafx.fxml;
}