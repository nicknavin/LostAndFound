package com.app.lostandfound.view.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.lostandfound.R;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.utility.DataPrefrence;
import com.app.lostandfound.utility.Utility;
import com.app.lostandfound.view.LoginActivity;
import com.app.lostandfound.view.ProfileEditActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CustomTextView txt_user_name,txt_user_mob,txt_user_email;
    LinearLayout layLogin,layLogout,layProfile;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void initView(View view)
    {
      txt_user_name=  (CustomTextView) view.findViewById(R.id.txt_user_name);
      txt_user_mob=  (CustomTextView) view.findViewById(R.id.txt_user_mob);
      txt_user_email=  (CustomTextView) view.findViewById(R.id.txt_user_email);
        layProfile= (LinearLayout) view.findViewById(R.id.layProfile);
        layProfile.setOnClickListener(this);
       layLogin= (LinearLayout) view.findViewById(R.id.layLogin);
       layLogin.setOnClickListener(this);
       layLogout= (LinearLayout) view.findViewById(R.id.layLogout);
        layLogout.setOnClickListener(this);






    }


    private void pageRefresh()
    {
        if( DataPrefrence.getPref(getContext(), Constant.LOGIN_FLAG,false))
        {
            layProfile.setVisibility(View.VISIBLE);
            txt_user_name.setText(DataPrefrence.getPref(getContext(),Constant.USER_NAME,""));
            txt_user_mob.setText(DataPrefrence.getPref(getContext(),Constant.MOBILE_NO,""));
            txt_user_email.setText(DataPrefrence.getPref(getContext(),Constant.EMAILID,""));
            layLogin.setVisibility(View.GONE);
            layLogout.setVisibility(View.VISIBLE);
        }
        else
        {
            layProfile.setVisibility(View.GONE);
            layLogin.setVisibility(View.VISIBLE);
            layLogout.setVisibility(View.GONE);

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        pageRefresh();
    }

    @Override
    public void onClick(View view)
    {
        Intent intent;
        switch (view.getId())
        {
            case R.id.layProfile:
                intent=new Intent(getContext(), ProfileEditActivity.class);
                startActivity(intent);
                break;
                case R.id.layLogin:
                intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
                case R.id.layLogout:
logoutDialog();
                break;
        }
    }


    public void logoutDialog() {

        final Dialog dd = new Dialog(getContext());
        try {
            dd.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dd.setContentView(R.layout.dialog_logout_confirm);
            dd.getWindow().setLayout(-1, -2);
            dd.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            ((CustomTextView) dd.findViewById(R.id.tvConfirmOk)).setText("Logout");
            ((CustomTextView) dd.findViewById(R.id.tvConfirmOk)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.clearDataBase(getContext());
                    pageRefresh();
                    dd.dismiss();
                }
            });

            ((CustomTextView) dd.findViewById(R.id.tvConfirmCancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dd.dismiss();
                }
            });
            dd.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Log.d(TAG, "Exception: " + e.getMessage());
        }
    }

}
