package com.lunacia.scorems.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Student {
	private String studentNum;
	private int subId;
	private int score;
	private String examDate;
	private int classNum;
	private int classRank;
	private int infoId;
	private String studentName;

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	private HashMap<String , Object> allScore;

	public HashMap<String, Object> getAllScore() {
		return allScore;
	}

	public void setAllScore(HashMap<String, Object> allScore) {
		this.allScore = allScore;
	}


	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public Student() {
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getClassNum() {
		return classNum;
	}

	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}

	public int getClassRank() {
		return classRank;
	}

	public void setClassRank(int classRank) {
		this.classRank = classRank;
	}

	public String getExamDate () {
		return examDate;
	}

	public void setExamDate (String examDate) {
		this.examDate = examDate;
	}

//	public String toString(){
//		return "student_id" + this.getStudentNum();
//	}

}
