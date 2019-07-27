/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sam.baomat.cuoiky.ui;

import com.sam.baomat.cuoiky.model.Block;
import com.sam.baomat.cuoiky.model.SealBlock;
import com.sam.baomat.cuoiky.sercurity.ManageBlock;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author SamFisher
 */
public class ResultPane extends GridPane {

    private GridPane blockUI;
    private GridPane sealBlockUI;

    public ResultPane() {
        initUI();
        initBlockUI();
        initSealBlockUI();
    }

    private void initBlockUI() {
        blockUI = new GridPane();
        blockUI.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: black;");
        if (ManageBlock.lengthOfBlock > 0) {

            for (int i = 0; i < ManageBlock.listBlock.size(); i++) {
                Block b = ManageBlock.listBlock.get(i);
                BlockPane blockPane = new BlockPane(b.getHeader(), b.getBody());
                blockUI.add(blockPane, i, 1);
            }

            this.add(blockUI, 1, 1);
        }
    }

    private void initSealBlockUI() {
        sealBlockUI = new GridPane();
        sealBlockUI.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: red;");
        if (ManageBlock.lengthOfBlock > 0) {

            for (int i = 0; i < ManageBlock.listSealBlock.size(); i++) {
                SealBlock s = ManageBlock.listSealBlock.get(i);
                BlockPane blockPane = new BlockPane(s.getHeader(), s.getBody());
                sealBlockUI.add(blockPane, i, 1);
            }

            this.add(sealBlockUI, 1, 2);
        }
    }

    private void initUI() {
        Label titleBlock = new Label("Block:");
        Label titleSealBlock = new Label("Sealing Block:");
        this.add(titleBlock, 0, 1);
        this.add(titleSealBlock, 0, 2);
    }

}
