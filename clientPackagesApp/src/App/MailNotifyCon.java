package App;

import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MailNotifyCon{

	@FXML
	private Button notifyButton;

	@FXML
	private ListView<String> packagesListview;

	@FXML
	private TextField searchBar;

	@FXML
	private Button searchButton;
	
	public Scene getScene() throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader fxmlloader =new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("MailNotifyPage.fxml"));
		Parent root = fxmlloader.load();
		MailNotifyCon containerCon= fxmlloader.getController();
		containerCon.setData();
		Scene scene = new Scene(root);
		return scene;
	}
	private void setData() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  
			Statement stmt=con.createStatement();
			String query= "SELECT PackageNo, status FROM package";
			//	+ " WHERE  PackageNo LIKE  '%"+searchBar.getText()+"%'";
			ResultSet rs= stmt.executeQuery(query);
			while(rs.next())
				packagesListview.getItems().add(rs.getString(1)+" Status: "+rs.getString(2));
			searchButton.setOnAction(e->{
				try {
					String query1= "SELECT PackageNo, status FROM package"
							+ " WHERE  PackageNo LIKE  '%"+searchBar.getText()+"%'";
					ResultSet rs1= stmt.executeQuery(query1);
					packagesListview.getItems().clear();
					while(rs1.next()) {
						packagesListview.getItems().add(rs1.getString(1)+" Status: "+rs1.getString(2));
					}
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			notifyButton.setOnAction(e->{
				ResultSet rsCustomer;
				try {
					String selectedPackage= packagesListview.getSelectionModel().getSelectedItem().substring(0,6);
					String query1= "SELECT status,Email,deliveryDate "
							+"  FROM package a Join ship b Join user c on a.packageNo= b.packageNo And b.To_Username= c.username "
							+" WHERE a.packageNo="+ selectedPackage;
					rsCustomer= stmt.executeQuery(query1);
					rsCustomer.next();
					String status=rsCustomer.getString(1);
					String email= rsCustomer.getString(2);
					String deliveryDate= rsCustomer.getString(3);
					String subject=URLEncoder.encode("Your Delivery Update: "+status, "UTF-8").replace("+", "%20");
					String caseOfPackage=".";
					if (status.equals("transit")) 
						caseOfPackage= " Your final delivery date is "+ deliveryDate+".";
					else if (status.equals("lost")||status.equals("damaged"))
						caseOfPackage= " You can now request the refund from the application.";
					String body="This is an email to notify you that your package number "+selectedPackage+ 
							" status has been changed to "+status+caseOfPackage;
					String bodyEncoded=URLEncoder.encode(body, "UTF-8").replace("+", "%20");
					String uriStr = String.format("mailto:%s?subject=%s&body=%s",
							email, // use semicolon ";" for Outlook!
							subject,
							bodyEncoded);
					Desktop.getDesktop().browse(new URI(uriStr));}
				catch(Exception e1) {
					e1.printStackTrace();
				}
			});
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
