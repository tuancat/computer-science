/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sam.baomat.cuoiky.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SamFisher
 */
public class PollStationModel {
    private int pollNumber;
    private String pollUID;
    private List<PhieuClass> listPhieu = new ArrayList<>();

    public PollStationModel() {
    }

    public PollStationModel(int pollNumber) {
        this.pollNumber = pollNumber;
    }

    public int getPollNumber() {
        return pollNumber;
    }

    public void setPollNumber(int pollNumber) {
        this.pollNumber = pollNumber;
    }

    public String getPollUID() {
        return pollUID;
    }

    public void setPollUID(String pollUID) {
        this.pollUID = pollUID;
    }

    public List<PhieuClass> getListPhieu() {
        return listPhieu;
    }

    public void setListPhieu(List<PhieuClass> listPhieu) {
        this.listPhieu = listPhieu;
    }
    
    
}
