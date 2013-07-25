package com.cintcm.hamster.relation.io.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.cintcm.hamster.relation.Relation;;

public class RelationRenderer {
	
	private static final String SHEET_NAME = "Relations";
		
	private List<Relation> rList;
	private File file;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;

	public RelationRenderer(List<Relation> rList, File file){
		
		this.rList = new ArrayList<Relation>(rList);
		Collections.sort(this.rList);
		Collections.reverse(this.rList);
		this.file = file;
		init();
		renderHead();
		renderRelations();
		//outputFile();
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
		sub.setCellValue("主体");
		
		HSSFCell pred = row.createCell(1);
		pred.setCellType(HSSFCell.CELL_TYPE_STRING);
		pred.setCellValue("谓词");
		
		HSSFCell obj = row.createCell(2);
		obj.setCellType(HSSFCell.CELL_TYPE_STRING);
		obj.setCellValue("客体");
		
		HSSFCell value = row.createCell(3);
		value.setCellType(HSSFCell.CELL_TYPE_STRING);
		value.setCellValue("综合评分");
		
		HSSFCell txt = row.createCell(4);
		txt.setCellType(HSSFCell.CELL_TYPE_STRING);
		txt.setCellValue("原文内容");
		
		HSSFCell docId = row.createCell(5);
		docId.setCellType(HSSFCell.CELL_TYPE_STRING);
		docId.setCellValue("原文标识");
	}
	
	private void renderRelations() {
		int i = 1;
		for (Relation rel : this.rList){
			HSSFRow row = sheet.createRow(i++);		
			HSSFCell sub = row.createCell(0);
			sub.setCellType(HSSFCell.CELL_TYPE_STRING);
			sub.setCellValue(rel.getSubject());
			
			HSSFCell pred = row.createCell(1);
			pred.setCellType(HSSFCell.CELL_TYPE_STRING);
			pred.setCellValue(rel.getPredicate());
			
			HSSFCell obj = row.createCell(2);
			obj.setCellType(HSSFCell.CELL_TYPE_STRING);
			obj.setCellValue(rel.getObject());
		
			HSSFCell value = row.createCell(3);
			value.setCellType(HSSFCell.CELL_TYPE_STRING);
			value.setCellValue(rel.getValue());
		
			HSSFCell txt = row.createCell(4);
			txt.setCellType(HSSFCell.CELL_TYPE_STRING);
			txt.setCellValue(rel.getText());
			
			HSSFCell docId = row.createCell(5);
			docId.setCellType(HSSFCell.CELL_TYPE_STRING);
			docId.setCellValue(rel.getDocId());
		}
	}
	
	public void outputFile() {
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(this.file);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
	public static void main(String[] args) throws IOException{
		List<Relation> rels = new ArrayList<Relation>();
		rels.add(new Relation ("主体1", "谓词1", "客体1").setValue(3));
		rels.add(new Relation ("主体2", "谓词2", "客体2").setValue(2));
		rels.add(new Relation ("主体3", "谓词3", "客体3").setValue(1));
		
		File file = new File("test.xls");
		new RelationRenderer(rels, file).outputFile();
		
		FileInputStream fIn = new FileInputStream(file);
		HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
		HSSFSheet readSheet = readWorkBook.getSheet(SHEET_NAME);
		
		Iterator<Row> it = readSheet.rowIterator();
		while (it.hasNext()){
			Row row = it.next();
			Iterator<Cell> cit = row.cellIterator();
			while(cit.hasNext()){
				Cell cell = cit.next();
				switch (cell.getCellType()){
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
