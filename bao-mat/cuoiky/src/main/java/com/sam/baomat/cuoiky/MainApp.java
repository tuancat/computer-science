package com.sam.baomat.cuoiky;

import com.sam.baomat.cuoiky.ui.BorderMainPane;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        BorderMainPane mainPane = new BorderMainPane(primaryStage);

        Scene scene = new Scene(mainPane, 300, 250);

        primaryStage.setTitle("Thi Cuoi Kỳ");
        primaryStage.setScene(scene);
//        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
