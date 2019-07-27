/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sam.baomat.cuoiky.sercurity;

import com.sam.baomat.cuoiky.model.Block;
import com.sam.baomat.cuoiky.model.PhieuClass;
import com.sam.baomat.cuoiky.model.SealBlock;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SamFisher
 */
public class ManageBlock {

    public static List<Block> listBlock = new ArrayList<Block>();
    public static List<SealBlock> listSealBlock = new ArrayList<SealBlock>();
    public static int lengthOfBlock = 0;

    public static void createBlock(int pollNumber, List<PhieuClass> listPhieu) {
        int lengthOfRandomNumber = 3;
        if (pollNumber == 0) {
            lengthOfRandomNumber = 3;
        } else {
            lengthOfRandomNumber = pollNumber + 2;
        }
        StringBuilder sb1 = new StringBuilder(String.valueOf(pollNumber));
        sb1.append(MD5.randomStringWithLength(lengthOfRandomNumber));
        String headerWithHash = MD5.hashData(sb1.toString());
        if (pollNumber != 0) {
            if (listBlock.get(pollNumber - 1) != null) {
                String hashLastBlock = MD5.hashData(listBlock.get(pollNumber - 1).getHeader() + listBlock.get(pollNumber - 1).getBody());
                headerWithHash += hashLastBlock;
            }
        }
        StringBuilder sb2 = new StringBuilder("");

        for (PhieuClass p : listPhieu) {

            if (p.getPollNumber() == pollNumber) {
                sb2.append(p.getResult());
            }
        }

        String body = sb2.toString();

        Block b = new Block(headerWithHash, body);
        lengthOfBlock = pollNumber + 1;
        System.out.println("lengthOfBlock:" + lengthOfBlock);
        System.out.println("headerWithHash:" + b.getHeader());
        System.out.println("body of block:" + b.getBody());
        listBlock.add(pollNumber, b);
    }

    public static void createSealBlock() {
        if (lengthOfBlock > 0) {
            for (int i = 0; i < listBlock.size(); i++) {
                SealBlock sealBlock = new SealBlock(listBlock.get(i).getHeader(), MD5.hashData(listBlock.get(i).getBody()));
                listSealBlock.add(sealBlock);
            }
        }
    }

    public static void main(String[] args) {

    }

}
