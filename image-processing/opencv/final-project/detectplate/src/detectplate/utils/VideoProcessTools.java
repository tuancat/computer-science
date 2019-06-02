/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detectplate.utils;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author SamFisher
 */
public class VideoProcessTools {

    /**
     * Get a frame from the opened video stream (if any)
     *
     * @return the {@link Image} to show
     */
    private Mat grabFrame(VideoCapture cap) {
        Mat frame = new Mat();

        // check if the capture is open
//        if (cap.) {
//            try {
//                // read the current frame
//                this.capture.read(frame);
//
//                // if the frame is not empty, process it
//                if (!frame.empty()) {
//                    // face detection
//                    this.detectAndDisplay(frame);
//                }
//
//            } catch (Exception e) {
//                // log the (full) error
//                System.err.println("Exception during the image elaboration: " + e);
//            }
//        }

        return frame;
    }
}
