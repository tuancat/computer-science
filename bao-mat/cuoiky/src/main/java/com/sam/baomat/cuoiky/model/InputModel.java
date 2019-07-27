/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sam.baomat.cuoiky.model;

import com.sam.baomat.cuoiky.sercurity.MD5;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author SamFisher
 */
public class InputModel {
    
    private int numberUngvien;
    private int numberPoll;
    private int numberPeople;
    private int totalNumberPeople;
    private List<PollStationModel> listPollStation = new ArrayList<>();
    
    public List<PhieuClass> ramdomInput() {
        List<PhieuClass> list = new ArrayList<>();
        totalNumberPeople = numberPoll * numberPeople;
        for (int i = 0; i < numberPoll; i++) {
            list = new ArrayList<>();
            for (int j = 0; j < numberPeople; j++) {
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(numberUngvien) + 1;
                PhieuClass p = new PhieuClass(i, randomInt);
                list.add(p);
            }
            PollStationModel pollStationModel = new PollStationModel(i);
            pollStationModel.setListPhieu(list);
            pollStationModel.setPollUID("P" + i);
            listPollStation.add(i, pollStationModel);
            
        }
        
        for (PhieuClass p : list) {
            System.err.println("rand:" + p.getResult());
            System.err.println("rand poll ID:" + p.getPollUID());
        }
        System.out.println("randome string: " + MD5.randomStringWithLength(3));
        return list;
    }
    
    public Block hashDataToCreateBlock(List<PhieuClass> listPhieu, int pollNumber) {
        int lengthOfRandomNumber = 3;
        if (pollNumber == 0) {
            lengthOfRandomNumber = 3;
        } else {
            lengthOfRandomNumber = pollNumber + 2;
        }
        StringBuilder sb1 = new StringBuilder(String.valueOf(pollNumber));
        sb1.append(MD5.randomStringWithLength(lengthOfRandomNumber));
        String headerWithHash = MD5.hashData(sb1.toString());
        
        StringBuilder sb2 = new StringBuilder("");
        
        for (PhieuClass p : listPhieu) {
            
            if (p.getPollNumber() == pollNumber) {
                sb2.append(p.getResult());
            }
        }
        
        String body = sb2.toString();
        
        Block b = new Block(headerWithHash, body);
        
        return b;
        
    }
    
    public int getNumberUngvien() {
        return numberUngvien;
    }
    
    public void setNumberUngvien(int numberUngvien) {
        this.numberUngvien = numberUngvien;
    }
    
    public int getNumberPoll() {
        return numberPoll;
    }
    
    public void setNumberPoll(int numberPoll) {
        this.numberPoll = numberPoll;
    }
    
    public int getNumberPeople() {
        return numberPeople;
    }
    
    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }
    
    public int getTotalNumberPeople() {
        return totalNumberPeople;
    }
    
    public void setTotalNumberPeople(int totalNumberPeople) {
        this.totalNumberPeople = totalNumberPeople;
    }
    
    public List<PollStationModel> getListPollStation() {
        return listPollStation;
    }
    
    public void setListPollStation(List<PollStationModel> listPollStation) {
        this.listPollStation = listPollStation;
    }
    
}
