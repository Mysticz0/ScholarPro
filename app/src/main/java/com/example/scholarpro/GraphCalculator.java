package com.example.scholarpro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class GraphCalculator {

    public final Map<GradeKey, Double> gradeMap;
    public ArrayList<Double> cgpaOverTime;
    public ArrayList<GradeKey> gradeEntryList;
    private static final double SCHOLARSHIP_AVERAGE = 10.0;

    public GraphCalculator() {
        gradeMap = new HashMap<>();
        cgpaOverTime = new ArrayList<>();
        gradeEntryList = new ArrayList<>();
        initializeGradeMap();
    }

    private void initializeGradeMap() {
        gradeMap.put(new GradeKey("A+", 1.00), 12.00);
        gradeMap.put(new GradeKey("A", 1.00), 11.00);
        gradeMap.put(new GradeKey("A-", 1.00), 10.00);
        gradeMap.put(new GradeKey("B+", 1.00), 9.00);
        gradeMap.put(new GradeKey("B", 1.00), 8.00);
        gradeMap.put(new GradeKey("B-", 1.00), 7.00);
        gradeMap.put(new GradeKey("C+", 1.00), 6.00);
        gradeMap.put(new GradeKey("C", 1.00), 5.00);
        gradeMap.put(new GradeKey("C-", 1.00), 4.00);
        gradeMap.put(new GradeKey("D+", 1.00), 3.00);
        gradeMap.put(new GradeKey("D", 1.00), 2.00);
        gradeMap.put(new GradeKey("D-", 1.00), 1.00);
        gradeMap.put(new GradeKey("F", 1.00), 0.00);

        gradeMap.put(new GradeKey("A+", 0.50), 6.00);
        gradeMap.put(new GradeKey("A", 0.50), 5.50);
        gradeMap.put(new GradeKey("A-", 0.50), 5.00);
        gradeMap.put(new GradeKey("B+", 0.50), 4.50);
        gradeMap.put(new GradeKey("B", 0.50), 4.00);
        gradeMap.put(new GradeKey("B-", 0.50), 3.50);
        gradeMap.put(new GradeKey("C+", 0.50), 3.00);
        gradeMap.put(new GradeKey("C", 0.50), 2.50);
        gradeMap.put(new GradeKey("C-", 0.50), 2.00);
        gradeMap.put(new GradeKey("D+", 0.50), 1.50);
        gradeMap.put(new GradeKey("D", 0.50), 1.00);
        gradeMap.put(new GradeKey("D-", 0.50), 0.50);
        gradeMap.put(new GradeKey("F", 0.50), 0.00);

        gradeMap.put(new GradeKey("A+", 0.25), 3.00);
        gradeMap.put(new GradeKey("A", 0.25), 2.75);
        gradeMap.put(new GradeKey("A-", 0.25), 2.50);
        gradeMap.put(new GradeKey("B+", 0.25), 2.25);
        gradeMap.put(new GradeKey("B", 0.25), 2.00);
        gradeMap.put(new GradeKey("B-", 0.25), 1.75);
        gradeMap.put(new GradeKey("C+", 0.25), 1.50);
        gradeMap.put(new GradeKey("C", 0.25), 1.25);
        gradeMap.put(new GradeKey("C-", 0.25), 1.00);
        gradeMap.put(new GradeKey("D+", 0.25), 0.75);
        gradeMap.put(new GradeKey("D", 0.25), 0.50);
        gradeMap.put(new GradeKey("D-", 0.25), 0.25);
        gradeMap.put(new GradeKey("F", 0.25), 0.00);

    }

    public void calculateCGPA() {
        double totalGradePoints = 0.0;
        double totalCredits = 0.0;

        for (int i = 0; i < gradeEntryList.size(); i++) {
            Double gradeValue = gradeMap.get(gradeEntryList.get(i));
            if (gradeValue != null) {
                totalGradePoints += gradeValue;
                totalCredits += gradeEntryList.get(i).weight;
            }
        }

        cgpaOverTime.add(totalGradePoints / totalCredits);
    }

    public void addGrade(String grade, double weight) {
        gradeEntryList.add(new GradeKey(grade, weight));
    }

    public double getCgpaAtX(int x) {
        return cgpaOverTime.get(x);
    }

    public List<Double> getCgpaOverTime() {
        return cgpaOverTime;
    }

    public double getCurrentCGPA(){
        if (cgpaOverTime.isEmpty()) {
            return 0.0;
        }
        return cgpaOverTime.get(cgpaOverTime.size() - 1);
    }

    public double getCreditsCompleted(){
        double credits = 0.0;
        for (int i = 0; i < gradeEntryList.size(); i++){
            credits += gradeEntryList.get(i).weight;

        }
        return credits;
    }
    public double getAverageNeeded(double creditsRemaining){
        double completeCredits = getCreditsCompleted();
        return ((SCHOLARSHIP_AVERAGE * (completeCredits + creditsRemaining)) - getCurrentCGPA() * completeCredits) / creditsRemaining;
    }

    public boolean isAveragePossible(double creditsRemaining){
        return getAverageNeeded(creditsRemaining) <= 12.0;
    }

    public void reset(){
        gradeEntryList.clear();
        cgpaOverTime.clear();

    }
}