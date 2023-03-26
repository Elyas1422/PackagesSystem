package App;

import Entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class accountCon {

    @FXML
    private TextField addressField;

    @FXML
    private Button backButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button submitButton;
    public void getScene(Stage stage ,User user) throws Exception {
    	FXMLLoader fxmlloader =new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("accountPage.fxml"));
		Parent root = fxmlloader.load();
		accountCon containerCon= fxmlloader.getController();
		containerCon.setData(stage, user);
		Scene scene = new Scene(root);
		stage.setScene(scene);
    }
    public void setData(Stage stage,User user) {
    	fnameField.setText(user.getFname());
    	lnameField.setText(user.getLname());
    	emailField.setText(user.getEmail());
    	phoneField.setText(user.getPhone());
    	passwordField.setText(user.getPassword());
    	addressField.setText(user.getAddress());
    	submitButton.setOnAction(e->{
    		String password= passwordField.getText();
    		String fname=fnameField.getText();
    		String lname= lnameField.getText();
    		String email=emailField.getText();
    		String phone=phoneField.getText();
    		String address=addressField.getText();
    		user.setUser(password, fname, lname, email, phone, address);
    		mainPageCon x =new mainPageCon();
    		try {
				x.getScene(stage, user);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	});
    	backButton.setOnAction(e->{
    		mainPageCon x =new mainPageCon();
    		try {
				x.getScene(stage, user);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	});
    }
}