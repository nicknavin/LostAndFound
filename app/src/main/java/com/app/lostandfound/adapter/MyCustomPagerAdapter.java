package com.app.lostandfound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.lostandfound.R;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.pojo.Images;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MyCustomPagerAdapter extends PagerAdapter
{
    Context context;

    LayoutInflater layoutInflater;
    ArrayList<Images> urlList;
    ApiCallback apiCallback;
    public MyCustomPagerAdapter(Context context, ArrayList<Images> list, ApiCallback callback)
    {
        this.context = context;
        urlList=list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        apiCallback=callback;
    }
    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item, container, false);
Images images=urlList.get(position);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imgPost);
       String url = ApiClient.BASE_URL_IMAGE+ images.getPost_image();
        Glide.with(context) //1
                .load(url)
                .placeholder(R.mipmap.place_holder)
                .error(R.mipmap.place_holder)
                .into(imageView);


        container.addView(itemView);

        //listening to image click
        imageView.setTag(images);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            apiCallback.result(position);
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
