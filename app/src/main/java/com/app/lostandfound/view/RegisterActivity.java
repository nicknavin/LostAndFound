package com.app.lostandfound.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.lostandfound.Main2Activity;
import com.app.lostandfound.R;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomEditText;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.network.ApiInterface;
import com.app.lostandfound.pojo.RegisterResponse;
import com.app.lostandfound.pojo.RegisterResponse;
import com.app.lostandfound.pojo.UserDetails;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.utility.DataPrefrence;
import com.app.lostandfound.utility.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;

public class RegisterActivity extends BaseActivity {

    CustomEditText edtEmail, edtPassword,edtFirstName,edtLastName,edtMobile,edtConfirmPassword;
    FirebaseAuth auth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth=FirebaseAuth.getInstance();
        initView();
    }

    private void initView()
    {
        ((CustomTextView)findViewById(R.id.header_toolbar)).setText(getResources().getString(R.string.register));
        edtFirstName = (CustomEditText) findViewById(R.id.edtFirstName);
        edtLastName = (CustomEditText) findViewById(R.id.edtLastName);
        edtEmail = (CustomEditText) findViewById(R.id.edtEmail);
        edtPassword = (CustomEditText) findViewById(R.id.edtPassword);
        edtMobile = (CustomEditText) findViewById(R.id.edtMobile);
        edtConfirmPassword = (CustomEditText) findViewById(R.id.edtConfirmPassword);
        
        ((CustomTextView)findViewById(R.id.btnRegister)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation())
                {
                    callRegister();
                }
            }
        });
        
        
        

    }


String fname,lname,email,password,mobile;
    private boolean validation() {

        fname=edtFirstName.getText().toString();
        lname=edtLastName.getText().toString();
        email=edtEmail.getText().toString();
        password=edtPassword.getText().toString();
        mobile=edtMobile.getText().toString();

        if (edtFirstName.getText().toString().isEmpty()) {
            edtFirstName.setError(context.getResources().getString(R.string.error_name));
            return false;
        }
        if (edtFirstName.getText().length()<3) {
            edtFirstName.setError(context.getResources().getString(R.string.valid_name));
            return false;
        }  if (edtLastName.getText().toString().isEmpty()) {
            edtLastName.setError(context.getResources().getString(R.string.error_name));
            return false;
        }
        if (edtLastName.getText().length()<3) {
            edtLastName.setError(context.getResources().getString(R.string.valid_name));
            return false;
        }
        if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError(context.getResources().getString(R.string.error_email));
            return false;
        }
        if (!Utility.emailValidator(edtEmail.getText().toString())) {
            edtEmail.setError(context.getResources().getString(R.string.error_email_invalid));
            return false;
        }


        if (edtMobile.getText().toString().isEmpty()) {
            edtMobile.setError(context.getResources().getString(R.string.error_mobile));
            return false;
        }
        if (edtMobile.getText().length()<10) {
            edtMobile.setError(context.getResources().getString(R.string.valid_mobile));
            return false;
        }
        
        if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError(context.getResources().getString(R.string.error_password));
            return false;
        }
        if (edtPassword.getText().length()<6) {
            edtPassword.setError(context.getResources().getString(R.string.valid_password));
            return false;
        }
        if (edtConfirmPassword.getText().toString().isEmpty()) {
            edtConfirmPassword.setError(context.getResources().getString(R.string.error_cnfm_password));
            return false;
        }
        if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setError(context.getResources().getString(R.string.msg_pwd));
            return false;
        }
        return true;
    }







    private void callRegister()
    {
        if (isConnectingToInternet(context)) {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RegisterResponse> call= apiInterface.callRegisterRequest(  edtEmail.getText().toString(),
                                                                            edtPassword.getText().toString(),
                                                                            edtMobile.getText().toString(),
                                                                            edtFirstName.getText().toString(),
                                                                            edtLastName.getText().toString()
                                                                          );
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response)
                {
                    dismiss_loading();
                    if(response.body()!=null) {
                        RegisterResponse registerResponse = response.body();
                        showToast(registerResponse.getMessage());
                        if (registerResponse.getStatus().equals("1"))
                        {
                            registerFireBase();

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
                public void onFailure(Call<RegisterResponse> call, Throwable t) {

                }
            });

        }
        else
        {
            showInternetConnectionToast();
        }
    }

    private void registerFireBase()
    {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            assert firebaseUser!=null;
                            String userid=firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("fristName",fname);
                            hashMap.put("lastName",lname);
                            hashMap.put("email",email);
                            hashMap.put("mobile",mobile);
                            hashMap.put("password",password);
                            hashMap.put("status","offline");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {

                                        finish();
                                    }
                                }
                            });

                        } else{
                            Toast.makeText(RegisterActivity.this, "You can't register woth this email or password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }



}
