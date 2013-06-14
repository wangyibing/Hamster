package com.chenlb.mmseg4j.example;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class testComplex {
	Complex complex = new Complex();
/*	@Before
	public void setUp() throws Exception {
		complex = new Complex();
	}*/

	@Test
	public void testSegWords1() throws IOException {
	    String txt = "人参有“补五脏、安精神、定魂魄、止惊悸、除邪气、明目开心益智”的功效，“久服轻身延年”。";		
		System.out.println(complex.segWords(txt, " | "));
	}

}
