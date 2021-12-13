package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {

	//declaring the scenes and the primary stage out of the start method to use them outside the Driver class:
	
	public static Stage primaryStage; // The primary stage used for all 3 scenes

	static Scene mainScene;
	static Scene studentsScene; 
	static Scene coursesScene; 
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Driver.primaryStage = primaryStage; //making the stage we get as a parameter as our main stage
		

		Driver.mainScene = new MainScene();
		Driver.studentsScene = new StudentsScene();
		Driver.coursesScene = new CoursesScene();
		// ^ initializing each scene as an object to its respective class ^
		
		primaryStage.setScene(mainScene); //making the Main scene as the scene we begin with
		primaryStage.setTitle("Course Offering\\Main"); //Setting the title of the scene
		primaryStage.show(); //showing the stage and scene

	}

	public static void main(String[] args) {

		CommonClass.loadBinaryData(); //loading all data using the LoadBinaryData method in the CommonClass

		launch(args); //launching the program

	}

}
