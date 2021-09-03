package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.app.lostandfound.R;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomEditText;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.pojo.PeopleData;

import java.util.Calendar;

public class LastLocationActivity extends BaseActivity {

    CustomTextView btnNext;
    PeopleData peopleData=null;
    CustomEditText edtState,edtCity,edtArea,edtStreet,edtExtraDetail;
    private int mYear, mMonth, mDay;
    CustomTextView txtLastData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_location);
        if(getIntent().getParcelableExtra("DATA")!=null)
        {
            peopleData=new PeopleData();
            peopleData=getIntent().getParcelableExtra("DATA");
        }
        initView();
    }


    private void dialogDate()
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtLastData.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }



    private void initView()
    {
        txtLastData=(CustomTextView)findViewById(R.id.txtLastData);
        txtLastData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDate();
            }
        });
        edtState=(CustomEditText)findViewById(R.id.edtState);
        edtCity=(CustomEditText)findViewById(R.id.edtCity);
        edtArea=(CustomEditText)findViewById(R.id.edtArea);
        edtStreet=(CustomEditText)findViewById(R.id.edtStreet);
        edtExtraDetail=(CustomEditText)findViewById(R.id.edtExtraDetail);

        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((CustomTextView)findViewById(R.id.header_toolbar)).setText(context.getResources().getString(R.string.lastlocation));


        btnNext=(CustomTextView)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if(validation())
{
    setPeopleData();
    Intent intent=new Intent(context,UploadLostFoundImageActivity.class);
    intent.putExtra("DATA",peopleData);
    startActivity(intent);
}

            }
        });
    }
    public boolean validation()
    {
        if(edtState.getText().toString().isEmpty())
        {
            edtState.setError(context.getResources().getString(R.string.enter_state));
            return false;
        }

        if(edtCity.getText().toString().isEmpty())
        {
            edtCity.setError(context.getResources().getString(R.string.enter_city));
            return false;
        }
        if(edtArea.getText().toString().isEmpty())
        {
            edtArea.setError(context.getResources().getString(R.string.enter_area));
            return false;
        }
        if(edtStreet.getText().toString().isEmpty())
        {
            edtStreet.setError(context.getResources().getString(R.string.enter_street));
            return false;
        }
        if(edtExtraDetail.getText().toString().isEmpty())
        {
            edtExtraDetail.setError(context.getResources().getString(R.string.enter_detail));
            return false;
        }

        return true;
    }



    private void setPeopleData()
    {

        peopleData.setLast_location(
                edtStreet.getText().toString()+" "+
                edtArea.getText().toString()+" , "+
                edtCity.getText().toString()+" , "+
                edtState.getText().toString()

                );
        peopleData.setLattitude("0.0");
        peopleData.setLongitude("0.0");
        peopleData.setLast_date(txtLastData.getText().toString());
        peopleData.setExtra_detial(edtExtraDetail.getText().toString());

    }


}
