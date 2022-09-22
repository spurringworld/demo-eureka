package com.flomesh.poc.xbx.vo;

public class SubjectVO {
    
    private int id;
    private String name;
    private String description;
    private int[] teacherIds;

    public SubjectVO(){
        super();
    }
    
    public SubjectVO(int id, String name, String description, int[] teacherIds) {
		this.id = id;
		this.name = name;
		this.description = description;
        this.teacherIds = teacherIds;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int[] getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(int[] teacherIds) {
        this.teacherIds = teacherIds;
    }

    

}
