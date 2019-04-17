/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhandien.bienso.utils;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author SamFisher
 */
public final class PreProcess {
    public static final Size GAUSSIAN_SMOOTH_FILTER_SIZE = new Size(5, 5);
    public static final int ADAPTIVE_THRESH_BLOCK_SIZE = 19;
    public static final int ADAPTIVE_THRESH_WEIGHT = 9;

    public static Mat extractValue(Mat imgOriginal) {

        Mat imgHSV = Mat.zeros(imgOriginal.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(imgOriginal, imgHSV, Imgproc.COLOR_BGR2GRAY);

        return imgHSV;

    }

    public static Mat preProcess(Mat imgOriginal) {
        Mat imgThresh = Mat.zeros(imgOriginal.size(), CvType.CV_8UC1);
        Mat imgGrayscale = new Mat();
        Imgproc.cvtColor(imgOriginal, imgGrayscale, Imgproc.COLOR_BGR2GRAY);
        Mat imgMaxContrastGrayscale = maximizeContrast(imgGrayscale);
        Mat imgBlurred = Mat.zeros(imgOriginal.size(), CvType.CV_8UC1);
        Imgproc.GaussianBlur(imgMaxContrastGrayscale, imgBlurred, GAUSSIAN_SMOOTH_FILTER_SIZE, 0);
        Imgproc.adaptiveThreshold(imgBlurred, imgThresh, 255.0, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV
                , ADAPTIVE_THRESH_BLOCK_SIZE, ADAPTIVE_THRESH_WEIGHT);
        
        return imgThresh;
    }

    public static Mat maximizeContrast(Mat imgGrayScale) {
        Mat imgTopHat = Mat.zeros(imgGrayScale.size(), CvType.CV_8UC1);
        Mat imgBlackHat = Mat.zeros(imgGrayScale.size(), CvType.CV_8UC1);
        Mat imgGrayscalePlusTopHat = new Mat();
        Mat imgGrayscalePlusTopHatMinusBlackHat = new Mat();
        Mat structuringElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));

        Imgproc.morphologyEx(imgGrayScale, imgTopHat, Imgproc.MORPH_TOPHAT, structuringElement);
        Imgproc.morphologyEx(imgGrayScale, imgBlackHat, Imgproc.MORPH_BLACKHAT, structuringElement);

        Core.add(imgGrayScale, imgTopHat, imgGrayscalePlusTopHat);
        Core.subtract(imgGrayscalePlusTopHat, imgBlackHat, imgGrayscalePlusTopHatMinusBlackHat);
        
        return imgGrayscalePlusTopHatMinusBlackHat;
    }
}
