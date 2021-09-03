package com.app.lostandfound.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.CategoryCustomAdapter;
import com.app.lostandfound.adapter.CategoryGridAdapter;
import com.app.lostandfound.base.BaseFragment;
import com.app.lostandfound.costomview.CustomEditText;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.network.ApiClient;
import com.app.lostandfound.network.ApiInterface;
import com.app.lostandfound.pojo.CategoryData;
import com.app.lostandfound.pojo.CategoryResponse;
import com.app.lostandfound.pojo.UserDetailResponse;
import com.app.lostandfound.pojo.UserDetails;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.utility.DataPrefrence;
import com.app.lostandfound.view.FilterActivity;
import com.app.lostandfound.view.SearchActivity;
import com.app.lostandfound.view.SubCategoryActivity;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends BaseFragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Context context;
    ImageView img_filter;
    CustomEditText edt_search;
    ArrayList<CategoryData> list =new ArrayList<>();
    int frgPosition=0;
    String key;
    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
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
        return inflater.inflate(R.layout.fragment_explore_demo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        getCategory();
    }

    public void initView(View view) {
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        edt_search=(CustomEditText)view.findViewById(R.id.edt_search);
        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                key = s.toString().trim();

                if(frgPosition==1)
                {
                    lostItemFragment.searchPost(key);
                }
                if(frgPosition==2)
                {
                    foundItemFragment.searchPost(key);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                 if(frgPosition==1)
                 {
                     lostItemFragment.searchPost(key);
                 }
                 if(frgPosition==2)
                 {
                     foundItemFragment.searchPost(key);
                 }
                    return true;

                }
                return false;
            }
        });
        img_filter=(ImageView)view.findViewById(R.id.img_filter);
        img_filter.setOnClickListener(this);
            viewPager = (ViewPager)view.findViewById(R.id.viewpager);
            setupViewPager(viewPager);
            tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
            tabLayout.setupWithViewPager(viewPager);

    }



    LostItemFragment lostItemFragment;
    FoundItemFragment foundItemFragment;
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        lostItemFragment=new LostItemFragment();
        foundItemFragment=new FoundItemFragment();
        adapter.addFragment(new AllLostFoundItemFragment()  , getContext().getResources().getString(R.string.all));
        adapter.addFragment(lostItemFragment  , getContext().getResources().getString(R.string.lost));
        adapter.addFragment(foundItemFragment, getContext().getResources().getString(R.string.found));

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //   LocaleManager.setNewLocale(context,"ar");
                frgPosition=position;
                showToast("position"+position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_filter:
                Intent intent=new Intent(context, FilterActivity.class);
                startActivityForResult(intent, Constant.REQUEST_CODE_FILTER);
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    public void getCategory()
    {
        if (isInternetConnected()) {
            showLoading();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<CategoryResponse> call = apiInterface.getCategory();
            call.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response)
                {
                    dismiss_loading();
                    CategoryResponse categoryResponse=response.body();

                    if(categoryResponse.getStatus()==1)
                    {
                        list =categoryResponse.getCategoryDataList();

                        int[] colors = context.getResources().getIntArray(R.array.rainbow);
                        CategoryCustomAdapter categoryGridAdapter =new CategoryCustomAdapter(context, list,new ApiCallback() {
                            @Override
                            public void result(int position)
                            {
                                CategoryData categoryData=list.get(position);
                                Intent intent=new Intent(context, SubCategoryActivity.class);
                                intent.putExtra("ID",categoryData.getCategory_id());
                                intent.putExtra("NAME",categoryData.getCategory_title());
                                intent.putExtra("POST_TYPE","3");
                                startActivity(intent);

                            }
                        });
                        recyclerView.setAdapter(categoryGridAdapter);
                    }

                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable t) {

                }
            });
        }
        else
        {
            showInternetConnectionToast();
        }

    }







}
