package com.flomesh.poc.xbx.vo;

import java.util.List;

public class StudentScoreVO {
    private StudentVO student;
    private List<ScoreVO> scores;
    
    public StudentVO getStudent() {
        return student;
    }
    public void setStudent(StudentVO student) {
        this.student = student;
    }
    public List<ScoreVO> getScores() {
        return scores;
    }
    public void setScores(List<ScoreVO> scores) {
        this.scores = scores;
    }
}
