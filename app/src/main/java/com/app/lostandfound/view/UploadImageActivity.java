package com.app.lostandfound.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.lostandfound.R;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.utility.PermissionsUtils;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import androidx.core.content.FileProvider;

public class UploadImageActivity extends BaseActivity implements View.OnClickListener{
    private Uri mCameraImageUri;
    public static final int REQUEST_CODE_CAMERA = 10;
    Context context;
    Button btn_take_picture, btn_upload_picture;
    ImageView imgPreview;
    String PicturePath = "", captureImg = "";
    String imgType = "", activity_name = "";

    byte[] ByteArray;
    String imgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        if (getIntent().getStringExtra("type") != null) {
            imgType = getIntent().getStringExtra("type");
        }
        if (getIntent().getStringExtra("activity") != null) {
            activity_name = getIntent().getStringExtra("activity");
        }
        context = this;

        initView();
    }

    public void initView() {
        ((ImageView) findViewById(R.id.imgback)).setOnClickListener(this);
        ((TextView) findViewById(R.id.header_toolbar)).setText(activity_name);
//        btn_upload_picture = (Button) findViewById(R.id.btn_upload_picture);
//        btn_upload_picture.setOnClickListener(this);
//        btn_take_picture = (Button) findViewById(R.id.btn_take_picture);
//        btn_take_picture.setOnClickListener(this);
//        btn_take_picture.setVisibility(View.GONE);
//        btn_upload_picture.setVisibility(View.GONE);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgback:
                finish();
                break;


        }
    }


    public void TakePic() {
        try {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "zestcredi.png");
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mCameraImageUri = Uri.fromFile(f);
            } else {
                mCameraImageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", f);
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraImageUri);
            cameraIntent.putExtra("return-data", true);
            startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void setGalleryPermission() {
        if (Build.VERSION.SDK_INT >= PermissionsUtils.SDK_INT_MARSHMALLOW) {
            if (!PermissionsUtils.getInstance(context).isPermissionGranted(context, Manifest.permission.READ_EXTERNAL_STORAGE, "Read External Storage")) {
                return;
            }
        }


    }
    public void setCameraPermission() {
        if (Build.VERSION.SDK_INT >= PermissionsUtils.SDK_INT_MARSHMALLOW) {
            if (!PermissionsUtils.getInstance(context).isPermissionGranted(context, Manifest.permission.CAMERA, "Camera")) {
                return;
            }

            if (Build.VERSION.SDK_INT >= PermissionsUtils.SDK_INT_MARSHMALLOW) {
                if (!PermissionsUtils.getInstance(context).isPermissionGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, "Write External Storage")) {
                    return;
                }
            }
        }
        TakePic();
    }




    private String base64String(byte[] b) {
        String imgString = Base64.encodeToString(b, Base64.NO_WRAP);
        System.out.println("imgString " + imgString);
        return "data:image/png;base64," + imgString;
    }



}
