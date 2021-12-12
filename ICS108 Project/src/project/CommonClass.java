package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CommonClass {

	private static ArrayList<Course> courseList = new ArrayList<>();
	private static ArrayList<Student> studentList = new ArrayList<>();
	private static int studentListSize;
	private static int courseListSize;
	private static final File FILE = new File("res/Registration.dat");

	public static final void loadBinaryData() {

		try (FileInputStream fis = new FileInputStream(FILE); ObjectInputStream ois = new ObjectInputStream(fis);) {
			CommonClass.courseList = (ArrayList<Course>) ois.readObject();
			CommonClass.studentList = (ArrayList<Student>) ois.readObject();
			studentListSize = studentList.size();
			courseListSize = courseList.size();
		}

		catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		catch (IOException | ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		}

	}

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

		for (int i = 0; i < courseList.size(); i++) {

			if (courseList.get(i).getCourseID().equals(ID)) {
				return i;
			}

		}

		return -1;

	}

	public static void save() {

		try (FileOutputStream fos = new FileOutputStream(FILE); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(CommonClass.courseList);
			oos.writeObject(CommonClass.studentList);

		}

		catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}

}