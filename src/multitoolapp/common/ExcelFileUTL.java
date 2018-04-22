/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.common;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelFileUTL {

    public FileInputStream fis = null;
    public FileOutputStream fos = null;
    public XSSFWorkbook workbook = null;
    public XSSFSheet sheet = null;
    public XSSFRow row = null;
    public XSSFCell cell = null;
    public XSSFCell CompNamecell = null;
    public XSSFCell CompTypecell = null;
    String xlFilePath;

    public ExcelFileUTL(String xlFilePath) throws Exception {
        this.xlFilePath = xlFilePath;
        fis = new FileInputStream(xlFilePath);
        workbook = new XSSFWorkbook(fis);
        fis.close();
    }

    public String[][] readCellData(String sheetName, String CompNameCellHeader, String CompTypeCellHeader) {

        String[][] CompNameType;
        CompNameType = new String[sheet.getPhysicalNumberOfRows()][2];
        sheet = workbook.getSheet(sheetName);
        int compCellNo = getColumnNum(sheetName, CompNameCellHeader);
        int comptypeCellNo = getColumnNum(sheetName, CompTypeCellHeader);
        //DbObjectsJDBCDAO dbobj = new DbObjectsJDBCDAO();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            CompNameType[i][0] = row.getCell(compCellNo).getStringCellValue();
            CompNameType[i][1] = row.getCell(comptypeCellNo).getStringCellValue();

            //setCellData("Credentials", "OBJECT_STATUS", (i + 1), dbobj.FindDbObject(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue()));
            //setCellData("Credentials", "OBJECT_SQL", (i + 1), "select OBJECT_NAME,OBJECT_TYPE,STATUS from SYS.DBA_OBJECTS where object_name =" + row.getCell(0).getStringCellValue() + " AND Object_type = " + row.getCell(1).getStringCellValue());
        }
        return CompNameType;
    }

    public int getColumnNum(String sheetName, String colmName) {
        int col_Numb = -1;
        sheet = workbook.getSheet(sheetName);

        row = sheet.getRow(0);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (row.getCell(i).getStringCellValue().trim().equals(colmName)) {
                col_Numb = i;
            }
        }

        return col_Numb;

    }

    public boolean SetHeaderCell(String sheetName, String value) {

        int col_Numb = -1;
        try {
            sheet = workbook.getSheet(sheetName);

            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(value)) {
                    col_Numb = i;
                }
            }

            if (col_Numb == -1) {
                sheet.autoSizeColumn(row.getLastCellNum());
                row = sheet.getRow(0);
                if (row == null) {
                    row = sheet.createRow(0);
                }

                cell = row.getCell(row.getLastCellNum());
                if (cell == null) {
                    cell = row.createCell(row.getLastCellNum());
                }

                cell.setCellValue(value);
            }

            fos = new FileOutputStream(xlFilePath);
            workbook.write(fos);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean setCellData(String sheetName, String colName, int rowNum, String value) {

        try {
            int col_Num = -1;
            sheet = workbook.getSheet(sheetName);

            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
                    col_Num = i;
                }
            }

            System.out.println("row.getLastCellNum():" + row.getLastCellNum());
            sheet.autoSizeColumn(col_Num);
            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                row = sheet.createRow(rowNum - 1);
            }

            cell = row.getCell(col_Num);
            if (cell == null) {
                cell = row.createCell(col_Num);
            }

            cell.setCellValue(value);

            fos = new FileOutputStream(xlFilePath);
            workbook.write(fos);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
