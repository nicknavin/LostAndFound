package com.app.lostandfound.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.LinkedList;

public class LostAllPostResponse
{

    @SerializedName("status")
    private String status;

    @SerializedName("total_records")
    private int total_records;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("no_of_records_per_page")
    private int no_of_records_per_page;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private LinkedList<LostAllPost> lostAllPostsList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal_records() {
        return total_records;
    }

    public void setTotal_records(int total_records) {
        this.total_records = total_records;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getNo_of_records_per_page() {
        return no_of_records_per_page;
    }

    public void setNo_of_records_per_page(int no_of_records_per_page) {
        this.no_of_records_per_page = no_of_records_per_page;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LinkedList<LostAllPost> getLostAllPostsList() {
        return lostAllPostsList;
    }

    public void setLostAllPostsList(LinkedList<LostAllPost> lostAllPostsList) {
        this.lostAllPostsList = lostAllPostsList;
    }
}
