package App;

import java.awt.Desktop;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Entities.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class packageContainerCon {

    @FXML
    private AnchorPane containerPane;
    
    @FXML
    private Text DestinationTitle;

    @FXML
    private Text DetailsText;

    @FXML
    private Text PackageidTitle;

    @FXML
    private Text PaymentTitle;

    @FXML
    private Text StatusTitle;

    @FXML
    private Button paymentButton;

    @FXML
    private Button trackButton;
    public  void setData(Stage stage,String packageId,User user) throws Exception {
    	//containerPane.setOnMouseEntered(e ->{containerPane.setScaleX(1.01); containerPane.setScaleY(1.01);});
    	//containerPane.setOnMouseExited(e ->{containerPane.setScaleX(1); containerPane.setScaleY(1);});
    	PackageidTitle.setText("Package: "+packageId); 
    	Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  
		Statement stmt=con.createStatement();
		ResultSet rs= stmt.executeQuery("SELECT Status FROM package WHERE PackageNo='"+packageId+"'");
		rs.next();
		StatusTitle.setText("Status: "+rs.getString("Status"));
		DestinationTitle.setText(getDestinationTitle(packageId,stmt));
		DetailsText.setText(getDetailsText(packageId, stmt));
    	paymentButton.setOnAction(e->{
    		paymentGatewayCon paymentpage= new paymentGatewayCon();
    		try {
				paymentpage.getScene(stage, packageId,getPayment(packageId, stmt,paymentButton),user);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
    	String price=getPayment(packageId, stmt,paymentButton);
		PaymentTitle.setText(price);
    	trackButton.setOnAction(e->{
    		trackCon x= new trackCon();
    		try {
				x.getScene(stage, user, packageId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
    }
    private String getDestinationTitle(String packageId,Statement stmt ) {
    	String destinationTitle ="Destination: ";
    	try {
    		ResultSet rs=stmt.executeQuery("SELECT City"
    				+ " FROM package a JOIN Location b ON a.DestinationId = b.LocationId"
					+ " WHERE packageNo= '"+packageId+"'");
    		rs.next();
    		destinationTitle+= rs.getString("City");
		} catch (SQLException e) {
			destinationTitle+= "Unkown";
		}
    	return destinationTitle;
    }
    private String getDetailsText(String packageId,Statement stmt) { 
    	String details="Details: ";
    	try {
			ResultSet rs=stmt.executeQuery("SELECT FragileFlag, Material,ChemicalFlag,Risktype,LiquidFlag,Volume"
					+ " FROM package"
					+ " WHERE packageNo= '"+packageId+"'");
			rs.next();
			if (rs.getInt("FragileFlag")==1) {
				details+= " Fragile ("+rs.getString("Material") +")";
			}
			if (rs.getInt("ChemicalFlag")==1) {
				details+= " Chemical ("+rs.getString("Risktype") +")";
			}
			if (rs.getInt("LiquidFlag")==1) {
				details+= " Liquid ("+rs.getString("Volume") +"mL)";
			}
			
		} catch (SQLException e) {
			details+="Unknown";
			e.printStackTrace();
		}
    	return details;
    }
    
    public String getPayment(String packageId,Statement stmt,Button paymentButton) throws Exception {
    	try {
			ResultSet rs=stmt.executeQuery("SELECT * From payment"+
    	" WHERE PackageNo= '"+packageId+"'");
			rs.next();
			float sum= rs.getFloat("Value")+rs.getFloat("Insurance")-rs.getFloat("Company Fines");
			if (rs.getInt("Confirmed")==1) {
				paymentButton.setDisable(true);
				paymentButton.setPrefWidth(110);
				paymentButton.setText("payment done");
			}
			else if (sum<=0) {
				//paymentButton.setDisable(true);
				paymentButton.setOnAction(e->{
					try {
					String subject=URLEncoder.encode("Refund Request", "UTF-8").replace("+", "%20");
					String body= "I'm Requesting a "+Math.abs(sum)+"SAR as refund for packageNo "+ packageId
							+"\n"+"My Bank IBAN: "+"[bank IBAN]";
					String bodyEncoded=URLEncoder.encode(body, "UTF-8").replace("+", "%20");
					String uriStr = String.format("mailto:%s?subject=%s&body=%s",
					            "paymentadmin@packageSysKFUPM.com", // use semicolon ";" for Outlook!
					            subject,
					            bodyEncoded);
					    
					    Desktop.getDesktop().browse(new URI(uriStr));}
					catch(Exception e1) {
						e1.printStackTrace();
					}
				});
				paymentButton.setPrefWidth(110);
				paymentButton.setText("request refund");
				return "Total Refund: "+ Math.abs(sum)+" SAR";
			}
			
			return "Total Payment: "+ sum+" SAR";
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    }
}
