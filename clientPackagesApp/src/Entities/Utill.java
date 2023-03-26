package Entities;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class Utill {
	public static void modifyButton (Button button, String colorPressed,int textSize,int wedith , int height) {
		Font buttonFont = new Font(textSize);
		button.setPrefSize(wedith,height); 
		button.setFont(buttonFont); //Setting size and font for the button.
		button.setTextFill(javafx.scene.paint.Color.WHITE); //make the text inside the button white.
		button.setOnMouseEntered(e ->{button.setScaleX(1.1); button.setScaleY(1.1);});
		button.setOnMouseExited(e ->{button.setScaleX(1); button.setScaleY(1);});
	}
	public static void modifyButton (Button button, String colorPressed,int textSize) {
		Font buttonFont = new Font(textSize);
		button.setFont(buttonFont); //Setting size and font for the button.
		button.setTextFill(javafx.scene.paint.Color.WHITE); //make the text inside the button white.
		button.setOnMouseEntered(e ->{button.setScaleX(1.1); button.setScaleY(1.1);});
		button.setOnMouseExited(e ->{button.setScaleX(1); button.setScaleY(1);});
	}
	


}
