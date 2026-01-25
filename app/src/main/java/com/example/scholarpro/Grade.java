package com.example.scholarpro;

import java.util.Objects;

public class Grade {
    String grade;
    double weight;

    public Grade(String grade, double weight) {
        this.grade = grade;
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, weight);
    }

}
