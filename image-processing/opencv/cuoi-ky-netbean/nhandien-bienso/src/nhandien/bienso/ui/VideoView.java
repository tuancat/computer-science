/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhandien.bienso.ui;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import nhandien.bienso.utils.DetectPlates;
import nhandien.bienso.utils.PossibleChar;
import nhandien.bienso.utils.PreProcess;
import nhandien.bienso.utils.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author SamFisher
 */
public class VideoView extends BorderPane {

    private ImageView img1;
    private static final String DEFAULT_URL = "Bike/0437.jpg";
    private final Image defaultImage = new Image(getClass().getClassLoader().getResourceAsStream(DEFAULT_URL));
    VideoCapture capture = new VideoCapture("C:\\Users\\SamFisher\\Downloads\\Video2.MP4");
    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;

    public VideoView() {
        this.setMinSize(400, 400);
        this.setPadding(new Insets(0, 10, 0, 10));
        initVideo();
    }

    private void initVideo() {
        System.err.println("init image");
        this.img1 = new ImageView(defaultImage);
        this.img1.setX(1024);
        this.img1.setY(1024);
        this.img1.setFitWidth(1024);
        this.img1.setPreserveRatio(true);
        this.setCenter(img1);

    }

    public void processVideo(String url) {
        
        capture = new VideoCapture(url);
        // grab a frame every 33 ms (30 frames/sec)
        Runnable frameGrabber = new Runnable() {

            @Override
            public void run() {
                // effectively grab and process a single frame
                Mat frame = grabFrame();
                // convert and show the frame
                Image imageToShow = Utils.mat2Image(frame);
                updateImageView(img1, imageToShow);
            }
        };
        this.timer = Executors.newSingleThreadScheduledExecutor();
        this.timer.scheduleAtFixedRate(frameGrabber, 0, 24, TimeUnit.MILLISECONDS);
    }

    /**
     * Get a frame from the opened video stream (if any)
     *
     * @return the {@link Image} to show
     */
    private Mat grabFrame() {
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened()) {
            try {
                // read the current frame
                this.capture.read(frame);

                // if the frame is not empty, process it
                if (!frame.empty()) {
                    // face detection
                    detectAndDisplayPlate(frame);
                }

            } catch (Exception e) {
                // log the (full) error
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    private void detectAndDisplayPlate(Mat src) {
        Mat imgThresh = PreProcess.preProcess(src); //quá trình tiền sử lý
        //quá trình xử lý loc ảnh lần 1 để tìm plate -> kết quả đuôc hiển thị tại hình ảnh 2 
        HashMap resultFilter1 = DetectPlates.findPossibleCharsInSceneFilter1(imgThresh);
        Mat imgContours = (Mat) resultFilter1.get("imgContours");
        List<PossibleChar> listOfPossibleChars = (List<PossibleChar>) resultFilter1.get("listOfPossibleChars");

        //quá trình xử lý loc ảnh lần 2 để tìm plate -> kết quả đuôc hiển thị tại hình ảnh 3
        HashMap resultFilter2 = DetectPlates.findPossibleCharsInSceneFilter2(src, imgThresh, listOfPossibleChars);
        Mat imgContoursFilter2 = (Mat) resultFilter2.get("imgContours");
        Rect rectLibPlate = (Rect) resultFilter2.get("findRectLicPlates");

        Imgproc.rectangle(src, rectLibPlate, new Scalar(0, 255, 0), 3);
    }

    /**
     * Update the {@link ImageView} in the JavaFX main thread
     *
     * @param view the {@link ImageView} to update
     * @param image the {@link Image} to show
     */
    private void updateImageView(ImageView view, Image image) {
        Utils.onFXThread(view.imageProperty(), image);
    }
}
