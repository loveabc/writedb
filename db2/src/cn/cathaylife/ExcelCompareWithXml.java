package cn.cathaylife;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelCompareWithXml {

	public List<List<String>> readExcel(String path,int columnNums) throws IOException, InvalidFormatException {
		InputStream is = new FileInputStream(path);
		Workbook workbook = WorkbookFactory.create(is);
		Sheet hssfSheet = workbook.getSheetAt(0);
		List<List<String>> rowList = new ArrayList<>();
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			List<String> list = new ArrayList<>(columnNums);
			Row xssfRow = hssfSheet.getRow(rowNum);
			if("#".equals(xssfRow.getCell(1).getStringCellValue())) {
				break;
			}
			for(int i=1;i<=columnNums;i++) {
				list.add(xssfRow.getCell(i).getStringCellValue());
			}
			rowList.add(list);
		}
		return rowList;
	}

	public static void main(String[] args) throws Exception {
		String excelPath = "E:\\pan\\job_bak\\new\\薪水相关\\04.xls";
		ExcelCompareWithXml o = new ExcelCompareWithXml();
		List<List<String>> rowList=o.readExcel(excelPath,50);
		System.out.println(rowList.size());

	}
}
