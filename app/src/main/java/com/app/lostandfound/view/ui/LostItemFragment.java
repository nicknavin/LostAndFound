package com.app.lostandfound.view.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.lostandfound.BuildConfig;
import com.app.lostandfound.R;
import com.app.lostandfound.adapter.LostFoundPostAdapter;
import com.app.lostandfound.base.BaseFragment;
import com.app.lostandfound.lostfoundinterface.ObjCallback;
import com.app.lostandfound.lostfoundinterface.ObjPostCallback;
import com.app.lostandfound.lostfoundinterface.ObjectPostCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.network.ApiInterface;
import com.app.lostandfound.pojo.Images;
import com.app.lostandfound.pojo.LostAllPost;
import com.app.lostandfound.pojo.LostAllPostResponse;
import com.app.lostandfound.utility.Utility;
import com.app.lostandfound.view.PostDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Native;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LostItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostItemFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String AD_UNIT_ID = "ca-app-pub-9444676719023189/2515573502";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    Context context;
    LinkedList<Object> lostAllPostArrayList=new LinkedList<>();

    LostFoundPostAdapter lostFoundPostAdapter;
    private boolean loaddingDone = true;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    int pageno = 1;
    int total_pages=0;
    LinearLayoutManager linearLayoutManager;
    public final static int spaceBetweenAds = 5;
    View view;
    public LostItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LostItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LostItemFragment newInstance(String param1, String param2) {
        LostItemFragment fragment = new LostItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lost_item, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        setupUI();
    }

    private void setupUI()
    {
        loadNativeAds();
        pageno =1;
        initView();
        setUpRecyclerListener();

        getAllLostPost(""+ pageno);

    }

    public void searchPost(String key)
    {
        lostFoundPostAdapter.getFilter().filter(key);
    }

    private void initView()
    {

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        linearLayoutManager= new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        lostFoundPostAdapter=new LostFoundPostAdapter(context, lostAllPostArrayList,spaceBetweenAds ,new ObjPostCallback() {
            @Override
            public void result(Object obj, int position, int type)
            {
                final LostAllPost lostAllPost=(LostAllPost)obj;

                if(type==1) {
                    callLikePost(lostAllPost,position);
                }
                else   if(type==0)
                {
                    callUnLikePost(lostAllPost,position);
                }
                else if(type==3)
                {
                    Intent intent=new Intent(context, PostDetailActivity.class);
                    intent.putExtra("DATA",lostAllPost);
                    startActivity(intent);
                }
                else if(type==4)
                {
                    String url = ApiClient.BASE_URL_IMAGE+lostAllPost.getImagesArrayList().get(0).getPost_image();
                    final String data =lostAllPost.getFirst_name()+" " +lostAllPost.getLast_name()+" Last Location : "+lostAllPost.getAddress()+"\n"+lostAllPost.getPost_description();

                    Glide.with(context)
                            .asBitmap()
                            .load(url)
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                                {
                                    Utility.sharePost(resource,lostAllPost,context);
//                                    sendData(resource,lostAllPost);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                }
                            });
                }
            }
        });
        recyclerView.setAdapter(lostFoundPostAdapter);

    }


    public void getAllLostPost(String page)
    {
        if (isInternetConnected()) {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<LostAllPostResponse> call = apiInterface.getAllLostPost(page);
            call.enqueue(new Callback<LostAllPostResponse>()
            {
                @Override
                public void onResponse(Call<LostAllPostResponse> call, Response<LostAllPostResponse> response)
                {
                    dismiss_loading();
                    LostAllPostResponse lostAllPostResponse=response.body();
                    total_pages=lostAllPostResponse.getTotal_pages();

                    if(lostAllPostResponse.getStatus().equals("1"))
                    {
                        LinkedList<LostAllPost> listupdate = new LinkedList<>();

                        listupdate =lostAllPostResponse.getLostAllPostsList();

                        if(listupdate.size()>0)
                        {
                            if(pageno <=total_pages) {
                                loading=true;
                                pageno++;
                            }
                        }

                        LinkedList<Object> tempList=new LinkedList<>();
                        tempList.addAll(listupdate);
                        if (mNativeAds.size()>0) {
                            for (int i = spaceBetweenAds; i <= tempList.size(); i += (spaceBetweenAds + 1))
                            {
                                System.out.println("ad tempList "+tempList.size());
                                System.out.println("ad size"+mNativeAds.size());
                                tempList.add(i, mNativeAds.get(Utility.getrandomNumber(NUMBER_OF_ADS)));
                            }
                        }


                        lostAllPostArrayList.addAll(tempList);
//                        addNativeExpressAds();
                       // insertAdsInMenuItems();

                        lostFoundPostAdapter.notifyDataSetChanged();






                    }


                }

                @Override
                public void onFailure(Call<LostAllPostResponse> call, Throwable t) {
dismiss_loading();
                }
            });
        }
        else
        {
            showInternetConnectionToast();
        }

    }


    public void setUpRecyclerListener() {
        loading = true;
        loaddingDone = true;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();


                if (loading && loaddingDone)
                {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount)
                    {

                        loading = false;
                        Utility.Log("inside the recly litner");

                        if (baseActivity.isConnectingToInternet(context))
                        {
                            getAllLostPost(""+ pageno);
                        } else {
                            showInternetConnectionToast();
                        }
                    }
                }
            }
        });
    }



    public void callLikePost(final LostAllPost lostAllPost, final int position)
    {
        if (isInternetConnected()) {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.callLikePost(userId,lostAllPost.getPost_id());
           call.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
               {
                   dismiss_loading();
                   if(response.body()!=null)
                   {
                      ResponseBody responseBody= response.body();
                       try {
                           String data =responseBody.string();
                           JSONObject jsonObject=new JSONObject(data);
                           showToast(jsonObject.getString("message"));
                           if(jsonObject.getString("status").equals("1")){
//                               lostAllPost.setLike(true);
                               lostFoundPostAdapter.notifyItemChanged(position,lostAllPost);

                           }
                       } catch (IOException | JSONException e) {
                           e.printStackTrace();
                       }


                   }

               }

               @Override
               public void onFailure(Call<ResponseBody> call, Throwable t) {
                   dismiss_loading();
               }
           });
        }
        else
        {
            showInternetConnectionToast();
        }

    }


    public void callUnLikePost(final LostAllPost lostAllPost, final int position)
    {
        if (isInternetConnected()) {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.callUnlikePost(userId,lostAllPost.getPost_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                {dismiss_loading();
                    if(response.body()!=null) {
                        ResponseBody responseBody = response.body();
                        try {
                            String data = responseBody.string();
                            JSONObject jsonObject = new JSONObject(data);
                            showToast(jsonObject.getString("message"));
                            if (jsonObject.getString("status").equals("1"))
                            {
//                                lostAllPost.setLike(false);
                                lostFoundPostAdapter.notifyItemChanged(position,lostAllPost);


                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
dismiss_loading();
                }
            });
        }
        else
        {
            showInternetConnectionToast();
        }

    }








    private void addNativeExpressAds() {

        // We are looping through our original dataset
        // And adding Admob's Native Express Ad at consecutive positions at a distance of spaceBetweenAds
        // You should change the spaceBetweenAds variable according to your need
        // i.e how often you want to show ad in RecyclerView

        for (int i = spaceBetweenAds; i <= lostAllPostArrayList.size(); i += (spaceBetweenAds + 1))
        {
            NativeExpressAdView adView = new NativeExpressAdView(context);
            // I have used a Test ID provided by Admob below
            // you should replace it with yours
            // And if wou are just experimenting, then just copy the code
            adView.setAdUnitId(AD_UNIT_ID);
            lostAllPostArrayList.add(i, adView);

        }

        // Below we are using post on RecyclerView
        // because we want to resize our native ad's width equal to screen width
        // and we should do it after RecyclerView is created

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                float scale = context.getResources().getDisplayMetrics().density;
                int adWidth = (int) (recyclerView.getWidth() - (2 * context.getResources().getDimension(R.dimen.activity_horizontal_margin)));

                // we are setting size of adView
                // you should check admob's site for possible ads size
                AdSize adSize = new AdSize((int) (adWidth / scale), 150);

                // looping over mDataset to sesize every Native Express Ad to ew adSize
                for (int i = spaceBetweenAds; i <= lostAllPostArrayList.size(); i += (spaceBetweenAds + 1)) {
                    NativeExpressAdView adViewToSize = (NativeExpressAdView) lostAllPostArrayList.get(i);
                    adViewToSize.setAdSize(adSize);
                }

                // calling method to load native ads in their views one by one
                loadNativeExpressAd(spaceBetweenAds);
            }
        });

    }

