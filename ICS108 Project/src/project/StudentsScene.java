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

	static VBox vBox = new VBox();

	static Stage primaryStage = Driver.primaryStage;

	static int indexOnStudentsPane = 0;

	static TextField text_ID;
	static ListView<String> list_Rcourses;
	static ComboBox<String> list_NRcourses;

	public StudentsScene() {

		super(vBox, 1024, 512);

		vBox.setPadding(new Insets(24, 24, 24, 24));

		HBox[] hBoxes = new HBox[4];

		Label[] labels = new Label[3];
		String[] labelsNames = { "Student ID", "Registered Courses", "Not Registered Courses" };

		text_ID = new TextField(getStudentID(indexOnStudentsPane));
		list_Rcourses = new ListView<String>();
		list_NRcourses = new ComboBox<String>();

		listRCourses(list_Rcourses, indexOnStudentsPane);
		listNRCourses(list_NRcourses, indexOnStudentsPane);

		Control[] views = { text_ID, list_Rcourses, list_NRcourses };

		for (int i = 0; i < hBoxes.length; i++) {
			hBoxes[i] = new HBox(12);
			hBoxes[i].setAlignment(Pos.CENTER_LEFT);

			if (i < 3) {
				labels[i] = new Label(labelsNames[i]);
				labels[i].prefWidthProperty().bind(hBoxes[i].widthProperty().divide(3));
				hBoxes[i].getChildren().add(labels[i]);

				views[i].prefWidthProperty().bind(hBoxes[i].widthProperty().divide(3).multiply(2));
				hBoxes[i].getChildren().add(views[i]);

			}
		}

		Button[] buttons = new Button[6];
		String[] buttonNames = { "Back", "Pervious", "Next", "Register", "Drop", "Search" };
		int buttonCounter = 0;
		Handler handler = new Handler();
		for (String name : buttonNames) {

			buttons[buttonCounter] = new Button(name);
			buttons[buttonCounter].prefWidthProperty().bind(hBoxes[3].widthProperty().divide(6));
			buttons[buttonCounter].setOnAction(handler);
			buttonCounter++;
		}

		hBoxes[3].getChildren().addAll(buttons);

		vBox.getChildren().addAll(hBoxes);

		for (int i : new int[] { 0, 2, 3 })
			hBoxes[i].prefHeightProperty().bind(this.heightProperty().divide(10));
		hBoxes[1].prefHeightProperty().bind(this.heightProperty().divide(10).multiply(7));

	}

	public static String getStudentID(int index) {
		index %= CommonClass.getStudentListSize();

		return CommonClass.getStudentList().get(index).getStudID();

	}

	public static int findStudentByID(String ID) {

		for (int i = 0; i < CommonClass.getStudentListSize(); i++) {

			if (CommonClass.getStudentList().get(i).getStudID().equals(ID)) {
				return i;
			}

		}

		return -1;
	}

	public static void listRCourses(ListView<String> listView, int index) {
		
			index %= CommonClass.getStudentListSize();

			listView.getItems().clear();

			for (int i = 0; i < CommonClass.getStudentList().get(index).getCourses().size(); i++) {

				listView.getItems().add(CommonClass.getStudentList().get(index).getCourses().get(i).getCourseID());

			}
		
	}

	public static void listNRCourses(ComboBox<String> comboBox, int index) {
		
			index %= CommonClass.getStudentListSize();

			comboBox.getItems().clear();

			for (int i = 0; i < CommonClass.getCourseListSize(); i++) {
				boolean isNRcourse = true;
				for (int j = 0; j < CommonClass.getStudentList().get(index).getCourses().size(); j++) {
					if (CommonClass.getCourseList().get(i).getCourseID()
							.equals(CommonClass.getStudentList().get(index).getCourses().get(j).getCourseID()))
						isNRcourse = false;
				}
				if (isNRcourse)
					comboBox.getItems().add(CommonClass.getCourseList().get(i).getCourseID());

			}
		 

	}

	public static void registerCourse(String stuID, ComboBox<String> comboBox) {
		String courseID = comboBox.getSelectionModel().getSelectedItem();
		if (courseID != null) {
			if (CommonClass.getCourseList().get(CommonClass.findCourseByID(courseID)).getAvailableSeats() > 0) {
				CommonClass.getStudentList().get(findStudentByID(stuID)).getCourses()
						.add(CommonClass.getCourseList().get(CommonClass.findCourseByID(courseID)));
			} else {
				CommonClass.showAlert("There is no avaliable seats");
			}

			CommonClass.getCourseList().get(CommonClass.findCourseByID(courseID)).register();
		} else {
			CommonClass.showAlert("You must choose a course to register");
		}
	}

	public static void dropCourse(String stuID, ListView<String> listView) {

		String courseID = listView.getSelectionModel().getSelectedItem();
		if (courseID != null) {
			CommonClass.getStudentList().get(findStudentByID(stuID)).getCourses()
					.remove(CommonClass.getCourseList().get(CommonClass.findCourseByID(courseID)));

			CommonClass.getCourseList().get(CommonClass.findCourseByID(courseID)).drop();
		} else {
			
			CommonClass.showAlert("You must choose a course to drop");
		}
		

	}
	

	

	class Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent button) {

			if (((Button) button.getSource()).getText().equals("Next")) {
				indexOnStudentsPane++;
				text_ID.setText(getStudentID(indexOnStudentsPane));

			} else if (((Button) button.getSource()).getText().equals("Pervious")) {

				if (indexOnStudentsPane == 0)
					indexOnStudentsPane = CommonClass.getStudentListSize();
				else
					indexOnStudentsPane--;
				text_ID.setText(getStudentID(indexOnStudentsPane));

			} else if (((Button) button.getSource()).getText().equals("Search")) {
				int index = findStudentByID(text_ID.getText());
				if(index >= 0) {indexOnStudentsPane = index;}
				else {CommonClass.showAlert("Wrong ID");}

			} else if (((Button) button.getSource()).getText().equals("Drop")) {

				dropCourse(text_ID.getText(), list_Rcourses);

			} else if (((Button) button.getSource()).getText().equals("Register")) {

				registerCourse(text_ID.getText(), list_NRcourses);

			} else if (((Button) button.getSource()).getText().equals("Back")) {

				primaryStage.setScene(Driver.mainScene);
				primaryStage.setTitle("Course Offering\\Main");

			}
			listRCourses(list_Rcourses, indexOnStudentsPane);
			listNRCourses(list_NRcourses, indexOnStudentsPane);

		}

	}

}