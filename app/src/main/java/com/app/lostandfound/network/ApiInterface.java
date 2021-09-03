package com.app.lostandfound.network;


import com.app.lostandfound.pojo.CategoryData;
import com.app.lostandfound.pojo.CategoryResponse;
import com.app.lostandfound.pojo.LostAllPostResponse;
import com.app.lostandfound.pojo.RegisterResponse;
import com.app.lostandfound.pojo.SubCategoryData;
import com.app.lostandfound.pojo.SubCategoryResponse;
import com.app.lostandfound.pojo.UserDetailResponse;
import com.app.lostandfound.pojo.UserDetailResult;
import com.app.lostandfound.pojo.UserDetails;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    String key="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ";

    @FormUrlEncoded
    @Headers("Authorization:"+key)
    @POST("login")
    public Call<UserDetailResponse> callLoginRequest(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("forget_password")
    public Call<UserDetails> callForgetPassword(@Field("email") String email);

    @FormUrlEncoded
    @Headers("Authorization:"+key)
    @POST("signup")
    public Call<RegisterResponse> callRegisterRequest
           (@Field("email") String email, @Field("password") String password,
            @Field("phone") String phone, @Field("f_name") String f_name,
            @Field("l_name") String l_name);

    @Headers("Authorization:"+key)
    @GET("category_list")
    public Call<CategoryResponse> getCategory();
    @GET("category_list")
    public Call<ResponseBody> getCategory1();
    @Headers("Authorization:"+key)
    @GET("subcategory_list/{id}")
    public Call<SubCategoryResponse> getSubCategory(@Path("id") String id);

    @Headers("Authorization:"+key)
    @GET("get_lost_all_post")
    public Call<LostAllPostResponse> getAllLostPost(@Query("pageno") String pageno);

    @Headers("Authorization:"+key)
    @GET("get_found_all_post")
    public Call<LostAllPostResponse> getAllFoundPost(@Query("pageno") String pageno);

    @Headers("Authorization:"+key)
    @GET("get_all_users_post")
    public Call<LostAllPostResponse> getAllPost(@Query("pageno") String pageno);




//    public Call<LostAllPostResponse> getAllFoundPost(@Query("pageno") String pageno);?user_id=32&pageno=1


@Headers("Authorization:"+key)
    @GET("get_lost_user_post")
    public Call<LostAllPostResponse> GetLostUserPost(@Query("user_id") String user_id,@Query("pageno") String pageno);

@Headers("Authorization:"+key)
    @GET("get_found_user_post")
    public Call<LostAllPostResponse> GetFoundUserPost(@Query("user_id") String user_id,@Query("pageno") String pageno);



    @Headers("Authorization:"+key)
    @GET("get_lost_liked_post")
    public Call<LostAllPostResponse> GetLostLikedPost(@Query("user_id") String user_id,@Query("pageno") String pageno);


    @Headers("Authorization:"+key)
    @GET("get_found_liked_post")
    public Call<LostAllPostResponse> GetFoundLikedPost(@Query("user_id") String user_id,@Query("pageno") String pageno);

    @Headers("Authorization:"+key)
    @GET("get_lost_post_by_category_subcategory")
    public Call<LostAllPostResponse> GetLostPostByCategory(@Query("category_id") String category_id,@Query("subcategory_id") String subcategory_id,@Query("pageno") String pageno);

    @Headers("Authorization:"+key)
    @GET("get_lost_post_by_category_subcategory")
    public Call<LostAllPostResponse> GetFoundPostByCategory(@Query("category_id") String category_id,@Query("subcategory_id") String subcategory_id,@Query("pageno") String pageno);
//http://imrankhan10776.com/projects/lostfound/api/get_found_post_by_category_subcategory?category_id=1&subcategory_id=1&pageno=1


//    @Headers("Authorization:"+key)
//    @GET("user_profile")
//    public Call<ResponseBody> getUserProfile(@Query("user_id") String user_id);

    @Headers("Authorization:"+key)
    @GET("user_profile/{id}")
    public Call<UserDetailResult> getUserProfile(@Path("id") String id);








    @Headers("Authorization:"+key)
    @Multipart
    @POST("add_post")
    public Call<ResponseBody> callPost
            (@Part("user_id") RequestBody user_id,
             @Part("category_id") RequestBody category_id,
             @Part("subcategory_id") RequestBody subcategory_id,
             @Part("first_name") RequestBody first_name,
             @Part("last_name") RequestBody last_name,
             @Part("Age") RequestBody Age,
             @Part("title") RequestBody title,
             @Part("breed") RequestBody breed,
             @Part("Gender") RequestBody Gender,
             @Part("Height") RequestBody Height,
             @Part("Last_location") RequestBody Last_location,
             @Part("Last_date") RequestBody Last_date,
             @Part("Extra_detial") RequestBody Extra_detial,
             @Part("color") RequestBody color,
             @Part("Reward") RequestBody Reward,
             @Part("lattitude") RequestBody lattitude,
             @Part("longitude") RequestBody longitude,
             @Part("post_type") RequestBody post_typ,
             @Part("Brand") RequestBody Brand,
             @Part("model") RequestBody model,
             @Part MultipartBody.Part postImage1,
             @Part MultipartBody.Part postImage2,
             @Part MultipartBody.Part postImage3,
             @Part MultipartBody.Part postImage4);

//    http://imrankhan10776.com/projects/lostfound/api/like_post
@Headers("Authorization:"+key)
    @FormUrlEncoded
    @POST("like_post")
    public Call<ResponseBody> callLikePost(@Field("user_id") String user_id, @Field("post_id") String post_id);
//http://imrankhan10776.com/projects/lostfound/api/unlike_post
    @Headers("Authorization:"+key)
    @FormUrlEncoded
    @POST("unlike_post")
    public Call<ResponseBody> callUnlikePost(@Field("user_id") String user_id, @Field("post_id") String post_id);


    @Headers("Authorization:"+key)
    @Multipart
    @POST("edit_profile")
    public Call<ResponseBody> callEditProfile
            (@Part("user_id") RequestBody user_id,
             @Part("f_name") RequestBody f_name,
             @Part("l_name") RequestBody l_name,
             @Part("phone") RequestBody phone,
             @Part MultipartBody.Part postImage1);



}
