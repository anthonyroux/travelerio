package com.example.evan.travelerio;


import android.content.Context;
import android.os.Bundle;
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
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class FragmentCitySpots extends Fragment {



    private RecyclerView recyclerView;
    private CitySpotsAdapter citySpotsAdapter;
    private Context mContext;
    private RequestQueue mQueue;

    private ArrayList<String> mImage = new ArrayList<>();
    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> mRating = new ArrayList<>();
    private ArrayList<String> mReviews = new ArrayList<>();
    private ArrayList<String> mChannelName = new ArrayList<>();
    private ArrayList<String> mTempToken = new ArrayList<>();

    private long mCurrentTime;

    private String mCityId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        if(getArguments() != null) {
            mCityId = getArguments().getString("city_id");
            Log.d(TAG, "onCreate: mCityId: " + mCityId);
        }

        citySpotsAdapter = new CitySpotsAdapter(mImage, mName, mRating, mReviews, mChannelName, mTempToken, mContext);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_spots, container, false);

        recyclerView = view.findViewById(R.id.recycler_cityspots);
        recyclerView.setAdapter(citySpotsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        jsonParse();

        ImageButton backButton = (ImageButton) view.findViewById(R.id.fragment_city_spots_back_arrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }


    private void jsonParse(){

        mQueue = Volley.newRequestQueue(mContext);

        String url = "https://64zepauzch.execute-api.us-east-1.amazonaws.com/FF/cityspots?key1=" + mCityId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    mCurrentTime = response.getLong("Server Time");

                    JSONArray citySpots = response.getJSONArray("City Spots");

                    for(int i = 0; i < citySpots.length(); i++){
                        JSONObject jsonObject = citySpots.getJSONObject(i);
                        String spot_image = jsonObject.getString("spot_image");
                        String spot_name = jsonObject.getString("spot_name");
                        int spot_rating = jsonObject.getInt("spot_rating");
                        int number_reviews = jsonObject.getInt("number_reviews");
                        String string_reviews = String.valueOf(number_reviews) + " reviews";
                        String channel_name = jsonObject.getString("channel_name");
                        String temp_token = jsonObject.getString("temp_token");


                        mImage.add(spot_image);
                        mName.add(spot_name);
                        mRating.add(String.valueOf(spot_rating));
                        mReviews.add(string_reviews);

                        mChannelName.add(channel_name);
                        mTempToken.add(temp_token);


                    }

                    Log.d(TAG, "onResponse: mChannelName: " + mChannelName);

                    citySpotsAdapter.notifyDataSetChanged();

                }catch(JSONException e){
                    Log.d(TAG, "onResponse: " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "FragmentAllCommentsPost: Function is cold timeout " + error);

            }
        });

        mQueue.add(request);

    }

}
