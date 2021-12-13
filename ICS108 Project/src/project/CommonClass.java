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

	private static ArrayList<Course> courseList = new ArrayList<>(); //Creating a holder for the ArrayList of Course objects in the Registeration.dat file
	private static ArrayList<Student> studentList = new ArrayList<>(); //Creating a holder for the ArrayList of Student objects in the Registeration.dat file
	private static int studentListSize; //A variable used to know the total amount of students
	private static int courseListSize; //A variable used to know the total amount of courses
	private static final File FILE = new File("res/Registration.dat"); //creating a file object of our main data folder (which has 2 array lists)

	public static final void loadBinaryData() { // A method used to initialize the reading of both objects as well as their sizes.
		
		try (FileInputStream fis = new FileInputStream(FILE); ObjectInputStream ois = new ObjectInputStream(fis);) {
			// ^ Reading data as an Object Input Stream to read the binary data
			CommonClass.courseList = (ArrayList<Course>) ois.readObject(); // Reading the arraylist of courses
			CommonClass.studentList = (ArrayList<Student>) ois.readObject(); // Reading the arraylist of students
			studentListSize = studentList.size(); //getting the total amount of students
			courseListSize = courseList.size(); // getting the total amount of courses
		}

		catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		catch (IOException | ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		}

	}

	public static ArrayList<Student> getStudentList() { //A method that returns the arraylist of student objects
		return studentList; 
	}

	public static ArrayList<Course> getCourseList() { //A method that returns the arraylist of course objects
		return courseList;
	}

	public static int getStudentListSize() { //A method that returns the total amount of students
		return studentListSize;
	}

	public static int getCourseListSize() { //A method that returns the total amount of courses
		return courseListSize;
	}

	public static int findCourseByID(String ID) { 
		// ^ A method that returns the index of a course in the ArrayList using its ID (string type) (e.g., ICS104) ^

		for (int i = 0; i < courseList.size(); i++) {

			if (courseList.get(i).getCourseID().equals(ID)) { //Checks all courses, attempts to find a match, then returns the index of the match
				return i;
			}

		}

		return -1;

	}

	public static void save() { // A method used to Save the data back into the Registeration.dat file after adding/removing students.

		try (FileOutputStream fos = new FileOutputStream(FILE); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(CommonClass.courseList); //writing back the tempered-with course objects arraylist
			oos.writeObject(CommonClass.studentList); //writing back the tempered-with student objects arraylist

		}

		catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}
	
	public static void showAlert(String message) {
		
		Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.show();
		
	}

}