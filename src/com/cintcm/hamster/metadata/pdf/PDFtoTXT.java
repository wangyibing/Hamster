package com.cintcm.hamster.metadata.pdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.cintcm.hamster.metadata.AuthorExtractor;

public class PDFtoTXT {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws CryptographyException 
	 */
	public static void main(String[] args) throws IOException, CryptographyException {
		PDDocument document = PDDocument.load("data/dataset1/中医百科全书.pdf");
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
		
		PDFTextStripper ts = new PDFTextStripper();
		String s = ts.getText(document);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(
		"data/dataset1/中医百科全书.txt"));


		writer.write(s);
		
		writer.close();

		

	}

}
