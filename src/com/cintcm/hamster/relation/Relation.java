package com.cintcm.hamster.relation;

public class Relation implements Comparable<Relation> {

	private String subject;
	private String predicate;
	private String object;
	private String text;
	private String docId;	
	private double value;
	
	public Relation(String subject, String predicate, String object) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}

	public Relation setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public Relation setPredicate(String predicate) {
		this.predicate = predicate;
		return this;
	}

	public Relation setObject(String object) {
		this.object = object;
		return this;
	}

	public Relation setValue(double value) {
		this.value = value;
		return this;
	}
	
	public Relation setText(String text) {
		this.text = text;
		return this;
	}

	public Relation setDocId(String docId) {
		this.docId = docId;
		return this;
	}
	
	public String getText() {
		return text;
	}
	
	public String getDocId() {
		return docId;
	}
	
	public String getSubject() {
		return subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public String getObject() {
		return object;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Relation [subject=" + subject + ", predicate=" + predicate
				+ ", object=" + object + "]";
	}

	@Override
	public int compareTo(Relation arg) {
		if (value > arg.value)
			return 1;
		else if (value == arg.value)
			return 0;
		else
			return -1;
	}
}
