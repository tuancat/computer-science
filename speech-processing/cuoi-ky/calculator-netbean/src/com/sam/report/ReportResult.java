package com.sam.report;

import com.sam.model.ReportModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class ReportResult {

    public static final String REPORT_FILE_PATH = "E:/report-speech/report.xls";
    public static final String TRUE = "True";
    public static final String FALSE = "False";
    public static List<ReportModel> listReportResult = new ArrayList<ReportModel>();

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public static void addReportResult(ReportModel reportResult) {
        listReportResult.add(reportResult);
    }

    public static Boolean updateLastResult(Boolean result) {

        ReportModel lasteReport = new ReportModel();
        if (listReportResult.size() > 0) {
            lasteReport = listReportResult.get(listReportResult.size() - 1);
            lasteReport.setResult(result);
            listReportResult.set(listReportResult.size() - 1, lasteReport);
            return true;
        } else {
            return false;
        }

    }

    public static void createExcelFile() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Report sheet");

        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        // Index
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Index");
        cell.setCellStyle(style);
        // Report String
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Report String");
        cell.setCellStyle(style);
        // Result
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Result");
        cell.setCellStyle(style);

        if (listReportResult.size() > 0) {
            for (ReportModel r : listReportResult) {
                rownum++;
                row = sheet.createRow(rownum);
                //set Id;
                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(r.getId());
                //set report result text
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(r.getResultText());
                //set result
                cell = row.createCell(2, CellType.BOOLEAN);
                cell.setCellValue(r.getResult());
            }
        }

        File file = new File(REPORT_FILE_PATH);
        file.getParentFile().mkdirs();

        FileOutputStream outFile;
        try {
            outFile = new FileOutputStream(file);
            workbook.write(outFile);
            System.out.println("Created file: " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
