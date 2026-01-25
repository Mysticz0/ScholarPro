package com.example.scholarpro;

import android.graphics.Point;
import android.graphics.PointF;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphCalculator {

    private static final float INITIAL_STEP = 1f;
    private Map<GradeKey, Double> gradeMap;
    private List<DataPoint> points;

    public GraphCalculator() {
        points = new ArrayList<>();
        gradeMap = new HashMap<>();
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

    public double calculateCGPA(List<Grade> gradeEntryList) {
        ArrayList<Grade> gradePoints = new ArrayList<Grade>();
        double totalGradePoints = 0.0;
        double totalWeight = 0.0;

        for (int i = 0; i < gradeEntryList.size(); i++) {
            gradePoints.add(gradeEntryList[i]);

        }


        for (int i = 0; i < gradeEntryList.size(); i++) {
            totalGradePoints = gradeMap.get(gradeEntryList[i]);
            totalWeight += gradeEntryList[i][1];

        }
        return totalGradePoints / totalWeight;
    }

    public List<DataPoint> calculateParabola(float start, float end) {

        for (float x = start; x <= end; x += INITIAL_STEP) {
            points.add(new DataPoint(x, x * x * x));
        }
        return points;
    }
}