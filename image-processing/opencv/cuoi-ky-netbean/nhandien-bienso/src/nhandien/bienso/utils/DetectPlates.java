/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhandien.bienso.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author SamFisher
 */
public final class DetectPlates {

    public static HashMap findPossibleCharsInSceneFilter1(Mat imgThresh) {
        HashMap result = new HashMap<String, Object>();
        
        List<PossibleChar> listOfPossibleChars = new ArrayList<PossibleChar>();
        int intCountOfPossibleChars = 0;
        Mat imgThreshCopy = imgThresh.clone();
        List<MatOfPoint> contours = new ArrayList<>();

        Mat hierarchy = new Mat();
        Imgproc.findContours(imgThreshCopy, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        for (int i = 0; i < contours.size(); i++) {
//            Imgproc.drawContours(imgContours, contours, i, new Scalar(255.0, 255.0, 255.0));
            PossibleChar possibleChar = new PossibleChar(contours.get(i));
            if (DetectChars.checkIfPossibleChar(possibleChar)) {
                intCountOfPossibleChars++;
                listOfPossibleChars.add(possibleChar);
            }
        }

        Mat imgContours = Mat.zeros(imgThreshCopy.size(), CvType.CV_8UC3);
        contours = new ArrayList<>();
        for (PossibleChar poss : listOfPossibleChars) {
            contours.add(poss.getContour());
        }
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(imgContours, contours, i, new Scalar(255.0, 255.0, 255.0));
        }
        System.out.println("");
        System.out.println("step 2 - len(listOfPossibleCharsInScene) =" + listOfPossibleChars.size());
        
        result.put("imgContours", imgContours);
        result.put("listOfPossibleChars", listOfPossibleChars);
        return result;
    }
    
    public static HashMap findPossibleCharsInSceneFilter2(Mat imgThresh, List<PossibleChar> listOfPossibleChars) {
        
        HashMap result = new HashMap<String, Object>();
        
        int intCountOfPossibleChars = 0;
        Mat imgThreshCopy = imgThresh.clone(); 
        
        
        Mat imgContours = Mat.zeros(imgThreshCopy.size(), CvType.CV_8UC3);
        List<MatOfPoint> contours = new ArrayList<>();
        
        List<PossibleChar> listOfListsOfMatchingCharsInScene  = DetectChars.findListOfListsOfMatchingChars(listOfPossibleChars);
        
        listOfListsOfMatchingCharsInScene.forEach((poss) -> {
            contours.add(poss.getContour());
            System.out.println("[" + poss.getIntBoundingRectX() + "," + poss.getIntBoundingRectY() + "]");
        });
        
        for (int i = 0; i < contours.size(); i++) {
                Imgproc.drawContours(imgContours, contours, i, new Scalar(255.0, 255.0, 255.0));
        }
        System.out.println("");
        System.out.println("step 3 - len(listOfListsOfMatchingCharsInScene) =" + listOfListsOfMatchingCharsInScene.size());
        
        result.put("imgContours", imgContours);
        result.put("listOfListsOfMatchingCharsInScene", listOfListsOfMatchingCharsInScene);
        result.put("findRectLicPlates", findRectLicPlates(listOfListsOfMatchingCharsInScene));
        return result;
    }
    
    public static Rect findRectLicPlates( List<PossibleChar> listOfListsOfMatchingCharsInScene ) {
        int minX = listOfListsOfMatchingCharsInScene.get(0).getIntCenterX();
        int posMinX = 0;
        int minY = listOfListsOfMatchingCharsInScene.get(0).getIntCenterY();
        int posMinY = 0;
        for (int i =1 ; i< listOfListsOfMatchingCharsInScene.size(); i++) {
            if ( listOfListsOfMatchingCharsInScene.get(i).getIntCenterX() < minX ) {
                posMinX = i;
                minX = listOfListsOfMatchingCharsInScene.get(i).getIntBoundingRectX();
            }
            
            if ( listOfListsOfMatchingCharsInScene.get(i).getIntCenterY() < minY ) {
                posMinY = i;
                minY = listOfListsOfMatchingCharsInScene.get(i).getIntCenterY();
            }
        }
        
        Rect rect = new Rect(minX - 10, minY - 20, listOfListsOfMatchingCharsInScene.get(posMinX).getIntBoundingRectWidth() * 10
                , listOfListsOfMatchingCharsInScene.get(posMinX).getIntBoundingRectHeight() * 3);
        return rect;
    }

    public static Mat findBiggestInScene(Mat imgThresh) {
        List<PossibleChar> listOfPossibleChars = new ArrayList<PossibleChar>();
        int intCountOfPossibleChars = 0;
        Mat imgThreshCopy = imgThresh.clone();
        List<MatOfPoint> contours = new ArrayList<>();

        Mat hierarchy = new Mat();

        Imgproc.findContours(imgThreshCopy, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        for (int i = 0; i < contours.size(); i++) {
//            Imgproc.drawContours(imgContours, contours, i, new Scalar(255.0, 255.0, 255.0));
            PossibleChar possibleChar = new PossibleChar(contours.get(i));
            listOfPossibleChars.add(possibleChar);

        }
        int biggestArea = listOfPossibleChars.get(0).getIntBoundingRectArea();
        PossibleChar biggestPoss = listOfPossibleChars.get(0);
        for (int i = 0; i < listOfPossibleChars.size(); i++) {
            if (biggestArea < listOfPossibleChars.get(i).getIntBoundingRectArea()) {
                biggestArea = listOfPossibleChars.get(i).getIntBoundingRectArea();
                biggestPoss = listOfPossibleChars.get(i);
            }
        }
        Mat imgContours = Mat.zeros(imgThreshCopy.size(), CvType.CV_8UC3);
        contours = new ArrayList<>();
        contours.add(biggestPoss.getContour());
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(imgContours, contours, i, new Scalar(255.0, 255.0, 255.0));
        }
        System.out.println("step 2 - find findBiggestInScene =" + listOfPossibleChars.size());
        return imgContours;
    }

    public static Mat findPossibleDrawContoursInScene(Mat imgThresh) {
        Mat imgThreshCopy = imgThresh.clone();
        List<MatOfPoint> contours = new ArrayList<>();

        Mat hierarchy = new Mat();
        Mat imgContours = Mat.zeros(imgThreshCopy.size(), CvType.CV_8UC3);
        Imgproc.findContours(imgThreshCopy, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(imgContours, contours, i, new Scalar(255.0, 255.0, 255.0));

        }
        return imgContours;
    }
}
