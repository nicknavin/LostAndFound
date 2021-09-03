package com.app.lostandfound.pojo;

import com.google.gson.annotations.SerializedName;

public class CategoryData {
    @SerializedName("category_id")
    private String category_id;
    @SerializedName("category_title")
    private String category_title;
    @SerializedName("category_image")
    private String category_image;
    @SerializedName("status")
    private String status;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
