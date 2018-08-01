package LatestWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelData1 {
	
	XSSFWorkbook wb;
	XSSFSheet sheet1 ;
	File src;
	
	
	public ExcelData1(String excelpath, int sheetnumber)
	{
		try {
			src = new File(excelpath);
			FileInputStream fis = new FileInputStream(src);
			wb = new XSSFWorkbook(fis);
			sheet1 = wb.getSheetAt(sheetnumber);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	public String getData(int row, int column) {
		return sheet1.getRow(row).getCell(column).getStringCellValue();
	}
	
	public int getLastRowNum() {
		int rowCount = 0;
 
		rowCount = sheet1.getLastRowNum();
		if (rowCount == 0) {
			rowCount = sheet1.getPhysicalNumberOfRows();
		}
		return rowCount;
	}
	
	public void WriteExcel(String output, int RowNumber) throws Exception{
		int lastColumn= sheet1.getRow(RowNumber).getLastCellNum();
		sheet1.getRow(RowNumber).createCell(lastColumn).setCellValue(output);
		
	}

	
	public void CloseExcel() throws IOException {
		FileOutputStream fileout = new FileOutputStream(src);
		wb.write(fileout);
		wb.close();
	}
	

}
