package com.lunacia.scorems.domain;

public class User {
	private String studentNum;
	private String name;
	private int classNum;
	private int leader;
	private int flag;

	public User() {
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClassNum() {
		return classNum;
	}

	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}

	public int getLeader() {
		return leader;
	}

	public void setLeader(int leader) {
		this.leader = leader;
	}

	public int getFlag () {
		return flag;
	}

	public void setFlag (int flag) {
		this.flag = flag;
	}
}
