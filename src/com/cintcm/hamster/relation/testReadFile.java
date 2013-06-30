package com.cintcm.hamster.relation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class testReadFile {
    @Test
    public void testRead() throws FileNotFoundException{
    	File doc = new File("data/dataset1/3.txt");
    	String[] sentences = Utils.breakIntoSentences(doc);
    	for (String s : sentences){
    		System.out.println(s);
    	}
    }
}
