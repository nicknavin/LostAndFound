package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.lostandfound.R;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CircleImageView;
import com.app.lostandfound.costomview.CustomEditText;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.databinding.ActivityProfileUpdateBinding;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.network.ApiInterface;
import com.app.lostandfound.pojo.LostAllPost;
import com.app.lostandfound.pojo.LostAllPostResponse;
import com.app.lostandfound.pojo.UserDetailResponse;
import com.app.lostandfound.pojo.UserDetailResult;
import com.app.lostandfound.pojo.UserDetails;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.utility.ConvetBitmap;
import com.app.lostandfound.utility.DataPrefrence;
import com.app.lostandfound.utility.PermissionsUtils;
import com.app.lostandfound.utility.Utility;
import com.app.lostandfound.viewmodels.LoginViewModel;
import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class ProfileEditActivity extends BaseActivity implements View.OnClickListener
{

    private ActivityProfileUpdateBinding binding;
    UserDetails details;
    private Uri mCameraImageUri, mImageCaptureUri;
    byte[] ByteArray;
    private Dialog dialogSelect;
    String patientProfile = "", PicturePath = "";
    public boolean isForCamera = false;
    CircleImageView img_profile;
    File fileImage=null;
    private CustomEditText edt_fname,edt_lname,edt_email,edt_mobile;
    private CustomTextView btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_update);
        img_profile=(CircleImageView)findViewById(R.id.img_profile);
        img_profile.setOnClickListener(this);
        edt_fname=findViewById(R.id.edt_fname);
        edt_lname=findViewById(R.id.edt_lname);
        edt_mobile=findViewById(R.id.edt_mobile);
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

       //renderProfile();
       getUserProfile();
    }

    private void renderProfile()
    {
        details=new UserDetails(DataPrefrence.getPref(context,Constant.EMAILID,""),
                DataPrefrence.getPref(context, Constant.USER_NAME,""),
                "",
                DataPrefrence.getPref(context,Constant.MOBILE_NO,""));
        binding.setViewModel(new LoginViewModel(details));

    }
    public void getUserProfile()
    {
        if (isInternetConnected()) {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UserDetailResult> call = apiInterface.getUserProfile(userId);
           call.enqueue(new Callback<UserDetailResult>() {
               @Override
               public void onResponse(Call<UserDetailResult> call, Response<UserDetailResult> response)
               {
                   dismiss_loading();
                   UserDetailResult detailResponse=response.body();
                   UserDetails details =detailResponse.getUserDetails();
//                   details=new UserDetails(DataPrefrence.getPref(context,Constant.EMAILID,""),
//                           DataPrefrence.getPref(context, Constant.USER_NAME,""),
//                           "",
//                           DataPrefrence.getPref(context,Constant.MOBILE_NO,""));

                   DataPrefrence.setPref(context,Constant.EMAILID,details.getEmail());
                   DataPrefrence.setPref(context, Constant.USER_NAME,details.getUsername());
                   binding.setViewModel(new LoginViewModel(details));
                   String url =  details.getProfile();
                   Glide.with(context) //1
                           .load(url)
                           .placeholder(R.mipmap.place_holder)
                           .error(R.mipmap.place_holder)
                           .into(img_profile);
                  log(details.toString());

               }

               @Override
               public void onFailure(Call<UserDetailResult> call, Throwable t) {
                   dismiss_loading();
               }
           });
        }
        else
        {
            showInternetConnectionToast();
        }

    }


    public void editProfile() {

        if (isInternetConnected()) {
            showLoading();
            RequestBody user_id=RequestBody.create( MediaType.parse("text/plain"),userId);
            RequestBody first_name=RequestBody.create( MediaType.parse("text/plain"),edt_fname.getText().toString());
            RequestBody last_name=RequestBody.create( MediaType.parse("text/plain"),edt_lname.getText().toString());
            RequestBody phone=RequestBody.create( MediaType.parse("text/plain"),edt_mobile.getText().toString());

            MultipartBody.Part img1=null;

               if(fileImage!=null) {
                   RequestBody requestfile = RequestBody.create(MediaType.parse("image/*"), fileImage);
                   img1 = MultipartBody.Part.createFormData("profile", fileImage.getName(), requestfile);
               }






            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiService.callEditProfile(user_id,first_name,last_name,phone,img1);

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




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_CODE_CAMERA:

                    CropImage.activity(mCameraImageUri)
                            .setAspectRatio(1, 1)
                            .setFixAspectRatio(true)
                            .start(this);

                    break;

                case Constant.REQUEST_CODE_ALBUM:
                    try {
                        mImageCaptureUri = data.getData();
                        CropImage.activity(mImageCaptureUri)
                                .setAspectRatio(1, 1)
                                .setFixAspectRatio(true)
                                .start(this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        Uri resultUri = result.getUri();
                        try {
                            ByteArray = null;
//                            Bitmap bm = BitmapFactory.decodeFile(PicturePath);
                            Bitmap bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), resultUri);

                            bm = ConvetBitmap.Mytransform(bm);
                            bm = Utility.rotateImage(bm, new File(PicturePath));

                            img_profile.setImageBitmap(bm);
                            ByteArrayOutputStream datasecond = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 50, datasecond);
                            ByteArray = datasecond.toByteArray();
                            patientProfile = base64String(datasecond.toByteArray());
                            //imgProfile.setImageBitmap(bm);
                            fileImage = Utility.getImageFile(context,bm);
                            if(fileImage.exists()){

                                Bitmap myBitmap = BitmapFactory.decodeFile(fileImage.getAbsolutePath());




                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                    break;


                default:
                    break;


            }
        }

    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsUtils.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Log.d("Checking", "permissions: " + Arrays.asList(permissions) + ", grantResults:" + Arrays.asList(grantResults));
                if (PermissionsUtils.getInstance(context).areAllPermissionsGranted(grantResults)) {
                    if (isForCamera)
                        TakePic();
                    else
                        Gallery();
                } else {
                    checkIfPermissionsGranted();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private String base64String(byte[] b) {
        String imgString = Base64.encodeToString(b, Base64.NO_WRAP);
        System.out.println("imgString " + imgString);
        return imgString;
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

    public void setGalleryPermission() {
        if (Build.VERSION.SDK_INT >= PermissionsUtils.SDK_INT_MARSHMALLOW) {
            if (!PermissionsUtils.getInstance(context).isPermissionGranted(context, Manifest.permission.READ_EXTERNAL_STORAGE, "Read External Storage")) {
                return;
            }
        }

        Gallery();
    }

    public void dialog() {
        dialogSelect = new Dialog(context, R.style.MaterialDialogSheet);
        dialogSelect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSelect.setContentView(R.layout.dialog_g);
        dialogSelect.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogSelect.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogSelect.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        int i = size.x;
        ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_photo)).setTranslationX(-i);
        ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_gallery)).setTranslationX(-i);
        ((CustomTextView) dialogSelect.findViewById(R.id.txtCancel)).setTranslationX(-i);
        ((View) dialogSelect.findViewById(R.id.view1)).setTranslationX(-i);
        ((View) dialogSelect.findViewById(R.id.view2)).setTranslationX(-i);
        ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_photo)).animate().translationX(0).setDuration(500).setStartDelay(400);
        ((View) dialogSelect.findViewById(R.id.view1)).animate().translationX(0).setDuration(500).setStartDelay(500);
        ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_gallery)).animate().translationX(0).setDuration(500).setStartDelay(600);
        ((View) dialogSelect.findViewById(R.id.view2)).animate().translationX(0).setDuration(500).setStartDelay(700);
        ((CustomTextView) dialogSelect.findViewById(R.id.txtCancel)).animate().translationX(0).setDuration(500).setStartDelay(800);
        ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_photo)).setOnClickListener(this);
        ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_gallery)).setOnClickListener(this);
        ((CustomTextView) dialogSelect.findViewById(R.id.txtCancel)).setOnClickListener(this);
        dialogSelect.show();
    }

    public void TakePic() {

        try {
            isForCamera = true;
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(getExternalCacheDir()+ "/lawyer.png");

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mCameraImageUri = Uri.fromFile(f);
            } else {
                mCameraImageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", f);
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraImageUri);
            cameraIntent.putExtra("return-data", true);
            startActivityForResult(cameraIntent, Constant.REQUEST_CODE_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //REQUEST_CODE_ALBUM
    public void Gallery() {

        if (Build.VERSION.SDK_INT < 19) {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, Constant.REQUEST_CODE_ALBUM);
        } else {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, Constant.REQUEST_CODE_ALBUM);
        }
    }

    private void goToSettings() {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myAppSettings);
    }

    public void checkIfPermissionsGranted() {
        AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(getString(R.string.permission));
        alertDialogBuilder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                goToSettings();
            }
        });


        alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.white));
        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_profile:
                dialog();
                break;

            case R.id.txtTake_photo:
                setCameraPermission();
                isForCamera = true;
                if (dialogSelect != null && dialogSelect.isShowing())
                    dialogSelect.dismiss();
                break;

            case R.id.txtTake_gallery:
                setGalleryPermission();
                isForCamera = false;
                if (dialogSelect != null && dialogSelect.isShowing())
                    dialogSelect.dismiss();
                break;

            case R.id.txtCancel:
                if (dialogSelect != null && dialogSelect.isShowing())
                    dialogSelect.dismiss();
                break;
            case R.id.btnSave:
                if(isInternetConnected()) {
                    editProfile();
                }
                else
                {
                    showInternetConnectionToast();
                }
                break;
        }
    }
}
