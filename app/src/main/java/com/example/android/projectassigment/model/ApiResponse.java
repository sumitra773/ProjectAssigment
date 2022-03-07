package com.example.android.projectassigment.model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("status")
    private Boolean status;

    public ApiResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
