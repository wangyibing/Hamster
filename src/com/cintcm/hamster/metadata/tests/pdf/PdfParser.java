package com.cintcm.hamster.metadata.tests.pdf;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;

import com.cintcm.hamster.metadata.pdf.TitleExtractor;

public class PdfParser {

	/**
	 * @param args
	 */
	

	public static void main(String[] args) throws Exception {
		//FileInputStream fis = new FileInputStream("data/dataset0/学位论文.pdf");
		//BufferedWriter writer = new BufferedWriter(new FileWriter("data/dataset0/学位论文.txt"));
	
		FileInputStream fis = new FileInputStream("data/dataset0/中医知识工程研究进展分析.pdf");
		BufferedWriter writer = new BufferedWriter(new FileWriter("data/dataset0/中医知识工程研究进展分析.txt"));
	
		PDFParser p = new PDFParser(fis);
		p.parse();
		PDFTextStripper ts = new PDFTextStripper();
		String s = ts.getText(p.getPDDocument());
		
		writer.write(s);
		fis.close();
		writer.close();
		
		
	}
}