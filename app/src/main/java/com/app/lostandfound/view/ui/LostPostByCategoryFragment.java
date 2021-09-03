package com.app.lostandfound.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.LostFoundPostAdapter;
import com.app.lostandfound.base.BaseFragment;
import com.app.lostandfound.lostfoundinterface.ObjPostCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.network.ApiInterface;
import com.app.lostandfound.pojo.LostAllPost;
import com.app.lostandfound.pojo.LostAllPostResponse;
import com.app.lostandfound.utility.Utility;
import com.app.lostandfound.view.PostDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LostPostByCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostPostByCategoryFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
    View view;
    String catg="",subcatg="";
    public LostPostByCategoryFragment() {
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
    public static LostPostByCategoryFragment newInstance(String param1, String param2) {
        LostPostByCategoryFragment fragment = new LostPostByCategoryFragment();
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
            catg = getArguments().getString(ARG_PARAM1);
            subcatg = getArguments().getString(ARG_PARAM2);
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
        lostFoundPostAdapter=new LostFoundPostAdapter(context, lostAllPostArrayList,10, new ObjPostCallback() {
            @Override
            public void result(Object obj, int position, int type) {
                LostAllPost lostAllPost=(LostAllPost)obj;
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
            }
        });
        recyclerView.setAdapter(lostFoundPostAdapter);

    }



    public void getAllLostPost(String page)
    {
        if (isInternetConnected()) {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<LostAllPostResponse> call = apiInterface.GetLostPostByCategory(catg,subcatg,page);
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

                        lostAllPostArrayList.addAll(listupdate);
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














}
//                    for(LostAllPost lostAllPost : lostAllPostArrayList)
//                    {
//                        System.out.println("title "+lostAllPost.getFirst_name());
//                    }