package com.app.lostandfound.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PeopleData implements Parcelable
{
    private String user_id;
    private String title;
    private String breed;
    private String category_id;
    private String subcategory_id;
    private String first_name;
    private String last_name;
    private String Age;
    private String Gender;
    private String Height;
    private String Last_location;
    private String Last_date;
    private String Extra_detial;
    private String color;
    private String Reward;
    private String lattitude;
    private String longitude;
    private String post_type;
    private String Brand;
    private String model;

  public  PeopleData()
    {

    }
    protected PeopleData(Parcel in) {
        user_id = in.readString();
        title = in.readString();
        breed = in.readString();
        category_id = in.readString();
        subcategory_id = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        Age = in.readString();
        Gender = in.readString();
        Height = in.readString();
        Last_location = in.readString();
        Last_date = in.readString();
        Extra_detial = in.readString();
        color = in.readString();
        Reward = in.readString();
        lattitude = in.readString();
        longitude = in.readString();
        post_type = in.readString();
        Brand = in.readString();
        model = in.readString();
    }

    public static final Creator<PeopleData> CREATOR = new Creator<PeopleData>() {
        @Override
        public PeopleData createFromParcel(Parcel in) {
            return new PeopleData(in);
        }

        @Override
        public PeopleData[] newArray(int size) {
            return new PeopleData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_id);
        parcel.writeString(title);
        parcel.writeString(breed);
        parcel.writeString(category_id);
        parcel.writeString(subcategory_id);
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(Age);
        parcel.writeString(Gender);
        parcel.writeString(Height);
        parcel.writeString(Last_location);
        parcel.writeString(Last_date);
        parcel.writeString(Extra_detial);
        parcel.writeString(color);
        parcel.writeString(Reward);
        parcel.writeString(lattitude);
        parcel.writeString(longitude);
        parcel.writeString(post_type);
        parcel.writeString(Brand);
        parcel.writeString(model);
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
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
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getLast_location() {
        return Last_location;
    }

    public void setLast_location(String last_location) {
        Last_location = last_location;
    }

    public String getLast_date() {
        return Last_date;
    }

    public void setLast_date(String last_date) {
        Last_date = last_date;
    }

    public String getExtra_detial() {
        return Extra_detial;
    }

    public void setExtra_detial(String extra_detial) {
        Extra_detial = extra_detial;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getReward() {
        return Reward;
    }

    public void setReward(String reward) {
        Reward = reward;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
