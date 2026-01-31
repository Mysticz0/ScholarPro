# ScholarPro

## Overview
ScholarPro is an Android application built with Java that helps students at Carleton University monitor their academic progress. By inputting course grades and credits, users can calculate their current CGPA and determine the average grade required in their remaining courses to achieve a target CGPA for scholarship retention.

The app features a clean, user-friendly interface for grade management and visualizes the user's CGPA trend over time using a chart. **ScholarPro now uses a REST API backend architecture**, allowing for cloud deployment and scalable data management.

## Features
-   **CGPA Calculation:** Automatically calculates the Cumulative Grade Point Average (CGPA) based on entered letter grades and course credits via backend API.
-   **Scholarship Target Calculation:** Determines the average grade needed in future courses to meet a specific academic target (e.g., a 10.0 CGPA).
-   **Feasibility Check:** Informs the user if their target CGPA is still mathematically achievable based on their remaining credits.
-   **Data Visualization:** A line chart (`FirstFragment`) displays the user's CGPA history, powered by the `MPAndroidChart` library.
-   **REST API Integration:** All calculations are performed by a separate Spring Boot backend service, demonstrating microservices architecture.
-   **Cloud-Ready:** Backend can be deployed via Docker, Kubernetes, and Helm for production environments.

## Architecture

### Frontend (Android App)
The application is structured around two main fragments managed by the Jetpack Navigation Component:

1.  **Grade Entry & Calculation (`SecondFragment`):** This is the primary interaction screen where users can:
    -   Submit a letter grade (e.g., "A+", "B") and the corresponding course credits.
    -   Input is validated to prevent empty or invalid entries.
    -   Enter the number of credits remaining in their degree.
    -   Press **"Get Average"** to calculate the required average needed to keep their scholarship. The result is displayed via API call, along with a message if the target is no longer achievable.

2.  **Chart View (`FirstFragment`):** Displays a line chart visualizing the user's CGPA trend over time fetched from the backend API, allowing for a quick visual check of academic performance.

### Backend API
The app connects to a RESTful Spring Boot backend that handles all CGPA calculations and data persistence. See the [scholarpro-backend repository](https://github.com/Mysticz0/scholarpro-backend) for backend implementation details including Docker, Kubernetes, and Helm deployment configurations.

## Tech Stack & Dependencies

### Android App
-   **Language:** Java 17
-   **Architecture:** Model-View-ViewModel (MVVM) with REST API integration
-   **UI:** Android Native UI with View Binding for type-safe view access
-   **Navigation:** Android Jetpack Navigation Component for managing navigation between fragments
-   **Networking:** 
    -   Retrofit 3.0.0 for HTTP API calls
    -   Gson Converter for JSON parsing
    -   OkHttp Logging Interceptor for debugging
-   **Charting:** [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) (v3.1.0) for rendering performance graphs
-   **Core Libraries:**
    -   `androidx.appcompat:appcompat:1.6.1`
    -   `com.google.android.material:material:1.10.0`
    -   `androidx.constraintlayout:constraintlayout:2.1.4`

### Backend
-   **Language:** Java 17
-   **Framework:** Spring Boot 3.x
-   **Build Tool:** Maven
-   **Deployment:** Docker, Kubernetes, Helm

## API Endpoints

The Android app communicates with the following REST endpoints:

-   `POST /api/grades/add` - Submit a new grade
-   `GET /api/grades/cgpa` - Retrieve current CGPA and history
-   `POST /api/grades/scholarship-check` - Calculate required average for scholarship
-   `POST /api/grades/reset` - Clear all grade data

## How to Use

### Prerequisites
-   Android Studio (latest version recommended)
-   Java 17 JDK
-   Running instance of [scholarpro-backend](https://github.com/Mysticz0/scholarpro-backend)

### Setup

1.  **Clone the repository:**
```bash
    git clone https://github.com/YOUR_USERNAME/scholarpro.git
    cd scholarpro
```

2.  **Configure backend URL:**
    
    Edit `RetrofitClient.java` and update the `BASE_URL`:
```java
    // For Android Emulator connecting to local backend
    private static final String BASE_URL = "http://10.0.2.2:8080/";
    
    // For real device on same network
    private static final String BASE_URL = "http://YOUR_COMPUTER_IP:8080/";
```

3.  **Open in Android Studio:**
    -   Open Android Studio
    -   Select "Open an existing project"
    -   Navigate to the cloned repository
    -   Wait for Gradle sync to complete

4.  **Run the app:**
    -   Connect an Android device or start an emulator
    -   Click the green "Run" button or press `Shift + F10`

### Using the App

1.  Ensure the backend service is running (see backend repository for instructions)
2.  Launch the app and navigate to the grade entry screen
3.  Enter a letter grade (e.g., "A+") and the corresponding course credits
4.  Press **"Submit Grade"** to save the entry. A confirmation message will appear
5.  To calculate your required average, enter the total credits you have left to complete in the "Credits Remaining" field
6.  Press **"Get Average Needed To Keep Scholarship"**. The app will display the average grade you need. If your goal is mathematically impossible, it will inform you
7.  To view your CGPA history, tap the **"View Chart"** button to see a graph of your progress
8.  Use **"Reset"** to clear all data (this will clear data on the server)

## Project Structure
```
app/src/main/java/com/example/scholarpro/
├── FirstFragment.java          # Chart view with CGPA visualization
├── SecondFragment.java         # Grade entry and scholarship calculator
├── ApiService.java             # Retrofit API interface
├── RetrofitClient.java         # HTTP client configuration
├── GradeRequest.java           # API request model
├── ScholarshipRequest.java     # API request model
├── GraphCalculator.java        # Legacy calculator (reference only)
└── GradeKey.java               # Grade mapping (reference only)
```

## Development Notes

-   The app previously performed calculations locally but has been refactored to use a REST API backend
-   `GraphCalculator.java` and `GradeKey.java` are kept in the repository for reference but are no longer actively used
-   All CGPA calculations now happen server-side via the Spring Boot backend
-   Network security configuration allows cleartext HTTP traffic for local development

## Related Repositories

-   **Backend API:** [scholarpro-backend](https://github.com/Mysticz0/scholarpro-backend) - Spring Boot REST API with Docker/Kubernetes deployment

## License

This project is for educational purposes.

## Author

Developed as a learning project to demonstrate:
-   Android app development with Java
-   REST API integration with Retrofit
-   MVVM architecture
-   Microservices architecture
-   Full-stack mobile application development
