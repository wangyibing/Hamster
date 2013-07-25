package com.cintcm.hamster.relation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.cintcm.hamster.db.tcmls.TTerm;

public class TCMTermDeciderTester {

	
	
	public void testIdentifyWordInDB(){
		String words[] = {"麦芽山楂饮", "萝卜饮", "山楂消食茶","不在里面"};
		List<TTerm> list = TCMTermDecider.identifyWordInDB(words);
		System.out.println(list.size());
		for(TTerm term:list)
			System.out.println(term.getTermLabel());
	}
	@Test
	public void testIdentifyWordInDB1(){
		String words[] = {"是"};
		List<TTerm> list = TCMTermDecider.identifyWordInDB(words);
		System.out.println(list.size());
		for(TTerm term:list)
			System.out.println(term.getTermLabel() + ":" + term.getConceptId());
	}
	
	
	
	public void testIsTCMTerm(){
		String words[] = {"尚", "萝卜饮", "山楂消食茶","不在里面"};
		for(String word : words)
			System.out.println(TCMTermDecider.isTCMTerm(word));
	}
	
	
	
}
