package Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    File file;
    FileInputStream fis;
    XSSFWorkbook wb;
    XSSFSheet sheet;
    XSSFRow row;
    XSSFCell cell;

    public ExcelReader(String filePath) throws IOException {
        file = new File(filePath);
        fis = new FileInputStream(file);
        wb = new XSSFWorkbook(fis);
    }

    // Row and column indexes start at zero; row 0 in the DDT file contains the headers.
    public String getStringData(String sheetName, int rowNumber, int cellNumber) {
        sheet = wb.getSheet(sheetName);
        row = sheet.getRow(rowNumber);
        cell = row.getCell(cellNumber);
        return cell.getStringCellValue();
    }

    // Returns the index of the last row, not the total number of rows.
    public int getLastRow(String sheet) {
        this.sheet = wb.getSheet(sheet);
        return this.sheet.getLastRowNum();
    }
}
