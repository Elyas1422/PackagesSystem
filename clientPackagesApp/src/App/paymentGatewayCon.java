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
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class paymentGatewayCon {

	@FXML
	private Text paymentTitle;

	@FXML
	private Button submitButton;
	public void getScene(Stage stage, String packageId,String price,User user) throws Exception {
		FXMLLoader fxmlloader =new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("paymentGatewayPage.fxml"));
		Parent root = fxmlloader.load();
		paymentGatewayCon containerCon= fxmlloader.getController();
		containerCon.setData(stage,packageId,price,user);
		Scene scene = new Scene(root);
		stage.setScene(scene);
	}
	public void setData( Stage stage,String packageId, String price,User user) {
		paymentTitle.setText(price);


		submitButton.setOnAction(e->{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");
				Statement stmt=con.createStatement();
				stmt.executeUpdate("UPDATE payment SET Confirmed=1 WHERE PackageNo='"+packageId+"'");
				userPackeagesPageCon x=new userPackeagesPageCon();
				x.getScene(stage, user);
			} catch (Exception e1) {
				userPackeagesPageCon x=new userPackeagesPageCon();
				try {
					x.getScene(stage, user);
				} catch (Exception e2) {}
			}
		});
	}

}
