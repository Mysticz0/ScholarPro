package com.example.scholarpro;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/grades/add")
    Call<Map<String, Object>> addGrade(@Body GradeRequest request);

    @GET("/api/grades/cgpa")
    Call<Map<String, Object>> getCGPA();

    @POST("/api/grades/scholarship-check")
    Call<Map<String, Object>> checkScholarship(@Body ScholarshipRequest request);

    @POST("/api/grades/reset")
    Call<Void> reset();
}