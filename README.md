# ScholarPro

## Overview

ScholarPro is an Android application built with Java that helps students at Carleton University monitor their academic progress. By inputting course grades and credits, users can calculate their current CGPA and determine the average grade required in their remaining courses to achieve a target CGPA for scholarship retention.

The app features a clean, user-friendly interface for grade management and visualizes the user's CGPA trend over time using a chart.

## Features

-   **CGPA Calculation:** Automatically calculates the Cumulative Grade Point Average (CGPA) based on entered letter grades and course credits.
-   **Scholarship Target Calculation:** Determines the average grade needed in future courses to meet a specific academic target (e.g., a 10.0 CGPA).
-   **Feasibility Check:** Informs the user if their target CGPA is still mathematically achievable based on their remaining credits.
-   **Data Visualization:** A line chart (`FirstFragment`) displays the user's CGPA history, powered by the `MPAndroidChart` library.
-   **State Persistence:** Uses `ViewModel` to ensure that grade data and calculations survive configuration changes like screen rotations.

## How It Works

The application is structured around two main fragments managed by the Jetpack Navigation Component:

1.  **Grade Entry & Calculation (`SecondFragment`):** This is the primary interaction screen where users can:
    -   Submit a letter grade (e.g., "A+", "B") and the corresponding course credits.
    -   Input is validated to prevent empty or invalid entries.
    -   Enter the number of credits remaining in their degree.
    -   Press **"Get Average"** to calculate the required average needed to keep their scholarship for their remaining credits. The result is displayed, along with a message if the target is no longer achievable.

2.  **Chart View (`FirstFragment`):** Displays a line chart visualizing the user's CGPA trend over time, allowing for a quick visual check of their academic performance.

The core logic is managed by `CalculatorViewModel` and `GraphCalculator`. `CalculatorViewModel` holds the application's data, while `GraphCalculator` contains the business logic for all calculations.

## Tech Stack & Dependencies

-   **Language:** Java
-   **Architecture:** Model-View-ViewModel (MVVM)
-   **UI:** Android Native UI with View Binding for type-safe view access.
-   **Navigation:** Android Jetpack Navigation Component for managing navigation between fragments.
-   **Charting:** [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) (v3.1.0) for rendering performance graphs.
-   **Core Libraries:**
    -   `androidx.appcompat:appcompat:1.6.1`
    -   `com.google.android.material:material:1.10.0`
    -   `androidx.constraintlayout:constraintlayout:2.1.4`

## How to Use

1.  Launch the app and navigate to the grade entry screen.
2.  Enter a letter grade (e.g., "A+") and the corresponding course credits.
3.  Press **"Submit Grade"** to save the entry. A confirmation message will appear.
4.  To calculate your required average, enter the total credits you have left to complete in the "Credits Remaining" field.
5.  Press **"Get Average Needed To Keep Scholarship"**. The app will display the average grade you need. If your goal is mathematically impossible, it will inform you.
6.  To view your CGPA history, tap the **"View Chart"** button to see a graph of your progress.
