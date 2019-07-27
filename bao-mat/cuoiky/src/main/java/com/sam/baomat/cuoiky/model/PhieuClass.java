/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sam.baomat.cuoiky.model;

/**
 *
 * @author SamFisher
 */
public class PhieuClass {
    private int pollNumber;
    private String pollUID;
    private int result;

    public PhieuClass() {
        pollUID = "P" + pollNumber;
    }

    public PhieuClass(int pollNumber, int result) {
        pollUID = "P" + pollNumber;
        this.pollNumber = pollNumber;
        this.result = result;
    }

    public int getPollNumber() {
        return pollNumber;
    }

    public void setPollNumber(int pollNumber) {
        this.pollNumber = pollNumber;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getPollUID() {
        return pollUID;
    }

    public void setPollUID(String pollUID) {
        this.pollUID = pollUID;
    }
    
    
}
