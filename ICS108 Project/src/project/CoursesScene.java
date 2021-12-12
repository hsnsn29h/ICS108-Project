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

	static BorderPane mainPane = new BorderPane();

	static Stage primaryStage = Driver.primaryStage;

	static TextField course_ID;
	static TextField course_Name;
	static TextField course_Days;
	static TextField course_Location;
	static TextField course_Time;
	static Button course_Status;
	static Label Course_Seats;
	ListView<String> list_Courses;
	ListView<String> list_RStudents;

	static int indexOnCoursesPane = 0;

	public CoursesScene() {

		super(mainPane, 1024, 512);

		mainPane.setPadding(new Insets(24, 24, 24, 24));
		GridPane gridpane = new GridPane();

		course_ID = new TextField();
		course_Name = new TextField();
		course_Days = new TextField();
		course_Location = new TextField();
		course_Time = new TextField();
		course_Status = new Button("Open");
		Course_Seats = new Label("There are   registered in ");
		VBox vboxTextFields = new VBox(5);
		vboxTextFields.getChildren().addAll(course_ID, course_Name, course_Days, course_Location, course_Time,
				course_Status);
		String[] labelNames = { "ID", "Name", "Days", "Location", "Time", "Status" };
		Label[] labels = new Label[6];

		VBox vboxLabels = new VBox(15);
		VBox vboxStudents = new VBox(15);

		int labelCounter = 0;

		for (String name : labelNames) {
			labels[labelCounter] = new Label(name);
			labelCounter++;

		}
		vboxLabels.getChildren().addAll(labels);

		gridpane.addColumn(0, vboxLabels);
		gridpane.addColumn(2, vboxTextFields);
		gridpane.setAlignment(Pos.CENTER);

		Button[] buttons = new Button[4];
		String[] buttonNames = { "Back", "Pervious", "Next", "Search" };
		int buttonCounter = 0;
		HBox hbox = new HBox(5);
		Handler handler = new Handler();

		for (String name : buttonNames) {

			buttons[buttonCounter] = new Button(name);
			buttons[buttonCounter].prefWidthProperty().bind(mainPane.widthProperty().divide(12));
			buttons[buttonCounter].setOnAction(handler);

			buttonCounter++;

		}

		list_Courses = new ListView<String>();
		listAllCourses(list_Courses, indexOnCoursesPane);

		list_RStudents = new ListView<String>();
		listRStudents(list_RStudents, indexOnCoursesPane);

		listData();

		list_Courses.getSelectionModel().selectedItemProperty().addListener(e -> {

			indexOnCoursesPane = list_Courses.getSelectionModel().getSelectedIndex();
			listData();

		});

		vboxStudents.getChildren().addAll(Course_Seats, list_RStudents);

		mainPane.setRight(vboxStudents);
		mainPane.setLeft(list_Courses);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(buttons);
		mainPane.setCenter(gridpane);
		mainPane.setBottom(hbox);

	}

	public static Course findCourseObjectByID(String ID) {

		for (int i = 0; i < CommonClass.getCourseListSize(); i++) {

			if (CommonClass.getCourseList().get(i).getCourseID().equals(ID)) {
				return CommonClass.getCourseList().get(i);
			}

		}
		return new Course("", "", "", "", "", 0);

	}

	public static void listAllCourses(ListView<String> listView, int index) {
		if (index >= 0) {

			index %= CommonClass.getCourseListSize();

			listView.getItems().clear();

			for (int i = 0; i < CommonClass.getCourseListSize(); i++) {

				listView.getItems().add(CommonClass.getCourseList().get(i).getCourseID());

			}

		}

	}

	public static void listRStudents(ListView<String> listView, int index) {
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

		Course course = findCourseObjectByID(list_Courses.getItems().get(indexOnCoursesPane));

		course_ID.setText(course.getCourseID());
		course_Days.setText(course.getCourseDays());
		course_Name.setText(course.getCourseName());
		course_Location.setText(course.getCourseLocation());
		course_Days.setText(course.getCourseDays());
		course_Time.setText(course.getCourseTime());
		course_Status.setText((course.getAvailableSeats() > 0) ? "Open" : "Closed");
		Course_Seats
				.setText("There are " + (25 - course.getAvailableSeats()) + " registered in " + course.getCourseID());
		listRStudents(list_RStudents, indexOnCoursesPane);
		list_Courses.getSelectionModel().select(indexOnCoursesPane);

	}

	class Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent button) {

			if (((Button) button.getSource()).getText().equals("Next")) {
				if (indexOnCoursesPane == CommonClass.getCourseListSize() - 1) {
					indexOnCoursesPane = 0;
				} else {
					indexOnCoursesPane++;
				}
			} else if (((Button) button.getSource()).getText().equals("Pervious")) {

				if (indexOnCoursesPane == 0)
					indexOnCoursesPane = CommonClass.getCourseListSize() - 1;
				else
					indexOnCoursesPane--;

			} else if (((Button) button.getSource()).getText().equals("Search")) {

				indexOnCoursesPane = CommonClass.findCourseByID(course_ID.getText());

			} else if (((Button) button.getSource()).getText().equals("Back")) {

				primaryStage.setScene(Driver.mainScene);
				primaryStage.setTitle("Course Offering\\Main");

			}

			listData();

		}

	}

}