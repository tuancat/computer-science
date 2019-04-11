package com.mycompany.calculator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mycompany.calculator.model.SpeechCalculator;
import com.mycompany.calculator.report.ReportResult;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * The main interface of the application
 * 
 * @author GOXR3PLUS
 *
 */
public class MainInterfaceController extends BorderPane {

	@FXML
	private Button start;

	@FXML
	private Button stop;

	@FXML
	private Button restart;

	@FXML
	private Label statusLabel;

	@FXML
	private TextArea infoArea;
	@FXML // fx:id="fruitCombo"
	private ComboBox<String> chooseLangCombo; // Value injected by FXMLLoader

	@FXML
	private CheckBox checkBoxTrue;
	@FXML
	private CheckBox checkBoxFalse;
	@FXML
	private HBox topMenu;

	@FXML
	private Button reportBtn;

	private SpeechCalculator speechCalculator;

	/**
	 * Constructor
	 */
	public MainInterfaceController() {
		speechCalculator = new SpeechCalculator("English");
		// speechCalculator = new SpeechCalculator("Vietnamese");

		// FXMLLoader
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainInterfaceController.fxml"));

		loader.setController(this);
		loader.setRoot(this);

		try {
			loader.load();
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, " FXML can't be loaded!", ex);
		}

	}

	private void createRadioGroup() {
		final ToggleGroup group = new ToggleGroup();
		RadioButton radioButtonTrue = new RadioButton("True");
		radioButtonTrue.setStyle("-fx-text-fill: white;");
		radioButtonTrue.setToggleGroup(group);
		RadioButton radioButtonFalse = new RadioButton("False");
		radioButtonFalse.setStyle("-fx-text-fill: white;");
		radioButtonFalse.setToggleGroup(group);
		topMenu.getChildren().add(radioButtonTrue);
		topMenu.getChildren().add(radioButtonFalse);

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				if (group.getSelectedToggle() != null) {
					RadioButton button = (RadioButton) group.getSelectedToggle();
					System.out.println("Button: " + button.getText());
					infoArea.appendText("You choosed the result of the report text : " + button.getText() + "\n");
				}
			}
		});
	}

	/**
	 * As soon as fxml has been loaded then this method will be called
	 * 1)-constructor,2)-FXMLLOADER,3)-initialize();
	 */
	@FXML
	private void initialize() {

		

		chooseLangCombo.getItems().setAll("English", "Vietnamese");
		chooseLangCombo.setValue("English");
		;

		speechCalculator.setInfoArea(infoArea);

		// start
		start.setOnAction(a -> {
			System.out.println("choose:" + chooseLangCombo.getSelectionModel().getSelectedItem());
			speechCalculator.setChooseLang(chooseLangCombo.getSelectionModel().getSelectedItem());
			statusLabel.setText("Status : [Running]");
			infoArea.appendText("Starting Speech Recognizer\n");
			speechCalculator.startSpeechThread();
		});

		// stop
		stop.setOnAction(a -> {
			statusLabel.setText("Status : [Stopped]");
			infoArea.appendText("Stopping Speech Recognizer\n");
			speechCalculator.stopSpeechThread();
			chooseLangCombo.setValue("English");
			;
		});

		// restart
		restart.setDisable(true);

		reportBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("report button");
				ReportResult.createExcelFile();

			}
		});
		createRadioGroup();
	}

	public Button getStart() {
		return start;
	}

	public void setStart(Button start) {
		this.start = start;
	}

	public Button getStop() {
		return stop;
	}

	public void setStop(Button stop) {
		this.stop = stop;
	}

	public Button getRestart() {
		return restart;
	}

	public void setRestart(Button restart) {
		this.restart = restart;
	}

	public Label getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(Label statusLabel) {
		this.statusLabel = statusLabel;
	}

	public TextArea getInfoArea() {
		return infoArea;
	}

	public void setInfoArea(TextArea infoArea) {
		this.infoArea = infoArea;
	}

	public SpeechCalculator getSpeechCalculator() {
		return speechCalculator;
	}

	public void setSpeechCalculator(SpeechCalculator speechCalculator) {
		this.speechCalculator = speechCalculator;
	}

}
