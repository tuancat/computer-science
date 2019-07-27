/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sam.baomat.cuoiky.ui;

import com.sam.baomat.cuoiky.model.PhieuClass;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author SamFisher
 */
public class PhieuUI extends VBox {
    PhieuClass modelPhieu = new PhieuClass();
    public PhieuUI() {
        this.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: black;");
        modelPhieu = new PhieuClass();
    }
    public void initUI() {
        Label pollUIDLabel = new Label("Poll UID:" + modelPhieu.getPollUID());
        Label pollNumber = new Label("Poll Number:" + modelPhieu.getPollNumber());
        Label result = new Label("Kết quả:" + modelPhieu.getResult());
        
//        this.
        
    }
    
}
