package com.example.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExelReaderUtil {

    /**
     * Reads data from an Excel sheet and returns it as a list of string arrays.
     * Each array represents a row.
     */
public static List<String[]> readExcelSheet(String filePath, String sheetName) {
        List<String[]> dataList = new ArrayList<>();
        try (InputStream is = ExelReaderUtil.class.getClassLoader().getResourceAsStream(filePath);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if(sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }
            for(Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for(Cell cell : row) {
                    cell.setCellType(CellType.STRING); // Force string format
                    rowData.add(cell.getStringCellValue().trim());
                }
                dataList.add(rowData.toArray(new String[0]));
            }

            } catch (Exception e) {
            throw new RuntimeException("Error reading Excel file: " + filePath, e);
        }
        return dataList;

        }

    }


