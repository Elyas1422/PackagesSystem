package App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class trackCon {

    @FXML
    private Button backButton;

    @FXML
    private VBox gridconS;

    @FXML
    private Text packageIdTitle;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Text totalTitle;
    public void getScene(Stage stage, User user,String packageId) throws Exception {
    	FXMLLoader fxmlloader =new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("trackPage.fxml"));
		Parent root = fxmlloader.load();
		trackCon containerCon= fxmlloader.getController();
		containerCon.setData(stage,user,packageId);
		Scene scene = new Scene(root);
		stage.setScene(scene);
    }
	public void setData(Stage stage,User user,String packageId) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");
		Statement stmt=con.createStatement();
		
		ResultSet rs= stmt.executeQuery("SELECT *"
				+ " from Location NATURAL JOIN  history "
				+ " WHERE PakageNo='"+packageId+"'"
				+" ORDER BY Time_Stamp");
		int row =0;
		while (rs.next()) {
			row++;
			FXMLLoader fxmlloader =new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("locationContainer.fxml"));
			AnchorPane anchorPane = fxmlloader.load();
			locationContainerCon containerCon1= fxmlloader.getController();
			containerCon1.setData(rs.getString("LocationId"),rs.getString("Time_Stamp"),rs.getString("city"),rs.getString("LocationType"));
			gridconS.getChildren().add(anchorPane);
			gridconS.setSpacing(10);
		}
		totalTitle.setText("Total Stations: "+ row);
		packageIdTitle.setText("Tracking Package Number: "+packageId);
		backButton.setOnAction(e->{
			userPackeagesPageCon x=new userPackeagesPageCon();
			try {
				x.getScene(stage, user);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
	}

}
