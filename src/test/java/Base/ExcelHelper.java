package Base;

import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    // Declaration of an instance variable of type ExcelReader
    ExcelReader excelReader;

    // Creating of a ExcelHelper constructor that will use ExcelReader
    public ExcelHelper(ExcelReader excelReader) {
        this.excelReader = excelReader;
    }

    // Method that will load all the values from one row
    public List<String> loadValues(String sheetName, int cellNumber) {

        List<String> values = new ArrayList<>();
        int lastRow = excelReader.getLastRow(sheetName);
        for (int i = 0; i <= lastRow; i++) {
            String value = excelReader.getStringData(sheetName, i, cellNumber);
            if (value != null && !value.isEmpty()) {
                values.add(value);
            }
        }

        return values;
    }

    // Method that loads a single valid value that we have from Excel file
    public String loadSingleValue(String sheetName, int rowNumber, int cellNumber) {
        return excelReader.getStringData(sheetName,rowNumber, cellNumber);
    }

    // Method that loads a single integer value that we have from Excel file
    public int loadSingleIntValue(String sheetName, int rowNumber, int cellNumber) {
        return excelReader.getIntegerData(sheetName,rowNumber, cellNumber);
    }





}
