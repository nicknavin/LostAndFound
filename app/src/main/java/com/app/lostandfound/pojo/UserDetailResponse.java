package com.app.lostandfound.pojo;

import com.google.gson.annotations.SerializedName;

public class UserDetailResponse
{
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("userDetails")
    private UserDetails userDetails;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
