package com.cintcm.hamster.relation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.cintcm.hamster.db.tcmls.TTerm;

public class testVerbDecider {

	@Test
	public void testFindVerbs() {
		List<String> verbs = VerbDecider.findVerbs(Arrays.asList("词语","爱","爱好"));
		System.out.println(verbs);
	}

	@Test
	public void testIsVerb1(){
		boolean t = VerbDecider.isVerb("补");
		System.out.println(t);
	}
	
	@Test
	public void testIsVerb2(){
		boolean t = VerbDecider.isVerb("安");
		System.out.println(t);
	}
	
	@Test
	public void testIsVerb3(){
		boolean t = VerbDecider.isVerb("定");
		System.out.println(t);
	}
	@Test
	public void testIsVerb4(){
		boolean t = VerbDecider.isVerb("止");
		System.out.println(t);
	}
	@Test
	public void testIsVerb5(){
		boolean t = VerbDecider.isVerb("除");
		System.out.println(t);
	}
	
}
