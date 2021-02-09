package by.bytechs.excel;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

/**
 * This class implementation ExcelService
 *
 * @author Romanovich Andrei
 * @version 1.0
 * @see ExcelService
 */
@Service
public class ExcelServiceService implements ExcelService {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableWorkbook workbook;

    @Override
    public void write(File file, List<String[]> reportList) throws IOException, WriteException {
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("ru", "RU"));

        workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Отчет о доступности сети АТМ(ПСТ)", 0);
        WritableSheet exelSheet = workbook.getSheet(0);

        WritableFont font = new WritableFont(WritableFont.TIMES, 9);
        times = new WritableCellFormat(font);
        times.setWrap(false);

        WritableFont fontHead = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
        timesBoldUnderline = new WritableCellFormat(fontHead);
        timesBoldUnderline.setWrap(false);

        createContent(exelSheet, reportList);

        workbook.write();
        workbook.close();
    }

    /**
     * This method create body report.
     *
     * @param sheet -
     * @param reportList -
     * @throws WriteException catch
     */
    public void createContent(WritableSheet sheet, List<String[]> reportList) throws WriteException {
        for (int i = 0; i < reportList.size(); i++) {
            String[] reports = reportList.get(i);
            int temp = 0;
            for (String s : reports) {
                if (s.equalsIgnoreCase("Итого:") || s.equals("Итого по всем: ")) {
                    temp = 1;
                    break;
                }
            }
            for (int j = 0; j < reports.length; j++) {
                if (i == 0) {
                    addCaption(sheet, j, i, reports[j]);
                } else if (i == 1) {
                    addCaption(sheet, j, i, reports[j]);
                } else if (temp == 1) {
                    addCaption(sheet, j, i, reports[j]);
                } else {
                    addNumber(sheet, j, i, reports[j]);
                }

            }
        }
    }

    /**
     * This method fills body report.
     *
     * @param sheet -
     * @param column -
     * @param row -
     * @param s -
     * @throws WriteException catch
     */
    public void addCaption(WritableSheet sheet, int column, int row, String s) throws WriteException {
        Label label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    /**
     * This method fills hat report.
     *
     * @param sheet -
     * @param column -
     * @param row -
     * @param s -
     * @throws WriteException catch
     */
    public void addNumber(WritableSheet sheet, int column, int row, String s) throws WriteException {
        Label label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

    public void closeWorkBook() {
        try {
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeXLSX(File file, List<String[]> reportList, boolean statusStyle, String name) throws IOException, WriteException {

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        workbook.setCompressTempFiles(true);

        SXSSFSheet sheet = workbook.createSheet(name);

        org.apache.poi.ss.usermodel.Font fontHead = workbook.createFont();
        fontHead.setFontName("Times New Roman");
        fontHead.setFontHeightInPoints((short) 11);
        fontHead.setBold(true);

        Font fontGrouped = workbook.createFont();
        fontGrouped.setFontName("Times New Roman");
        fontGrouped.setFontHeightInPoints((short) 9);
        fontGrouped.setBold(true);

        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 9);

        CellStyle styleHead = workbook.createCellStyle();
        styleHead.setFont(fontHead);
        CellStyle styleGrouped = workbook.createCellStyle();
        styleGrouped.setFont(fontGrouped);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);

        for (int i = 0; i < reportList.size(); i++) {
            Row row = sheet.createRow(i);
            String[] str = reportList.get(i);
            CellStyle tempStyle = null;
            for (String s : str) {
                if (s.equalsIgnoreCase("Итого:") || s.equals("Итого по всем: ")) {
                    tempStyle = styleHead;
                    break;
                }
            }
            if (tempStyle == null) {
                if (i == 0) {
                    tempStyle = styleHead;

                } else if (i == 1 && statusStyle) {
                    tempStyle = styleGrouped;
                } else {
                    tempStyle = style;
                }
            }

            int cellNum = 0;
            for (String s : str) {
                tempStyle.setDataFormat(workbook.createDataFormat().getFormat("General"));
                java.util.regex.Pattern patternInt = java.util.regex.Pattern.compile("\\d+");
                Matcher matcherInt = patternInt.matcher(s);
                java.util.regex.Pattern patternDouble = java.util.regex.Pattern.compile("\\d+\\.\\d+");
                Matcher matcherDouble = patternDouble.matcher(s);
                java.util.regex.Pattern atmPattern = java.util.regex.Pattern.compile("000\\d+");
                Matcher atmMatcher = atmPattern.matcher(s);
                java.util.regex.Pattern pstPattern = java.util.regex.Pattern.compile("09749\\d+");
                Matcher pstMatcher = pstPattern.matcher(s);
                java.util.regex.Pattern logicPattern = java.util.regex.Pattern.compile("0{1,2}\\d{1,2}");
                Matcher logicMatcher = logicPattern.matcher(s);
                if (atmMatcher.matches() || pstMatcher.matches() || logicMatcher.matches()) {
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(s);
                    cell.setCellStyle(tempStyle);
                    sheet.trackColumnForAutoSizing(cellNum);
                } else if (matcherInt.matches()) {
                    Cell cell = row.createCell(cellNum);
                    if (s.length() <= 6) {
                        cell.setCellValue(Integer.parseInt(s));
                    } else {
                        cell.setCellValue(s);
                    }
                    cell.setCellStyle(tempStyle);
                    sheet.trackColumnForAutoSizing(cellNum);
                } else if (matcherDouble.matches()) {
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(Double.parseDouble(s));
                    cell.setCellStyle(tempStyle);
                    sheet.trackColumnForAutoSizing(cellNum);
                } else {
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(s);
                    cell.setCellStyle(tempStyle);
                    sheet.trackColumnForAutoSizing(cellNum);
                }
                cellNum++;

            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);

        fileOutputStream.close();

        workbook.dispose();

    }
}
