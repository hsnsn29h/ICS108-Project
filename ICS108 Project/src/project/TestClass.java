package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Just a test class to check whether the data is read or not.

public class TestClass extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {


		Scene sceneStudents = createStudentsScene();
		
		
		primaryStage.setScene(sceneStudents);
		primaryStage.show();
		
	}
	
	
	 
	public static void main(String[] args) {
		CommonClass.loadBinaryData();
		
		launch(args);
		
	}
	
  
	
	
	
	
	public static Scene createStudentsScene() {
		
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(24,24,24,24));
		
		HBox[] hBoxes = new HBox[4]; 

		Label[] labels = new Label[3];
		String[] labelsNames = {"Student ID","Registered Courses","Not Registered Courses"};
		
		TextField text_ID = new TextField();
		ListView<String> list_Rcourses = new ListView<String>();
		ComboBox<String> list_NRcourses = new ComboBox<String>();
		
		Control[] views = {text_ID, list_Rcourses, list_NRcourses};
		
			for(int i = 0; i < hBoxes.length; i++) {
				hBoxes[i] = new HBox(12);
				hBoxes[i].setAlignment(Pos.CENTER_LEFT);
				
				if(i < 3) {
				labels[i] = new Label(labelsNames[i]);
				labels[i].prefWidthProperty().bind(hBoxes[i].widthProperty().divide(3));
				hBoxes[i].getChildren().add(labels[i]);
				
				views[i].prefWidthProperty().bind(hBoxes[i].widthProperty().divide(3).multiply(2));
				hBoxes[i].getChildren().add(views[i]);

				}
		}

		
		Button[] buttons = new Button[6];
		String[] buttonNames = {"Back", "Pervious", "Next", "Register", "Drop", "Search"};
		int buttonCounter = 0;
			for(String name : buttonNames) {
			
				buttons[buttonCounter] = new Button(name);
				buttons[buttonCounter].prefWidthProperty().bind(hBoxes[3].widthProperty().divide(6));
				buttonCounter++;	
				}

		
		hBoxes[3].getChildren().addAll(buttons);
		
		vBox.getChildren().addAll(hBoxes);
		
		Scene scene = new Scene(vBox, 512, 512);
		
		for(int i : new int[] {0,2,3}) hBoxes[i].prefHeightProperty().bind(scene.heightProperty().divide(10));
		hBoxes[1].prefHeightProperty().bind(scene.heightProperty().divide(10).multiply(7));

		
		return scene;
		
		}







	
	
	

}
