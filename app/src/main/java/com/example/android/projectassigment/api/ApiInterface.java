package com.example.android.projectassigment.api;

import com.example.android.projectassigment.model.ApiResponse;
import com.example.android.projectassigment.model.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/api/android_intern_task")
    Call<ApiResponse> userLogin(@Body LoginRequest request);
}
