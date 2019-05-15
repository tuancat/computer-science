package com.sam.application.ui;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GridMainPane extends GridPane {
	
	private ImageView img1;
	private ImageView img2;

	public GridMainPane() {
		// TODO Auto-generated constructor stub
		this.setMinSize(400, 400);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(0, 10, 0, 10));
		initImage();
	}
	private void initImage() {
		Image i = new Image(getClass().getClassLoader().getResourceAsStream("images/1.jpg"));
		this.img1 = new ImageView(i);
		this.img2 = new ImageView(i);
		this.add(img1, 0, 0);
		this.add(img2, 1, 0);
		processImage();
	}
	private void processImage() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);;
		Mat src = Imgcodecs.imread(getClass().getClassLoader().getResourceAsStream("images/1.jpg").toString());
		System.out.println("dump:+" + src.dump());
	}

}
