package com.app.lostandfound.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.ViewHolder> {

    Context context;
    private ArrayList<CategoryData> categoryList = new ArrayList<>();
    ApiCallback apiCallback;
    int[] colors;

    public CategoryGridAdapter(Context context, int[] colors, ArrayList<CategoryData> list, ApiCallback callback) {
        this.context = context;
        this.colors = colors;
        apiCallback = callback;
        categoryList = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_layout, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //setBackgroundDrawableColor(holder.layCategory, position, colors[position]);

        CategoryData categoryData=categoryList.get(position);

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

    private void setBackgroundDrawableColor(View view, int position, int color) {
//        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground().mutate();
//        gradientDrawable.setColor(color);

    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imgCategory;
        CustomTextView txtCategory;
        LinearLayout layCategory;

        public ViewHolder(View view) {
            super(view);

            txtCategory = (CustomTextView) view.findViewById(R.id.txtCategory);
            imgCategory = (ImageView) view.findViewById(R.id.imgCategory);
            layCategory=(LinearLayout)view.findViewById(R.id.layCategory);
        }

    }


}
