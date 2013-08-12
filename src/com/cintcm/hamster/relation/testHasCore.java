package com.cintcm.hamster.relation;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.cintcm.hamster.relation.io.mysql.MySQLUtils;

public class testHasCore {

	@Test
	public void testGetRelationsFileString() {
		
		
		
		
		
		String[] sentences = {"原料炮制杞豆汤参贝陈皮我们茵陈汁", "小儿化食丸这是马应龙八宝眼膏"};
		//List<Relation> relations = new ArrayList<Relation>();

		for (String sentence : sentences) {
			List<Relation> rels = new HasCoreRelationExtractor(sentence,  "中医百科全书")
					.getRelations();
			for (Relation rel: rels){
				System.out.println(rel);
			}
			//MySQLUtils.insertRelations("TCM_Encyclo", rels);
		}
		
	}

}
