package com.cintcm.hamster.relation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Decide if a word is a verb by using a dictionary of verbs.
 * 
 * @author asus
 * 
 */
public class TCMNounDecider {

	protected static Set<String> dic = new HashSet<String>();

	static {
		Scanner sc;
		try {
			//sc = new Scanner(new File("data/words-tcm.dic"));
			sc = new Scanner(new File("data/words-multi.dic"));
			while (sc.hasNextLine()) {
				String s = sc.nextLine();
				dic.add(s);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<String> findVerbs(List<String> words) {

		List<String> verbs = new ArrayList<String>();
		for (String word : words) {
			if (dic.contains(word)) {
				verbs.add(word);
			}
		}
		return verbs;

	}

	public static boolean isNoun(String word) {
		return dic.contains(word);
	}

}
