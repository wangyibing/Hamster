package com.chenlb.mmseg4j.example;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.chenlb.mmseg4j.Chunk;
import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Chunk.Word;

public class Complex {

	protected Dictionary dic;
	
	public Complex() {
		dic = new Dictionary();
	}

	public Complex(Dictionary dic) {
		this.dic = dic;
	}

	protected Seg getSeg() {
		return new ComplexSeg(dic);
	}
	
	public String segWords(Reader input, String wordSpilt) throws IOException {
		StringBuilder sb = new StringBuilder();
		Seg seg = getSeg();
		MMSeg mmSeg = new MMSeg(input, seg);
		Chunk chunk = null;
		boolean first = true;
		while((chunk=mmSeg.next())!=null) {
			for(int i=0; i<chunk.getCount(); i++) {
				Word word = chunk.getWords()[i];
				if(!first) {
					sb.append(wordSpilt);
				}
				String w = word.getString();
				sb.append(w);
				first = false;
			}
		}
		return sb.toString();
	}
	
	public String segWords(String txt, String wordSpilt) throws IOException {
		return segWords(new StringReader(txt), wordSpilt);
	}
	
	public static void main(String[] args) throws IOException {
		
		String txt = "人参有“补五脏、安精神、定魂魄、止惊悸、除邪气、明目开心益智”的功效，“久服轻身延年”。";
		
		if(args.length > 0) {
			txt = args[0];
		}		
	
		Complex complex = new Complex();
		System.out.println(complex.segWords(txt, " | "));
	}

}
