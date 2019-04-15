/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhandien.bienso;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author SamFisher
 */
public class VideoCap {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture cam = new VideoCapture(0);
//        VideoCapture cap = new VideoCapture(filename);
        
        if (!cam.isOpened()) {
            System.err.println("cam eeroed");
        } else {
            Mat frame = new Mat();
            while(true) {
                if (cam.read(frame)) {
                    System.err.println("height:" + frame.col(0).dump());
                }
            }
        }
    }
}
