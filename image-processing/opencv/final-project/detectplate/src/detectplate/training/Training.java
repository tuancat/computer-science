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
import org.opencv.core.Scalar;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.ml.Ml;
import org.opencv.ml.SVM;

/**
 *
 * @author SamFisher
 */
public class Training {

    protected static final String PATH_POSITIVE = "resources/data/";
    protected static final String PATH_NEGATIVE = "resources/data/01/";
    protected static final String XML = "resources/data/svm.txt";
    protected static final String FILE_TEST = "C:\\Users\\SamFisher\\Downloads\\output-image\\4.JPG";
    protected static final int number_of_class = 3;
    protected static final int number_of_sample = 10;
    protected static final int number_of_feature = 32;

    private static Mat trainingImages;
    private static Mat trainingLabels;
    private static Mat trainingData;
    private static Mat trainingMat;
    private static Mat classes;
    private static SVM clasificador;
    private static int img_area = 50 * 70;
    private static int negativo_size = 520;
    private static int positivo_size = 349;
    private static int file_count = 0;
    private static int[] labels1 = new int[30];

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        trainingLabels = new Mat(number_of_class * number_of_sample, 1, CvType.CV_32S);
        trainingMat = new Mat(number_of_class * number_of_sample, number_of_feature, CvType.CV_32FC1);
        classes = new Mat();
        clasificador = SVM.create();
        clasificador.setType(SVM.C_SVC);
        clasificador.setKernel(SVM.RBF);
        clasificador.setGamma(5.0625000000000009e-01);
        clasificador.setC(6.2500000000000000e+01);
//        clasificador.setDegree(3);
//        clasificador.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER, 100, 1e-6));
    }

//    public static void main(String[] args) {
//        System.out.println("heeloo");
//        clasificador = SVM.create();
//        clasificador.setDegree(3);
//        clasificador.setType(SVM.C_SVC);
//        clasificador.setKernel(SVM.RBF);
//        clasificador.setGamma(5.0625000000000009e-01);
//        clasificador.setC(6.2500000000000000e+01);
////        int[] labels = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
////        trainPositive("00");
////        trainingLabels.put(0, 0, labels);
////        train();
//        trainImages("00", 0);
//        trainImages("01", 1);
//        trainImages("02", 2);
//
//        for (int i = 0; i < 30; i++) {
//            if (i < 10) {
//                labels1[i] = 0;
//            } else if (i> 10 && i < 20){
//                labels1[i] = 1;
//            } else if (i> 20 && i < 30) {
//                labels1[i] = 2;
//            }
//
//        }
//        
//        System.out.println("trainingLabels:" + labels1.length);
//        trainingLabels.put(0, 0, labels1);
//        System.out.println("trainingLabels:" + trainingLabels.dump());
//
//        clasificador.train(trainingMat, Ml.ROW_SAMPLE, trainingLabels);
//        clasificador.save(XML);
//
////        int[] labels1 = {1};
////        trainPositive("01");
////        trainingLabels.put(1, 0, labels1);
////        train();
////        trainPositive("01");
////        trainPositive("03");
////        trainPositive("04");
////        trainPositive("05");
////        trainPositive("06");
////        trainNegative();
////        trainingData();
//        test();
//    }

    protected static void train() {
//        for (int i =0 ; i< 10 ; i++) {
//            if (i == 0) {
//                trainPositive()
//            }
//        }
//        clasificador.train(trainingMat, Ml.ROW_SAMPLE, trainingLabels);
////        clasificador.train(trainingMat, Ml.ROW_SAMPLE, trainingLabels);
//        clasificador.save(XML);
// Set up training data
        int[] labels = {1, -1, -1, -1};
        float[] trainingData = {501, 10, 255, 10, 501, 255, 10, 501};
        Mat trainingDataMat = new Mat(4, 2, CvType.CV_32FC1);
        trainingDataMat.put(0, 0, trainingData);
        Mat labelsMat = new Mat(4, 1, CvType.CV_32SC1);
        labelsMat.put(0, 0, labels);
        System.out.println("trainingDataMat:" + trainingDataMat.dump());
        System.out.println("labelsMat:" + labelsMat.dump());
        // Train the SVM
        SVM svm = SVM.create();
        svm.setType(SVM.C_SVC);
        svm.setKernel(SVM.LINEAR);
        svm.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER, 100, 1e-6));
        svm.train(trainingDataMat, Ml.ROW_SAMPLE, labelsMat);
        svm.save(XML);
    }

    protected static void trainImages(String classLabelsName, int labelValue) {

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

        System.out.println("trainingMat:" + trainingMat.dump());

        // Train the SVM
    }

    private static void test() {
        clasificador.load(new File("resources/data/svm.txt").getAbsolutePath());
        Mat in = getMat(FILE_TEST);
        in.reshape(1);
        Mat out = new Mat(1, number_of_feature, CvType.CV_32F);
        int ii = 0;
        for (int i = 0; i < in.rows(); i++) {
            for (int j = 0; j < in.cols(); j++) {
                out.put(ii, 0, in.get(i, j));
                ii++;
            }
        }
        System.out.println("out:" + out.dump());
        System.out.println("prediction is:" + clasificador.predict(out));
    }

    protected static void trainPositive(String classLabels) {
//        file_count = 0;
        int[] labels = {0};
        for (File file : new File(PATH_POSITIVE + classLabels + "/").listFiles()) {
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
//            trainingLabels.put(file_count, 0, labels);
        }
        int[] labels1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        trainingLabels.put(0, 0, labels1);
        System.out.println("trainingMat of " + classLabels + ": " + trainingMat.dump());
        System.out.println("trainingLabels of " + classLabels + ": " + trainingLabels.dump());
    }

    protected static void trainNegative() {
        for (File file : new File(PATH_NEGATIVE).listFiles()) {
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
    }

    protected static Mat getMat(String path) {
        Mat img = new Mat();
        Mat con = Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE);
        con.convertTo(img, CvType.CV_32FC1, 1.0 / 255.0);
        return img;
    }

    protected static void trainingData() {
        Mat data = new Mat(number_of_sample * number_of_class, number_of_feature, CvType.CV_32FC1);
        Mat label = new Mat(number_of_sample * number_of_class, 1, CvType.CV_32FC1);
        for (File file : new File(PATH_POSITIVE).listFiles()) {
            Mat img = getMat(file.getAbsolutePath());
            img.reshape(1);
            int ii = 0;
            for (int i = 0; i < img.rows(); i++) {
                for (int j = 0; j < img.cols(); j++) {
                    data.put(file_count, ii, img.get(i, j));
                    ii++;
                }
            }

            file_count++;
        }
//        label.put(file_count, 0, 0);
        label.rowRange(0, file_count).setTo(new Scalar(0));
        clasificador.train(data, Ml.ROW_SAMPLE, label);
        clasificador.save(XML);
    }

}
