package project;

import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CoursesScene extends Scene { 

	static BorderPane mainPane = new BorderPane(); //Initializing the main pane 

	static Stage primaryStage = Driver.primaryStage; //Making a variable for the primary stage initialized in the Driver file

	// Control objects to show the course information:
	
	static TextField course_ID; 
	static TextField course_Name; 
	static TextField course_Days; 
	static TextField course_Location; 
	static TextField course_Time; 
	static Button course_Status; 
	static Label Course_Seats; 
	ListView<String> list_Courses; 
	ListView<String> list_RStudents; 
	
	static int indexOnCoursesPane = 0; // The index of the selected course on the courses ListView
	
	// ^ declaring the all above variables as static variables to use them in all methods and handlers ^
	
	public CoursesScene() { 

		super(mainPane, 1024, 512); //using the constructor of the parent class (Scene)

		mainPane.setPadding(new Insets(24, 24, 24, 24)); 
		
		GridPane gridpane = new GridPane(); // Making a gridPane to hold all courses information

		course_ID = new TextField();
		course_Name = new TextField();
		course_Days = new TextField();
		course_Location = new TextField();
		course_Time = new TextField();
		course_Status = new Button("Open");
		Course_Seats = new Label("There are   registered in ");
		// ^ Initializing all data fields ^
		
		VBox vboxTextFields = new VBox(5); // Making a VBox to hold all data fields in a vertical order
		vboxTextFields.getChildren().addAll(course_ID, course_Name, course_Days, course_Location, course_Time,
				course_Status); 
		vboxTextFields.setPrefWidth(260);
		
		String[] labelNames = { "ID", "Name", "Days", "Location", "Time", "Status" }; 
		Label[] labels = new Label[6];
		// ^^ Making Labels array + Label Names for each data field

		int labelCounter = 0; // number to go through the list of labels

		for (String name : labelNames) {
			labels[labelCounter] = new Label(name); // Initializing each label individually through the loop
			labelCounter++; 

		}
		
		VBox vboxLabels = new VBox(15); // Making a VBox to hold the labels of the data fields
		vboxLabels.getChildren().addAll(labels); 

		gridpane.addColumn(0, vboxLabels); // Adding the VBox for the labels of the data field on the left of the gridPane
		gridpane.addColumn(2, vboxTextFields); // Adding the VBox for the data fields on the right of the gridPane
		gridpane.setAlignment(Pos.CENTER); // Aligning the gridPane to the center

		Button[] buttons = new Button[4]; // Making a list of buttons to initialize all buttons together
		String[] buttonNames = { "Back", "Pervious", "Next", "Search" }; 
		int buttonCounter = 0; // number to go through the array of buttons using a loop
		HBox hbox = new HBox(5); // HBox that holds all buttons in a horizontal way
		Handler handler = new Handler(); 

		for (String name : buttonNames) { // loop used to initialize buttons and assign handlers to them

			buttons[buttonCounter] = new Button(name);
			buttons[buttonCounter].prefWidthProperty().bind(mainPane.widthProperty().divide(12));
			buttons[buttonCounter].setOnAction(handler);

			buttonCounter++;

		}

		list_Courses = new ListView<String>(); // Initializing the listView that holds all courses
		listAllCourses(list_Courses, indexOnCoursesPane); // Adding all courses to the listView

		list_RStudents = new ListView<String>(); // Initializing a listView that holds all students registered in a selected course
		listRStudents(list_RStudents, indexOnCoursesPane); // Adding all those students to the list

		listData(); // A method that loads all data into the data fields (text fields/labels/buttons)

		list_Courses.getSelectionModel().selectedItemProperty().addListener(e -> {
			// This lambda expression makes it so that whenever you choose an item on the listView, the index on it will be changed
			// and all data fields will be updated as well to the new index, you can also use arrows to select a course on the listView 
			indexOnCoursesPane = list_Courses.getSelectionModel().getSelectedIndex();
			listData();

		});
		VBox vboxStudents = new VBox(15); // A VBox to hold the list of students registered in a course and how many there are
		vboxStudents.getChildren().addAll(Course_Seats, list_RStudents); 

		mainPane.setRight(vboxStudents); // Putting the VBox containing students registered in a course on the right of the main pane
		mainPane.setLeft(list_Courses); // Putting the ListView containing all courses on the left of the main pane
		hbox.setAlignment(Pos.CENTER); 
		hbox.getChildren().addAll(buttons); 
		mainPane.setBottom(hbox); // adding the hBox of buttons to the bottom of the main pane

		mainPane.setCenter(gridpane); // adding the gridPane with all data fields to the center of the main pane

	}

	public static Course findCourseObjectByID(String ID) {
		// This is a method that takes an ID of a course and tries to find a match, then returns the Course object of that course
		
		for (int i = 0; i < CommonClass.getCourseListSize(); i++) {

			if (CommonClass.getCourseList().get(i).getCourseID().equals(ID)) {
				return CommonClass.getCourseList().get(i);
			}

		}
		return new Course("", "", "", "", "", 0);

	}

	public static void listAllCourses(ListView<String> listView, int index) { 
		// This is a method that returns all courses in the Registeration.dat file
		if (index >= 0) {

			index %= CommonClass.getCourseListSize();

			listView.getItems().clear();

			for (int i = 0; i < CommonClass.getCourseListSize(); i++) {

				listView.getItems().add(CommonClass.getCourseList().get(i).getCourseID());

			}

		}

	}

	public static void listRStudents(ListView<String> listView, int index) {
		// This is a method that goes through all students, checks if they have a 
		// certain course by going through the names of all of their registered 
		// courses, and adds them to the given viewlist if they do
		if (index >= 0) {
			index %= CommonClass.getCourseListSize();

			listView.getItems().clear();

			for (int i = 0; i < CommonClass.getStudentListSize(); i++) {
				for (int j = 0; j < CommonClass.getStudentList().get(i).getCourses().size(); j++) {
					if (CommonClass.getStudentList().get(i).getCourses().get(j).getCourseID()
							.equals(CommonClass.getCourseList().get(index).getCourseID())) {
						listView.getItems().add(CommonClass.getStudentList().get(i).getStudID());
					}
				}

			}
		}
	}

	public void listData() {
		// This is a method that updates all data fields after the index of the selected item on the course listView is changed
		// or after a button has been pressed
		Course course = findCourseObjectByID(list_Courses.getItems().get(indexOnCoursesPane)); 
		// ^ Creating a Course object to make it easier to retrieve all information to fill the data fields ^
		
		course_ID.setText(course.getCourseID()); 
		course_Days.setText(course.getCourseDays()); 
		course_Name.setText(course.getCourseName()); 
		course_Location.setText(course.getCourseLocation()); 
		course_Time.setText(course.getCourseTime()); 
		course_Status.setText((course.getAvailableSeats() > 0) ? "Open" : "Closed"); 
		// ^ Updating all information fields
		
		Course_Seats.setText("There are " + (25 - course.getAvailableSeats()) + " registered in " + course.getCourseID());
		listRStudents(list_RStudents, indexOnCoursesPane);
		//^Updating the amount of students registered by using a formula considering that every class takes only 25 students
		//^and subtracting that number from the available seats
		list_Courses.getSelectionModel().select(indexOnCoursesPane); //Updating the selection of a course on the listView

	}

	class Handler implements EventHandler<ActionEvent> { //Inner class that creates a handler that all buttons use

		@Override
		public void handle(ActionEvent button) {

			if (((Button) button.getSource()).getText().equals("Next")) {
				//  increases the index on the courses list by one and set to 0 if it is the last index
				if (indexOnCoursesPane == CommonClass.getCourseListSize() - 1) {
					indexOnCoursesPane = 0;
				} else {
					indexOnCoursesPane++;
				}
			} else if (((Button) button.getSource()).getText().equals("Pervious")) {
				//  decreases the index on the courses list by one and set to last index if it is 0

				if (indexOnCoursesPane == 0)
					indexOnCoursesPane = CommonClass.getCourseListSize() - 1;
				else
					indexOnCoursesPane--;

			} else if (((Button) button.getSource()).getText().equals("Search")) {
				
				//  Tries to find the index on the courses listView
				// show the specified course provided in the ID data field and show alert when ID is wrong
				
				int index = CommonClass.findCourseByID(course_ID.getText());
				if(index >= 0) {indexOnCoursesPane = index;}
				else {CommonClass.showAlert("Wrong course ID");}

			} else if (((Button) button.getSource()).getText().equals("Back")) {
				//  Goes back to the main scene 
				primaryStage.setScene(Driver.mainScene);
				primaryStage.setTitle("Course Offering\\Main");

			}

			listData(); //Updates all data fields
			
		}

	}

}