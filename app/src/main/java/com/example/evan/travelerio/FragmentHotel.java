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
import android.widget.Toast;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.PointOfInterest;
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
import java.util.List;
import java.util.Random;

import static android.support.constraint.Constraints.TAG;
import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;


public class FragmentHotel extends Fragment {

    private Amadeus mAmadeus;

    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;

    private ArrayList<String> mHotelImage = new ArrayList<>();
    private ArrayList<String> mHotelName = new ArrayList<>();
    private ArrayList<String> mHotelRating = new ArrayList<>();
    private ArrayList<String> mHotelReviews = new ArrayList<>();
    private ArrayList<String> mHotelDistance = new ArrayList<>();
    private ArrayList<String> mHotelPrice = new ArrayList<>();
    private ArrayList<String> mCurrencyType = new ArrayList<>();

    private ArrayList<String> mHotelDescription = new ArrayList<>();
    private ArrayList<String> mHotelAttributes = new ArrayList<>();

    private ArrayList<String> mRandomList = new ArrayList<>();
    private ArrayList<String> mChannelName = new ArrayList<>();
    private ArrayList<String> mTempToken = new ArrayList<>();
    private Random randomGenerator;

    private Context mContext;

    private RequestQueue mQueue;
    private RelativeLayout mHotelProgressLayout;

    private String mCityCode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        mAmadeus = Amadeus
                .builder(getString(R.string.amadeus_api_key), getString(R.string.amadeus_client_secret))
                .build();

        if(getArguments() != null) {
            mCityCode = getArguments().getString("city_code");
            Log.d(TAG, "onCreate: mCityId: " + mCityCode);
        }



