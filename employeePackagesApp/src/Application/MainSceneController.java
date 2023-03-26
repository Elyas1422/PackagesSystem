package Application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.User;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainSceneController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private AnchorPane pane;
    @FXML
    private Label fail;
	@FXML
	private TextArea textLabeling;
	@FXML
	private Button enter;
	@FXML
	private PasswordField passInfo;
	@FXML
	private Label passLabel;
	@FXML
	private Label title;
	@FXML
	private TextField userInfo;
	@FXML
	private Label userLabel;
	@FXML
	private Button logout;
	@FXML
	private Button manageCustomer;
	@FXML
	private Button manageShip;
	@FXML
	private Button backManage;
	@FXML
	private Button backShipManage;
    @FXML
    private Button sendNotfi;
	//report page
	@FXML
	private Button generateReport;


	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void switchScene(Parent root, Stage stage) throws IOException {
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	void pressed(MouseEvent event) throws ClassNotFoundException, SQLException, IOException {
		String userName = userInfo.getText();
		String pass = passInfo.getText();
		
		FadeTransition t = new FadeTransition();
		t.setDuration(Duration.millis(800));


		if(javasql.checkLogin(userName, pass)) {
			Parent root = FXMLLoader.load(getClass().getResource("/Application/ManageScene.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();

			t.setNode(pane);
			t.setFromValue(1);
			t.setToValue(0);
			t.setOnFinished(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					try {
						switchScene(root, stage);
					} catch (IOException e) {
					}
				}
			});
			t.play();
		}else {
			t.setDuration(Duration.millis(3000));
			fail.setText("Invaild login");
			t.setNode(fail);
			t.setFromValue(1);
			t.setToValue(0);
			t.play();
		}
	}
	@FXML
	void pressLogout(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/Application/MainScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage);
	}
    @FXML
    void notfiPage(MouseEvent event) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/Application/MailNotifyPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage);
    }
	@FXML
	void pressShipManage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/Application/ShipScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage);
	}
	@FXML
	void pressBackManage(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/Application/ManageScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage);
	}
	@FXML
	void pressBackShipManage(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/Application/ManageScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage);
	}
	@FXML
	void manageCustomerPage(MouseEvent event) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/Application/CustomerManage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage);
	}
	//generate report page============================
	@FXML
	void generateReportPage(MouseEvent event) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/Application/generateReportScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage);
	}



}
