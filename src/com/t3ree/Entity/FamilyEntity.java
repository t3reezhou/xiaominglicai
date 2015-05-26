package com.t3ree.Entity;

public class FamilyEntity {
	private String id;
	private String family_name;
	private String question;

	public FamilyEntity(String id, String family_name, String question) {
		this.id = id;
		this.family_name = family_name;
		this.question = question;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}
