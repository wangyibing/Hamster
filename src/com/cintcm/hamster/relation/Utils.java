package com.cintcm.hamster.relation;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.chenlb.mmseg4j.example.Complex;
import com.cintcm.hamster.db.HibernateSessionFactory;
import com.cintcm.hamster.db.tcmls.TTerm;



public class Utils {
	private static Complex complex = new Complex();
	public static List<String> setWords(String txt){		
		String rst = "";
		try {
			rst = complex.segWords(txt, "|");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Arrays.asList(rst.split("\\|"));
	}
	
	public static String[] breakIntoSentences(String  paragraph){
		String sentences[] = paragraph.split("[。]");
		return sentences;
	}
	
	public static String[] breakIntoSentences(File file){
		StringBuilder sb = new StringBuilder();
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()){
	    		sb.append(scanner.nextLine());
	    	} 
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
    	    	
		String sentences[] = sb.toString().split("[。]");
		return sentences;
	}
	
}
