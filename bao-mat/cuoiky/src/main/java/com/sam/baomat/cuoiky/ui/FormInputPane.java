/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sam.baomat.cuoiky.ui;

import com.sam.baomat.cuoiky.model.InputModel;
import com.sam.baomat.cuoiky.model.PollStationModel;
import com.sam.baomat.cuoiky.sercurity.ManageBlock;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author SamFisher
 */
public class FormInputPane extends GridPane {
    
    private InputModel saveInputModel = new InputModel();
    NumberTextField numberUngVien;
    NumberTextField numberPool;
    NumberTextField numberPeoplePoll;
    
    public FormInputPane() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        this.initUI();
    }
    
    private void initUI() {
        Label userName = new Label("Số ứng viên:");
        this.add(userName, 0, 1);
        
        numberUngVien = new NumberTextField();
        this.add(numberUngVien, 1, 1);
        
        Label numPollLabel = new Label("Số Poll Station:");
        this.add(numPollLabel, 0, 2);
        
        numberPool = new NumberTextField();
        this.add(numberPool, 1, 2);
        
        Label numPeoplePollLabel = new Label("Số dân/ Station:");
        this.add(numPeoplePollLabel, 0, 3);
        
        numberPeoplePoll = new NumberTextField();
        this.add(numberPeoplePoll, 1, 3);
        
        Button btn = new Button("Bắt đầu");
        final Button btnSeal = new Button("Niêm phong");
        btnSeal.setDisable(true);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        hbBtn.getChildren().add(btnSeal);
        this.add(hbBtn, 1, 4);

//        Label pw = new Label("Password:");
//        this.add(pw, 0, 2);
//
//        PasswordField pwBox = new PasswordField();
//        this.add(pwBox, 1, 2);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveInputModel.setNumberUngvien(Integer.valueOf(numberUngVien.getText()));
                saveInputModel.setNumberPoll(Integer.valueOf(numberPool.getText()));
                saveInputModel.setNumberPeople(Integer.valueOf(numberPeoplePoll.getText()));
                
                saveInputModel.ramdomInput();
                
                List<PollStationModel> listPollStation = saveInputModel.getListPollStation();
                
                for (int i = 0; i< listPollStation.size() ; i++) {
                    ManageBlock.createBlock(i, listPollStation.get(i).getListPhieu());
                }
                btnSeal.setDisable(false);
            }
        });
        
        btnSeal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                ManageBlock.createSealBlock();
            }
        });
    }

    public InputModel getSaveInputModel() {
        return saveInputModel;
    }

    public void setSaveInputModel(InputModel saveInputModel) {
        this.saveInputModel = saveInputModel;
    }
    
}
