package com.cintcm.hamster.metadata;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;

import com.cintcm.hamster.relation.io.excel.MetadataRenderer;

public class testPDFMetadataExtractor {

	public void listFilesForFolder(final File folder, List<File> fileList) {
		File[] filesInFolder = folder.listFiles();
		if (filesInFolder != null) {
			for (final File fileEntry : filesInFolder) {
				if (fileEntry.isDirectory()) {
					listFilesForFolder(fileEntry, fileList);
				} else {
					fileList.add(fileEntry);
				}
			}
		}
	}

	@Test
	public void test() throws Exception {

		List<File> fileList = new ArrayList<File>();
		final File folder = new File("E://docs/中医药信息学/PDF文章/");
		listFilesForFolder(folder, fileList);
		List<PDFMetadataExtractor> extractorList = new ArrayList<PDFMetadataExtractor>();
		MetadataRenderer renderer = new MetadataRenderer();
		int c = 0; 
		for (final File file : fileList){
			/*
			PDDocument document = PDDocument
					.load(file);
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
			*/
			

			PDFMetadataExtractor extractor = new PDFMetadataExtractor(file);
			System.out.println(extractor.getFile().getName());
			System.out.println(extractor.getTitle());
			System.out.println(extractor.getAuthor());
			renderer.renderRelation(extractor);
			
			if (c++ > 100) break;
		}
		
		final File result = new File("E://docs/中医药信息学/result.xls");
		renderer.outputFile(result);
		
	}

}
