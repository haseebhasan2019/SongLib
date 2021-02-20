//Haseeb Hasan and Fawaz Tahir
package songlib.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import songlib.view.songlibController;

public class SongLib extends Application {

	@Override
	public void start(Stage primaryStage) 
	throws Exception {
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(getClass().getResource("/SongLib/view/songlib.fxml"));
//		//GridPane root = (GridPane)loader.load();
//		
//		songlibController listController = loader.getController();
//		listController.start(primaryStage);
//		
//		//Scene scene = new Scene(root);
//		//primaryStage.setScene(scene);
//		primaryStage.setTitle("Song Library");
//		primaryStage.setResizable(false);
//		primaryStage.show();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/SongLib/view/songlib.fxml"));
		Parent root = loader.load();
	    
		songlibController listController = loader.getController();
		listController.start(primaryStage);
		
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