//        Loads the Native Express ads in the items list.
//        Here we are loading next ad after previous ad has finished loading
//        so that it does not throw an error and blocks our UI

    private void loadNativeExpressAd(final int index)
    {

        if (index >= lostAllPostArrayList.size())
        {
            return;
        }

        Object item = lostAllPostArrayList.get(index);
        if (!(item instanceof NativeExpressAdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a Native"
                    + " Express ad.");
        }

        final NativeExpressAdView adView = (NativeExpressAdView) item;

        // Set an AdListener on the NativeExpressAdView to wait for the previous Native Express ad
        // to finish loading before loading the next ad in the items list.
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // The previous Native Express ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadNativeExpressAd(index + spaceBetweenAds + 1);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // The previous Native Express ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("AdmobMainActivity", "The previous Native Express ad failed to load. Attempting to"
                        + " load the next Native Express ad in the items list.");
                loadNativeExpressAd(index + spaceBetweenAds + 1);
            }
        });

        // Load the Native Express ad.
        //We also registering our device as Test Device with addTestDevic("ID") method
//        adView.loadAd(new AdRequest.Builder().addTestDevice("YOUR_TEST_DEVICE_ID").build());
        adView.loadAd(new AdRequest.Builder().build());
    }



    // The AdLoader used to load ads.
    private AdLoader adLoader;
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();
    // The number of native ads to load.
    public static final int NUMBER_OF_ADS = 5;
    private void loadNativeAds() {

        AdLoader.Builder builder = new AdLoader.Builder(getContext(), getString(R.string.ad_unit_id));
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // A native ad loaded successfully, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        mNativeAds.add(unifiedNativeAd);
                        System.out.println();
                        if (!adLoader.isLoading())
                        {
                           // insertAdsInMenuItems();
                        }
                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // A native ad failed to load, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        Log.e("MainActivity", "The previous native ad failed to load. Attempting to"
                                + " load another.");
                        if (!adLoader.isLoading()) {
                          // insertAdsInMenuItems();
                        }
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);
//        adLoader.loadAd(new AdRequest.Builder().build());
    }
    private void insertAdsInMenuItems()
    {
        if (mNativeAds.size() <= 0)
        {
            return;
        }

//        int offset = (lostAllPostArrayList.size() / mNativeAds.size()) + 1;
//        System.out.println("offset "+offset);
//        System.out.println("mNativeAds "+mNativeAds.size());
//        int index = 0;
//        for (UnifiedNativeAd ad : mNativeAds)
//        {
//            lostAllPostArrayList.add(index, ad);
//            index = index + offset;
//        }

int j=0;
        for (int i = spaceBetweenAds; i <= lostAllPostArrayList.size(); i += (spaceBetweenAds + 1))
        {
//            NativeExpressAdView adView = new NativeExpressAdView(context);
//            // I have used a Test ID provided by Admob below
//            // you should replace it with yours
//            // And if wou are just experimenting, then just copy the code
//            adView.setAdUnitId(AD_UNIT_ID);

            System.out.println("ad size"+mNativeAds.size());
            lostAllPostArrayList.add(i, mNativeAds.get(j));
            if(j<4) {
                j++;
            }
            else
            {
                j=0;
            }

        }
//        loadMenu();
    }

    private LinkedList<Object> insertAdsInMenuItemsLoadMore(LinkedList<Object> lostAllPostArrayList)
    {


//        int offset = (lostAllPostArrayList.size() / mNativeAds.size()) + 1;
//        System.out.println("offset "+offset);
//        System.out.println("mNativeAds "+mNativeAds.size());
//        int index = 0;
//        for (UnifiedNativeAd ad : mNativeAds)
//        {
//            lostAllPostArrayList.add(index, ad);
//            index = index + offset;
//        }

        int j=0;
        for (int i = spaceBetweenAds; i <= lostAllPostArrayList.size(); i += (spaceBetweenAds + 1))
        {
//            NativeExpressAdView adView = new NativeExpressAdView(context);
//            // I have used a Test ID provided by Admob below
//            // you should replace it with yours
//            // And if wou are just experimenting, then just copy the code
//            adView.setAdUnitId(AD_UNIT_ID);

            System.out.println("ad size"+mNativeAds.size());
            lostAllPostArrayList.add(i, mNativeAds.get(j));
            if(j<4) {
                j++;
            }
            else
            {
                j=0;
            }

        }
        return lostAllPostArrayList;
//        loadMenu();
    }

