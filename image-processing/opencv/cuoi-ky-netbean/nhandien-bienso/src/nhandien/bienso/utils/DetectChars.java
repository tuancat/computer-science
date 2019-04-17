/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhandien.bienso.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SamFisher
 */
public final class DetectChars {
    // constants for checkIfPossibleChar, this checks one possible char only (does not compare to another char)

    public static final int MIN_PIXEL_WIDTH = 2;
    public static final int MIN_PIXEL_HEIGHT = 8;

    public static final double MIN_ASPECT_RATIO = 0.25;
//public static final double MIN_ASPECT_RATIO = 0.25;
    public static final double MAX_ASPECT_RATIO = 1.0;

    public static final int MIN_PIXEL_AREA = 80;

    // constants for comparing two chars
    public static final double MIN_DIAG_SIZE_MULTIPLE_AWAY = 0.3;
    public static final double MAX_DIAG_SIZE_MULTIPLE_AWAY = 5.0;

    public static final double MAX_CHANGE_IN_AREA = 0.5;

    public static final double MAX_CHANGE_IN_WIDTH = 0.8;
    public static final double MAX_CHANGE_IN_HEIGHT = 0.2;

    public static final double MAX_ANGLE_BETWEEN_CHARS = 12.0;

    // other constants
    public static final int MIN_NUMBER_OF_MATCHING_CHARS = 3;

    public static Boolean checkIfPossibleChar(PossibleChar possibleChar) {
        if (possibleChar.getIntBoundingRectArea() > MIN_PIXEL_AREA && possibleChar.getIntBoundingRectWidth() > MIN_PIXEL_WIDTH
                && possibleChar.getIntBoundingRectHeight() > MIN_PIXEL_HEIGHT
                && possibleChar.getFltAspectRatio() > MIN_ASPECT_RATIO && possibleChar.getFltAspectRatio() < MAX_ASPECT_RATIO
                && possibleChar.getIntBoundingRectHeight() > possibleChar.getIntBoundingRectWidth()) {
            return true;
        } else {
            return false;
        }
    }

    public static List findListOfListsOfMatchingChars(List<PossibleChar> listOfPossibleChars) {
        List<List<PossibleChar>> listOfListsOfMatchingChars = new ArrayList<List<PossibleChar>>();
        for (PossibleChar possibleChar : listOfPossibleChars) {
            List listOfMatchingChars = findListOfMatchingChars(possibleChar, listOfPossibleChars);
            listOfMatchingChars.add(possibleChar);
            if (listOfMatchingChars.size() < MIN_NUMBER_OF_MATCHING_CHARS) {
                continue;
            }
            listOfListsOfMatchingChars.add(listOfMatchingChars);
        }
        List resultListsOfMatchingChars = new ArrayList<PossibleChar>();

        for (List<PossibleChar> listOfMatchingChars : listOfListsOfMatchingChars) {
            if (listOfMatchingChars.size() == 5 || listOfMatchingChars.size() == 4) {
                listOfMatchingChars.forEach((poss) -> {
                    resultListsOfMatchingChars.add(poss);
                });
            }

        }
        
        for (int i = 0; i< resultListsOfMatchingChars.size() -1 ; i++) {
            for (int j = i +  1; j < resultListsOfMatchingChars.size(); j++) {
                if (resultListsOfMatchingChars.get(i) == resultListsOfMatchingChars.get(j)) {
                    resultListsOfMatchingChars.remove(j);
                }
            }
        }
            

        return resultListsOfMatchingChars;
    }

    public static List<PossibleChar> findListOfMatchingChars(PossibleChar possibleChar, List<PossibleChar> listOfChars) {
        List<PossibleChar> listOfMatchingChars = new ArrayList<>();
        for (PossibleChar possibleMatchingChar : listOfChars) {
            if (possibleMatchingChar == possibleChar) {
                continue;
            }

            Double fltDistanceBetweenChars = distanceBetweenChars(possibleChar, possibleMatchingChar);
            Double fltAngleBetweenChars = angleBetweenChars(possibleChar, possibleMatchingChar);

            Double fltChangeInArea = Math.abs(possibleMatchingChar.getIntBoundingRectArea() - possibleChar.getIntBoundingRectArea()) * 1.0 / possibleChar.getIntBoundingRectArea();
            Double fltChangeInWidth = Math.abs(possibleMatchingChar.getIntBoundingRectWidth() - possibleChar.getIntBoundingRectWidth()) * 1.0 / possibleChar.getIntBoundingRectWidth();
            Double fltChangeInHeight = Math.abs(possibleMatchingChar.getIntBoundingRectHeight() - possibleChar.getIntBoundingRectHeight()) * 1.0 / possibleChar.getIntBoundingRectHeight();

            if (fltDistanceBetweenChars < (possibleChar.getFltDiagonalSize() * MAX_DIAG_SIZE_MULTIPLE_AWAY)
                    && fltAngleBetweenChars < MAX_ANGLE_BETWEEN_CHARS
                    && fltChangeInArea < MAX_CHANGE_IN_AREA
                    && fltChangeInWidth < MAX_CHANGE_IN_WIDTH
                    && fltChangeInHeight < MAX_CHANGE_IN_HEIGHT) {
                listOfMatchingChars.add(possibleMatchingChar);
            }

        }
        return listOfMatchingChars;
    }

    public static Double distanceBetweenChars(PossibleChar firstChar, PossibleChar secondChar) {
        Double intX = Math.abs(firstChar.getIntCenterX() - secondChar.getIntCenterX()) * 1.0;
        Double intY = Math.abs(firstChar.getIntCenterY() - secondChar.getIntCenterY()) * 1.0;
        return Math.sqrt((intX * intX) + (intY * intY));
    }

    public static Double angleBetweenChars(PossibleChar firstChar, PossibleChar secondChar) {
        Double fltAdj = Math.abs(firstChar.getIntCenterX() - secondChar.getIntCenterX()) * 1.0;
        Double fltOpp = Math.abs(firstChar.getIntCenterY() - secondChar.getIntCenterY()) * 1.0;
        Double fltAngleInRad;
        if (fltAdj != 0.0) {
            fltAngleInRad = Math.atan(fltOpp / fltAdj);
        } else {
            fltAngleInRad = 1.5708;
        }
        return fltAngleInRad * (180.0 / Math.PI);
    }
//    public 
}
