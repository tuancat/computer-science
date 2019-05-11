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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nhandien.bienso.utils.ImageProcessTools;
import nhandien.bienso.utils.Utils;

/**
 *
 * @author SamFisher
 */
public class TopMenu extends HBox {

    FileChooser fileChooser = new FileChooser();
    Stage primaryStage;
    Button browseFile = new Button("Chọn file");

    private ComboBox menuOption = new ComboBox();
    private int chooseOption = 0;

    public TopMenu() {
//        menuOption.setPadding(new Insets(10, 10, 10, 10));

//        this.set
        initUI();
    }

    public TopMenu(Stage prStage) {
        this.primaryStage = prStage;
        initUI();
    }

    private void initUI() {
        menuOption.setStyle("-margin-right:10px");
        menuOption.getItems().add(0, "Image");
        menuOption.getItems().add(1, "Video");
        menuOption.getItems().add(0, "Camera");
        Label loadLabel = new Label("Chọn loại hình");
        menuOption.setPlaceholder(loadLabel);
        fileChooser.setTitle("Chọn file");
//        fileChooser.showOpenDialog(primaryStage);
        this.getChildren().add(menuOption);
        this.getChildren().add(browseFile);
        this.browseFile.setOnAction((ActionEvent event) -> {

            String choose = menuOption.getValue().toString();
            configureFileChooser(fileChooser, choose);
            List<File> listFile = fileChooser.showOpenMultipleDialog(primaryStage);
            if (listFile != null) {
                processFiles(listFile);
            }
        });

    }

    private void openFile(File file) {
//        try {
////            desktop.open(file);
//        } catch (IOException ex) {
//            Logger.getLogger(
//                    TopMenu.class.getName()).log(
//                    Level.SEVERE, null, ex
//            );
//        }
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
                    if (Utils.checkFileIsImage(file.getName())) {
                        System.err.println("type of file:" + file.getAbsolutePath());
                    }
//                    ImageProcessTools.drawPlate(file);
                }
            });
            
        }
    }

    private void processImage(File file) {

    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public void setFileChooser(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Button getBrowseFile() {
        return browseFile;
    }

    public void setBrowseFile(Button browseFile) {
        this.browseFile = browseFile;
    }

    public ComboBox getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(ComboBox menuOption) {
        this.menuOption = menuOption;
    }

    public int getChooseOption() {
        return chooseOption;
    }

    public void setChooseOption(int chooseOption) {
        this.chooseOption = chooseOption;
    }

    private void configureFileChooser(
            FileChooser fileChooser, String option) {

        switch (option) {
            case "Video":
                chooseOption = 1;
                fileChooser.setTitle("View Video");
                fileChooser.setInitialDirectory(
                        new File(System.getProperty("user.home"))
                );
                fileChooser.getExtensionFilters().addAll(
                        //                new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                        new FileChooser.ExtensionFilter("AVI", "*.AVI")
                );
                break;
            case "Image":
            default:
                chooseOption = 0;
                fileChooser.setTitle("View Pictures");
                fileChooser.setInitialDirectory(
                        new File(System.getProperty("user.home"))
                );
                fileChooser.getExtensionFilters().addAll(
                        //                new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("PNG", "*.png")
                );
                break;

        }
//        fileChooser.setTitle("View Pictures");
//        fileChooser.setInitialDirectory(
//                new File(System.getProperty("user.home"))
//        );
//        fileChooser.getExtensionFilters().addAll(
//                //                new FileChooser.ExtensionFilter("All Images", "*.*"),
//                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
//                new FileChooser.ExtensionFilter("PNG", "*.png")
//        );
    }
}
