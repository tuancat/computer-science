/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sam.wachingmovies.MB;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.decoder.adaptation.Stats;
import edu.cmu.sphinx.decoder.adaptation.Transform;
import edu.cmu.sphinx.result.WordResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author SamFisher
 */
public class MainClass {

    public static void main(String[] args) throws IOException, Exception {
        System.out.println("Loading models...");

        Configuration configuration = new Configuration();

        // Load model from the jar
        configuration
                .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");

        // You can also load model from folder
        // configuration.setAcousticModelPath("file:en-us");
        configuration
                .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration
                .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(
                configuration);
//        InputStream stream = MainClass.class.getResourceAsStream("/edu/cmu/sphinx/demo/aligner/10001-90210-01803.wav");
        InputStream stream = new FileInputStream(new File("C:\\Users\\SamFisher\\Downloads\\ENG.wav"));
//        stream.skip(44);

        // Simple recognition with generic model
        recognizer.startRecognition(stream);
        SpeechResult result;
        String fullSentences = "";
        while ((result = recognizer.getResult()) != null) {

            System.out.format("Hypothesis: %s\n", result.getHypothesis());

            System.out.println("List of recognized words and their times:");
            for (WordResult r : result.getWords()) {
                System.out.println(r);
                System.out.println("word:" + r.getWord().toString());
                fullSentences += r.getWord().toString() + " "; 
            }

            System.out.println("Best 3 hypothesis:");
            for (String s : result.getNbest(3)) {
                System.out.println(s);
            }
            System.out.println("full version:" + fullSentences);
        }
        recognizer.stopRecognition();

//        // Live adaptation to speaker with speaker profiles
//        stream = MainClass.class
//                .getResourceAsStream("/edu/cmu/sphinx/demo/aligner/10001-90210-01803.wav");
//        stream.skip(44);
//
//        // Stats class is used to collect speaker-specific data
//        Stats stats = recognizer.createStats(1);
//        recognizer.startRecognition(stream);
//        while ((result = recognizer.getResult()) != null) {
//            stats.collect(result);
//        }
//        recognizer.stopRecognition();
//
//        // Transform represents the speech profile
//        Transform transform = stats.createTransform();
//        recognizer.setTransform(transform);
//
//        // Decode again with updated transform
//        stream = MainClass.class
//                .getResourceAsStream("/edu/cmu/sphinx/demo/aligner/10001-90210-01803.wav");
//        stream.skip(44);
//        recognizer.startRecognition(stream);
//        while ((result = recognizer.getResult()) != null) {
//            System.out.format("Hypothesis: %s\n", result.getHypothesis());
//        }
//        recognizer.stopRecognition();
    }
}
