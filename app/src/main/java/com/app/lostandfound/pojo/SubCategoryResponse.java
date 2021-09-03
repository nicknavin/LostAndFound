package com.app.lostandfound.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubCategoryResponse
{
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<SubCategoryData>subCategoryDataList;


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

    public ArrayList<SubCategoryData> getSubCategoryDataList() {
        return subCategoryDataList;
    }

    public void setSubCategoryDataList(ArrayList<SubCategoryData> subCategoryDataList) {
        this.subCategoryDataList = subCategoryDataList;
    }
}
