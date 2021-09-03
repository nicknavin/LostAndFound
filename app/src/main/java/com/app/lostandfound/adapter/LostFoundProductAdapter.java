package com.app.lostandfound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lostandfound.R;
import com.app.lostandfound.costomview.CustomImageView;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ObjCallback;
import com.app.lostandfound.pojo.LostAllPost;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Navya on 28-04-2016.
 */
public class LostFoundProductAdapter extends RecyclerView.Adapter<LostFoundProductAdapter.ViewHolder> {

    Context context;
    private ArrayList<LostAllPost> lostAllPostsList = new ArrayList<>();
   private ObjCallback callback;
    public LostFoundProductAdapter(Context context) {
        this.context = context;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lostproduct_layout, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {


    }


    @Override
    public int getItemCount() {
        return 12;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        CustomTextView txtLostFoundName,txtLostFoundAddress,txtLostFoundDate;
        CustomImageView imgPost;


        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            txtLostFoundName=(CustomTextView)view.findViewById(R.id.txtLostFoundName);
            txtLostFoundAddress=(CustomTextView)view.findViewById(R.id.txtLostFoundAddress);
            txtLostFoundDate=(CustomTextView)view.findViewById(R.id.txtLostFoundAddress);

        }

    }


}
