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
	// private int cursor = 0;
	private List<List<String>> words2;
	private List<String> words1;
	// private String subject;
	private List<Relation> relations = new ArrayList<Relation>();
	private String text;

	public SimpleRelationExtractor(String txt, String docId) {
		this.text = txt;
		this.words2 = Utils.breakSentence(txt);
        System.out.println(words2);
		this.words1 = new ArrayList<String>();
		for (List<String> l : words2) {
			this.words1.addAll(l);
		}

		this.docId = docId;

		int c = 0;
		while (c < words1.size()){
			int c2 = this.extractRelations(c);
			
			int o = 0;
			for (int i = 0; i <this.words2.size(); i++){
				o += this.words2.get(i).size();
				if (o >= c2) break;
			}			
			c = o;
		}
		

	}

	private int extractRelations(int c1) {
		//System.out.println("extract relations at: " + c1);
		
		String s = null;
		while (c1 < words1.size()) {
			s = words1.get(c1++);
			if (TCMNounDecider.isVerb(s)) break;			
		}
		
		int c2  = c1;
		
		while (c2 < words1.size()) {
			String obj = words1.get(c2);
			String pred = words1.get(c2 - 1);
			
			if ((!obj.equalsIgnoreCase(s))&&(!pred.equalsIgnoreCase(s))
					&& (TCMNounDecider.isVerb(obj))) {
				
				if (VerbDecider.isVerb(pred)) {
					//System.out.println(1/(c2-c1+1));
					Relation rel = new Relation(s, pred, obj).setDocId(
							this.docId).setText(this.text);
					rel.setValue(1.0/(double)(c2-c1+1));
					System.out.println(rel);
					relations.add(rel);
				} else {
					//rel.setValue(0);
				}
                
			}
			c2++;
		}
		return c1;

	}

	

	public List<Relation> getRelations() {
		return relations;
	}

	public static List<Relation> getRelations(File file, String docId) {
		String[] sentences = Utils.breakFileIntoSentences(file);
		List<Relation> relations = new ArrayList<Relation>();

		for (String sentence : sentences) {
			relations.addAll(new SimpleRelationExtractor(sentence, docId)
					.getRelations());
		}

		return relations;
	}

	public static List<Relation> getRelationsFromExcel(File exl) {
		List<Relation> relations = new ArrayList<Relation>();
		FileInputStream fIn;
		try {
			fIn = new FileInputStream(exl);
			HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
			HSSFSheet readSheet = readWorkBook.getSheet("docs");

			Iterator<Row> it = readSheet.rowIterator();
			while (it.hasNext()) {
				Row row = it.next();
				String text = row.getCell(0).getStringCellValue();
				String docId = row.getCell(1).getStringCellValue();
				String[] sentences = Utils.breakParagraphIntoSentences(text);
				for (String sentence : sentences) {
					relations.addAll(new SimpleRelationExtractor(sentence,
							docId).getRelations());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return relations;
	}

	public static void main(String[] args) {
		String txt = "人参有“补五脏、安精神、定魂魄、止惊悸、除邪气、明目开心益智”的功效,“久服轻身延年”";
		String doc = "神农本草经";
		File outputFile = new File("data/relations/人参功效.xls");

		SimpleRelationExtractor extractor = new SimpleRelationExtractor(txt,
				doc);
		List<Relation> rels = extractor.getRelations();
		new RelationRenderer(rels, outputFile).outputFile();

	}

}
