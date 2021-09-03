package com.app.lostandfound.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Images implements Parcelable
{
    @SerializedName("image_id")
   private String image_id;
    @SerializedName("post_id")
   private String post_id;
    @SerializedName("post_image")
   private String post_image;
    @SerializedName("status")
   private String status;

    protected Images(Parcel in) {
        image_id = in.readString();
        post_id = in.readString();
        post_image = in.readString();
        status = in.readString();
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image_id);
        parcel.writeString(post_id);
        parcel.writeString(post_image);
        parcel.writeString(status);
    }
}
