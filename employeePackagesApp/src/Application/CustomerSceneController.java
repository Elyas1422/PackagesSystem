package Application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.Customer;
import Entities.User;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CustomerSceneController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String ID;
	private String address;
	private String userName;
	private String password;
	private String Fname;
	private String Lname;
	private String email;
	private String phone;
	FadeTransition t = new FadeTransition();

    @FXML
    private Label fail;
	@FXML
	private Button addCustomer;
	@FXML
	private Button backManage;
	@FXML
	private Button editCutomerPage;
	@FXML
	private Button deleteCustomerPage;
	//add customer sets

	@FXML
	private Button backCustomerManage;

	@FXML
	private TextField customerAddress;
	@FXML
	private TextField customerID;
	@FXML
	private TextField customerUser;

	@FXML
	private Button insertCustomer;
	@FXML
	private TextField customerEmail;

	@FXML
	private TextField customerFname;
	@FXML
	private TextField customerLname;

	@FXML
	private TextField customerPhone;

	@FXML
	private TextField customerPassword;
	//edit customer sets====================
	@FXML
	private Button editCustomer;
	@FXML
	private ComboBox<String> customerIDCbox;
	//delete customer sets=======

    @FXML
    private Button deleteCustomer;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void switchScene(Parent root, Stage stage) throws IOException {
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
    void showError() {
    	fail.setTextFill(Color.color(1, 0, 0));
	    t = new FadeTransition();
		t.setDuration(Duration.millis(3000));
		fail.setText("Invaild insertion");
		t.setNode(fail);
		t.setFromValue(1);
		t.setToValue(0);
		t.play();
    }
    void showCorrect() {
    	fail.setTextFill(Color.color(0, 1, 0));
	    t = new FadeTransition();
		t.setDuration(Duration.millis(3000));
		fail.setText("Inserted successfully");
		t.setNode(fail);
		t.setFromValue(1);
		t.setToValue(0);
		t.play();
    }

	@FXML
	void pressBackCustomerManage(MouseEvent event) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/Application/CustomerManage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage);
	}
	@FXML
	public void pressBackManage(MouseEvent event) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/Application/ManageScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage);
	}


	//add page settings
	@FXML
	public void pressAddPage(MouseEvent event) throws Exception{
		FXMLLoader fxmlloader = new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("/Application/addCustomerManage.fxml"));
		Parent root = fxmlloader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage); 
	}

	@FXML
	void pressAddCustomer(MouseEvent event) throws Exception{
		if(customerUser.getText().isEmpty() || customerPassword.getText().isEmpty() || customerEmail.getText().isEmpty() || customerFname.getText().isEmpty() 
				|| customerLname.getText().isEmpty() || customerPhone.getText().isEmpty() || customerID.getText().isEmpty() || customerAddress.getText().isEmpty() 
				|| customerID.getText().length()!=10) {
			showError();
		}else {
			userName = customerUser.getText();
			password = customerPassword.getText();
			email = customerEmail.getText();
			Fname = customerFname.getText();
			Lname = customerLname.getText();
			phone = customerPhone.getText();
			ID = customerID.getText();
			address = customerAddress.getText();
			User u = new User(userName, password, email, Fname, Lname, phone);
			Customer c = new Customer(userName, address, ID);
			if(javasql.addUser(u))
				if(javasql.addCustomer(c)) {
					customerUser.clear();
					customerPassword.clear();
					customerEmail.clear();
					customerFname.clear();
					customerLname.clear();
					customerPhone.clear();
					customerID.clear();
					customerAddress.clear();
				}
			showCorrect();
		}

	}
	//edit customer page=========================
	@FXML
	public void editPage(MouseEvent event) throws Exception{
		FXMLLoader fxmlloader = new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("/Application/editCustomerManage.fxml"));
		Parent root = fxmlloader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage); 
		CustomerSceneController container = fxmlloader.getController();
		container.setCusID();

	}
	public void setCusID() throws Exception{
    	ObservableList<String> list = FXCollections.observableArrayList(javasql.getCustomerIDs());
		customerIDCbox.getItems().addAll(list);
	}
	@FXML
	void pressEditCustomer(MouseEvent event) throws Exception{
		if(customerUser.getText().isEmpty() || customerPassword.getText().isEmpty() || customerEmail.getText().isEmpty() || customerFname.getText().isEmpty() 
				|| customerLname.getText().isEmpty() || customerPhone.getText().isEmpty() || customerID.getText().isEmpty() || customerAddress.getText().isEmpty() 
				|| customerID.getText().length()!=10) {
			showError();
		}else {
			userName = customerUser.getText();
			password = customerPassword.getText();
			email = customerEmail.getText();
			Fname = customerFname.getText();
			Lname = customerLname.getText();
			phone = customerPhone.getText();
			ID = customerID.getText();
			address = customerAddress.getText();
			User u = new User(userName, password, email, Fname, Lname, phone);
			Customer c = new Customer(userName, address, ID);
			
			if(javasql.editCustomer(customerIDCbox.getValue(), c))
				if(javasql.editUser(userName, u)) {
					customerUser.clear();
					customerPassword.clear();
					customerEmail.clear();
					customerFname.clear();
					customerLname.clear();
					customerPhone.clear();
					customerID.clear();
					customerAddress.clear();
					customerIDCbox.setValue(null);
			    	ObservableList<String> list = FXCollections.observableArrayList(javasql.getCustomerIDs());
					customerIDCbox.setItems(list);
					showCorrect();
				}
		}
		
	}

	@FXML
	void pressCbox(ActionEvent event) throws Exception{
		Customer c = javasql.getCustomers(customerIDCbox.getValue());
		if(c!=null) {
			userName = c.getUserName();
			password = c.getPassWord();
			email = c.getEmail();
			Fname = c.getfName();
			Lname = c.getlName();
			phone = c.getPhone();
			ID = c.getID();
			address = c.getAddress();
			customerUser.setText(userName);
			customerPassword.setText(password);
			customerEmail.setText(email);
			customerFname.setText(Fname);
			customerLname.setText(Lname);
			customerPhone.setText(phone);
			customerID.setText(ID);
			customerAddress.setText(address);	
		}
		
	}
//delete page======
	@FXML
	public void pressDeletePage(MouseEvent event) throws Exception{
		FXMLLoader fxmlloader = new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("/Application/deleteCustomerManage.fxml"));
		Parent root = fxmlloader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage); 
		CustomerSceneController container = fxmlloader.getController();
		container.setCusID();
		
	}
    @FXML
    void pressDeleteCustomer(MouseEvent event) throws Exception{
    	ID = customerIDCbox.getValue();
    	Customer c = javasql.getCustomers(ID);
    	javasql.deleteUser(c.getUserName());
    	javasql.deleteCustomer(ID);
    	customerIDCbox.setValue(null);
    	ObservableList<String> list = FXCollections.observableArrayList(javasql.getCustomerIDs());
		customerIDCbox.setItems(list);

    }


}
