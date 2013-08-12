package com.cintcm.hamster.relation.io.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.cintcm.hamster.metadata.PDFMetadataExtractor;
import com.cintcm.hamster.relation.Relation;

;

public class MetadataRenderer {

	private static final String SHEET_NAME = "Metadata";

	// private List<PDFMetadataExtractor> rList;
	
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private int i = 1;

	public MetadataRenderer(List<PDFMetadataExtractor> rList) {

		// this.rList = new ArrayList<PDFMetadataExtractor>(rList);
		// Collections.sort(this.rList);
		// Collections.reverse(this.rList);
		
		init();
		renderHead();

		for (PDFMetadataExtractor r : rList) {
			renderRelation(r);
		}

		// outputFile();
	}
	
	public MetadataRenderer() {

		// this.rList = new ArrayList<PDFMetadataExtractor>(rList);
		// Collections.sort(this.rList);
		// Collections.reverse(this.rList);
		
		init();
		renderHead();

		

		// outputFile();
	}

	private void init() {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet();
		workbook.setSheetName(0, SHEET_NAME);
	}

	private void renderHead() {
		HSSFRow row = sheet.createRow(0);

		HSSFCell sub = row.createCell(0);
		sub.setCellType(HSSFCell.CELL_TYPE_STRING);
		sub.setCellValue("文件名");

		HSSFCell pred = row.createCell(1);
		pred.setCellType(HSSFCell.CELL_TYPE_STRING);
		pred.setCellValue("题目");

		HSSFCell obj = row.createCell(2);
		obj.setCellType(HSSFCell.CELL_TYPE_STRING);
		obj.setCellValue("作者");

	}

	public void renderRelation(PDFMetadataExtractor rel) {

		HSSFRow row = sheet.createRow(i++);
		HSSFCell sub = row.createCell(0);
		sub.setCellType(HSSFCell.CELL_TYPE_STRING);
		sub.setCellValue(rel.getFile().getName());

		HSSFCell pred = row.createCell(1);
		pred.setCellType(HSSFCell.CELL_TYPE_STRING);
		pred.setCellValue(rel.getTitle());

		HSSFCell obj = row.createCell(2);
		obj.setCellType(HSSFCell.CELL_TYPE_STRING);
		obj.setCellValue(rel.getAuthor());

	}

	public void outputFile(File file) {
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(file);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		File file = new File("data/dataset0/中医知识工程研究进展分析.pdf");

		PDFMetadataExtractor extractor = new PDFMetadataExtractor(file);
		List<PDFMetadataExtractor> list = Arrays.asList(extractor);

		File result_file = new File("data/dataset0/中医知识工程研究进展分析.xls");

		new MetadataRenderer(list).outputFile(result_file);

		FileInputStream fIn = new FileInputStream(result_file);
		HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
		HSSFSheet readSheet = readWorkBook.getSheet(SHEET_NAME);

		Iterator<Row> it = readSheet.rowIterator();
		while (it.hasNext()) {
			Row row = it.next();
			Iterator<Cell> cit = row.cellIterator();
			while (cit.hasNext()) {
				Cell cell = cit.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue() + " ");
					break;

				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue() + " ");
					break;

				}

			}
			System.out.println();
		}

	}
}
