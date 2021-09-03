package com.app.lostandfound.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.lostandfound.R;
import com.app.lostandfound.base.BaseActivity;
import com.app.lostandfound.costomview.CustomEditText;
import com.app.lostandfound.costomview.CustomTextView;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.view.ui.AllLostFoundItemFragment;
import com.app.lostandfound.view.ui.ExploreFragment;
import com.app.lostandfound.view.ui.FoundItemFragment;
import com.app.lostandfound.view.ui.FoundPostByCategoryFragment;
import com.app.lostandfound.view.ui.LostItemFragment;
import com.app.lostandfound.view.ui.LostPostByCategoryFragment;
import com.app.lostandfound.view.ui.RegionActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class LostFoundByCategoryActivity extends BaseActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String key;
    int frgPosition=0;
    CustomEditText edt_search;
    String catg="",subcatg="";
CustomTextView txtFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_found_by_category);
        if(getIntent().getStringExtra("CAT_ID")!=null)
        {
            catg=getIntent().getStringExtra("CAT_ID");
        }
        if(getIntent().getStringExtra("SUB_ID")!=null)
        {
            subcatg=getIntent().getStringExtra("SUB_ID");
        }
        initView();
    }

    public void initView()
    {
        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        edt_search=(CustomEditText)findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                key = s.toString().trim();

                if(frgPosition==0)
                {
                    lostPostByCategoryFragment.searchPost(key);
                }
                if(frgPosition==1)
                {
                    foundPostByCategoryFragment.searchPost(key);
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
                    if(frgPosition==0)
                    {
                        lostPostByCategoryFragment.searchPost(key);
                    }
                    if(frgPosition==1)
                    {
                        foundPostByCategoryFragment.searchPost(key);
                    }
                    return true;

                }
                return false;
            }
        });

        txtFilter=(CustomTextView)findViewById(R.id.txtFilter);
        txtFilter.setOnClickListener(this);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

public  LostPostByCategoryFragment lostPostByCategoryFragment;
    public FoundPostByCategoryFragment foundPostByCategoryFragment;
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        lostPostByCategoryFragment= LostPostByCategoryFragment.newInstance(catg,subcatg);
        foundPostByCategoryFragment=FoundPostByCategoryFragment.newInstance(catg,subcatg);
        adapter.addFragment(lostPostByCategoryFragment,context.getResources().getString(R.string.lost));
        adapter.addFragment( foundPostByCategoryFragment,context.getResources().getString(R.string.found));

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //   LocaleManager.setNewLocale(context,"ar");
                frgPosition=position;

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
            case R.id.txtFilter:
                Intent intent=new Intent(context,FilterPostActivity.class);
                startActivity(intent);
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



}
