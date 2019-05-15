/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhandien.bienso.ui;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nhandien.bienso.utils.Utils;

/**
 *
 * @author SamFisher
 */
public class BorderMainPane extends BorderPane {

    private TopMenu topMenu;
    private GridMainPane gridCenter;
    private GridPane gridRight;
    private VideoView videoView;
    private int chooseOption = 0;
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

        initTopMenu(); //khởi tạo top menu
        initCenterPane();//init center area
        initRightPane();//init right area
    }

    private void initCenterPane() {
        gridCenter = new GridMainPane();
        gridCenter.setStyle("-fx-padding: 10; "
                + "-fx-margin: 5;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: grey;");
        this.setCenter(gridCenter);
    }

    private void initRightPane() {
        gridRight = new GridPane();
        gridRight.setPrefSize(400, 400);
        gridRight.setStyle("-fx-padding: 10; "
                + "-fx-margin: 5;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: grey;");
        this.setRight(gridRight);
    }

    private void initTopMenu() {
        topMenu = new TopMenu(primaryStage);
        this.setTop(topMenu);

        topMenu.getBrowseFile().setOnAction((ActionEvent event) -> {
            topMenu.getFileChooser().getExtensionFilters().removeAll();
            String choose = topMenu.getMenuOption().getValue().toString();
            configureFileChooser(topMenu.getFileChooser(), choose);
            List<File> listFile = topMenu.getFileChooser().showOpenMultipleDialog(primaryStage);
            if (listFile != null) {
                processFiles(listFile);
            }
        });
    }

    private void processFiles(List<File> listFile) {
        if (listFile.isEmpty()) {
            Logger.getLogger(
                    TopMenu.class.getName()).log(
                    Level.SEVERE, null, "List Size File is empty"
            );
        } else {
            int countIndex = 0;
            listFile.forEach((file) -> {
                if (chooseOption == 0) { //  choose image 
                    gridCenter = new GridMainPane();
                    this.setCenter(gridCenter);
                    if (Utils.checkFileIsImage(file.getName())) {
                        System.err.println("type of file:" + file.getAbsolutePath());
                        gridCenter.callImageProcess(file.getAbsolutePath());
                    }
//                    
                }
                if (chooseOption == 1) {
                    videoView = new VideoView();
                    this.setCenter(videoView);
                    if (Utils.checkFileIsVideo(file.getName())) {
                        System.err.println("type of file video:" + file.getAbsolutePath());
                        videoView.processVideo(file.getAbsolutePath());
                    }
                }
            });

        }
    }

    public TopMenu getTopMenu() {
        return topMenu;
    }

    public void setTopMenu(TopMenu topMenu) {
        this.topMenu = topMenu;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void configureFileChooser(
            FileChooser fileChooser, String option) {
        
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        switch (option) {
            case "Video":
                chooseOption = 1;
                fileChooser.setTitle("View Video");
                fileChooser.setInitialDirectory(
                        new File(System.getProperty("user.home"))
                );
                break;
            case "Image":
            default:
                chooseOption = 0;
                fileChooser.setTitle("View Pictures");
                fileChooser.setInitialDirectory(
                        new File(System.getProperty("user.home"))
                );
                fileChooser.getExtensionFilters().removeAll();

                break;

        }
    }

}
