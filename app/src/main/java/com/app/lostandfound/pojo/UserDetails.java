package com.app.lostandfound.pojo;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.gson.annotations.SerializedName;

public class UserDetails
{
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("f_name")
    private String f_name;
    @SerializedName("l_name")
    private String l_name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("username")
    private String username;
    @SerializedName("profile")
    private String profile;


    public UserDetails(String email, String f_name, String l_name, String phone) {
        this.email = email;
        this.f_name = f_name;
        this.l_name = l_name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    //    public boolean isInputDataValid() {
//        return !TextUtils.isEmpty(getEmail()) && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches() && getPassword().length() > 5;
//    }
//

    public boolean isInputDataValid() {
        return !TextUtils.isEmpty(getF_name());
    }



}
