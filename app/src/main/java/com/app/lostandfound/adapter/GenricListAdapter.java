package com.app.lostandfound.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lostandfound.R;


import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class GenricListAdapter extends RecyclerView.Adapter<GenricListAdapter.ViewHolder> {

    Context context;
    private ArrayList<String> clinicArrayList = new ArrayList<>();

    public GenricListAdapter(Context context) {
        this.context = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_laundry_item, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

    }


    @Override
    public int getItemCount() {
        return 12;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }

    }


}
