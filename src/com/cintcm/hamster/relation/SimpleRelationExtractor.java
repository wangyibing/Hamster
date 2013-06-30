package com.cintcm.hamster.relation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.chenlb.mmseg4j.example.Complex;
import com.cintcm.hamster.relation.io.excel.RelationRenderer;

public class SimpleRelationExtractor {
	private String docId;
	private int cursor = 0;
	private List<String> words;
	private String subject;
	private List<Relation> relations = new ArrayList<Relation>();
	private String text;

	public SimpleRelationExtractor(String txt, String docId) {
		this.text = txt;
		this.words = Utils.setWords(txt);
		this.docId = docId;

		if (!extractSubject())
			return;
		extractRelations();

	}

	private boolean extractSubject() {
		while (cursor < words.size()) {
			String s = words.get(cursor++);
			if (TCMTermDecider.isTCMTerm(s)) {
				this.subject = s;
				return true;
			}
		}
		return false;

	}

	private void extractRelations() {
		while (cursor < words.size()) {
			String obj = words.get(cursor);
			if ((!obj.equalsIgnoreCase(this.subject))&&(TCMTermDecider.isTCMTerm(obj))) {
				String pred = words.get(cursor - 1);
				Relation rel = new Relation(this.subject, pred, obj).setDocId(
						this.docId).setText(this.text);
				if (VerbDecider.isVerb(pred)) {
					rel.setValue(5);
				} else {
					rel.setValue(3);
				}

				relations.add(rel);
			}
			cursor++;
		}
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public static List<Relation> getRelations(File file, String docId){
		String[] sentences = Utils.breakIntoSentences(file);
		List<Relation> relations = new ArrayList<Relation>();
		
    	for (String sentence : sentences){
    		relations.addAll( new SimpleRelationExtractor(sentence,
			"百度百科-人参").getRelations());
    	}		
		
		return relations;
	}
	
	public static List<Relation> getRelationsFromExcel(File exl){
		List<Relation> relations = new ArrayList<Relation>();		
		FileInputStream fIn;
		try {
			fIn = new FileInputStream(exl);
			HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
			HSSFSheet readSheet = readWorkBook.getSheet("docs");
			
			Iterator<Row> it = readSheet.rowIterator();
			while (it.hasNext()){
				Row row = it.next();
				String text = row.getCell(0).getStringCellValue();
				String docId = row.getCell(1).getStringCellValue();
				String[] sentences = Utils.breakIntoSentences(text);
				for (String sentence : sentences){
					relations.addAll( new SimpleRelationExtractor(sentence,docId).getRelations());
				}				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relations;
	}
	
	public static void main(String[] args) {
		String txt = "人参有“补五脏、安精神、定魂魄、止惊悸、除邪气、明目开心益智”的功效,“久服轻身延年”";
		File file = new File("data/relations/example.xls");

		SimpleRelationExtractor extractor = new SimpleRelationExtractor(txt,
				"神农本草经");
		List<Relation> rels = extractor.getRelations();
		new RelationRenderer(rels, file).outputFile();

	}

}
