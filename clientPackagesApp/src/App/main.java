package App;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Entities.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class main extends Application {
	@FXML
	private Text errorText;
	@FXML
	private Button loginButton;
	@FXML
	private TextField UsernameField;
	@FXML
	private PasswordField passwordField;
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage arg0) throws Exception {
		Text text = new Text();
		FXMLLoader fxmlloader =new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("loginPage.fxml"));
		Parent root = fxmlloader.load();
		main containerCon= fxmlloader.getController();
		containerCon.setData(arg0);
		Scene scene = new Scene(root);
		arg0.getIcons().add(new Image("file:Ship.png"));
		arg0.setTitle("Pakages System");
		arg0.setResizable(false);
		arg0.setScene(scene);
		arg0.show();
	}
	public void setData(Stage stage) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  
		Statement stmt=con.createStatement();
		errorText.setText("");
		loginButton.setOnAction(e->{
			String user= UsernameField.getText();
			String password=passwordField.getText();
			ResultSet rs;
			try {
				rs = stmt.executeQuery("SELECT username,password"
						+ " FROM customer NATURAL JOIN user  "
						+"WHERE username='"+user+"'");
				rs.next();
				if(password.equals(rs.getString("password"))) {
					mainPageCon mainpagecon= new mainPageCon();
					mainpagecon.getScene(stage,new User(user));
				}
				else
					errorText.setText("Password is inncorrect");

			} catch (Exception e1) {
				if (user=="")
					errorText.setText("Plese fill the Username and Password");
				else
					errorText.setText("User does not exist");
			} 
		});
	}
	public void getScene(Stage stage) throws Exception {
		FXMLLoader fxmlloader =new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("loginPage.fxml"));
		Parent root = fxmlloader.load();
		main containerCon= fxmlloader.getController();
		containerCon.setData(stage);
		Scene scene = new Scene(root);
		stage.setTitle("Pakages System");
		stage.setScene(scene);
	}
}
