/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detectplate.ui;

import detectplate.training.SVMTRaining;
import static detectplate.training.SVMTRaining.initSVMTrainingData;
import static detectplate.training.SVMTRaining.trainSVM;
import detectplate.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author SamFisher
 */
public class BorderMainPane extends BorderPane {

    private static final String DEFAULT_URL = "Bike/0437.jpg";
    private Image defaultImage = new Image(getClass().getClassLoader().getResourceAsStream(DEFAULT_URL));

    private TopMenu topMenu;
    private GridMainPane gridCenter;
    HashMap finalResultFilter;
    private GridPane gridRight;
    private VideoView videoView;
    private ImageView imgRight;
    private Label  SVMText;
    private Label  tesseractText;
    private int chooseOption = 0;
    Stage primaryStage;
    Tesseract tesseract;

    public BorderMainPane() {
        initTrainingData();
        this.setMinSize(400, 400);
        this.setPadding(new Insets(0, 10, 0, 10));
        initUI();

    }

    private void initTrainingSVM() {
        SVMTRaining.initSVMTraining();
        SVMTRaining.initSVMTRainingLabels();
        for (int i = 0; i < SVMTRaining.number_of_class; i++) {
            initSVMTrainingData(i);
        }
        trainSVM();
    }

    private void initTrainingData() {
        tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        tesseract.setDatapath("C:\\Users\\SamFisher\\Downloads\\tessdata");
        tesseract.setTessVariable("tessedit_char_whitelist", "0123456789,/ABCDEFGHJKLMNPQRSTUVWXY");
        tesseract.setTessVariable("language_model_penalty_non_freq_dict_word", "1");
        tesseract.setTessVariable("language_model_penalty_non_dict_word ", "1");
        tesseract.setTessVariable("load_system_dawg", "0");

    }

    public BorderMainPane(Stage stage) {
        initTrainingSVM();
        initTrainingData();
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
        imgRight = new ImageView();
        imgRight.setImage(defaultImage);
        imgRight.setStyle("-fx-padding: 5;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;");
//        gridRight.add(imgRight, 0, 0);

        SVMText = new Label ("SVM Text");
        SVMText.setAlignment(Pos.CENTER);
        SVMText.setStyle("-fx-border-color: blue;"
                + "-fx-margin: 5;"
                + "-fx-min-width:50"
                + "-fx-min-height:50"
                + "");
        SVMText.setFont(new Font("Arial", 40));
        gridRight.add(SVMText, 0, 0);
        
        tesseractText = new Label ("Tesseract:");
        tesseractText.setAlignment(Pos.CENTER);
        tesseractText.setStyle("-fx-border-color: blue;"
                + "-fx-margin: 5;"
                + "-fx-min-width:50"
                + "-fx-min-height:50"
                + "");
        tesseractText.setFont(new Font("Arial", 40));
        gridRight.add(tesseractText, 0, 1);
        
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
            listFile.forEach(new Consumer<File>() {
                @Override
                public void accept(File file) {
                    if (chooseOption == 0) {
                        //  choose image
                        gridCenter = new GridMainPane();
                        BorderMainPane.this.setCenter(gridCenter);
                        if (Utils.checkFileIsImage(file.getName())) {

                            finalResultFilter = gridCenter.callImageProcess(file.getAbsolutePath());
                            List listImageCharsCrop = new ArrayList<Mat>();
                            listImageCharsCrop = (List) finalResultFilter.get("listImageCharsCrop");
                            initRightPane();
                            System.err.println("listImageCharsCrop szize:" + listImageCharsCrop.size());
                            int count = 0;
                            StringBuilder fullPlate = new StringBuilder("");
                            StringBuilder fullPlatesvm = new StringBuilder("");
                            for (Object charsMat : listImageCharsCrop) {

                                Mat inputPat = (Mat) charsMat;
                                String detectSVMString = SVMTRaining.testData(inputPat);
                                fullPlatesvm.append(detectSVMString);
                                Image i = Utils.mat2Image((Mat) charsMat);
                                String detectText;
//                                   
                                if (i != null) {
                                    Imgcodecs.imwrite("C:\\Users\\SamFisher\\Downloads\\output-image\\" + String.valueOf(count) + ".JPG", (Mat) charsMat);
                                    try {
                                        detectText = tesseract.doOCR(SwingFXUtils.fromFXImage(i, null));
                                        System.out.println("detect plate to text:" + detectText);
                                        if (detectText != null && !"".equals(detectText)) {
                                            fullPlate.append(detectText.charAt(0));
                                        }
                                        
                                    } catch (TesseractException ex) {
                                        Logger.getLogger(BorderMainPane.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                    count++;
                                }
                            }

                            

                            SVMText.setText(SVMText.getText() +  ":"+ fullPlatesvm.toString());
                            tesseractText.setText(tesseractText.getText() + fullPlate.toString());
                        }
//                    
                    }
                    if (chooseOption == 1) {
                        videoView = new VideoView();
                        BorderMainPane.this.setCenter(videoView);
                        if (Utils.checkFileIsVideo(file.getName())) {
                            System.err.println("type of file video:" + file.getAbsolutePath());
                            videoView.processVideo(file.getAbsolutePath());
                        }
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
