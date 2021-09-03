package com.app.lostandfound.viewmodels;


import com.app.lostandfound.BR;
import com.app.lostandfound.pojo.UserDetails;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class LoginViewModel extends BaseObservable {
    public UserDetails user;


    private String successMessage = "Login was successful";
    private String errorMessage = "Please insert name";

    @Bindable
    public String toastMessage = null;


    public String getToastMessage() {
        return toastMessage;
    }


    private void setToastMessage(String toastMessage) {

        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

    public LoginViewModel(UserDetails details)
    {
        this.user = details;
    }

    public void afterNameTextChanged(CharSequence s)
    {
        user.setF_name(s.toString());
    }



    public void onUpdateClicked() {
        if (user.isInputDataValid())
            setToastMessage(successMessage);
        else
            setToastMessage(errorMessage);
    }
}