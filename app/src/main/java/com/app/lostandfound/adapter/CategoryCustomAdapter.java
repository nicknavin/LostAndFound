package com.app.lostandfound.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.lostandfound.R;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.pojo.CategoryData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Navya on 28-04-2016.
 */
public class CategoryCustomAdapter extends RecyclerView.Adapter<CategoryCustomAdapter.ViewHolder> {

    Context context;
    ArrayList<CategoryData> categoryList;
    ApiCallback apiCallback;
    int[] colors;
    public CategoryCustomAdapter(Context context, ArrayList<CategoryData> list, ApiCallback callback) {
        this.context = context;
        this.categoryList=list;
       colors = context.getResources().getIntArray(R.array.rainbow);
apiCallback=callback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_circle_layout, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        CategoryData categoryData=categoryList.get(position);
        //setBackgroundDrawableColor(holder.layCategory,position);

        if(categoryData.getCategory_image()!=null&&!categoryData.getCategory_image().isEmpty())
        {
            Glide.with(context)
                    .load(categoryData.getCategory_image())
                    .placeholder(R.mipmap.app_icon)
                    .into(holder.imgCategory);
        }
        holder.txtCategory.setText(categoryData.getCategory_title());
        holder.layCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiCallback.result(position);

            }
        });

    }

    private void setBackgroundDrawableColor(View view, int position) {
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground().mutate();
        gradientDrawable.setColor(context.getResources().getColor(R.color.ripple1));

    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout layCategory;
ImageView imgCategory;
CustomTextView txtCategory;

        public ViewHolder(View view) {
            super(view);
            layCategory = (RelativeLayout) view.findViewById(R.id.layCategory);
            txtCategory=(CustomTextView)view.findViewById(R.id.txtCategory);
            imgCategory=(ImageView)view.findViewById(R.id.imgCategory);
        }

    }


}
