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
public class SealBlock {
    private String header;
    private String body;

    public SealBlock(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public SealBlock() {
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
