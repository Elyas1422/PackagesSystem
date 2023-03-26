package App;
import java.io.IOException;
import java.sql.SQLException;
import Entities.User;
import Entities.Utill;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class mainPageCon {
	
    @FXML
    private Button accountButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button packagesButton;

    @FXML
    private Text welcomeMessage;
    
    public void getScene(Stage stage, User user) throws Exception {
    	FXMLLoader fxmlloader =new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("mainPage.fxml"));
		Parent root = fxmlloader.load();
		mainPageCon containerCon= fxmlloader.getController();
		containerCon.setData(stage,user);
		Scene scene = new Scene(root);
		stage.setScene(scene);
    }
    public void setData(Stage stage,User user) {
    	welcomeMessage.setText("Welcome "+user.getFname()+" "+user.getLname());
    	Utill.modifyButton(accountButton, "#000000", 22, 333, 55);
    	Utill.modifyButton(packagesButton, "#000000", 22, 333, 55);
    	
    	logoutButton.setOnAction(e->{
    		main loginPage= new main();
    		try {
				loginPage.getScene(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
    	packagesButton.setOnAction(e->{
    		userPackeagesPageCon packagesPage= new userPackeagesPageCon();
    		try {
    			
				packagesPage.getScene(stage, user);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	});
    	accountButton.setOnAction(e->{
    		accountCon accountPage= new accountCon();
    		try {
				accountPage.getScene(stage, user);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
    }
}
