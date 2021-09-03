package com.app.lostandfound.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LostAllPost implements Parcelable {
    @SerializedName("post_id")
    private String post_id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("post_type")
    private String post_type;
    @SerializedName("category_id")
    private String category_id;
    @SerializedName("subcategory_id")
    private String subcategory_id;
    @SerializedName("post_name")
    private String post_name;
    @SerializedName("post_description")
    private String post_description;
    @SerializedName("post_price")
    private String post_price;
    @SerializedName("post_image")
    private String post_image;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("address")
    private String address;
    @SerializedName("last_location")
    private String last_location;
    @SerializedName("last_date")
    private String last_date;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("age")
    private String age;
    @SerializedName("gender")
    private String gender;
    @SerializedName("height")
    private String height;
    @SerializedName("color")
    private String color;
    @SerializedName("reward")
    private String reward;
    @SerializedName("brand")
    private String brand;
    @SerializedName("model")
    private String model;

    @SerializedName("breed")
    private String breed;

    @SerializedName("title")
    private String title;

    @SerializedName("post_status")
    private String post_status;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("filter_status")
    private String filter_status;
    @SerializedName("found_user_id")
    private String found_user_id;
    @SerializedName("images")
    ArrayList<Images> imagesArrayList;

    @SerializedName("like")
    private int like;


    protected LostAllPost(Parcel in) {
        post_id = in.readString();
        user_id = in.readString();
        post_type = in.readString();
        category_id = in.readString();
        subcategory_id = in.readString();
        post_name = in.readString();
        post_description = in.readString();
        post_price = in.readString();
        post_image = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        address = in.readString();
        last_location = in.readString();
        last_date = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        age = in.readString();
        gender = in.readString();
        height = in.readString();
        color = in.readString();
        reward = in.readString();
        brand = in.readString();
        model = in.readString();
        title = in.readString();
        breed = in.readString();
        post_status = in.readString();
        created_at = in.readString();
        filter_status = in.readString();
        found_user_id = in.readString();
        imagesArrayList = in.readArrayList(Images.class.getClassLoader());
        like = in.readInt();
    }

    public static final Creator<LostAllPost> CREATOR = new Creator<LostAllPost>() {
        @Override
        public LostAllPost createFromParcel(Parcel in) {
            return new LostAllPost(in);
        }

        @Override
        public LostAllPost[] newArray(int size) {
            return new LostAllPost[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(post_id);
        parcel.writeString(user_id);
        parcel.writeString(post_type);
        parcel.writeString(category_id);
        parcel.writeString(subcategory_id);
        parcel.writeString(post_name);
        parcel.writeString(post_description);
        parcel.writeString(post_price);
        parcel.writeString(post_image);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(address);
        parcel.writeString(last_location);
        parcel.writeString(last_date);
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(age);
        parcel.writeString(gender);
        parcel.writeString(height);
        parcel.writeString(color);
        parcel.writeString(reward);
        parcel.writeString(brand);
        parcel.writeString(model);
        parcel.writeString(title);
        parcel.writeString(breed);
        parcel.writeString(post_status);
        parcel.writeString(created_at);
        parcel.writeString(filter_status);
        parcel.writeString(found_user_id);
        parcel.writeList(imagesArrayList);
        parcel.writeInt(like);
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

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

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }

    public String getPost_price() {
        return post_price;
    }

    public void setPost_price(String post_price) {
        this.post_price = post_price;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLast_location() {
        return last_location;
    }

    public void setLast_location(String last_location) {
        this.last_location = last_location;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPost_status() {
        return post_status;
    }

    public void setPost_status(String post_status) {
        this.post_status = post_status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFilter_status() {
        return filter_status;
    }

    public void setFilter_status(String filter_status) {
        this.filter_status = filter_status;
    }

    public String getFound_user_id() {
        return found_user_id;
    }

    public void setFound_user_id(String found_user_id) {
        this.found_user_id = found_user_id;
    }

    public ArrayList<Images> getImagesArrayList() {
        return imagesArrayList;
    }

    public void setImagesArrayList(ArrayList<Images> imagesArrayList) {
        this.imagesArrayList = imagesArrayList;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public static Creator<LostAllPost> getCREATOR() {
        return CREATOR;
    }
}
