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

public class CoursesScene extends Scene { //Extending the Scene class to get the features of it 

	static BorderPane mainPane = new BorderPane(); //Initializing the main used pane outside

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
	
	public CoursesScene() { //The main constructor which is used to create an object of the courses scene

		super(mainPane, 1024, 512); //using the constructor of the parent class (Scene)

		mainPane.setPadding(new Insets(24, 24, 24, 24)); //setting the paddings for the main pane
		
		GridPane gridpane = new GridPane(); // Making a gridpane to hold all courses information

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
				course_Status); // Adding all data fields to the VBox
		vboxTextFields.setPrefWidth(260);
		
		String[] labelNames = { "ID", "Name", "Days", "Location", "Time", "Status" }; 
		Label[] labels = new Label[6];
		// ^^ Making Labels array + Label Names for each data field
		VBox vboxLabels = new VBox(15); // Making a VBox to hold the labels of the data fields
		

		int labelCounter = 0; // number to go through the list of labels

		for (String name : labelNames) {
			labels[labelCounter] = new Label(name); // Initializing each label individually through the loop
			labelCounter++; 

		}
		vboxLabels.getChildren().addAll(labels); // Adding all labels to their VBox

		gridpane.addColumn(0, vboxLabels); // Adding the VBox for the lables of the data field on the left of the gridpane
		gridpane.addColumn(2, vboxTextFields); // Adding the VBox for the data fields on the right of the gridpane
		gridpane.setAlignment(Pos.CENTER); // Aligning the label to the center

		Button[] buttons = new Button[4]; // Making a list of buttons to initialize all buttons together
		String[] buttonNames = { "Back", "Pervious", "Next", "Search" }; // Button names for each button in their right order
		int buttonCounter = 0; // number to go through the array of buttons using a loop
		HBox hbox = new HBox(5); // HBox that holds all buttons in a horizontal way
		Handler handler = new Handler(); // Creating a class for the handler that all buttons use

		for (String name : buttonNames) { // loop used to initialize buttons and assign handlers to them

			buttons[buttonCounter] = new Button(name);
			buttons[buttonCounter].prefWidthProperty().bind(mainPane.widthProperty().divide(12));
			buttons[buttonCounter].setOnAction(handler);

			buttonCounter++;

		}

		list_Courses = new ListView<String>(); // Initializing the listview that holds all courses
		listAllCourses(list_Courses, indexOnCoursesPane); // Adding all courses to the listview

		list_RStudents = new ListView<String>(); // Initializing a listview that holds all students registered in a selected course
		listRStudents(list_RStudents, indexOnCoursesPane); // Adding all those students to the list

		listData(); // A method that loads all data into the data fields (text fields/labels/buttons)

		list_Courses.getSelectionModel().selectedItemProperty().addListener(e -> {
			// This lambda expression makes it so that whenever you choose an item on the listview, the index on it will be changed
			// and all data fields will be updated as well to the new index, you can also use arrows to select a course on the listview 
			indexOnCoursesPane = list_Courses.getSelectionModel().getSelectedIndex();
			listData();

		});
		VBox vboxStudents = new VBox(15); // A VBox to hold the list of students registered in a course and how many there are
		vboxStudents.getChildren().addAll(Course_Seats, list_RStudents); // Adding both of them to the VBox

		mainPane.setRight(vboxStudents); // Putting the VBox containing students registered in a course on the right of the main pane
		mainPane.setLeft(list_Courses); // Putting the Listview containing all courses on the leftof the main pane
		hbox.setAlignment(Pos.CENTER); 
		hbox.getChildren().addAll(buttons); //adding all buttons to the HBox of buttons
		mainPane.setBottom(hbox); // adding the hbox of buttons to the bottom of the main pane

		mainPane.setCenter(gridpane); // adding the gridpane with all data fields to the center of the main pane

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
		// This is a method that updates all data fields after the index of the selected item on the course viewlist is changed
		// or after a button has been pressed
		Course course = findCourseObjectByID(list_Courses.getItems().get(indexOnCoursesPane)); 
		// ^ Creating a Course object to make it easier to retrive all information to fill the data fields ^
		
		course_ID.setText(course.getCourseID()); //Updating the ID data field by the ID of the course
		course_Days.setText(course.getCourseDays()); //Updating the Days data field by the Days of the course
		course_Name.setText(course.getCourseName()); //Updating the Name data field by the Name of the course
		course_Location.setText(course.getCourseLocation()); //Updating the Location data field by the Location of the course
		course_Time.setText(course.getCourseTime()); //Updating the Time data field by the Time of the course
		course_Status.setText((course.getAvailableSeats() > 0) ? "Open" : "Closed"); //Updating the Status data field by the Status of the course
		Course_Seats.setText("There are " + (25 - course.getAvailableSeats()) + " registered in " + course.getCourseID());
		listRStudents(list_RStudents, indexOnCoursesPane);
		//^Updating the amount of students registered by using a formula considering that every class takes only 25 students
		//^and subtracting that number from the available seats
		list_Courses.getSelectionModel().select(indexOnCoursesPane); //Updating the selection of a course on the listview

	}

	class Handler implements EventHandler<ActionEvent> { //Inner class that creates a handler that all buttons use

		@Override
		public void handle(ActionEvent button) {

			if (((Button) button.getSource()).getText().equals("Next")) {
				// ^ Checks if the button name is Next, increases the index on the courses list by one if that is True
				if (indexOnCoursesPane == CommonClass.getCourseListSize() - 1) {
					indexOnCoursesPane = 0;
				} else {
					indexOnCoursesPane++;
				}
			} else if (((Button) button.getSource()).getText().equals("Pervious")) {
				// ^ Checks if the button name is Previous, decreases the index on the courses list by one if that is True

				if (indexOnCoursesPane == 0)
					indexOnCoursesPane = CommonClass.getCourseListSize() - 1;
				else
					indexOnCoursesPane--;

			} else if (((Button) button.getSource()).getText().equals("Search")) {
				// ^ Checks if the button name is Search, Tries to find the index on the courses listview
				// ^ of the specified course provided in the ID data field and show alert when ID is wrong
				
				int index = CommonClass.findCourseByID(course_ID.getText());
				if(index >= 0) {indexOnCoursesPane = index;}
				else {CommonClass.showAlert("Wrong ID");}

			} else if (((Button) button.getSource()).getText().equals("Back")) {
				// ^ Checks if the button name is Search, Goes back to the main scene if that returns True
				primaryStage.setScene(Driver.mainScene);
				primaryStage.setTitle("Course Offering\\Main");

			}

			listData(); //Updates all data fields
			
		}

	}

}