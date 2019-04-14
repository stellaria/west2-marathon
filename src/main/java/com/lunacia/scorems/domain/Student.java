package com.lunacia.scorems.domain;

import java.util.List;
import java.util.Map;

public class Student {
	private String st_num;
	private String name;
	private Map<String, Integer> score;
	private int date_code;

	public Student() {
	}

	public String getSt_num() {

		return st_num;
	}

	public void setSt_num(String st_num) {
		this.st_num = st_num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Integer> getScore() {
		return score;
	}

	public void setScore(Map<String, Integer> score) {
		this.score = score;
	}

	public int getDate_code() {
		return date_code;
	}

	public void setDate_code(int date_code) {
		this.date_code = date_code;
	}
}
