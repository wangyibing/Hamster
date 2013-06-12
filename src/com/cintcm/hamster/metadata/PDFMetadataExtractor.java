package com.cintcm.hamster.metadata;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;

import com.cintcm.hamster.metadata.pdf.TitleExtractor;


public class PDFMetadataExtractor {


	private String title;
	private String author;
	

	private String keyword;
	private String abstract_of_doc;
	private PDDocument document;
	private File input;	
	   
	public PDFMetadataExtractor(PDDocument document) throws Exception{
		this.document = document;
		// get the title
        this.InfoExtractTitle();
        
        // get the context
		this.InfoExtractContext();
        
        // get the authers
		this.InfoExtractAuther();
        
        // get the keywords
        
        // get the abstract
	}
    
	public void InfoExtractTitle() throws Exception {
		TitleExtractor InfoTitle = new TitleExtractor(this.document);
		this.title = InfoTitle.getTitle();				
	}
	
	public void InfoExtractContext() throws Exception {
	
	}
	
	public void InfoExtractAuther() throws Exception {
		
		PDFTextStripper ts = new PDFTextStripper();
		String s = ts.getText(this.document);		
		AuthorExtractor InfoAuther = new AuthorExtractor(this.title, new Scanner(s));
		this.author = InfoAuther.getAuthor();
	}
	
	

	public String getKeyword() {
		return keyword;
	}

	public String getAbstract() {
		return abstract_of_doc;
	}

	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public static void main(String[] args) throws Exception {

		PDDocument document = PDDocument.load("data/dataset0/_伤寒论_方治疗慢性胃炎.pdf");
		if (document.isEncrypted()) {
			try {
				document.decrypt("");
			} catch (InvalidPasswordException e) {
				System.err
						.println("Error: Document is encrypted with a password.");
				e.printStackTrace();
				return;
			}
		}
		
		
		PDFMetadataExtractor extractor = new PDFMetadataExtractor(document);
		System.out.println(extractor.getTitle());
		System.out.println(extractor.getAuthor());

	}
	    	
}
