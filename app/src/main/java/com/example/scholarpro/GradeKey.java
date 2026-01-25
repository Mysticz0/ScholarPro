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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeKey gradeKey = (GradeKey) o;
        return Double.compare(gradeKey.weight, weight) == 0 &&
                Objects.equals(grade, gradeKey.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, weight);
    }
}
