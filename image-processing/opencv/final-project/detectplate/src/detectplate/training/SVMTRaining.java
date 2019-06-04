/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detectplate.training;

import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.ml.Ml;
import org.opencv.ml.SVM;

/**
 *
 * @author SamFisher
 */
public final class SVMTRaining {

    private static final String PATH_POSITIVE = "resources/data/";
    private static final String PATH_NEGATIVE = "resources/data/01/";
    private static final String XML = "resources/data/svm.txt";
    private static final String FILE_TEST = "resources/data/00/1.JPG";

    public static final int number_of_class = 30;
    private static final int number_of_sample = 10;
    private static final int number_of_feature = 32;

    private static Mat trainingLabels;
    private static Mat trainingMat;

    private static SVM clasificador;
    private static int file_count = 0;

    public static void initSVMTraining() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        clasificador = SVM.create();
        clasificador.setType(SVM.C_SVC);
        clasificador.setKernel(SVM.RBF);
        clasificador.setGamma(0.50625);
        clasificador.setC(12.5);

        trainingLabels = new Mat(number_of_class * number_of_sample, 1, CvType.CV_32S);
        trainingMat = new Mat(number_of_class * number_of_sample, number_of_feature, CvType.CV_32FC1);

        file_count = 0;
    }

    public static void initSVMTRainingLabels() {
        int[] labels = new int[number_of_class * 10];
        int count = 0;
        for (int i = 0; i < number_of_class; i++) {
            for (int j = 0; j < 10; j++) {
                labels[count] = i;
                count++;
            }
        }
        System.out.println("labels:" + labels.length);
        trainingLabels.put(0, 0, labels);
        System.out.println("trainingLabels:" + trainingLabels.dump());
    }

    public static void initSVMTrainingData(int number) {
        String classLabelsName = "";
        if (number < 10) {
            classLabelsName = "0" + String.valueOf(number);
        } else {
            classLabelsName = String.valueOf(number);
        }

        for (File file : new File(PATH_POSITIVE + classLabelsName + "/").listFiles()) {
            Mat img = getMat(file.getAbsolutePath());
            img.reshape(1);
            int ii = 0;
            for (int i = 0; i < img.rows(); i++) {
                for (int j = 0; j < img.cols(); j++) {
                    trainingMat.put(file_count, ii, img.get(i, j));
                    ii++;
                }
            }
            file_count++;
        }

//        System.out.println("trainingMat:" + trainingMat.dump());
    }

    public static void trainSVM() {
        clasificador.train(trainingMat, Ml.ROW_SAMPLE, trainingLabels);
        clasificador.save(XML);
        System.out.println("Training data successfull and save to " + XML);

    }

    protected static Mat getMat(String path) {
        Mat img = new Mat();
        Mat con = Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE);
        con.convertTo(img, CvType.CV_32FC1, 1.0 / 255.0);
        return img;
    }

    public static String testData(String fileName) {
        clasificador.load(new File(XML).getAbsolutePath());
        Mat in = getMat(fileName);
        in.reshape(1);
        Mat out = new Mat(1, number_of_feature, CvType.CV_32F);
        int ii = 0;
        for (int i = 0; i < in.rows(); i++) {
            for (int j = 0; j < in.cols(); j++) {
                out.put(ii, 0, in.get(i, j));
                ii++;
            }
        }
        int predict = Math.round(clasificador.predict(out));
        System.out.println("prediction is:" + clasificador.predict(out));
        String result = convertPredictIntToString(predict);
        System.out.println("result is:" + result);
        return result;
    }
    
    public static String testData(Mat in) {
        in.reshape(1);
        Mat out = new Mat(1, number_of_feature, CvType.CV_32F);
        int ii = 0;
        for (int i = 0; i < in.rows(); i++) {
            for (int j = 0; j < in.cols(); j++) {
                out.put(ii, 0, in.get(i, j));
                ii++;
            }
        }
        int predict = Math.round(clasificador.predict(out));
        System.out.println("prediction is:" + clasificador.predict(out));
        String result = convertPredictIntToString(predict);
        System.out.println("result is:" + result);
        return result;
    }

    public static String convertPredictIntToString(int predict) {
        String result = "";
        if (predict >= 0 && predict <= 9) {
            result = Character.toString((char) (predict + 48));
        }

        if (predict >= 10 && predict <= 18) {
            result = Character.toString((char) (predict + 55));//ma accii A = 5, --> tu A-H
        }

        if (predict >= 18 && predict <= 22) {
            result = Character.toString((char) (predict + 55 + 2));//K-N, bo I,J
        }
        if (predict == 22) {
            result = "P";
        }
        if (predict == 23) {
            result = "S";
        }

        if (predict >= 24 && predict <= 27) {
            result = Character.toString((char) (predict + 60));//T-V,  
        }
        if (predict >= 27 && predict <= 30) {
            result = Character.toString((char) (predict + 61));//X-Z
        }
        return result;
    }

//    public static void main(String[] args) {
//        initSVMTraining();
//        initSVMTRainingLabels();
//        for (int i = 0; i < number_of_class; i++) {
//            initSVMTrainingData(i);
//        }
//        trainSVM();
//        testData("C:\\Users\\SamFisher\\Downloads\\output-image\\6.JPG");
//    }
}
