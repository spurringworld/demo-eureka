package com.flomesh.poc.xbx.vo;

import java.util.List;

public class SchoolVO {
    private int id;
    private String name;
    private String address;
    private List<StudentVO> students;
    private List<TeacherVO> teachers;

    public List<StudentVO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentVO> students) {
        this.students = students;
    }

    public SchoolVO() {
	}
    
    public SchoolVO(int id, String name, String address) {
		this.name = name;
		this.address = address;
		this.id = id;
	}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public List<TeacherVO> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherVO> teachers) {
        this.teachers = teachers;
    }


}
