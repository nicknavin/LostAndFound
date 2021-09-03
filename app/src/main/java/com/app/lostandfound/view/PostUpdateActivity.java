package com.app.lostandfound.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.app.lostandfound.R;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomEditText;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.pojo.PeopleData;

public class PostUpdateActivity extends BaseActivity {

String catId="",subCatId="",gender="", postType="";;
PeopleData peopleData;
    RadioGroup radioGroup;
CustomEditText edtFirstName,edtLastName,edtAge,edtBodyColor,edtHeight,edtExtraDetail,edtRewardPrice,edtBreed,edtBrand,edtModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_form);
        if(getIntent().getStringExtra("POST_TYPE")!=null)
        {
            postType=getIntent().getStringExtra("POST_TYPE");
        }if(getIntent().getStringExtra("CAT_ID")!=null)
        {
            catId=getIntent().getStringExtra("CAT_ID");
        }
        if(getIntent().getStringExtra("SUB_ID")!=null)
        {
subCatId=getIntent().getStringExtra("SUB_ID");
        }




        initView();

    }


    private void initView()
    {
         radioGroup = (RadioGroup)findViewById(R.id.groupradio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.radio_female) {
                    gender="F";
                } else if(checkedId == R.id.radio_male) {
                    gender="M";
                }
            }

        });
        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((CustomTextView)findViewById(R.id.header_toolbar)).setText(context.getResources().getString(R.string.peopledetail));

        ((CustomTextView) findViewById(R.id.btnNext)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(validation())
                {
                    setPeopleData();
                    Intent intent = new Intent(context, LastLocationActivity.class);
                    intent.putExtra("DATA",peopleData);
                    startActivity(intent);
                }
            }
        });

        edtBreed=(CustomEditText)findViewById(R.id.edtBreed);
        edtBrand=(CustomEditText)findViewById(R.id.edtBrand);
        edtModel=(CustomEditText)findViewById(R.id.edtModel);
        edtHeight=(CustomEditText)findViewById(R.id.edtHeight);
        edtBodyColor=(CustomEditText)findViewById(R.id.edtBodyColor);
        edtLastName=(CustomEditText)findViewById(R.id.edtLastName);
        edtFirstName=(CustomEditText)findViewById(R.id.edtFirstName);
        edtAge=(CustomEditText)findViewById(R.id.edtAge);
        edtExtraDetail=(CustomEditText)findViewById(R.id.edtExtraDetail);
        edtRewardPrice=(CustomEditText)findViewById(R.id.edtRewardPrice);

        if(catId.equals("1"))//people
        {
            edtModel.setVisibility(View.GONE);
            edtBrand.setVisibility(View.GONE);
            edtBreed.setVisibility(View.GONE);
        }
        if(catId.equals("2"))//pets
        {
            edtFirstName.setVisibility(View.GONE);
            edtLastName.setVisibility(View.GONE);
            edtModel.setVisibility(View.GONE);
            edtBrand.setVisibility(View.GONE);
        }
        if(catId.equals("6"))//Document
        {
            edtModel.setVisibility(View.GONE);
            edtBrand.setVisibility(View.GONE);
            edtBreed.setVisibility(View.GONE);
            edtAge.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
            edtBodyColor.setVisibility(View.GONE);
            edtHeight.setVisibility(View.GONE);
        }
        if(catId.equals("3")||catId.equals("4")||catId.equals("5")||catId.equals("7")||catId.equals("8")||catId.equals("9"))//Automobile,bags,electronics,keys,jwellry
        {
            edtFirstName.setVisibility(View.GONE);
            edtLastName.setVisibility(View.GONE);
            edtBreed.setVisibility(View.GONE);
            edtAge.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);

        }



        edtFirstName.setText("");
        edtLastName.setText("");
        edtHeight.setText("");
        edtBodyColor.setText("");
        edtAge.setText("");


    }



    public boolean validation()
    {
        if(catId.equals("1"))
        {
            if (edtFirstName.getText().toString().isEmpty())
            {
                edtFirstName.setError(context.getResources().getString(R.string.enter_first_name));
                return false;
            }

            if (edtLastName.getText().toString().isEmpty())
            {
                edtLastName.setError(context.getResources().getString(R.string.enter_last_name));
                return false;
            }
        }
        if(catId.equals("2"))
        {
            if (edtBreed.getText().toString().isEmpty())
            {
                edtBreed.setError(context.getResources().getString(R.string.enter_animal_breed));
                return false;
            }
        }
        if(catId.equals("3")||catId.equals("4")||catId.equals("5")||catId.equals("7")||catId.equals("8")||catId.equals("9"))//Automobile,bags,electronics,keys,jwellry
        {
            if (edtBrand.getText().toString().isEmpty()) {
                edtBrand.setError(context.getResources().getString(R.string.enter_brand));
                return false;
            }
            if (edtModel.getText().toString().isEmpty())
            {
                edtModel.setError(context.getResources().getString(R.string.enter_model));
                return false;
            }
        }
        if(catId.equals("1")||catId.equals("2")) {
            if (edtAge.getText().toString().isEmpty()) {
                edtAge.setError(context.getResources().getString(R.string.enter_age_name));
                return false;
            }
        }
            if (edtBodyColor.getText().toString().isEmpty()) {
                edtBodyColor.setError(context.getResources().getString(R.string.enter_body_color));
                return false;
            }

            if (edtHeight.getText().toString().isEmpty())
            {
                edtHeight.setError(context.getResources().getString(R.string.enter_height));
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
        peopleData=new PeopleData();
        peopleData.setCategory_id(catId);
        peopleData.setSubcategory_id(subCatId);
        peopleData.setPost_type(postType);
        peopleData.setFirst_name(edtFirstName.getText().toString());
        peopleData.setLast_name(edtLastName.getText().toString());
        peopleData.setAge(edtAge.getText().toString());
        peopleData.setGender(gender);
        peopleData.setColor(edtBodyColor.getText().toString());
        peopleData.setHeight(edtHeight.getText().toString());
        peopleData.setExtra_detial(edtExtraDetail.getText().toString());
        peopleData.setReward(edtRewardPrice.getText().toString());
        peopleData.setModel(edtModel.getText().toString());
        peopleData.setBrand(edtBrand.getText().toString());

    }




}
