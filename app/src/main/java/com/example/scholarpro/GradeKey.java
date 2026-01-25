package com.example.scholarpro;

import java.util.Objects;

public class GradeKey {
    String grade;
    double weight;

    public GradeKey(String grade, double weight) {
        this.grade = grade;
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, weight);
    }
}
