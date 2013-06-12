package com.cintcm.hamster.metadata.pdf;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;
import org.apache.pdfbox.exceptions.InvalidPasswordException;


public class TitleExtractor extends PDFTextStripper {
	private String Title = "";
	

	private float MaxFontSize = 0;
	private int TitleSize = 0;
	private int TmpSize = 0;
	private double influnce;
	private PDDocument document;

	public TitleExtractor(PDDocument document) throws Exception {
		this.document = document;
		super.setSortByPosition(true);		
		parseTitle();
	}

	private void reset() {
		this.Title = "";
		this.MaxFontSize = 0;
		this.TitleSize = 0;
		this.TmpSize = 0;
	}

	private void parseTitle() throws Exception {
		PDPage firstPage = (PDPage) document.getDocumentCatalog().getAllPages()
				.get(0);

		if (firstPage.getContents() == null)
			return;
		this.influnce = Constants.TITLE_INFLUNCE_SCALE;
		this.processStream(firstPage, firstPage.findResources(), firstPage
				.getContents().getStream());

		if (this.TitleSize < 3) {
			reset();
			this.influnce = Constants.TITLE_INFLUNCE_SCALE_LEVEL1;
			this.processStream(firstPage, firstPage.findResources(), firstPage
					.getContents().getStream());

			if (this.TitleSize < 3) {
				this.Title = "";
			}
		}

	}

	public String getTitle() {
		return Title;
	}
	
	/**
	 * @param text
	 *            The text to be processed
	 */
	@Override
	protected void processTextPosition(TextPosition text) {
		float tmpHybSize = 0;

		if (text.getFontSize() > 1) {
			tmpHybSize = text.getFontSize();
		} else {
			tmpHybSize = text.getXScale();
		}

		/* 字符大小显著增大，认为可能是标题开始 */
		if ((this.MaxFontSize * this.influnce) < tmpHybSize) {
			this.MaxFontSize = tmpHybSize;
			this.Title = text.getCharacter();
			this.TmpSize = 1;
		} else {
			/* 在标题影响范围内 */
			if (this.TmpSize > 0) {
				/* 字符大小显著变小，认为是标题结束 */
				if ((tmpHybSize * this.influnce) < this.MaxFontSize) {
					this.TitleSize = this.TmpSize;
					this.TmpSize = 0;
				} else if ((text.getCharacter().equals("\n"))
						|| (text.getCharacter().equals("\r"))) {
					this.TitleSize = this.TmpSize;
					this.TmpSize = 0;
				} else {
					/* 累加字符到标题中 */
					this.Title += text.getCharacter();
					this.TmpSize++;

					/* 超过常规标题长度，可以排除不是标题 */
					if (this.TmpSize > Constants.TITLE_MAX_SIZE) {
						this.TmpSize = 0;
						this.Title = "";
					}
				}
			}
		}
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
		
		
		TitleExtractor InfoTitle = new TitleExtractor(document);
		System.out.println(InfoTitle.Title);

	}
}