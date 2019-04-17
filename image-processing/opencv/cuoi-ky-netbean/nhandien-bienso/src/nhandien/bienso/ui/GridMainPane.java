/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhandien.bienso.ui;

import java.util.HashMap;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import nhandien.bienso.utils.DetectPlates;
import nhandien.bienso.utils.PossibleChar;
import nhandien.bienso.utils.PreProcess;
import nhandien.bienso.utils.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author SamFisher
 */
public class GridMainPane extends GridPane {

    private static final String DEFAULT_URL = "Bike/0437.jpg";
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private Image defaultImage = new Image(getClass().getClassLoader().getResourceAsStream(DEFAULT_URL));

    public GridMainPane() {
        // TODO Auto-generated constructor stub
        this.setMinSize(400, 400);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(0, 10, 0, 10));
        initImage();
    }

    private void initImage() {

        System.err.println("init image");
        this.img1 = new ImageView(defaultImage);
        this.img1.setX(600);
        this.img1.setY(400);

        this.img2 = new ImageView(defaultImage);
        this.img3 = new ImageView(defaultImage);
        this.img2.setX(600);
        this.img2.setY(400);
        this.img4 = new ImageView(defaultImage);
        this.add(img1, 0, 0);
        this.add(img2, 1, 0);
        this.add(img3, 0, 1);
        this.add(img4, 1, 1);
        this.img3.setX(600);
        this.img3.setY(400);
        callImageProcess();
    }

    private void callImageProcess() {
        Image i = new Image(getClass().getClassLoader().getResourceAsStream(DEFAULT_URL));
        Mat src = Imgcodecs.imread("resources/" + DEFAULT_URL);
//        Mat resizeNewImage = ImageProcessTools.newWay(src);
////        Mat newWay = ImageProcessTools.newWay(src);
//        Mat greyMat = ImageProcessTools.convertImageToGray(src);
//        Mat removeNoise = ImageProcessTools.removeNoiseAndequalizeHistImage(greyMat);
//        Mat getMoth = ImageProcessTools.getMatMorphologyEx(removeNoise);
//        Mat removeAllBack = ImageProcessTools.removeBackground(src);
//        Mat candyImage = ImageProcessTools.candyImage(removeAllBack);
//        Rect detectRect = new Rect();
//        Mat newCoures = ImageProcessTools.find(candyImage, detectRect);
        this.img1.setImage(Utils.mat2Image(src));
        Mat imgThresh = PreProcess.preProcess(src); //quá trình tiền sử lý
        
        
        //quá trình xử lý loc ảnh lần 1 để tìm plate -> kết quả đuôc hiển thị tại hình ảnh 2 
        HashMap resultFilter1 = DetectPlates.findPossibleCharsInSceneFilter1(imgThresh);
        Mat imgContours = (Mat) resultFilter1.get("imgContours");
        List<PossibleChar> listOfPossibleChars = (List<PossibleChar>) resultFilter1.get("listOfPossibleChars");
        this.img2.setImage(Utils.mat2Image(imgContours));
        
        
        
         //quá trình xử lý loc ảnh lần 2 để tìm plate -> kết quả đuôc hiển thị tại hình ảnh 3
        HashMap resultFilter2 = DetectPlates.findPossibleCharsInSceneFilter2(imgThresh, listOfPossibleChars);
        Mat imgContoursFilter2 = (Mat) resultFilter2.get("imgContours");
        Rect rectLibPlate = (Rect) resultFilter2.get("findRectLicPlates");
        this.img3.setImage(Utils.mat2Image(imgContoursFilter2));
        
        Imgproc.rectangle(src, rectLibPlate, new Scalar(0, 255, 0), 3);
        this.img4.setImage(Utils.mat2Image(src));
        
        
        
//        Mat imgBiggestContours = DetectPlates.findBiggestInScene(imgThresh);
//        this.img2.setImage(Utils.mat2Image(PreProcess.preProcess(src)));
        
//        this.img3.setImage(Utils.mat2Image(imgBiggestContours));
//        System.out.println("detectRect:" + ImageProcessTools.plateRect.toString());
//        Core.
//        Imgproc.rectangle(src, ImageProcessTools.plateRect, new Scalar(0, 255, 0), 3);
//        this.img4.setImage(Utils.mat2Image(src));

    }

}
