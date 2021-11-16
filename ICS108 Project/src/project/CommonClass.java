package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.scene.control.ListView;

public class CommonClass {
	public static ArrayList<Course> courseList = new ArrayList<>();
    public static ArrayList<Student> studentList = new ArrayList<>();
    public static int studentListSize;
    private static final  File FILE = new File("res/Registration.dat"); 
    
   
    
	public static final void loadBinaryData() {
        System.out.println("start loading");
  try (
      FileInputStream fos = new FileInputStream(FILE);
      ObjectInputStream oos = new ObjectInputStream(fos);
      ){
	  CommonClass.courseList = (ArrayList<Course> ) oos.readObject();
	  CommonClass.studentList = (ArrayList<Student> ) oos.readObject();
	  studentListSize = studentList.size();
	  for ( int i = 0 ; i<  CommonClass.courseList.size(); i++)
		  System.out.println(CommonClass.courseList.get(i));
	  
	  System.out.print(CommonClass.studentList.size());
	  for ( int i = 0 ; i<  CommonClass.studentList.size(); i++)
		  System.out.println(CommonClass.studentList.get(i));
      
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
	
	public static void listCourses(ListView<String> listView, int index) {
		
		index %= studentListSize;
		
		listView.getItems().clear();
		
		for(int i = 0; i < CommonClass.studentList.get(index).getCourses().size(); i++) {
			
			listView.getItems().add(CommonClass.studentList.get(index).getCourses().get(i).getCourseID());
			
		}
		
		
	}
	
	
	
	
	
    
    
}
