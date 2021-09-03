package com.app.lostandfound.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryResponse
{
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<CategoryData> categoryDataList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<CategoryData> getCategoryDataList() {
        return categoryDataList;
    }

    public void setCategoryDataList(ArrayList<CategoryData> categoryDataList) {
        this.categoryDataList = categoryDataList;
    }
}
