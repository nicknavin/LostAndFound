package com.app.lostandfound.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.lostandfound.R;
import com.app.lostandfound.costomview.CustomImageView;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.lostfoundinterface.ObjCallback;
import com.app.lostandfound.lostfoundinterface.ObjPostCallback;
import com.app.lostandfound.lostfoundinterface.ObjectPostCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.pojo.LostAllPost;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.utility.DataPrefrence;
import com.app.lostandfound.utility.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Navya on 28-04-2016.
 */
public class LostFoundPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private static final int DATA_VIEW_TYPE = 0;
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE  = 1;
   Context context;
   private LinkedList<Object> lostAllPostsList;
    LinkedList<Object> mArrayList;
   private ObjPostCallback callback;
    ObjectPostCallback objectPostCallback;
   String userId ;
    private int spaceBetweenAds;
    public LostFoundPostAdapter(Context context, LinkedList<Object> list, int spaceBetweenAds,ObjPostCallback callback)
    {
        this.context = context;
        this.callback=callback;
        lostAllPostsList=list;
        mArrayList=list;
        this.spaceBetweenAds=spaceBetweenAds;
        userId = DataPrefrence.getPref(context, Constant.USER_ID, "");
    }
//    public LostFoundPostAdapter(Context context, LinkedList<Object> list, int spaceBetweenAds, ObjectPostCallback callback)
//    {
//        this.context = context;
//        this.objectPostCallback=callback;
//        lostAllPostsList=list;
//        mArrayList=list;
//        this.spaceBetweenAds=spaceBetweenAds;
//        userId = DataPrefrence.getPref(context, Constant.USER_ID, "");
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType) {

            case UNIFIED_NATIVE_AD_VIEW_TYPE :
                View unifiedNativeLayoutView = LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.ad_unified,
                        parent, false);
                return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
            default:

                View dataLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.row_lostproduct_layout, parent, false);
                return new ViewHolder(dataLayoutView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        // Binding data based on View Type
        switch (viewType) {

            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                UnifiedNativeAd nativeAd = (UnifiedNativeAd) lostAllPostsList.get(position);
                populateNativeAdView(nativeAd, ((UnifiedNativeAdViewHolder) holder).getAdView());
            default:
                try {
                    final ViewHolder dataViewHolder = (ViewHolder) holder;
                    Object obj=lostAllPostsList.get(position);
                    LostAllPost lostAllPost=(LostAllPost)obj;
//                    if(lostAllPost.getPost_type().equals())
                    dataViewHolder.txtTitle.setText(lostAllPost.getTitle());
                    if(!lostAllPost.getFirst_name().isEmpty()){
                    dataViewHolder.txtLostFoundName.setText(lostAllPost.getFirst_name()+" "+lostAllPost.getLast_name());
                    }
                    else
                    {
                        dataViewHolder.txtLostFoundName.setVisibility(View.GONE);
                    }
                    dataViewHolder.txtLostFoundDate.setText(lostAllPost.getLast_date());
                    dataViewHolder.txtLostFoundAddress.setText(lostAllPost.getLast_location());
                    dataViewHolder.txtPostDetail.setText(lostAllPost.getPost_description());

                    if (lostAllPost.getImagesArrayList().size()>0)
                    {
                        String imgUrl= lostAllPost.getImagesArrayList().get(0).getPost_image();
                        System.out.println("imgUrl "+imgUrl);
                        String url = ApiClient.BASE_URL_IMAGE+imgUrl;
                        System.out.println("url : "+url);
                        Glide.with(context)
                                .asBitmap()
                                .load(url)
                                .transform(new RoundedCorners(16))

                                .into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

//                                        dataViewHolder.imgPost(resource);
                                        dataViewHolder.imgPost.setImageBitmap(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });
                    }

                    dataViewHolder.imgPost.setTag(lostAllPost);
                    dataViewHolder.imgPost.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LostAllPost post=(LostAllPost)view.getTag();
                            callback.result(post,position,3);
                        }
                    });


                    dataViewHolder.imgShare.setTag(lostAllPost);
                    dataViewHolder.imgShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LostAllPost post=(LostAllPost)view.getTag();
                            callback.result(post,position,4);
                        }
                    });

                    dataViewHolder.imgLike.setTag(lostAllPost);
                    dataViewHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {

                            if (!userId.isEmpty()) {
                                CheckBox checkBox=(CheckBox)view;
                                LostAllPost post=(LostAllPost)checkBox.getTag();
                                if(checkBox.isChecked())
                                {
                                    callback.result(post,position,1);
                                }
                                else
                                {
                                    callback.result(post,position,0);
                                }
                            }
                            else
                            {
                                Utility.showToast(context,"Please login");
                            }
                        }
                    });




                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
    private void populateNativeAdView(UnifiedNativeAd nativeAd,
                                      UnifiedNativeAdView adView) {
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        NativeAd.Image icon = nativeAd.getIcon();

        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd);
    }



    @Override
    public int getItemCount() {
        return lostAllPostsList.size();
    }

    @Override
    public Filter getFilter()
    {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    lostAllPostsList = mArrayList;
                } else {

                    LinkedList<Object> filteredList = new LinkedList<>();

                    for (Object obj : mArrayList)
                    {
                        LostAllPost Category=(LostAllPost)obj;
                        if (Category.getFirst_name().toLowerCase().contains(charString)||Category.getBrand().toLowerCase().contains(charString) ) {

                            filteredList.add(Category);
                        }
                    }

                    lostAllPostsList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = lostAllPostsList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                lostAllPostsList = (LinkedList<Object>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
//        return (position % (spaceBetweenAds + 1) == spaceBetweenAds) ? NATIVE_EXPRESS_AD_VIEW_TYPE: DATA_VIEW_TYPE;

        Object recyclerViewItem = lostAllPostsList.get(position);
        if (recyclerViewItem instanceof UnifiedNativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE;
        }
        return DATA_VIEW_TYPE;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        CustomTextView txtLostFoundName,txtLostFoundAddress,txtLostFoundDate,txtPostDetail,txtTitle;
        ImageView imgPost,imgShare;
        CheckBox imgLike;


        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            txtTitle=(CustomTextView)view.findViewById(R.id.txtTitle);
            txtLostFoundName=(CustomTextView)view.findViewById(R.id.txtLostFoundName);
            txtPostDetail=(CustomTextView)view.findViewById(R.id.txtPostDetail);
            txtLostFoundAddress=(CustomTextView)view.findViewById(R.id.txtLostFoundAddress);
            txtLostFoundDate=(CustomTextView)view.findViewById(R.id.txtLostFoundDate);
            imgPost=(ImageView)view.findViewById(R.id.imgPost);
            imgShare=(ImageView)view.findViewById(R.id.imgShare);
            imgLike=(CheckBox)view.findViewById(R.id.imgLike);
        }

    }


}
