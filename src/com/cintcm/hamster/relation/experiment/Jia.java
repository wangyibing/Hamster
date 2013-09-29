package com.cintcm.hamster.relation.experiment;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.cintcm.hamster.relation.CoreRelationExtractor;
import com.cintcm.hamster.relation.HasCoreRelationExtractor;
import com.cintcm.hamster.relation.LooseRelationExtractor;
import com.cintcm.hamster.relation.Relation;
import com.cintcm.hamster.relation.SimpleRelationExtractor;
import com.cintcm.hamster.relation.Utils;
import com.cintcm.hamster.relation.io.excel.RelationRenderer;
import com.cintcm.hamster.relation.io.mysql.MySQLUtils;

public class Jia {

	public void test1() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Properties props = new Properties();
			Properties prop = new Properties();
			// prop.put("charSet", "UTF-8");
			// prop.put("charSet", "UTF-8");

			prop.put("user", "");
			prop.put("password", "");
			prop.put("charSet", "utf-8");
			prop.put("lc_ctype", "utf-8");
			prop.put("encoding", "utf-8");

			// props.put ("charSet", "UTF-8");
			String url = "jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ=e:\\jia.mdb";
			// Connection conn = DriverManager.getConnection(url, "", "");
			Connection conn = DriverManager.getConnection(url, props);

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery("select * from wx");

			int i = 0;
			List<Relation> relations = new ArrayList<Relation>();
			while (resultSet.next()) {
				String doc_id = new String(resultSet.getBytes(1), "gbk");
				String text = new String(resultSet.getBytes(2), "gbk");
				String[] sentences = Utils.breakParagraphIntoSentences(text);

				for (String sentence : sentences) {

					MySQLUtils.insertRelations("jia",
							new HasCoreRelationExtractor(sentence, doc_id)
									.getRelations(), false);

				}

				// if (i++ > 2) break;
			}
			// new RelationRenderer(relations, new
			// File("test.xls")).outputFile();

			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void test2() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Properties props = new Properties();
			Properties prop = new Properties();
			// prop.put("charSet", "UTF-8");
			// prop.put("charSet", "UTF-8");

			prop.put("user", "");
			prop.put("password", "");
			prop.put("charSet", "utf-8");
			prop.put("lc_ctype", "utf-8");
			prop.put("encoding", "utf-8");

			// props.put ("charSet", "UTF-8");
			String url = "jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ=e:\\jia.mdb";
			// Connection conn = DriverManager.getConnection(url, "", "");
			Connection conn = DriverManager.getConnection(url, props);

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery("select * from wx");

			int i = 0;
			List<Relation> relations = new ArrayList<Relation>();
			while (resultSet.next()) {
				String doc_id = new String(resultSet.getBytes(1), "gbk");
				String text = new String(resultSet.getBytes(2), "gbk");
				String[] sentences = Utils.breakParagraphIntoSentences(text);

				for (String sentence : sentences) {
					/*
					 * MySQLUtils.insertRelations("jia", new
					 * HasCoreRelationExtractor(sentence, doc_id)
					 * .getRelations());
					 */
					relations.addAll(new HasCoreRelationExtractor(sentence,
							doc_id).getRelations());

					if (relations.size() > 5000) {
						// new RelationRenderer(relations, new
						// File("e://data/jia/jia" + (i++) +
						// ".xls")).outputFile();
						new RelationRenderer(relations, new File("e://data/jia"
								+ (i++) + ".xls")).outputFile();
						relations = new ArrayList<Relation>();
					}

				}

				// if (i++ > 2) break;
			}
			// new RelationRenderer(relations, new
			// File("test.xls")).outputFile();

			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void test3() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Properties props = new Properties();
			Properties prop = new Properties();
			// prop.put("charSet", "UTF-8");
			// prop.put("charSet", "UTF-8");

			prop.put("user", "");
			prop.put("password", "");
			prop.put("charSet", "utf-8");
			prop.put("lc_ctype", "utf-8");
			prop.put("encoding", "utf-8");

			// props.put ("charSet", "UTF-8");
			String url = "jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ=e:\\jia.mdb";
			// Connection conn = DriverManager.getConnection(url, "", "");
			Connection conn = DriverManager.getConnection(url, props);

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery("select * from wx");

			int i = 0;
			List<Relation> relations = new ArrayList<Relation>();
			while (resultSet.next()) {
				String doc_id = new String(resultSet.getBytes(1), "gbk");
				String text = new String(resultSet.getBytes(2), "gbk");
				// String[] sentences = Utils.breakParagraphIntoSentences(text);

				// for (String sentence : sentences){

				MySQLUtils.insertPairs("relation", new CoreRelationExtractor(
						text, doc_id).getRelations());

				// }

				// if (i++ > 2) break;
			}
			// new RelationRenderer(relations, new
			// File("test.xls")).outputFile();

			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Test
	public void test4() {
		try {
			Connection conn = MySQLUtils.getConnection();

			Statement stmt = conn.createStatement();

			int num_articles = getNumArticles(stmt);

			for (int i = 1; i <= num_articles; i++) {
				ResultSet resultSet = stmt
						.executeQuery("select id, description from resource where id=" + i);

				if (resultSet.next()) {
					String doc_id = resultSet.getString(1);
					String text = resultSet.getString(2);
					System.out.println(doc_id + ": " + text);
					// String[] sentences =
					// Utils.breakParagraphIntoSentences(text);

					// for (String sentence : sentences){

					MySQLUtils.insertPairs("relation",
							new CoreRelationExtractor(text, doc_id)
									.getRelations());

					// }

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

	private int getNumArticles(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("select count(*) as c from resource");
		rs.next();
		int num_articles = rs.getInt(1);
		rs.close();
		return num_articles;
	}

}