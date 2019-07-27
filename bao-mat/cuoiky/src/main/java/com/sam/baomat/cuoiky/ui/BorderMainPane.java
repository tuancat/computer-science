/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sam.baomat.cuoiky.ui;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author SamFisher
 */
public class BorderMainPane extends BorderPane {

    Stage primaryStage;

    public BorderMainPane() {
        this.setMinSize(400, 400);
        this.setPadding(new Insets(0, 10, 0, 10));
        initUI();

    }

    public BorderMainPane(Stage stage) {
        this.primaryStage = stage;
        this.setPadding(new Insets(0, 10, 0, 10));
        // Set the Size of the VBox
        setPrefSize(400, 400);
        // Set the Style-properties of the BorderPane
        this.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");
        initUI();
    }

    private void initUI() {
        initTopMenu(); //khoi tạo menu
    }

    private void initTopMenu() {
        HBox topMenu = new HBox();
        Button initButton = new Button("Chạy");
        topMenu.getChildren().add(initButton);

        initButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.err.println("init button");
                ResultPane resultPane = new ResultPane();
                Scene secondScene = new Scene(resultPane, 230, 100);

                Stage newWindow = new Stage();
                newWindow.setTitle("Kết quả");
                newWindow.setMaximized(true);
                newWindow.setScene(secondScene);

                // Set position of second window, related to primary window.
                newWindow.setX(primaryStage.getX() + 200);
                newWindow.setY(primaryStage.getY() + 100);

                newWindow.show();
            }
        });

        this.setTop(topMenu);
        FormInputPane inputPane = new FormInputPane();
        this.setLeft(inputPane);

    }
}
