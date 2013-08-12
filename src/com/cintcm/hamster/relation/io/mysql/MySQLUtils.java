package com.cintcm.hamster.relation.io.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.cintcm.hamster.relation.Relation;

public class MySQLUtils {
	
	

	public static void insertRelations(String table, List<Relation> rels) {
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs

		String url = "jdbc:mysql://localhost:3306/hamster";

		// MySQL配置时的用户名

		String user = "root";

		// Java连接MySQL配置时的密码

		String password = "yutong";

		try {
			// 加载驱动程序

			Class.forName(driver);

			// 连续数据库

			Connection conn = DriverManager.getConnection(url, user, password);

			if (!conn.isClosed())

				System.out.println("Succeeded connecting to the Database!");

			// statement用来执行SQL语句

			Statement st = conn.createStatement();

			for (Relation rel : rels) {
				/*
				String sql = "insert into " + table + " values('" + rel.getSubject()
						+ "','" + rel.getPredicate() + "','" + rel.getObject()
						+ "','" + rel.getValue() + "','" + rel.getText()
						+ "','" + rel.getDocId() + "')";*/
						
				String sql = "insert into " + table + " values('" + rel.getSubject()
				+ "','" + rel.getPredicate() + "','" + rel.getObject()
				+ "','" + rel.getValue() + "','','" + rel.getDocId() + "')";

				st.executeUpdate(sql);
			}

			/*
			 * ResultSet rs = st.executeQuery(sql); while (rs.next()) {
			 * System.out.println(rs.getString(1)); }
			 */
            st.close();
            conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
