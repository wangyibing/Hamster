package com.cintcm.hamster.relation;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.cintcm.hamster.relation.io.excel.RelationRenderer;

public class SimpleRelationExtractorTest {

	@Test
	public void testGetRelationsFromFile() {
		File doc = new File("data/dataset1/百度百科-人参.txt");
		List<Relation> rels = SimpleRelationExtractor.getRelations(doc, "百度百科-人参");
		
		File file = new File("data/dataset1/百度百科-人参.xls");
		new RelationRenderer(rels, file).outputFile();

	}
	
	@Test
	public void testGetRelationsFromExcel() {
		
		File doc = new File("data/relations/example.xls");
		List<Relation> rels = SimpleRelationExtractor.getRelationsFromExcel(doc);
		
		File file = new File("data/relations/example_results.xls");
		new RelationRenderer(rels, file).outputFile();

	}

}
