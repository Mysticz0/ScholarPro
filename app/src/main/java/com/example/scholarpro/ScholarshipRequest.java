package com.example.scholarpro;

@SuppressWarnings("unused")
public class ScholarshipRequest {
    private double creditsRemaining;

    public ScholarshipRequest(double creditsRemaining) {
        this.creditsRemaining = creditsRemaining;
    }

    public double getCreditsRemaining() { return creditsRemaining; }
}