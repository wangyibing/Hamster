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
	
	@Test
	public void testSegWords2() throws IOException {
	    String txt = "麦芽茵陈茶产于北京，属于理气方，可以治疗疏肝理气消食退黄";		
		System.out.println(complex.segWords(txt, " | "));
	}
	
	@Test
	public void testSegWords3() throws IOException {
	    String txt = "经过他再三哀求，王经理才答应他的要求。";		
		System.out.println(complex.segWords(txt, " | "));
	}
	
	@Test
	public void testSegWords4() throws IOException {
	    String txt = "京华时报1月23日报道 昨天，受一股来自中西伯利亚的强冷空气影响，本市出现大风降温天气，白天最高气温只有零下7摄氏度，同时伴有6到7级的偏北风。";
		
		System.out.println(complex.segWords(txt, " | "));
	}
	
	@Test
	public void testSegWords5() throws IOException {
	    String txt = "today,…………i'am chenlb,<《公主小妹》>?我@$#%&*()$!!,";
		System.out.println(complex.segWords(txt, " | "));
	}
	
	
	
}
