package com.mycompany.calculator.model;

import java.io.Serializable;

public class ReportModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String resultText;
	private Boolean result;

	public ReportModel() {
	}

	public ReportModel(int id, String resultText, Boolean result) {
		this.id = id;
		this.resultText = resultText;
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResultText() {
		return resultText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

}
