package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {

	public static Stage primaryStage;

	static Scene mainScene;
	static Scene studentsScene;
	static Scene coursesScene;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Driver.primaryStage = primaryStage;

		Driver.mainScene = new MainScene();
		Driver.studentsScene = new StudentsScene();
		Driver.coursesScene = new CoursesScene();

		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Course Offering\\Main");
		primaryStage.show();

	}

	public static void main(String[] args) {

		CommonClass.loadBinaryData();

		launch(args);

	}

}
