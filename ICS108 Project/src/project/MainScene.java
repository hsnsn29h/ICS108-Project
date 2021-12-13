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

	static BorderPane mainPane = new BorderPane(); 

	static Stage primaryStage = Driver.primaryStage; 

	public MainScene() {

		super(mainPane, 1024, 512);

		mainPane.setPadding(new Insets(24, 24, 24, 24));

		Label text = new Label("Registration System");
		text.setFont(Font.font(48));

		HBox hBox = new HBox(12);
		hBox.setAlignment(Pos.CENTER);

		mainPane.setCenter(text);
		mainPane.setBottom(hBox);

		Button[] buttons = new Button[3];
		String[] buttonsNames = { "View Courses", "View students Details", "Save" };

		for (int i = 0; i < buttons.length; i++) {

			buttons[i] = new Button(buttonsNames[i]);
			buttons[i].setOnAction(new Handler());
		}

		hBox.getChildren().addAll(buttons);

	}

	class Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent button) {

			if (((Button) button.getSource()).getText().equals("View students Details")) {

				primaryStage.setScene(Driver.studentsScene);
				primaryStage.setTitle("Course Offering\\Students");

			} else if (((Button) button.getSource()).getText().equals("View Courses")) {

				primaryStage.setScene(Driver.coursesScene);
				primaryStage.setTitle("Course Offering\\Courses");

			} else if (((Button) button.getSource()).getText().equals("Save")) {

				CommonClass.save();

			}

		}

	}
}