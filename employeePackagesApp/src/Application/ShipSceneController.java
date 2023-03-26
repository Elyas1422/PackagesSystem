package Application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.Packages;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ShipSceneController implements Initializable{

	private Stage stage;
	private Scene scene;
	private Parent root;
	private int fragile=0;
	private String matierial;
	private int chemical=0;
	private String riskType;
	private int liquid=0;
	private int volume=0;
	FadeTransition t = new FadeTransition();
	
    @FXML
    private Label fail;
	@FXML
    private Button backManage;
    @FXML
    private Button backShipManage;
    @FXML
    private TextField PackDepth;
    @FXML
    private ComboBox<String> idChoice;
    @FXML
    private CheckBox cCheck;
    @FXML
    private CheckBox fCheck;
    @FXML
    private CheckBox lCheck;
    @FXML
    private DatePicker packDate=null;
    @FXML
    private TextField packHeight;
    @FXML
    private Button packInsert;
    @FXML
    private TextField packMaterial;
    @FXML
    private TextField packNumber;
    @FXML
    private TextField packRisk;
    @FXML
    private TextField packStatus;
    @FXML
    private TextField packVolume;
    @FXML
    private TextField packWeight;
    @FXML
    private TextField packWidth;
    @FXML
    private Button addPackPage;
    //edit package page=========================================
    @FXML
    private Button editPack;
    @FXML
    private ComboBox<String> packagesCbox;
    @FXML
    private Button packEdit;
    //Delete package page========================================
    @FXML
    private Button deletePage;
	
    private String[] s = {"Sushi", "Tuna", "Potato"};
	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
    void pressBackManage(MouseEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/Application/ManageScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	switchScene(root, stage);
    }
	
    @FXML
    void pressBackShipManage(MouseEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/Application/ShipScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	switchScene(root, stage);
    }
    @FXML
    void pressAddPage(MouseEvent event) throws Exception {
    	FXMLLoader fxmlloader = new FXMLLoader();
    	fxmlloader.setLocation(getClass().getResource("/Application/addShipManage.fxml"));
    	Parent root = fxmlloader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	switchScene(root, stage); 
    	ShipSceneController container = fxmlloader.getController();
    	container.setDesID();

    	}
    public void setDesID() throws Exception{
    	ArrayList<String> list = javasql.getLocationIDs();
    	idChoice.getItems().addAll(list);
    }
    		

    @FXML
    void pressChemical(MouseEvent event) {
    	if(cCheck.isSelected())
    	packRisk.setEditable(true);
    	else
    		packRisk.setEditable(false);
    }

    @FXML
    void pressFragile(MouseEvent event) {
    	if(fCheck.isSelected())
    	packMaterial.setEditable(true);
    	else
    		packMaterial.setEditable(false);;
    }

    @FXML
    void pressLiquid(MouseEvent event) {
    	if(lCheck.isSelected())
    	packVolume.setEditable(true);
    	else
    		packVolume.setEditable(false);
    }
    @FXML
    void pressInsert(MouseEvent event) throws Exception{
		if(packNumber.getText().isEmpty() || packWeight.getText().isEmpty() || packHeight.getText().isEmpty() || (packNumber.getText().length()!=5)
		|| PackDepth.getText().isEmpty() || packDate.getValue()==null|| packStatus.getText().isEmpty() || idChoice.getValue().isEmpty()|| 
		(fCheck.isSelected() && packMaterial.getText().isEmpty()) || (cCheck.isSelected() && packRisk.getText().isEmpty()) || (lCheck.isSelected() && packVolume.getText().isEmpty())) {
			showError();
		}else {
			String packageNo = packNumber.getText();
			int weight = Integer.parseInt(packWeight.getText());
			int width = Integer.parseInt(packWidth.getText());
			int height = Integer.parseInt(packHeight.getText());
			int depth = Integer.parseInt(PackDepth.getText());
			LocalDate date = packDate.getValue();
			String deliveryDate  = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String status = packStatus.getText();
			if(fCheck.isSelected()) {
				fragile= 1;
				matierial= packMaterial.getText();
			}
			if(cCheck.isSelected()) {
				chemical= 1;
				riskType= packRisk.getText();
			}
			if(lCheck.isSelected()) {
				liquid= 1;
				volume= Integer.parseInt(packVolume.getText());
			}
			boolean insert = false;
			String destinationID = idChoice.getValue();
			try {
				Packages pack = new Packages(packageNo, weight, width, height, depth, deliveryDate, status, fragile, matierial, chemical, riskType, liquid, volume, destinationID);
				insert = javasql.addPack(pack);
		    	javasql.payment(pack);
			}catch(Exception e) {
				System.out.println("incorrect insertion");
			}
			if(insert) {
				showCorrect();
				packNumber.clear();
				packWeight.clear();
				packWidth.clear();
				packHeight.clear();
				PackDepth.clear();
				packStatus.clear();
				fCheck.setSelected(false);
				packMaterial.clear();
				cCheck.setSelected(false);
				packRisk.clear();
				lCheck.setSelected(false);
				packVolume.clear();
				idChoice.setValue(null);
				packDate.setValue(null);
				
			}else
				showError();

			
		}
			


    }

    //edit elements============================================================================
    @FXML
    void editPackPage(MouseEvent event) throws Exception{
    	FXMLLoader fxmlloader = new FXMLLoader();
    	fxmlloader.setLocation(getClass().getResource("/Application/editShipManage.fxml"));
    	Parent root = fxmlloader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	switchScene(root, stage); 
    	ShipSceneController container = fxmlloader.getController();
    	container.setpackID();
    	container.setDesID();
    }
    public void setpackID() throws Exception{
    	ObservableList<String> list = FXCollections.observableArrayList(javasql.getPackageIDs());
    	packagesCbox.getItems().addAll(list);
    }
    @FXML
    void pressChosePackage(ActionEvent event) throws Exception{
    	Packages pack = javasql.getPackage(packagesCbox.getValue());
    	if(pack!=null) {
    		packNumber.setText(pack.getPackageNo());;
    		packWeight.setText(pack.getWeight()+"");
    		packWidth.setText(pack.getWidth()+"");
    		packHeight.setText(pack.getHeight()+"");;
    		PackDepth.setText(pack.getDepth()+"");
    		packStatus.setText(pack.getStatus());
    		if(pack.getFragile()==1)
    			fCheck.setSelected(true);
    		else 
    			fCheck.setSelected(false);
    		packMaterial.setText(pack.getMatierial());
    		if(pack.getChemical()==1)
    			cCheck.setSelected(true);
    		else 
    			cCheck.setSelected(false);
    		packRisk.setText(pack.getRiskType());
    		if(pack.getLiquid()==1)
    			lCheck.setSelected(true);
    		else 
    			lCheck.setSelected(false);
    		packVolume.setText(pack.getVolume()+"");
    		idChoice.setValue(pack.getDestinationID()+"");
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    		LocalDate date = LocalDate.parse(pack.getDeliveryDate(), formatter);
    		packDate.setValue(date);	
    	}

    }

    @FXML
    void pressEdit(MouseEvent event) throws Exception {
		if(packNumber.getText().isEmpty() || packWeight.getText().isEmpty() || packHeight.getText().isEmpty() || (packNumber.getText().length()!=5)
		|| PackDepth.getText().isEmpty() || packDate.getValue()==null|| packStatus.getText().isEmpty() || idChoice.getValue().isEmpty()|| 
		(fCheck.isSelected() && packMaterial.getText().isEmpty()) || (cCheck.isSelected() && packRisk.getText().isEmpty()) || (lCheck.isSelected() && packVolume.getText().isEmpty())) {
			showError();
		}else {
			String packageNo = packNumber.getText();
			int weight = Integer.parseInt(packWeight.getText());
			int width = Integer.parseInt(packWidth.getText());
			int height = Integer.parseInt(packHeight.getText());
			int depth = Integer.parseInt(PackDepth.getText());
			LocalDate date = packDate.getValue();
			String deliveryDate  = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String status = packStatus.getText();
			if(fCheck.isSelected()) {
				fragile= 1;
				matierial= packMaterial.getText();
			}
			if(cCheck.isSelected()) {
				chemical= 1;
				riskType= packRisk.getText();
			}
			if(lCheck.isSelected()) {
				liquid= 1;
				volume= Integer.parseInt(packVolume.getText());
			}
			boolean insert = false;
			String destinationID = idChoice.getValue();
			try {
				Packages pack = new Packages(packageNo, weight, width, height, depth, deliveryDate, status, fragile, matierial, chemical, riskType, liquid, volume, destinationID);
				insert = javasql.editPackage(packagesCbox.getValue(), pack);
		    	javasql.paymentEdit(pack);
			}catch(Exception e) {
				System.out.println("Error at edit");
			}
			if(insert) {
				packNumber.clear();
				packWeight.clear();
				packWidth.clear();
				packHeight.clear();
				PackDepth.clear();
				packStatus.clear();
				fCheck.setSelected(false);
				packMaterial.clear();
				cCheck.setSelected(false);
				packRisk.clear();
				lCheck.setSelected(false);
				packVolume.clear();
				idChoice.setValue(null);
				packDate.setValue(null);
		    	FXMLLoader fxmlloader = new FXMLLoader();
		    	fxmlloader.setLocation(getClass().getResource("/Application/editShipManage.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		    	Parent root = fxmlloader.load();
		    	ShipSceneController container = fxmlloader.getController();
		    	ObservableList<String> list = FXCollections.observableArrayList(javasql.getPackageIDs());
		    	packagesCbox.setValue(null);
		    	packagesCbox.setItems(list);
				showCorrect();
			}else {
				showError();
			}
		}
		
    }
	public void updateScene(Parent root, Stage stage) throws IOException {
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    	FXMLLoader fxmlloader = new FXMLLoader();
    	fxmlloader.setLocation(getClass().getResource("/Application/editShipManage.fxml"));
    	
	}
    
    //Delete elements
    @FXML
    void pressDeletePage(MouseEvent event) throws Exception{
    	FXMLLoader fxmlloader = new FXMLLoader();
    	fxmlloader.setLocation(getClass().getResource("/Application/deleteShipManage.fxml"));
    	Parent root = fxmlloader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	switchScene(root, stage); 
    	ShipSceneController container = fxmlloader.getController();
    	container.setpackID();
    }
    @FXML
    void pressDelete(MouseEvent event) throws Exception{
    	javasql.deletePackage(packagesCbox.getValue());
    }

}
