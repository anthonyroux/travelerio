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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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


public class FragmentPointsInterest extends Fragment {

    private Amadeus mAmadeus;

    private Float mLat, mLng;
    private String mCityName;
    private Context mContext;

    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> mTags = new ArrayList<>();

    private RecyclerView recyclerView;
    private PointsInterestAdapter pointsInterestAdapter;

    private RelativeLayout mPointsInterestLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        mAmadeus = Amadeus
                .builder(getString(R.string.amadeus_api_key), getString(R.string.amadeus_client_secret))
                .build();

        if(getArguments() != null) {
            mLat = getArguments().getFloat("Lat");
            mLng = getArguments().getFloat("Lng");
            mCityName = getArguments().getString("CityName");
        }

        pointsInterestAdapter = new PointsInterestAdapter(mName, mTags, mContext);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_points_interest, container, false);

        mPointsInterestLayout = (RelativeLayout) view.findViewById(R.id.points_interest_progress_layout);
        mPointsInterestLayout.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recycler_points_interest);
        recyclerView.setAdapter(pointsInterestAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView cityName = (TextView) view.findViewById(R.id.textview_points_interest);
        String toolbar = "Popular places in " + mCityName;
        cityName.setText(toolbar);

        new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //your code or your request that you want to run on uiThread
                        try {
                            PointOfInterest[] pointsOfInterest = mAmadeus.referenceData.locations.pointsOfInterest.get(Params
                                    .with("latitude", mLat)
                                    .and("longitude", mLng));


                            JSONObject jsonObject = new JSONObject(pointsOfInterest[0].getResponse().getBody());

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject lol = jsonArray.getJSONObject(i);
                                String name = lol.getString("name");

                                String amenitiesString = "";
                                JSONArray tags = lol.getJSONArray("tags");
                                for(int a = 0; a < tags.length(); a++){
                                    if(a != tags.length()-1){
                                        amenitiesString += tags.getString(a) + ", ";
                                    }else{
                                        amenitiesString += tags.getString(a);
                                    }
                                }


                                mName.add(name);
                                mTags.add(amenitiesString);

                            }

                            pointsInterestAdapter.notifyDataSetChanged();

                            mPointsInterestLayout.setVisibility(View.GONE);


                        } catch (ResponseException e) {
                            Log.d(TAG, "onCreateView: FragmentSearch Error: " + e);
                            Toast.makeText(mContext, "Sandbox coordinates out of the allowed box", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();

        ImageButton backArrow = (ImageButton) view.findViewById(R.id.fragment_discover_back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });



        return view;
    }


}
