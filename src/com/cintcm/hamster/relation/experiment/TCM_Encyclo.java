package com.cintcm.hamster.relation.experiment;

import java.io.File;
import java.util.List;

import com.cintcm.hamster.relation.HasCoreRelationExtractor;
import com.cintcm.hamster.relation.Relation;
import com.cintcm.hamster.relation.Utils;
import com.cintcm.hamster.relation.io.mysql.MySQLUtils;

public class TCM_Encyclo {
    public static void main(String[] args){
    	File doc = new File("data/dataset1/中医百科全书.txt");
		//List<Relation> rels = HasCoreRelationExtractor.getRelations(doc, "中医百科全书");
		
		
		String[] sentences = Utils.breakFileIntoSentences(doc);
		//List<Relation> relations = new ArrayList<Relation>();

		for (String sentence : sentences) {
			List<Relation> rels = new HasCoreRelationExtractor(sentence,  "中医百科全书")
					.getRelations();
			MySQLUtils.insertRelations("TCM_Encyclo", rels, true);
		}
		
    }
}
