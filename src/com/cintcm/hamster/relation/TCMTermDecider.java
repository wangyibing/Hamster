package com.cintcm.hamster.relation;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.cintcm.hamster.db.HibernateSessionFactory;
import com.cintcm.hamster.db.tcmls.TTerm;

public class TCMTermDecider {
	/**
	 * 对于输入的概念中文名称, 在数据库中找到概念的对应信息(包括数据库中的id)
	 * @param words
	 * @return
	 */
	public static LinkedList<TTerm> identifyWordInDB(String words[]){
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery("from TTerm where termLabel = ?");

		String rst = "";
		LinkedList<TTerm> rstlist = new LinkedList<TTerm>();
		
		for (String word : words) {
			query.setParameter(0, word);
			List<TTerm> list = query.list();
			
			if(list.size() > 0) {
				TTerm term = list.get(0);
				rstlist.add(term);
			}
			
		}

		return rstlist;
	}
	
	public static boolean isTCMTerm(String word){
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery("from TTerm where termLabel = ?");

		String rst = "";
		LinkedList<TTerm> rstlist = new LinkedList<TTerm>();
		
		query.setParameter(0, word);
		List<TTerm> list = query.list();
			
		return (list.size() > 0) ;
	}
	
}
