package com.example.evan.travelerio;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ViewpagerFragmentRecentActivity extends Fragment {

    private RecyclerView recyclerView;
    private HorizontalAdapter horizontalAdapter;

    private ArrayList<String> mHotelName = new ArrayList<>();
    private ArrayList<String> mHotelPreview = new ArrayList<>();
    private Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();

        horizontalAdapter = new HorizontalAdapter(mHotelName, mHotelPreview, mContext);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_fragment_recent_activity, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_recent_activity);
        recyclerView.setAdapter(horizontalAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        mHotelName.add("Ritz Carlton Hotel");
        mHotelName.add("Marriott Marquis");
        mHotelName.add("Four Seasons Resort Bali");

        mHotelPreview.add("https://i.imgur.com/kh4gLdl.jpg");
        mHotelPreview.add("https://i.imgur.com/BieDfji.jpg");
        mHotelPreview.add("https://i.imgur.com/yufsKQP.jpg");

        horizontalAdapter.notifyDataSetChanged();

        return view;
    }
}
