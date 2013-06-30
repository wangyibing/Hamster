package com.cintcm.hamster.relation.io.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class testExcel {
	public static String xlsFile = "test.xls";

	@Test
	public void readFile() {

		FileInputStream fIn;
		try {
			fIn = new FileInputStream("data/relations/example.xls");
			HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
			HSSFSheet readSheet = readWorkBook.getSheet("docs");
			HSSFRow readRow = readSheet.getRow(0);
			HSSFCell readCell = readRow.getCell(0);
			System.out.println("第一个单元是：" + readCell.getStringCellValue());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			String SHEET_NAME = "Relations";
			workbook.setSheetName(0, SHEET_NAME);
			HSSFRow row = sheet.createRow(0);

			HSSFCell sub = row.createCell(0);
			sub.setCellType(HSSFCell.CELL_TYPE_STRING);
			sub.setCellValue("主体");

			HSSFCell pred = row.createCell(1);
			pred.setCellType(HSSFCell.CELL_TYPE_STRING);
			pred.setCellValue("谓词");

			HSSFCell obj = row.createCell(2);
			obj.setCellType(HSSFCell.CELL_TYPE_STRING);
			obj.setCellValue("客体");

			FileOutputStream fOut = new FileOutputStream(xlsFile);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
			System.out.println("文件生成...");
			FileInputStream fIn = new FileInputStream(xlsFile);
			HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
			HSSFSheet readSheet = readWorkBook.getSheet(SHEET_NAME);
			HSSFRow readRow = readSheet.getRow(0);
			HSSFCell readCell = readRow.getCell(0);
			System.out.println("第一个单元是：" + readCell.getStringCellValue());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
