package com.app.lostandfound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lostandfound.R;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.pojo.SubCategoryData;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Navya on 28-04-2016.
 */
public class SubCategoryListAdapter extends RecyclerView.Adapter<SubCategoryListAdapter.ViewHolder> {

    Context context;
    private ArrayList<SubCategoryData> subCategoryDataArrayList = new ArrayList<>();

    ApiCallback callback;
    public SubCategoryListAdapter(Context context,ArrayList<SubCategoryData> list, ApiCallback callback) {
        this.context = context;
this.callback=callback;
subCategoryDataArrayList=list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_subcategory_layout, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        SubCategoryData subCategoryData=subCategoryDataArrayList.get(position);
holder.txtSubCategory.setText(subCategoryData.getSubcategory_title());
holder.txtSubCategory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        callback.result(position);
    }
});
    }


    @Override
    public int getItemCount() {
        return subCategoryDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

CustomTextView txtSubCategory;

        public ViewHolder(View view) {
            super(view);

            txtSubCategory=(CustomTextView)view.findViewById(R.id.txtSubCategory);
        }

    }


}