        hotelAdapter = new HotelAdapter(mHotelImage, mHotelName, mHotelDistance, mHotelReviews, mHotelPrice,
                mHotelRating, mHotelDescription, mHotelAttributes, mCurrencyType, mChannelName, mTempToken, mContext);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);

        recyclerView = view.findViewById(R.id.fragment_hotel_recyclerview);
        recyclerView.setAdapter(hotelAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mHotelProgressLayout = (RelativeLayout) view.findViewById(R.id.hotel_progress_layout);
        mHotelProgressLayout.setVisibility(View.VISIBLE);

        ImageButton backArrow = (ImageButton) view.findViewById(R.id.fragment_hotel_back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //your code or your request that you want to run on uiThread
                        try {
                            HotelOffer[] offers = mAmadeus.shopping.hotelOffers.get(Params
                                    .with("cityCode", mCityCode));


                            JSONObject jsonObject = new JSONObject(offers[0].getResponse().getBody());



                            JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                            for(int i = 0; i < jsonArrayData.length(); i++){
                                JSONObject dataObject = jsonArrayData.getJSONObject(i);
                                JSONObject hotelObject = dataObject.getJSONObject("hotel");
                                JSONObject distanceObject = hotelObject.getJSONObject("hotelDistance");

                                //Log.d(TAG, "run: DATAOBJECT: " + dataObject.toString(4));


                                if(!hotelObject.has("name")){
                                    mHotelName.add("No Rating");
                                }else{
                                    String hotel_name = hotelObject.getString("name");
                                    mHotelName.add(hotel_name);
                                }

                                if(!distanceObject.has("distance")){
                                    mHotelName.add("null");
                                }else{
                                    String hotel_distance = distanceObject.getString("distance");
                                    String hotel_km = hotel_distance + " km";
                                    mHotelDistance.add(hotel_km);
                                }

                                if(!hotelObject.has("rating")){
                                    mHotelRating.add("No Rating");
                                }else{
                                    String hotel_rating = hotelObject.getString("rating");
                                    mHotelRating.add(hotel_rating);
                                }



                                if(!hotelObject.has("description")){
                                    mHotelDescription.add("No Description");
                                }else{
                                    JSONObject hotel_description = hotelObject.getJSONObject("description");
                                    String text = hotel_description.getString("text");
                                    mHotelDescription.add(text);
                                }

                                if(!hotelObject.has("amenities")){
                                    mHotelAttributes.add("Amenities N/A");
                                }else{
                                    JSONArray hotel_attributes = hotelObject.getJSONArray("amenities");
                                    String amenitiesString = "";
                                    for(int a = 0; a < 8; a++){
                                        Log.d(TAG, "run: GG: " + hotel_attributes.get(a));
                                        if(a != 7){
                                            amenitiesString += hotel_attributes.getString(a) + ", ";
                                        }else{
                                            amenitiesString += hotel_attributes.getString(a) + " . . .";
                                        }

                                    }
                                    mHotelAttributes.add(amenitiesString);

                                }

                            }

                            for (int i = 0; i < jsonArrayData.length(); i++){
                                JSONObject dataObject = jsonArrayData.getJSONObject(i);
                                JSONArray offersArray= dataObject.getJSONArray("offers");

                                JSONObject price = offersArray.getJSONObject(0);
                                JSONObject total = price.getJSONObject("price");


                                if(!total.has("total")){
                                    mHotelPrice.add("N/A");
                                }else{
                                    mHotelPrice.add(total.getString("total"));
                                }

                                if(!total.has("currency")){
                                    mCurrencyType.add("N/A");
                                }else{
                                    mCurrencyType.add(total.getString("currency"));
                                }



                            }



                            jsonParse();



                            Log.d(TAG, "run: HotelName: " + mHotelName);
                            Log.d(TAG, "run: HotelRating: " + mHotelRating);
                            Log.d(TAG, "run: HotelDistance: " + mHotelDistance);
                            Log.d(TAG, "run: HotelDescription: " + mHotelDescription);
                            Log.d(TAG, "run: HotelPrice: " + mHotelPrice);
                            Log.d(TAG, "run: CurrencyType: " + mCurrencyType);
                            Log.d(TAG, "run: HotelAttributes: " + mHotelAttributes);



                        } catch (ResponseException e) {
                            Log.d(TAG, "onCreateView: FragmentSearch Error: " + e);
                            Toast.makeText(mContext, "Invalid city code", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();








        return view;
    }

    private void jsonParse(){

        mQueue = Volley.newRequestQueue(mContext);

        String url = "https://64zepauzch.execute-api.us-east-1.amazonaws.com/FF/hoteldata";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    JSONArray hotelData = response.getJSONArray("Hotel Data");

                    for(int i = 0; i < hotelData.length(); i++){
                        JSONObject jsonObject = hotelData.getJSONObject(i);

                        String hotel_image = jsonObject.getString("hotel_image");
                        int hotel_reviews = jsonObject.getInt("hotel_reviews");
                        String hotel_reviews_string = String.valueOf(hotel_reviews) + " reviews";
                        String channel_name = jsonObject.getString("channel_name");
                        String temp_token = jsonObject.getString("temp_token");

                        mRandomList.add(hotel_image);
                        mHotelReviews.add(hotel_reviews_string);
                        mChannelName.add(channel_name);
                        mTempToken.add(temp_token);



                    }

                    for(int i = 0; i < mRandomList.size(); i++){

                        randomGenerator = new Random();
                        int index = randomGenerator.nextInt(mRandomList.size());
                        mHotelImage.add(mRandomList.get(index));
                    }

                    Log.d(TAG, "onResponse: Hotel Image: " + mHotelImage);
                    Log.d(TAG, "onResponse: Hotel Reviews: " + mHotelReviews);


                    mHotelProgressLayout.setVisibility(View.GONE);


                    hotelAdapter.notifyDataSetChanged();

                }catch(JSONException e){
                    Log.d(TAG, "onResponse: " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "FragmentHotel: Function is cold timeout " + error);

            }
        });

        mQueue.add(request);

    }


}
