package com.app.lostandfound.costomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;


import com.app.lostandfound.R;

import androidx.appcompat.widget.AppCompatImageView;


/**
 * Created by Rahul on 5/17/2016.
 */
public class CustomImageView extends AppCompatImageView {

    Context context;
    String imageType;
    int normalWidth, normalHeight;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomImageView);
        imageType = a.getString(R.styleable.CustomImageView_image_type);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        normalWidth = widthMeasureSpec;
        normalHeight = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (imageType.equals("0")) {
            setMeasuredDimension(normalWidth, normalHeight);
        } else if (imageType.equals("1")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, width);
        } else if (imageType.equals("2")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width / 2);
        } else if (imageType.equals("3")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width * 2 / 3);
        } else if (imageType.equals("4")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width * 3 / 2);
        } else if (imageType.equals("5")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width * 5 / 4);
            Log.d("UBABKJBKBKJ", width + "");
        } else if (imageType.equals("6")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) (width * 1.5));
            Log.d("UBABKJBKBKJ", width + "");
        }
    }

    public void setImageType(String tem) {
        imageType = tem;
        if (imageType.equals("0")) {
            setMeasuredDimension(normalWidth, normalHeight);
        } else if (imageType.equals("1")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, width);
        } else if (imageType.equals("2")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width / 2);
        } else if (imageType.equals("3")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width * 2 / 3);
        } else if (imageType.equals("4")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width * 3 / 2);
        } else if (imageType.equals("5")) {
            int width = getMeasuredWidth();
            Log.d("UBABKJBKBKJ", width + "");
            setMeasuredDimension(width, (int) width * 5 / 4);
        } else if (imageType.equals("6")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) (width * 1.5));
            Log.d("UBABKJBKBKJ", width + "");
        }
    }

}
