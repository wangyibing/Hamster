package com.cintcm.hamster.relation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class testUtils {

	
	public void testBreakParagraphIntoSentences() {
		String p = "woemn.ddfdfd。dfdfd.d.d";
		String[] s = Utils.breakParagraphIntoSentences(p);
		
		List<String> a = Arrays.asList(s);
		System.out.println(a);
	}
	
	@Test
	public void testSplit() {
		String p = "|woemn|ddfdfd|。dfdfd.d.d|";
		String[] strings = p.split("\\|");
		
		Set<String> a = new HashSet(Arrays.asList(strings));
		a.remove("");
		System.out.println(a);
	}
	

}
