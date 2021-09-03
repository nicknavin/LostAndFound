package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.lostandfound.R;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomEditText;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.network.ApiInterface;
import com.app.lostandfound.pojo.ReqLogin;
import com.app.lostandfound.pojo.UserDetailResponse;
import com.app.lostandfound.pojo.UserDetails;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.utility.DataPrefrence;
import com.app.lostandfound.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    CustomEditText edtEmail, edtPassword;
    CustomTextView btnLogin, btnForgot, btnRegister;
String email="",password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView() {
        ((CustomTextView) findViewById(R.id.header_toolbar)).setText(context.getResources().getString(R.string.login));
        edtEmail = (CustomEditText) findViewById(R.id.edtEmail);
        edtPassword = (CustomEditText) findViewById(R.id.edtPassword);
        edtEmail.setText("navin@gmail.com");
        edtPassword.setText("123456");
        ((CustomTextView) findViewById(R.id.btnLogin)).setOnClickListener(this);
        ((CustomTextView) findViewById(R.id.btnRegister)).setOnClickListener(this);
        ((CustomTextView) findViewById(R.id.btnForgot)).setOnClickListener(this);


        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(this);

    }

    private boolean validation() {
        if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError(context.getResources().getString(R.string.error_email));
            return false;
        }
        if (!Utility.emailValidator(edtEmail.getText().toString())) {
            edtEmail.setError(context.getResources().getString(R.string.error_email_invalid));
            return false;
        }
        if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError(context.getResources().getString(R.string.error_password));
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (validation())
                {
callLogin();
                }
                break;
            case R.id.btnRegister:

                startActivity(new Intent(context,RegisterActivity.class));

                break;
            case R.id.btnForgot:

                break;

            case R.id.imgBack:
                finish();
                break;
        }
    }



    private void callLogin()
    {
        if (isConnectingToInternet(context)) {
           showLoading();
             ReqLogin reqLogin=new ReqLogin();
            String email=edtEmail.getText().toString();
            String password=edtPassword.getText().toString();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UserDetailResponse> call= apiInterface.callLoginRequest(email,password);
            call.enqueue(new Callback<UserDetailResponse>() {
                @Override
                public void onResponse(Call<UserDetailResponse> call, Response<UserDetailResponse> response)
                {
                   dismiss_loading();
                    if(response.body()!=null) {
                        UserDetailResponse userDetailResponse = response.body();
                        if (userDetailResponse.getStatus().equals("1"))
                        {
                            UserDetails userDetails=userDetailResponse.getUserDetails();
                            DataPrefrence.setPref(context,Constant.USER_ID,userDetails.getId());
                            DataPrefrence.setPref(context,Constant.USER_NAME,userDetails.getUsername());
                            DataPrefrence.setPref(context,Constant.EMAILID,userDetails.getEmail());
                            DataPrefrence.setPref(context,Constant.MOBILE_NO,userDetails.getPhone());
                            DataPrefrence.setPref(context,Constant.LOGIN_FLAG,true);
                            finish();
                        }
                        else
                        {

                        }
                    }
                    else
                    {
                        try {
                            ResponseBody responseBody=response.errorBody();
                            String result= responseBody.string();
                            System.out.println("result : "+result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserDetailResponse> call, Throwable t) {
                    dismiss_loading();
                }
            });

        }
        else
        {
            showInternetConnectionToast();
        }
    }




}
