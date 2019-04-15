/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhandien.bienso;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import nhandien.bienso.ui.BorderMainPane;
import nhandien.bienso.ui.GridMainPane;
import org.opencv.core.Core;

/**
 *
 * @author SamFisher
 */
public class NhandienBienso extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
        
        GridMainPane gridPane = new GridMainPane();
        BorderMainPane mainPane = new BorderMainPane(primaryStage);
        
        
        Scene scene = new Scene(mainPane, 300, 250);
        
        primaryStage.setTitle("Thi Cuoi Kỳ");
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
