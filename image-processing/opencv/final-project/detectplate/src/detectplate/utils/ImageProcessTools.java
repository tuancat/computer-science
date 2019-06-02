/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detectplate.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author SamFisher
 */
public final class ImageProcessTools {

    private static Random rng = new Random(12345);
    public static Rect plateRect = new Rect();

    public static Mat convertImageToGray(Mat src) {
        Mat newSize = new Mat();
        Imgproc.resize(src, newSize, new Size(640, 480), 0, 0, Imgproc.INTER_CUBIC);
        Imgproc.blur(newSize, newSize, new Size(3, 3));
        Mat dest = new Mat();
        Imgproc.cvtColor(newSize, dest, Imgproc.COLOR_BGR2GRAY);

        return dest;
    }

    public static Mat removeNoiseAndequalizeHistImage(Mat src) {
        Mat dest = new Mat();
//        Imgproc.bilateralFilter(src, dest, 9, 75, 75);
        Core.normalize(src, dest, 0, 255, Core.NORM_MINMAX);
        Imgproc.equalizeHist(dest, dest);
//        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
//        Imgproc.morphologyEx(dest, dest, Imgproc.MORPH_OPEN, kernel);

        return dest;
    }

    public static Mat getMatMorphologyEx(Mat src) {
        Mat dest = new Mat();
        Mat newSrc = new Mat();
//        Imgproc.pyrDown(src, newSrc);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(src, dest, Imgproc.MORPH_OPEN, kernel, new Point(0, 0), 20);

        return dest;
    }

    public static Mat removeBackground(Mat src) {
        Mat endResult = new Mat();

        Mat greyMat = ImageProcessTools.convertImageToGray(src);
        Mat removeNoise = ImageProcessTools.removeNoiseAndequalizeHistImage(greyMat);
        Mat getMoth = ImageProcessTools.getMatMorphologyEx(removeNoise);
        Core.subtract(removeNoise, getMoth, endResult);
        Imgproc.threshold(endResult, endResult, 0, 255, Imgproc.THRESH_OTSU);

        return endResult;
    }

    public static Mat candyImage(Mat src) {
        Mat endResult = new Mat();
        Imgproc.Canny(src, endResult, 250, 255);
        Mat kernel = Mat.ones(new Size(3, 3), CvType.CV_16U);
        Imgproc.dilate(endResult, endResult, kernel);
        Core.normalize(endResult, endResult, 0, 255, Core.NORM_MINMAX);
        return endResult;
    }

    public static Mat newWay(Mat src) {
        Mat endResult = new Mat();
        Mat dest = convertImageToGray(src);

        Mat pyDown = new Mat();
        Imgproc.pyrDown(dest, pyDown);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));

        Mat newImg = new Mat();
        Imgproc.morphologyEx(pyDown, newImg, Imgproc.MORPH_OPEN, kernel, new Point(0, 0), 20);
        Core.normalize(newImg, newImg, 0, 255, Core.NORM_MINMAX);

        Imgproc.threshold(newImg, endResult, 0, 255, Imgproc.THRESH_BINARY);

        return endResult;
    }

    public static Mat find(Mat src, Rect resultRect) {
        Random rng = new Random(12345);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(src, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat drawing = Mat.zeros(src.size(), CvType.CV_8UC3);

        MatOfPoint[] newArrayContours = new MatOfPoint[contours.size()];
        Double[] area = new Double[newArrayContours.length];
        for (int i = 0; i < contours.size(); i++) {
            area[i] = Imgproc.contourArea(contours.get(i).t(), true);
            newArrayContours[i] = contours.get(i);
        }

        for (int i = 0; i < newArrayContours.length - 1; i++) {
            for (int j = i + 1; j < newArrayContours.length; j++) {
                if (area[i] < area[j]) {
                    MatOfPoint temp = newArrayContours[i];
                    newArrayContours[i] = newArrayContours[j];
                    newArrayContours[j] = temp;

                    Double temp1 = area[i];
                    area[i] = area[j];
                    area[j] = temp1;

                }
            }
        }
        contours = new ArrayList<>();

        for (int i = 0; i < newArrayContours.length; i++) {
//            System.err.println("area1:" + area[i]);
            contours.add(newArrayContours[i]);
//            Double area1 = Imgproc.contourArea(newArrayContours[i].t(), true);
//            System.err.println("area1:" + area1);
//            MatOfPoint2f  NewMtx = new MatOfPoint2f( contours.get(i).toArray() );
//            Double d= Imgproc.arcLength(NewMtx, true);
//            Imgproc.approxPolyDP(NewMtx, NewMtx, 0.06 * d, true);
//            
//            if (NewMtx.toArray().length ==4) {
//                Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
//                Imgproc.drawContours(drawing, contours, i, color, 2, 8, hierarchy, 0, new Point());
//            }

        }

        for (int i = 0; i < contours.size(); i++) {
            MatOfPoint2f NewMtx = new MatOfPoint2f(contours.get(i).toArray());
            Double d = Imgproc.arcLength(NewMtx, true);
            Double epsilon = 0.09 * d;
            Imgproc.approxPolyDP(NewMtx, NewMtx, epsilon, true);
//            
//            if (NewMtx.toArray().length ==4) {
//                Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
//                Imgproc.drawContours(drawing, contours, i, color, 2, 8, hierarchy, 0, new Point());
//            }
            if (i <= 5 && NewMtx.toArray().length == 4) {
                Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
                Imgproc.drawContours(drawing, contours, i, color, 2, 8, hierarchy, 0, new Point());
                resultRect = Imgproc.boundingRect(contours.get(i));
//                Imgproc.rectangle(drawing, resultRect, new Scalar(0,255,0), 3);
                plateRect = resultRect;
                break;
            }

        }

        return drawing;
    }
    //save image to file
    public static void saveToFile(Image image, String fileName) {
        File outputFile = new File("C:\\Users\\SamFisher\\Pictures\\output-images\\" + fileName);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "jpg", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    // detect and draw plate
    public static void drawPlate(File imageFile) {
        Mat src = Imgcodecs.imread(imageFile.getAbsolutePath());
        Mat resizeNewImage = ImageProcessTools.newWay(src);
//        Mat newWay = ImageProcessTools.newWay(src);
        Mat greyMat = ImageProcessTools.convertImageToGray(src);
        Mat removeNoise = ImageProcessTools.removeNoiseAndequalizeHistImage(greyMat);
        Mat getMoth = ImageProcessTools.getMatMorphologyEx(removeNoise);
        Mat removeAllBack = ImageProcessTools.removeBackground(src);
        Mat candyImage = ImageProcessTools.candyImage(removeAllBack);
        Rect detectRect = new Rect();
        Mat newCoures = ImageProcessTools.find(candyImage, detectRect);
        
        Imgproc.rectangle(src, ImageProcessTools.plateRect, new Scalar(0, 255, 0), 3);
        Image resultImage = Utils.mat2Image(src);
        
        saveToFile(resultImage, imageFile.getName());
    }

}
