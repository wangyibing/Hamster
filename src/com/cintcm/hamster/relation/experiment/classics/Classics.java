package com.cintcm.hamster.relation.experiment.classics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.cintcm.hamster.relation.CoreRelationExtractor;
import com.cintcm.hamster.relation.Utils;

public class Classics {
	private CoreRelationExtractor coreRelationExtractor;

	private int getNumArticles(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("select max(id) as c from resource");
		rs.next();
		int num_articles = rs.getInt(1);
		rs.close();
		return num_articles;
	}
    
	@Test
	public void test5() {
		try {
			Connection conn = MySQLUtils.getConnection();

			Statement stmt = conn.createStatement();

			int num_articles = getNumArticles(stmt);

			for (int i = 1; i <= num_articles; i++) {
				ResultSet resultSet = stmt
						.executeQuery("select id, description from resource where id="
								+ i);

				if (resultSet.next()) {
					String doc_id = resultSet.getString(1);
					String text = resultSet.getString(2);
					System.out.println("Extracting from doc: " + doc_id);
					String[] sentences = Utils
							.breakParagraphIntoSentences(text);

					for (String sentence : sentences) {

						MySQLUtils.insertRelations("relation",
								new CoreRelationExtractor(sentence, doc_id)
										.getRelations(), false);

					}

					// if (i++ > 2) break;
				}
				// new RelationRenderer(relations, new
				// File("test.xls")).outputFile();
				resultSet.close();
			}

			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
