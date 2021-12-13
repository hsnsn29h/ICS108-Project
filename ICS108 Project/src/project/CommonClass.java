package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

public class CommonClass {

	// Declare and initialize arrayLists to hold our registration data:
	private static ArrayList<Course> courseList = new ArrayList<>();
	private static ArrayList<Student> studentList = new ArrayList<>();

	// Number of students and courses:
	private static int studentListSize;
	private static int courseListSize;

	private static final File FILE = new File("res/Registration.dat"); // Reaching our registration data file

	public static final void loadBinaryData() { // A method used to initialize the reading of both objects as well as
												// their sizes.

		try (FileInputStream fis = new FileInputStream(FILE); ObjectInputStream ois = new ObjectInputStream(fis);) {
			// ^ Reading data as an Object Input Stream to read the binary data
			CommonClass.courseList = (ArrayList<Course>) ois.readObject(); // Reading the arrayList of courses
			CommonClass.studentList = (ArrayList<Student>) ois.readObject(); // Reading the arrayList of students
			studentListSize = studentList.size(); // getting the total amount of students
			courseListSize = courseList.size(); // getting the total amount of courses
		}

		catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		catch (IOException | ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		}

	}

	// Getters :

	public static ArrayList<Student> getStudentList() {
		return studentList;
	}

	public static ArrayList<Course> getCourseList() {
		return courseList;
	}

	public static int getStudentListSize() {
		return studentListSize;
	}

	public static int getCourseListSize() {
		return courseListSize;
	}
	
	

	public static int findCourseByID(String ID) {
		// ^ A method that returns the index of a course in the ArrayList using its ID
		// (string type) (e.g., ICS104) ^

		for (int i = 0; i < courseList.size(); i++) {

			if (courseList.get(i).getCourseID().equals(ID)) { // Checks all courses, attempts to find a match, then
																// returns the index of the match
				return i;
			}

		}

		return -1; // Returns -1 if ID does not match a course

	}

	public static void save() { // A method used to Save the data back into the Registeration.dat file after
								// adding/removing students.

		try (FileOutputStream fos = new FileOutputStream(FILE); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(CommonClass.courseList); // writing back the tempered-with course objects arraylist
			oos.writeObject(CommonClass.studentList); // writing back the tempered-with student objects arraylist

		}

		catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}

	public static void showAlert(String message) { // A method that shows a small window with a message

		Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.show();

	}

}