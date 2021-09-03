package com.app.lostandfound.base;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;

import com.app.lostandfound.R;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.utility.DataPrefrence;
import com.app.lostandfound.utility.Methods;
import com.app.lostandfound.utility.MyDialog_Message;
import com.app.lostandfound.utility.Utility;
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by Navin Nimade on 14/11/16.
 */

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog mProgressDialog;

//98059937
    public Context context;
    public Activity activity;
    public String profileImg = "";
    public String profileName = "", profileEmail = "", profileContact = "";
    public String accessToken = "", userId = "", deviceId = "2468", is_consultant = "", is_company = "", user_name = "";
    private static final String TAG = "tag";


    public String loginType = "";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        activity = this;
        loginType = DataPrefrence.getPref(context, Constant.LOGIN_TYPE, "");
        accessToken = DataPrefrence.getPref(context, Constant.ACCESS_TOKEN, "");
        userId = DataPrefrence.getPref(context, Constant.USER_ID, "");
        user_name = DataPrefrence.getPref(context, Constant.USER_NAME, "");
        profileContact = DataPrefrence.getPref(context, Constant.MOBILE_NO, "");
        System.out.println("user_id"+userId);

        startTransition();

    }



    @Override
    protected void onResume() {
        super.onResume();


    }

    public void log(String msg) {
        System.out.println(msg);
    }
    public void mlog(String msg) {
        Log.d(TAG,msg);
    }

    public static android.app.Dialog dd;
     static ProgressView progressDialog;

    public void showLoading() {

        System.out.println("baseFrg  start loading");

        if (dd != null) {
            dd.dismiss();
        }
        dd = new android.app.Dialog(context);
        try {
            dd.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dd.setContentView(R.layout.custom_loading);
            progressDialog = (ProgressView) dd.findViewById(R.id.rey_loading);
            progressDialog.start();
            dd.getWindow().setLayout(-1, -2);
            dd.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            dd.setCancelable(false);
            dd.show();

        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    public static void dismiss_loading() {
        try {
            if (dd != null) {
                if (dd.isShowing() || dd != null) {
                    if (progressDialog != null)
                        progressDialog.stop();
                }
                dd.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String  serverResponse(ResponseBody responseBody) throws IOException {
        String msg="";

        String data =responseBody.string();
        try {
            JSONObject object=new JSONObject(data);

            showToast(object.getString("message"));
            msg=object.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }


    public Boolean isInternetConnected() {
        return Methods.isInternetConnected(BaseActivity.this);
    }

    public void showToast(String x) {
        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();
    }

    public void showInternetConnectionToast() {
        Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
    }


    public void startTransition() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void finishTransition() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }


    public void msg(String msg) {
//           System.out.println(msg);
    }


    public int getHightWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }


//    public void showSettingsAlert(final ApiCallback apiCallback) {
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//        // Setting Dialog Title
//        alertDialog.setTitle(context.getResources().getString(R.string.gps_tital));
//
//        // Setting Dialog Message
//        alertDialog.setMessage(context.getResources().getString(R.string.gps_content));
//        // Setting Icon to Dialog
//        //  alertDialog.setIcon(R.drawable.);
//        alertDialog.setCancelable(false);
//        // On pressing Settings button
//        alertDialog.setPositiveButton(context.getResources().getString(R.string.settings), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivityForResult(intent, Constant.LOCATION);
//            }
//        });
//
//        // on pressing cancel button
//        alertDialog.setNegativeButton(context.getResources().getString(R.string.msg_cancel), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//                apiCallback.result("cancel");
//            }
//        });
//
//        // Showing Alert Message
//        alertDialog.show();
//    }


    public boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void showInternetPop(Context context) {
        MyDialog_Message.iPhone(context.getResources().getString(R.string.connection), context);
    }

    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void unAuthorized() {
        clearDataBase();

//        Intent intent = new Intent(context, SplashActivity.class);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
    }


    @Override
    public void finish() {
        super.finish();
        finishTransition();
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        View v = getCurrentFocus();
//
//        if (v != null &&
//                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
//                v instanceof EditText &&
//                !v.getClass().getName().startsWith("android.webkit.")) {
//            int scrcoords[] = new int[2];
//            v.getLocationOnScreen(scrcoords);
//            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
//            float y = ev.getRawY() + v.getTop() - scrcoords[1];
//
//            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
//                Utility.hideKeyboard(this);
//        }
//        return super.dispatchTouchEvent(ev);
//    }


    public void clearDataBase() {

        DataPrefrence.setPref(context, Constant.USER_ID, "");
        DataPrefrence.setPref(context, Constant.EMAILID, "");
        DataPrefrence.setPref(context, Constant.USER_NAME, "");
        DataPrefrence.setPref(context, Constant.MOBILE_NO, "");
        DataPrefrence.setPref(context, Constant.FULLNAME, "");
        DataPrefrence.setPref(context, Constant.COUNTRY_ID, "");
        DataPrefrence.setPref(context, Constant.ACCESS_TOKEN, "");
        DataPrefrence.setPref(context, Constant.LOGIN_FLAG, false);
        DataPrefrence.setPref(context, Constant.LOGIN_TYPE, "");

        DataPrefrence.setPref(context, Constant.LANGUAGE_SELECTED, false);
        DataPrefrence.setPref(context, Constant.CATEGORY_SELECTED, false);


    }


    public void showSettingsAlert(final ApiCallback apiCallback) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle(context.getResources().getString(R.string.gps_tital));

        // Setting Dialog Message
        alertDialog.setMessage(context.getResources().getString(R.string.gps_content));
        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.);
        alertDialog.setCancelable(false);
        // On pressing Settings button
        alertDialog.setPositiveButton(context.getResources().getString(R.string.settings), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, Constant.LOCATION);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton(context.getResources().getString(R.string.msg_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                apiCallback.result(0);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


}
