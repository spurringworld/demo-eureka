package com.flomesh.poc.xbx.vo;


public class StudentVO{
	private int id;
	private String name;
	private int age;
	private int schoolId;
	

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


	public StudentVO() {
		super();
	}
	
	public StudentVO(int id, String name, int age, int schoolId) {
		super();
		this.name = name;
		this.age = age;
		this.id = id;
		this.schoolId = schoolId;
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
 
}
 
