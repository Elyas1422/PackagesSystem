package App;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class locationContainerCon {

    @FXML
    private Text detailsTitle;

    @FXML
    private ImageView icon;

    @FXML
    private Text idTitle;
    public void setData(String packageId,String date, String city,String type) {
    	idTitle.setText("At "+city);
    	detailsTitle.setText("Date: "+date+ type+" id: "+ packageId);
    	if (type.toLowerCase().equals("building")) 
    		icon.setImage(new Image("file:building.png"));
    	else if (type.toLowerCase().equals("retail center")) 
    		icon.setImage(new Image("file:retailC.png"));
    	else if (type.toLowerCase().equals("transportation event"))
    		icon.setImage(new Image("file:truck-128.png"));
    }

}