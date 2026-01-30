package com.example.scholarpro;

@SuppressWarnings("unused")
public class GradeRequest {
    private String grade;
    private double weight;

    public GradeRequest(String grade, double weight) {
        this.grade = grade;
        this.weight = weight;
    }

    public String getGrade() { return grade; }
    public double getWeight() { return weight; }
}