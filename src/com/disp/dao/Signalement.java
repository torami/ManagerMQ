package com.disp.dao;

public class Signalement {
	private Integer id;
	private String importance;
	private String object;
	private String description;
	private String comment;
	private String place;
	private int idreporter;
	public Integer getId() {
		return id;
	}
	public String getImportance() {
		return importance;
	}
	public String getObject() {
		return object;
	}
	public String getDescription() {
		return description;
	}
	public String getComment() {
		return comment;
	}
	public String getPlace() {
		return place;
	}
	public int getIdreporter() {
		return idreporter;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public void setIdreporter(int idreporter) {
		this.idreporter = idreporter;
	}


}