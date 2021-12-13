package project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentsScene extends Scene {

	static VBox vBox = new VBox(); // Initializing the main pane

	static Stage primaryStage = Driver.primaryStage; // Making a variable for the primary stage initialized in the
														// Driver file

	static int indexOnStudentsPane = 0; // The index of the selected student

	// Control objects to show the student registered and not registered courses:
	static TextField text_ID;
	static ListView<String> list_Rcourses;
	static ComboBox<String> list_NRcourses;
	// ^ declaring the all above variables as static variables to use them in all
	// methods and handlers ^

	public StudentsScene() {

		super(vBox, 1024, 512); // using the constructor of the parent class (Scene)

		vBox.setPadding(new Insets(24, 24, 24, 24));

		HBox[] hBoxes = new HBox[4]; // Arrange control objects in horizontal lines

		Label[] labels = new Label[3]; // List of all used labels
		String[] labelsNames = { "Student ID", "Registered Courses", "Not Registered Courses" };

		// Initialize control objects that holds student information
		text_ID = new TextField(getStudentID(indexOnStudentsPane));
		list_Rcourses = new ListView<String>();
		list_NRcourses = new ComboBox<String>();

		// List first student information
		listRCourses(list_Rcourses, indexOnStudentsPane);
		listNRCourses(list_NRcourses, indexOnStudentsPane);

		// Put the control objects in a list so it all can be adjusted by index in a for
		// loop
		Control[] views = { text_ID, list_Rcourses, list_NRcourses };

		for (int i = 0; i < hBoxes.length; i++) {
			// Initialize hBoxes and align them
			hBoxes[i] = new HBox(12);
			hBoxes[i].setAlignment(Pos.CENTER_LEFT);

			if (i < 3) { // Make sure labels and views don't get index out of range
				// Initialize the label with its name, give it 1/3 of its hBox width and finally
				// add it to its hBox
				labels[i] = new Label(labelsNames[i]);
				labels[i].prefWidthProperty().bind(hBoxes[i].widthProperty().divide(3));
				hBoxes[i].getChildren().add(labels[i]);

				// Give the control objects 2/3 of its hBox and add it to its hBox
				views[i].prefWidthProperty().bind(hBoxes[i].widthProperty().divide(3).multiply(2));
				hBoxes[i].getChildren().add(views[i]);

			}
		}

		Button[] buttons = new Button[6]; // Making a list of buttons to initialize all buttons together
		String[] buttonNames = { "Back", "Pervious", "Next", "Register", "Drop", "Search" };
		int buttonCounter = 0; // number to go through the array of buttons using a loop
		Handler handler = new Handler();
		for (String name : buttonNames) { // loop used to initialize buttons and assign handlers to them

			buttons[buttonCounter] = new Button(name);
			buttons[buttonCounter].prefWidthProperty().bind(hBoxes[3].widthProperty().divide(6));
			buttons[buttonCounter].setOnAction(handler);
			buttonCounter++;
		}

		hBoxes[3].getChildren().addAll(buttons);

		vBox.getChildren().addAll(hBoxes);

		for (int i : new int[] { 0, 2, 3 }) // Give the first, third and fourth hBox 1/10 of the scene height
			hBoxes[i].prefHeightProperty().bind(this.heightProperty().divide(10));

		// Gives the second hBox 7/10 of the scene height
		hBoxes[1].prefHeightProperty().bind(this.heightProperty().divide(10).multiply(7));

	}

	public static String getStudentID(int index) { // Return student ID by its index in the students arrayList

		

		return CommonClass.getStudentList().get(index).getStudID();

	}

	public static int findStudentByID(String ID) { // Return student index in the students arrayList by its ID

		for (int i = 0; i < CommonClass.getStudentListSize(); i++) {

			if (CommonClass.getStudentList().get(i).getStudID().equals(ID)) {
				return i;
			}

		}

		return -1; // Returns -1 if given ID didn't match a student
	}

	public static void listRCourses(ListView<String> listView, int index) { // List the courses that the students
																			// registered in the listView

		

		// Clear list and add registered courses
		listView.getItems().clear(); 

		for (int i = 0; i < CommonClass.getStudentList().get(index).getCourses().size(); i++) {

			listView.getItems().add(CommonClass.getStudentList().get(index).getCourses().get(i).getCourseID());

		}

	}

	public static void listNRCourses(ComboBox<String> comboBox, int index) { // List the courses that the students
																			 // can registered in the comboBox

		// Clear comboBox and add not registered courses
		comboBox.getItems().clear();

		// Nested loops to check if the course is registered or not
		for (int i = 0; i < CommonClass.getCourseListSize(); i++) {
			boolean isNRcourse = true;
			for (int j = 0; j < CommonClass.getStudentList().get(index).getCourses().size(); j++) {
				if (CommonClass.getCourseList().get(i).getCourseID()
						.equals(CommonClass.getStudentList().get(index).getCourses().get(j).getCourseID()))
					isNRcourse = false;
			}
			if (isNRcourse) // If the course is not registered add it to the comboBox
				comboBox.getItems().add(CommonClass.getCourseList().get(i).getCourseID());

		}

	}

	public static void registerCourse(String stuID, ComboBox<String> comboBox) { // Register chosen course to the student
		
		String courseID = comboBox.getSelectionModel().getSelectedItem();
		if (courseID != null) {
			if (CommonClass.getCourseList().get(CommonClass.findCourseByID(courseID)).getAvailableSeats() > 0) {
				// Add course to student courses list
				CommonClass.getStudentList().get(findStudentByID(stuID)).getCourses()
						.add(CommonClass.getCourseList().get(CommonClass.findCourseByID(courseID)));
				// Decrease available seats by 1
				CommonClass.getCourseList().get(CommonClass.findCourseByID(courseID)).register();
			} else { // Show alert if the course is full
				CommonClass.showAlert("There is no avaliable seats");
			}

			
		} else { // Show alert if there was no chosen course
			CommonClass.showAlert("You must choose a course to register");
		}
	}

	public static void dropCourse(String stuID, ListView<String> listView) { // Register chosen course to the student

		String courseID = listView.getSelectionModel().getSelectedItem();
		if (courseID != null) {
			// Remove course to student courses list
			CommonClass.getStudentList().get(findStudentByID(stuID)).getCourses()
					.remove(CommonClass.getCourseList().get(CommonClass.findCourseByID(courseID)));
			// Increase available seats by 1
			CommonClass.getCourseList().get(CommonClass.findCourseByID(courseID)).drop();
			
		} else {// Show alert if there was no chosen course
			CommonClass.showAlert("You must choose a course to drop");
		}

	}

	class Handler implements EventHandler<ActionEvent> { //Inner class that creates a handler that all buttons use

		@Override
		public void handle(ActionEvent button) {

			if (((Button) button.getSource()).getText().equals("Next")) {
			//  increases the index on the student list by one and set to 0 if it is the last index
				if(indexOnStudentsPane == CommonClass.getStudentListSize() - 1)indexOnStudentsPane = 0;
				else indexOnStudentsPane++;
				text_ID.setText(getStudentID(indexOnStudentsPane));

			} else if (((Button) button.getSource()).getText().equals("Pervious")) {
			//  decreases the index on the student list by one and set to last index if it is 0
				if (indexOnStudentsPane == 0)
					indexOnStudentsPane = CommonClass.getStudentListSize() - 1;
				else
					indexOnStudentsPane--;
				text_ID.setText(getStudentID(indexOnStudentsPane));

			} else if (((Button) button.getSource()).getText().equals("Search")) {
			//  Tries to find the index on the courses listView
			// show the specified student provided in the ID data field and show alert when ID is wrong
				
				int index = findStudentByID(text_ID.getText());
				if (index >= 0) {
					indexOnStudentsPane = index;
				} else {
					CommonClass.showAlert("Wrong student ID");
				}

			} else if (((Button) button.getSource()).getText().equals("Drop")) {
				// Drop course for student
				dropCourse(text_ID.getText(), list_Rcourses);

			} else if (((Button) button.getSource()).getText().equals("Register")) {
				// Register course for student
				registerCourse(text_ID.getText(), list_NRcourses);

			} else if (((Button) button.getSource()).getText().equals("Back")) {
			//  Goes back to the main scene 
				primaryStage.setScene(Driver.mainScene);
				primaryStage.setTitle("Course Offering\\Main");

			}
			// Updated student information
			listRCourses(list_Rcourses, indexOnStudentsPane);
			listNRCourses(list_NRcourses, indexOnStudentsPane);

		}

	}

}