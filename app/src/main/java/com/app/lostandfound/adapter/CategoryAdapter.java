package com.app.lostandfound.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.lostandfound.R;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ApiCallback;

import java.util.ArrayList;
import java.util.Random;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Navya on 28-04-2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    private ArrayList<String> clinicArrayList = new ArrayList<>();
    ApiCallback apiCallback;
    int[] colors;
    public CategoryAdapter(Context context, int[] colors, ApiCallback callback) {
        this.context = context;
        this.colors=colors;
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
        setBackgroundDrawableColor(holder.layCategory, position,colors[position]);

        if (position == 1)
        {
holder.imgCategory.setImageResource(R.mipmap.people);
holder.txtCategory.setText("People");

        } if (position == 2)
        {
holder.imgCategory.setImageResource(R.mipmap.pass);
holder.txtCategory.setText("Pass/ID");
        }
        if (position == 3)
        {
holder.imgCategory.setImageResource(R.mipmap.pet);
holder.txtCategory.setText("Pets");
        }if (position == 4)
        {
holder.imgCategory.setImageResource(R.mipmap.car);
holder.txtCategory.setText("Car");
        }
        holder.layCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiCallback.result(position);

            }
        });

    }

    private void setBackgroundDrawableColor(View view, int position,int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground().mutate();
        gradientDrawable.setColor(color);

    }


    @Override
    public int getItemCount() {
        return 9;
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
