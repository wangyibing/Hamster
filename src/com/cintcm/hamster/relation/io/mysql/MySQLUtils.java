package com.cintcm.hamster.relation.io.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.cintcm.hamster.relation.Relation;

public class MySQLUtils {
	public static Connection conn = null;

	public static Connection getConnection() throws ClassNotFoundException,
			SQLException {

		if (conn == null) {

			String driver = "com.mysql.jdbc.Driver";

			// URL指向要访问的数据库名scutcs

			String url = "jdbc:mysql://localhost:3306/docs";

			// MySQL配置时的用户名

			String user = "root";

			// Java连接MySQL配置时的密码

			String password = "yutong";

			// 加载驱动程序

			Class.forName(driver);

			// 连续数据库

			conn = DriverManager.getConnection(url, user, password);

			if (!conn.isClosed())

				System.out.println("Succeeded connecting to the Database!");

		}

		return conn;

	}
	
	

	public static void insertPairs(String table, List<Relation> rels) {

		try {

			Connection conn = getConnection();

			if (!conn.isClosed())

				System.out.println("Succeeded connecting to the Database!");

			Statement st = conn.createStatement();
            int i = 0;
            System.out.println("insert relations: " + rels.size() );
			for (Relation rel : rels) {
				 System.out.println("insert relation: " + (i++));
				String predicate = rel.getPredicate();
				double value = rel.getValue();
				double distance = rel.getDistance();
				String docId = rel.getDocId();
                
				String query = "select predicate, frequency, distance, docs from " + table
						+ " where subject = '" + rel.getSubject()
						+ "' and object = '" + rel.getObject() + "'";
				
				
				ResultSet rs = st.executeQuery(query);
				String sql = null;
				if (rs.next()) {
					if ((predicate == "")||(rs.getString("predicate").contains("|" + predicate + "|"))) {
						predicate = rs.getString("predicate");
					} else {
						predicate = "|" + predicate + rs.getString("predicate");
					}
					
					String docs = rs.getString("docs");
					if ((docId != "")&&(!docs.contains("|" + docId + "|"))) {
						
						docs = "|" + docId + docs;
					}
					//value += rs.getDouble("value");
					double frequency = rs.getDouble("frequency");
					if (!(rs.getString("docs").contains("|" + docId + "|"))){
						frequency++;
					}
					
					
					distance = Math.min(distance, rs.getDouble("distance"));
					sql = "UPDATE " + table + " SET predicate='" + predicate + "', docs='" + docs + "', frequency='" + frequency + "', distance='" + distance + "' WHERE subject = '" + rel.getSubject()
						+ "' and object = '" + rel.getObject() + "'";  
					
				}else{
					
					 sql = "insert into " + table + " values('"
							+ rel.getSubject() + "','"+ (predicate == "" ? "" : ("|" + predicate + "|")) + "','"
							+ rel.getObject() + "','" + value + "','" + rel.getDistance() + "','1','" + (docId == "" ? "" : ("|" + docId + "|")) + "')";
					 
				}

				

				st.executeUpdate(sql);
			}

			/*
			 * ResultSet rs = st.executeQuery(sql); while (rs.next()) {
			 * System.out.println(rs.getString(1)); }
			 */
			st.close();
			// conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insertRelations(String table, List<Relation> rels,
			Boolean addText) {
		// String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs

		// String url = "jdbc:mysql://localhost:3306/hamster";

		// MySQL配置时的用户名

		// String user = "root";

		// Java连接MySQL配置时的密码

		// String password = "yutong";

		try {
			// 加载驱动程序

			// Class.forName(driver);

			// 连续数据库

			// Connection conn = DriverManager.getConnection(url, user,
			// password);
			Connection conn = getConnection();

			if (!conn.isClosed())

				System.out.println("Succeeded connecting to the Database!");

			// statement用来执行SQL语句

			Statement st = conn.createStatement();

			for (Relation rel : rels) {
				String sql = null;
				if (addText) {
					sql = "insert into " + table + " values('"
							+ rel.getSubject() + "','" + rel.getPredicate()
							+ "','" + rel.getObject() + "','" + rel.getValue()
							+ "','" + rel.getText() + "','" + rel.getDocId()
							+ "')";
				} else {
					sql = "insert into " + table + " values('"
							+ rel.getSubject() + "','" + rel.getPredicate()
							+ "','" + rel.getObject() + "','" + rel.getValue()
							+ "','','" + rel.getDocId() + "')";
				}
				st.executeUpdate(sql);
			}

			/*
			 * ResultSet rs = st.executeQuery(sql); while (rs.next()) {
			 * System.out.println(rs.getString(1)); }
			 */
			st.close();
			// conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
