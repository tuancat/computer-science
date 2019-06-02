/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detectplate.utils;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author SamFisher
 */
public class PossibleChar {

    private MatOfPoint contour;
    private Rect boundingRect;
    private int intBoundingRectX;
    private int intBoundingRectY;
    private int intBoundingRectWidth;
    private int intBoundingRectHeight;
    private int intBoundingRectArea;
    private int intCenterX;
    private int intCenterY;
    private Double fltDiagonalSize;
    private Double fltAspectRatio;

    public PossibleChar() {
    }

    public PossibleChar(MatOfPoint contour) {
        this.contour = contour;
        boundingRect = Imgproc.boundingRect(contour.clone());
        intBoundingRectX = boundingRect.x;
        intBoundingRectY = boundingRect.y;
        intBoundingRectWidth = boundingRect.width;
        intBoundingRectHeight = boundingRect.height;
        intBoundingRectArea = intBoundingRectWidth * intBoundingRectHeight;
        intCenterX = (intBoundingRectX + intBoundingRectX + intBoundingRectWidth) / 2;
        intCenterY = (intBoundingRectY + intBoundingRectY + intBoundingRectHeight) / 2;
        fltDiagonalSize = Math.sqrt((intBoundingRectWidth * intBoundingRectWidth) + (intBoundingRectHeight + intBoundingRectHeight));
        fltAspectRatio = (intBoundingRectWidth * 1.0 / intBoundingRectHeight);
    }

    public MatOfPoint getContour() {
        return contour;
    }

    public void setContour(MatOfPoint contour) {
        this.contour = contour;
    }

    public Rect getBoundingRect() {
        return boundingRect;
    }

    public void setBoundingRect(Rect boundingRect) {
        this.boundingRect = boundingRect;
    }

    public int getIntBoundingRectX() {
        return intBoundingRectX;
    }

    public void setIntBoundingRectX(int intBoundingRectX) {
        this.intBoundingRectX = intBoundingRectX;
    }

    public int getIntBoundingRectY() {
        return intBoundingRectY;
    }

    public void setIntBoundingRectY(int intBoundingRectY) {
        this.intBoundingRectY = intBoundingRectY;
    }

    public int getIntBoundingRectWidth() {
        return intBoundingRectWidth;
    }

    public void setIntBoundingRectWidth(int intBoundingRectWidth) {
        this.intBoundingRectWidth = intBoundingRectWidth;
    }

    public int getIntBoundingRectHeight() {
        return intBoundingRectHeight;
    }

    public void setIntBoundingRectHeight(int intBoundingRectHeight) {
        this.intBoundingRectHeight = intBoundingRectHeight;
    }

    public int getIntBoundingRectArea() {
        return intBoundingRectArea;
    }

    public void setIntBoundingRectArea(int intBoundingRectArea) {
        this.intBoundingRectArea = intBoundingRectArea;
    }

    public int getIntCenterX() {
        return intCenterX;
    }

    public void setIntCenterX(int intCenterX) {
        this.intCenterX = intCenterX;
    }

    public int getIntCenterY() {
        return intCenterY;
    }

    public void setIntCenterY(int intCenterY) {
        this.intCenterY = intCenterY;
    }

    public Double getFltDiagonalSize() {
        return fltDiagonalSize;
    }

    public void setFltDiagonalSize(Double fltDiagonalSize) {
        this.fltDiagonalSize = fltDiagonalSize;
    }

    public Double getFltAspectRatio() {
        return fltAspectRatio;
    }

    public void setFltAspectRatio(Double fltAspectRatio) {
        this.fltAspectRatio = fltAspectRatio;
    }

   

}