String urlPost;

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;

            File filesDir = context.getFilesDir();
           File imageFile = new File(filesDir, "post" + ".jpg");

            OutputStream os;
            try {
                os = new FileOutputStream(imageFile);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }
//        FileProvider.getUriForFile(Objects.requireNonNull(getActivity().getApplicationContext()),
//                BuildConfig.APPLICATION_ID + ".provider", imageFile);
        bmpUri=FileProvider.getUriForFile(getContext(),getContext().getApplicationContext().getPackageName()+".provider",imageFile);
//            bmpUri = Uri.fromFile(imageFile);

        return bmpUri;
    }


    public void sendData(Bitmap bitmap,LostAllPost lostAllPost)
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("image/*");
        String name ="Name : "+lostAllPost.getFirst_name()+lostAllPost.getLast_name()+"\n";

        String age=   "Age : "+lostAllPost.getAge()+"\n"+
                     "Height : "+lostAllPost.getHeight()+"\n" ;

        String color=  "Body Color : "+lostAllPost.getColor()+"\n";

        String pets="Breed : "+lostAllPost.getBreed()+"\n";

        String model="Brand : "+lostAllPost.getBrand()+"\n"+"Model : "+lostAllPost.getModel()+"\n";

        String date="Lost Date : "+lostAllPost.getLast_date()+"\n" +"Lost Location : "+lostAllPost.getLast_location()+"\n"+lostAllPost.getAddress()  +"\n"+lostAllPost.getPost_description();
String postInformation="";

         if(lostAllPost.getCategory_id().equals("1"))
         {
             postInformation= lostAllPost.getTitle()+" "+name+age+color+date;
         }
         if(lostAllPost.getCategory_id().equals("2"))
         {
             postInformation= lostAllPost.getTitle()+" "+pets+age+date;
         }
        if(lostAllPost.getCategory_id().equals("6"))
        {
            postInformation= lostAllPost.getTitle()+" "+name+date;
        }
        else
        {
            postInformation= lostAllPost.getTitle()+" "+model+color+date;
        }
        String postType="";
if(lostAllPost.getPost_type().equals("1"))
{
    postType="Lost Item";
}
else
{
    postType="Found Item";
}

        String msg =postType +"\n" + postInformation + "\n" + "This post is published on GoFound app!" + "\n" + "For more information,get GoFound app on Android for free!" + "\n" +
                "Google play:" + "\n" + "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName() + "\n" +
                "App Store:" + "\n" ;


        shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }




}
//                    for(LostAllPost lostAllPost : lostAllPostArrayList)
//                    {
//                        System.out.println("title "+lostAllPost.getFirst_name());
//                    }