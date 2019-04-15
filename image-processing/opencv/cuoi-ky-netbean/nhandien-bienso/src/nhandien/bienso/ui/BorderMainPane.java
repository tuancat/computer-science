/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhandien.bienso.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author SamFisher
 */
public class BorderMainPane extends BorderPane{
    private TopMenu topMenu;
    Stage primaryStage;
    public BorderMainPane() {
        this.setMinSize(400, 400);
        this.setPadding(new Insets(0, 10, 0, 10));
        initUI();
    }
    public BorderMainPane(Stage stage) {
        this.primaryStage = stage;
        this.setMinSize(400, 400);
        this.setPadding(new Insets(0, 10, 0, 10));
        initUI();
    }
    private void initUI() {
        topMenu = new TopMenu(primaryStage);
        this.setTop(topMenu);
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
    
    
}
