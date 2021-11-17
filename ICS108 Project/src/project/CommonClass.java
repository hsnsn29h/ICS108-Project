package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class CommonClass {
	public static ArrayList<Course> courseList = new ArrayList<>();
    public static ArrayList<Student> studentList = new ArrayList<>();
    public static int studentListSize;
    private static final  File FILE = new File("res/Registration.dat"); 
    
   
    
	public static final void loadBinaryData() {
  
  try (
      FileInputStream fos = new FileInputStream(FILE);
      ObjectInputStream oos = new ObjectInputStream(fos);
      ){
	  CommonClass.courseList = (ArrayList<Course> ) oos.readObject();
	  CommonClass.studentList = (ArrayList<Student> ) oos.readObject();
	  studentListSize = studentList.size();
      
  }   
  
  
  catch (FileNotFoundException  ex) {
      System.out.println(ex.getMessage() );
  }

  catch (IOException |  ClassNotFoundException ex) {
      System.out.println(ex.getMessage());
      }
  
  
}
	
	public static String getStudentID(int index) {
		index %= studentListSize;
		
		return CommonClass.studentList.get(index).getStudID();
			
	}
	
	public static void listRCourses(ListView<String> listView, int index) {
		if(index >= 0) {
		index %= studentListSize;
		
		listView.getItems().clear();
		
		for(int i = 0; i < CommonClass.studentList.get(index).getCourses().size(); i++) {
			
			listView.getItems().add(CommonClass.studentList.get(index).getCourses().get(i).getCourseID());
			
		}
		}else System.out.println("wrong id");
		
		
	}
	
	public static void listNRCourses(ComboBox<String> comboBox, int index) {
		if(index >= 0) {
		index %= studentListSize;
		
		comboBox.getItems().clear();
		
		for(int i = 0; i < courseList.size(); i++) {
			boolean isNRcourse = true;
			for(int j = 0; j < studentList.get(index).getCourses().size(); j++) {
			if(courseList.get(i).getCourseID().equals(studentList.get(index).getCourses().get(j).getCourseID()))isNRcourse = false;
			}
			if(isNRcourse)comboBox.getItems().add(CommonClass.courseList.get(i).getCourseID());
			
		}
		}else System.out.println("wrong id");
		
		
	}
	
	
	public static int findStudentByID(String ID) {
		
		
		for(int i = 0; i < studentListSize; i++) {
			
			
			if(studentList.get(i).getStudID().equals(ID)) {return i;}
			
			
			
		}
		
		return -1;
	}
		
		public static int findCourseByID(String ID) {
			
			
			for(int i = 0; i < courseList.size(); i++) {
				
				
				if(courseList.get(i).getCourseID().equals(ID)) {return i;}
				
				
				
			}
		
		
		return -1;
		
		
	}
	
	
		public static void dropCourse(String stuID, ListView<String> listView) {
			
			String courseID = listView.getSelectionModel().getSelectedItem();
			if(courseID != null) {
			studentList.get(findStudentByID(stuID)).getCourses().remove(courseList.get(findCourseByID(courseID)));
			
			courseList.get(findCourseByID(courseID)).drop();
			}else {System.out.println("you must choose a course to drop");}
			
			
		}
		
		public static void registerCourse(String stuID, ComboBox<String> comboBox) {
			String courseID = comboBox.getSelectionModel().getSelectedItem();
			if(courseID != null) {
			if(courseList.get(findCourseByID(courseID)).getAvailableSeats() > 0) {
			studentList.get(findStudentByID(stuID)).getCourses().add(courseList.get(findCourseByID(courseID)));
			}else {System.out.println("there is no avaliable seats");}
			
			courseList.get(findCourseByID(courseID)).register();
			}else {System.out.println("you must choose a course to register");}
		}
		
		public static void save() {
			
			
			  try (
				      FileOutputStream fos = new FileOutputStream(FILE);
				      ObjectOutputStream oos = new ObjectOutputStream(fos);
				      ){
					  oos.writeObject(CommonClass.courseList);
					  oos.writeObject(CommonClass.studentList);
				      
				  }   
				  
				  
				  catch (FileNotFoundException  ex) {
				      System.out.println(ex.getMessage() );
				  }

				  catch (IOException ex) {
				      System.out.println(ex.getMessage());
				      }
			
			
		}
	
	
	
}
