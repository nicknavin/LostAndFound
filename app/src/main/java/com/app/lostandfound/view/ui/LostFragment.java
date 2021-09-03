package com.app.lostandfound.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lostandfound.R;
import com.app.lostandfound.adapter.CategoryAdapter;
import com.app.lostandfound.lostfoundinterface.ApiCallback;
import com.app.lostandfound.utility.Constant;
import com.app.lostandfound.view.SubCategoryActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LostFragment newInstance(String param1, String param2) {
        LostFragment fragment = new LostFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view)
    {
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        int[] colors = getContext().getResources().getIntArray(R.array.rainbow);
//        TypedArray  = getContext().getResources().getIntArray(R.array.progress_colors_light);
        CategoryAdapter categoryAdapter=new CategoryAdapter(getContext(), colors, new ApiCallback() {
            @Override
            public void result(int position) {
                Intent intent=new Intent(getContext(), SubCategoryActivity.class);
                intent.putExtra("ID","id");
//                getActivity().startActivity(intent);
                startActivityForResult(intent, Constant.SUB_CATEGORY_CODE);

            }
        });
        recyclerView.setAdapter(categoryAdapter);


    }

    private void setBackgroundDrawableColor()
    {
//        GradientDrawable gradientDrawable = (GradientDrawable) edit.getBackground().mutate();
//        gradientDrawable.setColor(Color.BLUE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {


            case Constant.SUB_CATEGORY_CODE:
              //  ((HomeActivity)getActivity()).callLostFoundProductFrag();
                break;


            default:
                break;
        }
    }
}
