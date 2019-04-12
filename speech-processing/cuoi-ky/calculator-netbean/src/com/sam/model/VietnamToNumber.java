package com.sam.model;

public  class VietnamToNumber {
	public static int wordToNumber(String word) {
		int num = 0;
		switch (word) {
		case "MMOOJT":
			num = 1;
			break;
		case "HAI":
			num = 2;
			break;
		case "BA":
			num = 3;
			break;
		case "BOOSN":
			num = 4;
			break;
		case "NAWM":
			num = 5;
			break;
		case "SASU":
			num = 6;
			break;
		case "BBARY":
			num = 7;
			break;
		case "TASM":
			num = 8;
			break;
		case "CHISN":
			num = 9;
			break;
		case "KHOONG": 
			num = 0;
			break;
		default:
			num = -1;
			break;
		}
		return num;
	}
}
