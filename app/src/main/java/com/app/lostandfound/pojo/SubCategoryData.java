package com.app.lostandfound.pojo;

import com.google.gson.annotations.SerializedName;

public class SubCategoryData {

    @SerializedName("category_id")
    private String category_id;
    @SerializedName("subcategory_id")
    private String subcategory_id;
    @SerializedName("subcategory_title")
    private String subcategory_title;
    @SerializedName("subcategory_image")
    private String subcategory_image;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(String subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getSubcategory_title() {
        return subcategory_title;
    }

    public void setSubcategory_title(String subcategory_title) {
        this.subcategory_title = subcategory_title;
    }

    public String getSubcategory_image() {
        return subcategory_image;
    }

    public void setSubcategory_image(String subcategory_image) {
        this.subcategory_image = subcategory_image;
    }
}
