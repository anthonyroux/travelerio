package com.example.evan.travelerio;


import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.PointOfInterest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;
import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;


public class FragmentDiscover extends Fragment {


    private RecyclerView recyclerView;
    private DiscoverAdapter discoverAdapter;

    private ArrayList<String> mImagePreview = new ArrayList<>();
    private ArrayList<String> mCityName = new ArrayList<>();

    private ArrayList<Object> mLat = new ArrayList<>();
    private ArrayList<Object> mLng = new ArrayList<>();

    private Context mContext;

    private RelativeLayout mDiscoverProgressLayout;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        discoverAdapter = new DiscoverAdapter(mImagePreview, mCityName, mLat, mLng, mContext);

        mImagePreview.add("https://i.imgur.com/WRl92Me.jpg");
        mImagePreview.add("https://i.imgur.com/K8EEvRy.jpg");
        mImagePreview.add("https://i.imgur.com/2yaLF4h.jpg");
        mImagePreview.add("https://i.imgur.com/ypyvl1w.jpg");
        mImagePreview.add("https://i.imgur.com/2gHRGE0.jpg");
        mImagePreview.add("https://i.imgur.com/pfzS4qE.jpg");
        mImagePreview.add("https://i.imgur.com/marSnny.jpg");
        mImagePreview.add("https://i.imgur.com/ZOYkZBp.jpg");

        mCityName.add("Bangalore");
        mCityName.add("Barcelona");
        mCityName.add("Berlin");
        mCityName.add("Dallas");
        mCityName.add("London");
        mCityName.add("New York");
        mCityName.add("Paris");
        mCityName.add("San Francisco");

        mLat.add("13.023577");
        mLat.add("41.42");
        mLat.add("52.5200");
        mLat.add("32.7767");
        mLat.add("51.520180");
        mLat.add("40.792027");
        mLat.add("48.91");
        mLat.add("37.7749");

        mLng.add("77.536856");
        mLng.add("2.11");
        mLng.add("13.4050");
        mLng.add("96.7970");
        mLng.add("-0.169882");
        mLng.add("-74.058204");
        mLng.add("2.25");
        mLng.add("122.4194");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        mDiscoverProgressLayout = (RelativeLayout) view.findViewById(R.id.discover_progress_layout);
        mDiscoverProgressLayout.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recycler_discover);
        recyclerView.setAdapter(discoverAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageButton backArrow = (ImageButton) view.findViewById(R.id.fragment_discover_back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });


        discoverAdapter.notifyDataSetChanged();

        mDiscoverProgressLayout.setVisibility(View.GONE);




        return view;
    }


}
