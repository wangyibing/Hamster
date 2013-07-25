package com.cintcm.hamster.relation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

public class UtilsTest {

	//@Test
	public void testRead() throws FileNotFoundException {
		File doc = new File("data/dataset1/3.txt");
		String[] sentences = Utils.breakFileIntoSentences(doc);
		for (String s : sentences) {
			System.out.println(s);
		}
	}
	
	@Test
	public void testBreakSentence() throws FileNotFoundException {
		List<List<String>> list = Utils.breakSentence("人参有“补五脏,安精神，定魂魄；止惊悸;除邪气、明目开心益智”的功效,“久服轻身延年”");
	    System.out.println(list);
	}
	
}
