package Application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class generateReportSceneController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	LocalDate date1=null;
	LocalDate date2=null;
	int fragile=0;
	int chemical=0;
	int liquid=0;
	String city="";
	String username="";
	String customerID="";
	int lost=0;
	int delayed = 0;
	int delivered = 0;
	int transit = 0;
	int dmg = 0;
	String fromDate="0/00/0000";
	String toDate="9/99/9999";
	
    @FXML
    private CheckBox transitCheck;
    @FXML
    private CheckBox dmgCheck;
	@FXML
	private Button backManage;
    @FXML
    private Button generateReport;
    @FXML
    private CheckBox delayCheck;
    @FXML
    private CheckBox deliCheck;
    @FXML
    private CheckBox lostCheck;
    @FXML
    private ComboBox<String> reportCbox;
    @FXML
    private ComboBox<String> customerCbox;
    @FXML
    private TextArea reportShow;
    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private CheckBox cCheck;
    @FXML
    private CheckBox fCheck;
    @FXML
    private CheckBox lCheck;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> city;
		ObservableList<String> customer;
		try {
			city = FXCollections.observableArrayList(javasql.getCities());
			reportCbox.getItems().addAll(city);
			customer = FXCollections.observableArrayList(javasql.getCustomerUsername());
			customerCbox.getItems().addAll(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println();;
		} 

		
	}
	public void switchScene(Parent root, Stage stage) throws IOException {
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	public void pressBackManage(MouseEvent event) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/Application/ManageScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		switchScene(root, stage);
	}
	

    @FXML
    void pressGenerate(MouseEvent event) throws Exception {
    	reportShow.setText(" ");
    	if(cCheck.isSelected())
    		chemical=1;
    	if(fCheck.isSelected())
    		fragile=1;
    	if(lCheck.isSelected())
    		liquid=1;
    	if(lostCheck.isSelected())
    		lost=1;
    	if(delayCheck.isSelected())
    		delayed=1;
    	if(deliCheck.isSelected())
    		delivered=1;
    	if(dmgCheck.isSelected())
    		dmg=1;
    	if(transitCheck.isSelected())
    		transit=1;
    	if(reportCbox.getValue()!=null)
    		city=reportCbox.getValue();
    	if(dateFrom.getValue()!=null) {
    		date1 = dateFrom.getValue();
    		fromDate  = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    	}
    	if(dateTo.getValue()!=null) {
    		date2 = dateTo.getValue();
    		toDate  = date2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));	
    	}

		username = customerCbox.getValue();
    	String list = javasql.generateReport(city, fragile, chemical, liquid, fromDate, toDate, username, delayed, lost, delivered, transit, dmg);
//    	String result = "Package ID: " +list.get(0)+"						"+"Value: "+list.get(15)+"\n";
//    	result+=("Package Weight: "+ list.get(1)+"					"+"Company fines: "+list.get(16)+"\n"+"Width x Height x Depth: "
//    			+ ""+list.get(2)+"x"+list.get(3)+"x"+list.get(4)+"		"+"insurance amount: "+list.get(17)+"\n"
//    			+ "Delivary Date: "+list.get(5)+"\n"+"Status: "+list.get(6)+"\n");
//    	if(list.get(7).equals("1"))
//    		result+="Package is fragile materils: "+list.get(8)+"\n";
//    	if(list.get(9).equals("1"))
//    		result+="Package is chemical risk type: "+list.get(10)+"\n";
//    	if(list.get(11).equals("1"))
//    		result+="Package is liquid volume: "+list.get(12)+"\n";
//    	result+="Package Destination ID: "+list.get(13)+"\n";
    	reportShow.setText(list);
    	chemical=0;
    	fragile=0;
    	liquid=0;
    	lost=0;
    	delayed=0;
    	delivered=0;
    	city="";
    	fromDate = "0/00/0000";
    	toDate = "9/99/9999";
    	dmg=0;
    	transit=0;
    }

    @FXML
    void pressReportBox(MouseEvent event) {
    	
    }
}
