package App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import Entities.User;
import Entities.Utill;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class userPackeagesPageCon {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private Text totalTitle;
    @FXML
    private VBox gridconS;
    @FXML
    private CheckBox fromCBox;
    @FXML
    private CheckBox toCbox;
    @FXML
    private Button backButton;

	public void getScene(Stage stage, User user) throws Exception {
    	FXMLLoader fxmlloader =new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("userPackagespage.fxml"));
		Parent root = fxmlloader.load();
		userPackeagesPageCon containerCon= fxmlloader.getController();
		containerCon.setData(stage,user);
		Scene scene = new Scene(root);
		stage.setScene(scene);
    }
    public void setData(Stage stage,User user) throws Exception {
    	backButton.setOnAction(e->{
    		mainPageCon x= new mainPageCon();
    		try {
				x.getScene(stage, user);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
    	Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  
		Statement stmt=con.createStatement();
		ResultSet rs= stmt.executeQuery("SELECT PackageNo FROM ship WHERE From_Username='"+
		user.getUsername()+ "' or To_Username='"+user.getUsername()+"'");
		int row=0;
    	while (rs.next()) {
    		row++;
			FXMLLoader fxmlloader =new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("packageContainer.fxml"));
			AnchorPane anchorPane = fxmlloader.load();
			packageContainerCon containerCon1= fxmlloader.getController();
			containerCon1.setData(stage, rs.getString("PackageNo"),user);
			gridconS.getChildren().add(anchorPane);
			gridconS.setSpacing(15);
			gridconS.setAlignment(Pos.CENTER);
			
		}
    	totalTitle.setText("Total Packages: "+row);
    	EventHandler<ActionEvent> handler = e->{
    		String id= searchBar.getText();
    		gridconS.getChildren().clear();
    		int frow=0;
    		ResultSet fillteredRs;
    		String query= "SELECT PackageNo FROM ship"
					+ " WHERE  PackageNo LIKE  '%"+id+"%' AND (From_Username='"
					+user.getUsername()+ "' or To_Username='"+user.getUsername()+"')";
    				
    		if (fromCBox.isSelected()&&!toCbox.isSelected()) {
    			query= "SELECT PackageNo FROM ship"
    					+ " WHERE  PackageNo LIKE  '%"+id+"%' AND From_Username='"
    					+user.getUsername()+ "'";
    		}
    		else if (!fromCBox.isSelected()&&toCbox.isSelected()) {
    			query= "SELECT PackageNo FROM ship"
    					+ " WHERE  PackageNo LIKE  '%"+id+"%' AND To_Username='"
    					+user.getUsername()+ "'";
    		}
    		try {
				fillteredRs= stmt.executeQuery(query);
				
		    	while (fillteredRs.next()) {
		    		frow++;
					FXMLLoader fxmlloader =new FXMLLoader();
					fxmlloader.setLocation(getClass().getResource("packageContainer.fxml"));
					AnchorPane anchorPane = fxmlloader.load();
					packageContainerCon containerCon1= fxmlloader.getController();
					containerCon1.setData(stage, fillteredRs.getString("PackageNo"),user);
					gridconS.getChildren().add(anchorPane);
				}
		    	totalTitle.setText("Total Packages: "+frow);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	};
    	searchButton.setOnAction(handler);
    	fromCBox.setOnAction(handler);
    	toCbox.setOnAction(handler);
    }
}