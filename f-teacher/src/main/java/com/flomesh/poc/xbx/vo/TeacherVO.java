package com.flomesh.poc.xbx.vo;


public class TeacherVO{
	private int id;
	private String name;
	private String level;
	private int age;
	private int schoolId;
	private String email;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public TeacherVO() {
		super();
	}
	
	public TeacherVO(int id, String name, String level, int age, int schoolId, String email) {
		super();
		this.name = name;
		this.level = level;
		this.age = age;
		this.id = id;
		this.schoolId = schoolId;
		this.email = email;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public int getAge() {
		return age;
	}
 
	public void setAge(int age) {
		this.age = age;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
 
}
 
