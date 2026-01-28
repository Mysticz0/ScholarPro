package com.example.scholarpro;

import androidx.lifecycle.ViewModel;

public class CalculatorViewModel extends ViewModel {
    public GraphCalculator calculator;
    public CalculatorViewModel(){
        calculator = new GraphCalculator();
    }
}
