package com.cintcm.hamster.metadata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;

public class AuthorExtractor {

	private String title;

	private String author;
	private Scanner scanner = null;

	public AuthorExtractor(String title, File file) {

		this.title = title;
		try {
			FileReader fr = new FileReader(file);
			scanner = new Scanner(fr);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		work();
	}

	public AuthorExtractor(String title, Scanner scanner) {
		this.title = title;
		this.scanner = scanner;
		work();
	}

	private void work() {

		String tem = null;
		String preTem = null;

		if (scanner.hasNext()) {
			tem = scanner.nextLine();
			preTem = tem;
			// System.out.println(tem);
			if (tem.length() >= this.title.length()) {
				if (title_compare(this.title, tem)) {
					if (scanner.hasNext()) {
						this.author = scanner.nextLine();
						return;
					}
				}
			}
		}

		while (scanner.hasNext()) {
			String temp;
			tem = scanner.nextLine();
			temp = tem;
			if (tem.length() < this.title.length()) {
				tem = preTem + tem;
			}

			preTem = temp;

			if (title_compare(this.title, tem)) {
				if (scanner.hasNext()) {
					this.author = scanner.nextLine();
					break;
				}
			}
		}

	}

	private boolean title_compare(String title2, String tem) {
		String tmp;
		tmp = tem.replaceAll(" ", "");
		if (title2.equals(tmp)) {
			return true;
		}
		return false;
	}

	public String getAuthor() {
		return this.author;
	}

	public static void main(String[] args) throws Exception {

		FileInputStream fis = new FileInputStream(
				"data/dataset0/_伤寒论_经方合用治疗慢性浅表性胃炎临床观察.pdf");
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"data/dataset0/_伤寒论_经方合用治疗慢性浅表性胃炎临床观察.txt"));

		PDFParser p = new PDFParser(fis);
		p.parse();
		PDFTextStripper ts = new PDFTextStripper();
		String s = ts.getText(p.getPDDocument());

		writer.write(s);
		fis.close();
		writer.close();

		System.out.println(new AuthorExtractor("5伤寒论6经方合用治疗慢性浅表性胃炎临床观察", new File(
				"data/dataset0/_伤寒论_经方合用治疗慢性浅表性胃炎临床观察.txt")).getAuthor());

	}

}
