package com.cintcm.hamster.relation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cintcm.hamster.relation.io.mysql.MySQLUtils;

public class RelationManager {
	private static final String DELIMITER = ":";
	Map<String, Measurements> relations = new HashMap<String, Measurements>();

	class Measurements {
		Measurements(String predicate, double value, int frequency,
				int distance, String doc) {
			super();
			this.value = value;
			// this.frequency = frequency;
			this.distance = distance;
			addDoc(doc);
			addPredicate(predicate);
		}

		double value = 0;
		int frequency = 0;
		double distance = 0;
		Set<String> docs = new HashSet<String>();
		Set<String> predicates = new HashSet<String>();

		void addDoc(String doc) {
			if ((doc != null) && (doc != ""))
				this.docs.add(doc);
		}

		void addPredicate(String predicate) {
			if ((predicate != null) && (predicate != ""))
				this.predicates.add(predicate);
		}

		String serializePredicate() {
			String predicate = "";
			if (!predicates.isEmpty()) {
				predicate = "|";
				for (String s : predicates) {
					predicate += s + "|";
				}
			}
			return predicate;
		}

		String serializeDocs() {
			String doc = "";
			if (!docs.isEmpty()) {
				doc = "|";
				for (String s : docs) {
					doc += s + "|";
				}
			}
			return doc;
		}

	}

	public void addRelations(Collection<Relation> rels) {
		for (Relation rel : rels) {
			addRelation(rel);
		}
	}

	public void addRelation(Relation rel) {
		String key = getKey(rel);
		if (relations.keySet().contains(key)) {
			Measurements m = relations.get(key);
			// m.frequency++;
			m.distance = Math.min(rel.getDistance(), m.distance);
			m.addDoc(rel.getDocId());
			m.addPredicate(rel.getPredicate());

		} else {
			this.relations.put(
					key,
					new Measurements(rel.getPredicate(), rel.getValue(), 1, rel
							.getDistance(), rel.getDocId()));
		}
	}

	private String getKey(Relation rel) {
		return rel.getSubject() + DELIMITER + rel.getObject();
	}

	public void insertPairs(String table) {

		try {

			Connection conn = MySQLUtils.getConnection();

			// if (!conn.isClosed())
			// System.out.println("Succeeded connecting to the Database!");

			Statement st = conn.createStatement();
			int i = 0;

			for (String key : relations.keySet()) {

				try{
				String[] split = key.split(DELIMITER);
				String subject = split[0];
				String object = split[1];

				Measurements measurements = relations.get(key);

				// System.out.println("insert relations: (" + rel.getSubject() +
				// "," + rel.getPredicate() + "," + rel.getObject() + ")");

				String query = "select predicate, frequency, distance, docs from "
						+ table
						+ " where subject = '"
						+ subject
						+ "' and object = '" + object + "'";

				ResultSet rs = st.executeQuery(query);
				String sql = null;
				if (rs.next()) {

					measurements.predicates.addAll(Utils.splitWordsAsSet(rs
							.getString("predicate")));
					String predicate = measurements.serializePredicate();

					measurements.docs.addAll(Utils.splitWordsAsSet(rs
							.getString("docs")));

					String docs = measurements.serializeDocs();

					measurements.distance = Math.min(measurements.distance,
							rs.getDouble("distance"));
					sql = "UPDATE " + table + " SET predicate='" + predicate
							+ "', docs='" + docs + "', frequency='"
							+ measurements.docs.size() + "', distance='"
							+ measurements.distance + "' WHERE subject = '"
							+ subject + "' and object = '" + object + "'";
					System.out.println(sql);

				} else {

					sql = "insert into " + table + " (subject, predicate, object, value, distance, frequency, docs) values('" + subject
							+ "','" + measurements.serializePredicate() + "','"
							+ object + "','" + measurements.value + "','"
							+ measurements.distance + "','"
							+ measurements.docs.size() + "','"
							+ measurements.serializeDocs() + "')";
					System.out.println(sql);
				}

				st.executeUpdate(sql);
				}catch(Throwable t){
					t.printStackTrace();
				}
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

	public void insertPairsQuickly(String table) {

		try {

			Connection conn = MySQLUtils.getConnection();

			// if (!conn.isClosed())
			// System.out.println("Succeeded connecting to the Database!");

			Statement st = conn.createStatement();
			int i = 0;

			for (String key : this.relations.keySet()) {

				String[] split = key.split(DELIMITER);
				String subject = split[0];
				String object = split[1];
				// System.out.println(subject + "|" + object);

				String sql = "insert into " + table + " values('" + subject
						+ "','','" + object + "','" + relations.get(key).value
						+ "','" + relations.get(key).distance + "','"
						+ relations.get(key).frequency + "','')";
				// System.out.println( sql );

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

	public void insertRelationsQuickly(String table) {

		try {

			Connection conn = MySQLUtils.getConnection();

			// if (!conn.isClosed())
			// System.out.println("Succeeded connecting to the Database!");

			Statement st = conn.createStatement();
			int i = 0;

			for (String key : this.relations.keySet()) {
				try {
					String[] split = key.split(DELIMITER);
					String subject = split[0];
					String object = split[1];

					double value = relations.get(key).value;
					double distance = relations.get(key).distance;
					int frequency = relations.get(key).frequency;

					// System.out.println("insert relations: (" +
					// rel.getSubject() + "," + rel.getPredicate() + "," +
					// rel.getObject() + ")");
					// System.out.println("insert relations: (" + rel + ")");

					String query = "select frequency, distance from " + table
							+ " where subject = '" + subject
							+ "' and object = '" + object + "'";

					ResultSet rs = st.executeQuery(query);
					String sql = null;
					if (rs.next()) {
						/*
						 * if ((predicate ==
						 * "")||(rs.getString("predicate").contains("|" +
						 * predicate + "|"))) { predicate =
						 * rs.getString("predicate"); } else { predicate = "|" +
						 * predicate + rs.getString("predicate"); }
						 */

						frequency += rs.getDouble("frequency");

						distance = Math.min(distance, rs.getDouble("distance"));
						sql = "UPDATE " + table + " SET frequency='"
								+ frequency + "', distance='" + distance
								+ "' WHERE subject = '" + subject
								+ "' and object = '" + object + "'";
						// System.out.println( sql );

					} else {

						sql = "insert into " + table + " values('" + subject
								+ "','','" + object + "','" + value + "','"
								+ distance + "','" + frequency + "','')";
						// System.out.println( sql );
					}

					st.executeUpdate(sql);
				} catch (Throwable ex) {

					System.err.println(ex);

				}

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
