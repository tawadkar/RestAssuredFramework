package excelUtils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class TestData {

    public ArrayList<String> getData(String sheetName , String testCaseName) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        FileInputStream fis = new FileInputStream("T:\\restAssuredWorkSpace\\src\\test\\java\\resources\\TestData.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int sheets = workbook.getNumberOfSheets();
        for(int i=0;i<sheets;i++){
            if(workbook.getSheetName(i).equalsIgnoreCase(sheetName)){
                XSSFSheet sheet = workbook.getSheetAt(i);

                Iterator<Row> rows = sheet.iterator(); //sheet is collection of rows
                Row firstRow = rows.next();
                Iterator<Cell> cell = firstRow.cellIterator(); //row is collection of cells
                int k=0;
                int column=0;
                while(cell.hasNext()) {
                    Cell Cellvalue = cell.next();
                    if (Cellvalue.getStringCellValue().equalsIgnoreCase(testCaseName)) {
                        column=k;
                    }
                    k++;
                }
                System.out.println(column);

                /* Once the column is identified,then scan entire test case name column to identity the
                 desired test case row
                 */
                while(rows.hasNext()){

                    Row Row = rows.next();
                    if(Row.getCell(column).getStringCellValue().equalsIgnoreCase("AddBook")){
                        //After GetBook row is identified , pull all the data from that row and pass it to Test

                        Iterator<Cell> testCell =Row.cellIterator();
                        while(testCell.hasNext()){
                            Cell c = testCell.next();
                            if(c.getCellType()== CellType.STRING) {
                                data.add(c.getStringCellValue());
                            }else{
                                data.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                            }
                        }

                    }
                }

            }



        }
        return data;
    }

 }
