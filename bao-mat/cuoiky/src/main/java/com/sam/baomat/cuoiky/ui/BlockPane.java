/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sam.baomat.cuoiky.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author SamFisher
 */
public class BlockPane extends GridPane {

    private String header;
    private String body;

    public BlockPane() {
        initUI();
    }

    private void initUI() {
        this.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: red;");

        Label headerLabel = new Label("header:" +header);
        Label bodyLabel = new Label("body:" + body);
        this.add(headerLabel, 0, 1);
        this.add(bodyLabel, 0, 2);
    }

    public BlockPane(String header, String body) {
        this.header = header;
        this.body = body;

        initUI();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
