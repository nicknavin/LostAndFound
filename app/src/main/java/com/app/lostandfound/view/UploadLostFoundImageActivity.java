package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.CategoryAdapter;
import com.app.lostandfound.adapter.CategoryGridAdapter;
import com.app.lostandfound.adapter.LostFoundImageAdapter;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.network.ApiInterface;
import com.app.lostandfound.pojo.PeopleData;
import com.app.lostandfound.utility.Constant;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.fxn.utility.PermUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class UploadLostFoundImageActivity extends BaseActivity {
    RecyclerView recyclerView;
    LostFoundImageAdapter myAdapter;
    Options options;
    ArrayList<String> returnValue = new ArrayList<>();

    PeopleData peopleData;
    CustomTextView btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_lost_found_thing);
        if(getIntent().getParcelableExtra("DATA")!=null)
        {
            peopleData=getIntent().getParcelableExtra("DATA");
        }
        initView();

    }

    private void initView()
    {
        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((CustomTextView)findViewById(R.id.header_toolbar)).setText(context.getResources().getString(R.string.upload_photos));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new LostFoundImageAdapter(this);
        options = Options.init()
                .setRequestCode(100)
                .setCount(4)
                .setFrontfacing(false)
                .setPreSelectedUrls(returnValue)
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                .setPath("/foundlost/new")
        ;
        recyclerView.setAdapter(myAdapter);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                options.setPreSelectedUrls(returnValue);

                Pix.start(UploadLostFoundImageActivity.this, options);
            }
        });



        btnNext=(CustomTextView)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               postData();
            }
        });


    }





    public void postData() {

        if (isInternetConnected()) {
            showLoading();
            RequestBody user_id=RequestBody.create( MediaType.parse("text/plain"),userId);
            RequestBody category_id=RequestBody.create( MediaType.parse("text/plain"),peopleData.getCategory_id());
            RequestBody subcategory_id=RequestBody.create( MediaType.parse("text/plain"),peopleData.getSubcategory_id());
            RequestBody first_name=RequestBody.create( MediaType.parse("text/plain"),peopleData.getFirst_name());
            RequestBody last_name=RequestBody.create( MediaType.parse("text/plain"),peopleData.getLast_date());
            RequestBody Age=RequestBody.create( MediaType.parse("text/plain"),peopleData.getAge());
            RequestBody Gender=RequestBody.create( MediaType.parse("text/plain"),peopleData.getGender());
            RequestBody Height=RequestBody.create( MediaType.parse("text/plain"),peopleData.getHeight());
            RequestBody Last_location=RequestBody.create( MediaType.parse("text/plain"),peopleData.getLast_location());

            RequestBody Last_date=RequestBody.create( MediaType.parse("text/plain"),peopleData.getLast_date());

            RequestBody Extra_detial=RequestBody.create( MediaType.parse("text/plain"),peopleData.getExtra_detial());
            RequestBody color=RequestBody.create( MediaType.parse("text/plain"),peopleData.getColor());
            RequestBody Reward=RequestBody.create( MediaType.parse("text/plain"),peopleData.getReward());
            RequestBody lattitude=RequestBody.create( MediaType.parse("text/plain"),peopleData.getLattitude());
            RequestBody longitude=RequestBody.create( MediaType.parse("text/plain"),peopleData.getLongitude());
            RequestBody post_type=RequestBody.create( MediaType.parse("text/plain"),peopleData.getPost_type());
            RequestBody Brand=RequestBody.create( MediaType.parse("text/plain"),peopleData.getBrand());
            RequestBody model=RequestBody.create( MediaType.parse("text/plain"),peopleData.getModel());
            RequestBody title=RequestBody.create( MediaType.parse("text/plain"),peopleData.getTitle());
            RequestBody breed=RequestBody.create( MediaType.parse("text/plain"),peopleData.getBreed());





            MultipartBody.Part img1=null,img2= null,img3=null,img4=null;
            for(int i=0;i<returnValue.size();i++) {
               File file=new File(returnValue.get(i));
                RequestBody requestfile = RequestBody.create(MediaType.parse("image/*"), file);
              if(i==0) {
                  img1 = MultipartBody.Part.createFormData("Images[]", file.getName(), requestfile);
              } if(i==1) {
                  img1 = MultipartBody.Part.createFormData("Images[]", file.getName(), requestfile);
              } if(i==2) {
                  img3 = MultipartBody.Part.createFormData("Images[]", file.getName(), requestfile);
              } if(i==3) {
                  img4 = MultipartBody.Part.createFormData("Images[]", file.getName(), requestfile);
              }

            }




            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiService.callPost(user_id,category_id,subcategory_id,first_name,last_name,Age,title,breed,Gender,Height,Last_location,Last_date,Extra_detial,color,Reward,lattitude,longitude,post_type,Brand,model,img1,img2,img3,img4);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                {
                    dismiss_loading();
                    ResponseBody responseBody=response.body();

                    if(responseBody!=null)
                    {
                        try {

                            String data= responseBody.string();
                            JSONObject jsonObject=new JSONObject(data);

                            if(jsonObject.getString("status").equals("1"))
                            {

                                System.out.println("data" + data);
                                Intent intent=new Intent(context,HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            showToast(jsonObject.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dismiss_loading();
                    showToast("Please try again");
                }
            });



        } else {
            showInternetConnectionToast();
        }
    }



    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = context.getFilesDir();
     File   imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }

return imageFile;

    }



















    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                    myAdapter.addImage(returnValue);
                }
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(UploadLostFoundImageActivity.this, options);
                } else {
                    Toast.makeText(UploadLostFoundImageActivity.this, "Approve permissions to open LostFound ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }




}
