package com.flomesh.poc.xbx.vo;

public class ClubVO {
    
    private int id;
    private String name;
    private String description;
    private int[] studentIds;

    public ClubVO(){
        super();
    }
    public ClubVO(int id, String name, String description, int[] studentIds) {
		this.id = id;
		this.name = name;
		this.description = description;
        this.studentIds = studentIds;
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

    public int[] getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(int[] studentIds) {
        this.studentIds = studentIds;
    }


}
