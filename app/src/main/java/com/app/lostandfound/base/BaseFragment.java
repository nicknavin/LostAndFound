package com.app.lostandfound.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.app.lostandfound.R;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.utility.DataPrefrence;
import com.app.lostandfound.utility.Methods;
import com.app.lostandfound.utility.MyDialog_Message;
import com.rey.material.widget.ProgressView;


public class BaseFragment extends Fragment {

    public String loginType = "", accessToken = "", userId = "",lang="en",country_cd="",is_consultant ="",is_company="",user_name="";;
    public BaseActivity baseActivity;
   public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();

        loginType = DataPrefrence.getPref(getContext(), Constant.LOGIN_TYPE, "");
        accessToken = DataPrefrence.getPref(getContext(), Constant.ACCESS_TOKEN, "");
        userId = DataPrefrence.getPref(getContext(), Constant.USER_ID, "");
        country_cd=DataPrefrence.getPref(getContext(),Constant.COUNTRY_ID,"");
        baseActivity = (BaseActivity) this.getActivity();
        }

    public void showInternetPop(Context context) {
        MyDialog_Message.iPhone(context.getResources().getString(R.string.connection), context);
    }

    public void showToast(String x) {
        Toast.makeText(getContext(), x, Toast.LENGTH_SHORT).show();
    }

    public void showInternetConnectionToast() {
        Toast.makeText(getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
    }

    public void unAuthorized(Context context, Activity activity) {
//        clearDataBase(context);
//        Intent intent = new Intent(context, SplashActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        activity.finish();
    }

    public void clearDataBase(Context context) {
//        DataPrefrence.setPref(context, Constant.EMAILID, "");
//        DataPrefrence.setPref(context, Constant.PASSWORD, "");
//        DataPrefrence.setPref(context, Constant.CONTACT_NO, "");
//        DataPrefrence.setPref(context, Constant.PROFILE_IMAGE, "");
//        DataPrefrence.setPref(context, Constant.USER_ID, "");
//        DataPrefrence.setPref(context, Constant.ACCESS_TOKEN, "");
//        DataPrefrence.setPref(context, Constant.VERIFICATION_CODE, "");
//        DataPrefrence.setPref(context, Constant.LOGIN_FLAG, false);
//        DataPrefrence.setPref(context, Constant.VERIFICATION_FLAG, false);

    }

    public Boolean isInternetConnected() {
        return Methods.isInternetConnected(getActivity());
    }
    public android.app.Dialog dd;
    ProgressView progressDialog;

    public void showLoading() {

        System.out.println("baseFrg  start loading");

        if (dd != null) {
            dd.dismiss();
        }
        dd = new android.app.Dialog(baseActivity);
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

    public void dismiss_loading() {
        try {
            if (dd.isShowing() || dd != null) {
                if (progressDialog != null)
                    progressDialog.stop();
            }
            dd.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
