package com.app.lostandfound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.app.lostandfound.R;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ObjCallback;
import com.app.lostandfound.lostfoundinterface.ObjPostCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.pojo.LostAllPost;
import com.bumptech.glide.Glide;

import java.util.LinkedList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Navya on 28-04-2016.
 */
public class LostFoundAdsFavoriteAdapter extends RecyclerView.Adapter<LostFoundAdsFavoriteAdapter.ViewHolder> {

    Context context;
    private LinkedList<LostAllPost> lostAllPostsList = new LinkedList<>();
   private ObjPostCallback callback;
   private boolean flag;
    public LostFoundAdsFavoriteAdapter(Context context,boolean flag, LinkedList<LostAllPost> list, ObjPostCallback callback)
    {
        this.context = context;
        this.callback=callback;
        this.flag=flag;
        lostAllPostsList=list;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lostfound_favourite_layout, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        LostAllPost lostAllPost=lostAllPostsList.get(position);
        holder.txtLostFoundName.setText(lostAllPost.getFirst_name()+" "+lostAllPost.getLast_name());
        holder.txtLostFoundDate.setText("Last location date : "+lostAllPost.getLast_date());
        holder.txtLostFoundAddress.setText("Last location : "+lostAllPost.getLast_location());

        if (lostAllPost.getImagesArrayList().size()>0)
        {
            String imgUrl= lostAllPost.getImagesArrayList().get(0).getPost_image();

            if(!imgUrl.isEmpty()&&imgUrl.contains(".png"))
            {
                String url = ApiClient.BASE_URL_IMAGE+imgUrl;
                System.out.println("url : "+url);
                Glide.with(context) //1
                        .load(url)
                        .placeholder(R.mipmap.profile_large)
                        .error(R.mipmap.profile_large)
                        .into(holder.imgPost);
            }
        }

        holder.cardView.setTag(lostAllPost);
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LostAllPost lostAllPost=(LostAllPost)view.getTag();
                callback.result(lostAllPost,position,3);
            }
        });

     if(flag)
     {
         holder.imgLike.setVisibility(View.VISIBLE);
     }
     else
     {
         holder.imgLike.setVisibility(View.GONE);
     }




    }


    @Override
    public int getItemCount() {
        return lostAllPostsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        CustomTextView txtLostFoundName,txtLostFoundAddress,txtLostFoundDate;
        ImageView imgPost;
        CheckBox imgLike;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            txtLostFoundName=(CustomTextView)view.findViewById(R.id.txtLostFoundName);
            txtLostFoundAddress=(CustomTextView)view.findViewById(R.id.txtLostFoundAddress);
            txtLostFoundDate=(CustomTextView)view.findViewById(R.id.txtLostFoundDate);
            imgPost=(ImageView)view.findViewById(R.id.imgPost);
            imgLike=(CheckBox)view.findViewById(R.id.imgLike);
        }

    }


}
