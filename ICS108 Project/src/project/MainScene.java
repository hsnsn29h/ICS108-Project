package project;

import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainScene extends Scene {

	static BorderPane mainPane = new BorderPane(); //Initializing the main pane 

	static Stage primaryStage = Driver.primaryStage; //Making a variable for the primary stage initialized in the Driver file

	public MainScene() {

		super(mainPane, 1024, 512); //using the constructor of the parent class (Scene)

		mainPane.setPadding(new Insets(24, 24, 24, 24));

		Label text = new Label("Registration System"); // Big background text
		text.setFont(Font.font(48));

		HBox hBox = new HBox(12); // HBox that holds all buttons in a horizontal way
		hBox.setAlignment(Pos.CENTER);


		Button[] buttons = new Button[3]; // Making a list of buttons to initialize all buttons together
		String[] buttonsNames = { "View Courses", "View students Details", "Save" };

		for (int i = 0; i < buttons.length; i++) { // loop used to initialize buttons and assign handlers to them

			buttons[i] = new Button(buttonsNames[i]);
			buttons[i].setOnAction(new Handler());
		}

		hBox.getChildren().addAll(buttons);
		
		// Add the Control objects and arrange them
		mainPane.setCenter(text);
		mainPane.setBottom(hBox);

	}

	class Handler implements EventHandler<ActionEvent> { //Inner class that creates a handler that all buttons use

		@Override
		public void handle(ActionEvent button) {

			if (((Button) button.getSource()).getText().equals("View students Details")) {
			//  Goes to the Students scene 
				primaryStage.setScene(Driver.studentsScene);
				primaryStage.setTitle("Course Offering\\Students");

			} else if (((Button) button.getSource()).getText().equals("View Courses")) {
			//  Goes to the Courses scene 
				primaryStage.setScene(Driver.coursesScene);
				primaryStage.setTitle("Course Offering\\Courses");

			} else if (((Button) button.getSource()).getText().equals("Save")) {
			//  Saves the modifications on data
				CommonClass.save();
				CommonClass.showAlert("Data saved successfully");
			}

		}

	}
}