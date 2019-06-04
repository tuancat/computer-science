/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detectplate;

import detectplate.ui.BorderMainPane;
import detectplate.ui.VideoView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

/**
 *
 * @author SamFisher
 */
public class Detectplate extends Application {

    @Override
    public void start(Stage primaryStage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.setProperty("jna.library.path", "D:\\win32-x86-64\\libtesseract3051.dll");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        VideoView videoView = new VideoView();
        BorderMainPane mainPane = new BorderMainPane(primaryStage);
        root.getChildren().add(btn);

        Scene scene = new Scene(mainPane, 300, 250);

        primaryStage.setTitle("Nhận diện biển số");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
